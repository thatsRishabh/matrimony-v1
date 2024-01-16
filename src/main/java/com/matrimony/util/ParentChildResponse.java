package com.matrimony.util;

import com.matrimony.entity.Menu;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParentChildResponse {

    private Menu menu;
    private List<Menu> subMenu;


}