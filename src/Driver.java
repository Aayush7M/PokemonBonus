import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    public static void main (String[] args) {
        System.out.println("Welcome!");
        ArrayList <Album> albums = new ArrayList <>();
        try {
//            readFile(new BufferedReader(new FileReader("1.txt")), albums);
            readFile(new BufferedReader(new FileReader("2.txt")), albums);
//            readFile(new BufferedReader(new FileReader("3.txt")), albums);
            readFile(new BufferedReader(new FileReader("4.txt")), albums);
            readFile(new BufferedReader(new FileReader("5.txt")), albums);
        } catch (IOException ignored) {
        }
        Scanner in = new Scanner(System.in);
/*        if (getChar(in, "Would you like to add an album?") == 'y') {
            do {
                importAlbum(in, albums);
            } while (getChar(in, "Would you like to add more albums?") == 'y');
        }*/

        int mainMenuChoice;
        while ((mainMenuChoice = displayMenu(in, 0)) != 3) { // loop until exit
            switch (mainMenuChoice) {
                case 1 -> albumMenu(in, albums);
                case 2 -> cardMenu(in, albums);
            }
        }
        System.out.println("Goodbye!");
        in.close();
    }

    public static int displayMenu (Scanner in, int menuNum) {
        if (menuNum == 0) {
            System.out.println("----------  MAIN MENU  -----------");
            System.out.println("1) Accessing your list of albums");
            System.out.println("2) Accessing within a particular album");
            System.out.println("3) Exit");
            System.out.println("----------------------------------");
            return getInt(in, "\nPlease enter your choice", 1, 3);
        } else if (menuNum == 1) {
            System.out.println("\n---------  SUB-MENU #1  ----------");
            System.out.println("1) Display a list of all albums");
            System.out.println("2) Display info on a particular album");
            System.out.println("3) Add an album");
            System.out.println("4) Remove an album");
            System.out.println("5) Show statistics");
            System.out.println("6) Return back to main menu.");
            System.out.println("----------------------------------");
            return getInt(in, "\nPlease enter your choice", 1, 6);
        } else if (menuNum == 2) {
            System.out.println("\n---------  SUB-MENU #2  ----------");
            System.out.println("1) Display all cards (in the last sorted order)");
            System.out.println("2) Display info on a particular card");
            System.out.println("3) Add a card");
            System.out.println("4) Remove a card (4 options)");
            System.out.println("5) Edit attack");
            System.out.println("6) Sort cards (3 options)");
            System.out.println("7) Return back to main menu");
            System.out.println("----------------------------------");
            return getInt(in, "\nPlease enter your choice", 1, 7);
        } else if (menuNum == 3) {
            System.out.println("\n---------  REMOVE ...  ----------");
            System.out.println("1) By Album Number");
            System.out.println("2) By Date");
            return getInt(in, "\nPlease enter your choice", 1, 2);
        } else if (menuNum == 4) {
            System.out.println("\n---------  REMOVE ...  ----------");
            System.out.println("1) By Name");
            System.out.println("2) By HP");
            System.out.println("3) First Card in last sorted order");
            System.out.println("4) Last Card in last sorted order");
            return getInt(in, "\nPlease enter your choice", 1, 4);
        } else if (menuNum == 5) {
            System.out.println("\n---------  CHANGE ATTACK ...  ----------");
            System.out.println("1) Name");
            System.out.println("2) Description");
            System.out.println("3) Damage");
            return getInt(in, "\nPlease enter your choice", 1, 3);
        } else {
            System.out.println("\n---------  SORT BY ...  ----------");
            System.out.println("1) Name");
            System.out.println("2) HP");
            System.out.println("3) Date");
            return getInt(in, "\nPlease enter your choice", 1, 3);
        }
    }

    public static void albumMenu (Scanner in, ArrayList <Album> albums) {
        int choice;
        while ((choice = displayMenu(in, 1)) != 6) {
            int numOfAlbumsImported = albums.size();
            if (choice!=3){ // user does not want to add cards
                if (numOfAlbumsImported == 0) { // if no albums are imported
                    choice = 6;
                } else if (numOfAlbumsImported == 1) { // if only one album imported
                    if (choice != 1 && choice !=5) { // user doesn't want to print all albums or add cards
                        System.out.println("Only one album imported, so that album has been automatically chosen");
                    }
                    switch (choice) {
                        case 2 -> choice = 7;
                        case 4 -> choice = 8;
                    }
                }
            }
            switch (choice) {
                case 1: // Display a list of all albums
                    printNameDateAllAlbums(albums);
                    break;
                case 2: // Display info on a particular album
                    printAlbum(in, albums);
                    break;
                case 3: // Add an album
                    importAlbum(in, albums);
                    break;
                case 4: // Remove an album
                    removeAlbum(in, albums);
                    break;
                case 5: // Show statistics
                    printStatistics(albums);
                    break;
                case 6:
                    System.out.println("There are no albums imported.");
                    break;
                case 7:
                    System.out.println(albums.get(0));
                    break;
                case 8:
                    albums.get(0).removeAlbum();
                    albums.remove(0);
            }
        }
    }

    public static void cardMenu (Scanner in, ArrayList <Album> albums) {
        int choice;
        Album currentAlbum;
        if (albums.isEmpty()) {
            System.out.println("There are no albums imported.");
            return;
        } else if (albums.size()==1) {
            System.out.println("Only one album imported, so that album has been automatically chosen");
            currentAlbum = albums.get(0);
        } else {
            printAllAlbums(albums);
            currentAlbum = albums.get(getAlbumIndexFromNum(in, albums));
        }
        while ((choice = displayMenu(in, 2)) != 7) {
            int numOfCardsInAlbum = currentAlbum.getCardsSize();
            if (choice!=3){ // user does not want to add cards
                if (numOfCardsInAlbum == 0) { // if empty album and user doesn't want to add cards
                    choice = 7;
                } else if (numOfCardsInAlbum == 1) { // if only one card in album
                    if (choice != 1 && choice != 6) { // doesn't want to print all cards or sort.
                        System.out.println("Only one card in album, so that card has been automatically chosen");
                    }
                    switch (choice) {
                        case 2 -> choice = 8; // User wants card all info
                        case 4 -> choice = 9; // Remove only card
                        case 5 -> choice = 10; // Edit only card
                        case 6 -> choice = 11; // Sorting won't do anything
                    }
                }
            }
            switch (choice) {
                case 1 -> // Display all cards (in the last sorted order)
                    currentAlbum.printNameDateAllCards(); // prints the name and date
                case 2 -> // Display info on a particular card
                    System.out.println(getExistingCard(in, currentAlbum));
                case 3 -> // Add a card
                    addCard(in, currentAlbum);
                case 4 -> // Remove a card (4 options)
                    removeCard(displayMenu(in, 4), in, currentAlbum);
                case 5 -> // Edit attack
                    editAttack(in, getExistingCard(in, currentAlbum));
                case 6 -> // Sort cards (3 options)
                    sortCards(displayMenu(in, 6), currentAlbum);
                case 7 ->
                    System.out.println("There are no cards in album");
                case 8 -> // all info of only card
                    System.out.println(currentAlbum.getCard(0));
                case 9 -> // remove only card
                    currentAlbum.removeCard(0);
                case 10 ->
                    editAttack(in, currentAlbum.getCard(0));
                case 11 ->
                    System.out.println("Since there is only one card, output will the same no matter which method of " +
                            "sorting is chosen. Here is the card: \n"+currentAlbum.getCard(0));
            }
        }
    }

    public static Card getExistingCard (Scanner in, Album currentAlbum) {
        currentAlbum.printNameDateAllCards(); // print the name and date
        String message = "Which card would you like to get more information about?";
        return currentAlbum.getCards().get(getInt(in, message, 1, currentAlbum.getCardsSize()) - 1);
    }

    public static void printNameDateAllAlbums (ArrayList <Album> albums) {
        for (Album album : albums) {
            System.out.println(album.nameDateToString());
        }
    }

    public static void printAlbum (Scanner in, ArrayList <Album> albums) {
        printNameDateAllAlbums(albums);
        System.out.println(albums.get(getAlbumIndexFromNum(in, albums)));
    }

    public static int getAlbumIndexFromNum (Scanner in, ArrayList <Album> albums) {
        return albums.indexOf(new Album(getAlbumNum(in, albums), new Date(new int[]{-1, -1, -1})));
    }

    public static int getAlbumNum (Scanner in, ArrayList <Album> albums) {
        int albumNumEntered;
        while (!duplicateAlbumNum(albumNumEntered = getInt(in, "Enter the number of the album you are looking for", 1, Integer.MAX_VALUE), albums)) {
            System.out.println("invalid album number");
        }
        return albumNumEntered;
    }

    public static void printAllAlbums (ArrayList <Album> albums) {
        System.out.println("Here are all the albums: ");
        for (Album album : albums) {
            System.out.println(album);
        }
    }

    public static void removeAlbum (Scanner in, ArrayList <Album> albums) {
        int choice = displayMenu(in, 3);
        printNameDateAllAlbums(albums);
        switch (choice) {
            case 1:
                int index = getAlbumIndexFromNum(in,albums);
                albums.get(index).removeAlbum();
                albums.remove(index);
                break;
            case 2:
                Date date = getAlbumDate(in, albums);
                for (int i = 0; i < albums.size(); i++) {
                    if (albums.get(i).getDate().equals(date)) {
                        albums.get(i).removeAlbum();
                        albums.remove(i);
                        i--;
                    }
                }
                break;
        }
        albums.trimToSize();
    }

    public static Date getAlbumDate (Scanner in, ArrayList <Album> albums) {
        Date date;
        while (!duplicateAlbumDate(date = getDate(in, "Enter the date of the album you are looking for"), albums)) {
            System.out.println("invalid album date");
        }
        return date;
    }

    public static void printStatistics (ArrayList <Album> albums) {
        for (Album album : albums) {
            System.out.println(album.albumStatistics());
        }
        System.out.println(Album.collectionStatistics());
    }

    public static void addCard (Scanner in, Album currentAlbum) {
        if (currentAlbum.atMaxCapacity()) {
            System.out.println("Sorry, this album is at maximum capacity. You cannot add more cards.");
        } else {
            String name = getString(in, "What is the name of the card?", true);
            int HP = getInt(in, "What is the HP of this card?", 1, Integer.MAX_VALUE);
            String type = getString(in, "What type is this card?", true);
            Date thisCardDate = getDate(in, "What is the date you got this card? (in MM/DD/YYYY format)");
            Attack[] attacks = new Attack[getInt(in, "How many attacks does this card have?", 1, Integer.MAX_VALUE)];
            for (int j = 0; j < attacks.length; j++) {
                String attackName = getString(in, "What is the name of attack " + (j+1), true);
                String attackDescription = getString(in, "Enter attack description", false);
                String attackDamage = getString(in, "What is the damage of the attack", true);
                attacks[0] = (new Attack(attackName, attackDescription, attackDamage));
            }
            currentAlbum.addCard(new Card(name, HP, type, thisCardDate, attacks));
            System.out.println("Card added successfully!");
        }
    }

    public static void removeCard (int choice, Scanner in, Album currentAlbum) {
        boolean validCard = false;
        ArrayList <Card> cards = currentAlbum.getCards();
        switch (choice) {
            case 1: // remove all cards with name
                int firstIndexOfName;
                String name;
                do {
                    name = getString(in, "Please give the name of the card you want to remove", true);
                    if ((firstIndexOfName = currentAlbum.getCardIndexOfName(name)) > -1) {
                        validCard = true;
                    } else {
                        System.out.println("invalid card name");
                    }
                } while (!validCard);
                for (int i = firstIndexOfName; i < currentAlbum.getCardsSize(); i++) {
                    if (cards.get(i).getName().equalsIgnoreCase(name)) {
                        currentAlbum.removeCard(i);
                        i--;
                    }
                }
                break;
            case 2: // remove all cards with hp
                int firstIndexOfHP;
                int hp;
                do {
                    hp = getInt(in, "Please give the hp of the card you want to remove", 1, Integer.MAX_VALUE);
                    if ((firstIndexOfHP = currentAlbum.getCardIndexOfHP(hp)) > -1) {
                        validCard = true;
                    } else {
                        System.out.println("invalid card hp");
                    }
                } while (!validCard);
                for (int i = firstIndexOfHP; i < currentAlbum.getCardsSize(); i++) {
                    if (cards.get(i).getHP()==hp){
                        currentAlbum.removeCard(i);
                        i--;
                    }
                }
                break;
            case 3:
                currentAlbum.removeCard(0);
                break;
            case 4:
                currentAlbum.removeCard(currentAlbum.getCardsSize() - 1);
        }
        currentAlbum.getCards().trimToSize();
    }

    public static void editAttack (Scanner in, Card card) {
        Attack[] attacks = card.getAttacks();
        Attack attack;
        if (attacks.length == 1) {
            System.out.println("This card only has one attack. So, it has been automatically chosen.");
            attack = attacks[0];
        } else {
            card.printAttacks();
            attack = attacks[getInt(in, "Which attack would you like to edit?", 1, attacks.length)];
        }
        int choice = displayMenu(in, 5);
        switch (choice) {
            case 1: // name
                attack.edit("name", getString(in, "Enter new name: ", true));
                card.sortAttacks();
                break;
            case 2: // description
                attack.edit("description", getString(in, "Enter new description: ", false));
                break;
            case 3: // description
                attack.edit("damage", getString(in, "Enter new damage: ", true));
        }
    }

    public static void sortCards (int choice, Album currentAlbum) {
        switch (choice) {
            case 1:
                currentAlbum.sortCardsByName();
                break;
            case 2:
                currentAlbum.sortCardsByHP();
                break;
            case 3:
                currentAlbum.sortCardsByDate();
                break;
        }
        currentAlbum.printAllInfoAllCards();
    }

    public static String getString (Scanner in, String message, boolean emptyInputForbidden) {
        String inputString; // stores the input
        boolean validAnswer;
        do {
            try {
                System.out.print(message + ": ");
                inputString = in.nextLine().trim().toLowerCase();

                if (inputString.isEmpty() && emptyInputForbidden) { //input length 0 chars
                    throw new IOException();
                }

                validAnswer = true;
            } catch (IOException e) {
                // input length 0
                System.out.println("ERROR! You did not provide a response.\n");
                inputString = "?";
                validAnswer = false;
            }
        } while (!validAnswer);
        return inputString;
    }

    public static int getInt (Scanner in, String message, int min, int max) {
        if (max < min) {
            min = Integer.MIN_VALUE;
            max = Integer.MAX_VALUE;
        }
        double n = -1; // stores the input from the user
        int validInt = 0; // int returned to the user
        boolean validAnswer;
        do {
            try {
                System.out.print(message + ": ");
                n = Double.parseDouble(in.nextLine().trim());
                if (n % 1.0 == 0) { // if the remainder after dividing by 1 is 0
                    if (n < min) { //less than min
                        n = 1;
                        throw new NumberFormatException();
                    } else if (n > max) { // more than max
                        n = 2;
                        throw new NumberFormatException();
                    }
                } else {
                    n = -1;
                    throw new NumberFormatException();
                }
                validInt = (int) n;
                validAnswer = true;
            } catch (NumberFormatException e) {
                if (n == -1) { //invalid type
                    System.out.print("ERROR! You entered an Invalid Type. ");
                } else if (n == 1) { // less than min
                    System.out.print("ERROR! Your number cannot be less than " + min + ". ");
                } else { // more than max
                    System.out.print("ERROR! Your number cannot be greater than " + max + ". ");
                }
                System.out.println("Please enter an integer between " + min + " and " + max + " (inclusive)!");
                n = -1;
                validAnswer = false;
            }
        } while (!validAnswer);

        return validInt;
    }

    public static char getChar (Scanner in, String message) {
        String fullInput = "?"; // stores the original input from the user
        char charInput = '?'; // stores the user's character response
        boolean validAnswer;
        do {
            try {
                System.out.print(message + ": ");
                fullInput = in.nextLine().toLowerCase();

                if (fullInput.length() > 1) { // the input is too long (more than one character)
                    fullInput = "1";
                    throw new IOException();
                } else if (fullInput.isEmpty()) { //input length 0
                    fullInput = "2";
                    throw new IOException();
                } else if (!(fullInput.equals("y") || fullInput.equals("n"))) {
                    fullInput = "3";
                    throw new IOException();
                }
                charInput = fullInput.charAt(0);

                validAnswer = true;
            } catch (IOException e) {
                switch (fullInput) {
                    case "1" ->  // input too long
                            System.out.print("ERROR! Your response was more than one character. ");
                    case "2" ->  // input length 0
                            System.out.print("ERROR! You did not provide a response. ");
                    case "3" -> // input is not y or n
                            System.out.print("ERROR! Your response was not 'y' nor was it 'n'. ");
                }
                System.out.println("Please enter a valid character!");
                fullInput = "?";
                validAnswer = false;
            }
        } while (!validAnswer);
        return charInput;
    }

    public static Date getDate (Scanner in, String message) {
        String inputString = "?"; // stores the input
        boolean validAnswer;
        int[] parsedDate = {};
        do {
            try {
                System.out.print(message + ": ");
                inputString = in.nextLine().trim().toLowerCase();
                if (inputString.isEmpty()) { //input length 0 chars
                    inputString = "1";
                    throw new IOException();
                }
                int firstSlash = inputString.indexOf('/');
                int lastSlash = inputString.lastIndexOf('/');
                if (firstSlash == -1) {
                    inputString = "2";
                    throw new IOException();
                } else if (firstSlash == lastSlash) {
                    inputString = "3";
                    throw new IOException();
                }
                parsedDate = parseDate(inputString);
                if (parsedDate.length == 0) {
                    inputString = "1";
                    throw new NumberFormatException();
                } else if (!Date.validMonthDayYearTriplet(parsedDate)) {
                    inputString = "2";
                    throw new NumberFormatException();
                }
                validAnswer = true;
            } catch (NumberFormatException e) {
                switch (inputString) {
                    case "1" ->  // invalid number
                            System.out.print("ERROR! Your input had invalid characters. ");
                    case "2" ->  // no slashes
                            System.out.print("ERROR! Your date was invalid. ");
                }
                System.out.println("Please enter a date in format MM/DD/YYYY!");
                inputString = "?";
                validAnswer = false;
            } catch (IOException e) {
                switch (inputString) {
                    case "1" ->  // input length 0
                            System.out.print("ERROR! You did not provide a response. ");
                    case "2" ->  // no slashes
                            System.out.print("ERROR! Your input had no slashes. ");
                    case "3" -> // one slash
                            System.out.print("ERROR! Your input only had one slash. ");
                }
                System.out.println("Please enter a date in format MM/DD/YYYY!");
                inputString = "?";
                validAnswer = false;
            }
        } while (!validAnswer);
        return new Date(parsedDate);
    }

    public static void importAlbum (Scanner in, ArrayList <Album> albums) {
        boolean validFileName = false;
        while (!validFileName) {
            try {
                System.out.print("Please provide the name of the input file (WITHOUT FILE EXTENSION): ");
                String fileName = in.nextLine().trim();
                BufferedReader inFile = new BufferedReader(new FileReader(fileName + ".txt"));
                validFileName = true;
                readFile(inFile, albums);
                inFile.close();
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found\n");
            } catch (IOException e) {
                System.out.println("Reading error");
            }
        }

    }

    public static void readFile (BufferedReader inFile, ArrayList <Album> albums) {
        try {
            int albumNum;
            if (duplicateAlbumNum(albumNum = Integer.parseInt(inFile.readLine().trim()), albums)) {
                System.out.println("This is a duplicate album");
                return;
            }
            Date albumDate = new Date(parseDate(inFile.readLine().trim()));
            int maxCapacity = Integer.parseInt(inFile.readLine().trim());
            int cardsInAlbum = Integer.parseInt(inFile.readLine().trim());
            ArrayList <Card> cards = new ArrayList <>(cardsInAlbum);
            for (int i = 0; i < cardsInAlbum; i++) {
                String name = inFile.readLine().trim();
                int HP = Integer.parseInt(inFile.readLine().trim());
                String type = inFile.readLine().trim();
                Date thisCardDate = new Date(parseDate(inFile.readLine().trim()));
                Attack[] attacks = new Attack[Integer.parseInt(inFile.readLine())];
                for (int j = 0; j < attacks.length; j++) {
                    String attackNameDescription = inFile.readLine().trim();
                    int indexOfHyphen = attackNameDescription.indexOf('-');
                    String attackName, attackDescription;
                    if (indexOfHyphen == -1) { // no hyphen
                        attackName = attackNameDescription;
                        attackDescription = "";
                    } else {
                        attackName = attackNameDescription.substring(0, indexOfHyphen).trim();
                        attackDescription = attackNameDescription.substring(indexOfHyphen + 1).trim();
                    }
                    attacks[j] = (new Attack(attackName, attackDescription, inFile.readLine().trim()));
                }
                cards.add(new Card(name, HP, type, thisCardDate, attacks));
            }
            albums.add(new Album(albumNum, cards, maxCapacity, albumDate));
        } catch (IOException e) {
            System.out.println("Reading Error");
            return;
        }
        System.out.println("Album import successful!");
    }

    public static boolean duplicateAlbumNum (int albumNum, ArrayList <Album> albums) {
        return albums.contains(new Album(albumNum, new Date(new int[]{-1, -1, -1})));
    }

    public static boolean duplicateAlbumDate (Date date, ArrayList <Album> albums) {
        return albums.contains(new Album(-1, date));
    }

    public static int[] parseDate (String date) {
        try {
            int month, day, year;
            int firstSlash = date.indexOf("/");
            int secondSlash = date.lastIndexOf("/");
            month = Integer.parseInt(date.substring(0, firstSlash));
            day = Integer.parseInt(date.substring(firstSlash + 1, secondSlash));
            year = Integer.parseInt(date.substring(secondSlash + 1));
            return new int[]{month, day, year};
        } catch (NumberFormatException e) {
            return new int[]{};
        }
    }
}