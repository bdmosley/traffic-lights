package us.idlechatter.trafficlights

import org.eclipse.swt._
import org.eclipse.swt.events._
import org.eclipse.swt.graphics._
import org.eclipse.swt.layout._
import org.eclipse.swt.widgets._

import SWT._
import TrafficCondition._
import TrafficSequence._
import SelectionListener._

class TrafficLightsApp(private val display: Display) {
  private var shell: Shell = _
  private var canvas: Canvas = _
  private var combo: Combo = _
  private var sequence: TrafficSequence = _
  private var currentCondition: Condition = _
  private val updateDelay = 1500
  private val colorMap =
    Map(
      "green" -> display.getSystemColor(COLOR_GREEN),
      "red"   -> display.getSystemColor(COLOR_RED),
      "amber" -> new Color(display, 207, 94, 2),
      "black" -> display.getSystemColor(COLOR_BLACK)
    )
  private val sequenceMap =
    Map(
      "US Sequence"  -> US,
      "UK Sequence"  -> UK,
      "Always Red"   -> AlwaysRed,
      "Always Amber" -> AlwaysAmber,
      "Always Green" -> AlwaysGreen,
      "Always On"    -> AlwaysOn,
      "Always Off"   -> AlwaysOff
    )
  initialize()

  private def initialize(): Unit = {
    shell = new Shell(display, SHELL_TRIM & ~RESIZE)
    shell.setText("Traffic Lights")
    shell.setLayout(new GridLayout())
    shell.setSize(260, 775)

    sequence = AlwaysOff
    currentCondition = sequence.default

    combo = new Combo(shell, DROP_DOWN | READ_ONLY)
    combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL))
    for (s <- sequenceMap.keys.toIndexedSeq.sorted) combo.add(s)
    combo.select(2)
    combo.addSelectionListener {
      widgetSelectedAdapter { _ =>
        sequence = sequenceMap(combo.getText)
      }
    }

    canvas = new Canvas(shell, SWT.NONE)
    canvas.setLayoutData(new GridData(GridData.FILL_BOTH))
    canvas addPaintListener(e => drawTrafficLight(e.gc))

    display.timerExec(updateDelay, () => updateTrafficLight())

    shell.open()
  }

  private def drawRed(gc: GC, state: LightState): Unit = {
    val color = state match {
      case On  => colorMap("red")
      case Off => colorMap("black")
    }
    gc.setBackground(color)
    gc.fillOval(25, 25, 200, 200)
  }

  private def drawAmber(gc: GC, state: LightState): Unit = {
    val color = state match {
      case On  => colorMap("amber")
      case Off => colorMap("black")
    }
    gc.setBackground(color)
    gc.fillOval(25, 250, 200, 200)
  }

  private def drawGreen(gc: GC, state: LightState): Unit = {
    val color = state match {
      case On  => colorMap("green")
      case Off => colorMap("black")
    }
    gc.setBackground(color)
    gc.fillOval(25, 475, 200, 200)
  }

  private def drawTrafficLight(gc: GC): Unit = {
    val ConditionVal(red, amber, green) = currentCondition
    drawRed(gc, red)
    drawAmber(gc, amber)
    drawGreen(gc, green)
    canvas.update()
  }

  private def updateTrafficLight(): Unit = {
    currentCondition = sequence.next(currentCondition)
    canvas.redraw()
    canvas.update()
    display.timerExec(updateDelay, () => updateTrafficLight())
  }
}
