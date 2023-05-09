/** state: S : [0..N)
  * init: S = {} */

trait IntSet{

	/** Add elem to the set.
	  * pre: elem ∈ [0..N)
	  * post: S = S0 U {elem} */
	def add(elem: Int) : Unit

	/** Does the set contain elem?
	  * post: S = S0 ∧ returns elem ∈ S */
	def contains(elem: Int) : Boolean

	/** Remove elem from the set.
	  * post: S = S0 − {elem} */
	def remove(elem: Int) : Unit

	/** The size of the set.
	  * post: S = S0 ∧ returns #S */
	def size : Int

}

class BitMapSet extends IntSet {

	private val MAX = 1000;
	private val booleans = new Array[Boolean](MAX);
	private var maxIndex = -1; //Initialized as -1 for the case where the IntSet is empty

	// Abs: IntSet = {if (booleans(i)) ∃ i | i <- [0..MAX)}
  	// DTI: 0 <= maxIndex <= MAX && 
  	// 		∀i ∈ (maxIndex..MAX), booleans(i) == false 

	def add(elem: Int) : Unit = {
		assert(elem < MAX);
		booleans(elem) = true;
		if(elem > maxIndex) maxIndex = elem;
	}

	def contains(elem: Int) : Boolean = {
		return booleans(elem);
	}

	def remove(elem: Int) : Unit = {
		assert(elem < MAX);
		booleans(elem) = false;
	}

	def size : Int = {
		var count = 0; var i = 0;
		while(i <= maxIndex) {
			if(booleans(i)) count += 1;
			i += 1;
		}
		return count;
	}

	//For Question 4
	def head : Int = {
		var i = 0;
		while(i < MAX && !booleans(i)) i += 1;
		return i;
	}

}