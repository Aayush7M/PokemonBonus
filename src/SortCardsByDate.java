import java.util.Comparator;

/*
    Name: Aayush Mengane
    Due Date: Sunday, November 12, 2023
    Description: This comparator class sorts cards by date.
*/
public class SortCardsByDate implements Comparator <Card> {
    public int compare (Card a, Card b) {
        return a.getDate().compareTo(b.getDate());
    }
}
