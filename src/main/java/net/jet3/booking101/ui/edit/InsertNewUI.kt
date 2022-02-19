package net.jet3.booking101.ui.edit

import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
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
import javax.swing.text.html.ImageView

class InsertNewUI(var column: Int, var row: Int) {
    var titleField: TextField? = null
    var typeCombo: ComboBox<String>? = null;
    var description: TextField? = null
    var remindMe: CheckBox? = null
    var newProperty: Label? = null

    var year: TextField? = TextField()
    var month: TextField? = TextField()
    var day: TextField? = TextField()
    var hour: TextField? = TextField()

    var remindMeLabel: Label? = null
    var titleLabel: Label? = Label("Add title:")

    var confirmButton:Button = Button("Create")
    var cancelButton:Button = Button("Cancel")

    private var id: UUID? = null

    private var stage: Stage?= null;

    init {
        id = UUID.randomUUID()

        newProperty = Label("New property")
        newProperty?.setStyle("-fx-font-size: 20px;")
        newProperty!!.translateX = 70.0
        newProperty!!.translateY = 10.0
        val view = javafx.scene.image.ImageView(Image(ManagementYaar::class.java.classLoader.getResourceAsStream("assets/add_property.png")))
        view.fitWidth = 30.0
        view.fitHeight = 30.0
        newProperty?.graphic = view

        titleField = TextField()
        titleField?.promptText = "Title"
        titleField?.translateX = 70.0
        titleField?.translateY = 50.0
        titleField?.maxWidth = 400.0
        titleField?.minWidth = 400.0
        titleLabel?.translateX = 10.0
        titleLabel?.translateY = 10.0
        titleField?.styleClass?.add("text-field")

        typeCombo = ComboBox<String>()
        typeCombo?.translateX = 70.0
        typeCombo?.translateY = 100.0
        typeCombo?.maxWidth = 300.0
        typeCombo?.minWidth = 300.0
        typeCombo?.value = "Priority"
        typeCombo?.items?.addAll("Low", "Medium", "High")
        typeCombo?.styleClass?.add("combo")

        description = TextField()
        description?.translateX = 70.0
        description?.translateY = 150.0
        description?.maxWidth = 400.0
        description?.minWidth = 400.0
        description?.promptText = "Description"
        description?.styleClass?.add("text-field")

        titleField!!.textProperty().addListener { observable, oldValue, newValue ->
            run {
                titleField!!.styleClass.remove("errored")
            }
        }
        description!!.textProperty().addListener { observable, oldValue, newValue ->
            run {
                description!!.styleClass.remove("errored")
            }
        }

        remindMe = CheckBox()
        remindMe?.translateX = 70.0
        remindMe?.translateY = 190.0
        remindMe?.text = "Has a due date"
        remindMe?.isSelected = false
        remindMe?.styleClass?.add("check-box")

        year?.translateY = 230.0
        month?.translateY = 230.0
        day?.translateY = 230.0
        year?.translateX = 70.0
        month?.translateX = 150.0
        day?.translateX = 230.0

        confirmButton.translateX = 390.0
        confirmButton.translateY = 230.0
        confirmButton.maxWidth = 100.0
        confirmButton.minWidth = 100.0
        confirmButton.maxHeight = 30.0
        confirmButton.minHeight = 30.0
        confirmButton.styleClass.add("buttons")
        confirmButton.setOnMouseClicked { make() }

        cancelButton.translateX = 390.0
        cancelButton.translateY = 300.0
        cancelButton.maxWidth = 100.0
        cancelButton.minWidth = 100.0
        cancelButton.maxHeight = 30.0
        cancelButton.minHeight = 30.0
        cancelButton.styleClass.add("buttons")

        year?.minWidth = 70.0
        month?.minWidth = 70.0
        day?.minWidth = 70.0
        hour?.minWidth = 70.0
        year?.maxWidth = 70.0
        month?.maxWidth = 70.0
        day?.maxWidth = 70.0
        hour?.maxWidth = 70.0
        year?.isVisible = false
        month?.isVisible = false
        day?.isVisible = false
        hour?.isVisible = false
        year?.promptText = "Year"
        month?.promptText = "Month"
        day?.promptText = "Day"
        year?.styleClass?.add("text-field")
        month?.styleClass?.add("text-field")
        day?.styleClass?.add("text-field")
        remindMeLabel = Label("Has a due date")

        remindMe?.setOnAction {
            if (remindMe?.isSelected == true) {
                year?.isVisible = true
                month?.isVisible = true
                day?.isVisible = true
            } else {
                year?.isVisible = false
                month?.isVisible = false
                day?.isVisible = false
            }
        }
    }

    fun start() {
        val primaryStage = Stage()
        primaryStage.initOwner(MainUI.publicScene.window)
        primaryStage.initStyle(StageStyle.UNDECORATED)

        primaryStage.title = "Insert New"

        val root = Group(newProperty, titleField, typeCombo, description, remindMe, year, month, day, hour,  confirmButton)
        val sc = Scene(root, 500.0, 280.0)

        sc.setOnKeyPressed {
            if (it.code == KeyCode.ENTER) {
                make()
            }
            if (it.code == KeyCode.ESCAPE) {
                primaryStage.close()
            }
        }

        primaryStage.scene = sc
        primaryStage.scene.stylesheets.add("jfxstyle/insert.css")
        primaryStage.isResizable = false

        this.stage = primaryStage
        primaryStage.show()
    }

    fun make() {
        val action = Property.New(this.id);
        action.title = titleField?.text
        action.type = PropertyType.TASK
        action.description = description?.text
        action.notify = remindMe!!.isSelected
        try {
            action.priority = typeCombo?.value?.let { it1 -> Priority.valueOf(it1.uppercase()) }
        } catch (ex: Exception) {
            Toast.warn("An unexpected error has occurred while\nperforming the requested action:\n" + ex.message)
            typeCombo?.styleClass?.add("errored")
            return
        }

        if (titleField!!.text == "" || description!!.text == "") {
            Toast.warn("Please make sure to fill in the highlighted fields!")
            titleField!!.styleClass.add("errored")
            description!!.styleClass.add("errored")
            return
        }

        if (remindMe?.isSelected == true) {
            try {
                val year = year?.text!!.toInt()
                val month = month?.text!!.toInt()
                val day = day?.text!!.toInt()
                action.dateToExecute =
                    LocalDateTime.of(year, month, day, 0, 0).toInstant(ZoneOffset.UTC).toEpochMilli()
            } catch (ex: Exception) {
                Toast.warn("An unexpected error has occurred while\nperforming the requested action:\n" + ex.message)
                day?.styleClass?.add("errored")
                month?.styleClass?.add("errored")
                year?.styleClass?.add("errored")
                return
            }
        }

        action.create()
        ApplicationInitalizer.updateWorkspace()

        stage?.close()
        MainUI().update()
        Toast.success("New property '" + action.title + "' created!")
    }
}