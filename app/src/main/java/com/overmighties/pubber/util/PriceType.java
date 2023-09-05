package com.overmighties.pubber.util;

import lombok.Getter;

@Getter
public enum PriceType {
    NONE("None",0),
    SUPER_CHEAP("Super Cheap",1),
    CHEAP("Cheap",2),
    AVERAGE("Average",3),
    EXPENSIVE("Expensive",4),
    SUPER_EXPENSIVE("Super Expensive",5);
    private final String name;
    private final Integer id;


    PriceType(String name, Integer id) {
        this.name = name;
        this.id = id;
    }
    public static PriceType getById(Integer id)
    {
        if(id.equals(0))
            return NONE;
        if(id.equals(1))
            return SUPER_CHEAP;
        if(id.equals(2))
            return CHEAP;
        if(id.equals(3))
            return AVERAGE;
        if(id.equals(4))
            return EXPENSIVE;
        if(id.equals(5))
            return SUPER_EXPENSIVE;
        return NONE;
    }
}
