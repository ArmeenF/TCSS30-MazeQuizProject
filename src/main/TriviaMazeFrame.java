package main;

import controller.Controller;
import controller.Question;
import controller.SQLTriviaDeck;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
     * The width of the view in the main window.
     */
    public static final int VIEW_WIDTH = 200 * Model.ROW_LENGTH;

    /**
     * The height of the view in the main window.
     */
    public static final int VIEW_HEIGHT = 100 + (Model.END_NODE + 1) / Model.ROW_LENGTH * 200;

    /**
     * The width of the controller in the main window.
     */
    public static final int CONTROLLER_WIDTH = 400;

    /**
     * The height of the controller in the main window.
     */
    public static final int CONTROLLER_HEIGHT = 900;

    /**
     * The width of the main window.
     */
    public static final int MAIN_WIDTH = CONTROLLER_WIDTH + VIEW_WIDTH;

    /**
     * The height of the main window.
     */
    public static final int MAIN_HEIGHT = (Math.max(VIEW_HEIGHT, CONTROLLER_HEIGHT)) + 60;

    /**
     * The message for an Okay button.
     */
    public static final String ACKNOWLEDGE = "Okay";

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
        setLayout(null);
        myView.setBounds(0, 0, VIEW_WIDTH, VIEW_HEIGHT);
        add(myView);
        myController.setBounds(VIEW_WIDTH, 0, CONTROLLER_WIDTH, CONTROLLER_HEIGHT);
        add(myController);
        setVisible(true);
        setResizable(false);
    }

    /**
     * Shows gameplay instructions during startup.
     */
    private void startUpMessage() {
        final String help = "<html>We'd like to welcome you to our trivia maze game!" +
                "<br>The objective is to get from the starting point to the finishing point." +
                "<br>You may choose the direction to go in by using the directional arrows." +
                "<br>After you've decided on a direction, you'll be asked a trivia question." +
                "<br>If you answer right, you will go in the intended direction;" +
                "<br>if you answer incorrectly," +
                "<br>the chosen direction will be locked, and you will have to " +
                "choose another direction." +
                "<br>You will lose the game if you do not correctly answer all of" +
                "the directional trivia questions.<br></html>";
        final JLabel message = new JLabel(help);
        final ImageIcon esrb = new ImageIcon("E_rtg.png");
        final JLabel iconRating = new JLabel(esrb, SwingConstants.LEFT);
        iconRating.setVerticalAlignment(SwingConstants.BOTTOM);
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setVerticalAlignment(SwingConstants.CENTER);
        final JButton button = new JButton(ACKNOWLEDGE);
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
        myHelpFrame.setLayout(new GridLayout());
        myHelpFrame.pack();
        myHelpFrame.setLocationRelativeTo(null);
        myHelpFrame.setVisible(true);
        button.addActionListener(e -> myHelpFrame.setVisible(false));
    }

    /**
     * Creates the menu bar for this frame.
     */
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
        final String about = "This is Trivia Maze, produced for TCSS360 B, "
                + "made by Armeen Farange and Joseph Graves in 2021.";
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
        final JFrame frame = new JFrame("Cheat menu");
        final JButton cheatAnswerKey = new JButton("Answers to All Trivia Questions");
        final JButton cheatUnlockPaths = new JButton("Unlock All Paths");
        final JButton cheatShowAnswer = new JButton("Answer Cheat: "
                                                + myController.getAnswerCheat().toString());
        final JButton cheatSetPosition = new JButton("Set player position");
        cheatAnswerKey.setBorder(BorderFactory.createBevelBorder(1, Color.red, Color.blue));
        cheatUnlockPaths.setBorder(BorderFactory.createBevelBorder(1, Color.red, Color.blue));
        cheatShowAnswer.setBorder(BorderFactory.createBevelBorder(1, Color.red, Color.blue));
        cheatSetPosition.setBorder(BorderFactory.createBevelBorder(1, Color.red, Color.blue));
        frame.add(cheatAnswerKey);
        frame.add(cheatUnlockPaths);
        frame.add(cheatShowAnswer);
        frame.add(cheatSetPosition);
        frame.setLayout(new GridLayout(0, 1));
        final int width = 600;
        final int height = 250;
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        cheatAnswerKey.addActionListener(e -> cheatMenuAnswers());
        cheatUnlockPaths.addActionListener(e -> cheatMenuVertexReset());
        cheatShowAnswer.addActionListener(e -> {
            myController.setMyAnswerCheat(!myController.getAnswerCheat());
            cheatShowAnswer.setText("Answer Cheat: "
                    + myController.getAnswerCheat().toString());
        });
        cheatSetPosition.addActionListener(e -> cheatMenuPlayerPositionSet());
    }

    /**
     * Returns a String representing the questions and their answers.
     * @return a String representing the questions and their answers.
     */
    private String getAnswerKeyString() {
        final StringBuilder builder = new StringBuilder("<html><ul>");
        final List<Question> questions = ((SQLTriviaDeck) Controller.QUESTION_DECK).
                getQuestionList();
        for (Question question : questions) {
            final Optional<Map.Entry<String, Boolean>> optional = question.getAnswerMap().
                    entrySet().stream().filter(Map.Entry::getValue).findFirst();
            String answer = "";
            if (optional.isPresent()) {
                answer = optional.get().getKey();
            }
            builder.append("<li>").append(question.getQuestionString()).
                    append(": ").append(answer).append("</li>");
        }

        return builder.append("</ul></html>").toString();
    }

    /**
     * Cheat menu answers.
     */
    private void cheatMenuAnswers() {
        final JLabel message = new JLabel(getAnswerKeyString());
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setVerticalAlignment(SwingConstants.CENTER);
        final JButton button = new JButton(ACKNOWLEDGE);
        final JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(button, BorderLayout.LINE_END);
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        final JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.LIGHT_GRAY);
        frame.add(message);
        frame.add(bottomPanel, BorderLayout.PAGE_END);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        button.addActionListener(e -> frame.setVisible(false));
    }

    private void cheatMenuVertexReset() {
        final int oldPosition = myModel.getPlayerPosition();
        myModel.setUpNewGame();
        myModel.setPlayerPosition(oldPosition);
    }

    /**
     * Creates and displays a cheat menu that allows the user to set their position.
     */
    private void cheatMenuPlayerPositionSet() {
        final JFrame frame = new JFrame("Set Player Position");
        frame.setLayout(new GridLayout((Model.END_NODE + 1) / Model.ROW_LENGTH,
                                        Model.ROW_LENGTH));
        for (int i = 0; i < Model.END_NODE + 1; i++) {
            final JButton button = new JButton(i + "");
            final int finalI = i;
            button.addActionListener(e -> myModel.setPlayerPosition(finalI));
            button.setBorder(BorderFactory.createBevelBorder(1, Color.red, Color.blue));
            frame.add(button);
        }
        frame.setVisible(true);
        frame.pack();
        final int buttonWidthAndHeight = 75;
        frame.setSize(buttonWidthAndHeight * Model.ROW_LENGTH,
                buttonWidthAndHeight * ((Model.END_NODE + 1) / Model.ROW_LENGTH));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
        JOptionPane.showMessageDialog(null, theException.getMessage(),
                theException.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
        theException.printStackTrace();
    }
}
