/** A queue of data of type A.
  * state: q : seq A
  * init: q = [] */
trait Queue[A]{
	def enqueue(x: A) : Unit
	def dequeue(): A
	def isEmpty: Boolean
}

class ArrayQueue extends Queue[Int]{
	val MAX = 100 // max number of pieces of data
	var arr = new Array[Int](MAX)
	var front = -1; var rear = -1; // i was denoted as front, j as rear
	//Abs: Queue = [Int]
  	//DTI: MAX > front, rear >= -1 

	/** Add x to the back of the queue
	  * post: q = q0 ++ [x] */
	def enqueue(x: Int) : Unit = {
		require(!isFull);

		if(front == -1) {
			front = 0;
			rear = 0;
			arr(rear) = x;
		}

		else if ((rear == MAX-1) && (front != 0)) {
			rear = 0;
			arr(rear) = x;
		}

		else {
			rear += 1;
			arr(rear) = x;
		}
	}

	/** Remove and return the first element.
	  * pre: q 6= []
	  * post: q = tail q0 ∧ returns head q0
	  * or post: returns x s.t. q0 = [x] ++ q */
	def dequeue() : Int = {
		require(!isEmpty);

		val r = arr(front);

		if(front == rear) {
			front = -1;
			rear = -1;
		}

		else if (front == MAX - 1) {
			front = 0;
		}

		else {
			front += 1;
		}

		return r;
	}

	/** Is the queue empty?
	  * post: q = q0 ∧ returns q = [] */
	def isEmpty : Boolean = {
		return front == -1;
	}

	/** Is the queue full?
	  * post: q = q0 ∧ returns q.size == MAX */
	def isFull : Boolean = {
		return ((rear == MAX-1) && (front == 0)) || (rear == front - 1);
	}
}
