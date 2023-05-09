trait Book{
	def store(name: String, number: String) : Unit
	def recall(name: String) : String
	def isInBook(name: String) : Boolean
}

object OrderedArraysBook extends Book{
  private val MAX = 1000 
  private val names = new Array[String](MAX)
  private val numbers = new Array[String](MAX)
  private var count = 0

  //Time complexity: O(log n)
  private def findBinary(name: String) : Int = {
  	var l = 0; var r = count - 1;
  	while(l <= r) {
  		var m = (l + r)/2;

  		if(names(m) == name) return m;

  		if(names(m) < name) l = m + 1;

  		else r = m - 1;
  	}
  	return -1;
  }

  //Time complexity: O(n)
  private def pushArray(index: Int) : Unit = {
  	var i = 0; var j = count - i;
  	while(j > index) {
  		names(j) = names(j-1);
  		numbers(j) = numbers(j-1);
  		i += 1;
  	}
  }

  //Time complexity: O(n)
  private def pullArray(index: Int) : Unit = {
  	var i = index; 
  	while(i < count) {
  		names(i) = names(i+1);
  		numbers(i) = numbers(i+1);
  		i += 1;
  	}
  }

  //Time complexity: O(n)
  def store(name: String, number: String): Unit = {
    val i = findBinary(name); var j = 0;
    if(i == -1){
      assert(count < MAX); 
      while(names(j) < name) j += 1;
      pushArray(j);
      names(j) = name;
      numbers(j) = number;
    }
  }

  //Time complexity: O(n)
   def delete(name: String, number: String): Unit = {
    val i = findBinary(name); 
    if(i != -1){
      assert(count < MAX); 
      pullArray(i);
    }
  }

  //Time complexity: O(log n)
  def recall(name: String) : String = {
    val i = findBinary(name);
    assert(i != -1);
    return numbers(i);
  }

  //Time complexity: O(log n)
  def isInBook(name: String) : Boolean = (findBinary(name) != -1);
  
}