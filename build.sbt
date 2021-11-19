scalaVersion := "2.13.3"
name := "traffic-lights"
organization := "ch.epfl.scala"
version := "1.0"

ThisBuild / useCoursier := false

libraryDependencies += "org.eclipse.platform" % "org.eclipse.swt.gtk.linux.x86_64" % "3.117.0" exclude("org.eclipse.platform", "org.eclipse.swt.${osgi.platform}")
