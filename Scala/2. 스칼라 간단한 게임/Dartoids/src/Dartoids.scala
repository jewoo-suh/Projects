package ox.games

import java.awt.Color
import scala.swing.BorderPanel.Position._
import scala.swing._
import scala.swing.event._
import scala.util.Random
import java.awt.{ Color, Shape }

/**
        The Dartoids game is a fairly simple example of a `scala.swing`
        GUI application that is composed of a model, a view and a
        controller.

        The GUI was deliberately designed with too many components,
        so as to demonstrate as many useful features of the
        `scala.swing` toolkit as feasible.

        The view (a `Target`) knows only that the model has a list
        of things  (in this case, `Dart`s) that know their own
        `Shape`; and it redraws them when it is informed that the
        model has changed.
*/
object Dartoids extends SimpleSwingApplication {
    
    object theModel extends Model[Flingable] {
        /**  The score is the number of darts whose centre is 'close' to
         *   the centre of the target.
         *   This is easy to compute because dart positions are
         *   (dimensionless) fractions of the unit length.
         */
      override def theScore: Int =
      { var count: Int = 0
        for (d <- _things) 
            { 

              if (((d.x-0.5) * (d.x-0.5) + (d.y-0.5) * (d.y-0.5)) < (0.07 * 0.07)) {count += 4}
              else if (((d.x-0.5) * (d.x-0.5) + (d.y-0.5) * (d.y-0.5)) < (0.2 * 0.2)) {count += 3}
              else if (((d.x-0.5) * (d.x-0.5) + (d.y-0.5) * (d.y-0.5)) < (0.34 * 0.34)) {count += 2}
              else if (((d.x-0.5) * (d.x-0.5) + (d.y-0.5) * (d.y-0.5)) < (0.49 * 0.49)) {count += 1}
              else {count -= 1}


             }
        count 
      }
    }

    /*scr
              if (0.435 <= d.x && d.x <= 0.565 && 0.435 <= d.y && d.y <= 0.565) {count += 4}
              else if (0.3 <= d.x && d.x <= 0.7 && 0.3 <= d.y && d.y <= 0.7) {count += 3}
              else if (0.16 <= d.x && d.x <= 0.84 && 0.16 <= d.y && d.y <= 0.84) {count += 2}
              else if (0.05 <= d.x && d.x <= 0.95 && 0.05 <= d.y && d.y <= 0.95) {count += 1}
              else {count -= 1}
              */

  /** The view is a target, tied to the model at the point of its construction */
  val theView = new Target(theModel)  {
    preferredSize     = new Dimension(1000, 1000)
    background        = Color.green
    tooltip           = "Click to put a dart at the cursor"
    theModel.runWhenChanged(this.modelChanged)
  }
    
    /** Utility to make a row from components that occupy identical widths */
    def Row(components: Component*): Component =
    { 
      val result = new GridPanel(1, 0) 
      for (component <- components) result.contents += component
      result
    }
    
    /** Utility to make a column from components that occupy identical widths */
    def Col(components: Component*): Component =
    { 
      val result = new GridPanel(0, 1) 
      for (component <- components) result.contents += component
      result
    }
    
    /** The font to be used in buttons, labels, etc. */
    val buttonFont = new Font("Arial", java.awt.Font.BOLD, 18)
    

  ////////////////////////////////////////////
  // The named, visible components of the GUI
  ///////////////////////////////////////////


    val label = new Label {
      text = "Dartoids"
      font = new Font("Arial", java.awt.Font.BOLD, 36)
    }
    
    val throwButton = new Button {
      font          = buttonFont
      text          = "Throw"
      foreground    = Color.blue
      borderPainted = true
      enabled       = true
      tooltip       = "Click to throw a dart"
    }
    
    val clearButton = new Button { 
        font       = buttonFont
        foreground = Color.red
        text       = "Clear" 
        tooltip    = "Clear the darts and start again"
    }
    
    /** 
        This checkbox acts autonomously to change its caption  when necessary.
    */
    val clearingOnResize = new CheckBox { 
        text      = "Clear on Resize" 
        tooltip   = "Tick to clear the target of darts when the window is resized"
        reactions += {
              case ButtonClicked(_) =>
                   text = if (selected) "(Clearing on Resize)" else "Clear on Resize"
        }
    }
    
    val statusField = new TextField {
      columns  = 10
      text     = "Click on the target!"
      editable = false
    }
           
    val scoreLabel = new Label { 
      font       = new Font("Courier", java.awt.Font.BOLD, 18) 
      text       = "  Score  \n"
      foreground = Color.blue
      xAlignment = scala.swing.Alignment.Center
      def score(n: Int): Unit = { text = " Score: %3d ".format(n) }
      // Change this label when the model changes
      theModel.runWhenChanged({ case () => score(theModel.theScore) })
      locally { score(0) }
    }
    
    val topRow  = Row(clearingOnResize, label, new Label(""))
    
    val leftCol = Col(throwButton, clearButton)


   /////////////////////////////////////////////////
   ////// Definition of the menubar
   ////////////////////////////////////////////////
   private type star = java.awt.Polygon

   val theFileMenu = new Menu("File")
   { contents += new MenuItem(Action("ExitWhat")  { sys.exit(0) })
     contents += new MenuItem(Action("Clear") { theModel.clear() })

     // A button that generates a new, passive view of the model
     // Closing that window removes its expression of interest in the model
     contents += new MenuItem(Action("New Dartoid Viewer")
     {   /** Another view: tied to the same model */
       val anotherView = new Target(theModel)
       { background      = Color.yellow
         tooltip         = "This shows the same target as the main window"
       }

       val scoreLabel = new Label { 
        font       = new Font("Courier", java.awt.Font.BOLD, 18) 
        text       = "  Score  \n"
        foreground = Color.blue
        xAlignment = scala.swing.Alignment.Center
        def score(n: Int): Unit = { text = " Score: %3d ".format(n) }
        // Change this label when the model changes
        theModel.runWhenChanged({ case () => score(theModel.theScore) })
        locally { score(0) }
      }



       val key = theModel.runWhenChanged(anotherView.modelChanged)


       val anotherWindow = new MainFrame {
         override def closeOperation(): Unit = {
           // do nothing when the passive window finally closes
         }
         listenTo(anotherView.mouse.clicks)
         listenTo(anotherView)
         // After this window closes, ignore the model
         listenTo(this)
         reactions += {
           case MousePressed(`anotherView`, point, _, _, _) =>
              // means: case MousePressed(component, point, _, _, _)  if component==theView =>
              val (x, y) = anotherView.toModelSpace(point.x, point.y)

              theModel.addThing(new Dart(x, y, Color.pink) {
                override val fill = true
                override def shape(scale: Float): Shape = 
                { 
                  val midX = scale*x;
                  val midY = scale*y;
                  val radius = Array(
                    if (scale>400.00) 30 else if (scale>200.00) 15 else 7,
                    if (scale>400.00) 10 else if (scale>200.00) 5 else 2,
                    if (scale>400.00) 22 else if (scale>200.00) 11 else 6,
                    if (scale>400.00) 10 else if (scale>200.00) 5 else 2
                  );
                  val nPoints = 16;
                  var X = new Array[Int](nPoints);
                  var Y = new Array[Int](nPoints);
                  var current = 0.0

                  while(current < nPoints) 
                  {
                      var i = current.asInstanceOf[Int];
                      var x = Math.cos(current*((2*Math.PI)/nPoints))*radius(i % 4);
                      var y = Math.sin(current*((2*Math.PI)/nPoints))*radius(i % 4);

                      X(i) = (x+midX).asInstanceOf[Int];
                      Y(i) = (y+midY).asInstanceOf[Int];

                      current = current + 1
                  }

                  new star(X, Y, nPoints);
                }
              })
              statusField.text = (s"You clicked at ($x, $y) with new window.")

           case WindowClosing(_) =>
             theModel.ignoreWhenChanged(key) // detach from the model
             close()
         }

         //layout(scoreLabel) = East
         contents  = new BorderPanel {
          layout(anotherView) = Center
          layout(scoreLabel) = East
        } 
         title     = s"Dartoid Viewer ($key)"
         resizable = true
         centerOnScreen()
         visible   = true
         size      = new Dimension(1000, 1000)
       }
     })
   }


  locally
  {
    /**
     *  The controller is implemented as reactions to events generated by GUI components
     *  Here we specify which Components produce events of interest
     */
    listenTo(throwButton)
    listenTo(clearButton)
    listenTo(theView.mouse.clicks)
    listenTo(theView)

    /**
     *  Here we specify reactions to each of the events of interest
     */
    reactions += {
      case ButtonClicked(`throwButton`) =>
        // means: case MousePressed(component, point, _, _, _)  if component==throwButton =>
        val (x, y) = (Random.nextFloat(), Random.nextFloat())
        val c = new Color(Random.nextInt(Int.MaxValue))
        theModel.addThing(Dart(x, y, c))
        statusField.text = s"A dart was thrown randomly at ($x, $y)"

      case ButtonClicked(`clearButton`) =>
        // means: case MousePressed(component, point, _, _, _)  if component==clearButton =>
        theModel.clear()

      case MousePressed(`theView`, point, _, _, _) =>
        // means: case MousePressed(component, point, _, _, _)  if component==theView =>
        val (x, y) = theView.toModelSpace(point.x, point.y)
        theModel.addThing(new Dart(x, y, Color.black) {
          override val fill = false
        })
        statusField.text = (s"You clicked at ($x, $y).")

      case UIElementResized(`theView`) =>
        if (clearingOnResize.selected) theModel.clear()

    }
  }

  /*
     This function defines the main window that will contain the GUI.
     A function of this name and type must be defined in all
     `SimpleSwingApplication`s
  */
  def top: MainFrame = new MainFrame {

    title   = "Dartoids"
    menuBar = new MenuBar { contents += theFileMenu }

    // Make the top-level Panel and install the other visible components in it
    contents = new BorderPanel {
      layout(topRow) = North
      layout(leftCol) = West
      layout(theView) = Center
      layout(scoreLabel) = East
      layout(statusField) = South
    }
  }
  
}





