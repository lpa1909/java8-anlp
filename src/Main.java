import java.time.*;
import java.util.function.Function;

public class Main {
    interface StringTransformer {
        String transform(String str);
    }
    public static void main(String[] args) {
//        LocalDate localData = LocalDate.now();
//        System.out.println(localData);

//        LocalTime localDate = LocalTime.of(12, 20);
//        System.out.println(localDate);

//        LocalDateTime localDateTime = LocalDateTime.now();
//        System.out.println(localDateTime);

//        OffsetDateTime offsetDateTime = OffsetDateTime.now();
//        System.out.println(offsetDateTime);

//        ZonedDateTime zondedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul")); // ZoneId.getAvailableZoneIds().forEach(System.out::println); // System.out.println(zondedDateTime);
//        System.out.println(zondedDateTime);
//        Instant instant = Instant.now();
//        System.out.println(instant);
//        Instant instant1 = instant.plus(Duration.ofMillis(5000));
//        System.out.println(instant1);
//        Instant instant2 = instant.minus(Duration.ofMillis(5000));
//        System.out.println(instant2);
//        Instant instant3 = instant.minusSeconds(10);
//        System.out.println(instant3);
//        Period period = Period.ofDays(6);
//        System.out.println(period);
//        period = Period.ofMonths(6);
//        System.out.println(period);
//        period = Period.between(LocalDate.now(), LocalDate.now().plusDays(60));
//        System.out.println(period);


        Function<Integer, Integer> square = x -> x * x;
        System.out.println(square.apply(5));
    }
}