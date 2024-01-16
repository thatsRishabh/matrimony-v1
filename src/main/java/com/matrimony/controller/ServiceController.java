package com.matrimony.controller;

import com.matrimony.entity.ServiceEntity;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.ServiceService;
import com.matrimony.validator.ServiceValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<ServiceEntity>> addService(@Valid @RequestBody ServiceValidation serviceRequest){
        return this.serviceService.createService(serviceRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getServices(@RequestBody SearchPaginationRequest searchParams){
        return this.serviceService.getServices(searchParams);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<ApiResponse<ServiceEntity>>  updateService(@PathVariable("serviceId") Long serviceId , @Valid @RequestBody ServiceValidation serviceRequest){
        return this.serviceService.updateService(serviceId, serviceRequest);
    }

    // get single category
    @GetMapping("/{serviceId}")
    public ResponseEntity<ApiResponse<ServiceEntity>>  getService(@PathVariable("serviceId") Long serviceId){
        return this.serviceService.getService(serviceId);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<ApiResponse<?>> deleteService(@PathVariable("serviceId") Long serviceId){
        return this.serviceService.deleteService(serviceId);
    }
}
