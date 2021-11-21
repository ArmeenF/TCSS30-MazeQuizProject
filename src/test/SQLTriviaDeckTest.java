package test;

import controller.Question;
import controller.SQLTriviaDeck;
import controller.TriviaDeck;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SQLTriviaDeckTest {

    private TriviaDeck myDeck;

    @BeforeAll
    public void init() {
        myDeck = new SQLTriviaDeck("test.db");
    }

    @Test
    void testGetQuestionQuestion() {
        Question question = myDeck.getQuestion();
        String expected = "True";
        assertEquals(expected, question.getQuestionString());
    }

    @Test
    void testGetQuestionTruth() {
        Question question = myDeck.getQuestion();
        boolean expected = true;
        boolean result = question.getAnswerMap().get("True");
        assertEquals(expected, result);
    }

    @Test
    void testGetQuestionFalse() {
        Question question = myDeck.getQuestion();
        boolean expected = false;
        boolean result = question.getAnswerMap().get("False");
        assertEquals(expected, result);
    }
}