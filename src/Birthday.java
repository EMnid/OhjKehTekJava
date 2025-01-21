import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Birthday {

        public static void main(String[] args) {
                // Hanki nykyinen aikavyöhyke ja aika
                ZoneId zoneId = ZoneId.systemDefault();
                OffsetDateTime now = OffsetDateTime.now(zoneId);
                DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
                String dateNow = now.format(formatter);
                System.out.println(dateNow);
                System.out.println("Aseta ympäristömuuttujaan BIRTHDATE syntymäpäiväsi muodossa YYYY-MM-DD");

                // Lue ympäristömuuttuja
                String birthdateStr = System.getenv("BIRTHDATE");

                System.out.println("Ympäristömuuttujan BIRTHDATE arvo: " + birthdateStr);

                if (birthdateStr != null) {
                        try {
                                // Muunna OffsetDateTime-olioksi lisäämällä oletusaika ja aikavyöhyke
                                DateTimeFormatter birthdateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
                                OffsetDateTime birthdate = OffsetDateTime.parse(birthdateStr + "T00:00:00" + now.getOffset().toString(), birthdateFormatter);
                                System.out.println("Syntymäpäivä: " + birthdate);
                        } catch (DateTimeParseException e) {
                                System.out.println("Virhe päivämäärän jäsentämisessä: " + e.getMessage());
                        }
                } else {
                        System.out.println("Ympäristömuuttujaa BIRTHDATE ei ole asetettu.");
                }
        }
}
