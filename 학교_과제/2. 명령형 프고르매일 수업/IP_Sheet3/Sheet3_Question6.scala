object Sheet3_Question6 {

    def partition(a: Array[Int], l: Int, r: Int) : Int = {
        val x = a(l); // pivot

        var i = l + 1; var j = r;


        while(i < j){
            if (a(i) < x) i += 1
            else { 
                val t = a(i); 
                a(i) = a(j-1); 
                a(j-1) = t; 
                j -= 1; 
            }
            //for (k <- 0 to a.size-1) print(a(k) + " ");
        } 

        a(l) = a(i-1); 
        a(i-1) = x;
        //for (k <- 0 to a.size-1) print(a(k) + " ");
        println()

        return i-1; // position of the pivot
    }

    //Part b: Improved Partition

    def parititionImp(arr: Array[Int], l: Int, r: Int) : Int = {
        var a = arr; val x = a(l);

        var i = l + 1; var j = r;
        var counter = l;

        while(counter+1 < j){
            if (a(i) < x) {
                i = i+1; //I: i > l && i< j
                counter = counter+1; //I: counter < j && a[l+1..i) < x = a(l)
            }

            else { 
                a = a.slice(0, l+1) ++ a.slice(l+1,i) ++ (a.slice(i+1,r) :+ a(i)) ++ a.slice(r,a.size);
                //I: a[l+1..i) < x = a(l) && x = a(l) <= a[r-i..r)
                counter = counter+1; //I: counter < j
            }

            //for (k <- 0 to a.size-1) print(a(k) + " ");
        } 

        a(l) = a(i-1); 
        a(i-1) = x;
        //for (k <- 0 to a.size-1) print(a(k) + " ");

        return i-1;
    }




    

    def main(args: Array[String]) : Unit = {
        //Part a - the element '8' moves twice before finding its place
        println(parititionImp(Array(4,6,2,5,3,1,7,8,9), 0, 9))
    }
	
}
