import java.util.Comparator;

/*
    Name: Aayush Mengane
    Due Date: Sunday, November 12, 2023
    Description: This comparator class sorts cards by HP, then name, then date.
*/
public class SortCardsByHPNameDate implements Comparator <Card> {
    public int compare (Card a, Card b) {
        int hpResult = new SortCardsByHP().compare(a, b);
        if (hpResult == 0) { // hp is the same
            int nameResult = new SortCardsByName().compare(a, b);
            if (nameResult == 0) { // name is same
                return new SortCardsByDate().compare(a, b); // check date
            } else {
                return nameResult;
            }
        } else {
            return hpResult;
        }
    }
}
