package com.overmighties.pubber.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


//nazwania chyba trzeba zmienic, ale nwm w sumie jak to nazwać

public class DateTimetoCurrentTimeComparator {
    private long difference;
    private Integer minutes;
    private Integer hours;
    private String time;
    public DateType dateTimetoCurrentTimeComparator(Date OpeningTimeToday,Date ClosingTimeToday,Date OpeningTimeYesterday,Date ClosingTimeYesterday) throws ParseException {
        //getting current time
        TimeZone Poland=TimeZone.getTimeZone("Europe/Warsaw");
        time=Calendar.getInstance(Poland).get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance(Poland).get(Calendar.MINUTE);
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

        Date timeNow=parser.parse(time);
        Date hour24=new Date(3600000*24);


        if(hour24.getTime()>=ClosingTimeYesterday.getTime() && ClosingTimeYesterday.getTime()>OpeningTimeYesterday.getTime()){
            if(timeNow.before(OpeningTimeToday)){
                return new DateType((dateComperator(OpeningTimeToday,timeNow)),false);
            }
            else {
                return new DateType((dateComperator(ClosingTimeToday,timeNow)),true);
            }
        }
        else {
            if(timeNow.before(ClosingTimeYesterday)){
                return new DateType((dateComperator(ClosingTimeYesterday,timeNow)),true);
            }
            else {
                if(timeNow.before(OpeningTimeToday)){
                    return new DateType((dateComperator(OpeningTimeToday,timeNow)),false);
                }
                else {
                    return new DateType((dateComperator(ClosingTimeToday,timeNow)),true);
                }
            }
        }
    }
    public String dateComperator(Date LaterTime,Date EarlierTime){
        Date hour=new Date(3600000);

        difference=LaterTime.getTime()-EarlierTime.getTime();

        if(difference<0)
        {
            difference=LaterTime.getTime()+hour.getTime()*24-EarlierTime.getTime();
        }
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        time=parser.format(new Date(difference));
        minutes=Integer.valueOf(time.substring(3,5));
        hours=Integer.valueOf(time.substring(0,2));
        if(minutes>=15){
            if(minutes>=45){
                hours++;
                minutes=0;
            }
            else {
                minutes=5;
            }
        }
        else{
            minutes=0;
        }
        time=String.valueOf(hours)+"."+String.valueOf(minutes);


        return time;
    }
}
