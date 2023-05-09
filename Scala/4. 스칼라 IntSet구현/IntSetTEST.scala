import org.scalatest.funsuite.AnyFunSuite
import scala.collection.immutable.HashSet

class IntSet{
  // State: S : P(Int) (where "P" represents power set)

  // The following lines just define some aliases, so we can subsequently
  // write "Node" rather than "IntSet.Node".
  private type Node = IntSet.Node
  // Constructor
  private def Node(datum: Int, next: Node) = new IntSet.Node(datum, next)

  // Init: S = {}
  private var theSet : Node = new Node(Int.MaxValue, null) // or however empty set is represented

  /** Convert the set to a string.
    * (The given implementation is not sufficient.) 
    * Time Complexity: O(n) */
  override def toString : String = {
    var n = theSet; var str = "{";

    if(n.next == null) {return (str + "}");}
    else {str = str + n.datum.toString;}

    n = n.next;

    while (n.next != null) {
      str = str + ", " + n.datum.toString;
      n = n.next;
    }

    return str + "}";
    
  }

  /** Add element e to the set
    * Post: S = S_0 U {e} 
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
    * Time Complexity: O(n) */
  def size : Int = {
    var n = theSet; var count = 0;

    while(n.next != null) {
      count += 1;
      n = n.next;
    }

    return count;
  }

  /** Does the set contain e?
    * Post: S = S_0 && returns (e in S) 
    * Time Complexity: O(n) */
  def contains(e: Int) : Boolean = {
    var n = theSet;

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
    * Time Complexity: O(n) */
  override def equals(that: Any) : Boolean = that match {
    case s: IntSet => {
      var n = theSet; var m = s.theSet;
      if(size != s.size) {return false;}
      else {
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
      while(n.datum != e) {
        n = n.next;
      }
      n.next = n.next.next;
      return(true);
    }
  }
    
  /** Test whether this is a subset of that.
    * Post S = S_0 && returns S subset-of that.S 
    * Time Complexity: O(n^2) */
  def subsetOf(that: IntSet) : Boolean = {
    var n = theSet; var m = that.theSet;
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

class IntSetTEST extends AnyFunSuite{ 

  var n1 = IntSet(1, 2, 3, 4, 5)
  var n2 = IntSet(1, 2, 3)
  var n3 = IntSet(1, 2, 7)
  var n4 = IntSet(1, 2, 3)
  var n5 = IntSet()

  var x = 0;

  println("The initial arrays are as follows")
  println("n1: " + n1);
  println("n2: " + n2);
  println("n3: " + n3);
  println("n4: " + n4);
  println("n5: " + n5);
  println();

  test("----------Testing Contains----------"){assert (x == 0)}
  test("Testing contains: n1 contains 1"){assert (n1.contains(1))}
  test("Testing contains: n1 contains 5?"){assert (n1.contains(5))}
  test("Testing contains: n1 contains 7?"){assert (!n1.contains(7))}
  test("Testing contains: n2 contains 1?"){assert (n2.contains(1))}
  test("Testing contains: n2 contains 5?"){assert (!n2.contains(5))}
  test("Testing contains: n5 contains 0?"){assert (!n5.contains(0))}

  test("----------Testing Add----------"){assert (x == 0)}
  n1.add(5)
  test("Testing add: Adding 5 to n1; n1 contains 5"){assert (n1.contains(5))}
  n1.add(6)
  test("Testing add: Adding 5 to n1; n1 contains 6"){assert (n1.contains(6))}
  n3.add(0)
  test("Testing add: Adding 0 to n3; n3 contains 0"){assert (n3.contains(0))}
  n3.add(2)
  test("Testing add: Adding 0 to n3; n3 contains 2"){assert (n3.contains(2))}

  test("----------Testing Size----------"){assert (x == 0)}
  test("Testing size: n1 has size 5"){assert (!(n1.size == 5))}
  test("Testing size: n2 has size 3"){assert (n2.size == 3)}
  test("Testing size: n3 has size 3"){assert (!(n3.size == 3))}
  test("Testing size: n4 has size 3"){assert (!(n4.size == 3))}
  test("Testing size: n5 has size 0"){assert (n5.size == 0)}

  test("----------Testing Any----------"){assert (x == 0)}
  test("(Reminder: Any returns the head of the set"){assert (x == 0)}
  test("Testing any: any element of n1 is 1"){assert (n1.any == 1)}
  test("Testing any: any element of n2 is 1"){assert (n2.any == 1)}
  test("Testing any: any element of n3 is 1"){assert (!(n3.any == 1))}
  test("Testing any: any element of n4 is 1"){assert (!(n4.any == 1))}
  test("Testing any: any element of n5 is 1"){assert (!(n5.any == 1))}

  test("----------Testing Equals----------"){assert (x == 0)}
  test("Testing equals: n2 equals n1"){assert (!n2.equals(n1))}
  test("Testing equals: n2 equals n2"){assert (n2.equals(n2))}
  test("Testing equals: n2 equals n3"){assert (!n2.equals(n3))}
  test("Testing equals: n2 equals n4"){assert (!n2.equals(n4))}
  test("Testing equals: n2 equals n5"){assert (!n2.equals(n5))}
  test("Testing equals: n5 equals n1"){assert (!n5.equals(n1))}
  test("Testing equals: n5 equals n2"){assert (!n5.equals(n2))}
  test("Testing equals: n5 equals n3"){assert (!n5.equals(n3))}
  test("Testing equals: n5 equals n4"){assert (!n5.equals(n4))}
  test("Testing equals: n5 equals n5"){assert (n5.equals(n5))}

  test("----------Testing Remove----------"){assert (x == 0)}
  n4.remove(0)
  test("Testing remove: Removing 0 from n4; n4 contains 0"){assert (!n4.contains(0))}
  n4.remove(1)
  test("Testing remove: Removing 1 from n4; n4 contains 1"){assert (!n4.contains(1))}
  n4.remove(2)
  test("Testing remove: Removing 2 from n4; n4 contains 2"){assert (!n4.contains(2))}

  test("----------Attempting to Add/Remove Int.MaxValue----------"){assert (x == 0)}
  test("(Reminder: Int.MaxValue should not be able to be added/removed"){assert (x == 0)}
  n1.add(Int.MaxValue)
  test("Testing Add: Adding Int.MaxValue to n1")(assert (!n1.contains(Int.MaxValue)))
  n1.remove(Int.MaxValue)
  test("Testing Add: Removing Int.MaxValue to n1")(assert (!n1.contains(Int.MaxValue)))

  test("----------Testing SubsetOf----------"){assert (x == 0)}
  test("Testing SubsetOf: n2 is subset of n1"){assert (n2.subsetOf(n1))}
  test("Testing SubsetOf: n2 is subset of n2"){assert (n2.subsetOf(n2))}
  test("Testing SubsetOf: n2 is subset of n3"){assert (!n2.subsetOf(n3))}
  test("Testing SubsetOf: n2 is subset of n4"){assert (!n2.subsetOf(n4))}
  test("Testing SubsetOf: n4 is subset of n1"){assert (n4.subsetOf(n1))}
  test("Testing SubsetOf: n4 is subset of n2"){assert (n4.subsetOf(n2))}
  test("Testing SubsetOf: n4 is subset of n3"){assert (!n4.subsetOf(n3))}
  test("Testing SubsetOf: n4 is subset of n4"){assert (n4.subsetOf(n4))}

  test("----------Testing toString----------"){assert (x == 0)}
  test("Testing toString: n1 is {1, 2, 3, 4, 5, 6}"){assert (n1.toString == "{1, 2, 3, 4, 5, 6}")}
  test("Testing toString: n2 is {1, 2, 3}"){assert (n2.toString == "{1, 2, 3}")}
  test("Testing toString: n3 is {0, 1, 2, 7}"){assert (n3.toString == "{0, 1, 2, 7}")}
  test("Testing toString: n4 is {3}"){assert (n4.toString == "{3}")}
  test("Testing toString: n5 is {}"){assert (n5.toString == "{}")}












  /*
    To run the following scalaTest, please compile using
    fsc -cp ./scalatest-app_2.13-3.2.2.jar Sheet4_Question1.scala 
    And run using
    scala -cp ./scalatest-app_2.13-3.2.2.jar:./scala-xml_2.13-1.2.0.jar org.scalatest.run Sheet4_Question1
  */

}