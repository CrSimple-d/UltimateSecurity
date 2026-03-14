package net.crsimple.usecurity.api.keycard;

public enum LevelMode {
    EQUAL("=",(i, j) -> i == j),
    GREATER_THAN(">",(i, j) -> i > j),
    GREATER_THAN_OR_EQUAL(">=",(i, j) -> i >= j);

    private final String symb;
    public final Predicate predicate;

    LevelMode(String symb, Predicate predicate) {
        this.symb = symb;
        this.predicate = predicate;
    }

    public boolean test(int i, int j) {
        return predicate.test(i,j);
    }

    @Override
    public String toString() {
        return symb;
    }

    public interface Predicate {
        boolean test(int i,int j);
    }
}
