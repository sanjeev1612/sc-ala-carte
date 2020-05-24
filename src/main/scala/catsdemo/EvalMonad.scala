package catsdemo

import cats.Eval

object EvalDemo extends App {
  println(factorialWithEval(5000).value)

  def factorialWithEval(n: BigInt): Eval[BigInt] =
    if (n == 1) {
      Eval.now(n)
    } else {
      factorialWithEval(n - 1).map(_ * n)
    }

  def factorial(n: BigInt): BigInt =
    if (n == 1) n else n * factorial(n - 1)

  def factorialWithDefer(n: BigInt): Eval[BigInt] =
    if (n == 1) {
      Eval.now(n)
    } else {
      Eval.defer(factorialWithEval(n - 1).map(_ * n))
    }

}