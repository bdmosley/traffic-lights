package us.idlechatter.trafficlights

object TrafficCondition extends Enumeration {
  sealed case class ConditionVal(red: LightState, amber: LightState, green: LightState)
    extends super.Val
  type Condition = ConditionVal

  val Red      = new ConditionVal(On,  Off, Off)
  val Amber    = new ConditionVal(Off, On,  Off)
  val Green    = new ConditionVal(Off, Off, On)
  val AmberRed = new ConditionVal(On,  On,  Off)
  val AllOff   = new ConditionVal(Off, Off, Off)
  val AllOn    = new ConditionVal(On,  On,  On)
}
