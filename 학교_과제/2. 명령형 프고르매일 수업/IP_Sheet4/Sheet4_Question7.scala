trait Bag{
  def add(elem: Int) : Unit
  def occurence(elem: Int) : Int
}

object IntMap extends Bag{

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
}

