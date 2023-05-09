package ox.games
import java.awt.{ Color, Shape }
/**
        A `Flingable` generates a `Shape` at a given scale for use
        when a view component is drawing or redrawing the elements
        of a model. By default the shape is filled.
*/
trait Flingable {
  val color:                    Color
  val x:                        Float
  val y:                        Float
  def shape(scale: Float):      Shape
  val fill:                     Boolean = true
}
