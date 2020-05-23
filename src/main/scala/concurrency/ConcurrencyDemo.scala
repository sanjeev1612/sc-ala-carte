package concurrency

trait MyRunnable {
  def run(): Unit
}

trait MyCallable[V] {
  def call(): V
}
