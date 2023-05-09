import org.scalatest.funsuite.AnyFunSuite
import scala.collection.immutable.HashSet

class Sheet4_Question1 extends AnyFunSuite{ 

  val testHashSet = new scala.collection.mutable.HashSet[String]

  testHashSet.add("scala"); testHashSet.add("scala"); 
  testHashSet.add("python"); testHashSet.add("haskell");

  test("Testing Add: HashSet contains \"python\""){assert( testHashSet.contains("python") )}
  test("Testing Add: HashSet contains \"haskell\""){assert( testHashSet.contains("haskell") )}
  test("Testing Add: HashSet doesn't contain \"c++\""){assert( testHashSet.contains("c++") )}

  val testHashSet2 = new scala.collection.mutable.HashSet[String]

  testHashSet2.add("scala"); testHashSet2.add("scala"); 
  testHashSet2.add("python"); testHashSet2.add("haskell");
  testHashSet2.remove("scala");

  test("Testing Remove: HashSet doesn't contain \"scala\""){assert( testHashSet2.contains("scala") )}
  test("Testing Remove: HashSet contains \"python\""){assert( testHashSet2.contains("python") )}
  test("Testing Remove: HashSet contains \"haskell\""){assert( testHashSet2.contains("haskell") )}
  
  val testHashSet3 = new scala.collection.mutable.HashSet[String]

  testHashSet3.add("scala"); testHashSet3.add("python"); testHashSet3.add("haskell");

  test("Testing Size: HashSet doesn't have size of 2"){assert( testHashSet3.size == 2 )}
  test("Testing Size: HashSet has size of 3"){assert( testHashSet3.size == 3 )}

  val testHashSet4 = new scala.collection.mutable.HashSet[String]

  test("Testing isEmpty: HashSet is empty"){assert( testHashSet4.isEmpty)}

  val testHashSet5 = new scala.collection.mutable.HashSet[String]

  testHashSet5.add("scala"); testHashSet5.add("scala"); 
  testHashSet5.add("python"); testHashSet5.add("haskell");
  testHashSet5.remove("c++");

  test("Testing remove: When the variable \"c++\" is not in the HashSet"){assert( testHashSet5.contains("c++") )}

  val testHashSet6 = new scala.collection.mutable.HashSet[String]

  test("Testing remove: When the HashSet is already empty"){assert( testHashSet6.isEmpty )}

  val testHashSet7 = new scala.collection.mutable.HashSet[String]

  test("Testing intercept: When the HashSet is already empty"){assertThrows[NoSuchElementException]( testHashSet7.last == "scala" )}

  /*
    To run the following scalaTest, please compile using
    fsc -cp ./scalatest-app_2.13-3.2.2.jar Sheet4_Question1.scala 
    And run using
    scala -cp ./scalatest-app_2.13-3.2.2.jar:./scala-xml_2.13-1.2.0.jar org.scalatest.run Sheet4_Question1
  */

}