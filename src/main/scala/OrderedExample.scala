object OrderedExample {
  case class Foo(n: Long) extends Ordered[Foo] {
    def compare(that: Foo): Int = if (this.n < that.n) -1 else if (this.n > that.n) 1 else 0
  }

  def main(args: Array[String]): Unit = {
    println(Seq(Foo(5), Foo(3), Foo(1), Foo(2), Foo(4)).sorted)
  }
}
