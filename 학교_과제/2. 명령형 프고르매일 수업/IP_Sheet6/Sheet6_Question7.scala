import java.io.PrintWriter;

class Dictionary(fname: String){
  private val words = new scala.collection.mutable.HashSet[String]

  private def initDict(fname: String) = {
    val allWords = scala.io.Source.fromFile(fname).getLines()
    def include(w:String) = w.forall(_.isLower)
    for(w <- allWords; if include(w)) words += w
  }

  initDict(fname)

  def isWord(w: String) : Boolean = words.contains(w)
}

object Sheet6_Question7 {

	//Part a
	def permutations(word: String) : Array[String] = {
		val length = word.length();
		var ans = new Array[String](0);

		if(length == 1) {
			return Array(word);
		}

		if(length == 2) {
			return Array(word, word(1).toString() + word(0).toString())
		}

		for(i <- 0 to word.length()-1) {
			ans = ans ++ mergeCharList(word(i), permutations(word.slice(0,i) ++ word.slice(i+1, word.length())).distinct)
		}

		return ans.distinct;
	}

	def mergeCharList(a: Char, arr: Array[String]) : Array[String] = {
		var ans = new Array[String](arr.size);
		for(i <- 0 to arr.size-1) {
			ans(i) = a.toString() ++ arr(i);
		}
		return ans.distinct;
	}

	def permutationsDictionaryContained(word: String) : Array[String] = {
		var ans = new Array[String](0); var perms = permutations(word);
		val dict = new Dictionary("knuth_words.txt");
		for(i <- 0 to perms.length-1) {
			if(dict.isWord(perms(i))) ans = ans ++ Array(perms(i))
		}
		return ans;
	}

	//Part b
	def anagrammaticalDictionary() : Unit = {
		val s = new PrintWriter("knuth_words_annagrams.txt")

		var words = new Array[(String, String)](0);

		val allWords = scala.io.Source.fromFile("knuth_words.txt").getLines()
		def include(w:String) = w.forall(_.isLower)
    	for(w <- allWords; if include(w)) words = words :+ (w.sorted, w);

    	words = words.sorted;

		for (i <- 0 to words.size-1) {
  			s.println(words(i))
		}

		s.close()
	}

	def anagramFinder(word: String) : Array[String] = {
		var s = word.sorted;
		var sortedwords = new Array[String](0);
		var ans = new Array[String](0);

		val allWords = scala.io.Source.fromFile("knuth_words_annagrams.txt").getLines()
    	for(w <- allWords) {
			sortedwords = sortedwords :+ w;
    	} 

    	for(i <- 0 to sortedwords.size-1) {
    		if(sortedwords(i).slice(1,sortedwords(i).indexOf(",")) == s) ans = ans :+ sortedwords(i)
    	}

    	return ans;
	}

	def anagramFinderLongestMostFrequent() : Unit = {
		//Variables
		var sortedwords = new Array[String](0);
		var words = new Array[String](0);
		var sorts = new Array[String](0);
		var longestAnagrams = new Array[String](0);

		//Initiate arrays
		val allWords = scala.io.Source.fromFile("knuth_words_annagrams.txt").getLines()
    	for(w <- allWords) {
			sortedwords = sortedwords :+ w;
    	} 

    	for(s <- sortedwords) {
    		var i = s.indexOf(",")
    		sorts = sorts :+ s.slice(1, i);
    		words = words :+ s.slice(i+1, s.length()-1);
    	}

    	//Longest Anagram
    	var temp = sorts.diff(sorts.distinct);
    	var duplicates = temp ++ temp.distinct;

    	var longest = ""; var longestArr = "";
    	for(i <- 0 to duplicates.size-1) {
    		if(duplicates(i).size > longest.size) {
    			longest = duplicates(i);
    			longestArr = duplicates(i);
    		}
    		else if (duplicates(i).size == longest.size) {
    			longestArr = duplicates(i);
    		}
    	}

    	for(i <- 0 to sorts.size-1) {
    		if(sorts(i) == longestArr) longestAnagrams = longestAnagrams :+ words(i)
    	}

    	println("The longest words that are anagrams with one another are:");
    	for(s <- longestAnagrams) println(s);

    	//Most common Anagram
    	duplicates = temp.distinct;
    	var max = 0; var indexArray = new Array[Int](0);

    	for(i <- 0 to duplicates.size-1) {
    		var loopMax = 0; var loopIndexArray = new Array[Int](0);
    		for(j <- 0 to sorts.size-1) {
    			if(duplicates(i) == sorts(j)) {
    				loopMax += 1; loopIndexArray = loopIndexArray :+ j;
    			}
    			if(loopMax > max) {
    				max = loopMax; indexArray = loopIndexArray;
    			}
    		}
    	}

    	println();
    	println("The largest set of words that are anagrams of one another are:");
    	for(i <- indexArray) println(words(i));

	}

	def main(args: Array[String]) : Unit = {

		//Part a
		
		var perm = permutations("acres");
		println(perm.size);
	 	var arr = permutationsDictionaryContained("acres");
	 	println(arr.size)
	 	for(i <- 0 to arr.size-1) println(arr(i));
	 	
	 	anagrammaticalDictionary();
	 	println();
	 	/*
		The main reason part a takes longer to compute on long strings is because the 'permutations' function
		has a time complexity of O(N!), as in the worst case for a string of length N, there are N! possible
		permutations, which the 'permutations' function has to calculate.
	 	*/

	 	//Part B
	 	
	 	var arr2 = anagramFinder("bacterial")
	 	for(i <- 0 to arr2.size-1) println(arr2(i));
		println();
		
		anagramFinderLongestMostFrequent();
	 	
    }

}