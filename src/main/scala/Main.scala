object Main {
  val s = ""
  val ns = List(1, 2, 3, 4, 5)

  def main(args: Array[String]): Unit = {
    println(ns.map(_ * 2))
  }
}


class Point(val x: Int, val y: Int) {
  def +(p: Point): Point = new Point(x + p.x, y + p.y)

  override def toString(): String = s"($x, $y)"
}

object Point {
  def apply(x: Int, y: Int): Point = new Point(x, y)
}


class Adder {
  // currying
  def add(x: Int)(y: Int): Int = x + y
}


trait Greeter {
  def greet(): Unit
}

trait HelloGreeter extends Greeter {
  def greet(): Unit = println("Hello!")
}

trait Robot {
  // self type annotation
  self: Greeter =>

  def start(): Unit = greet()
}


// covariant
trait Stack[+T] {
  def pop: (T, Stack[T])
  def push[E >: T](e: E): Stack[E]
  def isEmpty: Boolean
}

class NonEmptyStack[+T](private val top: T, private val rest: Stack[T]) extends Stack[T] {
  def push[E >: T](e: E): Stack[E] = new NonEmptyStack[E](e, this)
  def pop: (T, Stack[T]) = (top, rest)
  def isEmpty: Boolean =  false
}

case object EmptyStack extends Stack[Nothing] {
  def pop: Nothing = throw new IllegalArgumentException("empty stack")
  def push[E >: Nothing](e: E): Stack[E] = new NonEmptyStack[E](e, this)
  def isEmpty: Boolean = true
}

object Stack {
  def apply(): Stack[Nothing] = EmptyStack
}

// covariant (return type) & contravariant (argument type)
class A
class B extends A
class C extends B
object ABC {
  def f: B => B = (x: A) => x.asInstanceOf[C]
}
