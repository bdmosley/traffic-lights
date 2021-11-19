package us.idlechatter.trafficlights

import TrafficCondition._

trait TrafficSequence {
  def default: Condition
  def next(current: Condition): Condition
}

object TrafficSequence {
  object US extends TrafficSequence {
    def default: Condition = Red
    def next(current: Condition): Condition = current match {
      case Red => Green
      case Green => Amber
      case Amber => Red
      case _ => default
    }
  }

  object UK extends TrafficSequence {
    def default: Condition = Red
    def next(current: Condition): Condition = current match {
      case Red => AmberRed
      case AmberRed => Green
      case Green => Amber
      case Amber => Red
      case _ => default
    }
  }

  object AlwaysRed extends TrafficSequence {
    def default: Condition = Red
    def next(current: Condition): Condition = Red
  }

  object AlwaysAmber extends TrafficSequence {
    def default: Condition = Amber
    def next(current: Condition): Condition = Amber
  }

  object AlwaysGreen extends TrafficSequence {
    def default: Condition = Red
    def next(current: Condition): Condition = Green
  }

  object AlwaysOff extends TrafficSequence {
    def default: Condition = AllOff
    def next(current: Condition): Condition = AllOff
  }

  object AlwaysOn extends TrafficSequence {
    def default: Condition = AllOn
    def next(current: Condition): Condition = AllOn
  }
}