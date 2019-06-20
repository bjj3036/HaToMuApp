package kr.hs.dgsw.hatomuapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final long SECOND = 1000;
    public static final long MINUTE = SECOND * 60;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;


    public static String diffFromCurrent(long before) {
        SimpleDateFormat getYear = new SimpleDateFormat("yyyy");
        Date currentDate = new Date(), beforeDate = new Date(before);
        String currentYear = getYear.format(currentDate), beforeYear = getYear.format(beforeDate);
        if (!currentYear.equals(beforeYear)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 M월 d일");
            return simpleDateFormat.format(beforeDate);
        }
        long current = currentDate.getTime();
        long diff = current - before;
        if (diff >= DAY * 7) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M월 d일");
            return simpleDateFormat.format(beforeDate);
        } else if (diff >= DAY) {
            return (int) (diff / DAY) + "일 전";
        } else if (diff >= HOUR) {
            return (int) (diff / HOUR) + "시간 전";
        } else if (diff >= MINUTE) {
            return (int) (diff / MINUTE) + "분 전";
        } else {
            return (int) (diff / SECOND) + "초 전";
        }
    }

}
