package model;

import java.beans.PropertyChangeListener;

public class Model implements ModelInterface {
    @Override
    public boolean[][] getAdjacencyMatrix() {
        return new boolean[0][];
    }

    @Override
    public int getPlayerPosition() {
        return 0;
    }

    @Override
    public int getEndPosition() {
        return 0;
    }

    @Override
    public boolean isPlayerAtEnd() {
        return false;
    }

    @Override
    public boolean canPlayerWin() {
        return false;
    }

    @Override
    public void setPlayerPosition(int thePosition) {

    }

    @Override
    public void setVertice(int theRow, int theColumn, boolean theValue) {

    }

    @Override
    public void addPropertyChangeListener(String thePropertyName, PropertyChangeListener theListener) {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener theListener) {

    }

    @Override
    public void removePropertyChangeListener(String thePropertyName, PropertyChangeListener theListener) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener theListener) {

    }
}
