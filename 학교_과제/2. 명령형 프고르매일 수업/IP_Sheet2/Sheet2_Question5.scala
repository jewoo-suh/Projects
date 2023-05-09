import org.scalatest.funsuite.AnyFunSuite

class Sheet2_Question5 extends AnyFunSuite{ 

	def searchA(patt: Array[Char], line: Array[Char]) : Boolean = {
 		val K = patt.size; val N = line.size
 		var j = 0; var found = true;
 		while(j <= N-K && !found){

 			// set found if line[j..j+K) = patt[0..K)
	 		// Invariant: line[j..j+k) = patt[0..k)

	 		var k = 0

	 		while(k<K && line(j+k)==patt(k)) k = k+1
	 		
	 		found = (k==K)
	 		j = j+1}
	 	return found;
	 }

	 def searchB(patt: Array[Char], line: Array[Char]) : Boolean = {
 		val K = patt.size; val N = line.size
 		var j = 0; var found = false;
 		while(j < N-K && !found){
	 		var k = 0
	 		while(k<K && line(j+k)==patt(k)) k = k+1
	 		found = (k==K)
	 		j = j+1}
	 	return found;
	 }

	 def searchC(patt: Array[Char], line: Array[Char]) : Boolean = {
 		val K = patt.size; val N = line.size
 		var j = 0; var found = false;
 		while(j <= N-K+1 && !found){
	 		var k = 0
	 		while(k<K && line(j+k)==patt(k)) k = k+1
	 		found = (k==K)
	 		j = j+1}
	 	return found;
	 }

	 def searchD(patt: Array[Char], line: Array[Char]) : Boolean = {
 		val K = patt.size; val N = line.size
 		var j = 0; var found = false;
 		while(j <= N-K && !found){
	 		var k = 1
	 		while(k<K && line(j+k)==patt(k)) k = k+1
	 		found = (k==K)
	 		j = j+1}
	 	return found;
	 }

	 def searchE(patt: Array[Char], line: Array[Char]) : Boolean = {
 		val K = patt.size; val N = line.size
 		var j = 0; var found = false;
 		while(j <= N-K && !found){
	 		var k = 0
	 		while(k <= K && line(j+k)==patt(k)) k = k+1
	 		found = (k==K)
	 		j = j+1}
	 	return found;
	 }

	 def searchF(patt: Array[Char], line: Array[Char]) : Boolean = {
 		val K = patt.size; val N = line.size
 		var j = 0; var found = false;
 		while(j <= N-K && !found){
	 		var k = 0
	 		while(k<K && line(j+k)==patt(k)) k = k+1
	 		found = (k>=K)
	 		j = j+1}
	 	return found;
	 }

  	test("(a) Error caused when compiling: search('abc', 'wxyz') -> Expected: False, Returned: True"){ 
  		assert(searchA(Array('a','b','c'), Array('x','y','z','w'))) 
  	}

  	test("(b) Error caused when compiling: search('abc', 'xabc') -> Expected: True, Returned: False"){ 
  		assert(searchB(Array('a','b','c'), Array('x','a','b','c'))) 
  	}

  	test("(c) Error caused when compiling: search('abc', 'xyab') -> Out of Bounds Exception"){ 
  		assert(searchC(Array('a','b','c'), Array('x','y','a','b'))) 
  	}

  	test("(d) Error caused when compiling: search('abc', 'xbcy') -> Expected: False, Returned: True"){ 
  		assert(searchD(Array('a','b','c'), Array('x','b','c','y'))) 
  	}

  	test("(e) Error caused when compiling: search('abc', 'abc') -> Out of Bounds Exception"){ 
  		assert(searchE(Array('a','b','c'), Array('a','b','c'))) 
  	}

  	test("""(f) Should not cause an error as the code segment 
  		while(k<K && line(j+k)==patt(k)) k = k+1 
  	prevents k from becoming greater than K (the while loop will terminate with max k value K)
  	Thus changing k==K to k>=K should have no impact on the functionality of the code, 
  	as the only time k>=K can be true is when k==K"""){ 
  		assert(searchF(Array('a','b'), Array('b','a','b','a'))) 
  	}

  
  /*
    To run the following scalaTest, please compile using
    fsc -cp ./scalatest-app_2.13-3.2.2.jar Sheet2_Question5.scala 
    And run using
    scala -cp ./scalatest-app_2.13-3.2.2.jar:./scala-xml_2.13-1.2.0.jar org.scalatest.run Sheet2_Question5
  */

}