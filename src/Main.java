import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//$$$$$$$\  $$\       $$$$$$$$\  $$$$$$\   $$$$$$\  $$$$$$$$\       $$$$$$\ $$\      $$\ $$$$$$$\   $$$$$$\  $$$$$$$\ $$$$$$$$\       $$$$$$$$\ $$\   $$\ $$$$$$$$\        $$$$$$\         $$$$$$$$\ $$$$$$$$\ $$$$$$$$\       $$$$$$$$\ $$$$$$\ $$\       $$$$$$$$\  $$$$$$\                $$$\
//$$  __$$\ $$ |      $$  _____|$$  __$$\ $$  __$$\ $$  _____|      \_$$  _|$$$\    $$$ |$$  __$$\ $$  __$$\ $$  __$$\\__$$  __|      \__$$  __|$$ |  $$ |$$  _____|      $$  __$$\        \__$$  __|\__$$  __|$$  _____|      $$  _____|\_$$  _|$$ |      $$  _____|$$  __$$\                \$$\
//$$ |  $$ |$$ |      $$ |      $$ /  $$ |$$ /  \__|$$ |              $$ |  $$$$\  $$$$ |$$ |  $$ |$$ /  $$ |$$ |  $$ |  $$ |            $$ |   $$ |  $$ |$$ |            \__/  $$ |          $$ |      $$ |   $$ |            $$ |        $$ |  $$ |      $$ |      $$ /  \__|          $$\   \$$\
//$$$$$$$  |$$ |      $$$$$\    $$$$$$$$ |\$$$$$$\  $$$$$\            $$ |  $$\$$\$$ $$ |$$$$$$$  |$$ |  $$ |$$$$$$$  |  $$ |            $$ |   $$$$$$$$ |$$$$$\           $$$$$$  |          $$ |      $$ |   $$$$$\          $$$$$\      $$ |  $$ |      $$$$$\    \$$$$$$\            \__|   $$ |
//$$  ____/ $$ |      $$  __|   $$  __$$ | \____$$\ $$  __|           $$ |  $$ \$$$  $$ |$$  ____/ $$ |  $$ |$$  __$$<   $$ |            $$ |   $$  __$$ |$$  __|         $$  ____/           $$ |      $$ |   $$  __|         $$  __|     $$ |  $$ |      $$  __|    \____$$\                  $$ |
//$$ |      $$ |      $$ |      $$ |  $$ |$$\   $$ |$$ |              $$ |  $$ |\$  /$$ |$$ |      $$ |  $$ |$$ |  $$ |  $$ |            $$ |   $$ |  $$ |$$ |            $$ |                $$ |      $$ |   $$ |            $$ |        $$ |  $$ |      $$ |      $$\   $$ |          $$\   $$  |
//$$ |      $$$$$$$$\ $$$$$$$$\ $$ |  $$ |\$$$$$$  |$$$$$$$$\       $$$$$$\ $$ | \_/ $$ |$$ |       $$$$$$  |$$ |  $$ |  $$ |            $$ |   $$ |  $$ |$$$$$$$$\       $$$$$$$$\       $$\ $$ |      $$ |   $$ |            $$ |      $$$$$$\ $$$$$$$$\ $$$$$$$$\ \$$$$$$  |$$\       \__|$$$  /
//\__|      \________|\________|\__|  \__| \______/ \________|      \______|\__|     \__|\__|       \______/ \__|  \__|  \__|            \__|   \__|  \__|\________|      \________|      \__|\__|      \__|   \__|            \__|      \______|\________|\________| \______/ \__|          \___/

/*
    Name: Aayush Mengane
    Due Date: Sunday, November 12, 2023
    Description:
    This program creates a GUI that allows the user to take actions from the two main menus: Album Menu and
    Card Menu.
    Album menu has 6 choices: "Display a list of all albums", "Display information on a particular album",
    "Add an album", "Remove an album (2 options)", "Show statistics", "Return to Main menu".
    Card menu has 7 choices: "Display all cards (in the last sorted order)", "Display information on a particular card",
    "Add a card", "Remove a card (4 options)", "Edit attack", "Sort cards (3 options)", "Return to Main Menu".
*/

public class Main implements ActionListener {
    // global variables
    JPanel mainPanel;
    JFrame mainFrame;
    yayJButton[] mainMenu, albumMenu, cardMenu, backButtons; // button arrays
    String atMenu = "m0";
    NoWrapJTextPane mainDisplayTextPane;
    JScrollPane mainDisplayScrollPane;
    ArrayList <Album> albums = new ArrayList <>(); // ArrayList that stores all the albums imported
    int albumIndexChosen = -1, cardIndexChosen = -1, attackIndexChosen = -1, sortIndexChosen = -1;
    Color myGreen = new Color(114, 170, 85);
    Color myBlue = new Color(0, 141, 213);
    Color defaultBackgroundColor = new Color(238, 238, 238);
    ImageIcon sadPikachu = new ImageIcon("sad pikachu.png");
    ImageIcon happyPikachu = new ImageIcon("happy pikachu.png");
    ImageIcon fullAppIcon = new ImageIcon("app icon.png");

    // Imported the Fonts used
    Font cmu_serif_20 = new Font("CMU Serif", Font.PLAIN, 20);
    Font cmu_serif_18 = new Font("CMU Serif", Font.PLAIN, 18);
    Font cmu_serif_40_bold = new Font("CMU Serif", Font.BOLD, 40);
    Font cmu_serif_14_bold = new Font("CMU Serif", Font.BOLD, 14);
    int buttonRounding = 50;
    int lessButtonRounding = 35;

    /**
     * This is the constructor for Main. It sets up the GUI and adds all the components to the mainPanel.
     */
    public Main () {
        readFile("1");
        readFile("2");
        readFile("3");
//        readFile("4");
        readFile("5");
        mainPanel = new JPanel();
        mainFrame = new JFrame();
        mainFrame = new JFrame();
        int width = 750;
        int height = 550;
        mainFrame.setSize(width, height + 28);
        mainFrame.setTitle("Pokemon Collection");
        mainFrame.setIconImage(fullAppIcon.getImage());
        mainPanel.setSize(width, height);
        mainPanel.setLayout(null);

        //--------------------------------------------SAD PIKACHU RESIZING------------------------------------------------
        Image image = sadPikachu.getImage();
        Image newImg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
        sadPikachu = new ImageIcon(newImg);
        image = happyPikachu.getImage();
        newImg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
        happyPikachu = new ImageIcon(newImg);
        //-----------------------------------------------MAIN MENU--------------------------------------------------------
        String[] mainMenuText = {"Album Actions", "Card Actions"};
        Color[] colorsInOrder = {myGreen, myBlue};
        mainMenu = new yayJButton[2];
        for (int i = 0; i < mainMenu.length; i++) {
            mainMenu[i] = new yayJButton(buttonRounding, mainMenuText[i]);
            setUpThisButton(mainMenu[i], buttonRounding, colorsInOrder[i], cmu_serif_14_bold);
            mainMenu[i].addActionListener(this);
            mainMenu[i].setActionCommand("m0o" + (i + 1));
            mainMenu[i].setVisible(true);
        }

        //-----------------------------------------ALBUM MENU-------------------------------------------------------------
        String[] subMenuOneText = {"Display a list of all albums", "Display information on a particular album",
                "Add an album", "Remove an album (2 options)", "Show statistics", "Return to main menu"};
        albumMenu = new yayJButton[6];
        for (int i = 0; i < albumMenu.length; i++) {
            albumMenu[i] = new yayJButton(lessButtonRounding, subMenuOneText[i]);
            setUpThisButton(albumMenu[i], lessButtonRounding, myGreen, cmu_serif_40_bold);
            albumMenu[i].addActionListener(this);
            albumMenu[i].setActionCommand("m1o" + (i + 1));
            albumMenu[i].setVisible(true);
        }

        //-----------------------------------------CARD MENU--------------------------------------------------------------
        String[] subMenuTwoText = {"Display all cards (in the last sorted order)",
                "Display information on a particular card", "Add a card", "Remove a card (4 options)", "Edit attack",
                "Sort cards (3 options)", "Return to Main Menu"};
        cardMenu = new yayJButton[7];
        for (int i = 0; i < cardMenu.length; i++) {
            cardMenu[i] = new yayJButton(lessButtonRounding, subMenuTwoText[i]);
            setUpThisButton(cardMenu[i], lessButtonRounding, myBlue, cmu_serif_40_bold);
            cardMenu[i].addActionListener(this);
            cardMenu[i].setActionCommand("m2o" + (i + 1));
            cardMenu[i].setVisible(true);
        }
        //------------------------------------ BACK BUTTONS --------------------------------------------------------------

        backButtons = new yayJButton[2];
        for (int i = 0; i < backButtons.length; i++) {
            backButtons[i] = new yayJButton(lessButtonRounding, "Go Back");
            setUpThisButton(backButtons[i], lessButtonRounding, colorsInOrder[i], cmu_serif_40_bold);
            backButtons[i].addActionListener(this);
            backButtons[i].setActionCommand("b" + (i + 1));
            backButtons[i].setVisible(true);
        }
        //------------------------------------ TEXT PANE SETUP -----------------------------------------------------------
        mainDisplayTextPane = new NoWrapJTextPane();
        mainDisplayTextPane.setFont(cmu_serif_14_bold);
        mainDisplayTextPane.setEditable(false);
        mainDisplayScrollPane = new JScrollPane(mainDisplayTextPane);
        mainDisplayScrollPane.setVisible(false);
        mainDisplayScrollPane.setFont(cmu_serif_14_bold);

        //------------------------------------FRAME RESIZED COMPONENT LISTENER -------------------------------------------
        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized (ComponentEvent e) {
                int width = mainPanel.getWidth();
                int height = mainPanel.getHeight();
                mainPanel.setSize(width, height);
                switch (atMenu) {
                    case "m0" -> perfectlySizedButtons(mainMenu, width, height, mainMenuFont(width * height));
                    case "m1" -> perfectlySizedButtons(albumMenu, width, height, firstMenuFont(width * height));
                    case "m2" -> perfectlySizedButtons(cardMenu, width, height, secondMenuFont(width * height));
                    case "d1" -> perfectlySizedDisplay(backButtons[0], width, height, displayFont(width * height));
                    case "d2" -> perfectlySizedDisplay(backButtons[1], width, height, displayFont(width * height));
                }

            }
        });

        //-----------------------------------------FINAL SETUP------------------------------------------------------------
        for (yayJButton jButton : mainMenu) {
            mainPanel.add(jButton);
        }
        for (yayJButton jButton : albumMenu) {
            mainPanel.add(jButton);
        }
        for (yayJButton jButton : cardMenu) {
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

    /**
     * This method performs the action when a button is pressed.
     * It gets the eventName to find out which button was pressed and performs the associated action.
     * @param e the event to be processed
     */
    public void actionPerformed (ActionEvent e) {
        String eventName = e.getActionCommand(); // stores the string given by the button when it was clicked
        int width = mainPanel.getWidth();
        int height = mainPanel.getHeight();
        int choice;
        switch (eventName) {
            case "m0o1": // album menu picked
                atMenu = "m1";
                System.out.println("album menu");
                for (yayJButton jButton : mainMenu) { // sets all main menu buttons to invisible
                    jButton.setVisible(false);
                }
                // resizes and sets all album menu buttons to visible
                perfectlySizedButtons(albumMenu, mainPanel.getWidth(), mainPanel.getHeight(),
                        firstMenuFont(mainPanel.getWidth() * mainPanel.getHeight()));
                for (yayJButton jButton : albumMenu) {
                    jButton.setVisible(true);
                }
                break;
            case "m0o2": // card menu picked
                System.out.println("card menu picked");
                if (albums.isEmpty()) { // checks if any albums are imported
                    noAlbumsImported(); // tells the user no albums are imported
                    return;
                } else if (albums.size() == 1) { // checks if only one album is imported
                    atMenu = "m2";
                    oneAlbumImported(); // tells the user only one album is imported and auto selects it
                    for (yayJButton jButton : mainMenu) { // sets all main menu buttons to invisible
                        jButton.setVisible(false);
                    }
                    // resizes and sets all album menu buttons to visible
                    perfectlySizedButtons(cardMenu, mainPanel.getWidth(), mainPanel.getHeight(),
                            secondMenuFont(mainPanel.getWidth() * mainPanel.getHeight()));
                    for (yayJButton jButton : cardMenu) {
                        jButton.setVisible(true);
                    }
                    System.out.println("in card menu");
                    return;
                } else {
                    // if more than one album is imported, the user chooses which album to go to
                    chooseFromAList(myBlue, true, "album", 150);
                    if (albumIndexChosen != -1) { // if the user chose an album
                        atMenu = "m2";
                        for (yayJButton jButton : mainMenu) {
                            jButton.setVisible(false);
                        }
                        // resizes and sets all album menu buttons to visible
                        perfectlySizedButtons(cardMenu, mainPanel.getWidth(), mainPanel.getHeight(),
                                secondMenuFont(mainPanel.getWidth() * mainPanel.getHeight()));
                        for (yayJButton jButton : cardMenu) {
                            jButton.setVisible(true);
                        }
                    }
                }
                break;
            case "m1o1", "m1o2", "m1o3", "m1o4", "m1o5": // album menu options, but not back button
                choice = Integer.parseInt(eventName.substring(3));
                int numOfAlbumsImported = albums.size();
                if (choice != 3) { // user does not want to add cards
                    if (numOfAlbumsImported == 0) { // if no albums are imported
                        choice = 6;
                    } else if (numOfAlbumsImported == 1) { // if only one album imported
                        if (choice != 1 && choice != 5) { // user doesn't want to print all albums or add cards
                            oneAlbumImported();
                        }
                        switch (choice) { // if only one album imported auto selects album
                            case 2 -> choice = 7;
                            case 4 -> choice = 8;
                        }
                    }
                }
                switch (choice) {
                    case 1: // Display a list of all albums
                        System.out.println("m1o1 picked");
                        showTheTextPane(albumMenu, 0, width, height);
                        printNameDateAllAlbums(mainDisplayTextPane);
                        break;
                    case 2: // Display info on a particular album
                        System.out.println("m1o2 picked");
                        chooseFromAList(myGreen, false, "album", 100);
                        if (albumIndexChosen != -1) {
                            showTheTextPane(albumMenu, 0, width, height);
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
                        showTheTextPane(albumMenu, 0, width, height);
                        System.out.println("m1o5 picked");
                        printStatistics(mainDisplayTextPane);
                        break;
                    case 6: // no albums imported, and the user does not want to add albums
                        System.out.println("m1 but !o3. 0 albums imported");
                        noAlbumsImported(); // tells the user no albums imported
                        break;
                    case 7: // 1 album imported, but the user wants to display an album
                        System.out.println("m1o2. 1 album imported");
                        showTheTextPane(albumMenu, 0, width, height);
                        printAlbum();
                        break;
                    case 8: // 1 album imported, but the user wants to remove an album
                        System.out.println("m1o4. 1 album imported");
                        albums.get(0).removeAlbum();
                        albums.remove(0);
                }
                break;
            case "m2o1", "m2o2", "m2o3", "m2o4", "m2o5", "m2o6": // card menu options, but not back button
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
                        showTheTextPane(cardMenu, 1, width, height);
                        printCards(mainDisplayTextPane, currentAlbum, false);
                        break;
                    case 2: // Display info on a particular card
                        System.out.println("m2o2 picked");
                        chooseFromAList(myBlue, false, "card", 100);
                        if (cardIndexChosen != -1) {
                            showTheTextPane(cardMenu, 1, width, height);
                            printCard(cards);
                        }
                        break;
                    case 3: // Add a card
                        System.out.println("m1o3 picked");
                        importCard(currentAlbum);
                        break;
                    case 4: // Remove a card (4 options)
                        System.out.println("m2o4 picked");
                        removeCard(currentAlbum);
                        break;
                    case 5: // Edit attack
                        System.out.println("m2o5");
                        chooseFromAList(myBlue, false, "card", 100);
                        if (cardIndexChosen != -1) { // card was chosen
                            if (currentAlbum.getCard(cardIndexChosen).getAttacksLength() == 1) {
                                oneAttackInCard(); // One attack in the card, so automatically chosen
                            } else { // choose attack
                                chooseFromAList(myBlue, true, "attack", 150);
                            }
                            if (attackIndexChosen != -1) { // if attack was chosen
                                editAttack(currentAlbum.getCard(cardIndexChosen));
                            }
                        }
                        break;
                    case 6: // Sort cards (3 options)
                        System.out.println("m2o6");
                        chooseFromAList(myBlue, false, "sort", 50); // choose how to sort
                        if (sortIndexChosen != -1) { // if the user chose a valid sort
                            sortCards(currentAlbum);
                            showTheTextPane(cardMenu, 1, width, height);
                            printCards(mainDisplayTextPane, currentAlbum, true);
                        }
                        break;
                    case 7: // no cards imported, and the user does not want to add cards
                        System.out.println("m2 but !o3. 0 albums imported");
                        noCardsInAlbum();
                        break;
                    case 8: // all info of only card
                        System.out.println("m2o2 only one card");
                        showTheTextPane(cardMenu, 1, width, height);
                        printCard(cards);
                        break;
                    case 9: // remove only card
                        System.out.println("m2o4 only one card");
                        currentAlbum.removeCard(0);
                        break;
                    case 10: // edit only card
                        System.out.println("m2o5 only one card");
                        if (currentAlbum.getCard(cardIndexChosen).getAttacksLength() == 1) { // IF ONLY ONE ATTACK IN CARD
                            oneAttackInCard();
                        } else { // if many attacks in card
                            chooseFromAList(myBlue, true, "attack", 150);
                        }
                        editAttack(currentAlbum.getCard(0));
                        break;
                    case 11: // sorting won't do anything
                        System.out.println("m2o6 only one card");
                        oneCardInAlbumSort();
                        showTheTextPane(cardMenu, 1, width, height);
                        printCard(cards);
                        break;
                }


                break;
            case "b1": // back button to get back to the album menu
                atMenu = "m1";
                System.out.println("back to album menu");
                backButtons[0].setVisible(false);
                mainDisplayScrollPane.setVisible(false);
                perfectlySizedButtons(albumMenu, mainPanel.getWidth(), mainPanel.getHeight(),
                        firstMenuFont(mainPanel.getWidth() * mainPanel.getHeight()));
                for (yayJButton jButton : albumMenu) {
                    jButton.setVisible(true);
                }
                break;
            case "b2": // back button to get back to the card menu
                atMenu = "m2";
                System.out.println("back to card menu");
                backButtons[1].setVisible(false);
                mainDisplayScrollPane.setVisible(false);
                perfectlySizedButtons(cardMenu, mainPanel.getWidth(), mainPanel.getHeight(),
                        secondMenuFont(mainPanel.getWidth() * mainPanel.getHeight()));
                for (yayJButton jButton : cardMenu) {
                    jButton.setVisible(true);
                }
                break;
            case "m1o6": // back button to get back to the main menu
                atMenu = "m0";
                System.out.println("m1o6 picked");
                System.out.println("back to main menu");
                for (yayJButton jButton : albumMenu) {
                    jButton.setVisible(false);
                }
                perfectlySizedButtons(mainMenu, mainPanel.getWidth(), mainPanel.getHeight(),
                        mainMenuFont(mainPanel.getWidth() * mainPanel.getHeight()));
                for (yayJButton jButton : mainMenu) {
                    jButton.setVisible(true);
                }
                break;
            case "m2o7": // back button to get back to the main menu
                atMenu = "m0";
                System.out.println("m2o7 picked");
                System.out.println("back to main menu");
                for (yayJButton jButton : cardMenu) {
                    jButton.setVisible(false);
                }
                perfectlySizedButtons(mainMenu, mainPanel.getWidth(), mainPanel.getHeight(),
                        mainMenuFont(mainPanel.getWidth() * mainPanel.getHeight()));
                for (yayJButton jButton : mainMenu) {
                    jButton.setVisible(true);
                }
        }
    }

    /**
     * This method sorts the cards in the album given based on the sortIndexChosen.
     * @param currentAlbum is the album to sort the cards of
     */
    public void sortCards (Album currentAlbum) {
        switch (sortIndexChosen) {
            case 0 -> currentAlbum.sortCardsByName();
            case 1 -> currentAlbum.sortCardsByHP();
            case 2 -> currentAlbum.sortCardsByDate();
        }
    }

    /**
     * This method sets up a default error pane to be used in the GUI.
     * @param errorPane this is the JTextPane to be set up.
     */
    public void errorPaneSetUp (JTextPane errorPane) {
        StyledDocument doc = errorPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        errorPane.setBackground(new Color(236, 236, 236));
        errorPane.setFont(cmu_serif_18);
        errorPane.setForeground(Color.red);
        errorPane.setEditable(false);
    }

    /**
     * This method allows the user to edit the attack of a given card
     * @param card the card to edit the attack of
     */
    public void editAttack (Card card) {
        // sets up the attack edit dialog
        JDialog editAttackDialog = new JDialog(mainFrame, "Edit Attack", true);
        JPanel editAttackPanel = new JPanel();
        editAttackPanel.setLayout(null);
        int componentWidth = 300;
        int xCo = 50;

        editAttackDialog.setPreferredSize(new Dimension(400, 400 + 28));
        editAttackPanel.setPreferredSize(new Dimension(400, 400));

        int radioHeight = 35;
        JLabel label1 = new JLabel("What would you like to change?");
        label1.setBounds(xCo + 4, 15, componentWidth, 20);
        label1.setFont(cmu_serif_18);

        // sets up radio buttons, so the user can choose which of the three different attributes to change
        JRadioButton radio1 = new JRadioButton("Name");
        radio1.setBounds(xCo, 50, componentWidth, radioHeight);
        radio1.setFont(cmu_serif_18);

        JRadioButton radio2 = new JRadioButton("Description");
        radio2.setBounds(xCo, 50 + radioHeight, componentWidth, radioHeight);
        radio2.setFont(cmu_serif_18);

        JRadioButton radio3 = new JRadioButton("Damage");
        radio3.setBounds(xCo, 50 + radioHeight * 2, componentWidth, radioHeight);
        radio3.setFont(cmu_serif_18);

        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);
        group.add(radio3);

        JLabel label2 = new JLabel();
        label2.setBounds(xCo + 4, 165, componentWidth, 50);
        label2.setFont(cmu_serif_18);
        label2.setVisible(false);

        JTextField textField = new JTextField();
        textField.setBounds(xCo, 215, componentWidth, 50);
        textField.setFont(cmu_serif_18);
        textField.setVisible(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setEditable(false);

        // sets up the error pane
        JTextPane errorPane = new JTextPane() {
            public boolean getScrollableTracksViewportWidth () {
                return true;
            }
        };
        errorPaneSetUp(errorPane);
        errorPane.setBounds(xCo, 275, componentWidth, 50);

        // sets up the button to edit the attack
        yayJButton removeCardButton = new yayJButton(20, "Edit Attack");
        setUpThisButton(removeCardButton, 20, myBlue, cmu_serif_20);
        removeCardButton.addActionListener(e -> {
            String input = textField.getText().trim();
            if (radio1.isSelected()) { //edit the name
                String errorName = validString(input, true);
                if (!errorName.isEmpty()) { // invalid input
                    errorPane.setText(errorName);
                } else {
                    card.getAttack(attackIndexChosen).edit("name", input);
                    editAttackDialog.dispose();
                    System.out.println("attack name edited");
                }
            } else if (radio2.isSelected()) { //edit the description
                String errorName = validString(input, false);
                if (!errorName.isEmpty()) { // invalid input
                    errorPane.setText(errorName);
                } else {
                    card.getAttack(attackIndexChosen).edit("description", input);
                    editAttackDialog.dispose();
                    System.out.println("attack description edited");
                }
            } else if (radio3.isSelected()) { //edit the date
                String errorName = validString(input, true);
                if (!errorName.isEmpty()) { // invalid input
                    errorPane.setText(errorName);
                } else {
                    card.getAttack(attackIndexChosen).edit("damage", input);
                    editAttackDialog.dispose();
                    System.out.println("attack damage edited");
                }
            }

        });
        removeCardButton.setVisible(false);
        removeCardButton.setBounds(xCo, 335, componentWidth, 50);

        radio1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                label2.setText("Enter new name:");
                label2.setVisible(true);
                textField.setEditable(true);
                textField.requestFocusInWindow();
                textField.setVisible(true);
                removeCardButton.setVisible(true);
                errorPane.setText("");
                EventQueue.invokeLater(textField::requestFocusInWindow);
            }
        });
        radio2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                label2.setText("Enter new description:");
                label2.setVisible(true);
                textField.setEditable(true);
                textField.requestFocusInWindow();
                textField.setVisible(true);
                removeCardButton.setVisible(true);
                errorPane.setText("");
                EventQueue.invokeLater(textField::requestFocusInWindow);
            }
        });
        radio3.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                label2.setText("Enter new damage:");
                label2.setVisible(true);
                textField.setEditable(true);
                textField.requestFocusInWindow();
                textField.setVisible(true);
                removeCardButton.setVisible(true);
                errorPane.setText("");
                EventQueue.invokeLater(textField::requestFocusInWindow);
            }
        });

        // final set up by adding all components to the panel and frame
        editAttackPanel.add(label1);
        editAttackPanel.add(radio1);
        editAttackPanel.add(radio2);
        editAttackPanel.add(radio3);
        editAttackPanel.add(label2);
        editAttackPanel.add(textField);
        editAttackPanel.add(errorPane);
        editAttackPanel.add(removeCardButton);
        editAttackDialog.add(editAttackPanel);

        editAttackDialog.setLocationRelativeTo(null);
        editAttackDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        editAttackDialog.setResizable(false);
        editAttackDialog.pack();
        editAttackDialog.setVisible(true);
    }

    /**
     * This method sets up a given JButton to have rounded corners, a given color, and a given font.
     * @param thisButton the JButton to set up
     * @param cornerRadius the corner radius of the button
     * @param buttonColor the color of the button
     * @param font the font the button should have
     */
    public void setUpThisButton (JButton thisButton, int cornerRadius, Color buttonColor, Font font) {
        thisButton.setFont(font);
        thisButton.setBackground(buttonColor);
        thisButton.setFocusable(false);
        thisButton.setOpaque(false); // Make the button transparent
        thisButton.setBorder(new RoundedBorder(cornerRadius, 1)); // Set the border as before
        thisButton.setContentAreaFilled(false); // Prevent the button from filling its area
        thisButton.setForeground(Color.WHITE); // Set the text color
        thisButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered (MouseEvent pABtn) {
                thisButton.setBackground(thisButton.getBackground().darker());
                thisButton.setBorder(new RoundedBorder(cornerRadius, 1 + 2)); // Set the border as before

            }

            public void mouseExited (MouseEvent pABtn) {
                thisButton.setBackground(buttonColor);
                thisButton.setBorder(new RoundedBorder(cornerRadius, 1)); // Set the border as before
            }
        });
    }

    /**
     * This method allows the user to import a card into the given album.
     * The user chooses the name, date, HP, type, and gives the attacks of the card
     * @param currentAlbum the album to add the card to
     */
    public void importCard (Album currentAlbum) {
        if (currentAlbum.atMaxCapacity()) { // if the album is at max capacity
            JLabel label = new JLabel("<html> Sorry, this album is <br> at maximum capacity. " +
                    "You <br> cannot add more cards. </html>");
            label.setFont(cmu_serif_18);
            JOptionPane.showMessageDialog(null, label, "Album at Max Capacity",
                    JOptionPane.INFORMATION_MESSAGE, sadPikachu);
        } else { // if space is available for more cards
            JDialog importCardDialog = new JDialog(mainFrame, "Add Card", true);
            JPanel importCardPanel = new JPanel();
            int height = 765;
            importCardDialog.setPreferredSize(new Dimension(500 + 19, height + 28 + 20));
            importCardPanel.setPreferredSize(new Dimension(500, height));
            importCardPanel.setLayout(null);
            JScrollPane importCardScrollPane = new JScrollPane(importCardPanel);
            JLabel[] promptLabels = new JLabel[5]; // labels for the prompts
            JLabel[] errorLabels = new JLabel[5]; // labels for the errors
            JTextField[] inputFields = new JTextField[5];  // text fields for the user input
            String[] prompts = {"Name", "HP", "Type", "Date in MM/DD/YYYY", "Number of Attacks"};  // prompts for user
            makeForm(promptLabels, inputFields, errorLabels, prompts, importCardPanel);

            // sets up the attack form
            JLabel[] attackPromptNumLabels = new JLabel[10];
            JLabel[] attackPromptTypeLabels = new JLabel[30];
            JLabel[] attackErrorLabels = new JLabel[20];
            JTextField[] attackInputFields = new JTextField[30];

            int startAttackAt = 445;
            makeAttackForm(attackPromptNumLabels, attackPromptTypeLabels, attackInputFields, attackErrorLabels,
                    importCardPanel, startAttackAt, 10);

            yayJButton submitCardButton = new yayJButton(20, "Import Album");
            setUpThisButton(submitCardButton, 20, myBlue, cmu_serif_20);
            submitCardButton.addActionListener(e -> {
                String name = getString(inputFields[0].getText(), errorLabels[0], true); // name of card
                int HP = getInt(inputFields[1].getText(), errorLabels[1], 1, Integer.MAX_VALUE); // HP of card
                String type = getString(inputFields[2].getText(), errorLabels[2], true); // card type
                errorLabels[3].setText(validDate(inputFields[3].getText()));
                int value = getInt(inputFields[4].getText(), errorLabels[4], 1, 10);
                for (int i = 0, j = 0; i < value * 3; i++) {
                    if (i % 3 == 0 || i % 3 == 2) {
                        attackErrorLabels[j].setText(validString(attackInputFields[i].getText(), true));
                        j++;
                    }
                }
                // this for loop checks if the user inputted valid attacks
                for (int i = 0; i < 5; i++) {
                    if (!errorLabels[i].getText().isEmpty()) {
                        return;
                    }
                }
                for (int i = 0; i < value * 2; i++) {
                    if (!attackErrorLabels[i].getText().isEmpty()) {
                        return;
                    }
                }
                // if the user inputted valid attacks
                Date date = new Date(parseDate(inputFields[3].getText().trim()));
                Attack[] attacks = new Attack[value];
                // adds attacks to an array
                for (int j = 0; j < attacks.length; j++) {
                    String attackName = attackInputFields[j * 3].getText().trim();
                    String attackDescription = attackInputFields[j * 3 + 1].getText().trim();
                    String attackDamage = attackInputFields[j * 3 + 2].getText().trim();
                    attacks[j] = (new Attack(attackName, attackDescription, attackDamage));
                }
                currentAlbum.addCard(new Card(name, HP, type, date, attacks)); //adds the card to the album
                importCardDialog.dispose();
                System.out.println("card imported");
            });
            submitCardButton.setVisible(false);
            submitCardButton.setBounds(15, 445, 480, 50);

            inputFields[4].getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate (DocumentEvent e) {
                    int value = getInt(inputFields[4].getText(), errorLabels[4], 1, 10);

                    if (value >= 1) { // if the user wants to add attacks, and the attack number is valid
                        // sets the attack prompts to visible
                        for (int i = 0; i < value; i++) {
                            attackPromptNumLabels[i].setVisible(true);
                            for (int j = i * 3; j < (i * 3 + 3); j++) {
                                attackPromptTypeLabels[j].setVisible(true);
                                attackInputFields[j].setVisible(true);
                            }
                            attackErrorLabels[i * 2].setVisible(true);
                            attackErrorLabels[i * 2 + 1].setVisible(true);
                        }
                        // sets the other attack prompts to invisible
                        for (int i = value; i < 10; i++) {
                            attackPromptNumLabels[i].setVisible(false);
                            for (int j = i * 3; j < (i * 3 + 3); j++) {
                                attackPromptTypeLabels[j].setVisible(false);
                                attackInputFields[j].setVisible(false);
                            }
                            attackErrorLabels[i * 2].setVisible(false);
                            attackErrorLabels[i * 2 + 1].setVisible(false);
                        }
                        submitCardButton.setVisible(true);
                        submitCardButton.setBounds(15, startAttackAt + 270 * value, 480, 50);
                        importCardPanel.setPreferredSize(new Dimension(500, startAttackAt + 65 + 270 * value));
                        importCardScrollPane.setPreferredSize(new Dimension(500,
                                startAttackAt + 65 + 270 * value));
                    } else {
                        // sets all attack prompts to invisible
                        for (int i = 0; i < 10; i++) {
                            attackPromptNumLabels[i].setVisible(false);
                            for (int j = i * 3; j < (i * 3 + 3); j++) {
                                attackPromptTypeLabels[j].setVisible(false);
                                attackInputFields[j].setVisible(false);
                            }
                            attackErrorLabels[i * 2].setVisible(false);
                            attackErrorLabels[i * 2 + 1].setVisible(false);
                        }
                        submitCardButton.setVisible(false);
                        importCardPanel.setPreferredSize(new Dimension(500, height));
                        importCardScrollPane.setPreferredSize(new Dimension(500, height));
                    }
                }

                @Override
                public void insertUpdate (DocumentEvent e) {
                    changedUpdate(e);
                }

                @Override
                public void removeUpdate (DocumentEvent e) {
                    changedUpdate(e);
                }
            });

            // sets up the final panel and dialog
            importCardPanel.add(submitCardButton);
            importCardScrollPane.setPreferredSize(new Dimension(500, height));
            importCardScrollPane.setOpaque(false);
            importCardScrollPane.getViewport().setOpaque(false);
            importCardScrollPane.setBorder(BorderFactory.createEmptyBorder());
            importCardDialog.add(importCardScrollPane);
            importCardDialog.setLocationRelativeTo(null);
            importCardDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            importCardDialog.setResizable(false);
            importCardDialog.pack();
            importCardDialog.setVisible(true);
        }
    }

    /**
     * This method makes a form for the import card dialog
     * @param promptLabels this is the array of prompt JLabels (Name, HP, Type, Date, Number of Attacks)
     * @param inputFields this is the array of input JTextFields to put the user input
     * @param errorLabels Array of error JLabels to display errors
     * @param prompts this is the String array of prompts to display in the prompt JLabels
     * @param importCardPanel this is the panel to add all the components to
     */
    public void makeForm (JLabel[] promptLabels, JTextField[] inputFields, JLabel[] errorLabels, String[] prompts,
                          JPanel importCardPanel) {
        for (int i = 0; i < 5; i++) { // loops 5 times for the 5 inputs
            promptLabels[i] = new JLabel(prompts[i] + ": ");
            promptLabels[i].setBounds(15, 20 + i * 85, 210, 50);
            promptLabels[i].setFont(cmu_serif_18);
            promptLabels[i].setHorizontalAlignment(JLabel.CENTER);
            promptLabels[i].setOpaque(true);
            importCardPanel.add(promptLabels[i]);

            inputFields[i] = new JTextField();
            inputFields[i].setBounds(15 + 210, 20 + i * 85, 275, 50);
            inputFields[i].setHorizontalAlignment(JTextField.CENTER);
            inputFields[i].setFont(cmu_serif_18);
            importCardPanel.add(inputFields[i]);

            errorLabels[i] = new JLabel();
            errorLabels[i].setBounds(15, 20 + 50 + i * 85, 480, 35);
            errorLabels[i].setForeground(Color.RED);
            errorLabels[i].setHorizontalAlignment(JLabel.CENTER);
            errorLabels[i].setFont(cmu_serif_18);
            errorLabels[i].setOpaque(true);
            importCardPanel.add(errorLabels[i]);
        }
        inputFields[0].getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate (DocumentEvent e) {
                errorLabels[0].setText(validString(inputFields[0].getText(), true));
            }

            @Override
            public void insertUpdate (DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate (DocumentEvent e) {
                changedUpdate(e);
            }
        });
        inputFields[1].getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate (DocumentEvent e) {
                errorLabels[1].setText(validInt(inputFields[1].getText(), 1, Integer.MAX_VALUE));
            }

            @Override
            public void insertUpdate (DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate (DocumentEvent e) {
                changedUpdate(e);
            }
        });
        inputFields[2].getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate (DocumentEvent e) {
                errorLabels[2].setText(validString(inputFields[2].getText(), true));
            }

            @Override
            public void insertUpdate (DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate (DocumentEvent e) {
                changedUpdate(e);
            }
        });
        inputFields[3].getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate (DocumentEvent e) {
                errorLabels[3].setText(validDate(inputFields[3].getText()));
            }

            @Override
            public void insertUpdate (DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate (DocumentEvent e) {
                changedUpdate(e);
            }
        });
    }

    public void makeAttackForm (JLabel[] attackPromptNumLabels, JLabel[] attackPromptTypeLabels,
                                JTextField[] attackInputFields, JLabel[] attackErrorLabels, JPanel importCardPanel,
                                int startYVal, int numOfAttacks) {
        for (int i = 0; i < numOfAttacks; i++) {
            int three = i * 3;
            int two = i * 2;
            int shiftBy = i * 270;

            attackPromptNumLabels[i] = new JLabel("Enter Attack #" + (i + 1) + ": ");
            attackPromptNumLabels[i].setBounds(15, startYVal + shiftBy, 480, 40);
            attackPromptNumLabels[i].setFont(cmu_serif_18);
            attackPromptNumLabels[i].setHorizontalAlignment(JLabel.CENTER);
            attackPromptNumLabels[i].setOpaque(true);
            attackPromptNumLabels[i].setVisible(false);
            importCardPanel.add(attackPromptNumLabels[i]);

            // NAME:
            attackPromptTypeLabels[three] = new JLabel("Name: ");
            attackPromptTypeLabels[three].setBounds(15, startYVal + 40 + shiftBy, 120, 50);
            attackPromptTypeLabels[three].setFont(cmu_serif_18);
            attackPromptTypeLabels[three].setHorizontalAlignment(JLabel.CENTER);
            attackPromptTypeLabels[three].setOpaque(true);
            attackPromptTypeLabels[three].setVisible(false);
            importCardPanel.add(attackPromptTypeLabels[three]);

            // Name Text Field
            attackInputFields[three] = new JTextField();
            attackInputFields[three].setBounds(15 + 120, startYVal + 40 + shiftBy, 365, 50);
            attackInputFields[three].setHorizontalAlignment(JTextField.CENTER);
            attackInputFields[three].setFont(cmu_serif_18);
            attackInputFields[three].setVisible(false);
            importCardPanel.add(attackInputFields[three]);

            // Name Error Label
            attackErrorLabels[two] = new JLabel();
            attackErrorLabels[two].setBounds(15, startYVal + 90 + shiftBy, 480, 35);
            attackErrorLabels[two].setForeground(Color.RED);
            attackErrorLabels[two].setHorizontalAlignment(JLabel.CENTER);
            attackErrorLabels[two].setFont(cmu_serif_18);
            attackErrorLabels[two].setOpaque(true);
            attackErrorLabels[two].setVisible(false);
            importCardPanel.add(attackErrorLabels[two]);
            int finalTwo = two;
            int finalThree = three;
            attackInputFields[three].getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate (DocumentEvent e) {
                    attackErrorLabels[finalTwo].setText(validString(attackInputFields[finalThree].getText(),
                            true));
                }

                @Override
                public void insertUpdate (DocumentEvent e) {
                    changedUpdate(e);
                }

                @Override
                public void removeUpdate (DocumentEvent e) {
                    changedUpdate(e);
                }
            });

            two++;
            three++;
            for (int j = 0; j < 2; j++, three++) {
                int yVal = startYVal + (40 + 50 + 35 + j * 50) + shiftBy;
                // description or damage:
                attackPromptTypeLabels[three] = new JLabel((j == 0 ? "Description" : "Damage") + ": ");
                attackPromptTypeLabels[three].setBounds(15, yVal, 120, 50);
                attackPromptTypeLabels[three].setFont(cmu_serif_18);
                attackPromptTypeLabels[three].setHorizontalAlignment(JLabel.CENTER);
                attackPromptTypeLabels[three].setOpaque(true);
                attackPromptTypeLabels[three].setVisible(false);
                importCardPanel.add(attackPromptTypeLabels[three]);

                // description or damage field
                attackInputFields[three] = new JTextField();
                attackInputFields[three].setBounds(15 + 120, yVal, 365, 50);
                attackInputFields[three].setHorizontalAlignment(JTextField.CENTER);
                attackInputFields[three].setFont(cmu_serif_18);
                attackInputFields[three].setVisible(false);
                importCardPanel.add(attackInputFields[three]);
            }
            // Damage Error Label
            attackErrorLabels[two] = new JLabel();
            attackErrorLabels[two].setBounds(15, startYVal + 225 + shiftBy, 480, 35);
            attackErrorLabels[two].setForeground(Color.RED);
            attackErrorLabels[two].setHorizontalAlignment(JLabel.CENTER);
            attackErrorLabels[two].setFont(cmu_serif_18);
            attackErrorLabels[two].setOpaque(true);
            attackErrorLabels[two].setVisible(false);
            importCardPanel.add(attackErrorLabels[two]);


            int finalThree1 = three - 1;
            int finalTwo1 = two;
            attackInputFields[finalThree1].getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate (DocumentEvent e) {
                    attackErrorLabels[finalTwo1].setText(validString(attackInputFields[finalThree1].getText(),
                            true));
                }

                @Override
                public void insertUpdate (DocumentEvent e) {
                    changedUpdate(e);
                }

                @Override
                public void removeUpdate (DocumentEvent e) {
                    changedUpdate(e);
                }
            });


        }
    }

    /**
     * This method allows the user to remove a card from the given album.
     * @param currentAlbum the album to remove the card from
     */
    public void removeCard (Album currentAlbum) {
        // sets up the remove card dialog
        JDialog removeCardDialog = new JDialog(mainFrame, "Remove Card", true);
        JPanel removeCardPanel = new JPanel();
        removeCardPanel.setLayout(null);
        int componentWidth = 300;
        int xCo = 50;
        int splitIntoParts = 52;

        removeCardDialog.setPreferredSize(new Dimension(850, 435 + 28));
        removeCardPanel.setPreferredSize(new Dimension(850, 435));

        int radioHeight = 35;
        // sets up the radio buttons
        JLabel label1 = new JLabel("Choose removal method:");
        label1.setBounds(xCo + 4, 15, componentWidth, 20);
        label1.setFont(cmu_serif_18);

        JRadioButton radio1 = new JRadioButton("By Name");
        radio1.setBounds(xCo, 50, componentWidth, radioHeight);
        radio1.setFont(cmu_serif_18);

        JRadioButton radio2 = new JRadioButton("By HP");
        radio2.setBounds(xCo, 50 + radioHeight, componentWidth, radioHeight);
        radio2.setFont(cmu_serif_18);

        JRadioButton radio3 = new JRadioButton("First Card (in last sorted order)");
        radio3.setBounds(xCo, 50 + radioHeight * 2, componentWidth, radioHeight);
        radio3.setFont(cmu_serif_18);

        JRadioButton radio4 = new JRadioButton("Last Card (in last sorted order)");
        radio4.setBounds(xCo, 50 + radioHeight * 3, componentWidth, radioHeight);
        radio4.setFont(cmu_serif_18);

        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);
        group.add(radio3);
        group.add(radio4);

        JLabel label2 = new JLabel();
        label2.setBounds(xCo + 4, 200, componentWidth, 50);
        label2.setFont(cmu_serif_18);
        label2.setVisible(false);

        JTextField textField = new JTextField();
        textField.setBounds(xCo, 250, componentWidth, 50);
        textField.setFont(cmu_serif_18);
        textField.setVisible(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setEditable(false);

        // sets up the error pane
        JTextPane errorPane = new JTextPane() {
            public boolean getScrollableTracksViewportWidth () {
                return true;
            }
        };
        errorPaneSetUp(errorPane);
        errorPane.setBounds(xCo, 310, componentWidth, 50);

        yayJButton removeCardButton = new yayJButton(20, "Remove Cards");
        setUpThisButton(removeCardButton, 20, myBlue, cmu_serif_20);
        removeCardButton.addActionListener(e -> {
            String input = textField.getText();
            if (radio1.isSelected()) { // number
                String result = removeCardName(input, currentAlbum);
                if (!result.equals("success")) {
                    errorPane.setText(result);
                } else {
                    removeCardDialog.dispose();
                    System.out.println("card removed by name");
                }
            } else if (radio2.isSelected()) { //date
                String result = removeCardHP(input, currentAlbum);
                if (!result.equals("success")) {
                    errorPane.setText(result);
                } else {
                    removeCardDialog.dispose();
                    System.out.println("card removed by hp");
                }
            } else if (radio3.isSelected()) { //date
                currentAlbum.removeCard(0);
                removeCardDialog.dispose();
                System.out.println("card removed by first in last sorted order");
            } else if (radio4.isSelected()) { //date
                currentAlbum.removeCard(currentAlbum.getCardsSize() - 1);
                removeCardDialog.dispose();
                System.out.println("card removed by last in last sorted order");
            }

        });
        removeCardButton.setVisible(false);
        removeCardButton.setBounds(xCo, 370, componentWidth, 50);
        // if the user chooses to remove by name
        radio1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                label2.setText("Enter card name:");
                label2.setVisible(true);
                textField.setEditable(true);
                textField.requestFocusInWindow();
                textField.setVisible(true);
                removeCardButton.setVisible(true);
                errorPane.setText("");
                EventQueue.invokeLater(textField::requestFocusInWindow);
            }
        });
        // if the user chooses to remove by HP
        radio2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                label2.setText("Enter card HP:");
                label2.setVisible(true);
                textField.setEditable(true);
                textField.requestFocusInWindow();
                textField.setVisible(true);
                removeCardButton.setVisible(true);
                errorPane.setText("");
                EventQueue.invokeLater(textField::requestFocusInWindow);
            }
        });
        // if the user chooses to remove the first card in the last sorted order
        radio3.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                label2.setText("");
                label2.setVisible(false);
                textField.setEditable(false);
                textField.setVisible(false);
                removeCardButton.setVisible(true);
                errorPane.setText("");
            }
        });
        // if the user chooses to remove the last card in the last sorted order
        radio4.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                label2.setText("");
                label2.setVisible(false);
                textField.setEditable(false);
                textField.setVisible(false);
                removeCardButton.setVisible(true);
                errorPane.setText("");
            }
        });

        NoWrapJTextPane removeCardTextPane = new NoWrapJTextPane();
        removeCardTextPane.setFont(cmu_serif_18);
        removeCardTextPane.setEditable(false);
        JScrollPane displayScrollPane = new JScrollPane(removeCardTextPane);
        displayScrollPane.setBounds(xCo + componentWidth + 25, (int) Math.round(splitIntoParts * 0.25),
                850 - 375 - (int) Math.round(splitIntoParts * 0.25), (int) Math.round((435 * 7.25) / 7.75));
        printCards(removeCardTextPane, currentAlbum, true);
        displayScrollPane.setVisible(true);
        removeCardTextPane.setVisible(true);

        removeCardPanel.add(label1);
        removeCardPanel.add(radio1);
        removeCardPanel.add(radio2);
        removeCardPanel.add(radio3);
        removeCardPanel.add(radio4);
        removeCardPanel.add(label2);
        removeCardPanel.add(textField);
        removeCardPanel.add(errorPane);
        removeCardPanel.add(displayScrollPane);
        removeCardPanel.add(removeCardButton);
        removeCardDialog.add(removeCardPanel);

        removeCardDialog.setLocationRelativeTo(null);
        removeCardDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        removeCardDialog.setResizable(false);
        removeCardDialog.pack();
        removeCardDialog.setVisible(true);
    }

    /**
     * This method allows the user to remove a card with a given name from the given album.
     * @param input the user's input as a string
     * @param currentAlbum the album to edit the card in
     * @return a string that represents the result of the edit.
     * "Success" if the edit was successful, something else if not.
     */
    public String removeCardName (String input, Album currentAlbum) {
        String errorName = validString(input, true);
        if (errorName.isEmpty()) { // if the input is valid
            String name = input.trim();
            int firstIndexOfName;
            if ((firstIndexOfName = currentAlbum.getCardIndexOfName(name)) == -1) { // if the name is not in the album
                return "invalid card name";
            }
            ArrayList <Card> cards = currentAlbum.getCards();
            for (int i = firstIndexOfName; i < currentAlbum.getCardsSize(); i++) { // loops through the cards
                if (cards.get(i).getName().equalsIgnoreCase(name)) {
                    currentAlbum.removeCard(i);
                    i--;
                }
            }
            return "success";
        } else {
            return errorName;
        }
    }

    /**
     * This method allows the user to remove a card with a given HP from the given album.
     * @param input the user's input as a string
     * @param currentAlbum the album to edit the card in
     * @return a string that represents the result of the edit.
     */
    public String removeCardHP (String input, Album currentAlbum) {
        String errorName = validInt(input, 1, Integer.MAX_VALUE);
        if (errorName.isEmpty()) { // if the input is valid
            int hp = (int) Double.parseDouble(input.trim());
            int firstIndexOfHP;
            if ((firstIndexOfHP = currentAlbum.getCardIndexOfHP(hp)) == -1) { // if the HP is not in the album
                return "invalid card hp";
            }
            ArrayList <Card> cards = currentAlbum.getCards();
            for (int i = firstIndexOfHP; i < currentAlbum.getCardsSize(); i++) {
                if (cards.get(i).getHP() == hp) {
                    currentAlbum.removeCard(i);
                    i--;
                }
            }
            return "success";
        } else {
            return errorName;
        }
    }

    /**
     * This checks if a given String is valid. If trimmed, it cannot be empty if empty input is forbidden.
     * @param in the String to check
     * @param emptyInputForbidden whether empty input is forbidden
     * @return a String that represents the error message. If the String is valid, it is empty.
     */
    public String validString (String in, boolean emptyInputForbidden) {
        String inputString; // stores the input
        inputString = in.trim();
        if (inputString.isEmpty() && emptyInputForbidden) { //input length 0 chars
            return "ERROR! You did not provide a response.";
        }
        return "";
    }

    /**
     * This method checks if a given String is a valid String.
     * @param in the String to check
     * @return returns the String in again
     */
    public String getString (String in, JLabel errorLbl, boolean emptyInputForbidden) {
        String validString = validString(in, emptyInputForbidden);
        errorLbl.setText(validString);
        return in.trim();
    }

    /**
     * This method checks if a given String is a valid integer.
     * @param in the String to check
     * @return a String that represents the error message. If the integer is valid, it is empty.
     */
    public String validInt (String in, int min, int max) {
        if (max < min) {
            min = Integer.MIN_VALUE;
            max = Integer.MAX_VALUE;
        }
        double n = -1; // stores the input from the user
        if (in.isEmpty()) {
            return "Please enter a number " + min + " to " + max + " (inclusive).";
        }
        try {
            n = Double.parseDouble(in.trim());
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
        } catch (NumberFormatException e) {
            if (n == -1) { //invalid type
                return "ERROR! You entered an Invalid Type.";
            } else if (n == 1) { // less than min
                return "ERROR! Your number cannot be less than " + min + ".";
            } else { // more than max
                return "ERROR! Your number cannot be greater than " + max + ".";
            }
        }
        return "";
    }

    /**
     * This method gets a valid integer from a given String.
     * @param in the String to get the integer from
     * @param errorLbl the error label to display the error message in
     * @param min the minimum value the integer can be
     * @param max the maximum value the integer can be
     * @return the integer from the String
     */
    public int getInt (String in, JLabel errorLbl, int min, int max) {
        String validInt = validInt(in, min, max);
        errorLbl.setText(validInt);
        if (validInt.isEmpty()) { // valid number
            return (int) Double.parseDouble(in.trim());
        } else {
            return (min - 1);
        }
    }

    /**
     * This method shows a dialog that tells the user that only one album was imported, so that album has been chosen.
     */
    public void oneAlbumImported () {
        JLabel label = new JLabel("<html> Only one album imported, <br> so that album has been <br> automatically" +
                " chosen. </html>");
        label.setFont(cmu_serif_18);
        JOptionPane.showMessageDialog(null, label, "One Album imported",
                JOptionPane.INFORMATION_MESSAGE, happyPikachu);
        albumIndexChosen = 0;
    }

    /**
     * This method shows a dialog that tells the user that only one attack was in the card,
     * so that attack has been chosen.
     */
    public void oneAttackInCard () {
        JLabel label = new JLabel("<html> Only one attack in card, <br> so that attack has been <br> " +
                "automatically chosen. </html>");
        label.setFont(cmu_serif_18);
        JOptionPane.showMessageDialog(null, label, "One Album imported",
                JOptionPane.INFORMATION_MESSAGE, happyPikachu);
        attackIndexChosen = 0;
    }

    /**
     * This method shows a dialog that tells the user that only one card was in the album, so that card has been chosen.
     */
    public void oneCardInAlbum () {
        JLabel label = new JLabel("<html> Only one card in album, <br> so that card has been <br> automatically " +
                "chosen </html>");
        label.setFont(cmu_serif_18);
        JOptionPane.showMessageDialog(null, label, "One Card in Album",
                JOptionPane.INFORMATION_MESSAGE, happyPikachu);
        cardIndexChosen = 0;
    }

    /**
     * This method shows a dialog that tells the user that only one card was in the album, so no point in sorting.
     */
    public void oneCardInAlbumSort () {
        JLabel label = new JLabel("<html> Since there is only one card, <br> output will the same no <br> matter " +
                "which method " + "of <br> sorting is chosen. Close this <br> window to see the only card <br> in the " +
                "album. </html>");
        label.setFont(cmu_serif_18);
        JOptionPane.showMessageDialog(null, label, "One Card in Album",
                JOptionPane.INFORMATION_MESSAGE, happyPikachu);
        cardIndexChosen = 0;
    }

    /**
     * This method shows a dialog that tells the user that no albums were imported.
     */
    public void noAlbumsImported () {
        JLabel label = new JLabel("<html> There are no albums <br> imported. Please import an <br> album to do this" +
                " action.</html>");
        label.setFont(cmu_serif_18);
        JOptionPane.showMessageDialog(null, label, "No Albums imported",
                JOptionPane.INFORMATION_MESSAGE, sadPikachu);
    }

    /**
     * This method shows a dialog that tells the user that no cards were in the album.
     */
    public void noCardsInAlbum () {
        JLabel label = new JLabel("<html> There are no cards <br> in album. Please add a <br> card to do this " +
                "action.</html>");
        label.setFont(cmu_serif_18);
        JOptionPane.showMessageDialog(null, label, "No Cards in Album",
                JOptionPane.INFORMATION_MESSAGE, sadPikachu);
    }

    /**
     * This method sizes the given array of buttons perfectly.
     * @param myButtons the array of JButtons to size
     * @param width the width of the frame
     * @param height the height of the frame
     * @param calculatedFont the font size to use
     */
    public void perfectlySizedButtons (yayJButton[] myButtons, int width, int height, int calculatedFont) {
        int divideBy = 3 * (myButtons.length) + 1;
        int buttonWidth = (int) Math.round((width * 3.0) / 4);
        int buttonHeight = (int) Math.round((height * 2.0) / divideBy);
        int xTranslation = (int) Math.round(width / 8.0);
        int yTranslation = (int) Math.round(height / (divideBy + 0.0));
        Font buttonFont = new Font("CMU Serif", Font.BOLD, calculatedFont);
        int yStep = (int) Math.round((height * 3.0) / divideBy);
        for (int i = 0; i < myButtons.length; i++) {
            myButtons[i].setBounds(xTranslation, yTranslation + yStep * i, buttonWidth, buttonHeight);
            myButtons[i].setFont(buttonFont);
        }
    }

    /**
     * This method sizes the display perfectly.
     * @param backButton the back button to size
     * @param width the width of the frame
     * @param height the height of the frame
     * @param calculatedFont the font size to use
     */
    public void perfectlySizedDisplay (yayJButton backButton, int width, int height, int calculatedFont) {

        int xCo = (int) Math.round(width / 8.0);
        int componentWidth = (int) Math.round((width * 3.0) / 4);

        int paneYCo = (int) Math.round((height * 1.0) / 10);
        int paneHeight = (int) Math.round((height * 6.0) / 10);

        int buttonYCo = (int) Math.round((height * 8.0) / 10);
        int buttonHeight = (int) Math.round((height * 1.0) / 10);

        backButton.setBounds(xCo, buttonYCo, componentWidth, buttonHeight);
        Font buttonFont = new Font("CMU Serif", Font.BOLD, calculatedFont);
        Font paneFont = new Font("CMU Serif", Font.PLAIN, (int) Math.round(calculatedFont -
                3.0 * (1.0 + 0.01 * calculatedFont)));
        backButton.setFont(buttonFont);
        mainDisplayScrollPane.setBounds(xCo, paneYCo, componentWidth, paneHeight);
        mainDisplayTextPane.setFont(paneFont);
    }

    /**
     * This method calculates the font size for the main menu.
     * @param area the area of the frame
     * @return the font size
     */
    public int mainMenuFont (double area) {
        return (int) Math.round(area / 12000.0);
    }

    /**
     * This method calculates the font size for the display.
     * @param area the area of the frame
     * @return the font size
     */
    public int displayFont (double area) {
        return (int) Math.round(area / 20000.0);
    }

    /**
     * This method calculates the font size for the first menu.
     * @param area the area of the frame
     * @return the font size
     */
    public int firstMenuFont (double area) {
        int font = (int) Math.round(Math.pow(area, 3) / (2.1678e16) + Math.pow(area, 2) / (-1.3351e10) +
                area / (25176.8) + (14.1098));
        if (font < 21) {
            return 21;
        } else return Math.min(font, 48);
    }

    /**
     * This method calculates the font size for the first menu.
     * @param area the area of the frame
     * @return the font size
     */
    public int secondMenuFont (double area) {
        int font = (int) Math.round(Math.pow(area, 3) / (2.1678e16) + Math.pow(area, 2) / (-1.3351e10) +
                area / (25176.8) + (14.1098));
        if (font < 21) {
            return 21;
        } else return Math.min(font, 48);
    }

    /**
     * This method prints out the statistics of the albums imported to a given JTextPane.
     * @param useThisTextPane the JTextPane to print the statistics to
     */
    public void printStatistics (JTextPane useThisTextPane) {
        useThisTextPane.setText("");
        for (Album album : albums) {
            appendString(album.albumStatistics() + "\n", useThisTextPane);
        }
        appendString(Album.collectionStatistics() + "\n", useThisTextPane);

    }

    /**
     * This method gets the text to put on a button in the chooseFromAList method.
     * @param title the title of the list, album, card, attack, or sort
     * @param i the index of the button
     * @param allInfo whether to get all the info or just the name and date
     * @return the text to put on the button
     */
    public String chooseHelperGetText (String title, int i, boolean allInfo) {
        switch (title) {
            case "album":
                if (allInfo) {
                    return albums.get(i).toString();
                } else {
                    return albums.get(i).nameDateToString();
                }
            case "card":
                return albums.get(albumIndexChosen).getCard(i).nameDateToString();
            case "attack":
                return albums.get(albumIndexChosen).getCard(cardIndexChosen).getAttack(i).toString();
            case "sort":
                return switch (i) {
                    case 0 -> "Sort By Name";
                    case 1 -> "Sort By HP";
                    case 2 -> "Sort By Date";
                    default -> "";
                };
        }
        return "";
    }

    /**
     * This method assigns the index of the chosen item in the chooseFromAList method.
     * @param title the title of the list, album, card, attack, or sort
     * @param i the index of the button
     */
    public void chooseHelperAssignI (String title, int i) {
        switch (title) {
            case "album" -> albumIndexChosen = i;
            case "card" -> cardIndexChosen = i;
            case "attack" -> attackIndexChosen = i;
            case "sort" -> sortIndexChosen = i;
        }
    }

    /**
     * This method gets the number of buttons to put in the chooseFromAList method.
     * @param title the title of the list, album, card, attack, or sort
     * @return the number of buttons to put in the chooseFromAList method
     */
    public int chooseHelperNumOfButtons (String title) {
        return switch (title) {
            case "album" -> albums.size();
            case "card" -> albums.get(albumIndexChosen).getCardsSize();
            case "attack" -> albums.get(albumIndexChosen).getCard(cardIndexChosen).getAttacksLength();
            case "sort" -> 3;
            default -> -1;
        };
    }

    /**
     * This method gets the rounding of the buttons in the chooseFromAList method.
     * @param title the title of the list, album, card, attack, or sort
     * @return the rounding of the buttons in the chooseFromAList method
     */
    public int chooseHelperButtonRounding (String title) {
        if (title.equals("sort")) {
            return 30;
        } else {
            return buttonRounding;
        }
    }

    /**
     * This method shows a dialog that allows the user to choose an album, card, attack, or sort.
     * @param buttonColor the color of the buttons
     * @param allData whether to get all the info or just the name and date
     * @param title the title of the list, album, card, attack, or sort
     * @param buttonHeight the height of the buttons
     */
    public void chooseFromAList (Color buttonColor, boolean allData, String title, int buttonHeight) {
        chooseHelperAssignI(title, -1);
        int buttonRounding = chooseHelperButtonRounding(title);
        int numOfButtons = chooseHelperNumOfButtons(title);
        JDialog chooseDialog = new JDialog(mainFrame, "Choose the " + title + " please", true);
        JPanel choosePanel = new JPanel();
        chooseDialog.setPreferredSize(new Dimension(400 + 19, 28 + Math.min(25 + (buttonHeight + 25) *
                numOfButtons, 700)));
        choosePanel.setPreferredSize(new Dimension(400, 21 + (buttonHeight + 25) * numOfButtons));
        choosePanel.setLayout(null);
        JScrollPane[] chooseScrollPanes = new JScrollPane[numOfButtons];
        yayJButton[] chooseButtons = new yayJButton[numOfButtons];
        String buttonText;

        for (int i = 0; i < chooseButtons.length; i++) { // loops through the buttons
            if (allData) {
                buttonText = chooseHelperGetText(title, i, true);
            } else {
                buttonText = chooseHelperGetText(title, i, false);
            }
            chooseButtons[i] = new yayJButton(buttonRounding, "<html>" + buttonText.replaceAll("\n",
                    "<br>") + "</html>") {
                @Override
                public Dimension getPreferredSize () {
                    Dimension size = super.getPreferredSize();
                    size.width = size.width - buttonRounding * 2;
                    size.height = size.height - buttonRounding * 2;
                    return size;
                }
            };
            setUpThisButton(chooseButtons[i], buttonRounding, buttonColor, cmu_serif_18);
            int finalI = i;
            chooseButtons[i].addActionListener(e -> {
                chooseHelperAssignI(title, finalI);
                chooseDialog.dispose();
                System.out.println(title + " index " + finalI + " chosen");
            });
            chooseButtons[i].setVisible(true);
            chooseScrollPanes[i] = new JScrollPane(chooseButtons[i]);
            chooseScrollPanes[i].setBounds(25, 25 + i * (25 + buttonHeight), 350, buttonHeight);
            chooseScrollPanes[i].setOpaque(false);
            chooseScrollPanes[i].getViewport().setOpaque(false);
            chooseScrollPanes[i].setBorder(BorderFactory.createEmptyBorder());
            choosePanel.add(chooseScrollPanes[i]);
        }

        // sets up the scroll pane
        JScrollPane chooseScrollPane = new JScrollPane(choosePanel);
        chooseScrollPane.setPreferredSize(new Dimension(400, 25 + (buttonHeight + 25) * numOfButtons));
        chooseScrollPane.setOpaque(false);
        chooseScrollPane.getViewport().setOpaque(false);
        chooseScrollPane.setBorder(BorderFactory.createEmptyBorder());
        chooseDialog.add(chooseScrollPane);
        chooseDialog.setLocationRelativeTo(null);
        chooseDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        chooseDialog.setResizable(false);
        chooseDialog.pack();
        chooseDialog.setVisible(true);
    }

    /**
     * This method parses a date from a given String.
     * @param date the String to parse the date from
     * @return an array of integers that represents the date
     */
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

    /**
     * This method reads a given file and parses it to read in the album.
     * @param fileNameEntered the name of the file to read
     * @return a String that represents the result of the import
     */
    public String readFile (String fileNameEntered) {
        try {
            BufferedReader inFile = new BufferedReader(new FileReader(fileNameEntered + ".txt"));
            int albumNum;
            if (duplicateAlbumNum(albumNum = Integer.parseInt(inFile.readLine().trim()))) {
                return "This is a duplicate album";
            }
            Date albumDate = new Date(parseDate(inFile.readLine().trim()));
            int maxCapacity = Integer.parseInt(inFile.readLine().trim());
            int cardsInAlbum = Integer.parseInt(inFile.readLine().trim());
            ArrayList <Card> cards = new ArrayList <>(cardsInAlbum);
            for (int i = 0; i < cardsInAlbum; i++) { // loops through the cards
                String name = inFile.readLine().trim();
                int HP = Integer.parseInt(inFile.readLine().trim());
                String type = inFile.readLine().trim();
                Date thisCardDate = new Date(parseDate(inFile.readLine().trim()));
                Attack[] attacks = new Attack[Integer.parseInt(inFile.readLine())];
                for (int j = 0; j < attacks.length; j++) {
                    // reads in the attack name, description, and damage
                    String attackNameDescription = inFile.readLine().trim();
                    int indexOfHyphen = attackNameDescription.indexOf('-');
                    String attackName, attackDescription;
                    if (indexOfHyphen == -1) { // no hyphen
                        attackName = attackNameDescription;
                        attackDescription = "";
                    } else { // there is a hyphen
                        attackName = attackNameDescription.substring(0, indexOfHyphen).trim();
                        attackDescription = attackNameDescription.substring(indexOfHyphen + 1).trim();
                    }
                    attacks[j] = (new Attack(attackName, attackDescription, inFile.readLine().trim()));
                }
                cards.add(new Card(name, HP, type, thisCardDate, attacks)); // adds the card to the album
            }
            albums.add(new Album(albumNum, cards, maxCapacity, albumDate)); // adds the album to the collection
            inFile.close();
        } catch (FileNotFoundException e) { // if the file is not found
            return "File Not Found";
        } catch (IOException e) { // if there is an error reading the file
            return "Reading Error";
        }
        return "Album import successful!";
    }

    /**
     * This method prints the name and date of all the albums to a given JTextPane.
     * @param useThisTextPane the JTextPane to print the name and date of all the albums to
     */
    public void printNameDateAllAlbums (JTextPane useThisTextPane) {
        useThisTextPane.setText("");
        for (int i = 0; i < albums.size() - 1; i++) {
            appendString(albums.get(i).nameDateToString() + "\n\n", useThisTextPane);
        }
        appendString(albums.get(albums.size() - 1).nameDateToString(), useThisTextPane);
    }

    /**
     * This method prints the data of all the cards in the given album to a given JTextPane.
     * @param useThisTextPane the JTextPane to print the data of all the cards in the given album to
     * @param currentAlbum the album to print the data of all the cards from
     * @param allData whether to print all the data or just the name and date
     */
    public void printCards (JTextPane useThisTextPane, Album currentAlbum, boolean allData) {
        useThisTextPane.setText("");
        appendString(allData ? currentAlbum.printAllInfoAllCards() : currentAlbum.printNameDateAllCards(), useThisTextPane);
    }

    /**
     * This method prints the data of all the attacks in the given card to a given JTextPane.
     */
    public void printAlbum () {
        mainDisplayTextPane.setText("");
        appendString(albums.get(albumIndexChosen).toString(), mainDisplayTextPane);
    }

    /**
     * This method prints all the data of one card in the given ArrayList to a given JTextPane.
     * @param cards the ArrayList of cards to print the data of one card from
     */
    public void printCard (ArrayList <Card> cards) {
        mainDisplayTextPane.setText("");
        appendString(cards.get(cardIndexChosen).toString(), mainDisplayTextPane);
    }

    /**
     * This method appends a given String to given JTextPane.
     * @param str the String to print
     * @param useThisTextPane the JTextPane to print the String to
     */
    public void appendString (String str, JTextPane useThisTextPane) {
        try {
            StyledDocument document = (StyledDocument) useThisTextPane.getDocument();
            document.insertString(document.getLength(), str, null);
        } catch (BadLocationException ignored) {
        }
    }

    /**
     * This method displays the main display pane
     * @param buttonsToHide the buttons to hide, album menu buttons or card menu buttons
     * @param backButtonToShow the back button to show, one which goes back to album or card menu
     * @param width the width of the frame
     * @param height the height of the frame
     */
    public void showTheTextPane (yayJButton[] buttonsToHide, int backButtonToShow, int width, int height) {
        atMenu = "d" + (backButtonToShow + 1);
        perfectlySizedDisplay(backButtons[backButtonToShow], width, height, displayFont(width * height));
        for (yayJButton jButton : buttonsToHide) {
            jButton.setVisible(false);
        }
        backButtons[backButtonToShow].setVisible(true);
        mainDisplayScrollPane.setVisible(true);
    }

    /**
     * This method imports an album from a given file.
     */
    public void importAlbum () {
        JDialog getFileNameDialog = new JDialog(mainFrame, "Import Album", true);
        JPanel getFileNamePanel = new JPanel();
        int componentWidth = (int) Math.round((600 * 6.0) / 8);
        int xCo = (int) Math.round((600 * 1.0) / 8);
        int splitIntoParts = (int) Math.round((300 * 1.0) / 9);
        getFileNameDialog.setPreferredSize(new Dimension(600, 300 + 28));
        getFileNamePanel.setPreferredSize(new Dimension(600, 300));
        getFileNamePanel.setLayout(null);

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

        // submit button
        yayJButton submitFileNameButton = new yayJButton(20, "Import Album");
        setUpThisButton(submitFileNameButton, 20, myGreen, cmu_serif_18);
        submitFileNameButton.addActionListener(e -> {
            String returnedString = readFile(fileNameField.getText().trim());
            if (!returnedString.equals("Album import successful!")) {
                errorLabel.setText(returnedString);
            } else {
                getFileNameDialog.dispose();
                System.out.println("album with file name \"" + fileNameField.getText().trim() + "\" imported");
            }

        });
        submitFileNameButton.setVisible(true);
        submitFileNameButton.setBounds(xCo, ((int) Math.round(splitIntoParts * 7.5)), componentWidth, splitIntoParts);

        // adds the components to the panel
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

    /**
     * This method removes an album from the collection
     */
    public void removeAlbum () {
        JDialog removeAlbumDialog = new JDialog(mainFrame, "Remove Album", true);
        JPanel removeAlbumPanel = new JPanel();
        removeAlbumPanel.setLayout(null);
        int width = 400;
        int height = 400;
        int componentWidth = (int) Math.round((width * 6.0) / 8);
        int xCo = (int) Math.round((width * 1.0) / 8);
        int splitIntoParts = (int) Math.round((height * 1.0) / 7.75);

        removeAlbumDialog.setPreferredSize(new Dimension(585, height + 28));
        removeAlbumPanel.setPreferredSize(new Dimension(585, height));

        JLabel label1 = new JLabel("Choose removal method:");
        label1.setBounds(xCo + 4, (int) Math.round(splitIntoParts * 0.25), componentWidth,
                (int) Math.round(splitIntoParts * 0.5));
        label1.setFont(cmu_serif_18);

        JRadioButton radio1 = new JRadioButton("By number");
        radio1.setBounds(xCo, splitIntoParts, componentWidth, (int) Math.round(splitIntoParts * 0.5));
        radio1.setFont(cmu_serif_18);

        JRadioButton radio2 = new JRadioButton("By date");
        radio2.setBounds(xCo, (int) Math.round(splitIntoParts * 1.75), componentWidth,
                (int) Math.round(splitIntoParts * 0.5));
        radio2.setFont(cmu_serif_18);

        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        group.add(radio2);

        JLabel label2 = new JLabel();
        label2.setBounds(xCo + 4, (int) Math.round(splitIntoParts * 2.75), componentWidth, splitIntoParts);
        label2.setFont(cmu_serif_18);
        label2.setVisible(false);

        // text field which will be used to get the album number or date
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
        errorPaneSetUp(errorPane);
        errorPane.setBounds(xCo, (int) Math.round(splitIntoParts * 5.25), componentWidth, splitIntoParts);

        yayJButton removeAlbumButton = new yayJButton(20, "Remove Albums");
        setUpThisButton(removeAlbumButton, 20, myGreen, cmu_serif_20);
        removeAlbumButton.addActionListener(e -> {
            String input = textField.getText().trim();
            if (radio1.isSelected()) { // number
                String result = removeAlbumNum(input);
                if (!result.equals("success")) {
                    errorPane.setText(result);
                } else {
                    removeAlbumDialog.dispose();
                    System.out.println("album removed by number");
                }
            } else if (radio2.isSelected()) { //date
                String result = removeAlbumDate(input);
                if (!result.equals("success")) {
                    errorPane.setText(result);
                } else {
                    removeAlbumDialog.dispose();
                    System.out.println("album removed by date");
                }
            }

        });
        removeAlbumButton.setVisible(false);
        removeAlbumButton.setBounds(xCo, (int) Math.round(splitIntoParts * 6.5), componentWidth, splitIntoParts);

        radio1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // if the user selects by number
                label2.setText("Enter album number:");
                label2.setVisible(true);
                textField.setEditable(true);
                textField.requestFocusInWindow();
                textField.setVisible(true);
                removeAlbumButton.setVisible(true);
                errorPane.setText("");
                EventQueue.invokeLater(textField::requestFocusInWindow);
            }
        });
        radio2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // if the user selects by date
                label2.setText("<html>Enter album date in MM/DD/YYYY format:</html>");
                label2.setVisible(true);
                textField.setEditable(true);
                textField.requestFocusInWindow();
                textField.setVisible(true);
                removeAlbumButton.setVisible(true);
                errorPane.setText("");
                EventQueue.invokeLater(textField::requestFocusInWindow);
            }
        });

        NoWrapJTextPane removeAlbumTextPane = new NoWrapJTextPane();
        removeAlbumTextPane.setFont(cmu_serif_18);
        removeAlbumTextPane.setEditable(false);
        JScrollPane displayScrollPane = new JScrollPane(removeAlbumTextPane);
        displayScrollPane.setBounds(xCo + componentWidth + 25, (int) Math.round(splitIntoParts * 0.25),
                585 - 375 - (int) Math.round(splitIntoParts * 0.25), (int) Math.round((height * 7.25) / 7.75));
        printNameDateAllAlbums(removeAlbumTextPane);
        displayScrollPane.setVisible(true);
        removeAlbumTextPane.setVisible(true);

        // adds the components to the panel
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

    /**
     * Removes an album from the collection by number
     * @param input the number of the album to remove
     * @return a String that represents the result of the removal
     */
    public String removeAlbumNum (String input) {
        String errorName = validInt(input, 1, Integer.MAX_VALUE);
        if (errorName.isEmpty()) { // success!, valid integer
            int validInt = (int) Double.parseDouble(input.trim());
            if (!duplicateAlbumNum(validInt)) { // not an imported album, but is an integer
                return "invalid album number";
            } else { // IS an imported album
                int index = albums.indexOf(new Album(validInt, new Date(new int[]{-1, -1, -1})));
                albums.get(index).removeAlbum();
                albums.remove(index);
                albums.trimToSize();
                return "success";
            }
        } else {
            return errorName;
        }
    }

    /**
     * Removes an album from the collection by date
     * @param input the date of the album to remove
     * @return a String that represents the result of the removal
     */
    public String removeAlbumDate (String input) {
        String validDate = validDate(input);
        if (validDate.isEmpty()) { // success!, valid date
            Date date = new Date(parseDate(input.trim()));
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
        } else {
            return validDate;
        }
    }

    /**
     * This method checks if the input String has a valid date.
     * @param input the String to check
     * @return a String that represents the result of the check
     */
    public String validDate (String input) {
        int[] parsedDate;
        input = input.trim().toLowerCase();
        System.out.println(input);
        if (input.isEmpty()) { //input length 0 chars
            return "ERROR! You did not provide a response.";
        }
        int firstSlash = input.indexOf('/');
        int lastSlash = input.lastIndexOf('/');
        if (firstSlash == -1) { // no slashes
            return "ERROR! Your input had no slashes.";
        } else if (firstSlash == lastSlash) { // only one slash
            return "ERROR! Your input only had one slash.";
        }
        parsedDate = parseDate(input);
        if (parsedDate.length == 0) { // invalid characters
            return "ERROR! Your input had invalid characters.";
        } else if (Date.invalidMonthDayYearTriplet(parsedDate)) {
            return "ERROR! Your date was invalid.";
        }
        return "";
    }

    /**
     * This method checks if the given there exists an album with the given number.
     * @param albumNum the album number to check
     * @return whether the album number is a duplicate
     */
    public boolean duplicateAlbumNum (int albumNum) {
        return albums.contains(new Album(albumNum, new Date(new int[]{-1, -1, -1})));
    }

    /**
     * This method checks if the given there exists an album with the given date.
     * @param date the album date to check
     * @param albums the ArrayList of albums to check
     * @return whether the album date is a duplicate
     */
    public boolean duplicateAlbumDate (Date date, ArrayList <Album> albums) {
        return albums.contains(new Album(-1, date));
    }

    /**
     * This method allows the rounding of the borders of a component
     * @param radius the radius of the border
     * @param thickness the thickness of the border
     */
    private record RoundedBorder(int radius, int thickness) implements Border {

        public Insets getBorderInsets (Component c) {
            return new Insets(this.radius + thickness, this.radius + thickness,
                    this.radius + thickness, this.radius + thickness);
        }

        public boolean isBorderOpaque () {
            return true;
        }

        public void paintBorder (Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK); // This will set the border color to red
            g2.setStroke(new BasicStroke(this.thickness));
            g2.drawRoundRect(x + thickness / 2, y + thickness / 2, width - thickness,
                    height - thickness, radius, radius);
        }
    }

    // This class is used to make the JTextPane not wrap lines
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

    // this class is used to make the buttons rounded
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
}