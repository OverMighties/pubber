package com.overmighties.pubber.core.model;


import com.overmighties.pubber.util.DayOfWeekConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeningHours {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private String weekday;
    private String timeOpen;
    private String timeClose;
    public LocalTime getLocalTimeOpen()
    {
        return  LocalTime.parse(timeOpen,TIME_FORMATTER);
    }
    public LocalTime getLocalTimeClose()
    {
        return  LocalTime.parse(timeClose,TIME_FORMATTER);
    }
    public DayOfWeekConverter getDayOfWeekConverter()
    {
        return DayOfWeekConverter.getByUpperCase(weekday);
    }
}
