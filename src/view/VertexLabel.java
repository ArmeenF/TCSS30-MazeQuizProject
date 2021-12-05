package view;

import java.util.function.Consumer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


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
     * A lambda which is used when the vertex is true.
     */
    private final Consumer<JLabel> myTrueConsumer;

    /**
     * A lambda which is used when the vertex is false.
     */
    private final Consumer<JLabel> myFalseConsumer;

    /**
     * Creates a label that represents a vertex.
     * When true, executes theTrueConsumer.
     * When false, executes theFalseConsumer.
     * @param theRow the row of the vertex on the adjacency matrix.
     * @param theColumn the column of the vertex on the adjacency matrix.
     * @param theTrueConsumer the consumer used when the vertex is false.
     * @param theFalseConsumer the consumer used when the vertex is true.
     */
    public VertexLabel(final int theRow, final int theColumn,
                       final Consumer<JLabel> theTrueConsumer,
                       final Consumer<JLabel> theFalseConsumer) {
        super();
        myRow = theRow;
        myColumn = theColumn;
        myTrueConsumer = theTrueConsumer;
        myFalseConsumer = theFalseConsumer;
        myTrueConsumer.accept(this);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Creates a label that represents a vertex.
     * @param theRow the row of the vertex on the adjacency matrix.
     * @param theColumn the column of the vertex on the adjacency matrix.
     * @param theTrueString the text if the vertex is true.
     * @param theFalseString the text if the vertex is false.
     */
    public VertexLabel(final int theRow, final int theColumn,
                       final String theTrueString, final String theFalseString) {
        this(theRow, theColumn,
                l -> l.setText(theTrueString), l -> l.setText(theFalseString));
    }

    /**
     * Private constructor, to prevent use.
     * Creates a null VertexLabel.
     */
    private VertexLabel() {
        this(0, 0, l -> { }, l -> { });
    }

    /**
     * Private constructor, to prevent use.
     * @param theText Does nothing.
     * @param theIcon Does nothing.
     * @param theHorizontalAlignment Does nothing.
     */
    private VertexLabel(final String theText,
                        final Icon theIcon,
                        final int theHorizontalAlignment) {
        this();
    }

    /**
     * Private constructor, to prevent use.
     * @param theText Does nothing.
     * @param theHorizontalAlignment Does nothing.
     */
    private VertexLabel(final String theText, final int theHorizontalAlignment) {
        this();
    }

    /**
     * Private constructor, to prevent use.
     * @param theText Does nothing.
     */
    private VertexLabel(final String theText) {
        this();
    }

    /**
     * Private constructor, to prevent use.
     * @param theIcon Does nothing.
     * @param theHorizontalAlignment Does nothing.
     */
    private VertexLabel(final Icon theIcon, final int theHorizontalAlignment) {
        this();
    }

    /**
     * Private constructor, to prevent use.
     * @param theIcon Does nothing.
     */
    private VertexLabel(final Icon theIcon) {
        this();
    }


    /**
     * Updates this vertex based on an adjacency matrix.
     * @param theGraph the adjacency matrix.
     */
    public void updateLabel(final boolean[][] theGraph) {
        if (theGraph[myRow][myColumn]) {
            myTrueConsumer.accept(this);
        } else {
            myFalseConsumer.accept(this);
        }
    }
}
