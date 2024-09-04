import java.time.*;

public class DateTimeChangeAPI {
    public static void main(String[] args) {
//        LocalDate localDate = LocalDate.now();
//        System.out.println(localDate.getDayOfWeek().toString()); //WEDNESDAY
//        System.out.println(localDate.getDayOfMonth());           //15
//        System.out.println(localDate.getDayOfYear());            //135
//        System.out.println(localDate.isLeapYear());              //false


//        LocalTime localTime = LocalTime.now();     //toString() in format 09:57:59.744
//        LocalTime localTime = LocalTime.of(12, 20);
//        System.out.println(localTime.toString());    //12:20
//        System.out.println(localTime.getHour());     //12
//        System.out.println(localTime.getMinute());   //20
//        System.out.println(localTime.getSecond());   //0
//        System.out.println(localTime.MIDNIGHT);      //00:00
//        System.out.println(localTime.NOON);          //12:00

//        LocalDateTime localDateTime = LocalDateTime.now();
//        System.out.println(localDateTime.toString());      //2013-05-15T10:01:14.911
//        System.out.println(localDateTime.getDayOfMonth()); //15
//        System.out.println(localDateTime.getHour());       //10
//        System.out.println(localDateTime.getNano());       //911000000

        OffsetDateTime offsetDate = OffsetDateTime.now();
        System.out.println(offsetDate);  //2023-02-07T15:10:46.260589600+05:30
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);  //2023-02-07T15:10:46.260589600+05:30[Asia/Calcutta]
    }
}
