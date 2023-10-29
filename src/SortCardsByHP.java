import java.util.Comparator;

public class SortCardsByHP implements Comparator <Card> {
    public int compare (Card a, Card b) {
        return a.getHP() - b.getHP();
    }
}