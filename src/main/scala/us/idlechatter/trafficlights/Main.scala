package us.idlechatter.trafficlights

import org.eclipse.swt.widgets.Display

object Main {
  def main(args: Array[String]): Unit = {
    val display = new Display
    val app = new TrafficLightsApp(display)
    while (display.getShells().length > 0 && !display.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep()
    }
    display.dispose();
  }
}
