package ox.games

/**
        A `Target` is a component used to display a collection of
        `Flingable` objects. It is shown in the form of a classic
        "Bull's Eye".  In the "Model-View-Controller" architecture
        it plays the role of a view.
        
        The standard way of adding a new graphical component to the
        `scala.swing` repertoire is to extend `Panel` class, with
        method(s) to paint the model it will be used to view.

        This view expects its associated model to inform it by
        invoking `modelChanged()` when the model changes. It
        requires only that the model can provide an `Iterator`
        over the `Flingable`s it is comprised of.
*/
class Target(theModel: Iterable[Flingable]) extends scala.swing.Panel {

 import scala.swing.event.{ UIElementResized, UIElementShown }
 import java.awt.{ Graphics2D, Color, RenderingHints }
     
 /**  Geometric properties of this component. The invariant relations
      between these properties are re-established (by `calculateGeometry`)
      in reaction to  the `UIElementResized` event that occurs whenever the
      component size changes.
  */

  var w, h, radius, diam, ox, oy, cx, cy = 0
  
  /** 
      Scale factor: `_*scale` maps model units to device units (pixels) and
      `_/scale` maps device units to model units. 

      Model units are expressed as fractions of the dimensions of
      the square inside which the target/background image is drawn.
      The invariants between geometric properties are:
      
      {{{   
        w      = size.width             // width of the component  (device units: pixels)
        h      = size.height            // height of the component (device units: pixels)
        diam   = Math.min(w, h) - 8     // diameter of the target             (device units)
        radius = diam/2                 // radius of the target               (device units)
        ox     = (w-diam)/2             // x coordinate  of the target origin (device units)
        oy     = (h-diam)/2             // y coordinate  of the target origin (device units)
        cx     = ox+radius              // x coordinate of target centre      (device units)
        cy     = oy+radius              // y coordinate of target centre      (device units)
        scale  = diam.toFloat           // Scale factor      
       }}}
 */
  var scale: Float = 1.0F

  /** Calculate geometric properties and scale from the current component size (in device coordinates). 
      This must be invoked whenever the component size changes. Happily a size change is reported when
      the component first becomes visible. 
  */
  def calculateGeometry(): Unit =
  {     w      = size.width
        h      = size.height
        diam   = Math.min(w, h) - 8
        scale  = diam.toFloat          
        radius = diam/2
        ox     = (w-diam)/2
        oy     = (h-diam)/2
        cx     = ox+radius
        cy     = oy+radius
  }
  
  locally {
     listenTo(this)
     // Register reaction to size or visibility changes, which is to recalculate the geometry of the target
     reactions += {
           case UIElementResized(_) => calculateGeometry()
           case UIElementShown(_)   => calculateGeometry()
      }
  }
  
  
  /** 
      Transform the pixel coordinates (in device-space) of a mouse
      click on this component into model space (ie fractions of the
      current device-space size of the target)
  */
  def toModelSpace(x: Int, y: Int): (Float, Float) =
  {   
      ((x-ox).toFloat/scale, (y-oy).toFloat/scale)
  }    
  
  /**
        All objects that get drawn on this graphics context will
        have coordinates computed relative to the origin of the
        background image.  We therefore set a coordinate transformation
        on the context that translates them appropriately.
  */
  private def setOrigin(g: Graphics2D): Unit =
  { val current = g.getTransform()              // get a /copy/ of the current graphics transform
    current.translate(ox.toDouble, oy.toDouble) // compose it with a translation to 
    g.setTransform(current)                     // set the current transform
  }
  
  /**
    Draw the background of the view. This can be changed in a subclass, but
    here it is designed to look like an archery target.
  */
  protected def drawBackgroundImage(g: Graphics2D): Unit = 
  { // Draw the target as three concentric discs
    g.setColor(Color.blue)
    g.fillOval(ox, oy, diam, diam)
    g.setColor(Color.gray)
    g.fillOval(cx-radius*2/3, cy-radius*2/3, diam*2/3, diam*2/3)
    g.setColor(Color.yellow)
    g.fillOval(cx-radius*2/5, cy-radius*2/5, diam*2/5, diam*2/5)
    g.setColor(Color.red)
    g.fillOval(cx-radius/7, cy-radius/7, diam/7, diam/7)
  }
  
  /** 
      Paint the model; this is invoked by the Swing framework at
      appropriate moments, including soon after `repaint()` is
      invoked.  It is also invoked if (parts of) the component
      were obscured and become visible. 
  */
  override def paintComponent(g: Graphics2D): Unit = 
  {
    // Antialising is enabled by this hint
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        
    // Erase the entire background and show a border
    g.setColor(background)
    g.fillRect(2, 2, w-2, h-2)
    g.setColor(foreground)
    g.drawRect(1, 1, w-2, h-2)
    
    // Draw the background image
    drawBackgroundImage(g)    
    
    // Set the origin of the drawing space so that the fill/draw coordinates are
    // interpreted relative to the origin of the target.
    setOrigin(g)
        
    for (flingable <- theModel) {
      g.setColor(flingable.color)
      if (flingable.fill)
         g.fill(flingable.shape(scale))
      else
         g.draw(flingable.shape(scale))
    }    
  }
  
  /** The model informs a view that it has changed by invoking `modelChanged()`. 
      There is only one thing to do, namely repaint the component. Calling `repaint` 
      schedules a call of `paintComponent` on the gui thread. 
  */
  def modelChanged(): Unit = repaint()
  
}




