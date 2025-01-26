import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

public class Birthday {

        public static void main(String[] args) {
                // Instruct the user to set their birthday in the environment variable
                System.out.println("Set the environment variable BIRTHDATE as your birthday in format YYYY-MM-DD");

                // Read the environment variable
                String birthdateStr = System.getenv("BIRTHDATE");

                System.out.println("Environment variable BIRTHDATE value: " + birthdateStr);

                if (birthdateStr != null) {
                        try {
                                // Convert to OffsetDateTime
                                OffsetDateTime birthdate = OffsetDateTime.parse(birthdateStr + "T00:00:00+00:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);

                                // Get the current date with timezone
                                TimeZone tz = TimeZone.getDefault();
                                OffsetDateTime today = OffsetDateTime.now(tz.toZoneId());

                                // Check if today is the user's birthday
                                if (today.toLocalDate().equals(birthdate.toLocalDate())) {
                                        System.out.println("Happy Birthday!");
                                }

                                // Calculate age in days
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
                                System.out.println("Error in parsing the date: " + e.getMessage());
                        }
                } else {
                        System.out.println("Environment variable BIRTHDATE is not set or is set incorrectly.");
                }
        }
}