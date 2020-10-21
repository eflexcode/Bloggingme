package com.eflexsoft.bloggingme.model;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UtilsClass {

    public static String formatDate(String date) {

        String isTime = null;

        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);

        try {
            Date datel = simpleDateFormat.parse(date);
            isTime = prettyTime.format(datel);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isTime;
    }

    public static String getCountry() {
        Locale locale = Locale.getDefault();

        String country = String.valueOf(locale.getCountry());

        return country.toLowerCase();
    }
}
