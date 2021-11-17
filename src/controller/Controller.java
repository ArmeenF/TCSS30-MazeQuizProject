package controller;

import model.Model;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The controller class for Trivia Maze. Allows the user to traverse the maze.
 * @author Armeen Farange
 * @author Joseph Graves
 * @version Fall 2021
 */
public class Controller extends JPanel implements PropertyChangeListener {

    /**
     * The deck of questions being asked in Trivia Maze.
     */
    public static final TriviaDeck QUESTION_DECK =
            new SQLTriviaDeck("trivia.db");

    /**
     * The height of the controller panel.
     */
    public static final int CONTROLLER_HEIGHT = 700;

    /**
     * The width of the controller panel.
     */
    public static final int CONTROLLER_WIDTH = 300;

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
     * The label used to display a question.
     */
    private JLabel myQuestionLabel;

    /**
     * The first potential answer to a question.
     */
    private JButton myAnswerButtonOne;

    /**
     * The second potential answer to a question.
     */
    private JButton myAnswerButtonTwo;

    /**
     * The third potential answer to a question.
     */
    private JButton myAnswerButtonThree;

    /**
     * The fourth potential answer to a question.
     */
    private JButton myAnswerButtonFour;

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
    }

    /**
     * Constructs a controller panel with controls for the user.
     * @param theModel a reference to the maze model.
     */
    public Controller(final Model theModel) {
        myModel = theModel;
        this.add(createQuestionBox());
        this.add(createMovementGrid());
        setUpDirectionListeners();
        this.setBackground(Color.gray);
        this.setSize(CONTROLLER_WIDTH, CONTROLLER_HEIGHT);
        this.setVisible(true);
        myModel.addPropertyChangeListener(this);
    }

    /**
     * Creates the question box.
     * @return the question box.
     */
    private Box createQuestionBox() {
        myQuestionLabel = new JLabel("", SwingConstants.CENTER);
        myQuestionLabel.setFont(new Font("Serif", Font.BOLD,
                QUESTION_FONT_SIZE));
        myAnswerButtonOne = new JButton("");
        myAnswerButtonTwo = new JButton("");
        myAnswerButtonThree = new JButton("");
        myAnswerButtonFour = new JButton("");
        final Box box = Box.createVerticalBox();
        box.add(myQuestionLabel);
        box.add(myAnswerButtonOne);
        box.add(myAnswerButtonTwo);
        box.add(myAnswerButtonThree);
        box.add(myAnswerButtonFour);
        this.setAnswerButtonEnabled(false);
        return box;
    }

    /**
     * Creates a movement box.
     * @return a movement box.
     */
    private JPanel createMovementGrid() {
        myUpButton = new JButton("↑");
        myDownButton = new JButton("↓");
        myLeftButton = new JButton("←");
        myRightButton = new JButton("→");
        final JPanel movementPanel = new JPanel(new GridLayout(MOVEMENT_ROWS,
                                                               MOVEMENT_COLS));
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
        final Question question = QUESTION_DECK.getQuestion();
        myQuestionLabel.setText(question.getQuestionString());
        final int offset = getOffset(theDirection);
        final List<Map.Entry<String, Boolean>> answerList
                = new ArrayList<>(question.getAnswerMap().entrySet());
        clearAnswerButtonText();
        setAnswerButtonEnabled(true);
        Collections.shuffle(answerList);
        Map.Entry<String, Boolean> answer = answerList.get(0);
        setAnswerHandler(myAnswerButtonOne, answer.getKey(), answer.getValue(), offset);
        if (answerList.size() > 1) {
            answer = answerList.get(1);
            setAnswerHandler(myAnswerButtonTwo, answer.getKey(), answer.getValue(), offset);
        }
        if (answerList.size() > 2) {
            answer = answerList.get(2);
            setAnswerHandler(myAnswerButtonThree, answer.getKey(), answer.getValue(), offset);
        }
        if (answerList.size() > 3) { //Not magic, can't use loop for this.
            answer = answerList.get(3);
            setAnswerHandler(myAnswerButtonFour, answer.getKey(), answer.getValue(), offset);
        }
        disableBlankAnswerButtons();
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
        myModel.setPlayerPosition(myModel.getPlayerPosition() + theOffset);
        disableAnswerButtons();
    }

    /**
     * Disables the vertice between the player position and player+offset nodes.
     * @param theOffset the directional offset.
     */
    private void falseAnswerHandler(final int theOffset) {
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
        myAnswerButtonOne.setEnabled(theValue);
        myAnswerButtonTwo.setEnabled(theValue);
        myAnswerButtonThree.setEnabled(theValue);
        myAnswerButtonFour.setEnabled(theValue);
    }

    /**
     * Clears the text from the answer buttons.
     */
    private void clearAnswerButtonText() {
        myAnswerButtonOne.setText("");
        myAnswerButtonTwo.setText("");
        myAnswerButtonThree.setText("");
        myAnswerButtonFour.setText("");
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
        myAnswerButtonOne.setEnabled(!myAnswerButtonOne.getText().isBlank());
        myAnswerButtonTwo.setEnabled(!myAnswerButtonTwo.getText().isBlank());
        myAnswerButtonThree.setEnabled(!myAnswerButtonThree.getText().isBlank());
        myAnswerButtonFour.setEnabled(!myAnswerButtonFour.getText().isBlank());
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
