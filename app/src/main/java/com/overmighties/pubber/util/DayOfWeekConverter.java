package com.overmighties.pubber.util;

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
    SUNDAY("SUNDAY","Sunday",7);
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
            default:
                return NONE;
        }
    }
}
