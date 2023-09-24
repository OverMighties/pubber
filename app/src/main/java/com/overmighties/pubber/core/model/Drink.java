package com.overmighties.pubber.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drink {
    @NonNull
    private String name;
    @NonNull
    private String type;
}
