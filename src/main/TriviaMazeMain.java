package main;

import model.Model;

import javax.swing.*;

/**
 * The main entry point for the Trivia Maze program.
 * @author Joseph Graves
 * @version Fall 2021
 */
public final class TriviaMazeMain {

    /**
     * Private Constructor to prevent instantiation.
     */
    private TriviaMazeMain() {
        //Does nothing...
    }

    /**
     * The main entry point for the Trivia Maze program.
     * @param theUnused unused.
     */
    public static void main(final String[] theUnused) {
        SwingUtilities.invokeLater(() -> new TriviaMazeFrame(new Model()));
    }
}
