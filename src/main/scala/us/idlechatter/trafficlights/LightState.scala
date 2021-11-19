package us.idlechatter.trafficlights

sealed class LightState
object On extends LightState
object Off extends LightState