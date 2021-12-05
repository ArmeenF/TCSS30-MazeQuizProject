package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.Model;
import model.ModelInterface;

/**
 * A view of the maze model.
 * @author Armeen Farange
 * @author Joseph Graves
 * @version Fall 2021
 */
public class View extends JPanel implements PropertyChangeListener {

    /**
     * The squares representing each node in the graph.
     */
    private final JLabel[] myNodes = new JLabel[Model.END_NODE + 1];

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
        super();
        myModel = theModel;
        myModel.addPropertyChangeListener(this);
        createAndShowPanel();
        updatePlayerNode();
        myNodes[myNodes.length - 1].setIcon(ViewIcons.GOAL.getImageIcon());
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

    private Runnable newGameFix() {
        return () -> {
            final int shortDelay = 15;
            try {
                Thread.sleep(shortDelay);
            } catch (final InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
            myModel.setUpNewGame();
        };
    }

    /**
     * Initializes the node and vertex array fields.
     */
    private void initArrays() {
        Arrays.setAll(myNodes, n ->
                new JLabel("", ViewIcons.EMPTY.getImageIcon(), SwingConstants.CENTER));

        Arrays.setAll(myLeftRightLabels,
                n -> myLeftRightLabels[n] = new VertexLabel(n, n + 1,
                        l -> l.setIcon(ViewIcons.VERTEX_LEFT_RIGHT.getImageIcon()),
                        l -> l.setIcon(ViewIcons.VERTEX_LEFT_RIGHT_DESTROYED.getImageIcon())));
        Arrays.setAll(myUpDownLabels,
                n -> myUpDownLabels[n] = new VertexLabel(n, n + Model.ROW_LENGTH,
                        l -> l.setIcon(ViewIcons.VERTEX_UP_DOWN.getImageIcon()),
                        l -> l.setIcon(ViewIcons.VERTEX_UP_DOWN_DESTROYED.getImageIcon())));
    }

    /**
     * Creates a row entirely of up-down arrows and blank spaces.
     * @param theOffset the offset of the row.
     */
    private void createUpDownRow(final int theOffset) {
        int offset = theOffset;
        final String blankMessage = "";
        final ImageIcon blankImage = ViewIcons.BLANK.getImageIcon();
        this.add(myUpDownLabels[offset]);
        this.add(new JLabel(blankMessage, blankImage, SwingConstants.CENTER));
        this.add(myUpDownLabels[++offset]);
        this.add(new JLabel(blankMessage, blankImage, SwingConstants.CENTER));
        this.add(myUpDownLabels[++offset]);
        this.add(new JLabel(blankMessage, blankImage, SwingConstants.CENTER));
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
        checkVertices();
    }

    /**
     * Updates the player's current position.
     */
    private void updatePlayerNode() {
        myNodes[myPosition].setIcon(ViewIcons.EMPTY.getImageIcon());
        myPosition = myModel.getPlayerPosition();
        myNodes[myPosition].setIcon(ViewIcons.PLAYER.getImageIcon());
    }

    /**
     * Checks the vertices and applies the appropriate symbol to the vertex label.
     */
    private void checkVertices() {
        final boolean[][] matrix = myModel.getAdjacencyMatrix();
        for (final VertexLabel label : myLeftRightLabels) {
            label.updateLabel(matrix);
        }
        for (final VertexLabel label : myUpDownLabels) {
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
            checkVertices();
            if (!myModel.canPlayerWin()) {
                JOptionPane.showMessageDialog(null, "You lose :(");
                myModel.setUpNewGame();
                (new Thread(newGameFix())).start();
                checkVertices();
            }
        }
        myNodes[myNodes.length - 1].setIcon(ViewIcons.GOAL.getImageIcon());
    }


}
