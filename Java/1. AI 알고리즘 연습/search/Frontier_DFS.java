package search;

import java.util.Stack;

//DFS needs Stack
public class Frontier_DFS implements Frontier {

    Stack<Node> front = new Stack<Node>();

    public void addNode (Node node) {
        front.push(node);
    }

    public void clear() {
        front.clear();
    }

    public boolean isEmpty() {
        return front.isEmpty();
    }

    public Node removeNode() {
        if (front.isEmpty()) {return null;}
        else {
            return front.pop();
        }
    }

    public int length() {
        return front.size();
    }
}