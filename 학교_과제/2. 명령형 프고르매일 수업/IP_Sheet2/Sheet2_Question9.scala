object Sheet2_Question9 {

    def log3Finder(y: Int) : Int = {
        var i = 0; var n = math.pow(3, i);
        while(n <= y) {
            i = i + 1;
            n = math.pow(3, i);
        }
        return i-1;
    }

	def main(args: Array[String]) : Unit = {
        
	}
}