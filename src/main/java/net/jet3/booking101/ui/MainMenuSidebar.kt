package net.jet3.booking101.ui

import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import net.jet3.booking101.ManagementYaar
import net.jet3.booking101.util.FXDialogs

class MainMenuSidebar {
    var option1: Button? = Button("Rename")
    var editorRoot: AnchorPane? = AnchorPane()
    var projectLabel: Label = Label();

    init {
        projectLabel = Label("Workspace: " + ManagementYaar.LAST_EDITED_PROJECT)
        projectLabel.styleClass.add("label-project")
        option1?.styleClass?.add("sidebar-btn")
        option1?.maxWidth = 200.0
        option1?.minWidth = 200.0
        option1?.maxHeight = 50.0
        option1?.minHeight = 50.0

        option1?.translateX = 10.0;
        option1?.translateY = 50.0;

        option1?.setOnMouseClicked {
            val input =
                FXDialogs.showTextInput("Rename workspace", "Enter new name", ManagementYaar.LAST_EDITED_PROJECT)
            ManagementYaar.LAST_EDITED_PROJECT = input
            MainUI().update()
        }
    }

    fun init(stage: Stage, root: BorderPane, scene: Scene) {
        scene.stylesheets.add("/jfxstyle/sidebar.css")

        editorRoot!!.children.add(option1)
        editorRoot!!.children.add(projectLabel)
        root.left = editorRoot;

        stage.scene = scene
    }
}