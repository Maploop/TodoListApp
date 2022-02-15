package net.jet3.booking101.ui.edit

import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import javafx.stage.StageStyle
import net.jet3.booking101.ManagementYaar
import net.jet3.booking101.Toast
import net.jet3.booking101.`object`.Priority
import net.jet3.booking101.`object`.Property
import net.jet3.booking101.ui.MainUI
import net.jet3.booking101.util.Util


class EditUI(var property: Property) {
    private var parent: BorderPane? = null

    fun start() {
        val primaryStage = Stage()
        primaryStage.initOwner(MainUI.publicScene.window)
        primaryStage.title = "Edit"
        primaryStage.initStyle(StageStyle.DECORATED)

        val parent = BorderPane()
        val scene = Scene(parent, 500.0, 200.0)
        parent.stylesheets.add("/jfxstyle/edit.css")

        this.parent = parent

        InitializeComponents()

        primaryStage.scene = scene

        primaryStage.icons.add(Image(ManagementYaar::class.java.classLoader.getResourceAsStream("assets/icon2.png")))
        primaryStage.isResizable = false
        primaryStage.show()
    }

    fun InitializeComponents() {
        val editorRoot = BorderPane()
        val group = Group()
        group.styleClass.add("editor-root")

        title = TextField(property.title)
        description = TextField(property.description)
        titleLabel = Label("Title")
        descriptionLabel = Label("Description")
        doneButton = Button("Done")
        cancelButton = Button("Cancel")
        priorityBox = ComboBox()
        id = Label("ID: ${property.id}")

        id?.translateX = 50.0
        id?.translateY = 10.0

        title?.translateX = 120.0
        title?.translateY = 40.0
        title?.prefWidth = 400.0
        titleLabel?.translateX = 40.0
        titleLabel?.translateY = 40.0

        description?.translateX = 120.0
        description?.translateY = 80.0
        description?.prefWidth = 400.0
        descriptionLabel?.translateX = 30.0
        descriptionLabel?.translateY = 80.0

        doneButton?.translateX = 300.0
        cancelButton?.translateX = 420.0
        doneButton?.translateY = 160.0
        cancelButton?.translateY = 160.0
        doneButton?.prefWidth = 70.0
        doneButton?.prefHeight = 40.0
        cancelButton?.prefWidth = 70.0
        cancelButton?.prefHeight = 40.0

        priorityBox?.translateX = 120.0
        priorityBox?.translateY = 120.0
        priorityBox?.items?.addAll(Priority.LOW.name, Priority.MEDIUM.name, Priority.HIGH.name)
        priorityBox?.value = property.priority.name
        priorityBox?.styleClass?.add("combo")

        title?.styleClass?.add("text-field")
        description?.styleClass?.add("text-field")
        doneButton?.styleClass?.add("buttons")
        cancelButton?.styleClass?.add("buttons")
        id?.styleClass?.add("label")

        doneButton?.setOnAction {
            if (title?.text == "" || description?.text == "") {
                title?.styleClass?.add("errored")
                description?.styleClass?.add("errored")

                Toast.warn("Please don't leave the highlighted fields empty!")
                return@setOnAction
            }

            property.title = title?.text
            property.description = description?.text
            property.priority = priorityBox?.value?.let { it1 -> Priority.valueOf(it1) }
            Util.runAsync {
                property.save()
            }
            MainUI().update()
            parent!!.scene.window.hide()
        }

        cancelButton?.setOnAction {
            parent!!.scene.window.hide()
        }



        group.children.addAll(id, title, description, titleLabel, descriptionLabel, doneButton, cancelButton, priorityBox)
        editorRoot.top = group
        parent?.top = editorRoot
    }

    var title: TextField? = null
    var description: TextField? = null
    var titleLabel: Label? = null
    var descriptionLabel: Label? = null
    var doneButton: Button? = null
    var cancelButton: Button? = null
    var priorityBox: ComboBox<String>? = null
    var id: javafx.scene.control.Label? = null
}