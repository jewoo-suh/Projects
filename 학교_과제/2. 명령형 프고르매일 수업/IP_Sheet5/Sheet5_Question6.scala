trait Queue[A]{
	def enqueue(x: A) : Unit
	def dequeue(): A
	def isEmpty: Boolean
}

class IntQueue extends Queue[Int]{

	private var list = new IntQueue.Node(Int.MinValue, null)
	//Abs: Queue = [Int]
  	//DTI: ∀ n ∈ list, if n.next != null, n.value != Int.MinValue

	def enqueue(x: Int) : Unit = {
		var n1 = new IntQueue.Node(x, list);
		list = n1;
	}

	def dequeue(): Int = {
		val r = list.value;
		list = list.next;
		return r;
	}

	def isEmpty: Boolean = {
		return list.value == Int.MinValue;
	}

}

object IntQueue{
	private class Node(val value:Int, val next:Node)
}
