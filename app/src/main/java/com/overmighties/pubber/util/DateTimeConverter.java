package com.overmighties.pubber.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {
    //Blocking default constructor
    private DateTimeConverter(){
        throw new AssertionError();
    }
    private static final String dateTimeConstant = " 01 01 2000";
    private static final DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("HH:mm dd MM yyyy");
    private static final DateTimeFormatter hoursTimeFormatter=DateTimeFormatter.ofPattern("HH:mm");

    public static String  getToStringDate(LocalDateTime localDateTime)
    {
       return  localDateTime==null?null:localDateTime.format(dateTimeFormatter);
    }
    public static LocalDateTime  getFromStringDate(String localDateTime)
    {
        return localDateTime==null?null:LocalDateTime.parse(localDateTime, dateTimeFormatter);
    }

    public static LocalTime getFromStringHours(String localDateTime)
    {
        return localDateTime==null?null: LocalTime.parse(localDateTime, hoursTimeFormatter);
    }

    public static LocalDateTime stringToLocalDateTime(String localTime){
        return localTime==null?null:LocalDateTime.parse(localTime+dateTimeConstant, dateTimeFormatter);
    }
}
