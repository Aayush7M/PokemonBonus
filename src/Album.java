import java.util.ArrayList;
import java.util.Collections;

public class Album implements Comparable <Album> {
    // static variables
    private static int collectionNumOfCards;
    private static int collectionCapacity;
    private static int collectionHP;
    // instance variables
    private final int albumNum;
    private final Date date;
    private ArrayList <Card> cards;
    private int maxCapacity;
    private int albumHP;

    // constructors
    public Album (int albumNum, ArrayList <Card> cards, int maxCapacity, Date date) {
        this.albumNum = albumNum;
        this.cards = cards;
        this.maxCapacity = maxCapacity;
        this.date = date;
        for (Card card : cards) {
            this.albumHP += card.getHP();
        }
        collectionHP += albumHP;
        collectionNumOfCards += cards.size();
        collectionCapacity += maxCapacity;
    }

    public Album (int albumNum, Date date) {
        this.albumNum = albumNum;
        this.date = date;
    }

    public static String collectionStatistics () {
        return String.format("Collection Statistics: %n\t%d cards out of %d%n\tAverage HP: %.3f%n",
                collectionNumOfCards,
                collectionCapacity,
                ((double) collectionHP / ((collectionNumOfCards == 0) ? 1 : collectionNumOfCards)));
    }

    // getters
    public Date getDate () {
        return date;
    }

    public ArrayList <Card> getCards () {
        return cards;
    }

    // methods
    public void removeAlbum () {
        collectionNumOfCards -= cards.size();
        collectionCapacity -= maxCapacity;
        collectionHP -= albumHP;
    }

    public String albumStatistics () { // average HP of THIS ALBUM
        return String.format("Album %d Statistics: %n\t%d cards out of %d%n\tAverage HP: %.3f%n",
                albumNum,
                cards.size(),
                maxCapacity,
                ((double) albumHP / ((cards.isEmpty()) ? 1 : cards.size())));
    }

    public String printNameDateAllCards () {
        StringBuilder returnString = new StringBuilder();
        for (int i = 0 ; i<cards.size()-1 ; i++) {
            returnString.append(cards.get(i).nameDateToString()).append("\n\n");
        }
        returnString.append(cards.get(cards.size()-1).nameDateToString());
        return returnString.toString();
    }

    public String printAllInfoAllCards () {
        StringBuilder returnString = new StringBuilder();
        for (int i = 0 ; i<cards.size()-1 ; i++) {
            returnString.append(cards.get(i)).append("\n\n");
        }
        returnString.append(cards.get(cards.size()-1));
        return returnString.toString();
    }

    public void addCard (Card c) {
        collectionNumOfCards++;
        int thisCardHP = c.getHP();
        albumHP += thisCardHP;
        collectionHP += thisCardHP;
        cards.add(c);
    }

    public void removeCard (int index) {
        collectionNumOfCards--;
        int thisCardHP = cards.get(index).getHP();
        collectionHP -= thisCardHP;
        albumHP -= thisCardHP;
        cards.remove(index);
    }

    public void sortCardsByName () {
        Collections.sort(cards);
    }

    public void sortCardsByHP () {
        cards.sort(new SortCardsByHPNameDate());
    }

    public void sortCardsByDate () {
        cards.sort(new SortCardsByDateNameHP());
    }

    public int getCardIndexOfName (String name) {
        return cards.indexOf(new Card(name, -1));
    }

    public int getCardIndexOfHP (int hp) {
        return cards.indexOf(new Card("", hp));
    }

    public Card getCard (int index) {
        return cards.get(index);
    }

    public int getCardsSize () {
        return cards.size();
    }

    public boolean atMaxCapacity () {
        return cards.size() == maxCapacity;
    }

    //comparable
    public int compareTo (Album a) {
        return (this.albumNum - a.albumNum);
    }

    // to string methods
    public String toString () {
        return String.format("Album Number: %d%n" + "Date: %s%n" + "Max Capacity: %d%n" + "Number of Cards: %d%n" + "Total HP: %d%n", albumNum, date, maxCapacity, cards.size(), albumHP);
    }

    public String nameDateToString () {
        return String.format("Album Number: %d%nDate: %s", albumNum, date);
    }

    // equals method
    public boolean equals (Object o) {
        if (!(o instanceof Album a)) {
            return false;
        }
        return this.date.equals(a.date) || this.albumNum == a.albumNum;

    }
}
