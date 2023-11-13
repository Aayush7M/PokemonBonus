import java.util.ArrayList;
import java.util.Collections;

/*
    Name: Aayush Mengane
    Due Date: Sunday, November 12, 2023
    Description: This class represents an album of cards.
    It contains an arraylist of cards, the date the album was created,
    the album number, and the max capacity of the album.
*/
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

    // methods

    /**
     * Removes the album from the collection statistics
     */
    public void removeAlbum () {
        collectionNumOfCards -= cards.size();
        collectionCapacity -= maxCapacity;
        collectionHP -= albumHP;
    }

    /**
     * This method returns the statistics of the album
     * @return the statistics of the album
     */
    public String albumStatistics () { // average HP of THIS ALBUM
        return String.format("Album %d Statistics: %n\t%d cards out of %d%n\tAverage HP: %.3f%n",
                albumNum,
                cards.size(),
                maxCapacity,
                ((double) albumHP / ((cards.isEmpty()) ? 1 : cards.size())));
    }

    /**
     * This method returns the name and date data of all the cards in the album
     * @return the name and date data of all the cards in the album
     */
    public String printNameDateAllCards () {
        StringBuilder returnString = new StringBuilder();
        for (int i = 0 ; i<cards.size()-1 ; i++) {
            returnString.append(cards.get(i).nameDateToString()).append("\n\n");
        }
        returnString.append(cards.get(cards.size()-1).nameDateToString());
        return returnString.toString();
    }

    /**
     * This method returns all the data of all the cards in the album
     * @return all the data of all the cards in the album
     */
    public String printAllInfoAllCards () {
        StringBuilder returnString = new StringBuilder();
        for (int i = 0 ; i<cards.size()-1 ; i++) {
            returnString.append(cards.get(i)).append("\n\n");
        }
        returnString.append(cards.get(cards.size()-1));
        return returnString.toString();
    }

    /**
     * This method adds a card to the album
     * @param c the card to be added
     */
    public void addCard (Card c) {
        collectionNumOfCards++;
        int thisCardHP = c.getHP();
        albumHP += thisCardHP;
        collectionHP += thisCardHP;
        cards.add(c);
    }

    /**
     * This method removes a card from the album
     * @param index the index of the card to be removed
     */
    public void removeCard (int index) {
        collectionNumOfCards--;
        int thisCardHP = cards.get(index).getHP();
        collectionHP -= thisCardHP;
        albumHP -= thisCardHP;
        cards.remove(index);
    }
    // sorts
    public void sortCardsByName () {
        Collections.sort(cards);
    }

    public void sortCardsByHP () {
        cards.sort(new SortCardsByHPNameDate());
    }

    public void sortCardsByDate () {
        cards.sort(new SortCardsByDateNameHP());
    }

    /**
     * This method checks if the album is at max capacity
     * @return true if the album is at max capacity, false otherwise
     */
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
