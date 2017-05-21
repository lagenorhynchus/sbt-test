import scala.annotation.tailrec

object Fibonacci {
  @tailrec
  def fibo(i: Int, prev: BigInt = 1, curr: BigInt = 0): BigInt = i match {
    case 0 => 0
    case i if i < 2 => prev + curr
    case _ => fibo(i - 1, curr, prev + curr)
  }

  type Matrix = (BigInt, BigInt, BigInt, BigInt)

  def fibo2(i: Int): BigInt = {
    def prod(a: Matrix)(b: Matrix): Matrix = (a, b) match {
      case ((a11, a12, a21, a22), (b11, b12, b21, b22)) =>
        (a11 * b11 + a12 * b21, a11 * b12 + a12 * b22,
         a21 * b11 + a22 * b21, a21 * b12 + a22 * b22)
    }
    Stream.iterate((1, 0, 0, 1): Matrix)(prod((1, 1, 1, 0)))(i)._2
  }

  def fibo3(i: Int): BigInt = {
    @tailrec
    def pow(f: Matrix => Matrix => Matrix, x: Matrix, n: Int, a: Matrix): Matrix = n match {
      case 0 => a
      case n if n % 2 == 0 => pow(f, f(x)(x), n / 2, a)
      case _ => pow(f, x, n - 1, f(x)(a))
    }
    def prod(a: Matrix)(b: Matrix): Matrix = (a, b) match {
      case ((a11, a12, a21, a22), (b11, b12, b21, b22)) =>
        (a11 * b11 + a12 * b21, a11 * b12 + a12 * b22,
         a21 * b11 + a22 * b21, a21 * b12 + a22 * b22)
    }
    pow(prod, (1, 1, 1, 0), i, (1, 0, 0, 1))._2
  }

  val fibos: Stream[BigInt] =
    BigInt(0) #:: BigInt(1) #:: fibos.zip(fibos.tail).map { case (prev, curr) => prev + curr }
}

object FibonacciTest {
  def main(args: Array[String]): Unit = {
    import Fibonacci._

    println("=== fibo ===")
    println((0 to 9) map { i => fibo(i) })
    println(fibo(100000))

    println("=== fibo2 ===")
    println((0 to 9) map { i => fibo2(i) })
    // println(fibo2(100000))

    println("=== fibo3 ===")
    println((0 to 9) map { i => fibo3(i) })
    println(fibo3(100000))

    println("=== fibos ===")
    println(fibos.take(10).toVector)
    println(fibos(100000))
  }
}
