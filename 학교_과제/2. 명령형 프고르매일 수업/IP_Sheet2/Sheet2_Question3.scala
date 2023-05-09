import org.scalatest.funsuite.AnyFunSuite

class Sheet2_Question3 extends AnyFunSuite{ 

  //Testing .sorted for arrays...

  val digits = Array(5,4,3,2,1)
  test("[5,4,3,2,1] is ordered"){ assert(digits===digits.sorted) }

  val digits2 = Array(1,2,3,4,5)
  test("[1,2,3,4,5] is ordered"){ assert(digits2===digits2.sorted) }

  val chars = Array('a', 'b', 'c', 'd', 'e')
  test("['a','b','c','d','e'] is ordered"){ assert(chars===chars.sorted) }

  val chars2 = Array('z', 'b', 'c', 'd', 'e')
  test("['z','b','c','d','e'] is ordered"){ assert(chars2===chars2.sorted) }

  val words = Array("a", "ab", "abc", "abcd", "abcde")
  test("[a,ab,abc,abcd,abcde] is ordered"){ assert(words===words.sorted) }

  val words2 = Array("abcdef", "ab", "abc", "abcd", "abcde")
  test("[abcdef,ab,abc,abcd,abcde] is ordered"){ assert(words2===words2.sorted) }
  /*
    To run the following scalaTest, please compile using
    fsc -cp ./scalatest-app_2.13-3.2.2.jar Sheet2_Question3.scala 
    And run using
    scala -cp ./scalatest-app_2.13-3.2.2.jar:./scala-xml_2.13-1.2.0.jar org.scalatest.run Sheet2_Question3
  */

}