import java.util.Comparator;

public class SortCardsByDateNameHP implements Comparator <Card> {
    public int compare (Card a, Card b) {
        int dateResult = new SortCardsByDate().compare(a, b);
        if (dateResult == 0) { // date is the same
            int nameResult = new SortCardsByName().compare(a, b);
            if (nameResult == 0) { // name is same
                return new SortCardsByHP().compare(a, b); // check date
            } else {
                return nameResult;
            }
        } else {
            return dateResult;
        }
    }
}
