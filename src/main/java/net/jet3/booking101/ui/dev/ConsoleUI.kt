package net.jet3.booking101.ui.dev

import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import net.jet3.booking101.component.AppConsole
import net.jet3.booking101.component.Console

class ConsoleUI {
    var field: TextField = TextField()
    var output: TextField = TextField()
    var execute: Button = Button("Send")
    var copy: Button = Button("Copy")
    var clear: Button = Button("Clear")

    var console: AppConsole

    init {
        console = AppConsole()
        output.isEditable = false
        output.prefHeight = 470.0
        output.text = console.text

        copy.translateX = 100.0
        clear.translateX = 140.0
        field.translateX = 185.0
        execute.translateX = 555.0
        field.setPrefSize(360.0, 20.0)
    }

    fun start() {
        val priamryStage = Stage()
        priamryStage.title = "Console"

        val bottom = Group()
        bottom.children.addAll(field, execute, copy, clear)

        val root = BorderPane()
        root.top = output;
        root.bottom = bottom;

        val scene = Scene(root, 500.0, 500.0)
        priamryStage.scene = scene

        priamryStage.show()
    }
}