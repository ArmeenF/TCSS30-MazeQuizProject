package controller;

import java.util.Map;

/**
 * An interface for the question class, which holds a question with a potential answer
 * @author Joseph Graves
 * @version Fall 2021
 */
public interface QuestionInterface {
    /**
     * Returns the question as a String.
     * @return the question as a String.
     */
    String getQuestionString();

    /**
     * Returns a map of potential answers. Contains at least one truth.
     * @return a map of potential answers.
     */
    Map<String, Boolean> getAnswerMap();
}
