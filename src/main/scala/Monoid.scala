trait Monoid[A] {
  def mempty: A
  def mappend(x: A, y: A): A
  def mconcat(xs: Seq[A]): A = xs.foldLeft(mempty)(mappend)
}

object Monoid {
  def sum[A: Monoid](xs: A*): A = implicitly[Monoid[A]].mconcat(xs)

  implicit def numericMonoid[A: Numeric] = new Monoid[A] {
    val numericOp = implicitly[Numeric[A]]
    def mempty: A = numericOp.zero
    def mappend(x: A, y: A): A = numericOp.plus(x, y)
  }

  implicit def setMonoid[A] = new Monoid[Set[A]] {
    def mempty: Set[A] = Set()
    def mappend(x: Set[A], y: Set[A]): Set[A] = x ++ y
  }
}

object Test {
  def main(args: Array[String]): Unit = {
    import Monoid._
    println(sum(0, 1, 1, 2, 3, 5))
    println(sum('a', 'b', 'b', 'c', 'd', 'f'))
    println(sum(Set(0, 1), Set(1, 2), Set(3, 5)))
  }
}
