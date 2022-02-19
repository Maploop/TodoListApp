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
    var total = Label()
    var done = Label()
    var remaining = Label()


    init {
        projectLabel = Label(ManagementYaar.WORKSPACE.name)
        projectLabel.styleClass.add("label-project")
        option1?.styleClass?.add("sidebar-btn")

        total.text = "Total: " + ManagementYaar.WORKSPACE.total
        done.text = "Done: " + ManagementYaar.WORKSPACE.done
        remaining.text = "Remaining: " +( ManagementYaar.WORKSPACE.total - ManagementYaar.WORKSPACE.done)
        total.styleClass.add("total")
        done.styleClass.add("done")
        remaining.styleClass.add("remaining")
    }

    fun init(stage: Stage, root: BorderPane, scene: Scene) {
        scene.stylesheets.add("/jfxstyle/sidebar.css")

        editorRoot!!.children.add(projectLabel)
        root.left = editorRoot;

        stage.scene = scene
    }
}