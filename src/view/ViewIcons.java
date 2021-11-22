package view;

import javax.swing.*;

/**
 * Image Icon assets for the View to use as graphics.
 * @author Joseph Graves
 * @version Fall 2021
 */
public enum ViewIcons {
    /**
     * Represents the player.
     */
    PLAYER("node_player.gif"),

    /**
     * Represents an empty node.
     */
    EMPTY("node_empty.png"),

    /**
     * Represents a vertex leading left to right, vice versa.
     */
    VERTEX_LEFT_RIGHT("vertex.png"),

    /**
     * Represents a vertex leading left to right, vice versa, destroyed.
     */
    VERTEX_LEFT_RIGHT_DESTROYED("vertex_locked.png"),

    /**
     * Represents a vertex leading up to down, vice versa.
     */
    VERTEX_UP_DOWN("vertex.png"),

    /**
     * Represents a vertex leading up to down, vice versa, destroyed.
     */
    VERTEX_UP_DOWN_DESTROYED("vertex_locked.png"),

    /**
     * Represents an empty space.
     */
    BLANK("blank.gif"),

    /**
     * Represents the ending node.
     */
    GOAL("node_end.gif");

    /**
     * The image stored for the asset.
     */
    private final ImageIcon myImageIcon;

    /**
     * Constructs an asset to use by the view.
     * @param theImagePath the path to the image.
     */
    ViewIcons(final String theImagePath) {
        myImageIcon = new ImageIcon(theImagePath);
    }

    public ImageIcon getImageIcon() {
        return myImageIcon;
    }
}
