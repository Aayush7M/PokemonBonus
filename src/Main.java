import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import java.util.ArrayList;

public class Main implements ActionListener {
    JPanel mainPanel;
    JFrame mainFrame;
    yayJButton[] mainMenu, subMenuOne, subMenuTwo, backButtons;
    String atMenu = "m0";
    NoWrapJTextPane mainDisplayTextPane;
    JScrollPane mainDisplayScrollPane;
    ArrayList <Album> albums = new ArrayList <>();
    int albumIndexChosen = -1, cardIndexChosen = -1;
    Color myGreen = new Color(114, 170, 85);
    Color myBlue = new Color(0, 141, 213);
    Color defaultBackgroundColor = new Color(238, 238, 238);
    ImageIcon sadPikachu = new ImageIcon("sad pikachu.png");
    ImageIcon happyPikachu = new ImageIcon("happy pikachu.png");
    Font cmu_serif_20 = loadFont(20,false);
    Font cmu_serif_18 = loadFont(18,false);
    Font cmu_serif_40_bold = loadFont(40,true);
    Font cmu_serif_14_bold = loadFont(14,true);

    public Main () {
        readFile("1", albums);
        readFile("2", albums);
        readFile("3", albums);
        readFile("4", albums);
        readFile("5", albums);
        mainPanel = new JPanel();
        mainFrame = new JFrame();
        int width = 750;
        int height = 550;
        int buttonRounding = 50;
        int buttonBorder = 1;

        ImageIcon appIcon = new ImageIcon("app icon.png");

        mainFrame.setSize(width, height + 28);
        mainFrame.setTitle("Pokemon Collection");
        mainFrame.setIconImage(appIcon.getImage());
        mainPanel.setSize(width, height);
        mainPanel.setLayout(null);

        //--------------------------------------------SAD PIKACHU RESIZING--------------------------------------------------
        Image image = sadPikachu.getImage();
        Image newImg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
        sadPikachu = new ImageIcon(newImg);
        image = happyPikachu.getImage();
        newImg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
        happyPikachu = new ImageIcon(newImg);
        //-----------------------------------------------MAIN MENU----------------------------------------------------------
        String[] mainMenuText = {"Album Actions", "Card Actions"};
        Color[] colorsInOrder = {myGreen, myBlue};
        mainMenu = new yayJButton[2];
        for (int i = 0; i < mainMenu.length; i++) {
            mainMenu[i] = new yayJButton(buttonRounding, mainMenuText[i]);
            mainMenu[i].setFont(cmu_serif_14_bold);
            mainMenu[i].setBackground(colorsInOrder[i]);
            mainMenu[i].setFocusable(false);
            mainMenu[i].setOpaque(false); // Make the button transparent
            mainMenu[i].setBorder(new RoundedBorder(buttonRounding, buttonBorder)); // Set the border as before
            mainMenu[i].setContentAreaFilled(false); // Prevent the button from filling its area
            mainMenu[i].setForeground(Color.WHITE); // Set the text color
            int finalI = i;
            mainMenu[i].addMouseListener(new MouseAdapter() {
                public void mouseEntered (MouseEvent pABtn) {
                    mainMenu[finalI].setBackground(mainMenu[finalI].getBackground().darker());
                    mainMenu[finalI].setBorder(new RoundedBorder(buttonRounding, buttonBorder + 2)); // Set the border as before

                }

                public void mouseExited (MouseEvent pABtn) {
                    mainMenu[finalI].setBackground(colorsInOrder[finalI]);
                    mainMenu[finalI].setBorder(new RoundedBorder(buttonRounding, buttonBorder)); // Set the border as before
                }
            });
            mainMenu[i].addActionListener(this);
            mainMenu[i].setActionCommand("m0o" + (i + 1));
            mainMenu[i].setVisible(true);
        }

        //-----------------------------------------ALBUM MENU-------------------------------------------------------
        String[] subMenuOneText =
                {"Display a list of all albums",
                        "Display information on a particular album",
                        "Add an album",
                        "Remove an album (2 options)",
                        "Show statistics",
                        "Return back to main menu"};
        subMenuOne = new yayJButton[6];
        for (int i = 0; i < subMenuOne.length; i++) {
            subMenuOne[i] = new yayJButton(buttonRounding,subMenuOneText[i]);
            subMenuOne[i].setFont(cmu_serif_40_bold);
            subMenuOne[i].setBackground(myGreen);
            subMenuOne[i].setFocusable(false);
            subMenuOne[i].setOpaque(false); // Make the button transparent
            subMenuOne[i].setBorder(new RoundedBorder(buttonRounding, buttonBorder)); // Set the border as before
            subMenuOne[i].setContentAreaFilled(false); // Prevent the button from filling its area
            subMenuOne[i].setForeground(Color.WHITE); // Set the text color
            int finalI = i;
            subMenuOne[i].addMouseListener(new MouseAdapter() {
                public void mouseEntered (MouseEvent pABtn) {
                    subMenuOne[finalI].setBackground(subMenuOne[finalI].getBackground().darker());
                }

                public void mouseExited (MouseEvent pABtn) {
                    subMenuOne[finalI].setBackground(myGreen);
                }
            });
            subMenuOne[i].addActionListener(this);
            subMenuOne[i].setActionCommand("m1o" + (i + 1));
            subMenuOne[i].setVisible(true);
        }

        //-----------------------------------------CARD MENU-------------------------------------------------------
        String[] subMenuTwoText =
                {"Display all cards (in the last sorted order)",
                        "Display information on a particular card",
                        "Add a card",
                        "Remove a card (4 options)",
                        "Edit attack",
                        "Sort cards (3 options)",
                        "Return back to main menu"};
        subMenuTwo = new yayJButton[7];
        for (int i = 0; i < subMenuTwo.length; i++) {
            subMenuTwo[i] = new yayJButton(buttonRounding, subMenuTwoText[i]);
            subMenuTwo[i].setFont(cmu_serif_40_bold);
            subMenuTwo[i].setBackground(myBlue);
            subMenuTwo[i].setFocusable(false);
            subMenuTwo[i].setOpaque(false); // Make the button transparent
            subMenuTwo[i].setBorder(new RoundedBorder(buttonRounding, buttonBorder)); // Set the border as before
            subMenuTwo[i].setContentAreaFilled(false); // Prevent the button from filling its area
            subMenuTwo[i].setForeground(Color.WHITE); // Set the text color
            int finalI = i;
            subMenuTwo[i].addMouseListener(new MouseAdapter() {
                public void mouseEntered (MouseEvent pABtn) {
                    subMenuTwo[finalI].setBackground(subMenuTwo[finalI].getBackground().darker());
                }

                public void mouseExited (MouseEvent pABtn) {
                    subMenuTwo[finalI].setBackground(myBlue);
                }
            });
            subMenuTwo[i].addActionListener(this);
            subMenuTwo[i].setActionCommand("m2o" + (i + 1));
            subMenuTwo[i].setVisible(true);
        }
        //------------------------------------ BACK BUTTONS ---------------------------------------------

        backButtons = new yayJButton[2];
        for (int i = 0; i < backButtons.length; i++) {
            backButtons[i] = new yayJButton(buttonRounding,"Go Back");
            backButtons[i].setFont(cmu_serif_40_bold);
            backButtons[i].setBackground(myGreen);
            backButtons[i].setFocusable(false);
            backButtons[i].setOpaque(false); // Make the button transparent
            backButtons[i].setBorder(new RoundedBorder(buttonRounding, buttonBorder)); // Set the border as before
            backButtons[i].setContentAreaFilled(false); // Prevent the button from filling its area
            backButtons[i].setForeground(Color.WHITE); // Set the text color
            int finalI = i;
            backButtons[i].addMouseListener(new MouseAdapter() {
                public void mouseEntered (MouseEvent pABtn) {
                    backButtons[finalI].setBackground(backButtons[finalI].getBackground().darker());
                }

                public void mouseExited (MouseEvent pABtn) {
                    backButtons[finalI].setBackground(myGreen);
                }
            });
            backButtons[i].addActionListener(this);
            backButtons[i].setActionCommand("b" + (i + 1));
            backButtons[i].setVisible(true);
        }
        //------------------------------------ TEXT PANE SETUP ---------------------------------------------
        mainDisplayTextPane = new NoWrapJTextPane();
        mainDisplayTextPane.setFont(cmu_serif_14_bold);
        mainDisplayTextPane.setEditable(false);
        mainDisplayScrollPane = new JScrollPane(mainDisplayTextPane);
        mainDisplayScrollPane.setVisible(false);

        //------------------------------------FRAME RESIZED COMPONENT LISTENER ---------------------------------------------
        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized (ComponentEvent e) {
                int width = mainPanel.getWidth();
                int height = mainPanel.getHeight();
                mainPanel.setSize(width, height);
                switch (atMenu) {
                    case "m0":
                        perfectlySizedButtons(mainMenu, width, height, mainMenuFont(width * height));
                        break;
                    case "m1":
                        perfectlySizedButtons(subMenuOne, width, height, firstMenuFont(width * height));
                        break;
                    case "m2":
                        perfectlySizedButtons(subMenuTwo, width, height, secondMenuFont(width * height));
                        break;
                    case "d1":
                        perfectlySizedDisplay(backButtons[0], width, height, displayFont(width * height));
                        break;
                    case "d2":
                        perfectlySizedDisplay(backButtons[1], width, height, displayFont(width * height));
                        break;
                }

            }
        });

        //-----------------------------------------FINAL SETUP-------------------------------------------------------
        for (yayJButton jButton : mainMenu) {
            mainPanel.add(jButton);
        }
        for (yayJButton jButton : subMenuOne) {
            mainPanel.add(jButton);
        }
        for (yayJButton jButton : subMenuTwo) {
            mainPanel.add(jButton);
        }
        for (yayJButton jButton : backButtons) {
            mainPanel.add(jButton);
        }
        mainPanel.add(mainDisplayScrollPane);
        mainFrame.add(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setMinimumSize(new Dimension(width, height + 28));
        mainFrame.setMaximumSize(new Dimension(1512, 916 + 28));

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public static void main (String[] args) {
        new Main();
    }

    public void actionPerformed (ActionEvent e) {
        String eventName = e.getActionCommand();
        int width = mainPanel.getWidth();
        int height = mainPanel.getHeight();
        int choice;
        switch (eventName) {
            case "m0o1":
                atMenu = "m1";
                System.out.println("album menu");
                for (yayJButton jButton : mainMenu) {
                    jButton.setVisible(false);
                }
                perfectlySizedButtons(subMenuOne, mainPanel.getWidth(), mainPanel.getHeight(), firstMenuFont(mainPanel.getWidth() * mainPanel.getHeight()));
                for (yayJButton jButton : subMenuOne) {
                    jButton.setVisible(true);
                }
                break;
            case "m0o2":
                System.out.println("card menu");
                if (albums.isEmpty()) {
                    noAlbumsImported();
                    return;
                } else if (albums.size() == 1) {
                    atMenu = "m2";
                    oneAlbumImported();
                    for (yayJButton jButton : mainMenu) {
                        jButton.setVisible(false);
                    }
                    perfectlySizedButtons(subMenuTwo, mainPanel.getWidth(), mainPanel.getHeight(), secondMenuFont(mainPanel.getWidth() * mainPanel.getHeight()));
                    for (yayJButton jButton : subMenuTwo) {
                        jButton.setVisible(true);
                    }
                    return;
                } else {
                    chooseAlbum(myBlue, true);
                    if (albumIndexChosen != -1) {
                        atMenu = "m2";
                        for (yayJButton jButton : mainMenu) {
                            jButton.setVisible(false);
                        }
                        perfectlySizedButtons(subMenuTwo, mainPanel.getWidth(), mainPanel.getHeight(), secondMenuFont(mainPanel.getWidth() * mainPanel.getHeight()));
                        for (yayJButton jButton : subMenuTwo) {
                            jButton.setVisible(true);
                        }
                    }
                }
                break;
            case "m1o1", "m1o2", "m1o3", "m1o4", "m1o5":
                choice = Integer.parseInt(eventName.substring(3));
                int numOfAlbumsImported = albums.size();
                if (choice != 3) { // user does not want to add cards
                    if (numOfAlbumsImported == 0) { // if no albums are imported
                        choice = 6;
                    } else if (numOfAlbumsImported == 1) { // if only one album imported
                        if (choice != 1 && choice != 5) { // user doesn't want to print all albums or add cards
                            oneAlbumImported();
                        }
                        switch (choice) {
                            case 2 -> choice = 7;
                            case 4 -> choice = 8;
                        }
                    }
                }
                switch (choice) {
                    case 1: // Display a list of all albums
                        System.out.println("m1o1 picked");
                        showTheTextPane(subMenuOne, 0, width, height);
                        printNameDateAllAlbums(mainDisplayTextPane);
                        break;
                    case 2: // Display info on a particular album
                        System.out.println("m1o2 picked");
                        chooseAlbum(myGreen, false);
                        if (albumIndexChosen != -1) {
                            showTheTextPane(subMenuOne, 0, width, height);
                            printAlbum();
                        }
                        break;
                    case 3: // Add an album
                        System.out.println("m1o3 picked");
                        importAlbum();
                        break;
                    case 4: // Remove an album
                        System.out.println("m1o4 picked");
                        removeAlbum();
                        break;
                    case 5: // Show statistics
                        showTheTextPane(subMenuOne, 0, width, height);
                        System.out.println("m1o5 picked");
                        printStatistics(mainDisplayTextPane);
                        break;
                    case 6:
                        System.out.println("m1 but !o3. 0 albums imported");
                        noAlbumsImported();
                        break;
                    case 7:
                        System.out.println("m1o2. 1 album imported");
                        showTheTextPane(subMenuOne, 0, width, height);
                        printAlbum();
                        break;
                    case 8:
                        System.out.println("m1o4. 1 album imported");
                        albums.get(0).removeAlbum();
                        albums.remove(0);
                }
                break;
            case "m2o1", "m2o2", "m2o3", "m2o4", "m2o5", "m2o6":
                choice = Integer.parseInt(eventName.substring(3));
                Album currentAlbum = albums.get(albumIndexChosen);
                ArrayList <Card> cards = albums.get(albumIndexChosen).getCards();
                int numOfCardsInAlbum = currentAlbum.getCardsSize();
                if (choice != 3) { // user does not want to add cards
                    if (numOfCardsInAlbum == 0) { // if empty album and user doesn't want to add cards
                        choice = 7;
                    } else if (numOfCardsInAlbum == 1) { // if only one card in album
                        if (choice != 1 && choice != 6) { // doesn't want to print all cards or sort.
                            oneCardInAlbum();
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
                    case 1: // Display all cards (in the last sorted order)
                        System.out.println("m2o1 picked");
                        showTheTextPane(subMenuTwo, 1, width, height);
                        printNameDateAllCards(mainDisplayTextPane, cards);
                        break;
                    case 2: // Display info on a particular card
                        System.out.println("m1o2 picked");
                        chooseCard(myBlue, false, cards);
                        if (cardIndexChosen != -1) {
                            showTheTextPane(subMenuTwo, 1, width, height);
                            printCard(cards);
                        }
                        break;
                    case 3: // Add a card
                        System.out.println("m1o3 picked");
                        importCard(currentAlbum);
                        break;
                    case 4: // Remove a card (4 options)
                        System.out.println("m2o4");
                        //removeCard();
                        //removeCard(displayMenu(in, 4), in, currentAlbum);
                    case 5: // Edit attack
                        System.out.println("m2o5");
                        //editAttack(in, getExistingCard(in, currentAlbum));
                    case 6: // Sort cards (3 options)
                        System.out.println("m2o6");
                        //sortCards(displayMenu(in, 6), currentAlbum);
                    case 7:
                        System.out.println("m2 but !o3. 0 albums imported");
                        noCardsInAlbum();
                        break;
                    case 8: // all info of only card
                        System.out.println("m2o2 only one card");
                        //System.out.println(currentAlbum.getCard(0));
                        break;
                    case 9: // remove only card
                        System.out.println("m2o4 only one card");
                        //currentAlbum.removeCard(0);
                        break;
                    case 10:
                        System.out.println("m2o5 only one card");
                        //editAttack(in, currentAlbum.getCard(0));
                        break;
                    case 11:
                        System.out.println("m2o6 only one card");
                        //System.out.println("Since there is only one card, output will the same no matter which method of " +"sorting is chosen. Here is the card: \n"+currentAlbum.getCard(0));
                        break;
                }


                break;
            case "b1":
                atMenu = "m1";
                System.out.println("back to album menu");
                backButtons[0].setVisible(false);
                mainDisplayScrollPane.setVisible(false);
                perfectlySizedButtons(subMenuOne, mainPanel.getWidth(), mainPanel.getHeight(), firstMenuFont(mainPanel.getWidth() * mainPanel.getHeight()));
                for (yayJButton jButton : subMenuOne) {
                    jButton.setVisible(true);
                }
                break;
            case "b2":
                atMenu = "m2";
                System.out.println("back to card menu");
                backButtons[1].setVisible(false);
                mainDisplayScrollPane.setVisible(false);
                perfectlySizedButtons(subMenuTwo, mainPanel.getWidth(), mainPanel.getHeight(), secondMenuFont(mainPanel.getWidth() * mainPanel.getHeight()));
                for (yayJButton jButton : subMenuTwo) {
                    jButton.setVisible(true);
                }
                break;
            case "m1o6":
                atMenu = "m0";
                System.out.println("m1o6 picked");
                System.out.println("back to main menu");
                for (yayJButton jButton : subMenuOne) {
                    jButton.setVisible(false);
                }
                perfectlySizedButtons(mainMenu, mainPanel.getWidth(), mainPanel.getHeight(), mainMenuFont(mainPanel.getWidth() * mainPanel.getHeight()));
                for (yayJButton jButton : mainMenu) {
                    jButton.setVisible(true);
                }
                break;
            case "m2o7":
                atMenu = "m0";
                System.out.println("m2o7 picked");
                System.out.println("back to main menu");
                for (yayJButton jButton : subMenuTwo) {
                    jButton.setVisible(false);
                }
                perfectlySizedButtons(mainMenu, mainPanel.getWidth(), mainPanel.getHeight(), mainMenuFont(mainPanel.getWidth() * mainPanel.getHeight()));
                for (yayJButton jButton : mainMenu) {
                    jButton.setVisible(true);
                }
                albumIndexChosen = -1;
        }
    }

    public void importCard (Album currentAlbum) {
        if (currentAlbum.atMaxCapacity()) {
            JLabel label = new JLabel("<html> Sorry, this album is <br> at maximum capacity. You <br> cannot add more cards. </html>");
            label.setFont(cmu_serif_18);
            JOptionPane.showMessageDialog(null, label, "Album at Max Capacity", JOptionPane.INFORMATION_MESSAGE, happyPikachu);
        } else {


            JDialog importCardDialog = new JDialog(mainFrame, "Add Card", true);
            JPanel importCardPanel = new JPanel();
            importCardDialog.setPreferredSize(new Dimension(500 + 19, 750 + 28 + 20));
            importCardPanel.setPreferredSize(new Dimension(500, 750));
            int width = 500;
            int height = 750;
            importCardPanel.setLayout(null);
            int BUTTONRounding = 50;
            int BUTTONBorder = 1;
            JLabel[] promptLabels = new JLabel[5];
            JLabel[] errorLabels = new JLabel[5];
            JTextField[] inputFields = new JTextField[5];
            String buttonText;
            int buttonHeight;
            String[] prompts = {"Name", "HP", "Type", "Date in MM/DD/YYYY"};
            for (int i = 0; i < 4; i++) {
                promptLabels[i] = new JLabel( prompts[i] + ": ");
                promptLabels[i].setBounds(15, 20 + i * 115, 210, 50);
                promptLabels[i].setFont(cmu_serif_18);
                promptLabels[i].setHorizontalAlignment(JLabel.CENTER);
                promptLabels[i].setBackground(Color.white);
                promptLabels[i].setOpaque(true);
                importCardPanel.add(promptLabels[i]);

                inputFields[i] = new JTextField();
                inputFields[i].setBounds(15 + 210, 20 + i * 115, 275, 50);
                inputFields[i].setHorizontalAlignment(JTextField.CENTER);
                inputFields[i].setFont(cmu_serif_18);
                importCardPanel.add(inputFields[i]);

                errorLabels[i] = new JLabel("bingbong");
                errorLabels[i].setBounds(15, 70 + i * 115, 480, 50);
                errorLabels[i].setForeground(Color.RED);
                errorLabels[i].setHorizontalAlignment(JLabel.CENTER);
                errorLabels[i].setFont(cmu_serif_18);
                errorLabels[i].setBackground(Color.white);
                errorLabels[i].setOpaque(true);
                importCardPanel.add(errorLabels[i]);
            }

            JScrollPane chooseAlbumScrollPane = new JScrollPane(importCardPanel);
            chooseAlbumScrollPane.setBounds(0, 0, 500, 750);
            importCardDialog.add(chooseAlbumScrollPane);
            importCardDialog.setLocationRelativeTo(null);
            importCardDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            importCardDialog.setResizable(false);
            importCardDialog.pack();
            importCardDialog.setVisible(true);


//
//
//            String name = getString(in, "What is the name of the card?", true);
//            int HP = getInt(in, "What is the HP of this card?", 1, Integer.MAX_VALUE);
//            String type = getString(in, "What type is this card?", true);
//            Date thisCardDate = getDate(in, "What is the date you got this card? (in MM/DD/YYYY format)");
//            Attack[] attacks = new Attack[getInt(in, "How many attacks does this card have?", 1, Integer.MAX_VALUE)];
//            for (int j = 0; j < attacks.length; j++) {
//                String attackName = getString(in, "What is the name of attack " + (j+1), true);
//                String attackDescription = getString(in, "Enter attack description", false);
//                String attackDamage = getString(in, "What is the damage of the attack", true);
//                attacks[0] = (new Attack(attackName, attackDescription, attackDamage));
//            }
//            currentAlbum.addCard(new Card(name, HP, type, thisCardDate, attacks));
        }
    }

    public void oneAlbumImported () {
        JLabel label = new JLabel("<html> Only one album imported, <br> so that album has been <br> automatically chosen. </html>");
        label.setFont(cmu_serif_18);
        JOptionPane.showMessageDialog(null, label, "One Album imported", JOptionPane.INFORMATION_MESSAGE, happyPikachu);
        albumIndexChosen = 0;
    }

    public void oneCardInAlbum () {
        JLabel label = new JLabel("<html> Only one card in album, <br> so that card has been <br> automatically chosen </html>");
        label.setFont(cmu_serif_18);
        JOptionPane.showMessageDialog(null, label, "One Card in Album", JOptionPane.INFORMATION_MESSAGE, happyPikachu);
        albumIndexChosen = 0;
    }

    public void noAlbumsImported () {
        JLabel label = new JLabel("<html> There are no albums <br> imported. Please import an <br> album to do this action.</html>");
        label.setFont(cmu_serif_18);
        JOptionPane.showMessageDialog(null, label, "No Albums imported", JOptionPane.INFORMATION_MESSAGE, sadPikachu);
    }

    public void noCardsInAlbum () {
        JLabel label = new JLabel("<html> There are no cards <br> in album. Please add a <br> card to do this action.</html>");
        label.setFont(cmu_serif_18);
        JOptionPane.showMessageDialog(null, label, "No Cards in Album", JOptionPane.INFORMATION_MESSAGE, sadPikachu);
    }

    public void perfectlySizedButtons (yayJButton[] myButtons, int width, int height, int calculatedFont) {
        int divideBy = 3 * (myButtons.length) + 1;
        int buttonWidth = (int) Math.round((width * 3.0) / 4);
        int buttonHeight = (int) Math.round((height * 2.0) / divideBy);
        int xTranslation = (int) Math.round(width / 8.0);
        int yTranslation = (int) Math.round(height / (divideBy + 0.0));
        Font buttonFont = loadFont(calculatedFont,true);
        int yStep = (int) Math.round((height * 3.0) / divideBy);
        for (int i = 0; i < myButtons.length; i++) {
            myButtons[i].setBounds(xTranslation, yTranslation + yStep * i, buttonWidth, buttonHeight);
            myButtons[i].setFont(buttonFont);
        }
    }

    public void perfectlySizedDisplay (yayJButton backButton, int width, int height, int calculatedFont) {
        int xCo = (int) Math.round(width / 8.0);
        int componentWidth = (int) Math.round((width * 3.0) / 4);

        int paneYCo = (int) Math.round((height * 1.0) / 10);
        int paneHeight = (int) Math.round((height * 6.0) / 10);

        int buttonYCo = (int) Math.round((height * 8.0) / 10);
        int buttonHeight = (int) Math.round((height * 1.0) / 10);

        Font buttonFont = loadFont(calculatedFont,true);
        backButton.setBounds(xCo, buttonYCo, componentWidth, buttonHeight);
        backButton.setFont(buttonFont);
        mainDisplayScrollPane.setBounds(xCo, paneYCo, componentWidth, paneHeight);
    }

    public int mainMenuFont (double area) {
        return (int) Math.round(area / 12000.0);
    }

    public int displayFont (double area) {
        return (int) Math.round(area / 20000.0);
    }

    public int firstMenuFont (double area) {
        int font = (int) Math.round(Math.pow(area, 3) / (2.1678e16) + Math.pow(area, 2) / (-1.3351e10) + area / (25176.8) + (14.1098));
        if (font < 21) {
            return 21;
        } else return Math.min(font, 48);
    }

    public int secondMenuFont (double area) {
        int font = (int) Math.round(Math.pow(area, 3) / (2.1678e16) + Math.pow(area, 2) / (-1.3351e10) + area / (25176.8) + (14.1098));
        if (font < 21) {
            return 21;
        } else return Math.min(font, 48);
    }

    public void printStatistics (JTextPane useThisTextPane) {
        useThisTextPane.setText("");
        try {
            for (Album album : albums) {
                appendString(album.albumStatistics() + "\n", useThisTextPane);
            }
            appendString(Album.collectionStatistics() + "\n", useThisTextPane);
        } catch (BadLocationException ignored) {
        }
    }

    public void chooseAlbum (Color buttonColor, boolean allData) {
        JDialog chooseAlbumDialog = new JDialog(mainFrame, "Choose the album you want to know more about", true);
        JPanel chooseAlbumPanel = new JPanel();
        int buttonHeight;
        if (allData) {
            buttonHeight = 150;
        } else {
            buttonHeight = 100;
        }
        chooseAlbumDialog.setPreferredSize(new Dimension(400 + 19, 28 + Math.min(25 + (buttonHeight + 25) * albums.size(), 700)));
        chooseAlbumPanel.setPreferredSize(new Dimension(400, 21 + (buttonHeight + 25) * albums.size()));
        chooseAlbumPanel.setLayout(null);
        int BUTTONRounding = 50;
        int BUTTONBorder = 1;
        JScrollPane[] pickAlbumScrollPanes = new JScrollPane[albums.size()];
        yayJButton[] pickAlbumButtons = new yayJButton[albums.size()];
        String buttonText;

        for (int i = 0; i < pickAlbumButtons.length; i++) {
            if (allData) {
                buttonText = albums.get(i).toString();
            } else {
                buttonText = albums.get(i).nameDateToString();
            }
            // "<html><body style='width: 100px'>" + buttonText.replaceAll("\n", "<br>") + "</body></html>"
            pickAlbumButtons[i] = new yayJButton(BUTTONRounding,"<html>" + buttonText.replaceAll("\n", "<br>") + "</html>") {
                @Override
                public Dimension getPreferredSize () {
                    Dimension size = super.getPreferredSize();
                    size.width = size.width - BUTTONRounding * 2;
                    size.height = size.height - BUTTONRounding * 2;
                    return size;
                }
            };
            pickAlbumButtons[i].setFont(cmu_serif_18);
            pickAlbumButtons[i].setBackground(buttonColor);
            pickAlbumButtons[i].setFocusable(false);
            pickAlbumButtons[i].setOpaque(false); // Make the button transparent
            pickAlbumButtons[i].setBorder(new RoundedBorder(BUTTONRounding, BUTTONBorder)); // Set the border as before
            pickAlbumButtons[i].setContentAreaFilled(false); // Prevent the button from filling its area
            pickAlbumButtons[i].setForeground(Color.WHITE); // Set the text color
            int finalI = i;
            pickAlbumButtons[i].addMouseListener(new MouseAdapter() {
                public void mouseEntered (MouseEvent pABtn) {
                    pickAlbumButtons[finalI].setBackground(pickAlbumButtons[finalI].getBackground().darker());
                }

                public void mouseExited (MouseEvent pABtn) {
                    pickAlbumButtons[finalI].setBackground(buttonColor);
                }
            });
            pickAlbumButtons[i].addActionListener(e -> {
                albumIndexChosen = finalI;
                System.out.println("albumChosen = " + albumIndexChosen);
                chooseAlbumDialog.dispose();
            });
            pickAlbumButtons[i].setVisible(true);
            pickAlbumScrollPanes[i] = new JScrollPane(pickAlbumButtons[i]);
            pickAlbumScrollPanes[i].setBounds(25, 25 + i * (25 + buttonHeight), 350, buttonHeight);
            pickAlbumScrollPanes[i].setOpaque(false);
            pickAlbumScrollPanes[i].getViewport().setOpaque(false);
            pickAlbumScrollPanes[i].setBorder(BorderFactory.createEmptyBorder());
        }

        for (JScrollPane jscrollpane : pickAlbumScrollPanes) {
            chooseAlbumPanel.add(jscrollpane);
        }
        JScrollPane chooseAlbumScrollPane = new JScrollPane(chooseAlbumPanel);
        chooseAlbumScrollPane.setPreferredSize(new Dimension(400, 25 + (buttonHeight + 25) * albums.size()));
        chooseAlbumScrollPane.setOpaque(false);
        chooseAlbumScrollPane.getViewport().setOpaque(false);
        chooseAlbumScrollPane.setBorder(BorderFactory.createEmptyBorder());
        chooseAlbumDialog.add(chooseAlbumScrollPane);
        chooseAlbumDialog.setLocationRelativeTo(null);
        chooseAlbumDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        chooseAlbumDialog.setResizable(false);
        chooseAlbumDialog.pack();
        chooseAlbumDialog.setVisible(true);
    }

    public void chooseCard (Color buttonColor, boolean allData, ArrayList <Card> cards) {
        JDialog chooseCardDialog = new JDialog(mainFrame, "Choose the card you want to know more about", true);
        JPanel chooseCardPanel = new JPanel();
        chooseCardDialog.setPreferredSize(new Dimension(400 + 19, 700 + 28 + 20));
        chooseCardPanel.setPreferredSize(new Dimension(400, 700));
        chooseCardPanel.setLayout(null);
        int BUTTONRounding = 50;
        int BUTTONBorder = 1;
        JScrollPane[] pickCardScrollPanes = new JScrollPane[albums.size()];
        yayJButton[] pickCardButtons = new yayJButton[albums.size()];
        String buttonText;
        int buttonHeight;

        for (int i = 0; i < pickCardButtons.length; i++) {
            if (allData) {
                buttonText = cards.get(i).toString();
                buttonHeight = 100;
            } else {
                buttonText = cards.get(i).nameDateToString();
                buttonHeight = 100;
            }
            // "<html><body style='width: 100px'>" + buttonText.replaceAll("\n", "<br>") + "</body></html>"
            pickCardButtons[i] = new yayJButton(BUTTONRounding,"<html>" + buttonText.replaceAll("\n", "<br>") + "</html>") {
                @Override
                public Dimension getPreferredSize () {
                    Dimension size = super.getPreferredSize();
                    size.width = size.width - BUTTONRounding * 2;
                    size.height = size.height - BUTTONRounding * 2;
                    return size;
                }
            };
            pickCardButtons[i].setFont(cmu_serif_18);
            pickCardButtons[i].setBackground(buttonColor);
            pickCardButtons[i].setFocusable(false);
            pickCardButtons[i].setOpaque(false); // Make the button transparent
            pickCardButtons[i].setBorder(new RoundedBorder(BUTTONRounding, BUTTONBorder)); // Set the border as before
            pickCardButtons[i].setContentAreaFilled(false); // Prevent the button from filling its area
            pickCardButtons[i].setForeground(Color.WHITE); // Set the text color
            int finalI = i;
            pickCardButtons[i].addMouseListener(new MouseAdapter() {
                public void mouseEntered (MouseEvent pABtn) {
                    pickCardButtons[finalI].setBackground(pickCardButtons[finalI].getBackground().darker());
                }

                public void mouseExited (MouseEvent pABtn) {
                    pickCardButtons[finalI].setBackground(buttonColor);
                }
            });
            pickCardButtons[i].addActionListener(e -> {
                cardIndexChosen = finalI;
                System.out.println("albumChosen = " + cardIndexChosen);
                chooseCardDialog.dispose();
            });
            pickCardButtons[i].setVisible(true);
            pickCardButtons[i].getInsets(new Insets(10, 10, 10, 10));
            pickCardScrollPanes[i] = new JScrollPane(pickCardButtons[i]);
            pickCardScrollPanes[i].setBounds(25, 25 + i * (25 + buttonHeight), 350, buttonHeight);
            pickCardScrollPanes[i].setBorder(BorderFactory.createLineBorder(defaultBackgroundColor));
        }

        for (JScrollPane jscrollpane : pickCardScrollPanes) {
            chooseCardPanel.add(jscrollpane);
        }
        JScrollPane chooseAlbumScrollPane = new JScrollPane(chooseCardPanel);
        chooseAlbumScrollPane.setBounds(0, 0, 400, 700);
        chooseCardDialog.add(chooseAlbumScrollPane);
        chooseCardDialog.setLocationRelativeTo(null);
        chooseCardDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        chooseCardDialog.setResizable(false);
        chooseCardDialog.pack();
        chooseCardDialog.setVisible(true);
    }

    public int[] parseDate (String date) {
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

    public String readFile (String fileNameEntered, ArrayList <Album> albums) {
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(fileNameEntered + ".txt"));
            int albumNum;
            if (duplicateAlbumNum(albumNum = Integer.parseInt(inFile.readLine().trim()), albums)) {
                return "This is a duplicate album";
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
            inFile.close();
        } catch (FileNotFoundException e) {
            return "File Not Found";
        } catch (IOException e) {
            return "Reading Error";
        }
        return "Album import successful!";
    }

    public void printNameDateAllAlbums (JTextPane useThisTextPane) {
        useThisTextPane.setText("");
        for (Album album : albums) {
            try {
                appendString(album.nameDateToString() + "\n", useThisTextPane);
            } catch (BadLocationException ignored) {
            }
        }
    }

    public void printNameDateAllCards (JTextPane useThisTextPane, ArrayList <Card> cards) {
        useThisTextPane.setText("");
        for (Card card : cards) {
            try {
                appendString(card.nameDateToString() + "\n", useThisTextPane);
            } catch (BadLocationException ignored) {
            }
        }
    }

    public void printAlbum () {
        mainDisplayTextPane.setText("");
        try {
            appendString(albums.get(albumIndexChosen) + "\n", mainDisplayTextPane);
        } catch (BadLocationException ignored) {
        }
        albumIndexChosen = -1;
    }

    public void printCard (ArrayList <Card> cards) {
        mainDisplayTextPane.setText("");
        try {
            appendString(cards.get(cardIndexChosen) + "\n", mainDisplayTextPane);
        } catch (BadLocationException ignored) {
        }
        cardIndexChosen = -1;
    }


    public void appendString (String str, JTextPane useThisTextPane) throws BadLocationException {
        StyledDocument document = (StyledDocument) useThisTextPane.getDocument();
        document.insertString(document.getLength(), str, null);
    }

    public void showTheTextPane (yayJButton[] buttonsToHide, int backButtonToShow, int width, int height) {
        atMenu = "d" + (backButtonToShow + 1);
        perfectlySizedDisplay(backButtons[backButtonToShow], width, height, displayFont(width * height));
        for (yayJButton jButton : buttonsToHide) {
            jButton.setVisible(false);
        }
        backButtons[backButtonToShow].setVisible(true);
        mainDisplayScrollPane.setVisible(true);
    }

    public void importAlbum () {
        JDialog getFileNameDialog = new JDialog(mainFrame, "Import Album", true);
        JPanel getFileNamePanel = new JPanel();
        int componentWidth = (int) Math.round((600 * 6.0) / 8);
        int xCo = (int) Math.round((600 * 1.0) / 8);
        int splitIntoParts = (int) Math.round((300 * 1.0) / 9);
        getFileNameDialog.setPreferredSize(new Dimension(600, 300 + 28));
        getFileNamePanel.setPreferredSize(new Dimension(600, 300));
        getFileNamePanel.setLayout(null);
        int BUTTONRounding = 20;
        int BUTTONBorder = 1;

        JTextPane textPane = new JTextPane() {
            public boolean getScrollableTracksViewportWidth () {
                return true;
            }
        };
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_CENTER);
        textPane.setParagraphAttributes(attrs, false);
        textPane.setText("Enter the file name to import the new Album from. No file extension.");
        textPane.setBounds(xCo, splitIntoParts, componentWidth, splitIntoParts * 2);
        textPane.setBackground(defaultBackgroundColor);
        textPane.setFont(cmu_serif_20);
        textPane.setEditable(false);

        JTextField fileNameField = new JTextField();
        fileNameField.setBounds(xCo, ((int) Math.round(splitIntoParts * 3.5)), componentWidth, splitIntoParts * 2);
        fileNameField.setHorizontalAlignment(JTextField.CENTER);
        fileNameField.setFont(cmu_serif_18);

        JLabel errorLabel = new JLabel("");
        errorLabel.setBounds(xCo, splitIntoParts * 6, componentWidth, splitIntoParts);
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setFont(cmu_serif_18);

        yayJButton submitFileNameButton = new yayJButton(BUTTONRounding,"Import Album");
        submitFileNameButton.setFont(cmu_serif_18);
        submitFileNameButton.setBackground(myGreen);
        submitFileNameButton.setFocusable(false);
        submitFileNameButton.setOpaque(false); // Make the button transparent
        submitFileNameButton.setBorder(new RoundedBorder(BUTTONRounding, BUTTONBorder)); // Set the border as before
        submitFileNameButton.setContentAreaFilled(false); // Prevent the button from filling its area
        submitFileNameButton.setForeground(Color.WHITE); // Set the text color
        submitFileNameButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered (MouseEvent pABtn) {
                submitFileNameButton.setBackground(submitFileNameButton.getBackground().darker());
            }

            public void mouseExited (MouseEvent pABtn) {
                submitFileNameButton.setBackground(myGreen);
            }
        });
        submitFileNameButton.addActionListener(e -> {
            String returnedString = readFile(fileNameField.getText(), albums);
            if (!returnedString.equals("Album import successful!")) {
                errorLabel.setText(returnedString);
            } else {
                getFileNameDialog.dispose();
            }

        });
        submitFileNameButton.setVisible(true);
        submitFileNameButton.setBounds(xCo, ((int) Math.round(splitIntoParts * 7.5)), componentWidth, splitIntoParts);

        EventQueue.invokeLater(fileNameField::requestFocusInWindow);
        getFileNamePanel.add(textPane);
        getFileNamePanel.add(fileNameField);
        getFileNamePanel.add(errorLabel);
        getFileNamePanel.add(submitFileNameButton);
        getFileNameDialog.add(getFileNamePanel);

        getFileNameDialog.setLocationRelativeTo(null);
        getFileNameDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getFileNameDialog.setResizable(false);
        getFileNameDialog.pack();
        getFileNameDialog.setVisible(true);
    }

    public void removeAlbum () {
        JDialog removeAlbumDialog = new JDialog(mainFrame, "Import Album", true);
        JPanel removeAlbumPanel = new JPanel();
        removeAlbumPanel.setLayout(null);
        int width = 400;
        int height = 400;
        int componentWidth = (int) Math.round((width * 6.0) / 8);
        int xCo = (int) Math.round((width * 1.0) / 8);
        int splitIntoParts = (int) Math.round((height * 1.0) / 7.75);

        removeAlbumDialog.setPreferredSize(new Dimension(700, height + 28));
        removeAlbumPanel.setPreferredSize(new Dimension(700, height));

        JLabel label1 = new JLabel("Choose removal method:");
        label1.setBounds(xCo + 4, (int) Math.round(splitIntoParts * 0.25), componentWidth, (int) Math.round(splitIntoParts * 0.5));
        label1.setFont(cmu_serif_18);
        label1.setBackground(Color.orange);


        JRadioButton radio1 = new JRadioButton("By number");
        radio1.setBounds(xCo, splitIntoParts, componentWidth, (int) Math.round(splitIntoParts * 0.5));
        radio1.setFont(cmu_serif_18);

        JRadioButton radio2 = new JRadioButton("By date");
        radio2.setBounds(xCo, (int) Math.round(splitIntoParts * 1.75), componentWidth, (int) Math.round(splitIntoParts * 0.5));
        radio2.setFont(cmu_serif_18);

        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);

        JLabel label2 = new JLabel();
        label2.setBounds(xCo + 4, (int) Math.round(splitIntoParts * 2.75), componentWidth, splitIntoParts);
        label2.setFont(cmu_serif_18);
        label2.setVisible(false);

        JTextField textField = new JTextField();
        textField.setBounds(xCo, splitIntoParts * 4, componentWidth, splitIntoParts);
        textField.setFont(cmu_serif_18);
        textField.setVisible(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setEditable(false);

        JTextPane errorPane = new JTextPane() {
            public boolean getScrollableTracksViewportWidth () {
                return true;
            }
        };
        StyledDocument doc = errorPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        errorPane.setBackground(new Color(236, 236, 236));
        errorPane.setFont(cmu_serif_18);
        errorPane.setForeground(Color.red);
        errorPane.setBounds(xCo, (int) Math.round(splitIntoParts * 5.25), componentWidth, splitIntoParts);
        errorPane.setEditable(false);

        yayJButton removeAlbumButton = new yayJButton(20,"Remove Albums");
        removeAlbumButton.setFont(cmu_serif_20);
        removeAlbumButton.setBackground(myGreen);
        removeAlbumButton.setFocusable(false);
        removeAlbumButton.setOpaque(false); // Make the button transparent
        removeAlbumButton.setBorder(new RoundedBorder(20, 1)); // Set the border as before
        removeAlbumButton.setContentAreaFilled(false); // Prevent the button from filling its area
        removeAlbumButton.setForeground(Color.WHITE); // Set the text color
        removeAlbumButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered (MouseEvent pABtn) {
                removeAlbumButton.setBackground(removeAlbumButton.getBackground().darker());
            }

            public void mouseExited (MouseEvent pABtn) {
                removeAlbumButton.setBackground(myGreen);
            }
        });
        removeAlbumButton.addActionListener(e -> {
            String input = textField.getText();
            if (radio1.isSelected()) { // number
                String result = removeAlbumNum(input);
                if (!result.equals("success")) {
                    errorPane.setText(result);
                } else {
                    removeAlbumDialog.dispose();
                }
            } else if (radio2.isSelected()) { //date
                String result = removeAlbumDate(input);
                if (!result.equals("success")) {
                    errorPane.setText(result);
                } else {
                    removeAlbumDialog.dispose();
                }
            }

        });
        removeAlbumButton.setVisible(false);
        removeAlbumButton.setBounds(xCo, (int) Math.round(splitIntoParts * 6.5), componentWidth, splitIntoParts);

        radio1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                label2.setText("Enter album number:");
                label2.setVisible(true);
                textField.setEditable(true);
                textField.requestFocusInWindow();
                textField.setVisible(true);
                removeAlbumButton.setVisible(true);
                EventQueue.invokeLater(textField::requestFocusInWindow);
            }
        });
        radio2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                label2.setText("<html>Enter album date in MM/DD/YYYY format:</html>");
                label2.setVisible(true);
                textField.setEditable(true);
                textField.requestFocusInWindow();
                textField.setVisible(true);
                removeAlbumButton.setVisible(true);
                EventQueue.invokeLater(textField::requestFocusInWindow);
            }
        });

        NoWrapJTextPane displayPane = new NoWrapJTextPane();
        displayPane.setFont(cmu_serif_18);
        displayPane.setEditable(false);
        JScrollPane displayScrollPane = new JScrollPane(displayPane);
        displayScrollPane.setBounds(xCo + componentWidth + 25, (int) Math.round(splitIntoParts * 0.25), 700 - 375 - (int) Math.round(splitIntoParts * 0.25), (int) Math.round((height * 7.25) / 7.75));
        printNameDateAllAlbums(displayPane);
        displayScrollPane.setVisible(true);
        displayPane.setVisible(true);


        removeAlbumPanel.add(label1);
        removeAlbumPanel.add(radio1);
        removeAlbumPanel.add(radio2);
        removeAlbumPanel.add(label2);
        removeAlbumPanel.add(textField);
        removeAlbumPanel.add(errorPane);
        removeAlbumPanel.add(displayScrollPane);
        removeAlbumPanel.add(removeAlbumButton);
        removeAlbumDialog.add(removeAlbumPanel);

        removeAlbumDialog.setLocationRelativeTo(null);
        removeAlbumDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        removeAlbumDialog.setResizable(false);
        removeAlbumDialog.pack();
        removeAlbumDialog.setVisible(true);
    }

    public String removeAlbumNum (String input) {
        double n; // stores the input from the user
        int validInt; // int returned to the user
        try {
            n = Double.parseDouble(input.trim());
            if (n % 1.0 == 0) { // if the remainder after dividing by 1 is 0
                if (n < 1) { //less than min
                    return "ERROR! Your number cannot be less than 1.";
                } else if (n > Integer.MAX_VALUE) { // more than max
                    return "ERROR! Your number cannot be greater than " + Integer.MAX_VALUE + ".";
                }
            } else {
                throw new NumberFormatException();
            }
            validInt = (int) n;
        } catch (NumberFormatException e) {
            return "ERROR! You entered an Invalid Type.";
        }
        if (!duplicateAlbumNum(validInt, albums)) { // not an imported album, but is an integer
            return "invalid album number";
        } else { // IS an imported album
            int index = albums.indexOf(new Album(validInt, new Date(new int[]{-1, -1, -1})));
            albums.get(index).removeAlbum();
            albums.remove(index);
            albums.trimToSize();
            return "success";
        }
    }

    public String removeAlbumDate (String input) {
        int[] parsedDate;
        input = input.trim().toLowerCase();
        if (input.isEmpty()) { //input length 0 chars
            return "ERROR! You did not provide a response.";
        }
        int firstSlash = input.indexOf('/');
        int lastSlash = input.lastIndexOf('/');
        if (firstSlash == -1) {
            return "ERROR! Your input had no slashes.";
        } else if (firstSlash == lastSlash) {
            return "ERROR! Your input only had one slash.";
        }
        parsedDate = parseDate(input);
        if (parsedDate.length == 0) {
            return "ERROR! Your input had invalid characters.";
        } else if (!Date.validMonthDayYearTriplet(parsedDate)) {
            return "ERROR! Your date was invalid.";
        }
        Date date = new Date(parsedDate);
        if (!duplicateAlbumDate(date, albums)) {
            return "invalid album date";
        } else {
            for (int i = 0; i < albums.size(); i++) {
                if (albums.get(i).getDate().equals(date)) {
                    albums.get(i).removeAlbum();
                    albums.remove(i);
                    i--;
                }
            }
            albums.trimToSize();
            return "success";
        }
    }

    public boolean duplicateAlbumNum (int albumNum, ArrayList <Album> albums) {
        return albums.contains(new Album(albumNum, new Date(new int[]{-1, -1, -1})));
    }

    public boolean duplicateAlbumDate (Date date, ArrayList <Album> albums) {
        return albums.contains(new Album(-1, date));
    }

    private record RoundedBorder(int radius, int thickness) implements Border {

        public Insets getBorderInsets (Component c) {
            return new Insets(this.radius + thickness, this.radius + thickness, this.radius + thickness, this.radius + thickness);
        }

        public boolean isBorderOpaque () {
            return true;
        }

        public void paintBorder (Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK); // This will set the border color to red
            g2.setStroke(new BasicStroke(this.thickness));
            g2.drawRoundRect(x + thickness / 2, y + thickness / 2, width - thickness, height - thickness, radius, radius);
        }
    }

    public static class NoWrapJTextPane extends JTextPane {
        @Override
        public boolean getScrollableTracksViewportWidth () {
            return getUI().getPreferredSize(this).width <= getParent().getSize().width;
        }

        @Override
        public Dimension getPreferredSize () {
            return getUI().getPreferredSize(this);
        }
    }
    public static class yayJButton extends JButton {
        int buttonRounding;
        public yayJButton (int buttonRounding, String text) {
            super(text);
            this.buttonRounding = buttonRounding;
        }
        // This paintComponent method fills the button with a round rectangle.
        @Override
        protected void paintComponent (Graphics g) {
            // Use antialiasing for smoother edges
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Set the color and fill the round rectangle
            g2.setColor(getBackground());

            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), this.buttonRounding, buttonRounding));
            // Call the super method to draw the text
            super.paintComponent(g);
        }
    }
    public static Font loadFont(float size, boolean bold) {
        Font customFont = new Font("Arial",Font.PLAIN,16);
        try {
            // Load the font file
            if (bold) {
                return customFont = Font.createFont(Font.TRUETYPE_FONT, new File("cmunbx.ttf")).deriveFont(size);
            } else {
                return customFont = Font.createFont(Font.TRUETYPE_FONT, new File("cmunrm.ttf")).deriveFont(size);
            }
            // Register the font
//            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            ge.registerFont(customFont);
        } catch (IOException | FontFormatException ignored) {}
        return customFont;
    }
}