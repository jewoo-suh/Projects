object Sheet2_Question6 {

	def duplicateExistInt(arr: Array[Char]) : Int = {
    
    val K = arr.length;
    var equal = true; var i = 0;
    
    for(j <- 1 to K) {
    	
    	while (i < K-j) {
    		equal = equal && arr(i)==arr(i+j);
    		i = i+1;
    	}

    	i = 0;

      	if(equal) {
        	return j;
      	}

      	equal = true;
    }

    return (-1);

  }


	def main(args: Array[String]) : Unit = {

		println(duplicateExistInt(Array('a','b','c','d','e','e','e','e','a','b','c')))

	}
}