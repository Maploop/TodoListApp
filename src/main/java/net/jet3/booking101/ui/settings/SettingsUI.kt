package net.jet3.booking101.ui.settings

import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.stage.Stage
import net.jet3.booking101.ManagementYaar

class SettingsUI {
    var devMode = CheckBox()
    var save = Button()
    var cancel = Button()

    private var stage: Stage? = null

    fun SettingsUI(){
        devMode.isSelected = ManagementYaar.DEVELOPER_MODE;
        devMode.text = "Developer Mode"
        devMode.translateX = 50.0

        save.text = "Apply"
        cancel.text = "Cancel"
        save.styleClass.add("button")
        cancel.styleClass.add("button")
        save.translateX = 310.0
        cancel.translateX = 400.0
        save.translateY = 300.0
        cancel.translateY = 300.0
        save.setPrefSize(70.0, 30.0)
        cancel.setPrefSize(70.0, 30.0)

        save.setOnMouseClicked {
            ManagementYaar.DEVELOPER_MODE = devMode.isSelected
            stage?.close()
        }
        cancel.setOnMouseClicked {
            stage?.close()
        }
    }

    fun start() {
        SettingsUI()

        val primaryStage = Stage()

        primaryStage.title = "Settings"

        val root = Group(devMode, save, cancel)
        primaryStage.scene = Scene(root, 500.0, 350.0)
        primaryStage.scene.stylesheets.add("jfxstyle/settings.css")
        primaryStage.isResizable = false
        primaryStage.isAlwaysOnTop = true

        this.stage = primaryStage

        primaryStage.show()
    }
}