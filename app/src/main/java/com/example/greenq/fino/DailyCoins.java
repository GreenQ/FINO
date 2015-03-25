package com.example.greenq.fino;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by GreenQ on 25.03.2015.
 */
public class DailyCoins {
    public static int dateVadidation(long date, Locale locale) {

        Date currentDates = new Date(System.currentTimeMillis());
        Date dates = new Date(date);
        String currentDate = String.valueOf(currentDates);
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy", locale);

        try {
            Date dtCurrent = new Date(df.format(currentDates));
            // использовать dtCurrent.getTime - кол-во милисекунд, эквивалентных 1, 2 и 3 дням

            Date dtCompareWith = new Date(df.format(dates));
           // dtCompareWith.setTime(dtCompareWith.getTime() - 432000000L);

            Date date1daysBefore = new Date();
            date1daysBefore.setTime(dtCurrent.getTime()-86400000L);
            Date date2daysBefore = new Date();
            date2daysBefore.setTime(dtCurrent.getTime()-172800000L);
            Date date3daysBefore = new Date();
            date3daysBefore.setTime(dtCurrent.getTime()- 259200000L);
            Date date4daysBefore = new Date();
            date4daysBefore.setTime(dtCurrent.getTime()- 345600000L);
            Date date5daysBefore = new Date();
            date5daysBefore.setTime(dtCurrent.getTime()- 432000000L);
            Date date6daysBefore = new Date();
            date6daysBefore.setTime(dtCurrent.getTime()- 518400000L);
            Date date7daysBefore = new Date();
            date7daysBefore.setTime(dtCurrent.getTime()- 604800000L);


            //if(dtCompareWith.equals(dtCurrent.getTime()))
            if(dtCompareWith.equals(date7daysBefore))
                return 7;

            else if(dtCompareWith.equals(date6daysBefore))
                return 6;

            else if(dtCompareWith.equals(date5daysBefore))
                return 5;

            else if(dtCompareWith.equals(date4daysBefore))
                return 4;

            else if(dtCompareWith.equals(date3daysBefore))
                return 3;

            else if(dtCompareWith.equals(date2daysBefore))
                return 2;

            else if(dtCompareWith.equals(date1daysBefore))
                return 1;

            else if (dtCurrent.after(dtCompareWith))
                return 8;
        }
        catch (Exception e) {
            Log.e("DATE_EQUAL_TO_LESS_THAN_TODAY", "Date Parsing Exception:" + e.getMessage());
        }
        return 0;
    }
}
