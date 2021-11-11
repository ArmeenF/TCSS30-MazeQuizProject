package controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Data representing a question. Includes true and false answers.
 * @author Joseph Graves
 * @version Fall 2021
 */
public class Question implements QuestionInterface{

    /**
     * The question being asked.
     */
    private final String myQuestionString;

    /**
     * A map of true and false answers to the question being asked.
     */
    private final Map<String, Boolean> myAnswers;

    /**
     * Creates a Question given a question and a map of true and false answers.
     * @param theQuestion a question.
     * @param theAnswers a map of true and false answers.
     */
    public Question(final String theQuestion, final Map<String, Boolean> theAnswers) {
        myQuestionString = theQuestion;
        myAnswers = theAnswers;
    }


    /**
     * Creates a question given a question, the correct answer, and one or more false answers.
     * @param theQuestion a question.
     * @param theCorrect the correct answer.
     * @param theIncorrect one or more false answers.
     * @return a question.
     */
    public static Question createQuestion(final String theQuestion, final String theCorrect,
                                          final String ... theIncorrect) {
        Map<String, Boolean> answerMap = new HashMap<>();
        answerMap.put(theCorrect, true);
        for (String incorrect : theIncorrect) {
            answerMap.put(incorrect, false);
        }
        return new Question(theQuestion, answerMap);
    }

    @Override
    public String getQuestionString() {
        return myQuestionString;
    }

    @Override
    public Map<String, Boolean> getAnswerMap() {
        return myAnswers;
    }
}
