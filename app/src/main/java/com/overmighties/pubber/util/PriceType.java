package com.overmighties.pubber.util;

import static com.overmighties.pubber.data.FilterConstants.NONE_PRICE;

import lombok.Getter;

@Getter
public enum PriceType {
    NONE("None", NONE_PRICE,0),
    SUPER_CHEAP("Super Cheap","$",1),
    CHEAP("Cheap","$$",2),
    AVERAGE("Average","$$$",3),
    EXPENSIVE("Expensive","$$$$",4),
    SUPER_EXPENSIVE("Super Expensive","$$$$$",5);
    private final String name;
    private final String icon;
    private final Integer id;


    PriceType(String name,String icon, Integer id) {
        this.name = name;
        this.icon=icon;
        this.id = id;
    }
    public static PriceType getById(Integer id)
    {
        if(id==null)
            return NONE;
        switch (id)
        {
            case 1:
                return SUPER_CHEAP;
            case 2:
                return CHEAP;
            case 3:
                return AVERAGE;
            case 4:
                return EXPENSIVE;
            case 5:
                return SUPER_EXPENSIVE;
            default:
                return NONE;

        }
    }
    public static PriceType getByIcon(String stringIcon)
    {
        if(stringIcon==null)
            return NONE;
        switch (stringIcon)
        {
            case "$":
                return SUPER_CHEAP;
            case "$$":
                return CHEAP;
            case "$$$":
                return AVERAGE;
            case "$$$$":
                return EXPENSIVE;
            case "$$$$$":
                return SUPER_EXPENSIVE;
            default:
                return NONE;

        }
    }
}
