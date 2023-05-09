object Sheet3_Question8 {

    def partition(l: Int, r: Int) : Int = {
        val x = a(l) // pivot

        var i = l+1; var j = r
        while(i < j){
            if(a(i) < x) i += 1
            else{ val t = a(i); a(i) = a(j-1); a(j-1) = t; j -= 1 }
        }
        a(l) = a(i-1); a(i-1) = x
        i-1;
    }

    def QSort(l: Int, r: Int) : Unit = {
        if(r-l > 1){ // nothing to do if segment empty or singleton
            val k = partition(l,r)
            QSort(l,k); QSort(k+1,r)
        }
    }

    //Part d

    def partitionImp(arr: Array[Int], l: Int, r: Int) : (Int, Int) = {
        var a = arr; val x = a(l) // pivot

        var i = l; 
        var j = i+1;
        var k = r;

        while(j < k){
            if(a(j) < x) {
                val t = a(i); 
                a(i) = a(j); 
                a(j) = t; 
                i += 1;
                j += 1;
            } 

            else if (a(j) == x) {
                val t = a(i); 
                a(i) = a(j); 
                a(j) = t; 
                j += 1;
            }

            else { 
                val t = a(j); 
                a(j) = a(k-1); 
                a(k-1) = t; 
                k -= 1;
            }
            //for (k <- 0 to a.size-1) print(a(k) + " ");
            println();
        }

        // a(l) = a(i-1); a(i-1) = x
        //print("FINAL: ")
        for (k <- 0 to a.size-1) print(a(k) + " ");
        
        return (i,j);
    }

    def main(args: Array[String]) : Unit = {
        println(partitionImp(Array(4,6,4,2,5,3,1,4,7,8,9,4,6,7,1,2,4), 0, 17))
    }
	
}

/*
Part a: 
    
    When a Quicksort is used for an array with all identical entries, the time complexity 
    still remains at O(n*log(n)), as for each partition made with 'QSort' recursions, in the while
    loop of the 'parititon' function, 
        "
            while(i < j){
                if(a(i) < x) i += 1
                else{ val t = a(i); a(i) = a(j-1); a(j-1) = t; j -= 1 }
            }
        "
    The else function will alwys be computed, as a(i)=x<x = False. Thus, the running time will also
    increase alongside the size of the array. This is highly inefficient as the code segment within
    the else statement, 
        "
            val t = a(i); a(i) = a(j-1); a(j-1) = t; j -= 1
        " 
    is essentially swapping two entires within the array that have the same values, thus there is no
    overall net change to the array.
Part b:
    Similar to part (a), these identical entries will be compared within the 'paritions' function, 
    and go through a swapping process within the 'else' statement, which makes no overall change to
    the array, thus being inefficient. 
Part c:
    The 'else' clause of the 'if' statement is run much more frequently because the 'then' clause
    is only run when a(i) < x, whereas the else statement is run either when a(i) == x or a(i) > x. 
    The number of overall cases where "a(i) < x" is true will be roughly similar to "a(i) > x" 
    within an array, thus it can be calculated that the 'else' clause will be run more, as all
    cases where "a(i) == x" will lead to 'else'. 
    Changing from '<' to '<=' will shift the "a(i) == x" cases from the 'else' clause to the 'then'
    clause, allowing the 'then' clause to be run more frequently, and since the 'then' clause 
    takes less compiling time than the 'else' clause, the change will improve performance. 
Part e:
    
    The new 'partition' function could help improve the performance of Quicksort, as it prevents
    elements identical from the pivot from being evaluated and wasting time by separating such
    values into a separate array, and later merging them together to form the sorted array. 
*/