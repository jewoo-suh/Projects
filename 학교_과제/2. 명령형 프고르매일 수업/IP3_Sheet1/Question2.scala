import scala.math

trait shape {
	def width : Float;
	def height : Float;
	def area : Float;
	def changeDim(wNew: Float, hNew: Float) : Unit;
	def isPerfect : Boolean; //return true if shape is square or circle
}

class rectangle(wInit: Float, hInit: Float) extends shape {
	private var w = wInit;
	private var h = hInit;
	def width : Float = w;
	def height : Float = h;
	def area : Float = w * h;
	def changeDim(wNew: Float, hNew: Float) = {w = wNew; h = hNew;}
	def isPerfect = (w == h); //Checks if rectangle is square
}

class ellipse(wInit: Float, hInit: Float) extends shape {
	private var w = wInit;
	private var h = hInit;
	def width : Float = w;
	def height : Float = h;
	def area : Float = w * h * math.Pi.asInstanceOf[Float];
	def changeDim(wNew: Float, hNew: Float) = {w = wNew; h = hNew;}
	def isPerfect = (w == h); //Checks if ellipse is circle
}