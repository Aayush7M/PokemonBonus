import java.util.Comparator;

/*
    Name: Aayush Mengane
    Due Date: Sunday, November 12, 2023
    Description: This comparator class sorts cards by name.
*/
public class SortCardsByName implements Comparator <Card> {
    public int compare (Card a, Card b) {
        return a.getName().compareTo(b.getName());
    }
}
