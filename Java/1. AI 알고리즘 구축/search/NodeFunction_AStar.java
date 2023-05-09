package search;

public class NodeFunction_AStar implements NodeFunction{

    public NodeFunction heuristicFunction;

    public NodeFunction_AStar(NodeFunction heuristicFunction) {
        this.heuristicFunction = heuristicFunction;
    }

    @Override
    public int nodeToInt(Node n) {
        if(n.parent == null) {return heuristicFunction.nodeToInt(n);}
        return ((Node_BestFS) n.parent).totalCost + heuristicFunction.nodeToInt(n);
    }
}
