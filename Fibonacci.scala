object Fibonacci {
  def fibo(x: BigInt, prev: BigInt = 1, curr: BigInt = 0): BigInt = x match {
    case x if x < 1 => prev + curr
    case _ => fibo(x - 1, curr, prev + curr)
  }

  val fibos: Stream[BigInt] = 1 #:: 1 #:: fibos.zip(fibos.tail).map { case (prev, curr) => prev + curr }
}

object FibonacciTest {
  def main(args: Array[String]): Unit = {
    import Fibonacci._
    println((1 to 10) map { x => fibo(BigInt(x)) })
    println(fibo(114514))
    println(fibos(114514))
  }
}
