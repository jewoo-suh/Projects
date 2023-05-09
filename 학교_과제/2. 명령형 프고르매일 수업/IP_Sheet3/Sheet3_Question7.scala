import Sheet3_Question4._;

object Sheet3_Question7 {

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

    def QSort(l: Int, r: Int) : Unit = {
        if(r-l > 1) { // nothing to do if segment empty or singleton
            val k = partition(l,r)
            QSort(l,k); QSort(k+1,r)
        }
    }

    def QSortWhile(arr: Array[Int], l: Int, r: Int) : Unit = {
        if(r-l > 1) { // nothing to do if segment empty or singleton
            val k = partition(l,r)
            QSort(l,k); QSort(k+1,r)
        }

        require(r-l > 1);

        val k = partition(l,r)
        QSort(l,k); 

        var i = k + 1;
        var sortArr = arr.slice(k+1, r);

        while (i < r) {
            var tempArr = sortArr.slice(0, i);
            var index = binSearch(tempArr, arr(i));
            tempArr = (tempArr.slice(0, index) :+ arr(i)) ++ tempArr.slice(index, tempArr.size);
            sortArr = tempArr ++ arr.slice(i+1, arr.size);
            i = i + 1;
        }

        //I switched the QSort(k+1, r) portion of the code from a recurstion to an insertion sort 
    }


    def main(args: Array[String]) : Unit = {
        
    }
	
}

/*

Part b:
    
    The call stack may reach a depth of 'N' as in the worst case, a total 'N' number
    of QSorts have to be called to sort the array. 

Part c:

    One possible way to ensure the stack does not get deeper than ceiling(log_2 N) is 
    
*/