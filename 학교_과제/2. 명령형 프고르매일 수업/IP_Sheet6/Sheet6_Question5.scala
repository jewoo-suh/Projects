case class Tree(var word: String, var left: Tree, var right: Tree)

object Sheet6_Question4 {

	def flip(t: Tree) : Unit = {
		var temp = t.left;
		t.left = t.right;
		t.right = temp;
		if(t.left != null) flip(t.left);
		if(t.right != null) flip(t.right);
	}

	//From previous Question, used for demonstration purposes
	def printTree(tree: Tree, rep: Int) : Unit = {
		print(" . " * rep);
		if(tree.word != null) println(tree.word);
		else println("null");
		if(tree.left == null) println(" . " * (rep+1) + "null");
		if(tree.left != null) printTree(tree.left, rep+1);
		if(tree.right != null) printTree(tree.right, rep+1);		
		if(tree.right == null) println(" . " * (rep+1) + "null");
	}

	 def main(args: Array[String]) : Unit = {

	 	var tr = Tree("three", Tree("four", Tree("five",null,null), Tree("six",
	Tree("seven", Tree("one",null,null), null), null)), Tree("two",null,null))

	 	printTree(tr, 0);
	 	flip(tr); println();
	 	printTree(tr, 0);

    }

}