package controller;

/**
 * An interface for the TriviaDeck. This will contain a collection of Question class objects
 * and dispense them when getQuestion is called.
 * @author Joseph Graves
 * @version Fall 2021
 */
@FunctionalInterface
public interface TriviaDeck {

    /**
     * Returns a Question, which contains a question String and possible answers.
     * @return a Question.
     */
    Question getQuestion();
}
