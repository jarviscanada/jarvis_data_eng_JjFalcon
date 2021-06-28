package main.scala.ca.jrvs

object App {

  // takes x as Array[String] and returns a String
  //def foo(x: Array[String]) = x.foldLeft("")((a, b) => a + " " + b)

  def testThis(x: String): String = {
    s"I like $x"
  }

  def main(args: Array[String]) {
    println("Hello World!")
    //println("concat arguments = " + foo(args))
    println(testThis("apple"))
  }
}
