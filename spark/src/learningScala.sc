// VARIABLES
// Scala is strongly-typed language
// will infer Data Type if not provided
// var vs val => val is the same as constant declaration
val x: Int = 68
println(x)

var y = 2
// y = "two" gives error
// x = 67 gives error
println(x+y)

// BOOLEAN and CONDITIONS
var bool = false
var bool2 = 42!=24
var bool3 = bool2 && bool
println(bool3)
println(bool2 || bool )

// scala does not provide truthsy/falsey similar to python
// if ("hello") println ("wow") gives error

// EXPRESSION
// blocking works similar to Java with ; for multi-statements if on single line
// {} are optional, you can write everything in single line w/o these
println{
  // expression
  {
    var x = 10
    x * 5 // will not be evaluated
    if (x < 5)
      {
        x * 10
      }
    else
      {
        x * 2
      }
    // only the last statement gets executed
  }
}

// STRING INTERPOLATION s"$var x"
// s to indicate string interpolation
// $ means it's a variable
// global variable scope
var first = "lion"
println{
  {
    var second = "king"
    s"$first $second movie"
  }
}
// println(second) gives an error

// FUNCTIONS
// takes x as String and returns a String
def testThis(x: String): String = {
  s"I like $x"
}
println(testThis("apple"))

// using default argument
def cartoon(x: String = "pokemon"): String = {
  s"I play $x"
}
println(cartoon("sports"))

// OBJECT ORIENTED SCALA
// Method Overloading => similar to Java
class Animal(val name: String, val sound: String = "moo"){
  val warmBlooded = true
  def makeSounds: String = {
    s"$name makes $sound"
  }
  def howMany(x: String): String = {
    s"String: There's only $x."
  }
  def howMany(x: Int): String = {
    s"Integer: Theres only $x"
  }
}
var u = new Animal("Cat", "meows")
println(u.makeSounds)
println(u.howMany(2))
println(u.howMany("one"))

class Singer(var name: String)
val m = new Singer("Miley")
val b = new Singer("Bradley")
m.name = "Mikey"

val singers = List(m, b)

println {
  singers.map {(s: Singer) =>
    s.name }
}
// using shortcut placeholder
println {
  singers.map (_.name)
}

// Inheritance works similar to Java
// Super and Override
class Dog extends Animal("Dog") {
  override def makeSounds: String = s"$name makes woof-woof"
}
var d = new Dog
var warm = d.warmBlooded
println(d.makeSounds, s" Warm Blooded: $warm")

// Abstract Class => cannot be instantiated
abstract class Tree {
  var flowering = false
}

class Rose extends Tree
var r = new Rose
r.flowering = true
println("Rose flowers: ", r.flowering)
// var t = new Tree gives error

// SCALA APPLY
// default method which will be ran w/o calling it explicitly
class Cloud {
  def apply() = {
    "floating"
  }
}
var c = new Cloud
println(c())

// OBJECTS
// objects can't be instantiated
// objects can inherit from a class
// classes can't inherit from objects
class Simpson {
  var color = "yellow"
}

object Bart extends Simpson {
  var food = "cookies"
  var drinks = "beer"
  def speak= {
    s"I am $color"
  }
  def apply() = {
    s"Give me $food and $drinks"
  }
}

println(Bart.speak)
println(Bart())

// TRAITS
// can't be instantiated directly
// but objects can inherit from multiple traits [like an interface]

trait Cool {
  var speak = "I am groovy"
}

trait  Collected {
  def feel = {
    "I am feeling collected"
  }
}

object joeCool extends Cool with Collected {
  def describe = {
    s"$speak and $feel"
  }
}
println(joeCool.describe)

