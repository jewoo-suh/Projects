package search;

public class Search_Tree implements Search {

    public Frontier frontier;
    public int nodesGenerated = 0;
    public int maxNodesStored = 0;

    public Search_Tree (Frontier frontier) {
        this.frontier = frontier;
    }

    public Node searchAlgorithm(Node root, GoalTest goal) {

        frontier.clear(); nodesGenerated = 0; maxNodesStored = 0;

        frontier.addNode(root); nodesGenerated ++; maxNodesStored = 1;

        while (!frontier.isEmpty()) {

            Node temp = frontier.removeNode();

            if(goal.isGoal(temp.state)) { return temp; }

            else {
                for (Action action : temp.state.getApplicableActions()) {
                    State newState = temp.state.getActionResult(action);
                    Node n = new Node(temp, action, newState);
                    frontier.addNode(n);
                    nodesGenerated ++;
                    if(frontier.length() > maxNodesStored) {maxNodesStored = frontier.length();}
                }
            }
        }
        return null;
    }
    public int getNodesGenerated() {
        return nodesGenerated;
    }

    public int getMaxNodesStored() {
        return maxNodesStored;
    }
}
