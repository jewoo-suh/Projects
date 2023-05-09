class LinkedListHeaderBook extends Book{

  	private var list = new LinkedListHeaderBook.Node("?", "?", null)

  	//Abs: Book = while (list.next != null), {list.name -> list.number; list = list.next}
  	//DTI: if (list.next == null), list.name == list.number == "?"
  	// 			&& ∀ n, m ∈ list, (n.name == m.name) == false

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
    	return n.next.number;
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