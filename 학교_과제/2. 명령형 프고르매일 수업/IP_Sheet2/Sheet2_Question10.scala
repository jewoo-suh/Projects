object Sheet2_Question10 {

    def eval(a: Array[Double], x: Double) : Double = {
        var counter : Double = 0;
        for(i <- 0 to a.length-1) {
            counter = counter + (a(i) * math.pow(x, i));
        }
        return counter;
    }

    def evalEfficient(a: Array[Double], x: Double) : Double = {
        var counter : Double = 0;
        var array = a;

        if(a == Array()) {
            return 1.0
        }

        for(i <- 0 to a.length-1) {
            counter = a(0) + x * (evalEfficient(array.slice(1,array.length), x));
        }

        return counter;
    }

	def main(args: Array[String]) : Unit = {
        println(evalEfficient(Array(5,4,3,2,1), 2))
    }
}