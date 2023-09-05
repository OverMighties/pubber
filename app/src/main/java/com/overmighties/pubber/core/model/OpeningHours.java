package com.overmighties.pubber.core.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class OpeningHours {
    private String weekday;
    private String timeOpen;
    private String timeClose;
}
