trait Book{
    def store(name: String, number: String) : Unit
    def recall(name: String) : String
    def isInBook(name: String) : Boolean
}

class LinkedListHeaderBook extends Book{

  	private var list = new LinkedListHeaderBook.Node("?", "?", null)

  	private def find(name:String) : LinkedListHeaderBook.Node = {
    	var n = list
    	while(n.next != null && n.next.name < name) n = n.next
    	return n
  	}

  	def isInBook(name: String): Boolean = {
  		return find(name).next.name == name;
  	}

  	def recall(name: String) : String = {
  		assert(isInBook(name));
    	val n = find(name); 
      val num = n.next.number;
      /*
      Implementation: One possible heeuristic for a faster recall would be to move the 
      recalled name/number to the front of the list. Under the assumption that there are
      certain numbers that are more frequently recalled than others, and pushing these to
      the front of the linked list would decrease the expected value of the time it takes for
      the 'find(name)' function to compute
      */
      n.next = n.next.next;
      list.name = name; list.number = num;
      list = new LinkedListHeaderBook.Node("?", "?", list)
    	return num;
  	}

  	def store(name: String, number: String): Unit = {
    	val n = find(name)
    	if(!isInBook(name)){ // store new info in current list header
      		var newEntry = new LinkedListHeaderBook.Node(name, number, n.next)
      		n.next = newEntry;
    	}
    	else n.next.number = number
  	}

  	def delete(name: String) : Boolean = {
    	val n = find(name)
    	if(isInBook(name)){ 
      		n.next = n.next.next; 
      		return true 
    	}
    	else false
  	}
}

object LinkedListHeaderBook{
  	private class Node(var name:String, var number:String, var next:Node)
}