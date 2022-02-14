package net.jet3.booking101.ui.edit

import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.stage.Stage
import net.jet3.booking101.Toast
import net.jet3.booking101.`object`.Property
import net.jet3.booking101.`object`.PropertyType
import net.jet3.booking101.ui.MainUI
import java.util.*

class InsertNewUI(var column: Int, var row: Int) {
    var titleField: TextField? = null
    var typeCombo: ComboBox<String>? = null;
    var description: TextField? = null
    var remindMe: CheckBox? = null

    var year: TextField? = TextField()
    var month: TextField? = TextField()
    var day: TextField? = TextField()
    var hour: TextField? = TextField()

    var remindMeLabel: Label? = null
    var titleLabel: Label? = Label("Add title:")

    var confirmButton:Button = Button("Create")
    var cancelButton:Button = Button("Cancel")

    private var stage: Stage?= null;

    fun InsertNewUI() {
        titleField = TextField()
        titleField?.text = "Title"
        titleField?.translateX = 70.0
        titleField?.translateY = 10.0
        titleField?.maxWidth = 400.0
        titleField?.minWidth = 400.0
        titleLabel?.translateX = 10.0
        titleLabel?.translateY = 10.0

        typeCombo = ComboBox<String>()
        typeCombo?.translateX = 70.0
        typeCombo?.translateY = 60.0
        typeCombo?.maxWidth = 300.0
        typeCombo?.minWidth = 300.0
        typeCombo?.value = "Select type"
        typeCombo?.items?.addAll("Task", "Reminder", "Note")

        description = TextField()
        description?.translateX = 70.0
        description?.translateY = 110.0
        description?.maxWidth = 400.0
        description?.minWidth = 400.0
        description?.text = "Description"

        remindMe = CheckBox()
        remindMe?.translateX = 70.0
        remindMe?.translateY = 140.0
        remindMe?.text = "Remind me when the time arrives"
        remindMe?.isSelected = false

        year?.translateY = 170.0
        month?.translateY = 170.0
        day?.translateY = 170.0
        hour?.translateY = 170.0
        year?.translateX = 70.0
        month?.translateX = 120.0
        day?.translateX = 170.0
        hour?.translateX = 220.0

        confirmButton.translateX = 280.0
        confirmButton.translateY = 300.0
        confirmButton.maxWidth = 100.0
        confirmButton.minWidth = 100.0
        confirmButton.maxHeight = 30.0
        confirmButton.minHeight = 30.0
        confirmButton.styleClass.add("buttons")

        cancelButton.translateX = 400.0
        cancelButton.translateY = 300.0
        cancelButton.maxWidth = 100.0
        cancelButton.minWidth = 100.0
        cancelButton.maxHeight = 30.0
        cancelButton.minHeight = 30.0
        cancelButton.styleClass.add("buttons")

        year?.minWidth = 50.0
        month?.minWidth = 50.0
        day?.minWidth = 50.0
        hour?.minWidth = 50.0
        year?.maxWidth = 50.0
        month?.maxWidth = 50.0
        day?.maxWidth = 50.0
        hour?.maxWidth = 50.0
        remindMeLabel = Label("Remind me")

        confirmButton.setOnMouseClicked {
            val action = Property.getProperty(UUID.randomUUID());
            action.title = titleField?.text
            action.type = typeCombo?.value?.let { it1 -> PropertyType.valueOf(it1.uppercase()) }
            action.description = description?.text
            action.notify = remindMe!!.isSelected
            action.dateToExecute = GregorianCalendar(year?.text!!.toInt(), month?.text!!.toInt(), day?.text!!.toInt(), hour?.text!!.toInt(), 0).timeInMillis
            action.save()

            stage?.close()
            MainUI().update()
            Toast.success("New property '" + action.title + "' created!")
        }

        cancelButton.setOnMouseClicked {
            stage?.close()
        }
    }

    fun start() {
        InsertNewUI()

        val primaryStage = Stage()
        primaryStage.initOwner(MainUI.publicScene.window)

        primaryStage.title = "Insert New"

        val root = Group(titleField, typeCombo, description, remindMe, titleLabel, year, month, day, hour, cancelButton, confirmButton)
        primaryStage.scene = Scene(root, 500.0, 350.0)
        primaryStage.scene.stylesheets.add("jfxstyle/insert.css")
        primaryStage.isResizable = false

        this.stage = primaryStage
        primaryStage.show()
    }
}