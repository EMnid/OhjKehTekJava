package wk5;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Today {
    public static void main(String[] args) {
        // Replace with a valid path to the events.csv file on your own computer!
        final String fileName = "C:\\Users\\eetu-\\OneDrive\\Asiakirjat\\events.csv";
        //final String fileName = "foobar";
        EventProvider provider = new CSVEventProvider(fileName);

        final MonthDay monthDay = MonthDay.of(2, 11);

        // Get events for given day, any year, any category, newest first
        List<Event> events = provider.getEventsOfDate(monthDay);
        Collections.sort(events);
        Collections.reverse(events);

        for (Event event : events) {
            System.out.println(event);
        }
    }
}