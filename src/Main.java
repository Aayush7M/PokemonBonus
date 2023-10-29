import javax.swing.*;
import javax.swing.border.Border;
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

public class Main implements ActionListener {
    JPanel panel;
    JFrame frame;
    JButton[] mainMenu, subMenuOne, subMenuTwo, backButtons;
    String atMenu = "m0";
    NoWrapJTextPane textPane;
    JScrollPane scrollPane;
    ArrayList <Album> albums = new ArrayList <>();
    int albumIndexChosen = -1;
    Color myGreen = new Color(114, 170, 85);
    Color myBlue = new Color(0, 141, 213);

    ImageIcon sadPikachu = new ImageIcon("sad pikachu.png");
    ImageIcon happyPikachu = new ImageIcon("happy pikachu.png");
    Font cmu_serif_20 = new Font("CMU Serif", Font.PLAIN, 20);
    Font cmu_serif_18 = new Font("CMU Serif", Font.PLAIN, 18);
    Font cmu_serif_40_bold = new Font("CMU Serif", Font.BOLD, 40);
    Font cmu_serif_14_bold = new Font("CMU Serif", Font.BOLD, 14);

    public Main () {
        //            readFile(new BufferedReader(new FileReader("1.txt")), albums);
        readFile("2", albums);
//            readFile(new BufferedReader(new FileReader("3.txt")), albums);
        readFile("4", albums);
        readFile("5", albums);
        panel = new JPanel();
        frame = new JFrame();
        int width = 750;
        int height = 550;
        int buttonRounding = 50;
        int buttonBorder = 1;

        ImageIcon appIcon = new ImageIcon("app icon.png");

        frame.setSize(width, height + 28);
        frame.setTitle("Pokemon Collection");
        frame.setIconImage(appIcon.getImage());
        panel.setSize(width, height);
        panel.setLayout(null);

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
        mainMenu = new JButton[2];
        for (int i = 0; i < mainMenu.length; i++) {
            mainMenu[i] = new JButton(mainMenuText[i]) {
                // This paintComponent method fills the button with a round rectangle.
                @Override
                protected void paintComponent (Graphics g) {
                    // Use antialiasing for smoother edges
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    // Set the color and fill the round rectangle
                    g2.setColor(getBackground());

                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), buttonRounding, buttonRounding));
                    // Call the super method to draw the text
                    super.paintComponent(g);
                }
            };
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
                }

                public void mouseExited (MouseEvent pABtn) {
                    mainMenu[finalI].setBackground(colorsInOrder[finalI]);
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
        subMenuOne = new JButton[6];
        for (int i = 0; i < subMenuOne.length; i++) {
            subMenuOne[i] = new JButton(subMenuOneText[i]) {
                // This paintComponent method fills the button with a round rectangle.
                @Override
                protected void paintComponent (Graphics g) {
                    // Use antialiasing for smoother edges
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    // Set the color and fill the round rectangle
                    g2.setColor(getBackground());

                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), buttonRounding, buttonRounding));
                    // Call the super method to draw the text
                    super.paintComponent(g);
                }
            };
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
        subMenuTwo = new JButton[7];
        for (int i = 0; i < subMenuTwo.length; i++) {
            subMenuTwo[i] = new JButton(subMenuTwoText[i]) {
                // This paintComponent method fills the button with a round rectangle.
                @Override
                protected void paintComponent (Graphics g) {
                    // Use antialiasing for smoother edges
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    // Set the color and fill the round rectangle
                    g2.setColor(getBackground());

                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), buttonRounding, buttonRounding));
                    // Call the super method to draw the text
                    super.paintComponent(g);
                }
            };
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

        backButtons = new JButton[2];
        for (int i = 0; i < backButtons.length; i++) {
            backButtons[i] = new JButton("Go Back") {
                // This paintComponent method fills the button with a round rectangle.
                @Override
                protected void paintComponent (Graphics g) {
                    // Use antialiasing for smoother edges
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    // Set the color and fill the round rectangle
                    g2.setColor(getBackground());

                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), buttonRounding, buttonRounding));
                    // Call the super method to draw the text
                    super.paintComponent(g);
                }
            };
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
        textPane = new NoWrapJTextPane();
        textPane.setFont(cmu_serif_14_bold);
        textPane.setEditable(false);
        scrollPane = new JScrollPane(textPane);
        scrollPane.setVisible(false);

        //------------------------------------FRAME RESIZED COMPONENT LISTENER ---------------------------------------------
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized (ComponentEvent e) {
                int width = panel.getWidth();
                int height = panel.getHeight();
                panel.setSize(width, height);
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
        for (JButton jButton : mainMenu) {
            panel.add(jButton);
        }
        for (JButton jButton : subMenuOne) {
            panel.add(jButton);
        }
        for (JButton jButton : subMenuTwo) {
            panel.add(jButton);
        }
        for (JButton jButton : backButtons) {
            panel.add(jButton);
        }
        panel.add(scrollPane);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(width, height + 28));
        frame.setMaximumSize(new Dimension(1512, 916 + 28));


        frame.pack();
        frame.setVisible(true);
    }

    public static void main (String[] args) {
        new Main();
    }

    public void actionPerformed (ActionEvent e) {
        String eventName = e.getActionCommand();
        switch (eventName) {
            case "m0o1":
                atMenu = "m1";
                System.out.println("album menu");
                for (JButton jButton : mainMenu) {
                    jButton.setVisible(false);
                }
                perfectlySizedButtons(subMenuOne, panel.getWidth(), panel.getHeight(), firstMenuFont(panel.getWidth() * panel.getHeight()));
                for (JButton jButton : subMenuOne) {
                    jButton.setVisible(true);
                }
                break;
            case "m0o2":
                atMenu = "m2";
                System.out.println("card menu");
                chooseAlbum(myBlue, true);
                if (albumIndexChosen != -1) {
                    for (JButton jButton : mainMenu) {
                        jButton.setVisible(false);
                    }
                    perfectlySizedButtons(subMenuTwo, panel.getWidth(), panel.getHeight(), secondMenuFont(panel.getWidth() * panel.getHeight()));
                    for (JButton jButton : subMenuTwo) {
                        jButton.setVisible(true);
                    }
                }
                break;
            case "m1o1", "m1o2", "m1o3", "m1o4", "m1o5":
                int width = panel.getWidth();
                int height = panel.getHeight();
                int choice = Integer.parseInt(eventName.substring(3));
                int numOfAlbumsImported = albums.size();
                if (choice != 3) { // user does not want to add cards
                    if (numOfAlbumsImported == 0) { // if no albums are imported
                        choice = 6;
                    } else if (numOfAlbumsImported == 1) { // if only one album imported
                        if (choice != 1 && choice != 5) { // user doesn't want to print all albums or add cards
                            JLabel label = new JLabel("<html> Only one album imported, <br> so that album has been <br> automatically chosen. </html>");
                            label.setFont(cmu_serif_18);
                            JOptionPane.showMessageDialog(null, label, "One Album imported", JOptionPane.INFORMATION_MESSAGE, happyPikachu);

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
                        printNameDateAllAlbums(textPane);
                        break;
                    case 2: // Display info on a particular album
                        System.out.println("m1o2 picked");
                        chooseAlbum(myGreen, false);
                        if (albumIndexChosen != -1) {
                            showTheTextPane(subMenuOne, 0, width, height);
                            printAlbum();
                            albumIndexChosen = -1;
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
                        printStatistics(textPane);
                        break;
                    case 6:
                        System.out.println("m1 but !o3. 0 albums imported");
                        JLabel label = new JLabel("<html> There are no albums <br> imported. Please import an <br> album to do this action.</html>");
                        label.setFont(cmu_serif_18);
                        JOptionPane.showMessageDialog(null, label, "No Albums imported", JOptionPane.INFORMATION_MESSAGE, sadPikachu);
                        break;
                    case 7:
                        System.out.println("m1o2. 1 album imported");
                        albumIndexChosen = 0;
                        showTheTextPane(subMenuOne, 0, width, height);
                        printAlbum();
                        albumIndexChosen = -1;
                        break;
                    case 8:
                        System.out.println("m1o4. 1 album imported");
                        albums.get(0).removeAlbum();
                        albums.remove(0);
                }
                break;
            case "m2o1", "m2o2", "m2o3", "m2o4", "m2o5", "m2o6":
                break;
            case "b1":
                atMenu = "m1";
                System.out.println("back to album menu");
                backButtons[0].setVisible(false);
                scrollPane.setVisible(false);
                perfectlySizedButtons(subMenuOne, panel.getWidth(), panel.getHeight(), firstMenuFont(panel.getWidth() * panel.getHeight()));
                for (JButton jButton : subMenuOne) {
                    jButton.setVisible(true);
                }
                break;
            case "b2":
                atMenu = "m2";
                System.out.println("back to card menu");
                backButtons[1].setVisible(false);
                scrollPane.setVisible(false);
                perfectlySizedButtons(subMenuTwo, panel.getWidth(), panel.getHeight(), secondMenuFont(panel.getWidth() * panel.getHeight()));
                for (JButton jButton : subMenuTwo) {
                    jButton.setVisible(true);
                }
                break;
            case "m1o6":
                atMenu = "m0";
                System.out.println("m1o6 picked");
                System.out.println("back to main menu");
                for (JButton jButton : subMenuOne) {
                    jButton.setVisible(false);
                }
                perfectlySizedButtons(mainMenu, panel.getWidth(), panel.getHeight(), mainMenuFont(panel.getWidth() * panel.getHeight()));
                for (JButton jButton : mainMenu) {
                    jButton.setVisible(true);
                }
                break;
            case "m2o7":
                atMenu = "m0";
                System.out.println("m2o7 picked");
                System.out.println("back to main menu");
                for (JButton jButton : subMenuTwo) {
                    jButton.setVisible(false);
                }
                perfectlySizedButtons(mainMenu, panel.getWidth(), panel.getHeight(), mainMenuFont(panel.getWidth() * panel.getHeight()));
                for (JButton jButton : mainMenu) {
                    jButton.setVisible(true);
                }
                albumIndexChosen = -1;
        }
    }

    public void perfectlySizedButtons (JButton[] myButtons, int width, int height, int calculatedFont) {
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

    public void perfectlySizedDisplay (JButton backButton, int width, int height, int calculatedFont) {
        int xCo = (int) Math.round(width / 8.0);
        int componentWidth = (int) Math.round((width * 3.0) / 4);

        int paneYCo = (int) Math.round((height * 1.0) / 10);
        int paneHeight = (int) Math.round((height * 6.0) / 10);

        int buttonYCo = (int) Math.round((height * 8.0) / 10);
        int buttonHeight = (int) Math.round((height * 1.0) / 10);

        Font buttonFont = new Font("CMU Serif", Font.BOLD, calculatedFont);
        backButton.setBounds(xCo, buttonYCo, componentWidth, buttonHeight);
        backButton.setFont(buttonFont);
        scrollPane.setBounds(xCo, paneYCo, componentWidth, paneHeight);
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

        JDialog chooseAlbumDialog = new JDialog(frame, "Choose the album you want to know more about", true);
        JPanel chooseAlbumPanel = new JPanel();
        chooseAlbumDialog.setPreferredSize(new Dimension(400 + 19, 700 + 28 + 20));
        chooseAlbumPanel.setPreferredSize(new Dimension(400, 700));
        chooseAlbumPanel.setLayout(null);
        int BUTTONRounding = 50;
        int BUTTONBorder = 1;
        JButton[] pickAlbumButtons = new JButton[albums.size()];
        String buttonText;
        for (int i = 0; i < pickAlbumButtons.length; i++) {
            if (allData) {
                buttonText = albums.get(i).toString();
            } else {
                buttonText = albums.get(i).nameDateToString();
            }
            pickAlbumButtons[i] = new JButton("<html>" + buttonText.replaceAll("\n", "<br>") + "</html>") {
                // This paintComponent method fills the button with a round rectangle.
                @Override
                protected void paintComponent (Graphics g) {
                    // Use antialiasing for smoother edges
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    // Set the color and fill the round rectangle
                    g2.setColor(getBackground());

                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), BUTTONRounding, BUTTONRounding));
                    // Call the super method to draw the text
                    super.paintComponent(g);
                }
            };
            pickAlbumButtons[i].setFont(cmu_serif_40_bold);
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
            pickAlbumButtons[i].setBounds(25, 25 + i * 125, 350, 100);
        }

        for (JButton jButton : pickAlbumButtons) {
            chooseAlbumPanel.add(jButton);
        }
        JScrollPane chooseAlbumScrollPane = new JScrollPane(chooseAlbumPanel);
        chooseAlbumDialog.add(chooseAlbumScrollPane);
        chooseAlbumDialog.setLocationRelativeTo(null);
        chooseAlbumDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        chooseAlbumDialog.setResizable(false);
        chooseAlbumDialog.pack();
        chooseAlbumDialog.setVisible(true);
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

    public void printAlbum () {
        textPane.setText("");
        try {
            appendString(albums.get(albumIndexChosen) + "\n", textPane);
        } catch (BadLocationException ignored) {
        }
        albumIndexChosen = -1;
    }

    public void appendString (String str, JTextPane useThisTextPane) throws BadLocationException {
        StyledDocument document = (StyledDocument) useThisTextPane.getDocument();
        document.insertString(document.getLength(), str, null);
    }

    public void showTheTextPane (JButton[] buttonsToHide, int backButtonToShow, int width, int height) {
        atMenu = "d" + (backButtonToShow + 1);
        perfectlySizedDisplay(backButtons[backButtonToShow], width, height, displayFont(width * height));
        for (JButton jButton : buttonsToHide) {
            jButton.setVisible(false);
        }
        backButtons[backButtonToShow].setVisible(true);
        scrollPane.setVisible(true);
    }

    public void importAlbum () {
        JDialog getFileNameDialog = new JDialog(frame, "Import Album", true);
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
        textPane.setBackground(new Color(236, 236, 236));
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

        JButton submitFileNameButton = new JButton("Import Album") {
            // This paintComponent method fills the button with a round rectangle.
            @Override
            protected void paintComponent (Graphics g) {
                // Use antialiasing for smoother edges
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Set the color and fill the round rectangle
                g2.setColor(getBackground());

                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), BUTTONRounding, BUTTONRounding));
                // Call the super method to draw the text
                super.paintComponent(g);
            }
        };
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
        JDialog removeAlbumDialog = new JDialog(frame, "Import Album", true);
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

        JButton submitFileNameButton = new JButton("Remove Albums") {
            // This paintComponent method fills the button with a round rectangle.
            @Override
            protected void paintComponent (Graphics g) {
                // Use antialiasing for smoother edges
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Set the color and fill the round rectangle
                g2.setColor(getBackground());

                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                // Call the super method to draw the text
                super.paintComponent(g);
            }
        };
        submitFileNameButton.setFont(cmu_serif_20);
        submitFileNameButton.setBackground(myGreen);
        submitFileNameButton.setFocusable(false);
        submitFileNameButton.setOpaque(false); // Make the button transparent
        submitFileNameButton.setBorder(new RoundedBorder(20, 1)); // Set the border as before
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
        submitFileNameButton.setVisible(false);
        submitFileNameButton.setBounds(xCo, (int) Math.round(splitIntoParts * 6.5), componentWidth, splitIntoParts);

        radio1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                label2.setText("Enter album number:");
                label2.setVisible(true);
                textField.setEditable(true);
                textField.requestFocusInWindow();
                textField.setVisible(true);
                submitFileNameButton.setVisible(true);
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
                submitFileNameButton.setVisible(true);
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
        removeAlbumPanel.add(submitFileNameButton);
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
}