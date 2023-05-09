package search;

import java.util.Comparator;
import java.util.PriorityQueue;

class BestFSComparator implements Comparator<Node_BestFS> {
    public int compare(Node_BestFS n1, Node_BestFS n2) {
        if(n1.nodeVal < n2.nodeVal) { return -1;}
        if(n1.nodeVal > n2.nodeVal) { return 1;}
        return 0;
    }
}

public class Frontier_BestFirstSearch implements Frontier{

    PriorityQueue<Node_BestFS> front;
    NodeFunction nodeFunction;

    public Frontier_BestFirstSearch(NodeFunction nodeFunction) {
        this.front = new PriorityQueue<Node_BestFS>(new BestFSComparator());
        this.nodeFunction = nodeFunction;
    }

    public void addNode(Node n) {
        Node_BestFS newNode = new Node_BestFS(n.parent, n.action, n.state, nodeFunction.nodeToInt(n));
        front.add(newNode);
    }

    public void clear() {
        front.clear();
    }

    public boolean isEmpty() {
        return front.isEmpty();
    }

    public Node removeNode() {
        if(!front.isEmpty()) { return front.poll(); }
        return null;
    }

    public int length() {
        return front.size();
    }
}
