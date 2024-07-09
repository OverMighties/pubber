package com.overmighties.pubber.core.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private List<Integer> pubsSavedList;
    private ThemeConfig themeConfig;

}
