import java.time.LocalDate;

public class EventTest {
    public static void main(String[] args) {
        Event beforeTodayEvent = new Event(
                LocalDate.of(1995, 1, 1),
                "Finland joins the European Union",
                new Category("politics", "eu"));

        Event afterTodayEvent = new Event(
                LocalDate.of(2038, 1, 19),
                "Unix epoch clock (32-bit) rolls over",
                new Category("computing", "unix"));

        Event todayEvent = new Event(
                LocalDate.now(),
                "Whatever it is, it happens today",
                new Category("test", "test"));

        Event[] events = new Event[3];
        events[0] = beforeTodayEvent;
        events[1] = afterTodayEvent;
        events[2] = todayEvent;

        for (Event event : events) {
            long days = event.getTodayDifference();
            switch (event.getTodayRelation()) {
                case TodayRelatable.Relation.BEFORE_TODAY:
                    System.out.print(String.format("%d days ago", days));
                    break;
                case TodayRelatable.Relation.AFTER_TODAY:
                    System.out.print(String.format("In %d days", days));
                    break;
                default:
                    System.out.print("Today");
                    break;
            }
            System.out.println(": " + event.getDescription());
        }
    }
}