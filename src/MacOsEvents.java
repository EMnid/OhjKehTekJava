import java.time.LocalDate;
import java.util.Arrays;

public class MacOsEvents {
    public static void main(String[] args) {
        // Using Category class (from the previous lessons), create a new category for Apple macOS
        Category category = new Category("apple", "macos");

        // Initialize the events array (also from previous lesson examples), adding the release dates and descriptions of the last five macOS versions
        Event[] events = {
                new Event(LocalDate.parse("2024-09-16"), "macOS 15 Sequoia released", category),
                new Event(LocalDate.parse("2023-09-26"), "macOS 14 Sonoma released", category),
                new Event(LocalDate.parse("2022-10-24"), "macOS 13 Ventura released", category),
                new Event(LocalDate.parse("2021-10-25"), "macOS 12 Monterey released", category),
                new Event(LocalDate.parse("2020-11-12"), "macOS 11 Big Sur released", category)
        };

        // Print each event in the required format
        for (Event event : events) {
            LocalDate date = event.getDate();
            String description = event.getDescription();
            // Get the macOS version number using the word "macOS" as a delimiter
            String version = description.substring(6, 8).trim();
            // Get the name of the macOS version using the word "released" as a delimiter
            String name = description.substring(9, description.indexOf(" released"));
            // Get the day of the week from the date
            String dayOfWeek = date.getDayOfWeek().toString().toLowerCase();
            // Capitalize the first letter of the day of the week
            dayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1);
            System.out.println(String.format("macOS %s %s was released on a %s", version, name, dayOfWeek));
        }

        // Collect all OS names into a String array
        String[] osNames = new String[events.length];
        for (int i = 0; i < events.length; i++) {
            String description = events[i].getDescription();
            osNames[i] = description.substring(9, description.indexOf(" released"));
        }

        // Alphabetically sort the OS names
        Arrays.sort(osNames);

        // Print the sorted OS names as a single string
        System.out.println("In alphabetical order: " + Arrays.toString(osNames));
    }
}