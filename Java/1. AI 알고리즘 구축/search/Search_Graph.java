package search;

import java.util.HashSet;
import java.util.Set;

public class Search_Graph implements Search{

    public Frontier frontier;
    public int nodesGenerated = 0;
    public int maxNodesStored = 0;

    //Constructor
    public Search_Graph (Frontier frontier) {
        this.frontier = frontier;
    }

    public Node searchAlgorithm(Node root, GoalTest goal) {

        //To keep track of the visited states
        Set<State> visitedStates = new HashSet<>();

        //Initialize variables
        frontier.clear(); nodesGenerated = 0; maxNodesStored = 0;

        //Add root to search algorithm frontier and update variables
        frontier.addNode(root);
        visitedStates.add(root.state);
        nodesGenerated ++;
        maxNodesStored = 1;

        //Until the frontier is empty 9we search all nodes)
        while (!frontier.isEmpty()) {

            Node temp = frontier.removeNode();

            if(goal.isGoal(temp.state)) {
                return temp; }

            else {
                for (Action action : temp.state.getApplicableActions()) {
                    State newState = temp.state.getActionResult(action);
                    if(!visitedStates.contains(newState)) {
                        frontier.addNode(new Node(temp, action, newState));
                        nodesGenerated ++;
                        if(frontier.length() > maxNodesStored) {maxNodesStored = frontier.length();}
                        visitedStates.add(newState);
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
