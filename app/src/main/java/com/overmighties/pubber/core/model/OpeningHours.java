package com.overmighties.pubber.core.model;


import com.overmighties.pubber.util.DayOfWeek;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeningHours {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    @NonNull
    private String weekday;
    @NonNull
    private String timeOpen;
    @NonNull
    private String timeClose;
    public LocalTime getLocalTimeOpen()
    {
        return  LocalTime.parse(timeOpen,TIME_FORMATTER);
    }
    public LocalTime getLocalTimeClose()
    {
        return  LocalTime.parse(timeClose,TIME_FORMATTER);
    }
    public DayOfWeek getDayOfWeekConverter()
    {
        return DayOfWeek.getByUpperCase(weekday);
    }
}
