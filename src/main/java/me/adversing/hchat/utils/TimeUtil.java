package me.adversing.hchat.utils;

public class TimeUtil {
    public static String millisToRoundedTime(long millis) {
        if(millis == Long.MAX_VALUE) {
            return "Permanent";
        }
        ++millis;
        long seconds = millis / 1000L;
        long minutes = seconds / 60L;
        long hours = minutes / 60L;
        long days = hours / 24L;
        long weeks = days / 7L;
        long months = weeks / 4L;
        long years = months / 12L;
        if (years > 0L) {
            return years + " year" + ((years == 1L) ? "" : "s");
        }
        if (months > 0L) {
            return months + " month" + ((months == 1L) ? "" : "s");
        }
        if (weeks > 0L) {
            return weeks + " week" + ((weeks == 1L) ? "" : "s");
        }
        if (days > 0L) {
            return days + " day" + ((days == 1L) ? "" : "s");
        }
        if (hours > 0L) {
            return hours + " hour" + ((hours == 1L) ? "" : "s");
        }
        if (minutes > 0L) {
            return minutes + " minute" + ((minutes == 1L) ? "" : "s");
        }
        return seconds + " second" + ((seconds == 1L) ? "" : "s");
    }

}
