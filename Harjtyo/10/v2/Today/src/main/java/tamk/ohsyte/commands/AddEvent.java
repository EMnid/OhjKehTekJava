package tamk.ohsyte.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import tamk.ohsyte.EventFactory;
import tamk.ohsyte.EventManager;
import tamk.ohsyte.datamodel.Event;
import tamk.ohsyte.providers.EventProvider;
import tamk.ohsyte.providers.WritableEventProvider;

@Command(
        name = "addevent",
        description = "Add a new event to a writable event provider"
)
public class AddEvent implements Runnable {
    @Option(
            names = "-d",
            required = true,
            description = "Date in format YYYY-MM-DD for historical events or --MM-dd for annual events"
    )
    private String dateString;

    @Option(
            names = "-c",
            required = true,
            description = "Category in format primary/secondary"
    )
    private String categoryString;

    @Option(
            names = "-p",
            required = true,
            description = "Provider ID to add the event to (e.g. 'standard' for CSV provider)"
    )
    private String providerId;

    @Option(
            names = "-desc",
            required = true,
            description = "Event description"
    )
    private String description;

    @Override
    public void run() {
        try {
            EventManager manager = EventManager.getInstance();
            EventProvider provider = manager.getEventProvider(providerId);

            if (provider == null) {
                System.err.println("Provider '" + providerId + "' not found");
                return;
            }

            if (!(provider instanceof WritableEventProvider)) {
                System.err.println("Provider '" + providerId + "' does not support adding events");
                return;
            }

            Event event = EventFactory.makeEvent(dateString, description, categoryString);
            WritableEventProvider writable = (WritableEventProvider) provider;

            if (writable.addEvent(event)) {
                System.out.println("Event added successfully");
            } else {
                System.err.println("Failed to add event");
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}