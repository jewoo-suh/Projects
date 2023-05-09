package ox.games
/**
        Here a `Model[Thing]` consists of a list of `Thing`s that can
        get added to or completely cleared by its clients.

        It communicates with a view (and a GUI/controller) by having
        them register procedures to call when its list of things
        changes.

        Each registration is associated with an integer `key` that
        can be used to withdraw the registration.

        The model can also be seen as an `Iterable` that provides
        a way of iterating through its current list of things.
*/

class Model[Thing] extends Iterable[Thing] {
  
  /** The list of things that constitute the model: accessible only from subclasses */
  protected var _things = List[Thing]()
  
  /** A client may read, but not write, the model's things: it does so by iterating over it */
  def iterator: Iterator[Thing] = _things.iterator
  
  /** 
     A notional 'score' that can be overriden in concrete instances of a `Model`. 
  */
  def theScore: Int = _things.length
  
  /** Add to the list of things */
  def addThing(thing: Thing): Unit = {
    _things = _things :+ thing
    modelChanged()
  }
  
  /** Clear the list of things */
  def clear(): Unit = {
    if (! _things.isEmpty) {
       _things = List[Thing]()
       modelChanged()
    }
  }  
  
  // The list of procedures to invoke whenever there is a change. 
  // An expression of interest can be retracted by presenting its key 
  private var _onChanges = List[(Int, ()=>Unit)]()
  private var _key       = 0
  
  /** Add a procedure to invoke whenever this model changes, and return its key. 
      This is where interest can be registered by views, GUIs, etc.
  */
  def runWhenChanged(proc: () => Unit): Int = 
      { _key += 1; _onChanges = (_key, proc) :: _onChanges; _key }
  
  /** 
      Retract the procedure corresponding to `key`, if there is one.
  */
  def ignoreWhenChanged(key: Int): Unit = { _onChanges = _onChanges.filter { case (thisKey, _) => thisKey != key } }
    
  /** Invoked by the model whenever its list of things has changed */
  def modelChanged(): Unit  = { 
      for ((key, proc) <- _onChanges) proc()
  }
  
}




