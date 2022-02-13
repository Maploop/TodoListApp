package net.jet3.booking101.component

import javafx.scene.Scene
import javafx.scene.control.MenuBar
import javafx.scene.control.ToolBar
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import javafx.stage.StageStyle

abstract class CustomToolbar {
    fun initToolbar(root: BorderPane, scene: Scene?, stage: Stage) {
        stage.initStyle(StageStyle.UNDECORATED)

        root.top = toolbar()


        stage.scene = scene

    }

    abstract fun toolbar(): MenuBar
}