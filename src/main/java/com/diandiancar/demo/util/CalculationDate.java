package com.diandiancar.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalculationDate {

    public static long calculationDate(Date beginDate,Date endDate) throws ParseException {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        c1.setTime(beginDate);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(endDate);

        long t1 = c1.getTimeInMillis();
        long t2 = c2.getTimeInMillis();

        long result = (t2-t1)/(24*60*60*1000);
        //加一天自然日

        return ++result;
    }
}
