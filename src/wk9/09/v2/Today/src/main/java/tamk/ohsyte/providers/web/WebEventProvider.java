package tamk.ohsyte.providers.web;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import tamk.ohsyte.EventFactory;
import tamk.ohsyte.datamodel.Category;
import tamk.ohsyte.datamodel.Event;
import tamk.ohsyte.providers.EventProvider;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

public class WebEventProvider implements EventProvider {
    private final String serverUri;
    private final List<Event> events;
    private final String identifier;
    private final Path backupPath;
    private final HttpClient client;
    private final ObjectMapper mapper;

    public WebEventProvider(URI serverUri) {
        this.serverUri = serverUri.toString();
        this.events = new ArrayList<>();
        this.identifier = "web";
        this.client = HttpClient.newHttpClient();

        // Setup JSON mapper with custom deserializer
        this.mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("EventDeserializer");
        module.addDeserializer(Event.class, new EventDeserializer());
        this.mapper.registerModule(module);

        // Setup backup file path
        String homeDirectory = System.getProperty("user.home");
        String configDirectory = ".today";
        this.backupPath = Paths.get(homeDirectory, configDirectory, "webevents.csv");
        createBackupFile();
    }

    private void createBackupFile() {
        try {
            if (!Files.exists(this.backupPath)) {
                Files.createDirectories(this.backupPath.getParent());
                Files.createFile(this.backupPath);
            }
        } catch (IOException e) {
            System.err.println("Unable to create backup file: " + e.getMessage());
        }
    }

    private void saveToBackupFile(List<Event> events) {
        try {
            StringBuilder sb = new StringBuilder();
            for (Event event : events) {
                sb.append(String.format("%s,%s,%s%n",
                        event.getMonthDay(),
                        event.getDescription(),
                        event.getCategory()));
            }
            Files.writeString(this.backupPath, sb.toString());
        } catch (IOException e) {
            System.err.println("Error saving backup: " + e.getMessage());
        }
    }

    private List<Event> fetchEvents(MonthDay date) {
        String dateParam = date.toString().substring(2); // Remove leading "--"
        String serverEventsPath = "api/v1/events";
        String eventsParameters = String.format("?date=%s", dateParam);
        String serverUriString = String.format("%s%s%s",
                this.serverUri, serverEventsPath, eventsParameters);

        try {
            URI requestUri = new URI(serverUriString);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(requestUri)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = this.client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String body = response.body();
                if (body == null || body.isBlank()) {
                    System.err.println("Empty response from server");
                    return getBackupEvents();
                }

                JavaType eventListType = this.mapper.getTypeFactory()
                        .constructCollectionType(List.class, Event.class);
                List<Event> fetchedEvents = this.mapper.readValue(body, eventListType);
                saveToBackupFile(fetchedEvents);
                return fetchedEvents;
            } else {
                System.err.printf("HTTP error %d: %s%n",
                        response.statusCode(), response.body());
                return getBackupEvents();
            }
        } catch (Exception e) {
            System.err.println("Error fetching events: " + e.getMessage());
            return getBackupEvents();
        }
    }

    private List<Event> getBackupEvents() {
        try {
            String backup = Files.readString(this.backupPath);
            if (!backup.isBlank()) {
                List<Event> backupEvents = new ArrayList<>();
                for (String line : backup.split("\n")) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        Event event = EventFactory.makeEvent(
                                parts[0], parts[1], parts[2]);
                        backupEvents.add(event);
                    }
                }
                return backupEvents;
            }
        } catch (IOException e) {
            System.err.println("Error reading backup: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Event> getEvents() {
        return this.events;
    }

    @Override
    public List<Event> getEventsOfCategory(Category category) {
        return this.events.stream()
                .filter(e -> e.getCategory().equals(category))
                .toList();
    }

    @Override
    public List<Event> getEventsOfDate(MonthDay monthDay) {
        return fetchEvents(monthDay);  // Ei tarvitse päivittää events-listaa
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }
}