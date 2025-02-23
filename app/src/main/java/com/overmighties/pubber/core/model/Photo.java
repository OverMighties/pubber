package com.overmighties.pubber.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    @NonNull
    private String title;
    @NonNull
    private String photoPath;
}
