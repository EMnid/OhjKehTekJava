package wk6;

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Today {
    public static void main(String[] args) {
        // Gets the singleton manager. Later calls to getInstance
        // will return the same reference.
        tamk.ohsyte.EventManager manager = tamk.ohsyte.EventManager.getInstance();

        // Add a couple of event providers for testing purposes
        manager.addEventProvider(new tamk.ohsyte.FirstEventProvider("first"));
        manager.addEventProvider(new tamk.ohsyte.SecondEventProvider("second"));

        // Add a CSV event provider that reads from the given file.
        // Replace with a valid path to the events.csv file on your own computer!
        final String fileName = "C:/Users/eetu-/today/events.csv";
        tamk.ohsyte.CSVEventProvider csvProvider = new tamk.ohsyte.CSVEventProvider(fileName);
        boolean added = manager.addEventProvider(csvProvider);
        System.out.println("CSVEventProvider added: " + added);

        // Try to add the same CSVEventProvider again
        added = manager.addEventProvider(csvProvider);
        System.out.println("CSVEventProvider added again: " + added);

        // Remove the CSVEventProvider
        boolean removed = manager.removeEventProvider(csvProvider.getIdentifier());
        System.out.println("CSVEventProvider removed: " + removed);

        // Verify removal by trying to remove it again
        removed = manager.removeEventProvider(csvProvider.getIdentifier());
        System.out.println("CSVEventProvider removed again: " + removed);

        // Print the final state of the manager
        List<tamk.ohsyte.Event> events = manager.getAllEvents();
        int providerCount = manager.getEventProviderCount();
        int eventCount = events.size();
        System.out.printf("Manager has %d event providers,%n", providerCount);
        System.out.printf("with a total of %d events.%n", eventCount);

        List<String> identifiers = manager.getEventProviderIdentifiers();
        System.out.println("Event providers: " + Arrays.toString(identifiers.toArray()));

        // Print the number of events from the CSV file
        int csvEventCount = csvProvider.getEvents().size();
        System.out.printf("CSV file contains %d events.%n", csvEventCount);
    }
}