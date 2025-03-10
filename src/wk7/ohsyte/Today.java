package tamk.ohsyte;

import java.time.MonthDay;
import java.util.List;

public class Today {
    public static void main(String[] args) {
        EventManager manager = EventManager.getInstance();

        String fileName = "C:/Users/eetu-/today/events.csv";
        manager.addEventProvider(new CSVEventProvider(fileName));

        MonthDay today = MonthDay.now();
        Category testCategory = Category.parse("society");

        // Test DateFilter
        DateFilter dateFilter = new DateFilter(today);
        List<Event> dateFilteredEvents = manager.getFilteredEvents(dateFilter);
        System.out.println("Date Filtered Events:");
        for (Event event : dateFilteredEvents) {
            System.out.println(event);
        }

        // Test CategoryFilter
        CategoryFilter categoryFilter = new CategoryFilter(testCategory);
        List<Event> categoryFilteredEvents = manager.getFilteredEvents(categoryFilter);
        System.out.println("Category Filtered Events:");
        for (Event event : categoryFilteredEvents) {
            System.out.println(event);
        }

        // Test DateCategoryFilter
        DateCategoryFilter dateCategoryFilter = new DateCategoryFilter(today, testCategory);
        List<Event> dateCategoryFilteredEvents = manager.getFilteredEvents(dateCategoryFilter);
        System.out.println("Date and Category Filtered Events:");
        for (Event event : dateCategoryFilteredEvents) {
            System.out.println(event);
        }
    }
}