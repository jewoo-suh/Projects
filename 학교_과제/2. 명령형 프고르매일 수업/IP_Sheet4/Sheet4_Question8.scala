trait Bag{
  def add(elem: Int) : Unit
  def occurence(elem: Int) : Int
}

class IntMap extends Bag{
  private val MAX = 1000 
  private val c = new Array[Int](MAX)
  private var maxIndex = -1; //Initialized as -1 for the case where the IntSet is empty

  // Abs: Bag = {c(i) -> #i | i <- [0..MAX)}
  // DTI: ∀i ∈ [0..MAX), c(i) >= 0
  //    ∀i ∈ (maxIndex..MAX), c(i) == 0 

  def add(elem: Int) : Unit = {
    require(elem < MAX)
    c(elem) += 1
    if(elem > maxIndex) maxIndex = elem
  }

  def occurence(elem: Int) : Int = {
    return c(elem)
  }

  def returnMaxIndex : Int = return maxIndex;

}

object Sheet2_Question4 {

  //For Question 8
  def returnSortedArray(intArr: Array[Int]) : Array[Int] = {
    var sortedIntArr = new Array[Int](intArr.size);
    var newBag = new IntMap;

    for(i <- 0 to intArr.size-1) {
      newBag.add(intArr(i));
    }

    var max = newBag.returnMaxIndex; 
    var i = 0; var j = 0; var index = 0;

    while(i <= max) {
      if(newBag.occurence(i) != 0) {
        while(j < newBag.occurence(i)) {
          sortedIntArr(index) = i;
          index += 1; j += 1;
        }
        j = 0;
      }
      i += 1;
    }

    return sortedIntArr;
  }

  def main(args: Array[String]) : Unit = {

    var intArr = Array(9,8,7,6,5,4,3,2,1,1,2,3,4,5,6,7,8,9);

    var sortedIntArr = returnSortedArray(intArr);

    for(i <- 0 to sortedIntArr.size-1) {
      print(sortedIntArr(i) + " ")
    } 

    println();

  }
}

