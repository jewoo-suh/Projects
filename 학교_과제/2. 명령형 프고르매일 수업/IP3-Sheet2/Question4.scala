trait Sequence[T] { 
	def ins(pos: Int, t: T): Unit 
	def del(pos: Int): T 
	def apply(pos: Int): T 
	def length: Int 
}


trait IterPlugin[T] extends Sequence[T] {

	val self: Sequence[T] = this;

	def iterator(): Iter[T] = {

		var thisSelf = self;

		var ans : Iter[T] = new Singleton[T](thisSelf.del(0));
		var i = 0;

		while(thisSelf.length > 0) {
			ans = new Cons(thisSelf.del(0), ans);
		}

		return ans;
	}

}
