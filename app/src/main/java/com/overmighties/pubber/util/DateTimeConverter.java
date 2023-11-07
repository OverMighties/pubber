package com.overmighties.pubber.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {
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
