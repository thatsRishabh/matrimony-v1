package com.matrimony.controller;

import com.matrimony.entity.Slider;
import com.matrimony.entity.Subscription;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.service.SliderService;
import com.matrimony.service.SubscriptionService;
import com.matrimony.validator.SliderValidation;
import com.matrimony.validator.SubscriptionValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    //add menu
    @PostMapping()
    public ResponseEntity<ApiResponse<Subscription>> addSubscription(@Valid @RequestBody SubscriptionValidation subscriptionRequest){
        return this.subscriptionService.createSubscription(subscriptionRequest);
    }

    //get all category
    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<Object>> getSubscriptions(@RequestBody SearchPaginationRequest searchParams){
        return this.subscriptionService.getSubscriptions(searchParams);
    }

    @PutMapping("/{subscriptionId}")
    public ResponseEntity<ApiResponse<Subscription>>  updateSubscription(@PathVariable("subscriptionId") Long subscriptionId , @Valid @RequestBody SubscriptionValidation subscriptionRequest){
        return this.subscriptionService.updateSubscription(subscriptionId, subscriptionRequest);
    }

    // get single category
    @GetMapping("/{subscriptionId}")
    public ResponseEntity<ApiResponse<Subscription>>  getSubscription(@PathVariable("subscriptionId") Long subscriptionId){
        return this.subscriptionService.getSubscription(subscriptionId);
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<ApiResponse<?>> deleteSubscription(@PathVariable("subscriptionId") Long subscriptionId){
        return this.subscriptionService.deleteSubscription(subscriptionId);
    }
}
