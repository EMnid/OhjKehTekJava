package tamk.ohsyte;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import tamk.ohsyte.commands.ListProviders;
import tamk.ohsyte.commands.ListEvents;
import tamk.ohsyte.providers.CSVEventProvider;
import tamk.ohsyte.providers.web.WebEventProvider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Command(name = "today",
        subcommands = { ListProviders.class, ListEvents.class },
        description = "Shows events from history and annual observations")
public class Today {
    public Today() {
        // Get the singleton event manager instance
        EventManager manager = EventManager.getInstance();

        // Setup local CSV event storage
        setupCSVProvider(manager);

        // Setup web event provider for remote events
        setupWebProvider(manager);
    }

    private void setupCSVProvider(EventManager manager) {
        // Construct path to CSV file in user's home directory
        String homeDirectory = System.getProperty("user.home");
        String configDirectory = ".today";
        Path path = Paths.get(homeDirectory, configDirectory, "events.csv");

        // Create the events file if it doesn't exist
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            } catch (IOException e) {
                System.err.println("Unable to create events file");
                System.exit(1);
            }
        }

        // Add CSV event provider with standard identifier
        String csvProviderId = "standard";
        manager.addEventProvider(
                new CSVEventProvider(path.toString(), csvProviderId));
    }

    private void setupWebProvider(EventManager manager) {
        // Setup web event provider with Heroku server URL
        try {
            URI serverUri = new URI("https://todayserver-89bb2a1b2e80.herokuapp.com/");
            manager.addEventProvider(new WebEventProvider(serverUri));
        } catch (URISyntaxException e) {
            System.err.println("Error creating web provider: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Today()).execute(args);
        System.exit(exitCode);
    }
}