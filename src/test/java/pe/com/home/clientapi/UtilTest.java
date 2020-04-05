package pe.com.home.clientapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pe.com.home.clientapi.model.entity.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

class UtilTest {

    @Test
    public void shouldReturnFormattedDateFromDate() {
        LocalDate localDate = LocalDate.of(1989, 01, 23);

        Assertions.assertEquals("23/01/1989",
                Util.fromDateToString(Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())));
    }

    @Test
    public void shouldReturnDateFromFormattedDateString() {
        LocalDateTime localDateTime = LocalDateTime.of(1989, 1, 23, 0, 0);
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        Assertions.assertTrue(date.compareTo(Util.fromStringToDate("23/01/1989")) == 0);
    }

    @Test
    public void shouldReturnFormattedDateMajorThanBirthDate() {
        LocalDateTime localDateTime = LocalDateTime.of(1989, 1, 23, 0, 0);
        Client client = Client.builder().name("Cesar").lastname("Casasola").age(31)
                .birthDate(Date.from(localDateTime.toInstant(ZoneOffset.of("-5")))).build();

        Date deadDate = Util.getDeadDate(client);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate deadLocalDate = LocalDate.parse(Util.fromDateToString(deadDate), dateTimeFormatter);
        LocalDate birthLocalDate = LocalDate.parse(Util.fromDateToString(client.getBirthDate()), dateTimeFormatter);

        Assertions.assertTrue(deadLocalDate.getYear() - birthLocalDate.getYear() >= 1);
    }
}