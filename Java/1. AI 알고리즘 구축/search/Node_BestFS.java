package search;

public class Node_BestFS extends Node{

    //This is the Node class used for Best First Search

    public int nodeVal; //This is f(n)
    //We put nodeVal in the constructor so that we can manually feed it f(n) = g(n) + h(n) via NodeFunction_AStar

    /*
    Ok this is confusing but here's what I think is happening:

    We have nodeVal and totalCost in this Node class.

    nodeVal records f(n) = g(n) + h(n), while totalCost records g(n).

    We add these to the Frontier_BestFS, where they sort nodes according to a "NodeFunction" which decides
    the values of the nodeVal = f(n) = g(n) + h(n);

    Here, the NodeFunction we use is "NodeFunction_AStar", which takes in a heuristic NodeFunction as a
    constructor. The function here is "NodeFunction_MisplacedTiles".

    So when we add a Node in Frontier_BestFS, we evoke NodeFunction_AStar, which uses NF_MPT to
    Calculate node.totalCost = g(n) + node.MPT = h(n) = f(n) and update nodeVal = f(n).
     */
    public int totalCost; //This is g(n)
    public Node_BestFS(Node parent, Action action, State state, int nodeVal) {

        super(parent, action, state);
        this.nodeVal = nodeVal;

        if(parent == null) {this.totalCost = 0;}
        else {this.totalCost = ((Node_BestFS) parent).totalCost + action.actionCost();} //This is Q2-b
        //Nice I wrote parent.nodeVal, not parent.totalCost and it took 9 min to fix that 2023/03/07

    }
}
