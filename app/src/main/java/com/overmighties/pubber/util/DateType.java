package com.overmighties.pubber.util;

import lombok.Getter;
import lombok.Setter;


public class DateType {
    @Getter
    private String time;

    private final boolean type;

    public DateType(String time, boolean type) {
        this.time = time;
        this.type = type;
    }


    public boolean getType() {
        return type;
    }
    public void setTime(String time){
        this.time=time;
    }
}
