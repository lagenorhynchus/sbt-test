object Main {
  val s = ""
  val ns = List(1, 2, 3, 4, 5)

  def main(args: Array[String]): Unit = {
    println(ns.map(_ * 2))
  }
}


class Point(val x: Int, val y: Int) {
  def +(p: Point): Point = new Point(x + p.x, y + p.y)

  override def toString: String = s"($x, $y)"
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

// covariant (result type) & contravariant (argument type)
class AClass
class BClass extends AClass
class CClass extends BClass
object ABC {
  def f: BClass => BClass = (x: AClass) => x.asInstanceOf[CClass]
}


// Array
object ArrayExercise {
  def swapArray[T](arr: Array[T])(i: Int, j: Int): Unit = {
    val tmp = arr(i)
    arr(i) = arr(j)
    arr(j) = tmp
  }
}

// List
object ListExercise {
  def joinByComma(start: Int, end: Int): String = (start to end).mkString(",")

  def reverse[T](xs: List[T]): List[T] = xs.foldLeft(List[T]())((acc, x) => x :: acc)

  def sum(ns: List[Int]): Int = ns.foldRight(0)((n, acc) => n + acc)

  def product(ns: List[Int]): Int = ns.foldRight(1)((n, acc) => n * acc)

  def mkString[T](xs: List[T])(sep: String): String = xs.addString(new StringBuilder(), sep).toString

  def map[T, U](xs: List[T])(f: T => U): List[U] = xs.foldLeft(List[U]())((acc, x) => f(x) :: acc).reverse

  def filter[T](xs: List[T])(f: T => Boolean): List[T] = xs.foldLeft(List[T]())((acc, x) => if (f(x)) x :: acc else acc).reverse

  def count[T](xs: List[T])(f: T => Boolean): Int = xs.foldLeft(0)((acc, x) => if (f(x)) 1 + acc else acc)
}


// pattern matching
object PatternMatchingExercise {
  sealed abstract class DayOfWeek(val label: String)
  case object Sunday extends DayOfWeek("日")
  case object Monday extends DayOfWeek("月")
  case object Tuesday extends DayOfWeek("火")
  case object Wednesday extends DayOfWeek("水")
  case object Thursday extends DayOfWeek("木")
  case object Friday extends DayOfWeek("金")
  case object Saturday extends DayOfWeek("土")

  def nextDayOfWeek(d: DayOfWeek): DayOfWeek = d match {
    case Sunday => Monday
    case Monday => Tuesday
    case Tuesday => Wednesday
    case Wednesday => Thursday
    case Thursday => Friday
    case Friday => Saturday
    case Saturday => Sunday
  }

  sealed trait Tree
  case class Branch(value: Int, left: Tree, right: Tree) extends Tree
  case object Empty extends Tree

  def max(t: Tree): Int = t match {
    case Empty => sys.error("Empty tree")
    case Branch(v, Empty, Empty) => v
    case Branch(v, Empty, r) => Math.max(v, max(r))
    case Branch(v, l, Empty) => Math.max(v, max(l))
    case Branch(v, l, r) => Seq(v, max(l), max(r)).max
  }

  def min(t: Tree): Int = t match {
    case Empty => sys.error("Empty tree")
    case Branch(v, Empty, Empty) => v
    case Branch(v, Empty, r) => Math.min(v, min(r))
    case Branch(v, l, Empty) => Math.min(v, min(l))
    case Branch(v, l, r) => Seq(v, min(l), min(r)).min
  }

  def depth(t: Tree): Int = t match {
    case Empty => 0
    case Branch(_, l, r) => Seq(depth(l), depth(r)).max + 1
  }

  def sort(t: Tree): Tree = {
    def toList(t: Tree): List[Int] = t match {
      case Empty => Nil
      case Branch(v, l, r) => toList(l) ++ List(v) ++ toList(r)
    }
    def toBinarySearchTree(ns: List[Int]): Tree = {
      def insert(n: Int, t: Tree): Tree = t match {
        case Empty => Branch(n, Empty, Empty)
        case Branch(v, l, r) =>
          if (n < v) Branch(v, insert(n, l), r) else Branch(v, l, insert(n, r))
      }
      ns.foldLeft(Empty: Tree)((acc, n) => insert(n, acc))
    }
    toBinarySearchTree(toList(t))
  }
}


// implicit parameter
object ImplicitParameterExercise {
  trait Monoid[T] {
    def mzero: T
    def mappend(a: T, b: T): T
  }

  object Monoid {
    implicit val intMonoid = new Monoid[Int] {
      def mzero = 0
      def mappend(a: Int, b: Int) = a + b
    }

    implicit val stringMonoid = new Monoid[String] {
      def mzero = ""
      def mappend(a: String, b: String) = a + b
    }

    implicit val pointMonoid = new Monoid[Point] {
      def mzero = Point(0, 0)
      def mappend(a: Point, b: Point) = Point(a.x + b.x, a.y + b.y)
    }
  }

  def sum[T: Monoid](xs: Seq[T]): T = {
    val m = implicitly[Monoid[T]]
    xs.foldLeft(m.mzero)(m.mappend)
  }

  case class Complex(r: Double, i: Double) {
    override def toString: String = {
      val sign = if (i < 0) "" else "+"
      s"${r}${sign}${i}i"
    }
  }
  object Complex {
    implicit val monoid = new Monoid[Complex] {
      def mzero = Complex(0, 0)
      def mappend(a: Complex, b: Complex): Complex = Complex(a.r + b.r, a.i + b.i)
    }
  }
}

object OptionExercise {
  val result1: Option[Int] =
    Some(2).map { a =>
      Some(3).map { b =>
        Some(5).map { c =>
          Some(7).map { d =>
            Some(11).map { e => a * b * c * d * e }
          }.flatten
        }.flatten
      }.flatten
    }.flatten

  val result2: Option[Int] =
    Some(2).flatMap { a =>
      Some(3).flatMap { b =>
        Some(5).flatMap { c =>
          Some(7).flatMap { d =>
            Some(11).map { e => a * b * c * d * e }
          }
        }
      }
    }

  val result3: Option[Int] = for {
    a <- Some(2)
    b <- Some(3)
    c <- Some(5)
    d <- Some(7)
    e <- Some(11)
  } yield a * b * c * d * e

  val result4: Either[String, Int] = for {
    a <- Right(2).right
    b <- Right(3).right
    c <- Right(5).right
    d <- Right(7).right
    e <- Right(11).right
  } yield a * b * c * d * e
}
