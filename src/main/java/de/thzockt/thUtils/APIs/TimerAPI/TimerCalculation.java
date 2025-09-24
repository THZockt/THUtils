package de.thzockt.thUtils.APIs.TimerAPI;

public class TimerCalculation {

    public static long[] times(long time) {
        long millis = 0;
        long seconds = 0;
        long minutes = 0;
        long hours = 0;
        long days = 0;
        long rawDuration = time;

        if (time / 1000 / 60 / 60 >= 1) {
            days = time / 24 / 60 / 60 / 1000;
            time -= time / 24 / 60 / 60 / 1000 * 24 * 60 * 60 * 1000;
        }

        if (time / 1000 / 60 / 60 >= 1) {
            hours = time / 60 / 60 / 1000;
            time -= time / 60 / 60 / 1000 * 60 * 60 * 1000;
        }

        if (time / 1000 / 60 >= 1) {
            minutes = time / 60 / 1000;
            time -= time / 60 / 1000 * 60 * 1000;
        }

        if (time / 1000 >= 1) {
            seconds = time / 1000;
            time -= time / 1000 * 1000;
        }

        if (time >= 1) {
            millis = time;
        }

        // 0 = days; 1 = hours; 2 = minutes; 3 = seconds; 4 = millis
        return new long[] {days, hours, minutes, seconds, millis};
    }

    public static long getDays(long time) {
        return times(time)[0];
    }
    public static long getHours(long time) {
        return times(time)[1];
    }
    public static long getMinutes(long time) {
        return times(time)[2];
    }
    public static long getSeconds(long time) {
        return times(time)[3];
    }
    public static long getMilliseconds(long time) {
        return times(time)[4];
    }

}