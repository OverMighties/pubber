package com.overmighties.pubber.core.network.model;

import android.graphics.Paint;

import androidx.core.util.Pair;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrinkDto {
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("styles")
    private List<StyleDto> styles;
}
