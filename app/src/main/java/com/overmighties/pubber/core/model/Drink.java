package com.overmighties.pubber.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drink {
    @NonNull
    private String name;
    @NonNull
    private String type;
}
