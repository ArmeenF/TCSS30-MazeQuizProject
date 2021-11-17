package main;

import controller.Controller;
import model.Model;
import view.View;

import javax.swing.*;
import java.io.*;

public class TriviaMazeFrame extends JFrame {

    public static final String SAVE_FILE = "save.ser";

    private JMenuBar myMenuBar;

    private JMenu myFileMenu;

    private JMenu myHelpMenu;

    private JMenuItem mySaveGameMenuItem;

    private JMenuItem myLoadGameMenuItem;

    private JMenuItem myNewGameMenuItem;

    private JMenuItem myExitMenuItem;

    private JMenuItem myAboutMenuItem;

    private JMenuItem myHowToPlayMenuItem;

    private Model myModel;

    private View myView;

    private Controller myController;

    private TriviaMazeFrame() {
        //Does nothing..
    }

    public TriviaMazeFrame(final Model theModel) {
        myModel = theModel;
        myView = new View(myModel);
        myController = new Controller(myModel);
        layoutComponents();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Lays out the components for the main frame.
     */
    private void layoutComponents() {
        this.setTitle("Joseph & Armeen's Trivia Maze Game");
        myMenuBar = new JMenuBar();
        setUpFileMenu();
        setUpFileMenuHandlers();
        setUpHelpMenu();
        setUpHelpMenuHandlers();
        this.setJMenuBar(myMenuBar);
        final JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        pane.add(myView);
        pane.add(myController);
        this.add(pane);
        this.setSize(1000, 700);
        this.setVisible(true);
    }

    /**
     * Creates and adds components to the file menu.
     */
    private void setUpFileMenu() {
        myFileMenu = new JMenu("File");
        myNewGameMenuItem = new JMenuItem("New Game");
        mySaveGameMenuItem = new JMenuItem("Save Game");
        myLoadGameMenuItem = new JMenuItem("Load Game");
        myExitMenuItem = new JMenuItem("Exit");
        myFileMenu.add(mySaveGameMenuItem);
        myFileMenu.add(myLoadGameMenuItem);
        myFileMenu.add(myNewGameMenuItem);
        myFileMenu.add(myExitMenuItem);
    }

    /**
     * Creates and adds components to the help menu.
     */
    private void setUpHelpMenu() {
        myHelpMenu = new JMenu("Help");
        myAboutMenuItem = new JMenuItem("About");
        myHowToPlayMenuItem = new JMenuItem("Game Play instructions");
        myHelpMenu.add(myAboutMenuItem);
        myHelpMenu.add(myHowToPlayMenuItem);
        myMenuBar.add(myFileMenu);
        myMenuBar.add(myHelpMenu);
    }

    /**
     * Sets up the handlers for the file menu.
     */
    private void setUpFileMenuHandlers() {
        myNewGameMenuItem.addActionListener(e -> myModel.setUpNewGame());
        mySaveGameMenuItem.addActionListener(e -> saveGame());
        myLoadGameMenuItem.addActionListener(e -> loadGame());
        myExitMenuItem.addActionListener(e -> this.dispose());
    }

    /**
     * Saves the gamestate to SAVE_FILE.
     */
    private void saveGame() {
        try (FileOutputStream file = new FileOutputStream(SAVE_FILE);
             ObjectOutputStream output = new ObjectOutputStream(file)) {
            output.writeObject(myModel);
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
    }

    /**
     * Sets up the handlers for the help menu.
     */
    private void setUpHelpMenuHandlers() {
        String about = "This is Trivia Maze, produced for TCSS360 B," +
                "made by Armeen Farange and Joseph Graves in 2021.";
        //TODO Add help string.
        String help = "idk just click some buttons";
        myAboutMenuItem.addActionListener(e ->
                JOptionPane.showMessageDialog(null, about));
        myHowToPlayMenuItem.addActionListener(e ->
                JOptionPane.showMessageDialog(null, help));
    }
}
