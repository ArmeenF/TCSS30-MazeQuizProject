package view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class View extends JFrame {
    // menubar
    static JMenuBar mb;
    // JMenu
    static JMenu x,x1;
    // Menu items
    static JMenuItem m1, m2, m3, m4, m5, m6;
    // create a frame
    static JFrame f;

    public static void main(String[] args) {
        // create a frame
        f = new JFrame("Joseph & Armeen's Trivia Maze Game");
        // create a menubar
        mb = new JMenuBar();
        // create a menu
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

        //create 2d array with locations (A,B,C,D,etc)

        //create the move box
        //This is where the user will interact with which direction to move in

        //create a key
        //Basic symbols with descriptions

        //create a question box
        //Where the questions will be displayed

    }
}
