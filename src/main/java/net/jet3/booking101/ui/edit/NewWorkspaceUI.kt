package net.jet3.booking101.ui.edit

import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import javafx.stage.StageStyle
import net.jet3.booking101.ManagementYaar
import net.jet3.booking101.Toast
import net.jet3.booking101.`object`.Priority
import net.jet3.booking101.`object`.Property
import net.jet3.booking101.`object`.PropertyType
import net.jet3.booking101.initalization.ApplicationInitalizer
import net.jet3.booking101.ui.MainUI
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class NewWorkspaceUI {
    var titleField: TextField? = null
    var label: Label? = null
    var l1: Label? = null

    private var id: UUID? = null
    private var stage: Stage?= null;

    init {
        id = UUID.randomUUID()
        l1 = Label("New Workspace")
        l1!!.style = "-fx-font-size: 12px; -fx-text-fill: gray;"
        l1!!.translateX = 13.0
        l1!!.translateY = 33.0
        titleField = TextField()
        titleField?.translateX = 10.0
        titleField?.translateY = 35.0
        titleField?.maxWidth = 200.0
        titleField?.minWidth = 200.0
        titleField?.styleClass?.add("text-field")
        titleField!!.promptText = "Name"
        titleField?.requestFocus()

        val icon = Image("/assets/workspace.png")
        val view = ImageView(icon)
        view.fitWidth = 25.0
        view.fitHeight = 25.0

        label = Label("New Workspace")
        label!!.graphic = view
        label!!.styleClass.add("big-label")
        label!!.translateX = 10.0
    }

    fun start() {
        val primaryStage = Stage()
        primaryStage.initOwner(MainUI.publicScene.window)
        primaryStage.initStyle(StageStyle.UNDECORATED)

        primaryStage.title = "Insert New"

        val root = Group(label, titleField)
        val sc = Scene(root, 220.0, 100.0)
        primaryStage.scene = sc
        sc.setOnKeyPressed {
            if (it.code == KeyCode.ENTER) {
                primaryStage.close()
                Toast.success("pog")
            }
            if (it.code == KeyCode.ESCAPE) {
                primaryStage.close()
            }
        }
        primaryStage.scene.stylesheets.add("jfxstyle/insert.css")
        primaryStage.isResizable = false

        this.stage = primaryStage
        primaryStage.show()
    }
}