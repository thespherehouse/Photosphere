package com.suhel.photosphere.utils.common;

import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Years;

import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatSimple(Date date) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime then = LocalDateTime.fromDateFields(date);

        int difference = Math.abs(Seconds.secondsBetween(then, now).getSeconds());
        if (difference < 10)
            return "Just now";
        if (difference < 60)
            return String.format(Locale.getDefault(), "%d seconds ago", difference);

        difference = Math.abs(Minutes.minutesBetween(then, now).getMinutes());
        if (difference == 1)
            return "A minute ago";
        if (difference < 60)
            return String.format(Locale.getDefault(), "%d minutes ago", difference);

        difference = Math.abs(Hours.hoursBetween(then, now).getHours());
        if (difference == 1)
            return "An hour ago";
        if (difference < 24)
            return String.format(Locale.getDefault(), "%d hours ago", difference);

        difference = Math.abs(Days.daysBetween(then, now).getDays());
        if (difference == 1)
            return "A day ago";
        if (difference < 30)
            return String.format(Locale.getDefault(), "%d days ago", difference);

        difference = Math.abs(Months.monthsBetween(then, now).getMonths());
        if (difference == 1)
            return "A month ago";
        if (difference < 12)
            return String.format(Locale.getDefault(), "%d months ago", difference);

        difference = Math.abs(Years.yearsBetween(then, now).getYears());
        if (difference == 1)
            return "A year ago";
        return String.format(Locale.getDefault(), "%d years ago", difference);
    }

}
