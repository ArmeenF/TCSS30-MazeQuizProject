package controller;

import javax.swing.*;

/**
 * The controller class for Trivia Maze. Allows the user to traverse the maze.
 */
public class Controller extends JPanel {
    /**
     * The deck of questions being asked in Trivia Maze.
     */
    public static final TriviaDeck QUESTION_DECK = () ->
            Question.createQuestion("True", "True", "False");

}
