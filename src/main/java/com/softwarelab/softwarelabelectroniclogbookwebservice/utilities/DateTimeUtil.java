package com.softwarelab.softwarelabelectroniclogbookwebservice.utilities;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class DateTimeUtil {

    private   DateTimeFormatter dateFormat =  DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a",Locale.US);
    private   DateTimeFormatter capricornDateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z",Locale.US);
    public LocalDateTime getNowPlusMins(int mins){
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("UTC+1"));
       return currentTime.plusMinutes(30);
    }
    public LocalDateTime getNow(){
        return LocalDateTime.now(ZoneId.of("UTC+1"));
    }

    public long getUnixTimestamp(){
       return System.currentTimeMillis()/1000L;
        //return new Date().getTime()/1000L;
    }

    public String formatDate(LocalDateTime localDateTime){
       if(localDateTime ==null){
           return null;
       }
       dateFormat =dateFormat.withZone(ZoneId.of("UTC+1"));
        return localDateTime.format(dateFormat).toString();
    }
    public String getCapricornTime(){
        LocalDateTime now = LocalDateTime.now();
        capricornDateFormat = capricornDateFormat.withZone(ZoneId.of("GMT"));
        return now.format(capricornDateFormat).toString();
    }
}
