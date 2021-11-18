package view;

import javax.swing.*;

/**
 * A label representing a vertex in a graph.
 * @author Joseph Graves
 * @version Fall 2021
 */
public class VertexLabel extends JLabel {
    /**
     * The row on the ajacency matrix for this vertex.
     */
    private final int myRow;

    /**
     * The column on the adjacency matrix for this vertex.
     */
    private final int myColumn;

    /**
     * The label's text if this vertex is true.
     */
    private final String myTrueString;

    /**
     * The label's text if this vertex is false.
     */
    private final String myFalseString;

    /**
     * Creates a label that represents a vertex.
     * @param theRow the row of the vertex on the adjacency matrix.
     * @param theColumn the column of the vertex on the adjacency matrix.
     * @param theTrueString the text if the vertex is true.
     * @param theFalseString the tetx if the vertex is false.
     */
    public VertexLabel(final int theRow, final int theColumn,
                       final String theTrueString, final String theFalseString) {
        myRow = theRow;
        myColumn = theColumn;
        myTrueString = theTrueString;
        myFalseString = theFalseString;
        setText(theTrueString);
        setHorizontalAlignment(SwingConstants.CENTER);
    }


    /**
     * Updates this vertex based on an adjacency matrix.
     * @param theGraph the adjacency matrix.
     */
    public void updateLabel(final boolean[][] theGraph) {
        if (theGraph[myRow][myColumn]) {
            setText(myTrueString);
        } else {
            setText(myFalseString);
        }
    }
}
