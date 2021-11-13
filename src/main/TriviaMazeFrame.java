package main;

import controller.Controller;
import model.Model;

import javax.swing.*;

public class TriviaMazeFrame extends JFrame {

    private JMenuBar myMenuBar;

    private JMenu myFileMenu;

    private JMenu myHelpMenu;

    private JMenuItem mySaveGameMenuItem;

    private JMenuItem myLoadGameMenuItem;

    private JMenuItem myNewGameMenuItem;

    private JMenuItem myExitMenuItem;

    private JMenuItem myAboutMenuItem;

    private JMenuItem myHowToPlayMenuItem;

    private Model myModel;

    private TriviaMazeFrame() {
        //Does nothing..
    }

    public TriviaMazeFrame(final Model theModel) {
        myModel = theModel;
        layoutComponents();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void layoutComponents() {
        this.setTitle("Joseph & Armeen's Trivia Maze Game");
        myMenuBar = new JMenuBar();
        setUpFileMenu();
        setUpFileMenuHandlers();
        setUpHelpMenu();
        setUpHelpMenuHandlers();
        this.setJMenuBar(myMenuBar);
        this.add(new Controller(myModel));
        this.setSize(700, 700);
        this.setVisible(true);
    }

    private void setUpFileMenu() {
        myFileMenu = new JMenu("File");
        myNewGameMenuItem = new JMenuItem("New Game");
        mySaveGameMenuItem = new JMenuItem("Save Game");
        myLoadGameMenuItem = new JMenuItem("Load Game");
        myExitMenuItem = new JMenuItem("Exit");
        myFileMenu.add(mySaveGameMenuItem);
        myFileMenu.add(myLoadGameMenuItem);
        myFileMenu.add(myNewGameMenuItem);
        myFileMenu.add(myExitMenuItem);
    }

    private void setUpHelpMenu() {
        myHelpMenu = new JMenu("Help");
        myAboutMenuItem = new JMenuItem("About");
        myHowToPlayMenuItem = new JMenuItem("Game Play instructions");
        myHelpMenu.add(myAboutMenuItem);
        myHelpMenu.add(myHowToPlayMenuItem);
        myMenuBar.add(myFileMenu);
        myMenuBar.add(myHelpMenu);
    }

    private void setUpFileMenuHandlers() {
        myNewGameMenuItem.addActionListener(e -> myModel.setUpNewGame());
        myExitMenuItem.addActionListener(e -> this.dispose());
    }

    private void setUpHelpMenuHandlers() {
        String about = "This is Trivia Maze, produced for TCSS360 B," +
                "made by Armeen Farange and Joseph Graves in 2021.";
        //TODO Add help string.
        String help = "idk just click some buttons";
        myAboutMenuItem.addActionListener(e ->
                JOptionPane.showMessageDialog(null, about));
        myHowToPlayMenuItem.addActionListener(e ->
                JOptionPane.showMessageDialog(null, help));
    }
}
