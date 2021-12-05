package main;

import controller.Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import model.Model;
import view.View;

/**
 * The main frame shown for the Trivia Maze Game.
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
        final String help = "<html>We'd like to welcome you to our trivia maze game! The objective<br>"
                + " is to get from the starting point to the finishing point. You may<br>"
                + " choose the direction to go in by using the directional arrows. After<br> "
                + "you've decided on a direction, you'll be asked a trivia question. If you<br>"
                + " answer right, you will go in the intended direction; if you answer incorrectly,<br> "
                + "the chosen direction will be locked, and you will have to choose another direction.<br> "
                + "You will lose the game if you do not correctly answer all of the directional trivia questions.<br> "
                + "Click Help then Gameplay Instructions for additional information. Best of luck!</html>";
        final JLabel message = new JLabel(help);
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setVerticalAlignment(SwingConstants.CENTER);
        final JButton button = new JButton("Okay");
        final JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(button, BorderLayout.LINE_END);
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(message);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
        myHelpFrame = new JFrame();
        myHelpFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        myHelpFrame.getContentPane().add(mainPanel);
        myHelpFrame.setSize(600, 450);
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
     * @return a help menu.
     */
    private JMenu setUpHelpMenu() {
        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem aboutMenuItem = new JMenuItem("About");
        final JMenuItem howToPlayMenuItem = new JMenuItem("Game Play instructions");
        final JMenuItem cheatMenuItem = new JMenuItem("Cheats");
        final String about = "This is Trivia Maze, produced for TCSS360 B,"
                + "made by Armeen Farange and Joseph Graves in 2021.";
        final JLabel cheats = new JLabel("<html><ul><li>Cheat 1</li><li>Cheat 2</li>"
                + "<li>Cheat 3</li></ul><html>"); //TODO This can't just be a JOptionPane..
        aboutMenuItem.addActionListener(e ->
                JOptionPane.showMessageDialog(null, about));
        howToPlayMenuItem.addActionListener(e -> myHelpFrame.setVisible(true));
        cheatMenuItem.addActionListener(e ->
                JOptionPane.showMessageDialog(null, cheats));
        helpMenu.add(aboutMenuItem);
        helpMenu.add(howToPlayMenuItem);
        helpMenu.add(cheatMenuItem);
        return helpMenu;
    }

    /**
     * Saves the gamestate to SAVE_FILE.
     */
    private void saveGame() {
        try (FileOutputStream file = new FileOutputStream(SAVE_FILE);
             ObjectOutputStream output = new ObjectOutputStream(file)) {
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
        try (FileInputStream file = new FileInputStream(SAVE_FILE);
             ObjectInputStream input = new ObjectInputStream(file)) {
            updateModel((Model) input.readObject());
        } catch (final IOException | ClassNotFoundException exception) {
            showError(exception);
        }
    }

    /**
     * Updates the model reference for all three GUI elements.
     * @param theModel the model to update with.
     */
    private void updateModel(final Model theModel) {
        myModel = theModel;
        myView.updateModel(theModel);
        myController.updateModel(theModel);
    }

    /**
     * Shows an exception to the user using a Message Dialog.
     * @param theException the exception to be shown.
     */
    private void showError(final Exception theException) {
        JOptionPane.showMessageDialog(null, theException.getMessage(),
                theException.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
        theException.printStackTrace();
    }
}
