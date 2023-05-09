// A class of objects to represent a set
// The class is constructed with a dummy tail that has a value of Int.MaxValue

class IntSet{
  // State: S : P(Int) (where "P" represents power set)

  // The following lines just define some aliases, so we can subsequently
  // write "Node" rather than "IntSet.Node".
  private type Node = IntSet.Node
  // Constructor
  private def Node(datum: Int, next: Node) = new IntSet.Node(datum, next)

  // Init: S = {}
  private var theSet : Node = new Node(Int.MaxValue, null) // or however empty set is represented
  //Abs: IntSet = {a_1, a_2, ..., a_n | ∀ i ∈ [1..n], a_i ∈ N}
  //DTI: ∀ n ∈ theSet, if(n.next != null) {n.datum < Int.MaxValue}
  //        && ∀ n ∈ theSet, iff(n.datum == Int.MaxValue) {n.next == null}

  /** Convert the set to a string.
    * (The given implementation is not sufficient.) 
    * Time Complexity: O(n) */
  override def toString : String = {
    var n = theSet; var str = "{";

    if(n.next == null) {return (str + "}");}
    else {str = str + n.datum.toString;}

    n = n.next;

    //Invariant I: str = "{a_1, a_2, ... , a_i" && i.next != null ∀ i ∈ theSet
    while (n.next != null) {
      str = str + ", " + n.datum.toString;
      n = n.next;
    }

    return str + "}";
    
  }

  /** Add element e to the set
    * Post: S = S_0 U {e} 
    * The add function was designed to add integers to the set in increasing order;
    * the increasing order tactic allows for O(N) time complexities for 'equals' and 'subset' functions
    * Also, it does not add if the set already contains the input
    * Time Complexity: O(n) -> It is possible to make this O(1), but making add O(n) saves complexity for
    * other functions below */
  def add(e: Int) : Unit = {
    var n = theSet;
    if(e == Int.MaxValue) {println("Cannot add value as it inteferes with dummy");}

    if(n.next == null || (e < n.datum && !contains(e))) {
      theSet = new Node (e, n);
    }
    else if(!contains(e) && (e != Int.MaxValue)) {
      while(e > n.next.datum) {
        n = n.next;
      }
      n.next = new Node (e, n.next);
    }
  }

  /** Length of the list
    * Post: S = S_0 && returns #S 
    * The size function simply increments a counter variable until n.next == null
    * Time Complexity: O(n) */
  def size : Int = {
    var n = theSet; var count = 0;

    //Invariant I: 0 <= count <= i && i.next != null ∀ i ∈ N
    while(n.next != null) {
      count += 1;
      n = n.next;
    }

    return count;
  }

  /** Does the set contain e?
    * Post: S = S_0 && returns (e in S) 
    * The contains function iterates through theSet until it either
    * (1) find the parameter, in which true is given or
    * (2) either n.next == null or n.datum > e - when n.datum > e, all the nodes withint theSet
    * after 'n' will have a datum greater than e, as the set is in increasing order
    * although the time complexity is still O(N), this will, on average, be faster than
    * a while loop with just n.next != null
    * Time Complexity: O(n) */
  def contains(e: Int) : Boolean = {
    var n = theSet;

    //Invariant I: i.datum <= e && i.next != null
    while(n.next != null && n.datum <= e) {
      if (n.datum == e) {return true;}
      n = n.next;
    }

    return false;
  }

  /** Return any member of the set.  (This is comparable to the operation
    * "head" on scala.collection.mutable.Set, but we'll use a name that does
    * not suggest a particular order.)
    * Pre: S != {}
    * Post: S = S_0 && returns e s.t. e in S 
    * The simplest way seemed to either return the head, or if the set is empty print a warning 
    * Time Complexity: O(1) */
  def any : Int = {
    var n = theSet;
    if(n.next == null) {
      println("The set is empty, returning dummy tail datum value");
    }
    return theSet.datum;
  }

  /** Does this equal that?
    * Post: S = S_0 && returns that.S = S 
    * The function first checks if the sizes are the same; if so, 
    * it runs a while loop to check if the datum for nodes in the same place are the same;
    * since both sets are in increasing order, this is possible
    * if at least one is different, it returns false
    * Time Complexity: O(n) */
  override def equals(that: Any) : Boolean = that match {
    case s: IntSet => {
      var n = theSet; var m = s.theSet;
      if(size != s.size) {return false;}
      else {
        //Invariant I: i.datum == that.i.datum && i.next != null ∀ i ∈ theSet
        while(n.next != null) {
          if(n.datum != m.datum) {return false;}
          n = n.next; m = m.next;
        }
      }
      return true;
    }
    case _ => false
  }

  /** Remove e from the set; result says whether e was in the set initially
    * Post S = S_0 - {e} && returns (e in S_0) 
    * The function checks if the set contains the input, and then if so, 
    * fincd the node and simply connects the node before it to the one after
    * Time Complexity: O(n) */
  def remove(e: Int) : Boolean = {
    var n = theSet;
    if(e == Int.MaxValue) {println("Cannot remove value as it inteferes with dummy"); return false;}
    if(!contains(e)) {return false}
    else if (n.datum == e) {
      theSet = n.next;
      return true;
    }
    else {
      //Invariant I: i.datum != e && i.next != null
      while(n.datum != e) {
        n = n.next;
      }
      n.next = n.next.next;
      return(true);
    }
  }
    
  /** Test whether this is a subset of that.
    * Post S = S_0 && returns S subset-of that.S 
    * The function iterates through the datum of this set, comparing it to the
    * datum of that set; if m.next == null is met before n.next == null, it indicates
    * there are some datums of n not included in that, returning false
    * Time Complexity: O(n) */
  def subsetOf(that: IntSet) : Boolean = {
    var n = theSet; var m = that.theSet;
    //Invariant I: that.contains(i.datum) && i.next != null ∀ i ∈ theSet
    while(n.next != null) {
      if(m.next == null) {return false;}

      if(n.datum == m.datum) {
        n = n.next; m = m.next;
      }

      else {m = m.next;}
    }
    return true;
  }

  // ----- optional parts below here -----

  /** return union of this and that.  
    * Post: S = S_0 && returns res s.t. res.S = this U that.S */
  def union(that: IntSet) : IntSet = ???

  /** return intersection of this and that.  
    * Post: S = S_0 && returns res s.t. res.S = this intersect that.S */
  def intersect(that: IntSet) : IntSet = ???

  /** map
    * Post: S = S_0 && returns res s.t. res.S = {f(x) | x <- S} */
  def map(f: Int => Int) : IntSet = ???

  /** filter
    * Post: S = S_0 && returns res s.t. res.S = {x | x <- S && p(x)} */
  def filter(p : Int => Boolean) : IntSet = ???
}


// The companion object
object IntSet{
  /** The type of nodes defined in the linked list */
  private class Node(var datum: Int, var next: Node)

  /** Factory method for sets.
    * This will allow us to write, for example, IntSet(3,5,1) to
    * create a new set containing 3, 5, 1 -- once we have defined 
    * the main constructor and the add operation. 
    * post: returns res s.t. res.S = {x1, x2,...,xn}
    *       where xs = [x1, x2,...,xn ] */
  def apply(xs: Int*) : IntSet = {
    val s = new IntSet; for(x <- xs) s.add(x); s
  }

}

object main{
    def main(args: Array[String]) : Unit = {

      var n1 = IntSet(1, 2, 3, 4, 5)
      var n2 = IntSet(1, 2, 3)
      var n3 = IntSet(1, 2, 7)
      var n4 = IntSet(1, 2, 3)
      var n5 = IntSet()

      println("n1: " + n1);
      println("n2: " + n2);
      println("n3: " + n3);
      println("n4: " + n4);
      println("n5: " + n5);
      println();

      
      println("Add 5 and 6 to n1");
      n1.add(5);
      n1.add(6);
      println("n1: " + n1);
      println();
      println("Add 0 and 2 to n3");
      n3.add(0);
      n3.add(2);
      println("n3: " + n3);
      println();

      println("Size of n1: " + n1.size);
      println("Size of n2: " + n2.size);
      println("Size of n3: " + n3.size);
      println("Size of n4: " + n4.size);
      println("Size of n5: " + n5.size);
      println();

      print("Currently")
      println(" n1: " + n1);
      println("Does n1 contain 1? " + n1.contains(1));
      println("Does n1 contain 2? " + n1.contains(2));
      println("Does n1 contain 5? " + n1.contains(5));
      println("Does n1 contain 10? " + n1.contains(10));
      println();

      println("Any of n1: " + n1.any);
      println("Any of n2: " + n2.any);
      println("Any of n3: " + n3.any);
      println("Any of n4: " + n4.any);
      println("Any of n5: " + n5.any);
      println();
      
      println("n2 equals n1: " + n2.equals(n1));
      println("n2 equals n3: " + n2.equals(n3));
      println("n2 equals n4: " + n2.equals(n4));
      println("n2 equals n5: " + n2.equals(n5));
      println();

      println("n5 equals n1: " + n5.equals(n1));
      println("n5 equals n2: " + n5.equals(n2));
      println("n5 equals n3: " + n5.equals(n3));
      println("n5 equals n4: " + n5.equals(n4));
      println();

      println("Remove 0, 1 and 2 from n4");
      n4.remove(0);
      n4.remove(1);
      n4.remove(2);
      println("n4: " + n4);
      println();
      println("Add 1 back to n4");
      n4.add(1);
      println("n4: " + n4);
      println();
      
      println("Attempt to Add/Remove Int.MaxValue from n1");
      print("Trying Add: ");
      n1.add(Int.MaxValue);
      print("Trying Remove: ");
      n1.remove(Int.MaxValue);
      println();

      println("Currently: ")
      println("n1: " + n1);
      println("n2: " + n2);
      println("n3: " + n3);
      println("n4: " + n4);
      println();
      
      println("Testing for subset function");
      println("n2 is subset of n1: " + n2.subsetOf(n1));
      println("n2 is subset of n2: " + n2.subsetOf(n2));
      println("n2 is subset of n3: " + n2.subsetOf(n3));
      println("n2 is subset of n4: " + n2.subsetOf(n4));
      println();
      println("n4 is subset of n1: " + n4.subsetOf(n1));
      println("n4 is subset of n2: " + n4.subsetOf(n2));
      println("n4 is subset of n3: " + n4.subsetOf(n3));
      println("n4 is subset of n4: " + n4.subsetOf(n4));
      println();
      

    }
}