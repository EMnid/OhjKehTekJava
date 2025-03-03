package wk1_2_3;

public interface TodayRelatable {
    public enum Relation {
        BEFORE_TODAY, TODAY, AFTER_TODAY
    }

    Relation getTodayRelation();
    long getTodayDifference();
}
