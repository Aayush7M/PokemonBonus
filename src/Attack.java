public class Attack implements Comparable <Attack> {
    private String name;
    private String description;
    private String damage;

    // constructor
    public Attack (String name, String description, String damage) {
        this.name = name;
        this.description = description;
        this.damage = damage;
    }

    // to string
    public String toString () {
        return String.format("Name: %s%nDescription: %s%nDamage: %s", name, description, damage);
    }
    public String tabbedToString () {
        return String.format("\t\tName: %s%n\t\tDescription: %s%n\t\tDamage: %s", name, description, damage);
    }

    // method
    public void edit (String attributeToChange, String changeTo) {
        if (attributeToChange.equals("name")) {
            this.name = changeTo;
        } else if (attributeToChange.equals("description")) {
            this.description = changeTo;
        } else { // damage
            this.damage = changeTo;
        }
    }

    // comparable
    public int compareTo (Attack a) {
        return this.name.compareToIgnoreCase(a.name);
    }
}
