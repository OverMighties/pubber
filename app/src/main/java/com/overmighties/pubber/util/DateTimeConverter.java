package com.overmighties.pubber.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {
    //Blocking default constructor
    private DateTimeConverter(){
        throw new AssertionError();
    }
    private static final DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("HH:mm dd MM yyyy");
    public static String  getToString(LocalDateTime localDateTime)
    {
       return  localDateTime==null?null:localDateTime.format(dateTimeFormatter);
    }
    public static LocalDateTime  getFromString(String localDateTime)
    {
        return localDateTime==null?null:LocalDateTime.parse(localDateTime, dateTimeFormatter);
    }
}
