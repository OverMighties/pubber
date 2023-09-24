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
public class Photo {
    @NonNull
    private String title;
    @NonNull
    private String photoPath;
}
