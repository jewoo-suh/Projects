package ox.games

import java.awt.{ Color, Shape }

/** Dart coordinates are expressed in 'model space'; that is
    as a fraction of the unit square.
*/
case class Dart(val x: Float, val y: Float, val color: Color) extends Flingable {
  private type Ellipse = java.awt.geom.Ellipse2D.Float
  
  /** The shape of this dart at this `scale`; this is what gets drawn by the 
      target.
  */
  def shape(scale: Float): Shape = 
  { val diam = if (scale>400.00) 24.0F else if (scale>200.00) 16.00F else 6.0F
    new Ellipse(scale*x-diam/2.0F, scale*y-diam/2.0F, diam, diam)
  }
}


