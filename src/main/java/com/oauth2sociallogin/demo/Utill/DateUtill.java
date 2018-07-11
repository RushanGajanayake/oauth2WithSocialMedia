package com.oauth2sociallogin.demo.Utill;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

/**
 * Created by rusha on 7/10/2018.
 */
public class DateUtill {

    private DateUtill(){}

    public static Date getCurrentDate(){
        return new DateTime(DateTimeZone.UTC).toDate();
    }

    public static SimpleDateFormat getyyMMddDateFormatter(){
        return new SimpleDateFormat("yyMMdd");
    }

    public static SimpleDateFormat getyyyyMMddDateFormatter(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static String getyyMMDateFormatter(final Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("MMM-yy");
        return formatter.format(date);
    }

    public static String getStringDateInMMMMddyyyyFormat(final Date date){
        LocalDate localDate = new LocalDate(date);
        return localDate.toString("MMMM dd YYYY");
    }

    public static String getyyyyMMMMDDDateFormatter(final Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yy");
        return formatter.format(date);
    }
}
