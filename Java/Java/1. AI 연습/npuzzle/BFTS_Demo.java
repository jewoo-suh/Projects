package npuzzle;

import search.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class BFTS_Demo {
	public static void main(String[] args) {
		System.out.println("This is a demonstration of breadth-first tree search on 8-puzzle");
		System.out.println();
		
		Tiles initialConfiguration = new Tiles(new int[][] {
			{ 7, 4, 2 },
			{ 8, 1, 3 },
			{ 5, 0, 6 }
		});

		//Initialize goal test
		GoalTest goalTest = new TilesGoalTest();

		//Initialize tree search
		Frontier_BFS fBFS = new Frontier_BFS();
		Frontier_DFS fDFS = new Frontier_DFS();
		Search_Tree sTreeBFS = new Search_Tree(fBFS);
		Search_Tree sTreeDFS = new Search_Tree(fDFS);

		//Initialize graph search
		Frontier_BFS fBFS_g = new Frontier_BFS();
		Frontier_DFS fDFS_g = new Frontier_DFS();
		Search_Graph sGraphBFS = new Search_Graph(fBFS_g);
		Search_Graph sGraphDFS = new Search_Graph(fDFS_g);

		//Initialize start node
		Node startNode = new Node(null, null, initialConfiguration);
		Node startNodeIDS = new Node_IDS(null, null, initialConfiguration, 0);


		//Tree search + BFS

		//Node solution1 = sTreeBFS.searchAlgorithm(startNode, goalTest);
		//new NPuzzlePrinting().printSolution(solution1);
		System.out.println("Tree Search + BFS");
		System.out.println("The number of nodes generated is");
		System.out.println(sTreeBFS.getNodesGenerated());
		System.out.println("The max length of the frontier is");
		System.out.println(sTreeBFS.getMaxNodesStored());
		System.out.println();

		//Tree search + DFS

		//Node solution2 = sTreeDFS.searchAlgorithm(startNode, goalTest);
		//new NPuzzlePrinting().printSolution(solution2);
		System.out.println("Tree Search + DFS");
		System.out.println("The number of nodes generated is");
		System.out.println(sTreeDFS.getNodesGenerated());
		System.out.println("The max length of the frontier is");
		System.out.println(sTreeDFS.getMaxNodesStored());
		System.out.println();

		//Graph search + BFS

		//Node solution3 = sGraphBFS.searchAlgorithm(startNode, goalTest);
		//new NPuzzlePrinting().printSolution(solution3);
		System.out.println("Graph Search + BFS");
		System.out.println("The number of nodes generated is");
		System.out.println(sGraphBFS.getNodesGenerated());
		System.out.println("The max length of the frontier is");
		System.out.println(sGraphBFS.getMaxNodesStored());
		System.out.println();

		//Graph search + DFS

		//Node solution4 = sGraphDFS.searchAlgorithm(startNode, goalTest);
		//new NPuzzlePrinting().printSolution(solution4);
		System.out.println("Graph Search + DFS");
		System.out.println("The number of nodes generated is");
		System.out.println(sGraphDFS.getNodesGenerated());
		System.out.println("The max length of the frontier is");
		System.out.println(sGraphDFS.getMaxNodesStored());

		//IDS

		//Initialize IDS tree search
		Frontier_DFS fDFS_IDS = new Frontier_DFS();
		Search_IDS sTreeIDS = new Search_IDS();
		sTreeIDS.maxSearchDepth = 13;

		Node solution5 = sTreeIDS.searchAlgorithm(startNodeIDS, goalTest);
		new NPuzzlePrinting().printSolution(solution5);
		System.out.println("IDS");
		System.out.println("The number of nodes generated is");
		System.out.println(sTreeIDS.getNodesGenerated());
		System.out.println("The max length of the frontier is");
		System.out.println(sTreeIDS.getMaxNodesStored());
		System.out.println();

	}
}
