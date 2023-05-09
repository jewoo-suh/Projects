object Sheet3_Question4 {

    def binSearch(a: Array[Int], x: Int) : Int = {
        val N = a.size;
        var i = 0; var j = N;

        while(i < j){
            val m = (i+j)/2;
            if(a(m) < x) i = m + 1 else j = m;
        }

        return i;
    }

    def insSort(arr: Array[Int]) : Array[Int] = {
        var i = 1;
        var sortArr = arr;

        while (i < arr.size) {
            var tempArr = sortArr.slice(0, i);
            var index = binSearch(tempArr, arr(i));
            tempArr = (tempArr.slice(0, index) :+ arr(i)) ++ tempArr.slice(index, tempArr.size);
            sortArr = tempArr ++ arr.slice(i+1, arr.size);
            i = i + 1;
        }

        return sortArr;

    }

    def main(args: Array[String]) : Unit = {

        var array = Array(3,4,2,1,5,8,3,55,651,5,43,234,5,86,8,45,78,90,0);
        for(i <- 0 to array.size-1) println(insSort(array)(i))

    }
	
}

/*

    The time complexity of insertion sort will be O(n*log(n)), since in the worst case scenario, 
    for an array of size 'n', a total of 'n' binary searches have to be made, and the time complexity 
    of a binary search is (log(n)), thus the time complexity of the insertion sort will be (n*log(n)).


*/