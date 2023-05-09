object Sheet3_Question2 {

    def ternary_search(x: Int) : Int = {
        require(x >= 0);

        if (x <= 1) return x;

        var lower_bound = 0;
        var upper_bound = x;
        var first_bound = x/3;
        var second_bound = 2*x/3;


        while(first_bound != second_bound) {

            if(x == first_bound*first_bound) return first_bound;

            if(x == second_bound*second_bound) return second_bound;
            
            if(x < first_bound*first_bound) upper_bound = first_bound;

            else if(x > second_bound*second_bound) lower_bound = second_bound;
    
            else {upper_bound = second_bound; lower_bound = first_bound}

            first_bound = lower_bound + (upper_bound - lower_bound)/3;
            second_bound = lower_bound + 2*(upper_bound - lower_bound)/3;

            println("lower_bound = " + lower_bound + ", first_bound = " + first_bound + 
                ", second_bound = " + second_bound + ", upper_bound = " + upper_bound)

        }

        return first_bound;
    }

    def main(args: Array[String]) : Unit = {

        println(ternary_search(18))

    }
	
}

/*
Part a: 

    Code above

Part b: 

    As the size of a search interval becomes smaller and smaller, it is possible for the ternary spli
    to produce an empty sub interval; For example, using the print statement above to observe the
    values of the lower_bound, first_bound, second_bound, and upper_bound for 'ternary_search(18)', 
    we notice that 

    "
        ~/ScalaTest/Worksheet 3$ scala Sheet3_Question2.scala
        lower_bound = 0, first_bound = 2, second_bound = 4, upper_bound = 6
        lower_bound = 4, first_bound = 4, second_bound = 5, upper_bound = 6
        lower_bound = 4, first_bound = 4, second_bound = 4, upper_bound = 5
        4
    "

    We notice that in step 2, the first interval(lower_bound ~ first_bound) is from 4 to 4, 
    and thus is an empty sub interval; however, the code continues to compile until the 
    condition(first_bound == second_bound) is met, and thus shows that there is the potential
    for an iteration of the while loop to work with an empty interval.

*/