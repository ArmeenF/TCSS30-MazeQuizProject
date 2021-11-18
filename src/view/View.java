package view;

import model.Model;
import model.ModelInterface;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
    private final JButton[] myNodes = new JButton[Model.END_NODE + 1];

    /**
     * The squares representing each left to right arrow vertex in the graph.
     */
    private final VertexLabel[] myLeftRightLabels = new VertexLabel[Model.END_NODE];

    /**
     * The squares representing each up to down arrow vertex in the graph.
     */
    private final VertexLabel[] myUpDownLabels =
            new VertexLabel[Model.END_NODE + 1 - Model.ROW_LENGTH];


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
    private void createAndShowPanel() {
        final int rows = 7;
        final int columns = 8;
        int counter = 0;
        this.setLayout(new GridLayout(rows, columns));
        initArrays();
        createLeftRightRow(0);
        createUpDownRow(Model.ROW_LENGTH * counter++); //Ignore IDE
        createLeftRightRow(Model.ROW_LENGTH * counter);
        createUpDownRow(Model.ROW_LENGTH * counter++);
        createLeftRightRow(Model.ROW_LENGTH * counter);
        createUpDownRow(Model.ROW_LENGTH * counter++);
        createLeftRightRow(Model.ROW_LENGTH * counter);
        this.setBackground(Color.GRAY);
        this.setVisible(true);
    }

    /**
     * Initializes the node and vertex array fields.
     */
    private void initArrays() {
        final String lockedPath = "X";
        Arrays.setAll(myNodes, n -> new JButton(""));
        Arrays.setAll(myLeftRightLabels,
                n -> myLeftRightLabels[n] = new VertexLabel(n, n + 1,
                        "↔", lockedPath));
        Arrays.setAll(myUpDownLabels,
                n -> myUpDownLabels[n] = new VertexLabel(n, n + Model.ROW_LENGTH,
                        "↕", lockedPath));
    }

    /**
     * Creates a row entirely of up-down arrows and blank spaces.
     * @param theOffset the offset of the row.
     */
    private void createUpDownRow(final int theOffset) {
        int offset = theOffset;
        final String blankMessage = "";
        this.add(myUpDownLabels[offset]);
        this.add(new JLabel(blankMessage));
        this.add(myUpDownLabels[++offset]);
        this.add(new JLabel(blankMessage));
        this.add(myUpDownLabels[++offset]);
        this.add(new JLabel(blankMessage));
        this.add(myUpDownLabels[++offset]);
    }

    /**
     * Creates a row entirely of nodes and left right arrows.
     * @param theOffset The left most node of the row.
     */
    private void createLeftRightRow(final int theOffset) {
        int offset = theOffset;
        this.add(myNodes[offset]);
        this.add(myLeftRightLabels[offset]);
        this.add(myNodes[++offset]);
        this.add(myLeftRightLabels[offset]);
        this.add(myNodes[++offset]);
        this.add(myLeftRightLabels[offset]);
        this.add(myNodes[++offset]);
    }

    /**
     * Updates the model reference.
     * @param theModel the model to update with.
     */
    public void updateModel(final Model theModel) {
        myModel = theModel;
        myModel.addPropertyChangeListener(this);
        updatePlayerNode();
    }

    /**
     * Updates the player's current position.
     */
    private void updatePlayerNode() {
        myNodes[myPosition].setText("");
        myPosition = myModel.getPlayerPosition();
        myNodes[myPosition].setText(View.PLAYER_ICON);
    }

    /**
     * Checks the vertices and applies the appropriate symbol to the vertex label.
     */
    private void checkVertices() {
        final boolean[][] matrix = myModel.getAdjacencyMatrix();
        for (VertexLabel label : myLeftRightLabels) {
            label.updateLabel(matrix);
        }
        for (VertexLabel label : myUpDownLabels) {
            label.updateLabel(matrix);
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(ModelInterface.PLAYER_POSITION)) {
            updatePlayerNode();
            if (myModel.isPlayerAtEnd()) {
                JOptionPane.showMessageDialog(null, "You win!!!");
                myModel.setUpNewGame();
            }
        }
        if (theEvent.getPropertyName().equals(ModelInterface.ADJACENCY_MATRIX)) {
            if (!myModel.canPlayerWin()) {
                JOptionPane.showMessageDialog(null, "You lose :(");
                myModel.setUpNewGame();
            }
            checkVertices();
        }
    }


}
