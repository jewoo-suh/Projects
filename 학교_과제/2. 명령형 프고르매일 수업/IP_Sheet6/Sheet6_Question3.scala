// A hash table, representing a bag of words; i.e., for each word we record
// how many times the word is stored.

// Companion object
object HashBag{
  // Nodes for forming linked lists
}

class HashBag{
  // The hash function we will use
  private def hash(word: String) : Int = {
    def f(e: Int, c: Char) = (e*41 + c.toInt) % MAX
    word.foldLeft(1)(f)
    // word.foldLeft(1)(((_:Int)*41+(_:Char).toInt) % N)
  }

  private val MAX = 100 // # buckets in the hash table
  private var size_ = 0 // # distinct words stored

  private var stringTable = new Array[String](MAX) // the hash table
  private var intTable = new Array[Int](MAX) // the hash table
  for(i <- 0 to MAX-1) {stringTable(i) = ""; intTable(i) = 0}; // Initialize the hash table

  //DTI: ∀i ∈ [0..MAX), intTable(i) >= 0
  //        && size_ <= MAX
  //        && if(stringTable(j) == ""), intTable(j) == 0;

  /** Find node containing word in linked list starting at head, or 
    * return MAX if word does not appear */
  private def find(word: String) : Int = {
    val orgIdx = hash(word); var idx = hash(word); 
    if(stringTable(idx) == word) return idx;
    while(idx < MAX && stringTable(idx) != word && idx != orgIdx) {
      if(idx == MAX-1) idx = 0; 
      if(stringTable(idx) == "") return MAX;
      idx += 1;
    }
    return idx;
  }


  /** Add an occurrence of word to the table */
  def add(word: String) = {
    assert(size_ < MAX)
    var h = hash(word); var n = find(word)
    if(n != MAX) intTable(n) += 1;
    else{ 
      while(stringTable(h) != "") {if(h == MAX -1) h = 0; h += 1;}
      stringTable(h) = word; intTable(h) += 1;
      size_ += 1;
    }
  }

  /** The count stored for a particular word */
  def count(word: String) : Int = {
    val h = hash(word); val n = find(word)
    if(n == MAX) return 0 
    else return intTable(n);
  }

  /** Deleting a particular word **/
  def delete(word: String) : Boolean = {
    val idx = find(word); var nextEmpty = find(word);

    if(idx == MAX) return false;

    while(stringTable(nextEmpty) != "") {if(nextEmpty == MAX -1) nextEmpty = 0; nextEmpty += 1;}
    nextEmpty -= 1;
    stringTable(idx) = stringTable(nextEmpty); intTable(idx) = intTable(nextEmpty);
    stringTable(nextEmpty) = ""; intTable(nextEmpty) = 0;
    return true;
  }

  // return the size
  def size = size_

  /*
  // print the hash table
  def printBag = {
    var maxbucket=0
    for(i <- 0 until N){
      var n = table(i); var bucket=0
      while(n != null){ println(n.word+"\t"+n.count); bucket += 1; n = n.next }
      if (bucket>maxbucket) maxbucket=bucket
    }
    println("Maximum bucket size = "+maxbucket)
  }
  */
}
