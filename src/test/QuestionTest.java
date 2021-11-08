package test;

import controller.Question;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test suite for the Question data class.
 */
class QuestionTest {

    /**
     * Tests if the question String is properly stored in the Question class.
     */
    @Test
    void getQuestionString() {
        String tfQuestion = "True or False? (The answer is true)";
        Map<String, Boolean> answerMap = new HashMap<>();
        answerMap.put("True", true);
        answerMap.put("False", false);
        Question question = new Question(tfQuestion, answerMap);
        assertEquals(tfQuestion, question.getQuestionString());
    }

    /**
     * Tests if the answer Map is properly stored in the Question class.
     */
    @Test
    void getAnswerMap() {
        String tfQuestion = "True or False? (The answer is true)";
        Map<String, Boolean> answerMap = new HashMap<>();
        answerMap.put("True", true);
        answerMap.put("False", false);
        Question question = new Question(tfQuestion, answerMap);
        assertEquals(answerMap, question.getAnswerMap());
    }
}