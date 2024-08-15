package com.overmighties.pubber.util;

import static java.lang.Math.ceil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class DateType {
    public static final DateType NONE=new DateType("",false);
    @Setter
    private String time;
    private final boolean type;

    public void convertToPolish()
    {
        String ending ="";
        if(Float.parseFloat(time)==1){ending="a";}
        else if (Float.parseFloat(time)<=4.5 || (Float.parseFloat(time)>=5 && ceil(Float.parseFloat(time))!=Float.parseFloat(time))) {
            ending="y";
        }
        if(time.charAt(time.length() - 1) == '0'){
            time = (time.substring(0,time.length()-2));
        }
        if(type){time="Otwarte jeszcze "+time+" godzin"+ending;}
        else {time="Zamknięte jeszcze "+time+" godzin"+ending;}
    }
}
