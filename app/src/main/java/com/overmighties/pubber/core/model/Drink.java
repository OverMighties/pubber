package com.overmighties.pubber.core.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drink {
    private Long drinkId;
    @NonNull
    private String name;
    @NonNull
    private String type;
    private String description;
    @Nullable
    private List<DrinkStyle> drinkStyles;
    @Nullable
    private Beer beer;
}
