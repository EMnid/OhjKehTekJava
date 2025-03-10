package tamk.ohsyte;

public class DescriptionFilter extends EventFilter {
    private final String keyword;

    public DescriptionFilter(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean accepts(Event event) {
        return event.getDescription().contains(keyword);
    }
}
