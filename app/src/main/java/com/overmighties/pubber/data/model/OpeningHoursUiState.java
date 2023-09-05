package com.overmighties.pubber.data.model;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpeningHoursUiState {
    private String weekday;
    private String timeOpen;
    private String timeClose;
}
