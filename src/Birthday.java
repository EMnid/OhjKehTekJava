import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

public class Birthday {

        public static void main(String[] args) {
                System.out.println("Set the environment variable BIRTHDATE as your birthday in format YYYY-MM-DD");

                String birthdateStr = System.getenv("BIRTHDATE");

                System.out.println("Environment variable BIRTHDATE value: " + birthdateStr);

                if (birthdateStr != null) {
                        try {
                                OffsetDateTime birthdate = OffsetDateTime.parse(birthdateStr + "T00:00:00+00:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);

                                TimeZone tz = TimeZone.getDefault();
                                OffsetDateTime today = OffsetDateTime.now(tz.toZoneId());

                                if (today.getMonth() == birthdate.getMonth() && today.getDayOfMonth() == birthdate.getDayOfMonth()) {
                                        System.out.println("Happy Birthday!");
                                }

                                long daysOld = ChronoUnit.DAYS.between(birthdate.toLocalDate(), today.toLocalDate());
                                if (daysOld > 0) {
                                        System.out.println("You are " + daysOld + " days old.");
                                        if (daysOld % 1000 == 0) {
                                                System.out.println("That's a nice round number.");
                                        }
                                } else if (daysOld == 0) {
                                        System.out.println("Today is your very first birthday, welcome to the world!");
                                } else {
                                        System.out.println("Woah! You're from the future!");
                                }
                        } catch (DateTimeParseException e) {
                                System.err.println("Error in parsing the date: " + e.getMessage());
                        }
                } else {
                        System.err.println("Environment variable BIRTHDATE is not set or is set incorrectly.");
                }
        }
}