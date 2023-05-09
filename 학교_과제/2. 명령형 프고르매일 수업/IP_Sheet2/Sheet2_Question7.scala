object Sheet2_Question7 {

    def exists(p : Int => Boolean, N : Int): Boolean = {

        var exists = false;
        var index = 0;
        

        while(!exists && index < N) {
            //Invariant: exists = At least one p(i) is true for i∈[0...index]
            exists = exists || p(index) 
            //Invariant: index <= N
            index = index + 1           
        }
        
        return exists;
    }

    //Proof by induction
    /*
        When N = 1, 

            exists = exists || p(0) = false || p(0)
        
        exists will be true when p(0) is true and false when p(0) is false, 
        thus accurately calculating whether or not 
        ∃i ∈ [0...N) = [0...1) = 0 => ∃i = 0 s.t. p(i)=true
        
        Thus the code works for when N=1;

        Now assuming the code works for N=k, 
        we evaluate when N=k+1.

        We notice that if the code segment returns true for N=k, 
        the while loop terminates when index < N=k, thus will not evaluate index=k, 
        which is the only additional unique while loop that can be evaluated when moving from
        N=k to N=k+1. 

        Thus if the function returns true for N=k, it will return true for N=k+1, as intended.

        If N=k returns false, the code segment will evaluate the last loop, where index=k.
        Here, 

            exists = exists || p(k) = false || p(k)

        exists will be true when p(k) is true and false when p(k) is false, 
        Thus accurately calculating 
        ∀i ∈ [0...k) s.t p(i)=false, ∃i ∈ [0...k+1) p(i)
        => if P(k)=true, ∃i ∈ [0...k+1) p(i) =∀i ∈ [0...k) p(i) || p(k) = false || true = true
        => if P(k)=false, ∃i ∈ [0...k+1) p(i) =∀i ∈ [0...k) p(i) || p(k) = false || false = false
    
        Thus by induction the function 'exists' works for all integer values of N
    */

	def main(args: Array[String]) : Unit = {
        val threedigits:(Int=>Boolean) = i => {i >= 100 && i<1000}
        println(exists(threedigits,100))
        println(exists(threedigits,101))
	}
}