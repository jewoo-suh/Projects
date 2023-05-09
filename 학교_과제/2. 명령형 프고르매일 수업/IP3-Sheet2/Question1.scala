trait Iter[+T] { 
	def hasNext: Boolean 
	def next():  T 
}

class Cons[T](head: T, tail: Iter[T]) extends Iter[T] {

	private var thisHead = head;
	private var thisTail = tail;

	def hasNext : Boolean = (thisHead == null);
	def next() : T = {
		require(this.hasNext)
		thisHead = thisTail.next();
		return thisHead;
	}

}

class Cat[T] (t: Iter[T], u: Iter[T]) extends Iter[T] {

	private var beforeIter = t;
	private var afterIter = u;

	def hasNext : Boolean = (beforeIter.hasNext) || (afterIter.hasNext);
	def next() : T = {
		require(this.hasNext)
		if (beforeIter.hasNext) { return beforeIter.next(); }
		else return afterIter.next();
	}

}

class Empty[T] extends Iter[T] {

	def hasNext : Boolean = false;
	def next() : T = { scala.sys.error("Sequence Null, Cannot Drop"); }

}

class Singleton[T](t: T) extends Iter[T] {

	private var isUsed = false;
	private var element = t;

	def hasNext : Boolean = !isUsed;
	def next() : T = {
		require(!isUsed);
		isUsed = true;
		return element;
	}

}
