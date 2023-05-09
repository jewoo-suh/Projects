trait RoseTree[T] {
    def label: T
    def subtrees(): Iter[RoseTree[T]]
    def isLeaf: Boolean
}

object Question2 {

	def breadthFirst[T](t: RoseTree[T]) = new Iter[RoseTree[T]] {

    	var queue: Iter[RoseTree[T]] = new Singleton(t)

    	def hasNext: Boolean = queue.hasNext

    	def next(): RoseTree[T] = {
        	val out = queue.next()
        	if (!out.isLeaf)
        		queue = new Cat(queue, out.subtrees())
        	out
    	}

	}

	def depthFirst[T](t: RoseTree[T]) = new Iter[RoseTree[T]] {

		var queue : Iter[RoseTree[T]] = new Singleton(t);

		def hasNext: Boolean = queue.hasNext;

		def next() : RoseTree[T] = {
			val out = queue.next();
			if(!out.isLeaf) {
				if(out.subtrees() == null) {
					queue = new Cat(new Cons(out.subtrees().next(), out.subtrees()), queue);
				}
				else {
					queue = new Cons(out.subtrees().next(), queue);
				}
			}
			out
		}

	}

	def main(args: Array[String]) = {

	}
}

