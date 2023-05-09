package search;

public interface Frontier {
    public void addNode(Node node);
    public void clear();
    public boolean isEmpty();
    public Node removeNode();
    public int length();
}

