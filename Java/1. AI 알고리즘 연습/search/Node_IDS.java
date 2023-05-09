package search;

import java.util.Objects;

public class Node_IDS extends Node{

    public int depth = 0;

    public Node_IDS(Node parent, Action action, State state, int depth) {
        super(parent, action, state);
        this.depth = depth;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        if (!super.equals(that)) return false;

        Node_IDS nodeIds = (Node_IDS) that;

        return depth == nodeIds.depth;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + depth;
        return result;
    }
}
