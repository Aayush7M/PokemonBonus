import java.util.Comparator;

public class SortCardsByName implements Comparator <Card> {
    public int compare (Card a, Card b) {
        return a.getName().compareToIgnoreCase(b.getName());
    }
}
