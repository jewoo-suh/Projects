package search;

import java.util.LinkedList;
import java.util.Queue;

//BFS needs Queue
public class Frontier_BFS implements Frontier {

    Queue<Node> front = new LinkedList<>();

    public void addNode(Node node) {
        front.add(node);
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
            return front.remove();
        }
    }

    public int length() {
        return front.size();
    }

}