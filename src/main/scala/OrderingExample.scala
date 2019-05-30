object OrderingExample {
  case class Foo(n: Long)

  implicit object FooOrdering extends Ordering[Foo] {
    def compare(x: Foo, y: Foo): Int = if (x.n < y.n) -1 else if (x.n > y.n) 1 else 0
  }

  def main(args: Array[String]): Unit = {
    println(Seq(Foo(5), Foo(3), Foo(1), Foo(2), Foo(4)).sorted)
  }
}
