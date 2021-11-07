package model;

import java.beans.PropertyChangeListener;

/**
 * An interface for the maze's model class.
 * @author Joseph Graves
 * @version Fall 2021
 */
public interface ModelInterface {

    /**
     * The property name for the player's position.
     */
    String PLAYER_POSITION = "pposition";

    /**
     * The property name for the adjacency matrix.
     */
    String ADJACENCY_MATRIX = "amatrix";

    /**
     * Returns the adjacency matrix for the maze.
     * @return the adjacency matrix for the maze.
     */
    boolean[][] getAdjacencyMatrix();

    /**
     * Returns the player's position as a decimal.
     * @return the player's position as a decimal.
     */
    int getPlayerPosition();

    /**
     * Returns the end node position as a decimal.
     * @return the end node position as a decimal.
     */
    int getEndPosition();

    /**
     * Returns true if the player and end position are the same, false otherwise.
     * @return true if the player and end position are the same.
     */
    boolean isPlayerAtEnd();

    /**
     * Returns true if the player can win, false otherwise.
     * @return true if the player can win.
     */
    boolean canPlayerWin();

    /**
     * Sets the player position.
     * @param thePosition the position to set.
     */
    void setPlayerPosition(final int thePosition);

    /**
     * Sets the row column pair in the adjacency matrix to a boolean value.
     * @param theRow The row to set.
     * @param theColumn The column to set.
     * @param theValue The boolean value.
     */
    void setVertice(final int theRow, final int theColumn, final boolean theValue);

    /**
     * Adds a listener to this model.
     * @param thePropertyName The name of the property to listen for.
     * @param theListener The listener to add.
     */
    void addPropertyChangeListener(final String thePropertyName, final PropertyChangeListener theListener);

    /**
     * Adds a listener to this model.
     * @param theListener The listener to add.
     */
    void addPropertyChangeListener(final PropertyChangeListener theListener);

    /**
     * Removes a listener with a property name from this model.
     * @param thePropertyName The property name.
     * @param theListener The listener to remove.
     */
    void removePropertyChangeListener(final String thePropertyName, final PropertyChangeListener theListener);

    /**
     * Removes a listener from this model.
     * @param theListener the listener to remove.
     */
    void removePropertyChangeListener(final PropertyChangeListener theListener);
}
