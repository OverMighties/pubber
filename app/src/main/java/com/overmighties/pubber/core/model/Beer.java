package com.overmighties.pubber.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Beer {
    private Long beerId;
    private String longDescription;
    private String shortDescription;
    private String photoUrl;
    private String maltiness;
    private String blg;
    private String alcoholContent;
}
