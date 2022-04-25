package com.example.medical.helpers;

import java.util.Calendar;
import java.util.Date;

public class Constants {
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL_ID = "mail_id";
    public static final String USER_TYPE = "user_type";
    public static final String PASSWORD = "password";
    public static final String HOUSE_NO = "house_no";
    public static final String STREET = "street";
    public static final String TOWN = "town";
    public static final String STATE = "state";
    public static final String SELECTED_LANG = "en";


    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
