/** Import is for readLine so that we can write input directly to the program */
import scala.io.StdIn

object Brack{
	//Maximum length of word so we can define our arrays in dynamic programming
	val MAXWORD = 30

	//Operation to take 'A', 'B' and 'C' to corresponding Ints
  def LetterToInt(a: Char) : Int = {
		if(a == 'A' || a == 'B' || a == 'C'){
			return (a.toInt - 'A'.toInt);
		} else{
			println("Please only Letters from A,B,C.")
			sys.exit
		}
	}
	
  //Defining the op array for everything to use
  val op = Array.ofDim[Int](3,3)  
  op(0)(0) = 1; op(0)(1) = 1; op(0)(2) = 0
	op(1)(0) = 2; op(1)(1) = 1; op(1)(2) = 0
	op(2)(0) = 0; op(2)(1) = 2; op(2)(2) = 2

  /** Read file into array (discarding the EOF character) */
  def readFile(fname: String) : Array[Char] = 
    scala.io.Source.fromFile(fname).toArray.init

 
  /* Functions below here need to be implemented */


	//TASK 1
	//PossibleRec checks whether bracketing to something is possible recursively
	//Checks whether w(i,j) can be bracketed to z
	
	def PossibleRec(w: Array[Int], i: Int, j: Int, z:Int): Boolean = {

		if(i+1 == j) {return w(i) == z}

		else {

			for(k <- i+1 to j-1) {

				if(z == 0) 
				{
					if( (PossibleRec(w, i, k, 0) && PossibleRec(w, k, j, 2)) || 
							(PossibleRec(w, i, k, 1) && PossibleRec(w, k, j, 2)) || 
							(PossibleRec(w, i, k, 2) && PossibleRec(w, k, j, 0))
						) {return true;}
				}
				if(z == 1) 
				{
					if( (PossibleRec(w, i, k, 0) && PossibleRec(w, k, j, 0)) || 
							(PossibleRec(w, i, k, 0) && PossibleRec(w, k, j, 1)) || 
							(PossibleRec(w, i, k, 1) && PossibleRec(w, k, j, 1))
						) {return true;}
				}
				if(z == 2) 
				{
					if( (PossibleRec(w, i, k, 1) && PossibleRec(w, k, j, 0)) || 
							(PossibleRec(w, i, k, 2) && PossibleRec(w, k, j, 1)) || 
							(PossibleRec(w, i, k, 2) && PossibleRec(w, k, j, 2))
						) {return true;}
				}
			}
		}
		return false;
	} 

	
	//TASK 2
	//NumberRec which checks the ways you get a result recursively
	//Computes number of ways w(i,j) can be bracketed to get z
	
	def NumberRec(w: Array[Int], i: Int, j: Int, z:Int): Int = {
		
		var counter = 0;

		if(i+1==j) 
		{
			if(w(i) == z) {return 1}
			else {return 0}
		}

		else 
		{
			for(k <- i+1 to j-1)
			{
				if(z == 0)
				{
					if(PossibleRec(w, i, k, 0) && PossibleRec(w, k, j, 2)) 
						{counter += NumberRec(w, i, k, 0) * NumberRec(w, k, j, 2)}
					if(PossibleRec(w, i, k, 1) && PossibleRec(w, k, j, 2)) 
						{counter += NumberRec(w, i, k, 1) * NumberRec(w, k, j, 2)}
					if(PossibleRec(w, i, k, 2) && PossibleRec(w, k, j, 0)) 
						{counter += NumberRec(w, i, k, 2) * NumberRec(w, k, j, 0)}
				}
				if(z == 1)
				{
					if(PossibleRec(w, i, k, 0) && PossibleRec(w, k, j, 0)) 
						{counter += NumberRec(w, i, k, 0) * NumberRec(w, k, j, 0)}
					if(PossibleRec(w, i, k, 0) && PossibleRec(w, k, j, 1)) 
						{counter += NumberRec(w, i, k, 0) * NumberRec(w, k, j, 1)}
					if(PossibleRec(w, i, k, 1) && PossibleRec(w, k, j, 1)) 
						{counter += NumberRec(w, i, k, 1) * NumberRec(w, k, j, 1)}
				}
				if(z == 2)
				{
					if(PossibleRec(w, i, k, 1) && PossibleRec(w, k, j, 0)) 
						{counter += NumberRec(w, i, k, 1) * NumberRec(w, k, j, 0)}
					if(PossibleRec(w, i, k, 2) && PossibleRec(w, k, j, 1)) 
						{counter += NumberRec(w, i, k, 2) * NumberRec(w, k, j, 1)}
					if(PossibleRec(w, i, k, 2) && PossibleRec(w, k, j, 2)) 
						{counter += NumberRec(w, i, k, 2) * NumberRec(w, k, j, 2)}
				}
			}
		}
		return counter;
	} 

	
	//TASK 3
	/*
	
	The time for PossibleRec(w, 0, n, z) and NumberRec(w, 0, n, z) should take 

	We notice that for the recursion format, the time complexity is
	T(n) = 6T(n-1) + 6T(n-2) + 6T(n-3) + 6T(n-4) +....+ 6T(1)
	T(n-1) = 6T(n-2) + 6T(n-3) + 6T(n-4) + 6T(n-5) +....+ 6T(1)
	T(n) = 7T(n-1)
	and time complexity is O(7^n)

	*/
	//Test Case 1
	/*
	jewoosuh@DESKTOP-1VVIMQL:/mnt/c/users/jewoo/Downloads/ScalaTest$ time scala Brack.scala -PossibleRec
	warning: 8 deprecations (since 2.13.0)
	warning: 5 deprecations (since 2.13.3)
	warning: 13 deprecations in total; re-run with -deprecation for details
	ABBA
	Bracketing values for ABBA
	A is Possible
	B is Possible
	C is Possible

	real    0m5.183s
	user    0m20.137s
	sys     0m0.992s

	jewoosuh@DESKTOP-1VVIMQL:/mnt/c/users/jewoo/Downloads/ScalaTest$ time scala Brack.scala -NumberRec
	warning: 8 deprecations (since 2.13.0)
	warning: 5 deprecations (since 2.13.3)
	warning: 13 deprecations in total; re-run with -deprecation for details
	ABBA
	Bracketing values for ABBA
	A can be achieved in 2 ways
	B can be achieved in 1 way
	C can be achieved in 2 ways

	real    0m5.398s
	user    0m17.558s
	sys     0m0.882s
	*/

	//Test Case 2
	/*
	jewoosuh@DESKTOP-1VVIMQL:/mnt/c/users/jewoo/Downloads/ScalaTest$ time scala Brack.scala -NumberRec
	warning: 8 deprecations (since 2.13.0)
	warning: 5 deprecations (since 2.13.3)
	warning: 13 deprecations in total; re-run with -deprecation for details
	BACB
	Bracketing values for BACB
	A can be achieved in 0 ways
	B can be achieved in 1 way
	C can be achieved in 4 ways

	real    0m6.331s
	user    0m20.322s
	sys     0m0.972s
	*/
	
	
	//You may find the following class useful for Task 7
	// Binary tree class
	abstract class BinaryTree
	case class Node (left : BinaryTree, right : BinaryTree) extends BinaryTree
	case class Leaf (value : Char) extends BinaryTree

	//Printing for a binary tree
	def print_tree(t : BinaryTree) {
	//TODO(optional)
	}

	//These arrays should hold the relevant data for dynamic programming
	var poss = Array.ofDim[Boolean](MAXWORD, MAXWORD, 3)
	var ways = Array.ofDim[Int](MAXWORD, MAXWORD, 3)
	var exp = Array.ofDim[BinaryTree](MAXWORD, MAXWORD, 3)


	//Task 4, 5, and 7(optional)
	//TODO Fill out arrays with dynamic programming solution
	
	
	def Tabulate(w: Array[Int], n: Int): Unit = {

		for(i <- 0 to n-1) {
			poss(i)(i+1)(w(i)) = true;
			ways(i)(i+1)(w(i)) += 1;
		}

		//O(n^3)
		for(i <- 2 to n) {
			for(j <- 0 to n-i) {
				for(k <- 1 to i-1) {
					for(i1 <- 0 to 2) {
						for(i2 <- 0 to 2) {
							if(poss(j)(j+k)(i1) && poss(j+k)(j+i)(i2)) {
								poss(j)(j+i)(op(i1)(i2)) = true;
								ways(j)(j+i)(op(i1)(i2)) += ways(j)(j+k)(i1) * ways(j+k)(j+i)(i2);
							}
						}
					}
				}
			}
		}
	}

	//Task 6
	//TODO Runtime analysis of dynamic programming version with tests

	/*
	The maximum length is 30, as defined in the line
		val MAXWORD = 30

	We can notice the dynamic method is faster than the recursive method.

	For the dynamic format, the time complexity is simply O(N^3) (3 for loops)
	and overall 7^n will increase faster (and thus the recursive algorithm will be slower)
	that n^3, thus the dynamic method is faster.
	
	To prove such, test cases are provided below
	*/

	//Test Case 1: AAAAABBBBBCCCCC
	/*
	jewoosuh@DESKTOP-1VVIMQL:/mnt/c/users/jewoo/Downloads/ScalaTest$ time scala Brack.scala -NumberRec
	warning: 8 deprecations (since 2.13.0)
	warning: 5 deprecations (since 2.13.3)
	warning: 13 deprecations in total; re-run with -deprecation for details
	AAAAABBBBBCCCCC
	Bracketing values for AAAAABBBBBCCCCC
	A can be achieved in 1364930 ways
	B can be achieved in 668813 ways
	C can be achieved in 640697 ways

	real    0m9.579s
	user    0m18.075s
	sys     0m0.823s

	---vs---

	jewoosuh@DESKTOP-1VVIMQL:/mnt/c/users/jewoo/Downloads/ScalaTest$ time scala Brack.scala -Tabulate
	warning: 8 deprecations (since 2.13.0)
	warning: 5 deprecations (since 2.13.3)
	warning: 13 deprecations in total; re-run with -deprecation for details
	AAAAABBBBBCCCCC
	Bracketing values for AAAAABBBBBCCCCC
	A can be achieved 1364930 ways
	For example:
	B can be achieved 668813 ways
	For example:
	C can be achieved 640697 ways
	For example:

	real    0m6.624s
	user    0m12.030s
	sys     0m0.622s
	*/

	//Test Case 2: ABCBAABCBAABCBAAB
	/*
	jewoosuh@DESKTOP-1VVIMQL:/mnt/c/users/jewoo/Downloads/ScalaTest$ time scala Brack.scala -NumberRec
	warning: 8 deprecations (since 2.13.0)
	warning: 5 deprecations (since 2.13.3)
	warning: 13 deprecations in total; re-run with -deprecation for details
	ABCBAABCBAABCBAAB
	Bracketing values for ABCBAABCBAABCBAAB
	A can be achieved in 5781177 ways
	B can be achieved in 24649530 ways
	C can be achieved in 4926963 ways

	real    0m33.520s
	user    0m39.488s
	sys     0m1.002s

	---vs---

	jewoosuh@DESKTOP-1VVIMQL:/mnt/c/users/jewoo/Downloads/ScalaTest$ time scala Brack.scala -Tabulate
	warning: 8 deprecations (since 2.13.0)
	warning: 5 deprecations (since 2.13.3)
	warning: 13 deprecations in total; re-run with -deprecation for details
	ABCBAABCBAABCBAAB
	Bracketing values for ABCBAABCBAABCBAAB
	A can be achieved 5781177 ways
	For example:
	B can be achieved 24649530 ways
	For example:
	C can be achieved 4926963 ways
	For example:

	real    0m7.179s
	user    0m12.702s
	sys     0m0.583s
	*/

	//Test Case 3
	/*
	jewoosuh@DESKTOP-1VVIMQL:/mnt/c/users/jewoo/Downloads/ScalaTest$ time scala Brack.scala -NumberRec
	warning: 8 deprecations (since 2.13.0)
	warning: 5 deprecations (since 2.13.3)
	warning: 13 deprecations in total; re-run with -deprecation for details
	ABCBAABCBAABCBABCB
	Bracketing values for ABCBAABCBAABCBABCB
	A can be achieved in 31268195 ways
	B can be achieved in 78950527 ways
	C can be achieved in 19426068 ways

	real    1m6.759s
	user    1m3.784s
	sys     0m1.407s

	---vs---

	jewoosuh@DESKTOP-1VVIMQL:/mnt/c/users/jewoo/Downloads/ScalaTest$ time scala Brack.scala -Tabulate
	warning: 8 deprecations (since 2.13.0)
	warning: 5 deprecations (since 2.13.3)
	warning: 13 deprecations in total; re-run with -deprecation for details
	ABCBAABCBAABCBABCB
	Bracketing values for ABCBAABCBAABCBABCB
	A can be achieved 31268195 ways
	For example:
	B can be achieved 78950527 ways
	For example:
	C can be achieved 19426068 ways
	For example:

	real    0m7.823s
	user    0m12.820s
	sys     0m0.713s
	*/


	
  

/** The main method just selects which piece of functionality to run */
  def main(args: Array[String]) = {

    // string to print if error occurs
    val errString = 
      "Usage: scala Brack -PossibleRec [file]\n"+
      "     | scala Brack -NumberRec [file]\n"+
      "     | scala Brack -Tabulate [file]\n"
		
		if (args.length > 2){
			println(errString)
			sys.exit
		}

    //Get the plaintext, either from the file whose name appears in position
    //pos, or from standard input
    def getPlain(pos: Int) = 
      if(args.length==pos+1) readFile(args(pos)) else StdIn.readLine.toArray

    // Check there are at least n arguments
    def checkNumArgs(n: Int) = if(args.length<n){println(errString); sys.exit}

    // Parse the arguments, and call the appropriate function
    checkNumArgs(1)
		val plain = getPlain(1)
    val command = args(0)

		//Making sure the letters are of the right type
		val len = plain.length
		var plainInt = new Array[Int](len)
		if (len > MAXWORD){
			println("Word Too Long! Change MAXWORD")
			sys.exit;
		} else {
    	for (i <- 0 until len){
				plainInt(i) = LetterToInt(plain(i))
			}
		}
		
		//Executing appropriate command
    if(command=="-PossibleRec"){
		println("Bracketing values for "+ plain.mkString(""))
		for(i<-0 to 2){
			if(PossibleRec(plainInt, 0, len, i)){
				println(('A'.toInt + i).toChar + " is Possible");
			}
			else{
				println(('A'.toInt + i).toChar + " is not Possible");
			}
		}
    }
    else if(command=="-NumberRec"){
		var z: Int = 0
		println("Bracketing values for "+ plain.mkString(""))
		for(i<-0 to 2){
			z = NumberRec(plainInt, 0, len, i)
			if(z == 1){
				printf(('A'.toInt + i).toChar+ " can be achieved in %d way\n", z)
			}
			else{
				printf(('A'.toInt + i).toChar+ " can be achieved in %d ways\n", z)
			}
		}
    }

    else if(command=="-Tabulate"){
		Tabulate(plainInt,len)
		println("Bracketing values for "+ plain.mkString(""))
		for(v<-0 to 2){
		var z: Int = ways(0)(len)(v)
			if(z==0){
			println(('A'.toInt + v).toChar+ " cannot be achieved")
			}
			else if(z==1){
				printf(('A'.toInt + v).toChar+ " can be achieved %d way\n", z)
				printf("For example:")
				print_tree(exp(0)(len)(v))
				printf("\n")
			}
			else if (z > 1){
				printf(('A'.toInt + v).toChar+ " can be achieved %d ways\n", z)
				printf("For example:")
				print_tree(exp(0)(len)(v))
				printf("\n")
			}
		}
    }      
    else println(errString)
  }
}

