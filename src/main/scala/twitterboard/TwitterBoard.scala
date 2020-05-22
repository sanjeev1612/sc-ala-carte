package twitterboard

import javax.swing._
import twitterboard.controllers.{IFrameControllerInterface, NewsSourceIFrameController, TwitterSourceIFrameController}

trait UpdateControllerInterface {
    def updateContent(): Unit
}

object FutureBoard extends App with UpdateControllerInterface {

    // swing things
    val jFrame = new JFrame("Twitter Hash Tags")
    val desktopPane = new JDesktopPane()
    val menuBar = new JMenuBar()
    val fileMenu = new JMenu("File")
    val updateMenuItem = new JMenuItem("Get From Twitter")
    val exitMenuItem = new JMenuItem("Exit")

    val pythonTwitterSource =TwitterSource(
        "Twitter #python",
        "https://twitter.com/search?q=%23python&src=typed_query"
    )

    val javaTwitterSource = TwitterSource(
        "Twitter #java",
        "https://twitter.com/search?q=%23java&src=typed_query"
    )

    val scalaTwitterSource = TwitterSource(
        "Twitter #scala",
        "https://twitter.com/search?q=%23scala&src=typd"
    )


    // MVC controllers
    val pythonController = new TwitterSourceIFrameController(pythonTwitterSource)
    val javaController = new TwitterSourceIFrameController(javaTwitterSource)
    val scalaController = new TwitterSourceIFrameController(scalaTwitterSource)
    val iFrameControllers: Seq[IFrameControllerInterface] = Seq(
        pythonController, javaController, scalaController
    )


    // “main” begins
    FutureBoardUtils.configMainFrameSizeAndLocation(jFrame)

    //TODO don’t pass the controllers here; just use a callback to us
    FutureBoardUtils.configureMenu(
        jFrame,
        menuBar,
        fileMenu,
        exitMenuItem,
        updateMenuItem,
        this
    )
    jFrame.setContentPane(desktopPane)

    FutureBoardUtils.addIFramesToDesktopPane(
        pythonController,
        javaController,
        scalaController,
        desktopPane
    )

    FutureBoardUtils.makeMainFrameVisible(jFrame)

    /**
      * Enable this if desired. This will cause the application
      * to retrieve its results from the web pages as soon as
      * the application is started.
      */
    //updateContent()


    /**
      * when called, tell all of the controllers to update their content
      */
    def updateContent(): Unit = {
        for (c <- iFrameControllers) c.updateContent()
    }

}







