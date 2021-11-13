package main;

import model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TriviaMazeFrame extends JFrame {

    private JMenuBar mb;

    private JMenu x,x1;

    private JMenuItem m1;

    private JMenuItem m2;

    private JMenuItem m3;

    private JMenuItem m4;

    private JMenuItem m5;

    private JMenuItem m6;

    private JFrame f;

    public TriviaMazeFrame(Model model) {
        layoutComponents();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void layoutComponents() {

        f = new JFrame("Joseph & Armeen's Trivia Maze Game");

        mb = new JMenuBar();

        x = new JMenu("File");
        x1 = new JMenu("Help");
        // create menuitems for File
        m1 = new JMenuItem("Save Game");
        m2 = new JMenuItem("Load Game");
        m3 = new JMenuItem("New Game");
        m4 = new JMenuItem("Exit");
        // create menuitems for Help
        m5 = new JMenuItem("About");
        m6 = new JMenuItem("Game Play instructions");
        // add menu items to File
        x.add(m1);
        x.add(m2);
        x.add(m3);
        x.add(m4);
        // add menu items to Help
        x1.add(m5);
        x1.add(m6);
        // add menu to menu bar
        mb.add(x);
        mb.add(x1);
        // add menubar to frame
        f.setJMenuBar(mb);
        // set the size of the frame
        f.setSize(700, 700);
        f.setVisible(true);

        //Frame dispose feature for Menu/Exit
        m4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                f.dispose();
            }
        });
        //Dialogue boxes for Menu/Help and Menu/Game Play instructions
        m5.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showMessageDialog(null, "Simple Information Message");
            }
        });
        m6.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showMessageDialog(null, "Simple Information Message");
            }
        });
    }
}
