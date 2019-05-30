object OrderingExampleUsingDefaultImpl {
  case class Foo(n: Long)

  implicit val fooOrdering: Ordering[Foo] = Ordering.by(_.n)

  def main(args: Array[String]): Unit = {
    println(Seq(Foo(5), Foo(3), Foo(1), Foo(2), Foo(4)).sorted)
  }
}
