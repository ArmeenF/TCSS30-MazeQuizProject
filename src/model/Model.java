package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * A model representing a trivia maze.
 * @author Joseph Graves
 * @version Fall 2021
 */
public class Model implements ModelInterface {

    /**
     * The node designated the end, and also the end index of the adjacency array.
     */
    public static final int END_NODE = 15;

    /**
     * The row length of the nodes, if they were to be placed in a square array.
     */
    public static final int ROW_LENGTH = 4;

    /**
     * The adjacency matrix which shows valid moves.
     */
    private boolean[][] myAdjacencyMatrix;

    /**
     * The player's node position.
     */
    private int myPlayerPosition;

    /**
     * Property change support to help facilitate an MVC design.
     */
    private final PropertyChangeSupport myPropertyChangeSupport;

    /**
     * Create a model of a trivia maze.
     */
    public Model() {
        myPropertyChangeSupport = new PropertyChangeSupport(this);
        setUpNewGame();
    }

    @Override
    public boolean[][] getAdjacencyMatrix() {
        return myAdjacencyMatrix;
    }

    @Override
    public int getPlayerPosition() {
        return myPlayerPosition;
    }

    @Override
    public int getEndPosition() {
        return END_NODE;
    }

    @Override
    public boolean isPlayerAtEnd() {
        return myPlayerPosition == END_NODE;
    }

    @Override
    public boolean canPlayerWin() {
        /*
         * This uses DFS to construct a list of nodes using
         * the player's position as the start point.
         * If END_NODE isn't on the list, the player cannot win.
         */
        List<Integer> nodeList = new ArrayList<>();
        Deque<Integer> nodeDeque = new ArrayDeque<>();
        int current = myPlayerPosition;
        nodeList.add(current);
        nodeDeque.add(current);
        while (!nodeDeque.isEmpty()) {
            for (int i = 0; i < END_NODE + 1; i++) {
                if (myAdjacencyMatrix[current][i] && !nodeList.contains(i)) {
                    nodeList.add(i);
                    nodeDeque.add(i);
                }
            }
            current = nodeDeque.pop();
        }
        return nodeList.contains(END_NODE);
    }

    @Override
    public void setPlayerPosition(final int thePosition) {
        int oldValue = myPlayerPosition;
        myPlayerPosition = thePosition;
        myPropertyChangeSupport.firePropertyChange(ModelInterface.PLAYER_POSITION, oldValue, thePosition);
    }

    @Override
    public void setVertice(final int theRow, final int theColumn, final boolean theValue) {
        boolean oldValue = myAdjacencyMatrix[theRow][theColumn];
        myAdjacencyMatrix[theRow][theColumn] = theValue;
        myPropertyChangeSupport.firePropertyChange(ModelInterface.ADJACENCY_MATRIX, oldValue, theValue);
    }

    @Override
    public void addPropertyChangeListener(final String thePropertyName, final PropertyChangeListener theListener) {
        myPropertyChangeSupport.addPropertyChangeListener(thePropertyName, theListener);
    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPropertyChangeSupport.addPropertyChangeListener(theListener);
    }

    @Override
    public void removePropertyChangeListener(final String thePropertyName, final PropertyChangeListener theListener) {
        myPropertyChangeSupport.removePropertyChangeListener(thePropertyName, theListener);
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPropertyChangeSupport.removePropertyChangeListener(theListener);
    }

    /**
     * Sets a row column pair and it's mirrored value across the diagonal to a value.
     * @param theRow the row.
     * @param theColumn the column.
     * @param theValue the value to set.
     */
    public void setSymmetricalVertice(final int theRow, final int theColumn, final boolean theValue) {
        //SonarLint is screaming about this,
        //but the swapped parameters are so that they're mirrored on the diagonal.
        setVertice(theRow, theColumn, theValue);
        setVertice(theColumn, theRow, theValue);
    }

    /**
     * Sets up a new game. Sets player position to 0, and sets the appropriate vertex values in the
     * adjacency matrix.
     */
    public void setUpNewGame() {
        myPlayerPosition = 0;
        myAdjacencyMatrix = new boolean[END_NODE + 1][END_NODE + 1];
        for (int i = 0; i < END_NODE + 1; i++){
            if ((i+1)%ROW_LENGTH != 0) {
                setSymmetricalVertice(i, i + 1, true);
            }
            if (i + ROW_LENGTH < END_NODE + 1) {
                setSymmetricalVertice(i, i+4, true);
            }
        }
    }


}
