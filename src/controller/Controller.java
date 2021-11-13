package controller;

import model.Model;

import javax.swing.*;
import java.awt.*;

/**
 * The controller class for Trivia Maze. Allows the user to traverse the maze.
 */
public class Controller extends JPanel {

    /**
     * The deck of questions being asked in Trivia Maze.
     */
    public static final TriviaDeck QUESTION_DECK = () ->
            Question.createQuestion("True", "True", "False");

    private JLabel myQuestionLabel;

    private JButton myAnswerButtonOne;

    private JButton myAnswerButtonTwo;

    private JButton myAnswerButtonThree;

    private JButton myAnswerButtonFour;

    private Model myModel;

    private Controller() {
        //Not used.
    }

    public Controller(Model theModel) {
        myModel = theModel;
        layoutQuestionBox();
        setUpQuestionBoxListeners();
    }

    private void layoutQuestionBox() {
        myQuestionLabel = new JLabel("Question", SwingConstants.CENTER);
        myQuestionLabel.setFont(new Font("Serif", Font.CENTER_BASELINE, 24));
        myAnswerButtonOne = new JButton("Button 1");
        myAnswerButtonTwo = new JButton("Button 2");
        myAnswerButtonThree = new JButton("Button 3");
        myAnswerButtonFour = new JButton("Button 4");
        Box box = Box.createVerticalBox();
        box.add(myQuestionLabel);
        box.add(myAnswerButtonOne);
        box.add(myAnswerButtonTwo);
        box.add(myAnswerButtonThree);
        box.add(myAnswerButtonFour);
        this.add(box);
        this.setBackground(Color.gray);
        this.setSize(300, 300);
        this.setVisible(true);
    }

    private void setUpQuestionBoxListeners() {
        myAnswerButtonOne.addActionListener(ae ->
                JOptionPane.showMessageDialog(null, "Button 1 !!!!"));
        myAnswerButtonTwo.addActionListener(ae ->
                JOptionPane.showMessageDialog(null, "Button 2 !!!!"));
        myAnswerButtonThree.addActionListener(ae ->
                JOptionPane.showMessageDialog(null, "Button 3 !!!!"));
        myAnswerButtonFour.addActionListener(ae ->
                JOptionPane.showMessageDialog(null, "Button 4 !!!!"));
    }


}
