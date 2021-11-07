package test;

import model.ModelInterface;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * An object to help test PropertyChangeListener.
 * @author Joseph Graves
 * @version Fall 2021
 */
public class ModelUpdateChecker implements PropertyChangeListener {

    /**
     * The state of whether this object has received one update.
     */
    private boolean myUpdated = false;

    /**
     * The state of whether this object has received an update for player position.
     */
    private boolean myPlayerPositionUpdated = false;

    /**
     * The state of whether this object has received an update for the matrix.
     */
    private boolean myAdjacencyMatrixUpdated = false;

    /**
     * Returns true if the object has been updated.
     * @return true if the object has been updated.
     */
    public boolean isMyUpdated() {
        return myUpdated;
    }

    /**
     * Returns true if the object has been updated with player position.
     * @return true if the object has been updated with player position.
     */
    public boolean isMyPlayerPositionUpdated() {
        return myPlayerPositionUpdated;
    }

    /**
     * Returns true if the object has been updated with matrix information.
     * @return true if the object has been updated with matrix information.
     */
    public boolean isMyAdjacencyMatrixUpdated() {
        return myAdjacencyMatrixUpdated;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        myUpdated = true;
        if (ModelInterface.PLAYER_POSITION.equals(theEvent.getPropertyName())){
            myPlayerPositionUpdated = true;
        }
        if (ModelInterface.ADJACENCY_MATRIX.equals(theEvent.getPropertyName())){
            myAdjacencyMatrixUpdated = true;
        }
    }
}
