package wk6;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides events stored in a CSV file. Uses the OpenCSV library.
 */
public class CSVEventProvider implements EventProvider {
    private final List<Event> events;
    private final String identifier;

    public CSVEventProvider(String fileName) {
        this.events = new ArrayList<>();
        this.identifier = "CSV"; // You can change this to any identifier you prefer

        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).build();
            String[] line;
            while ((line = reader.readNext()) != null) {
                Event event = new Event(
                        LocalDate.parse(line[0]),
                        line[1],
                        Category.parse(line[2]));
                this.events.add(event);
            }
        } catch (FileNotFoundException fnfe) {
            System.err.println("CSV file '" + fileName + "' not found! " + fnfe.getLocalizedMessage());
        } catch (CsvValidationException cve) {
            System.err.println("Error in CSV file contents: " + cve.getLocalizedMessage());
        } catch (DateTimeParseException dtpe) {
            System.err.println("Error in date format: " + dtpe.getLocalizedMessage());
        } catch (IOException ioe) {
            System.err.println("Error reading CSV file: " + ioe.getLocalizedMessage());
        }
    }

    @Override
    public List<Event> getEvents() {
        return this.events;
    }
    /**
     * Gets all events matching the specified category.
     *
     * @param category the category to match
     * @return list of matching events
     */
    @Override
    public List<Event> getEventsOfCategory(Category category) {
        List<Event> result = new ArrayList<>();
        for (Event event : this.events) {
            if (event.getCategory().equals(category)) {
                result.add(event);
            }
        }
        return result;
    }
    /**
     * Gets the events matching the given month-day combination.
     *
     * @param monthDay month and day to match
     * @return list of matching events
     */
    @Override
    public List<Event> getEventsOfDate(MonthDay monthDay) {
        List<Event> result = new ArrayList<>();
        for (Event event : this.events) {
            final Month eventMonth = event.getDate().getMonth();
            final int eventDay = event.getDate().getDayOfMonth();
            if (monthDay.getMonth() == eventMonth && monthDay.getDayOfMonth() == eventDay) {
                result.add(event);
            }
        }
        return result;
    }
    /**
     * Gets the identifier of this event provider.
     *
     * @return the identifier
     */
    @Override
    public String getIdentifier() {
        return this.identifier;
    }
}