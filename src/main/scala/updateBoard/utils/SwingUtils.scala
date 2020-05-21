package updateBoard.utils

import javax.swing.SwingUtilities

object SwingUtils {

  def invokeLater(callback: => Unit) {
    SwingUtilities.invokeLater(new Runnable() {
      override def run(): Unit = callback

    })
  }

}


