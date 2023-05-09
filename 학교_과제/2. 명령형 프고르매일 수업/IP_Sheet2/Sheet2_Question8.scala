object Sheet2_Question8 {

    def findM(p : Int, q: Int): Int = (q.toFloat/p.toFloat).ceil.toInt

    def loopFindReciprocal(p: Int, q: Int) : Array[Int] = {
        var reciprocals : Array[Int] = Array();
        var m = 0; var x = p; var y = q;
        while(x != 0) {
            m = findM(x, y);
            //println(i + " th m is " + m)
            reciprocals = reciprocals :+ m;
            //println("my = " + (m*y));
            //println("gcd = " + gcdFinder((m*x - y), (m*y)));
            x = m*x-y; 
            y = m*y;
            //println("x = " + x);
            //println("y = " + y);
        }
        return reciprocals;
    }

	def main(args: Array[String]) : Unit = {
        var l = loopFindReciprocal(40, 61);
        for(i <- 0 to l.length-1) print(l(i) + " ")
        println();
	}
}

/*

Part c:

    It is assumed that p>0. 

    We notice that 
    p'/q' = p/q - 1/m = (p*m-q)/(q*m)

    And want to prove that 
    p'<p -> p*m-q < p

    In the findM function, we notice that
    m = (q/p).ceil 
    => m-1 < q/p <= m
    => m-1 < q/p 
    => p(m-1) - q < 0
    => p*m-q < p
    => p'<p

    We notice p is strictly decreasing and bounded at p > 0, thus it can be deduced that p will reach 0.

Part d:

    We notice that if we denote 

        d(i) = (q/p).ceil = m

    Then

        d(i+1) = ((qm)/(pm-q)).ceil = n

    Thus we can deduce two inequalities

        m-1 < q/p <= m
        n-1 < (qm)/(pm-q) <= n 

    Which can be further broken down into 4 inequalities

        (1) pm-q-p < 0
        (2) pm-q >= 0
        (3) pmn-pm-qn-qm+q < 0
        (4) pmn-qn-qm >=0

    Here, we can deduce from (4) that

        (5) pmn >= qn+qm

    And from (1) that

        (6) pm < q+p -> n>0 => pmn < qn+pn

    And from (5) and (6) that 

        qn+qm <= pmn < qn+pn
        qn+qm < qn+pn 
        qm < pn

    And since it is assumed that p < q, it can be deduced that for qm < pn, since p < q, n > m

    And by induction, this holds true for all adjacent two entries of array d, 
    thus showing that the entire array d is strictly increasing. 

*/