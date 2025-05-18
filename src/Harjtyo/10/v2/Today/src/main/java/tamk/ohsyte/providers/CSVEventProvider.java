package tamk.ohsyte.providers;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import tamk.ohsyte.*;
import tamk.ohsyte.datamodel.AnnualEvent;
import tamk.ohsyte.datamodel.Category;
import tamk.ohsyte.datamodel.Event;
import tamk.ohsyte.datamodel.SingularEvent;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Month;
import java.time.MonthDay;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;

public class CSVEventProvider implements WritableEventProvider {
    private final List<Event> events;
    private final String identifier;
    private final String fileName;

    public CSVEventProvider(String fileName, String identifier) {
        this.fileName = fileName;
        this.identifier = identifier;
        this.events = new ArrayList<>();

        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).build();
            String[] line;
            while ((line = reader.readNext()) != null) {
                Event event = EventFactory.makeEvent(line[0], line[1], line[2]);
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
    public boolean addEvent(Event event) {
        try (FileWriter fw = new FileWriter(fileName, true);
             CSVWriter writer = new CSVWriter(fw)) {

            String dateStr;
            if (event instanceof AnnualEvent) {
                dateStr = "--" + event.getMonthDay().toString();
            } else if (event instanceof SingularEvent) {
                dateStr = ((SingularEvent) event).getDate().toString();
            } else {
                throw new IllegalArgumentException("Unsupported event type");
            }

            String[] line = new String[]{
                    dateStr,
                    event.getDescription(),
                    event.getCategory().toString()
            };

            writer.writeNext(line);
            this.events.add(event);
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Event> getEvents() {
        return this.events;
    }

    @Override
    public List<Event> getEventsOfCategory(Category category) {
        List<Event> result = new ArrayList<Event>();
        System.out.println("Looking for category: " + category);
        for (Event event : this.events) {
            System.out.println("Comparing with: " + event.getCategory());
            if (event.getCategory().equals(category)) {
                result.add(event);
            }
        }
        return result;
    }

    @Override
    public List<Event> getEventsOfDate(MonthDay monthDay) {
        List<Event> result = new ArrayList<Event>();

        for (Event event : this.events) {
            Month eventMonth;
            int eventDay;
            if (event instanceof SingularEvent) {
                SingularEvent s = (SingularEvent) event;
                eventMonth = s.getDate().getMonth();
                eventDay = s.getDate().getDayOfMonth();
            } else if (event instanceof AnnualEvent) {
                AnnualEvent a = (AnnualEvent) event;
                eventMonth = a.getMonthDay().getMonth();
                eventDay = a.getMonthDay().getDayOfMonth();
            } else {
                throw new UnsupportedOperationException(
                        "Operation not supported for " +
                                event.getClass().getName());
            }
            if (monthDay.getMonth() == eventMonth && monthDay.getDayOfMonth() == eventDay) {
                result.add(event);
            }
        }

        return result;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }
}