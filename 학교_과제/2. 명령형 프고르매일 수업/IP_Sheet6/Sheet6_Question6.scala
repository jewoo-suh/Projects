import scala.collection.mutable.Stack

object BinaryTreeBag{
  private class Tree(var word: String, var count: Int, var left: Tree, var right: Tree)
}

class BinaryTreeBag{

  private type Tree = BinaryTreeBag.Tree
  private def Tree(word: String, count: Int, left: Tree, right: Tree) =
    new BinaryTreeBag.Tree(word, count, left, right)

  private var root : Tree = null

  //Using pre-defined add function from the lecture notes
  def add(word: String) = 
    if(root==null) root = Tree(word, 1, null, null)
    else{
      var t = root
      while(word < t.word && t.left != null || 
            word > t.word && t.right != null)
      {
        if(word < t.word) t = t.left else t = t.right
      }
      if(word == t.word) t.count += 1
      else if(word < t.word) t.left = Tree(word, 1, null, null)
      else t.right = Tree(word, 1, null, null)
    }

  //sum of count using recursion
  def sum = countSum(root);

  private def countSum(t: Tree) : Int = {
    var sum = 0;
    if(t != null) sum += t.count;
    if(t.left != null) sum += countSum(t.left);
    if(t.right != null) sum += countSum(t.right);
    return sum;
  }

  //sum of count using stacks
  def stackSum = countStackSum(root);

  private def countStackSum(t: Tree) : Int = {
    var s = Stack(t); var sum = 0;
    if(t == null) return sum;
    while(!s.isEmpty) {
      //I: s.isEmpty == false;
      //variant: The number of nodes left to evaluate is decreasing with each iteration
      if(s.top != null) sum += s.top.count;
      var temp = s.pop();
      if(temp.left != null) s.push(temp.left);
      if(temp.right != null) s.push(temp.right);
    }
    return sum;
  }

}

object Sheet6_Question6 {

  def main(args: Array[String]) : Unit = {
    val bag = new BinaryTreeBag;
    for(w <- List("c", "a", "f", "k", "d", "e", "1", "f", "g", 
                  "l", "z", "y", "s", "r", "q")) bag.add(w);
    println("Sum of count calculated using recursion: " + bag.sum)
    println("Sum of count calculated using stack: " + bag.stackSum)
  }

}