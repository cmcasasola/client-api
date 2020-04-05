package pe.com.home.clientapi;

import pe.com.home.clientapi.model.entity.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static final String PATTERN_DATE = "dd/MM/yyyy";
    public static final int MAX_AGE = 100;

    private Util() {

    }

    public static String fromDateToString(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE);

        return localDateTime.format(dateTimeFormatter);
    }

    public static Date fromStringToDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE);
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);

        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getDeadDate(Client client) {
        int randomAge = ThreadLocalRandom.current().nextInt(client.getAge(), MAX_AGE + 1);

        LocalDateTime localDateTime = LocalDateTime
                .ofInstant(client.getBirthDate().toInstant(), ZoneId.systemDefault());
        localDateTime = localDateTime.plusYears(randomAge);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE);

        return Date.from(localDateTime.toInstant(ZoneOffset.of("-5")));
    }
}
