package view;

import javax.swing.*;
import java.awt.*;

public class View extends JPanel {
    public static void main(String[] args){
        SwingUtilities.invokeLater(View::createAndShowJFrame);
    }

    private static void createAndShowJFrame(){
        JFrame f = new JFrame("Grid Layout Example");

        // Create layout and add buttons to show restraints
        JPanel p = new JPanel(new GridLayout(7, 8));



        p.add(new JButton("A"));
        p.add(new JLabel("L/R",SwingConstants.CENTER));
        p.add(new JButton("B"));
        p.add(new JLabel("L/R",SwingConstants.CENTER));
        p.add(new JButton("C"));
        p.add(new JLabel("L/R",SwingConstants.CENTER));
        p.add(new JButton("D"));

        p.add(new JLabel("U/D",SwingConstants.CENTER));
        p.add(new JButton("Blank"));
        p.add(new JLabel("U/D",SwingConstants.CENTER));
        p.add(new JButton("Blank"));
        p.add(new JLabel("U/D",SwingConstants.CENTER));
        p.add(new JButton("Blank"));
        p.add(new JLabel("U/D",SwingConstants.CENTER));

        p.add(new JButton("E"));
        p.add(new JLabel("L/R",SwingConstants.CENTER));
        p.add(new JButton("F"));
        p.add(new JLabel("L/R",SwingConstants.CENTER));
        p.add(new JButton("G"));
        p.add(new JLabel("L/R",SwingConstants.CENTER));
        p.add(new JButton("H"));

        p.add(new JLabel("U/D",SwingConstants.CENTER));
        p.add(new JButton("Blank"));
        p.add(new JLabel("U/D",SwingConstants.CENTER));
        p.add(new JButton("Blank"));
        p.add(new JLabel("U/D",SwingConstants.CENTER));
        p.add(new JButton("Blank"));
        p.add(new JLabel("U/D",SwingConstants.CENTER));

        p.add(new JButton("I"));
        p.add(new JLabel("L/R",SwingConstants.CENTER));
        p.add(new JButton("J"));
        p.add(new JLabel("L/R",SwingConstants.CENTER));
        p.add(new JButton("K"));
        p.add(new JLabel("L/R",SwingConstants.CENTER));
        p.add(new JButton("L"));

        p.add(new JLabel("U/D",SwingConstants.CENTER));
        p.add(new JButton("Blank"));
        p.add(new JLabel("U/D",SwingConstants.CENTER));
        p.add(new JButton("Blank"));
        p.add(new JLabel("U/D",SwingConstants.CENTER));
        p.add(new JButton("Blank"));
        p.add(new JLabel("U/D",SwingConstants.CENTER));

        p.add(new JButton("M"));
        p.add(new JLabel("L/R",SwingConstants.CENTER));
        p.add(new JButton("N"));
        p.add(new JLabel("L/R",SwingConstants.CENTER));
        p.add(new JButton("O"));
        p.add(new JLabel("L/R",SwingConstants.CENTER));
        p.add(new JButton("P"));


        f.setContentPane(p);
        f.pack();
        f.setLocationRelativeTo(null);
        p.setBackground(Color.GRAY);

        // set the size of frame
        f.setSize(400, 400);

        f.show();
    }
}
