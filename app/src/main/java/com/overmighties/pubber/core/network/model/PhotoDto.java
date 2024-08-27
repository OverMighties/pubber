package com.overmighties.pubber.core.network.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDto {
    @SerializedName("title")
    private String title;
    @SerializedName("photoUrl")
    private String photoUrl;
}
