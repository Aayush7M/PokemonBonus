/*
    Name: Aayush Mengane
    Due Date: Sunday, November 12, 2023
    Description: This Card class represents a card.
    It stores the name, HP, type, date, and attacks of the card.
*/
public class Card implements Comparable <Card> {
    // instance variables
    private final String name;
    private final int HP;
    private String type;
    private Date date;
    private Attack[] attacks;

    // constructors
    public Card (String name, int HP, String type, Date date, Attack[] attacks) {
        this.name = name;
        this.HP = HP;
        this.type = type;
        this.date = date;
        this.attacks = attacks;
    }

    public Card (String name, int HP) {
        this.name = name;
        this.HP = HP;
    }

    // getters
    public String getName () {
        return name;
    }

    public int getHP () {
        return HP;
    }

    public Date getDate () {
        return date;
    }

    public Attack getAttack (int index) {
        return attacks[index];
    }

    public int getAttacksLength () {
        return attacks.length;
    }

    // comparable
    public int compareTo (Card c) {
        int nameResult = new SortCardsByName().compare(this, c);
        if (nameResult == 0) { // name is same
            int dateResult = new SortCardsByDate().compare(this, c);
            if (dateResult == 0) { // date is the same
                return new SortCardsByHP().compare(this, c); // check hp
            } else {
                return dateResult;
            }
        } else {
            return nameResult;
        }
    }

    // to string methods
    public String nameDateToString () {
        return String.format("Name: %s%nDate: %s", name, date);
    }

    public String toString () {
        StringBuilder returnString = new StringBuilder(String.format("Name: %s%nHP: %d%nType: %s%nDate: %s%nAttacks: %n", name, HP, type, date));
        for (int i = 0; i < attacks.length-1; i++) {
            returnString.append("    Attack ").append(i + 1).append(":\n").append(attacks[i].tabbedToString()).append("\n");
        }
        returnString.append("    Attack ").append(attacks.length).append(":\n").append(attacks[attacks.length-1].tabbedToString());
        return returnString.toString();
    }

    // equals method
    public boolean equals (Object o) {
        if (!(o instanceof Card c)) {
            return false;
        }
        return this.name.equalsIgnoreCase(c.name) || this.HP == c.HP;

    }
}
