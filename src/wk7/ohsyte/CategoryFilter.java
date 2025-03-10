package tamk.ohsyte;

public class CategoryFilter extends EventFilter {
    private final Category category;

    public CategoryFilter(Category category) {
        this.category = category;
    }

    @Override
    public boolean accepts(Event event) {
        return category != null && category.equals(event.getCategory());
    }
}