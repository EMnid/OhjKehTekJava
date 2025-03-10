package tamk.ohsyte;

import java.time.MonthDay;

public class DateFilter extends EventFilter {
    private final MonthDay monthDay;
    private final Integer year;

    public DateFilter(MonthDay monthDay) {
        this.monthDay = monthDay;
        this.year = null;
    }

    public DateFilter(MonthDay monthDay, int year) {
        this.monthDay = monthDay;
        this.year = year;
    }

    @Override
    public boolean accepts(Event event) {
        if (event instanceof SingularEvent) {
            SingularEvent s = (SingularEvent) event;
            if (year != null) {
                return s.getDate().getMonth() == monthDay.getMonth() &&
                        s.getDate().getDayOfMonth() == monthDay.getDayOfMonth() &&
                        s.getDate().getYear() == year;
            } else {
                return s.getDate().getMonth() == monthDay.getMonth() &&
                        s.getDate().getDayOfMonth() == monthDay.getDayOfMonth();
            }
        } else if (event instanceof AnnualEvent) {
            AnnualEvent a = (AnnualEvent) event;
            return a.getMonthDay().getMonth() == monthDay.getMonth() &&
                    a.getMonthDay().getDayOfMonth() == monthDay.getDayOfMonth();
        }
        return false;
    }
}