import Patterns._
	def range(lo: Int, hi: Int): Cursor[Int] =
		new Cursor[Int] {
			var current = lo
			def hasCurrent = current<hi
			def next = current += 1
}
