import cats.data.State

val a: State[Int, String] = State[Int, String] { state =>
  (state, s"The state is $state")
}
// a: cats.data.State[Int,String] = cats.data.IndexedStateT@54401d89

// Get the state and the result:
val (state, result) = a.run(10).value
// state: Int = 10
// result: String = The state is 10
// Get the state, ignore the result:
val state = a.runS(10).value
// state: Int = 10
// Get the result, ignore the state:
val result = a.runA(10).value
// result: String = The state is 10

val step1 = State[Int, String] { num =>
  val ans = num + 1
  (ans, s"Result of step1: $ans")
}
// step1: cats.data.State[Int,String] = cats.data.IndexedStateT@4985a202
val step2 = State[Int, String] { num =>
  val ans = num * 2
  (ans, s"Result of step2: $ans")
}
// step2: cats.data.State[Int,String] = cats.data.IndexedStateT@29f286da
val both = for {a <- step1
                b <- step2
                } yield (a, b)
// both: cats.data.IndexedStateT[cats.Eval,Int,Int,(String, String)] =cats.data.IndexedStateT@5458eb96
val (state, result) = both.run(20).value
// state: Int = 42
// result: (String, String) = (Result of step1: 21,Result of step2:42)