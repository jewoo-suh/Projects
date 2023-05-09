import Array._;

object Sheet3_Question3 {

    def tooBig(y: BigInt) : Boolean = {
        val X = 4;
        return (y > X);
    }

    //Part a

    def binSearch_X(arr: Array[Int]) : Int = {
        val N = arr.size;
        var i = 0; var j = N;

        while(i < j) {
            val m = (i + j)/2;
            if(tooBig(arr(m))) {
                j = m;
            }
            else {
                i= m + 1;
            }
        }
        return i-1;
    }

    def findX() : Int = {
        return binSearch_X(range(1, 1000));
    }

    //Part b
    
    def my_binSearch_X(lb: BigInt, ub :BigInt) : BigInt = {
        var i = lb; var j = ub;

        while(i < j) {
            val m = (i + j)/2;
            if(tooBig(m)) {
                j = m;
            }
            else {
                i= m + 1;
            }
        }
        return i-1; 
        //Subtraction of 1 is necessary as essentially the while loop is looking for
        //the first value which tooBig(value) = true, which is X+1 
    }

    def findXFast() : BigInt = {
        var lower_bound = 1;

        while(!tooBig(lower_bound)) {
            lower_bound = lower_bound * 2;
        }

        var upper_bound = lower_bound;
        lower_bound = lower_bound / 2;

        return my_binSearch_X(lower_bound, upper_bound);
    }
    

    //Part c

    def main(args: Array[String]) : Unit = {

        println(findXFast())

    }
	
}
