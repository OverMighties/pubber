package com.overmighties.pubber.util;

import java.util.Calendar;
import java.util.Date;

import lombok.Getter;

@Getter
public enum DayOfWeekConverter {
    NONE(null,null,null),
    MONDAY("MONDAY","Monday",1),
    TUESDAY("TUESDAY","Tuesday",2),
    WEDNESDAY("WEDNESDAY","Wednesday",3),
    THURSDAY("THURSDAY","Thursday",4),
    FRIDAY("FRIDAY","Friday",5),
    SATURDAY("SATURDAY","Saturday",6),
    SUNDAY("SUNDAY","Sunday",7),
    PONIEDZIALEK("PONIEDZIAŁEK","Poniedziałek",1),
    WTOREK("WTOREK","Wtorek",2),
    SRODA("ŚRODA","Środa",3),
    CZWARTEK("CZWARTEK","Czwartek",4),
    PIATEK("PIĄTEK","Piątek",5),
    SOBOTA("SOBOTA","Sobota",6),
    NIEDZIELA("NIEDZIELA","Niedziela",7);


    private final String upperCase;
    private final String normal;
    private final Integer numeric;


    DayOfWeekConverter(String upperCase, String normal, Integer numeric) {
        this.upperCase = upperCase;
        this.normal = normal;
        this.numeric = numeric;
    }

    public static DayOfWeekConverter getByUpperCase(String upperCaseDayOfWeek)
    {
        switch (upperCaseDayOfWeek)
        {
            case "MONDAY":
                return MONDAY;
            case "TUESDAY":
                return TUESDAY;
            case "WEDNESDAY":
                return WEDNESDAY;
            case "THURSDAY":
                return THURSDAY;
            case "FRIDAY":
                return FRIDAY;
            case "SATURDAY":
                return SATURDAY;
            case "SUNDAY":
                return SUNDAY;
            default:
                return NONE;
        }
    }

    public static DayOfWeekConverter getByNumber(Integer numberOfDayOfWeek)
    {
        switch (numberOfDayOfWeek)
        {
            case 1:
                return MONDAY;
            case 2:
                return TUESDAY;
            case 3:
                return WEDNESDAY;
            case 4:
                return THURSDAY;
            case 5:
                return FRIDAY;
            case 6:
                return SATURDAY;
            case 7:
                return SUNDAY;
            default:
                return NONE;

        }
    }
    public static DayOfWeekConverter getByCamelCase(String camelCaseDayOfWeek)
    {
        switch (camelCaseDayOfWeek)
        {
            case "Monday":
                return MONDAY;
            case "Tuesday":
                return TUESDAY;
            case "Wednesday":
                return WEDNESDAY;
            case "Thursday":
                return THURSDAY;
            case "Friday":
                return FRIDAY;
            case "Saturday":
                return SATURDAY;
            case "Sunday":
                return SUNDAY;
            case "Poniedziałek":
                return MONDAY;
            case "Wtorek":
                return TUESDAY;
            case "Środa":
                return WEDNESDAY;
            case "Czwartek":
                return THURSDAY;
            case "Piątek":
                return FRIDAY;
            case "Sobota":
                return SATURDAY;
            case "Niedziela":
                return SUNDAY;
            default:
                return NONE;
        }
    }
    public static DayOfWeekConverter getByCurrentDay()
    {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek=c.get(Calendar.DAY_OF_WEEK);
        switch(dayOfWeek)
        {
            case 1:
            case 7:
                return SUNDAY;
            case 2:
                return MONDAY;
            case 3:
                return TUESDAY;
            case 4:
                return WEDNESDAY;
            case 5:
                return THURSDAY;
            case 6:
                return FRIDAY;
            default:
                return NONE;
        }
    }
    public static DayOfWeekConverter polishDayOfWeekConverter(Integer numberOfDayOfWeek){
        switch (numberOfDayOfWeek)
        {
            case 1:
                return PONIEDZIALEK;
            case 2:
                return WTOREK;
            case 3:
                return SRODA;
            case 4:
                return CZWARTEK;
            case 5:
                return PIATEK;
            case 6:
                return SOBOTA;
            case 7:
                return NIEDZIELA;
            default:
                return NONE;

        }

    }
}
