package test;

import model.ModelInterface;
import org.junit.jupiter.api.Test;
import model.Model;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test suite for the model class.
 * @author Joseph Graves
 * @version Fall 2021
 */
class ModelTest {

    /**
     * Tests if the model has an adjacency matrix.
     */
    @Test
    void testGetAdjacencyMatrix() {
        Model model = new Model();
        assertTrue(model.getAdjacencyMatrix().length > 0);
    }

    /**
     * Tests if the player position can be gotten.
     */
    @Test
    void testGetPlayerPosition() {
        Model model = new Model();
        int position = 5;
        model.setPlayerPosition(position);
        assertEquals(position, model.getPlayerPosition());
    }

    /**
     * Tests if the end position is 15 as expected.
     */
    @Test
    void testGetEndPosition() {
        Model model = new Model();
        int end = 15;
        assertEquals(end, model.getEndPosition());
    }

    /**
     * Tests if the model can tell when the player is at the end.
     */
    @Test
    void testIsPlayerAtEnd() {
        Model model = new Model();
        int end = 15;
        model.setPlayerPosition(end);
        assertTrue(model.isPlayerAtEnd());
    }

    /**
     * Tests if the player can't win.
     */
    @Test
    void testCanPlayerWinFalse() {
        Model model = new Model();
        for(int i = 0; i < 16; i++){
            model.setVertice(0, i, false);
        }
        assertFalse(model.canPlayerWin());
    }

    /**
     * Tests if the player can win.
     */
    @Test
    void testCanPlayerWinTrue() {
        Model model = new Model();
        assertTrue(model.canPlayerWin());
    }

    /**
     * Tests if the model can set the player's position.
     */
    @Test
    void testSetPlayerPosition() {
        Model model = new Model();
        int position = 12;
        model.setPlayerPosition(position);
        assertEquals(position, model.getPlayerPosition());
    }

    /**
     * Tests if the model can set a vertice to true.
     */
    @Test
    void testSetVertice() {
        Model model = new Model();
        model.setVertice(1, 1, true);
        assertTrue(model.getAdjacencyMatrix()[1][1]);
    }

    /**
     * Tests if a property change listener can be added to the model.
     */
    @Test
    void testAddPropertyChangeListener() {
        ModelUpdateChecker check = new ModelUpdateChecker();
        Model model = new Model();
        model.addPropertyChangeListener(check);
        model.setPlayerPosition(1);
        assertTrue(check.isMyUpdated());
    }

    /**
     * Tests if a property change listener listening for player position can be added to the model.
     */
    @Test
    void testAddPropertyChangeListenerWithPlayerProperty() {
        ModelUpdateChecker check = new ModelUpdateChecker();
        Model model = new Model();
        model.addPropertyChangeListener(ModelInterface.PLAYER_POSITION, check);
        model.setPlayerPosition(1);
        assertTrue(check.isMyPlayerPositionUpdated());
    }

    /**
     * Tests if a property change listener listening for matrix updates can be added to the model.
     */
    @Test
    void testAddPropertyChangeListenerWithMatrixProperty() {
        ModelUpdateChecker check = new ModelUpdateChecker();
        Model model = new Model();
        model.addPropertyChangeListener(ModelInterface.ADJACENCY_MATRIX, check);
        model.setVertice(1, 1, true);
        assertTrue(check.isMyAdjacencyMatrixUpdated());
    }

    /**
     * Tests if a property change listener can be removed.
     */
    @Test
    void testRemovePropertyChangeListener() {
        ModelUpdateChecker check = new ModelUpdateChecker();
        Model model = new Model();
        model.addPropertyChangeListener(check);
        model.removePropertyChangeListener(check);
        model.setPlayerPosition(1);
        assertFalse(check.isMyUpdated());
    }

    /**
     * Tests if a property change listener checking for player position can be removed.
     */
    @Test
    void testRemovePropertyChangeListenerWithPlayerProperty() {
        ModelUpdateChecker check = new ModelUpdateChecker();
        Model model = new Model();
        model.addPropertyChangeListener(ModelInterface.PLAYER_POSITION, check);
        model.removePropertyChangeListener(ModelInterface.PLAYER_POSITION, check);
        model.setPlayerPosition(1);
        assertFalse(check.isMyPlayerPositionUpdated());
    }

    /**
     * Tests if a property change listener listening for matrix updates can be removed.
     */
    @Test
    void testRemovePropertyChangeListenerWithMazeProperty() {
        ModelUpdateChecker check = new ModelUpdateChecker();
        Model model = new Model();
        model.addPropertyChangeListener(ModelInterface.ADJACENCY_MATRIX, check);
        model.removePropertyChangeListener(ModelInterface.ADJACENCY_MATRIX, check);
        model.setVertice(1, 1, true);
        assertFalse(check.isMyAdjacencyMatrixUpdated());
    }

}