package com.overmighties.pubber.util;

import static java.lang.Math.ceil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class DateType {
    @Setter
    private String time;
    private final boolean type;

    public void convertToPolish()
    {
        String ending ="";
        if(Float.valueOf(time)==1){ending="a";}
        else if (Float.valueOf(time)<=4.5 || (Float.valueOf(time)>=5 && ceil(Float.valueOf(time))!=Float.valueOf(time))) {
            ending="y";
        }
        if(time.substring(time.length()-1,time.length()).equals("0")){
            time = (time.substring(0,time.length()-2));
        }
        if(type){time="Otwarte jeszcze "+time+" godzin"+ending;}
        else {time="Zamknięte jeszcze "+time+" godzin"+ending;}
    }
}
