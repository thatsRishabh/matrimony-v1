package com.matrimony.service;

import com.matrimony.entity.Slider;
import com.matrimony.entity.Subscription;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.validator.SliderValidation;
import com.matrimony.validator.SubscriptionValidation;
import org.springframework.http.ResponseEntity;

public interface SubscriptionService {
    public ResponseEntity<ApiResponse<Subscription>> createSubscription(SubscriptionValidation subscriptionRequest);

    public ResponseEntity<ApiResponse<Object>> getSubscriptions(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<Subscription>> updateSubscription(Long subscriptionId, SubscriptionValidation subscriptionRequest);

    public ResponseEntity<ApiResponse<Subscription>>  getSubscription(Long subscriptionId);

    public ResponseEntity<ApiResponse<?>> deleteSubscription(Long subscriptionId);
}
