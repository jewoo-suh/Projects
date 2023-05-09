object IP_Sheet12{

//Q1-i
  def square(n: Int) : Int = {
    return n * n
  }
//Q1-ii
  def mod3(n: Int) : Int = {
    return n - (n / 3) * 3
  }
//Q1-iii
  def maxSqr(n: Int) : Int = {
    var x = 1
    while(true) {
      if(n - square(x) > 0) {
        x = x + 1
      }
      else {
        return square(x-1)
      }
    }
    return 1 
  }

//Q3
  def findMax(arr: Array[Int]) : Int = {
    val n = arr.size 
    var max = 0; var i = 0;
    // Invariant I: max = max(arr[0..i]) && 0 <= i <= n
    // Variant n-i
    while(i<n) {
      if (arr(i) > max) {
        max = arr(i)
      }
      i += 1
    }
    return max;
  }

//Q4

/*
Some room for improvement on the given segment of code include changing

  val n = a.size ; var i = 0

  while(i < n){
    total += a(i)
    i += 1
  }

to 

  for(i <- 0 to (a.size -1)) total += a(i) 

As it eliminates the unnecessary calculations made when compiling "i += 1"
while also discarding two unnecessary variables 'n' and 'i', And

to 

  for(i <- 0 until args.size) a(i) = args(i).toInt

As it also discards an unnecessary variable 'n'.
*/

//Q5 

  def fib(n: Int) : Int = {
    if (n==0) return 0
    else if (n==1) return 1 

    return fib(n-2) + fib(n-1)
  }

  def fibTree(n: Int, depth: Int) : Unit = {
    for(i <- 0 to depth-1) print("|");
    println("fib(" + n + ")");

    if (n==0 || n==1) {
      for(i <- 0 to depth-1) print("|");
      println("= " + n);
    }

    if(n>1) {
      fibTree(n-1, depth+1)
      fibTree(n-2, depth+1)
      for(i <- 0 to depth-1) print("|");
      println("= " + fib(n));
    }
  }

//Q6 
  def fibNonRecursive(n: Int) : Int = {
    var fibList = Array.ofDim[Int](n+1);
    fibList(0) = 0 ; fibList(1) = 1;
    for(i <- 2 to n) {
      //Invariant I: fibList(i) = 'i'th term of fibonacci sequence && 0 <= i <= n
      //Variant: n-i
      fibList(i) = fibList(i-1) + fibList(i-2)
    }
    return fibList(n)
  }

//Q7
  def divMod(x: Int, y: Int) : (Int, Int) = {
    var diff = x-y ; var q = 0 ; var r = x;
    while(diff >= 0) {
      diff = diff - y //Invariant I: 0 <= diff
      q = q + 1; //Invariant I: q <= x/y
      r = r-y //Invariant I: r >= x%y
    }
    return (q,r)
  }

//Q8 
  def gcdFinder(m: Int, n: Int) : Int = {
    if(m == 0) {
      return n;
    }

    if(n == 0) {
      return m;
    }

    if(m < n) {
      return gcdFinder(n, m);
    }

    return gcdFinder(m%n, n);
  }

  def gcdFinderXY(m: Int, n: Int) : (Int, Int) = {
    if(m < n) {
      return gcdFinderXY(n, m);
    }

    var gcd = gcdFinder(n, m);
    var m1 = m/gcd ; var m2 = n/gcd;

    var a = 1 ; var b = 1; 
    var diff = m1*a - m2*b;

    while(diff != 1) {
      diff = m1*a - m2*b;
      while(diff > 0) {
        b = b + 1;
        diff = m1*a - m2*b;
        if(diff == 1) {
          return (a, -b);
        }
      }
      a = a + 1;
    }

    /* 
    The only invariants I could find where that:
    Line 122: b = b + 1; -> b >= 0
    Line 128: a = a + 1; -> a >= 0
    For the 'diff' variable, I was unable to find an invariant pattern.
    */

    return (a, -b);
  }

//Q9
  def numberHit(arr: Array[Int]) : Int = {
    var max = arr(0); var counter = 0;

    for(i <- 1 to (arr.size - 1)) {
      if(max < arr(i)) {
        counter = counter + 1; //Invariant I: counter >= 0
        max = arr(i); //Invariant I: (var)max = max(arr[0..i])
      }
    }

    return counter;
  }


  // Main method
  def main(args: Array[String]) : Unit = { 
    println("Question 1 Test Output (Testing variable n = 11)");
    val n = 11;
    println("n squared is " + square(n));
    println("n modulo 3 is " + mod3(n));
    println("Largest square smaller than n is " + maxSqr(n));
    println();
    println("Question 3 Test Output (Testing Array arr = [1,2,3,5,3,100,3,3,3,3,1])");
    println("Max number of pints consumed is " + findMax(Array(1,2,3,5,3,100,3,3,3,3,1)));
    println();
    println("Question 5 Test Output (Testing fib(5))");
    println("(Q5) fib(5) = " + fib(5));
    println("fib(5) Tree is");
    println(fibTree(5, 0))
    println();
    println("Question 6 Test Output (Testing fib(5))");
    println("(Q6) fib(5) = " + fibNonRecursive(5));
    println();
    println("Question 7 Test Output (Testing with x = 10, y = 4)");
    println("10 = 4*" + divMod(10,4)._1 + " + " + divMod(10,4)._2);
    println();
    println("Question 8 Test Output (Testing with n = 1001, m = 56)");
    println("(a) GCD of 1001 and 56 is " + gcdFinder(1001, 56)); 
    println("(b) We can calculate that 7 = " + gcdFinderXY(1001,56)._1 + "*1001 " + gcdFinderXY(1001,56)._2 + "*56")
    println();
    println("Question 9 Test Output (Testing Array arr = [1,2,3,5,3,100,3,3,3,3,1])");
    println("Number of hits is " + numberHit(Array(1,2,3,5,3,100,3,3,3,3,1)));
  }


}
