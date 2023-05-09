class DoubleEndedQueue{

	private var startNode = new DoubleEndedQueue.Node(Int.MinValue, null, null);
	private var endNode = new DoubleEndedQueue.Node(Int.MaxValue, startNode, null);
	startNode.next = endNode;
	//Abs: Queue = [Int]
  	//DTI: ∀ n ∈ list, if n.next != null, n.value != Int.MaxValue
	//        && ∀ n ∈ list, if n.prev != null, n.value != Int.MinValue

	/** Is the queue empty? */
	def isEmpty : Boolean = {
		return startNode.next.next == null;
	}

	/** add x to the start of the queue. */
	def addLeft(x:Int) : Unit = {
		var prevLeft = startNode.next;
		var node = new DoubleEndedQueue.Node(x, startNode, prevLeft);
		prevLeft.prev = node;
		startNode.next = node;
	}

	/** get and remove element from the start of the queue. */
	def getLeft() : Int = {
		return startNode.next.datum;
	}

	/** add element to the end of the queue. */
	def addRight(x: Int) : Unit = {
		var prevRight = endNode.prev;
		var node = new DoubleEndedQueue.Node(x, prevRight, endNode);
		prevRight.next = node;
		endNode.prev = node;
	}

	/** get and remove element from the end of the queue. */
	def getRight() : Int = {
		return endNode.prev.datum;
	}

}

object DoubleEndedQueue{
	class Node(var datum: Int, var prev: Node, var next: Node);
}
