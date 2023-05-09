class Node(val datum: Int, var next: Node){

	override def toString : String = {
		var str = datum.toString(); var n = next;
		while(n != null) {
			str = str + " -> " + n.datum.toString();
			n = n.next;
		} 
		return str;
	}


}

object Sheet5_Question1 {

	def main(args: Array[String]) : Unit = {

		var myList = new Node(1, null);

	 	for(i <- 2 to 12) {
	 		val n1 = new Node(i, myList);
	 		myList = n1;
	 	}

	 	println(myList);

	 	var prev : Node = null;
	 	var current = myList;
	 	var next : Node = null;

		while(current != null) {
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		myList = prev;

		println(myList);

    }
}