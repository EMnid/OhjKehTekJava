package tamk.ohsyte;

import java.time.MonthDay;

public class DateCategoryFilter extends EventFilter {
    private final DateFilter dateFilter;
    private final CategoryFilter categoryFilter;

    public DateCategoryFilter(MonthDay monthDay, Category category) {
        this.dateFilter = new DateFilter(monthDay);
        this.categoryFilter = new CategoryFilter(category);
    }

    @Override
    public boolean accepts(Event event) {
        return dateFilter.accepts(event) && categoryFilter.accepts(event);
    }
}
