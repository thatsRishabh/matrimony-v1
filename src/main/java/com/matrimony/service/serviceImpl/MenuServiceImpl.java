package com.matrimony.service.serviceImpl;

import com.matrimony.entity.Menu;
import com.matrimony.repository.MenuRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.MenuService;
import com.matrimony.util.ParentChildResponse;
import jakarta.persistence.PrePersist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<Menu>> createMenu(Menu menuRequest) {
        try {
            Menu payload = this.menuRepository.save(menuRequest);
            return ResponseEntity.ok(new ApiResponse<>("success", "Data saved successfully", payload, 200));
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while saving data", e);
            return ResponseEntity.internalServerError().body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

//   below method will by default create the timestamp
    @PrePersist
    public void prePersist(Menu menu) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        menu.setCreatedAt(now);
        menu.setUpdatedAt(now);
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> getMenus(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String name = searchParams.getName();
            Integer perPageRecord = searchParams.getPer_page_record();

            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;

            Page<Menu> categoryPage;

            if (id != null) {
                Optional<Menu> categoryOptional = menuRepository.findById(id);
                if (categoryOptional.isPresent()) {
                    Menu category = categoryOptional.get();
                    categoryPage = new PageImpl<>(Collections.singletonList(category));
                } else {
                    categoryPage = Page.empty(); // No matching category found
                }
            }
            else if (name != null) {
                categoryPage = menuRepository.findByNameContaining(name, PageRequest.of(page - 1, perPageRecord, Sort.by(Sort.Order.desc("id"))));
            }
            else {
//                categoryPage = menuRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
                categoryPage = menuRepository.findAllByParentIdIsNull(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            }
            //  Below code is when we are making any join to two tables
                       List<Menu> parents = categoryPage.getContent();
                       List<ParentChildResponse> responseList = new ArrayList<>();

                        for (Menu parent : parents) {
                        List<Menu> children = menuRepository.findByParentId(parent.getId());
                        ParentChildResponse response = new ParentChildResponse();
        //            response.setParentId(parent.getId());
                        response.setMenu(parent);
                        response.setSubMenu(children);
                        responseList.add(response);
                    }

            Map<String, Object> map = Map.of(
                    "data", responseList,
                    "totalElements", categoryPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", categoryPage.getTotalPages()
            );
            return ResponseEntity.ok( new ApiResponse<>("success", "Data retrieved successfully", map, 200));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("An error occurred while saving data", e);
//            return e.getMessage();
            return ResponseEntity.internalServerError().body( new ApiResponse<>("error", e.getMessage(), null, 500));
        }

    }

    @Override
    public ResponseEntity<ApiResponse<Menu>> updateMenu(Long menuId, Menu menuRequest) {
        try {
            Optional<Menu> existingMenu = this.menuRepository.findById(menuId);

            if (existingMenu.isPresent()) {
                // Update the existing category with the new data
                Menu updatedMenu = existingMenu.get();
                updatedMenu.setName(menuRequest.getName());
                updatedMenu.setSlug(menuRequest.getSlug());
                updatedMenu.setUrl(menuRequest.getUrl());
                updatedMenu.setOrder_number(menuRequest.getOrder_number());
                updatedMenu.setPosition_type(menuRequest.getPosition_type());
                updatedMenu.setStatus(menuRequest.getStatus());
                updatedMenu.setIcon_path(menuRequest.getIcon_path());
                Menu payload = this.menuRepository.save(updatedMenu);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data updated successfully", payload, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "MenuId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while updating data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Menu>>  getMenu(Long menuId) {
        try {
            Optional<Menu> menuEntityOptional = this.menuRepository.findById(menuId);

            if (menuEntityOptional.isPresent()) {
                Menu categoryEntity = menuEntityOptional.get();
                return ResponseEntity.ok(new ApiResponse<>("success", "Data retrieved successfully", categoryEntity, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "MenuId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while retrieving data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> deleteMenu(Long menuId) {
        try {
            Optional<Menu> category = this.menuRepository.findById(menuId);
            if (category.isPresent()) {
                //  CategoryEntity categoryEntity= new CategoryEntity();
                //  categoryEntity.setCid(categoryID);
                //  this.categoryRepository.delete(categoryEntity);

                // below one is easy single line code

                this.menuRepository.deleteById(menuId);
                return ResponseEntity.ok(new ApiResponse<>("success", "Data deleted successfully", null, 200));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("error", "MenuId not found", null, 404));
            }
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while deleting data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

}
