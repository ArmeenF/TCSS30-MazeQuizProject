package main;

import controller.Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;

import model.Model;
import view.View;

/**
 * The main frame shown for the Trivia Maze Game.
 *
 * @author Armeen Farange
 * @author Joseph Graves
 * @version Fall 2021
 */
public class TriviaMazeFrame extends JFrame {

    /**
     * The name of the save file.
     */
    public static final String SAVE_FILE = "save.ser";

    /**
     * The width of the main window.
     */
    public static final int MAIN_WIDTH = 1200;

    /**
     * The height of the main window.
     */
    public static final int MAIN_HEIGHT = 950;

    /**
     * The width of the view in the main window.
     */
    public static final int VIEW_WIDTH = 800;

    /**
     * The height of the view in the main window.
     */
    public static final int VIEW_HEIGHT = 900;

    /**
     * The width of the controller in the main window.
     */
    public static final int CONTROLLER_WIDTH = 400;

    /**
     * The height of the controller in the main window.
     */
    public static final int CONTROLLER_HEIGHT = 900;

    /**
     * Contains how to play information.
     */
    private JFrame myHelpFrame;

    /**
     * A reference to the model.
     */
    private Model myModel;

    /**
     * The View panel, displays the view.
     */
    private View myView;

    /**
     * The controller panel, allows the player to play.
     */
    private Controller myController;

    /**
     * Private constructor to force passing a model reference.
     */
    private TriviaMazeFrame() {
        //Does nothing..
    }

    /**
     * Constructs a new TriviaMazeFrame, given a model.
     *
     * @param theModel the model for the game.
     */
    public TriviaMazeFrame(final Model theModel) {
        super();
        myModel = theModel;
        myView = new View(myModel);
        myController = new Controller(myModel);
        layoutComponents();
        startUpMessage();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Lays out the components for the main frame.
     */
    private void layoutComponents() {
        this.setTitle("Joseph & Armeen's Trivia Maze Game");
        setUpMenuBar();
        setBounds(0, 0, MAIN_WIDTH, MAIN_HEIGHT);
        final JPanel pane = new JPanel();
        setContentPane(pane);
        pane.setLayout(null);
        myView.setBounds(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        pane.add(myView);
        myController.setBounds(VIEW_WIDTH, 0, CONTROLLER_WIDTH, CONTROLLER_HEIGHT);
        pane.add(myController);
        this.setVisible(true);
    }

    /**
     * Shows gameplay instructions during startup.
     */
    private void startUpMessage() {
        final String help = "<html>We'd like to welcome you to our trivia maze game! The objective<br>" + " " +
                "is to get from the starting point to the finishing point. You may<br>" + " choose the direction " +
                "to go in by using the directional arrows. After<br> " + "you've decided on a direction, you'll " +
                "be asked a trivia question. If you<br>" + " answer right, you will go in the intended direction; " +
                "if you answer incorrectly,<br> " + "the chosen direction will be locked, and you will have to " +
                "choose another direction.<br> " + "You will lose the game if you do not correctly answer all of" +
                " the directional trivia questions.<br> " + "Click Help then Gameplay Instructions for additional" +
                " information. Best of luck!</html>";
        final JLabel message = new JLabel(help);
        final ImageIcon iconImage = new ImageIcon("E_rtg.png");
        final JLabel iconRating = new JLabel(iconImage, SwingConstants.LEFT);
        iconRating.setVerticalAlignment(SwingConstants.BOTTOM);
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setVerticalAlignment(SwingConstants.CENTER);
        final JButton button = new JButton("Okay");
        final JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(button, BorderLayout.LINE_END);
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.add(iconRating);
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(message);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
        myHelpFrame = new JFrame("Welcome");
        myHelpFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        myHelpFrame.getContentPane().add(mainPanel);
        myHelpFrame.setSize(600, 650);
        myHelpFrame.setLayout(new GridLayout());
        myHelpFrame.pack();
        myHelpFrame.setLocationRelativeTo(null);
        myHelpFrame.setVisible(true);
        button.addActionListener(e -> myHelpFrame.setVisible(false));
    }

    private void setUpMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        menuBar.add(setUpFileMenu());
        menuBar.add(setUpHelpMenu());
    }

    /**
     * Creates and adds components to the file menu.
     *
     * @return a file menu.
     */
    private JMenu setUpFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.addActionListener(e -> myModel.setUpNewGame());
        final JMenuItem saveGameMenuItem = new JMenuItem("Save Game");
        saveGameMenuItem.addActionListener(e -> saveGame());
        final JMenuItem loadGameMenuItem = new JMenuItem("Load Game");
        loadGameMenuItem.addActionListener(e -> loadGame());
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> this.dispose());
        fileMenu.add(saveGameMenuItem);
        fileMenu.add(loadGameMenuItem);
        fileMenu.add(newGameMenuItem);
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    /**
     * Creates and adds components to the help menu.
     *
     * @return a help menu.
     */
    private JMenu setUpHelpMenu() {
        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem aboutMenuItem = new JMenuItem("About");
        final JMenuItem howToPlayMenuItem = new JMenuItem("Game Play instructions");
        final JMenuItem cheatMenuItem = new JMenuItem("Cheats");
        final String about = "This is Trivia Maze, produced for TCSS360 B," + "made by Armeen Farange and Joseph " +
                "Graves in 2021.";
        aboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(null, about));
        howToPlayMenuItem.addActionListener(e -> myHelpFrame.setVisible(true));
        cheatMenuItem.addActionListener(e -> cheatMenuLayout());
        helpMenu.add(aboutMenuItem);
        helpMenu.add(howToPlayMenuItem);
        helpMenu.add(cheatMenuItem);
        return helpMenu;
    }

    /**
     * Cheat menu layout.
     */
    private void cheatMenuLayout() {
        final JFrame f2 = new JFrame("Cheat menu");
        final JPanel p = new JPanel();
        final JMenuBar mb = new JMenuBar();
        final JButton c1 = new JButton("Answers to All Trivia Questions");
        final JButton c2 = new JButton(" include a cheat that shows perhaps a cheat that allows you to " +
                "skip a room/door");
        c1.setBorder(BorderFactory.createBevelBorder(1, Color.red, Color.blue));
        c2.setBorder(BorderFactory.createBevelBorder(1, Color.red, Color.blue));
        f2.add(p);
        p.add(c1);
        p.add(c2);
        p.setLayout(new GridLayout(0, 1));
        f2.setJMenuBar(mb);
        f2.setSize(600, 250);
        f2.setVisible(true);
        f2.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        c1.addActionListener(e -> cheatMenuAnswers());
        c2.addActionListener(e -> //unlock all vertices
                JOptionPane.showMessageDialog(null, "Action to be performed"));
    }

    /**
     * Cheat menu answers.
     */
    private void cheatMenuAnswers() {
        final JLabel message = new JLabel("<html><ul>" + "<li>What was the first commercially successful video " +
                "game?	Pong</li>" + "<li>What is the best selling videogame of all time?	Minecraft</li>" + "<li>" +
                "What is the highest-selling gaming console to date?	Playsation2</li>" + "<li>What product did " +
                "Nintendo first release before taking on the world of video games?	Playing " + "Cards</li>" + "<li>" +
                "What food was the character Pac Man modeled after?	Pizza</li>" + "<li>What is the best-selling " +
                "handheld gaming system to date?	Nintendo DS</li>" + "<li>What was the first video game character " +
                "to have a balloon featured in the Macy’s Thanksgiving " + "Day Parade?	Sonic the Hedgehog</li>" +
                "<li>What is the most expensive video game made to date?	Grand Theft Auto 5</li>" + "<li>What " +
                "year was the first virtual reality headset created?	1995</li>" + "<li>What crowdsource funding " +
                "platform is responsible for the success of the Oculus Rift?	" + "Kickstarter</li>" + "<li>Who " +
                "released the first flight simulator game?	Microsoft</li>" + "<li>What popular dining franchise is " +
                "the founder of Atari also responsible for?	" + "Chuck E Cheese</li>" + "<li>What position did the " +
                "creator of the Game Boy have at Nintendo?	Janitor</li>" + "<li>Mario first appeared in what video" +
                " game?	Donkey Kong</li>" + "<li>What is the name of Mario’s dinosaur sidekick?	Yoshi</li>" +
                "<li>What are the paintings in Minecraft modeled after?	Counterstrike maps</li>" + "<li>What " +
                "is the name of the first video game to be played in outer space?	StarCraft</li>" + "<li>What" +
                " video game franchise has racked up over 1 Billion dollars in lawsuits?	" + "Grand Theft " +
                "Auto</li>" + "<li>What is the average age of gamers in the United States?	35</li>" + "<li>Who " +
                "is on the cover of EA Sport's \"FIFA 19\"?	Cristiano Ronaldo</li>" + "<li>In what year was " +
                "Atari Inc. founded?	1972</li>" + "<li>What was Atari’s first arcade game that they launched" +
                " with?	Pong</li>" + "<li>What company developed and released Pac Man for the arcades in 1980?	" +
                "Namco</li>" + "<li>What did SONY end up creating as a result of their ended relationship with " +
                "Nintendo?	" + "PlayStation</li>" + "<li>What was the first video game console to be offered " +
                "by an American company since Atari?	" + "XBOX</li>" + "<li>What was the first mobile phone " +
                "game?	Tetris</li>" + "<li>What game did the computer  \"Bertie the Brain\"  play against you? " +
                "In what is considered to " + "be, one of the earliest video games.	Tic-Tac-Toe</li>" + "<li>Who " +
                "was the first independent, third-party, console video game developer?	Activision</li>" +
                "<li>What American video game company was launched by former Apple employee Trip Hawkins in 1982?	" +
                "" + "Electronic Arts</li>" + "<li>The SEGA Saturn was the first gaming console to have internal " +
                "memory 	T</li>" + "<li>Australia refused to provide a rating for Fallout 3 due to Morphine use " +
                "in the game	T</li>" + "<li>Halo was used by Rooster Teeth for their show Red vs. Blue	T</li>"
                + "<li>In Burnout Paradise the Obama campaign paid for in-game advertising?	T</li>" + "<li>Detective " +
                "Pikachu (Pokémon) is the highest-grossing movie based on a video game	T</li>" + "<li>6 - 10 hours " +
                "is the average amount of time a player of Fortnite enjoys the game weekly	T</li>" + "<li>Jordan" +
                " vs. Bird is the first videogame to feature basketball legend Michael Jordan	T</li>" + "<li>" +
                "Ryan Reynolds provided the voice of Pikachu in the 2019 Pokémon detective Pikachu movie 	" +
                "" + "T</li>" + "<li>00 players can compete against each other simultaneously in a Fortnite " +
                "battle royale	T</li>" + "<li>Tennis for Two is the name of the first ever video game	T</li>"
                + "<li>Codemasters developed, mastered, and published the 2015 video game F1 2015	T</li>" + "" +
                "<li>The United States generates the most revenue in the video game industry	F</li>" + "<li>" +
                "Naboo is the name of the planet that is home to the game Gears of War	F</li>" + "<li>YouTube " +
                "Gaming is popular streaming service launched in June 2011, to allows content creators" + " to " +
                "stream video games while chatting with viewers	F</li>" + "<li>Wii sports is taken so seriously" +
                " in South Korea it is officially recognized as an e-Sport?	" + "F</li>" + "<li>The Star Wars " +
                "movie franchise influenced the creation of the game Doom	F</li>" + "<li>Princess Peach " +
                "supposedly stripped down to her birthday suit if players entered the correct " + "button " +
                "code?	F</li>" + "<li>The cold War is the date setting of the original call of duty	F</li>" +
                "<li>The Plasma display system was utilized by the Nintendo game boy	F</li>" + "<li>Master " +
                "chief is the iconic videogame character originated as a simple place holder	F</li>" + "<li>" +
                "Green is the color the ghost enemies turn to once Pac man eats a power pellet	F</li>" + "<li>" +
                "Bowling is the sport featured in the video game \"Tecmo Bowl\"	F</li>" + "<li>The video game " +
                "happy feet features the animal zebra	F</li>" + "</ul><html>");

        final ImageIcon iconImage = new ImageIcon("E_rtg.png");
        final JLabel iconRating = new JLabel(iconImage, SwingConstants.LEFT);
        iconRating.setVerticalAlignment(SwingConstants.BOTTOM);
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setVerticalAlignment(SwingConstants.CENTER);
        final JButton button = new JButton("Okay");
        final JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(button, BorderLayout.LINE_END);
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.add(iconRating);
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(message);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
        myHelpFrame = new JFrame();
        myHelpFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        myHelpFrame.getContentPane().add(mainPanel);
        myHelpFrame.setSize(600, 650);
        myHelpFrame.setLayout(new GridLayout());
        myHelpFrame.pack();
        myHelpFrame.setLocationRelativeTo(null);
        myHelpFrame.setVisible(true);
        button.addActionListener(e -> myHelpFrame.setVisible(false));
    }

    /**
     * Saves the gamestate to SAVE_FILE.
     */
    private void saveGame() {
        try (FileOutputStream file = new FileOutputStream(SAVE_FILE); ObjectOutputStream output = new ObjectOutputStream(file)) {
            myModel.removePropertyChangeListener(myView);
            output.writeObject(myModel);
            myModel.addPropertyChangeListener(myView);
        } catch (final IOException exception) {
            showError(exception);
        }
    }

    /**
     * Loads the gamestate from SAVE_FILE.
     */
    private void loadGame() {
        try (FileInputStream file = new FileInputStream(SAVE_FILE); ObjectInputStream input = new ObjectInputStream(file)) {
            updateModel((Model) input.readObject());
        } catch (final IOException | ClassNotFoundException exception) {
            showError(exception);
        }
    }

    /**
     * Updates the model reference for all three GUI elements.
     *
     * @param theModel the model to update with.
     */
    private void updateModel(final Model theModel) {
        myModel = theModel;
        myView.updateModel(theModel);
        myController.updateModel(theModel);
    }

    /**
     * Shows an exception to the user using a Message Dialog.
     *
     * @param theException the exception to be shown.
     */
    private void showError(final Exception theException) {
        JOptionPane.showMessageDialog(null, theException.getMessage(), theException.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
        theException.printStackTrace();
    }
}
