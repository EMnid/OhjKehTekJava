package tamk.ohsyte;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.MonthDay;
import java.util.List;
import java.util.Collections;

public class Today {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Please provide only one CSV file name as a command line argument.");
            return;
        }

        String fileName = args[0];

        // Selvitetään kotihakemisto käyttöjärjestelmästä riippuen, jos Windows, käytetään USERPROFILE-muuttujaa
        // Muuten käytetään user.home-muuttujaa
        String homeDir = System.getProperty("user.home") + File.separator + "today" + File.separator + args[0];
        //Jätän tämän aikaisemman yritelmän tänne kommentoituna, koska en pääse testaamaan, toimisiko nykyinen version Linuxilla tai Macilla
        //String os = System.getProperty("os.name").toLowerCase();
        //String todayDir;

        //if (os.contains("win")) {
        //    todayDir = System.getenv("USERPROFILE") + "\\today";
        //} else {
        //    todayDir = homeDir + "/today";
        //}

        // Määritetään tiedoston polku
        Path filePath = Paths.get(homeDir);

        // Tarkistetaan, että tiedosto on olemassa
        if (!Files.exists(filePath)) {
            System.err.println("File '" + fileName + "' was not found from the directory: " + homeDir);
            return;
        }

        // Luodaan CSVEventProvider ja haetaan tapahtumat
        EventProvider provider = new CSVEventProvider(filePath.toString());
        // Pakotetaan päivä 11.2. mukaan
        final MonthDay monthDay = MonthDay.of(2, 11);

        // Haetaan tapahtumat tietylle päivälle, minkä tahansa vuoden ja kategorian mukaan, uusimmat ensin
        List<Event> events = provider.getEventsOfDate(monthDay);
        Collections.sort(events);
        Collections.reverse(events);

        for (Event event : events) {
            System.out.println(event);
        }
    }
}