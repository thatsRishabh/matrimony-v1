package com.matrimony.service;

import com.matrimony.entity.Menu;
import com.matrimony.request.SearchPaginationRequest;
import com.matrimony.response.ApiResponse;
import com.matrimony.util.ParentChildResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MenuService {
    public ResponseEntity<ApiResponse<Menu>> createMenu(Menu menuRequest);

    public ResponseEntity<ApiResponse<Object>> getMenus(SearchPaginationRequest searchParams);

    public ResponseEntity<ApiResponse<Menu>> updateMenu(Long menuId, Menu menuRequest);

    public ResponseEntity<ApiResponse<Menu>>  getMenu(Long menuId);

    public ResponseEntity<ApiResponse<?>> deleteMenu(Long menuId);

}
