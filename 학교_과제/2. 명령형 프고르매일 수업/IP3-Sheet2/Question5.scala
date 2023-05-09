

trait Counting[T] extends Sequence[T] {
	val pred: T => Boolean

	var count: Long = 0

	abstract override def ins(pos: Int, t: T): Unit =
	{ super.ins(pos, t)
	if (pred(t)) { count += 1 }
	}

	abstract override def del(pos: Int): T =
	{ val t = super.del(pos)
	if (pred(t)) { count -= 1 }
	t
	}
}


class CharSeq(initial: String) extends Sequence[Char] {
	private var rep = initial.toList

	def ins(pos: Int, t: Char): Unit =
		{ rep = rep.take(pos) ++ List(t) ++ rep.drop(pos) }

	def del(pos: Int): Char = {
		val t = rep(pos)
		rep = rep.take(pos) ++ rep.drop(pos+1)
		t
	}

	def apply(pos: Int): Char = rep(pos)

	def length: Int = rep.length

	override def toString: String = rep.mkString("CharSeq(\"","", "\")")
}

object Question5 {

	def main(args: Array[String]) = {

		var test1 = new CharSeq("abcdefghijkl") with Counting[Char] {val pred = _.isUpper;}

	}

}
