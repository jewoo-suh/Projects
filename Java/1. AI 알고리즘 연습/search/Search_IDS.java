package search;

public class Search_IDS implements Search {

    public Frontier_DFS frontier = new Frontier_DFS();
    public int maxSearchDepth = 15;
    public int nodesGenerated = 0;
    public int maxNodesStored = 0;

    public Search_IDS() {

    }

    public Node searchAlgorithm(Node root, GoalTest goal) {

        frontier.clear(); nodesGenerated = 0; maxNodesStored = 0;

        Node_IDS startNode = new Node_IDS(root.parent, root.action, root.state, 0);

        frontier.addNode(root); nodesGenerated ++; maxNodesStored = 1;

        while (!frontier.isEmpty()) {

            Node_IDS temp = (Node_IDS) frontier.removeNode();

            if(goal.isGoal(temp.state)) { return temp; }

            else {
                for (Action action : temp.state.getApplicableActions()) {

                    if(temp.depth < maxSearchDepth) {
                        State newState = temp.state.getActionResult(action);
                        frontier.addNode(new Node_IDS(temp, action, newState, temp.depth + 1));
                        nodesGenerated ++;
                        if(frontier.length() > maxNodesStored) {maxNodesStored = frontier.length();}
                    }
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
