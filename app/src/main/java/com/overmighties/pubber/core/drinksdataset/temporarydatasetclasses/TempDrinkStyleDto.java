package com.overmighties.pubber.core.drinksdataset.temporarydatasetclasses;

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
public class TempDrinkStyleDto {
    @SerializedName("styleName")
    private String styleName;
}
