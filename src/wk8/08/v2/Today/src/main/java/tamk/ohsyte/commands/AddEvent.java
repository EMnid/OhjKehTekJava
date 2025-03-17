package tamk.ohsyte.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import tamk.ohsyte.EventFactory;
import tamk.ohsyte.EventManager;
import tamk.ohsyte.datamodel.Event;
import tamk.ohsyte.providers.CSVEventProvider;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeParseException;

@Command(name = "addevent")
public class AddEvent implements Runnable {
    @Option(names = "--date", required = true, description = "Date in format YYYY-MM-DD or --MM-DD")
    private String dateString;

    @Option(names = "--category", required = true, description = "Category of the event")
    private String categoryString;

    @Option(names = "--description", required = true, description = "Description of the event")
    private String description;

    @Option(names = "--provider", description = "Event provider ID (default: standard)")
    private String providerId = "standard";

    @Override
    public void run() {
        EventManager manager = EventManager.getInstance();

        // Check if provider exists and is CSV type
        if (!manager.getEventProviderIdentifiers().contains(providerId)) {
            System.err.printf("Provider '%s' not found%n", providerId);
            return;
        }

        // Find the provider instance
        CSVEventProvider provider = null;
        for (var p : manager.getEventProviders()) {
            if (p.getIdentifier().equals(providerId) && p instanceof CSVEventProvider) {
                provider = (CSVEventProvider) p;
                break;
            }
        }

        if (provider == null) {
            System.err.printf("Provider '%s' is not a CSV provider%n", providerId);
            return;
        }

        // Create event using factory
        Event event;
        try {
            event = EventFactory.makeEvent(dateString, description, categoryString);
        } catch (DateTimeParseException | IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
            return;
        }

        // Append to CSV file
        try (PrintWriter writer = new PrintWriter(new FileWriter(provider.getFileName(), true))) {
            writer.printf("%s,%s,%s%n",
                    dateString,
                    description.replace(",", "\\,"),
                    categoryString);
            System.out.println("Event added successfully");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
