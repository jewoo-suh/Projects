/** state: S : A
  * init: S = {} */

trait Set[A] {

	/** Add new element to set
	  * post: S = S0 ++ {elem} */
	def push(elem: A) : Unit

	/** Remove the most recently added element
	  * post: S = S0[0..(S0.size -1))*/
	def pop : Unit 

	/** Check if set is empty
	  * post: S = S0^ returns S=={} */
	def isEmpty : Boolean

}
