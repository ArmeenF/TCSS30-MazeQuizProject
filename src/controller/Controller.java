package controller;

import javax.swing.*;
import java.awt.*;

/**
 * The controller class for Trivia Maze. Allows the user to traverse the maze.
 */
public class Controller extends JPanel {
    static JFrame f;

    // JButton
    static JButton b1;
    static JButton b2;
    static JButton b3;
    static JButton b4;

    // label to display text
    static JLabel l;

    // main class
    public static void main(String[] args)
    {
        // create a new frame to store text field and button
        f = new JFrame("Question Panel");

        // Creating a label to display text
        l = new JLabel("Question", SwingConstants.CENTER);
        l.setFont(new Font("Serif", Font.CENTER_BASELINE, 24));

        // Creating a new buttons
        b1 = new JButton("Button 1");
        b1.addActionListener(ae -> JOptionPane.showMessageDialog(null, "Button 1 !!!!"));
        b2 = new JButton("Button 2");
        b2.addActionListener(ae -> JOptionPane.showMessageDialog(null, "Button 2 !!!!"));
        b3 = new JButton("Button 3");
        b3.addActionListener(ae -> JOptionPane.showMessageDialog(null, "Button 3 !!!!"));
        b4 = new JButton("Button 4");
        b4.addActionListener(ae -> JOptionPane.showMessageDialog(null, "Button 4 !!!!"));

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        // add buttons and textfield to panel
        Box box = Box.createVerticalBox();
        box.add(l);
        box.add(b1);
        box.add(b2);
        box.add(b3);
        box.add(b4);

        // add panel to frame
        p.add(box);
        f.add(p);

        // Setting background of panel
        p.setBackground(Color.gray);

        // set a layout for panel
        //p.setLayout(new FlowLayout());

        // set the size of frame
        f.setSize(300, 300);

        f.show();
    }
    /**
     * The deck of questions being asked in Trivia Maze.
     */
    public static final TriviaDeck QUESTION_DECK = () ->
            Question.createQuestion("True", "True", "False");

}
