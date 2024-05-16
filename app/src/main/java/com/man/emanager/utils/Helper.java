package com.man.emanager.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    public static String formateDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, YYYY");
        return dateFormat.format(date);
    }

    public static String formateDatebymonth(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, YYYY");
        return dateFormat.format(date);
    }

}
