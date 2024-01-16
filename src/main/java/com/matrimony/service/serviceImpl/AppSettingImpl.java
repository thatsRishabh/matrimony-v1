package com.matrimony.service.serviceImpl;

import com.matrimony.entity.AppSettings;
import com.matrimony.repository.AppSettingRepository;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.AppSettingService;
import com.matrimony.validator.AppSettingValidation;
import jakarta.persistence.PrePersist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class AppSettingImpl implements AppSettingService {

    @Autowired
    AppSettingRepository appSettingRepository;

    //    below method will by default create the timestamp
    @PrePersist
    public void prePersist(AppSettings appSettings) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        appSettings.setCreatedAt(now);
        appSettings.setUpdatedAt(now);
    }
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity<ApiResponse<Object>> getAppSetting(SearchPaginationRequest searchParams) {

        try {
            Long id = searchParams.getId();
            String title = searchParams.getTitle();
            Integer perPageRecord = searchParams.getPer_page_record();
            // Set the default value of page to 1
            Integer page = (searchParams.getPage() != null) ? searchParams.getPage() : 1;
            Page<AppSettings> sliderPage;
            sliderPage = appSettingRepository.findAll(PageRequest.of(page - 1, perPageRecord,Sort.by(Sort.Order.desc("id"))));
            List<AppSettings> sliderEntities = sliderPage.getContent();
            Map<String, Object> map = Map.of(
                    "data", sliderEntities,
                    "totalElements", sliderPage.getTotalElements(),
                    "currentPage", page,
                    "perPageRecord", perPageRecord,
                    "totalPages", sliderPage.getTotalPages()
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
    public ResponseEntity<ApiResponse<AppSettings>> addOrUpdateAppSetting(AppSettingValidation settingRequest) {
        try {
            AppSettings setting = appSettingRepository.findFirstByOrderByIdAsc().orElse(new AppSettings());
            setting.setApp_name(settingRequest.getApp_name());
            setting.setLogo_path(settingRequest.getLogo_path());
            setting.setLogo_thumb_path(settingRequest.getLogo_thumb_path());
            setting.setFav_icon(settingRequest.getFav_icon());
            setting.setCall_us(settingRequest.getCall_us());
            setting.setMail_us(settingRequest.getMail_us());
            setting.setDescription(settingRequest.getDescription());
            setting.setAddress(settingRequest.getAddress());
            setting.setFb_url(settingRequest.getFb_url());
            setting.setTwitter_url(settingRequest.getTwitter_url());
            setting.setInsta_url(settingRequest.getInsta_url());
            setting.setLinkedIn_url(settingRequest.getLinkedIn_url());
            setting.setPinterest_url(settingRequest.getPinterest_url());
            setting.setCopyright_text(settingRequest.getCopyright_text());
            setting.setMeta_title(settingRequest.getMeta_title());
            setting.setMeta_keywords(settingRequest.getMeta_keywords());
            setting.setMeta_description(settingRequest.getMeta_description());
            setting.setOrg_number(settingRequest.getOrg_number());
            setting.setCustomer_care_number(settingRequest.getCustomer_care_number());
            setting.setAllowed_app_version(settingRequest.getAllowed_app_version());
            setting.setInvite_url(settingRequest.getInvite_url());
            setting.setPlay_store_url(settingRequest.getPlay_store_url());
            setting.setApp_store_url(settingRequest.getApp_store_url());
            setting.setSupport_email(settingRequest.getSupport_email());
            setting.setSupport_contact_number(settingRequest.getSupport_contact_number());
            setting.setCertificate_image(settingRequest.getCertificate_image());
            appSettingRepository.save(setting);

                return ResponseEntity.ok(new ApiResponse<>("success", "Data updated successfully", setting, 200));
        } catch (Exception e) {
            // Handle the exception here and log it
            log.error("An error occurred while updating data", e);
            return ResponseEntity.internalServerError()
                    .body(new ApiResponse<>("error", e.getMessage(), null, 500));
        }
    }

}
