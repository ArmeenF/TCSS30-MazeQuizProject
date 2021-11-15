package view;

import model.Model;
import model.ModelInterface;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

public class View extends JPanel implements PropertyChangeListener {

    public static final String PLAYER_ICON = "☻";

    private JButton[] myNodes = new JButton[Model.END_NODE + 1];

    private int myPosition;

    private Model myModel;

    private View() {
        //... Does Nothing...
    }

    public View(final Model theModel) {
        myModel = theModel;
        myModel.addPropertyChangeListener(this);
        createAndShowPanel();
        updatePlayerNode();
    }


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

//        if (!myModel.canPlayerWin()) {
//            JOptionPane.showMessageDialog(null, "You lose :(");
//            myModel.setUpNewGame();
//        }
    }
}
