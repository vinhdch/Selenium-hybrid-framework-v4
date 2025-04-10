package utilities;

import java.util.Random;

public class Utils{

    public static int generateRandomNumber() {
        Random rand = new Random();

        return rand.nextInt(99999);
    }

//    protected String getCurrentDate() {
//        //DateTime nowUTC = new DateTime(DateTimeZone.UTC);
//        DateTime nowUTC = new DateTime();
//        int day = nowUTC.getDayOfMonth();
//        if (day < 10) {
//            String dayValue = "0" + day;
//            return dayValue;
//        }
//        return String.valueOf(day);
//    }
//
//    protected String getCurrentMonth() {
//        DateTime now = new DateTime();
//        int month = now.getMonthOfYear();
//        if (month < 10) {
//            String monthValue = "0" + month;
//            return monthValue;
//        }
//        return String.valueOf(month);
//    }
//
//    protected String getCurrentYear() {
//        DateTime now = new DateTime();
//        return now.getYear() + "";
//    }
//
//    protected String getToday() {
//        return getCurrentDate() + "/" + getCurrentMonth() + "/" + getCurrentYear();
//    }

//     protected String getCurrentTimeZone() {
//     Date currentDate = new Date();
//
//     SimpleDateFormat dateFormat = new SimpleDateFormat("D Month, Yr");
//
//     String currentDateTime = dateFormat.format(currentDate);
//
//     return currentDateTime;
//     }
}
