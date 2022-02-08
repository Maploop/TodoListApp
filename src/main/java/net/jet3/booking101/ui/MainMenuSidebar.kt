package net.jet3.booking101.ui

import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextInputDialog
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Stage
import net.jet3.booking101.Main
import net.jet3.booking101.initalization.ApplicationInitalizer
import net.jet3.booking101.util.Util

class MainMenuSidebar {
    var option1: Button? = Button("Rename")
    var box: HBox? = HBox()
    var editorRoot: AnchorPane? = AnchorPane()

    fun beforeInit() {
        option1?.getStyleClass()?.add("sidebar-btn")
        option1?.setMaxWidth(200.0)
        option1?.setMinWidth(200.0)
        option1?.setMaxHeight(50.0)
        option1?.setMinHeight(50.0)

        option1?.translateX = 10.0;
        option1?.translateY = 50.0;

        option1?.setOnMouseClicked {
            val input = TextInputDialog()
            input.setTitle("Rename")
            input.setHeaderText("Enter a new name for the file")
            input.setContentText("New name:")
            val result = input.showAndWait()
            Util.set(ApplicationInitalizer.configFile, "lastProject", result.get())
            Main.LAST_EDITED_PROJECT = result.get()
        }
    }

    fun init(stage: Stage, root: VBox, scene: Scene) {
        beforeInit()
        scene.stylesheets.add("/jfxstyle/sidebar.css")

        box!!.alignment = Pos.BOTTOM_LEFT

        box!!.children.add(option1)
        editorRoot!!.children.add(box)
        AnchorPane.setTopAnchor(box, 0.0)
        AnchorPane.setLeftAnchor(box, 0.0)
        AnchorPane.setRightAnchor(box, 0.0)
        AnchorPane.setBottomAnchor(box, 0.0)
        root.children.add(editorRoot)

        stage.scene = scene
    }
}