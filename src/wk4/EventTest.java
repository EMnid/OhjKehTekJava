package wk4;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class EventTest {
    public static void main(String[] args) {
        Category category = new Category("politics", "eu");

        Event sweden = new Event(
                LocalDate.of(1995, 1, 1),
                "Sweden joins the European Union",
                category);
        Event finland = new Event(
                LocalDate.of(1995, 1, 1),
                "Finland joins the European Union",
                category);
        Event denmark = new Event(
                LocalDate.of(1974, 1, 1),
                "Denmark joins the European Union",
                category);
        Event france = new Event(
                LocalDate.of(1958, 1, 1),
                "France joins the European Union",
                category);

        Event finlandDuplicated = new Event(
                LocalDate.of(1995, 1, 1),
                "Finland joins the European Union",
                new Category("politics", "domestic"));

        List<Event> events = new ArrayList<>();
        events.add(sweden);
        events.add(finland);
        events.add(denmark);
        events.add(france);
        events.add(finlandDuplicated);

        System.out.println("Unsorted events:");
        for (Event event : events) {
            System.out.println(event);
        }

        Collections.sort(events);

        System.out.println("\nEvents sorted by date / description / category:");
        for (Event event : events) {
            System.out.println(event);
        }
    }
}