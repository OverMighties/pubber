package com.overmighties.pubber.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//name to be changed

public class TimePeriodConverter {
    //Blocking default constructor
    private TimePeriodConverter(){
        throw new AssertionError();
    }
    public static TimePeriod createFromOpeningTimes(final Date openingTimeToday, final Date closingTimeToday, final Date openingTimeYesterday, final Date closingTimeYesterday) throws ParseException {
        //getting current time
        TimeZone Poland=TimeZone.getTimeZone("Europe/Warsaw");
        String time=Calendar.getInstance(Poland).get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance(Poland).get(Calendar.MINUTE);
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

        Date timeNow=parser.parse(time);
        Date hour24=new Date(3600000*24);


        if(hour24.getTime()>=closingTimeYesterday.getTime() && closingTimeYesterday.getTime()>openingTimeYesterday.getTime()){
            if(timeNow.before(openingTimeToday)){
                return new TimePeriod((createFrom(openingTimeToday,timeNow)),false);
            }
            else {
                return new TimePeriod((createFrom(closingTimeToday,timeNow)),true);
            }
        }
        else {
            if(timeNow.before(closingTimeYesterday)){
                return new TimePeriod((createFrom(closingTimeYesterday,timeNow)),true);
            }
            else {
                if(timeNow.before(openingTimeToday)){
                    return new TimePeriod((createFrom(openingTimeToday,timeNow)),false);
                }
                else {
                    return new TimePeriod((createFrom(closingTimeToday,timeNow)),true);
                }
            }
        }
    }
    public static String createFrom(Date laterTime, Date earlierTime){
        Date hour=new Date(3600000);

        long difference = laterTime.getTime() - earlierTime.getTime();

        if(difference <0)
        {
            difference =laterTime.getTime()+hour.getTime()*24-earlierTime.getTime();
        }
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        String time = parser.format(new Date(difference));
        int minutes = Integer.parseInt(time.substring(3, 5));
        Integer hours = Integer.valueOf(time.substring(0, 2));
        if(minutes >=15){
            if(minutes >=45){
                hours++;
                minutes =0;
            }
            else {
                minutes =5;
            }
        }
        else{
            minutes =0;
        }
        time = hours +"."+ minutes;


        return time;
    }
}
