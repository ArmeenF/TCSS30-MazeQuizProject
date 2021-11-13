package main;

import model.Model;

import javax.swing.*;

/**
 * The main entry point for the Trivia Maze program.
 * @author Joseph Graves
 * @version Fall 2021
 */
public class TriviaMazeMain {

    /**
     * Private Constructor to prevent instantiation.
     */
    private TriviaMazeMain() {
        //Does nothing...
    }

    /**
     * The main entry point for the Trivia Maze program.
     * @param args Unused.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TriviaMazeFrame(new Model()));
    }
}
