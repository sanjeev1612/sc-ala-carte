package interview

import java.util.concurrent.LinkedBlockingQueue

case class Printer(name: String)

case class PrinterInput(jobName: String = "defaultPrinter", timeToComplete: Int)

case class PrinterOutPut(actualTimeToComplet: Int)

sealed trait PrinterAck

case object Success extends PrinterAck

case object Failure extends PrinterAck

case class Task(printerName: String, printerInput: PrinterInput)

object Controller {

  val queue = new LinkedBlockingQueue[Task]();

  import ServiceLayer._

  def addTaskToQueueandPollIfIdle(task: Task): Unit = {
    //add the task to the queue for processing one by one based on availabilty of printer
    //take input from Input
    queue.add(Task("task1", PrinterInput("printSanjeevJob", 20)))
    queue.add(Task("task1", PrinterInput("printBharatJob", 30)))
    queue.add(Task("task1", PrinterInput("printRahulJob", 40)))

  }

  def routeToServiceLayer(printer: Printer, printerInput: PrinterInput) = {
    getOutputFromPrinterLib(printer: Printer, printerInput: PrinterInput);
  }

}

object ServiceLayer {

  def getOutputFromPrinterLib(printer: Printer, printerInput: PrinterInput): PrinterOutPut = {
    // logic to call printer library and get the time based on printer name and printer input
    val actualValue = 10
    val printerOutput = PrinterOutPut(actualValue)
    printerOutput
    //return a future or warp in an IO Monad of the time duration as its an I/O call and get it once it is complete

  }


















  

}
