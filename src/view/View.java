package view;

import model.Model;
import model.ModelInterface;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

/**
 * A view of the maze model.
 * @author Armeen Farange
 * @author Joseph Graves
 * @version Fall 2021
 */
public class View extends JPanel implements PropertyChangeListener {

    /**
     * The icon representing the player.
     */
    public static final String PLAYER_ICON = "☻";

    /**
     * The squares representing each node in the graph.
     */
    private JButton[] myNodes = new JButton[Model.END_NODE + 1];

    /**
     * The player's current position.
     */
    private int myPosition;

    /**
     * A reference to the maze model.
     */
    private Model myModel;

    /**
     * Private constructor to prevent a model not being referenced.
     */
    private View() {
        //... Does Nothing...
    }

    /**
     * Creates a view of a maze model, given the model.
     * @param theModel the model.
     */
    public View(final Model theModel) {
        myModel = theModel;
        myModel.addPropertyChangeListener(this);
        createAndShowPanel();
        updatePlayerNode();
    }


    /**
     * Creates and shows the view panel.
     */
    private void createAndShowPanel(){
        this.setLayout(new GridLayout(7, 8));
        Arrays.setAll(myNodes, n -> new JButton(""));
        createLeftRightRow(0);
        createUpDownRow();
        createLeftRightRow(4);
        createUpDownRow();
        createLeftRightRow(8);
        createUpDownRow();
        createLeftRightRow(12);
        this.setBackground(Color.GRAY);
        this.setVisible(true);
    }

    /**
     * Creates a row entirely of up-down arrows and blank spaces.
     */
    private void createUpDownRow() {
        String upDownArrow = "↕";
        String blankMessage = "Blank";
        this.add(new JLabel(upDownArrow,SwingConstants.CENTER));
        this.add(new JButton(blankMessage));
        this.add(new JLabel(upDownArrow,SwingConstants.CENTER));
        this.add(new JButton(blankMessage));
        this.add(new JLabel(upDownArrow,SwingConstants.CENTER));
        this.add(new JButton(blankMessage));
        this.add(new JLabel(upDownArrow,SwingConstants.CENTER));
    }

    /**
     * Creates a row entirely of nodes and left right arrows.
     * @param theOffset The left most node of the row.
     */
    private void createLeftRightRow(final int theOffset) {
        final String leftRightArrow = "↔";
        this.add(myNodes[theOffset]);
        this.add(new JLabel(leftRightArrow,SwingConstants.CENTER));
        this.add(myNodes[1 + theOffset]);
        this.add(new JLabel(leftRightArrow,SwingConstants.CENTER));
        this.add(myNodes[2 + theOffset]);
        this.add(new JLabel(leftRightArrow,SwingConstants.CENTER));
        this.add(myNodes[3 + theOffset]);
    }

    /**
     * Updates the player's current position.
     */
    private void updatePlayerNode() {
        myNodes[myPosition].setText("");
        myPosition = myModel.getPlayerPosition();
        myNodes[myPosition].setText(View.PLAYER_ICON);
    }

    @Override
    public void propertyChange(PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(ModelInterface.PLAYER_POSITION)) {
            updatePlayerNode();
            if (myModel.isPlayerAtEnd()) {
                JOptionPane.showMessageDialog(null, "You win!!!");
                myModel.setUpNewGame();
            }
        }
        if(theEvent.getPropertyName().equals(ModelInterface.ADJACENCY_MATRIX)
            && !myModel.canPlayerWin()) {
                JOptionPane.showMessageDialog(null, "You lose :(");
                myModel.setUpNewGame();
        }
    }
}
