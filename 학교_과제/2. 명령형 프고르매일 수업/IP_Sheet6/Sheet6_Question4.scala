import scala.collection.mutable.Stack

class Tree(var word: String, var left: Tree, var right: Tree)

object Sheet6_Question4 {

	def Tree(word: String, left: Tree, right: Tree) = new Tree(word, left, right)

	var tr = Tree("three", Tree("four", Tree("five",null,null), Tree("six",
	Tree("seven", Tree("one",null,null), null), null)), Tree("two",null,null))

	//recursion
	def printTree(tree: Tree, rep: Int) : Unit = {
		print(" . " * rep);
		if(tree.word != null) println(tree.word);
		else println("null");
		if(tree.left == null) println(" . " * (rep+1) + "null");
		if(tree.left != null) printTree(tree.left, rep+1);
		if(tree.right != null) printTree(tree.right, rep+1);		
		if(tree.right == null) println(" . " * (rep+1) + "null");
	}

	//stack
	def printTreeStack(tree: Tree) : Unit = {
		var s = Stack((tree,0)); val dots = " . ";
		while(!s.isEmpty) {
			if(s.top._1 != null) println(dots * (s.top._2) + s.top._1.word);
			else println(dots * (s.top._2) + "null");
			var temp = s.pop(); 
			if(temp._1 != null) s.push((temp._1.right, temp._2+1),(temp._1.left, temp._2+1));
		}
	}

	 def main(args: Array[String]) : Unit = {

        printTree(tr, 0);
        println();
        printTreeStack(tr);

    }

}