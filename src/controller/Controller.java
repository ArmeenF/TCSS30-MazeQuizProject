package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.Model;
import view.GameSound;

/**
 * The controller class for Trivia Maze. Allows the user to traverse the maze.
 * @author Armeen Farange
 * @author Joseph Graves
 * @version Fall 2021
 */
public class Controller extends JPanel implements PropertyChangeListener {



    /**
     * The font size of the Question label.
     */
    public static final int QUESTION_FONT_SIZE = 24;

    /**
     * The number of rows in the movement button grid.
     */
    public static final int MOVEMENT_ROWS = 2;

    /**
     * The number of columns in the movement button grid.
     */
    public static final int MOVEMENT_COLS = 3;

    /**
     * The width of the answer buttons and question label.
     */
    public static final int ANSWER_WIDTH = 357;

    /**
     * The height of each answer button.
     */
    public static final int ANSWER_HEIGHT = 23;

    /**
     * The x position for the answer buttons and question label.
     */
    public static final int ANSWER_X = 10;

    /**
     * The y position for the first answer button.
     */
    public static final int ANSWER_Y = 235;

    /**
     * The height of the question label.
     */
    public static final int QUESTION_HEIGHT = 213;

    /**
     * The y position for the question label.
     */
    public static final int QUESTION_Y = 11;

    /**
     * The x position of the movement box.
     */
    public static final int MOVEMENT_X = 90;

    /**
     * The y position of the movement box.
     */
    public static final int MOVEMENT_Y = 540;

    /**
     * The width of the movement box.
     */
    public static final int MOVEMENT_WIDTH = 192;

    /**
     * The height of the movement box.
     */
    public static final int MOVEMENT_HEIGHT = 128;


    /**
     * The deck of questions being asked in Trivia Maze.
     */
    private TriviaDeck myQuestionDeck;

    /**
     * Determines whether to mark the correct answer on the answer buttons.
     */
    private boolean myAnswerCheat;

    /**
     * The label used to display a question.
     */
    private JLabel myQuestionLabel;

    /**
     * The first potential answer to a question.
     */
    private JButton[] myAnswerButtons;

    /**
     * A button that moves the player up.
     */
    private JButton myUpButton;

    /**
     * A button that moves the player down.
     */
    private JButton myDownButton;

    /**
     * A button that moves the player left.
     */
    private JButton myLeftButton;

    /**
     * A button that moves the player right.
     */
    private JButton myRightButton;

    /**
     * A reference to the model of the maze.
     */
    private Model myModel;

    /**
     * Private constructor to force the passing of a model reference.
     */
    private Controller() {
        //Not used.
        initDatabase();
    }

    /**
     * Constructs a controller panel with controls for the user.
     * @param theModel a reference to the maze model.
     */
    public Controller(final Model theModel) {
        super();
        myModel = theModel;
        initDatabase();
        setLayout(null);
        createQuestionBox();
        this.add(createMovementGrid());
        setUpDirectionListeners();
        this.setBackground(Color.gray);
        this.setVisible(true);
        myModel.addPropertyChangeListener(this);
    }

    /**
     * Enables or disables a cheat that shows the correct answer.
     * True for enable, false otherwise.
     * @param theTruth True for enable, false otherwise.
     */
    public void setMyAnswerCheat(final boolean theTruth) {
        myAnswerCheat = theTruth;
    }

    /**
     * Returns the whether the answer cheat is on.
     * True for enabled, false otherwise.
     * @return True for enabled, false otherwise.
     */
    public Boolean getAnswerCheat() {
        return myAnswerCheat;
    }


    /**
     * Returns a String representing the questions and their answers.
     * @return a String representing the questions and their answers.
     */
    public String getAnswerKeyString() {
        final StringBuilder builder = new StringBuilder("<html><ul>");
        final List<Question> questions = ((SQLTriviaDeck) myQuestionDeck).
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
     * Checks if trivia.db exists in the file directory, if it doesn't loads from jar.
     */
    private void initDatabase() {
        if ((new File("trivia.db")).exists()) {
            myQuestionDeck = new SQLTriviaDeck("trivia.db");
        } else {
            myQuestionDeck = new SQLTriviaDeck(":resource:trivia.db");
        }
    }

    /**
     * Creates the question box.
     */
    private void createQuestionBox() {
        myQuestionLabel = new JLabel("", SwingConstants.CENTER);
        myQuestionLabel.setFont(new Font("Serif", Font.BOLD,
                QUESTION_FONT_SIZE));
        myQuestionLabel.setBounds(ANSWER_X, QUESTION_Y, ANSWER_WIDTH, QUESTION_HEIGHT);
        final int maxAnswers = 4;
        myAnswerButtons = new JButton[maxAnswers];
        Arrays.setAll(myAnswerButtons, n -> new JButton());
        final int buttonOffset = 25;
        for (int i = 0; i < myAnswerButtons.length; i++) {
            myAnswerButtons[i].setBounds(ANSWER_X, ANSWER_Y + i * buttonOffset,
                    ANSWER_WIDTH, ANSWER_HEIGHT);
            add(myAnswerButtons[i]);
        }
        add(myQuestionLabel);
        setAnswerButtonEnabled(false);
    }

    /**
     * Creates a movement box.
     * @return a movement box.
     */
    private JPanel createMovementGrid() {
        myUpButton = new JButton("", new ImageIcon("up.png"));
        myDownButton = new JButton("", new ImageIcon("down.png"));
        myLeftButton = new JButton("", new ImageIcon("left.png"));
        myRightButton = new JButton("", new ImageIcon("right.png"));
        final JPanel movementPanel = new JPanel(new GridLayout(MOVEMENT_ROWS,
                                                               MOVEMENT_COLS));
        movementPanel.setBounds(MOVEMENT_X, MOVEMENT_Y, MOVEMENT_WIDTH, MOVEMENT_HEIGHT);
        final JPanel blank1 = new JPanel();
        blank1.setBackground(Color.gray);
        movementPanel.add(blank1);
        movementPanel.add(myUpButton);
        final JPanel blank2 = new JPanel();
        blank2.setBackground(Color.gray);
        movementPanel.add(blank2);
        movementPanel.add(myLeftButton);
        movementPanel.add(myDownButton);
        movementPanel.add(myRightButton);
        checkMovementButtonValidity();
        return movementPanel;
    }

    /**
     * Set up the action listeners for the directional buttons.
     */
    private void setUpDirectionListeners() {
        myUpButton.addActionListener(e -> queueAnswerButtons(Direction.UP));
        myDownButton.addActionListener(e -> queueAnswerButtons(Direction.DOWN));
        myLeftButton.addActionListener(e -> queueAnswerButtons(Direction.LEFT));
        myRightButton.addActionListener(e -> queueAnswerButtons(Direction.RIGHT));
    }

    /**
     * for a given direction, primes the answer buttons for movement.
     * False answers will disable the vertice between player and the offset.
     * True answers will move the player.
     * @param theDirection The direction of the movement button.
     */
    private void queueAnswerButtons(final Direction theDirection) {
        disableMovement();
        GameSound.playSound("movement_queue.wav", false);
        final Question question = myQuestionDeck.getQuestion();
        setQuestionText(question.getQuestionString());
        final int offset = getOffset(theDirection);
        final List<Map.Entry<String, Boolean>> answerList
                = new ArrayList<>(question.getAnswerMap().entrySet());
        clearAnswerButtonText();
        setAnswerButtonEnabled(true);
        Collections.shuffle(answerList);
        setAnswerHandlersFromList(answerList, offset);
        disableBlankAnswerButtons();
    }

    private void setAnswerHandlersFromList(final List<Map.Entry<String, Boolean>> theList,
                                           final int theOffset) {
        Map.Entry<String, Boolean> answer;
        for (int i = 0; i < theList.size(); i++) {
            answer = theList.get(i);
            if (i >= myAnswerButtons.length) {
                break;
            }
            setAnswerHandler(myAnswerButtons[i], answer.getKey(),
                answer.getValue(), theOffset);
        }
    }

    private void disableMovement() {
        myUpButton.setEnabled(false);
        myDownButton.setEnabled(false);
        myLeftButton.setEnabled(false);
        myRightButton.setEnabled(false);
    }

    private void setQuestionText(final String theQuestionString) {
        myQuestionLabel.setText("<html>" + theQuestionString + "</html>");
    }

    /**
     * Retrieves the offset for the direction based upon the maze model.
     * @param theDirection The directional input.
     * @return the offset for that direction.
     */
    private int getOffset(final Direction theDirection) {
        return switch (theDirection) {
            case UP -> -Model.ROW_LENGTH;
            case DOWN -> Model.ROW_LENGTH;
            case LEFT -> -1;
            case RIGHT -> 1;
        };
    }

    /**
     * Sets up the answer buttons handler and text.
     * If the truth is false, set up a disable-vertice handler.
     * Otherwise, set up a move-player handler.
     * @param theButton The button to be modified.
     * @param theText The answer text.
     * @param theTruth The truth of the answer.
     * @param theOffset The directional offset.
     */
    private void setAnswerHandler(final JButton theButton, final String theText,
                                  final boolean theTruth, final int theOffset) {
        removeAnswerButtonListener(theButton);
        theButton.setText(theText);
        if (theTruth) {
            theButton.addActionListener(e -> trueAnswerHandler(theOffset));
            if (myAnswerCheat) {
                theButton.setText(theButton.getText() + " *");
            }
        } else {
            theButton.addActionListener(e -> falseAnswerHandler(theOffset));
        }
    }

    /**
     * Moves the player to player+offset.
     * Disables answer buttons afterwards.
     * @param theOffset the directional offset.
     */
    private void trueAnswerHandler(final int theOffset) {
        GameSound.playSound("correct.wav", false);
        myModel.setPlayerPosition(myModel.getPlayerPosition() + theOffset);
        disableAnswerButtons();
    }

    /**
     * Disables the vertice between the player position and player+offset nodes.
     * @param theOffset the directional offset.
     */
    private void falseAnswerHandler(final int theOffset) {
        GameSound.playSound("lock_vertex.wav", false);
        disableVertice(theOffset);
        disableAnswerButtons();
    }

    /**
     * Disables the vertice between player position and player+offset.
     * @param theOffset the directional offset.
     */
    private void disableVertice(final int theOffset) {
        myModel.setSymmetricalVertice(myModel.getPlayerPosition(),
                myModel.getPlayerPosition() + theOffset, false);
    }

    /**
     * Sets the enabled value for all 4 answer buttons.
     * @param theValue The boolean value.
     */
    private void setAnswerButtonEnabled(final boolean theValue) {
        Arrays.stream(myAnswerButtons).forEach(b -> b.setEnabled(theValue));
    }

    /**
     * Clears the text from the answer buttons.
     */
    private void clearAnswerButtonText() {
        Arrays.stream(myAnswerButtons).forEach(b -> b.setText(""));
    }

    /**
     * Removes all the action listeners current on a JButton.
     * @param theButton the JButton to remove listeners from.
     */
    private void removeAnswerButtonListener(final JButton theButton) {
        Stream.of(theButton.getActionListeners()).
                forEach(theButton::removeActionListener);
    }

    /**
     * Disables all four answer buttons.
     */
    private void disableAnswerButtons() {
        myQuestionLabel.setText("");
        clearAnswerButtonText();
        setAnswerButtonEnabled(false);
    }

    /**
     * Disables any of the four answer buttons that happens to be blank.
     */
    private void disableBlankAnswerButtons() {
        Arrays.stream(myAnswerButtons).forEach(b -> b.setEnabled(!b.getText().isBlank()));
    }

    /**
     * Updates the model reference.
     * @param theModel the model to update with.
     */
    public void updateModel(final Model theModel) {
        myModel = theModel;
        myModel.addPropertyChangeListener(this);
        checkMovementButtonValidity();
    }

    /**
     * Checks the validity of a movement option. If not valid, disable.
     */
    private void checkMovementButtonValidity() {
        final boolean[][] matrix = myModel.getAdjacencyMatrix();
        final int playerPosition = myModel.getPlayerPosition();
        myLeftButton.setEnabled(playerPosition - 1 >= 0
                && matrix[playerPosition][playerPosition - 1]);
        myUpButton.setEnabled(playerPosition - Model.ROW_LENGTH >= 0
                && matrix[playerPosition][playerPosition - Model.ROW_LENGTH]);
        myDownButton.setEnabled(playerPosition + Model.ROW_LENGTH <= Model.END_NODE
                && matrix[playerPosition][playerPosition + Model.ROW_LENGTH]);
        myRightButton.setEnabled(playerPosition + 1 <= Model.END_NODE
                && matrix[playerPosition][playerPosition + 1]);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        checkMovementButtonValidity();
    }
}
