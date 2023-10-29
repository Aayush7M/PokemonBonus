import java.util.Comparator;

public class SortCardsByDate implements Comparator <Card> {
    public int compare (Card a, Card b) {
        return a.getDate().compareTo(b.getDate());
    }
}
