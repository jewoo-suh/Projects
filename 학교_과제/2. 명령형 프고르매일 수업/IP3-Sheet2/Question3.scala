trait Iter[T] { 
	def hasNext: Boolean 
	def next(): T 
}

trait Cursor[T] { 
	def hasCurrent: Boolean 
	def next(): Unit 
	def current: T 
}

class Iter2Cursor[T](it: Iter[T]) extends Cursor[T] {

	var _current: T = _
	var _valid = false

	def hasCurrent: Boolean = { 
		if (! _valid) {
			_valid = it.hasNext
			if ( _valid) _current = it.next()
		}
		_valid
	}

	def current: T = if (hasCurrent)
						_current
					else
						scala.sys.error("_current␣when␣!has_current")

	def next(): Unit = if (hasCurrent)
							_valid = false
						else
							scala.sys.error("next(␣when␣!has_current")

}

class Cursor2Iter[T](cr: Cursor[T]) extends Iter[T] {

	var thisCursor: Cursor[T] = cr

	def hasNext : Boolean = thisCursor.hasCurrent;
	def next() : T = {
		require(this.hasNext);
		thisCursor.next();
		return thisCursor.current;
	}

}