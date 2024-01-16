package com.matrimony.controller;

import com.matrimony.entity.Menu;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.MenuService;
import com.matrimony.util.ParentChildResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<Menu>> addMenu(@RequestBody Menu menuRequest){
        return this.menuService.createMenu(menuRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getMenus(@RequestBody SearchPaginationRequest searchParams){
        return this.menuService.getMenus(searchParams);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<ApiResponse<Menu>>  updateMenu(@PathVariable("menuId") Long menuId ,@RequestBody Menu menuRequest){
        return this.menuService.updateMenu(menuId, menuRequest);
    }

    // get single category
    @GetMapping("/{menuId}")
    public ResponseEntity<ApiResponse<Menu>>  getMenu(@PathVariable("menuId") Long menuId){
        return this.menuService.getMenu(menuId);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<ApiResponse<?>> deleteMenu(@PathVariable("menuId") Long menuId){
        return this.menuService.deleteMenu(menuId);
    }

}
