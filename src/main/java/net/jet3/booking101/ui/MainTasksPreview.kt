package net.jet3.booking101.ui

import javafx.scene.Cursor
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.input.MouseButton
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.text.TextAlignment
import javafx.stage.Stage
import net.jet3.booking101.ManagementYaar
import net.jet3.booking101.Toast
import net.jet3.booking101.`object`.Property
import net.jet3.booking101.ui.edit.EditUI
import net.jet3.booking101.ui.edit.InsertNewUI
import net.jet3.booking101.ui.edit.NewWorkspaceUI
import net.jet3.booking101.util.FXDialogs
import net.jet3.booking101.util.Log
import net.jet3.booking101.util.Util
import java.text.SimpleDateFormat
import java.util.*

class MainTasksPreview {
    private var root: StackPane? = null

    var rows = 30
    var columns = 12
    var width = 1500.0
    var height = 600.0

    private var contextMenu: ContextMenu? = null
    private var insertNew: MenuItem? = null
    private var deleteCurrent: MenuItem? = null
    private var view: MenuItem? = null

    init {
        root = StackPane()
        contextMenu = ContextMenu()
        view = MenuItem("Mark as done")
        insertNew = MenuItem("Edit")
        deleteCurrent = MenuItem("Delete")
        contextMenu!!.items.addAll(view, insertNew, deleteCurrent)
    }

    fun init(stage: Stage, root: BorderPane, scene: Scene) {
        val rect = javafx.scene.shape.Rectangle(10.0, 10.0, 10.0, 10.0)
        rect.styleClass.add("rect")
        val x = 0.0;
        var y = 0.0;

        val scroll = ScrollPane(rect)
        scroll.stylesheets.add("/jfxstyle/property.css")
        scroll.styleClass.add("column")
        scroll.prefWidth = 300.0
        scroll.maxWidth = 300.0
        scroll.maxHeight = height
        scroll.prefHeight = height
        scroll.translateX = 300.0

        val finished = ScrollPane(rect)
        finished.stylesheets.add("/jfxstyle/property.css")
        finished.styleClass.add("column")
        finished.prefWidth = 300.0
        finished.maxWidth = 300.0
        finished.maxHeight = height
        finished.prefHeight = height
        finished.minHeight = height
        finished.translateX = 650.0


        val group = Group()
        group.translateX = 25.0 /*-700.0*/
        group.translateY = 25.0 /*-370.0*/

        val finishedGroup = Group()
        finishedGroup.translateX = 25.0
        finishedGroup.translateY = 25.0

        if (!ManagementYaar.WORKSPACE.properties.isEmpty()) {
            for (property in ManagementYaar.WORKSPACE.properties) {
                val obj = BorderPane()

                obj.stylesheets.add("/jfxstyle/property.css");

                obj.setOnMouseClicked {
                    if (it.button == MouseButton.PRIMARY) {
                        if (it.clickCount == 2) {
                            EditUI(property).start()
                            return@setOnMouseClicked
                        }
                    }
                    if (it.button != MouseButton.SECONDARY)
                        return@setOnMouseClicked

                    if (property.done)
                        view?.text = "Mark as unfinished"
                    else
                        view?.text = "Mark as finished"

                    view?.setOnAction {
                        property.done = !property.done
                        Util.runAsync {
                            property.save()
                        }
                        if (property.done)
                            Toast.success("Property marked as finished!")
                        else
                            Toast.success("Property marked as unfinished!")
                        MainUI().update()
                    }

                    insertNew?.setOnAction {
                        EditUI(property).start()
                    }

                    deleteCurrent?.setOnAction {
                        val result = FXDialogs.showConfirm("Are you sure you want to delete this property?", "", "Yes", "Cancel")
                        if (result == "Yes") {
                            property.delete()
                            MainUI().update()
                        }
                    }

                    contextMenu!!.show(obj, it.screenX, it.screenY)
                }
                obj.translateX = x;
                obj.translateY = y;
                obj.styleClass.add("parent")
                val label = Label(property.title)
                if (property.dateToExecute != (-1).toLong()) {
                    val date = Date(property.dateToExecute)
                    label.text = "${property.title}\n${SimpleDateFormat("dd/MM/hh").format(date)}"
                }
                label.translateX = -10.0
                label.styleClass.add("title")
                label.textAlignment = TextAlignment.CENTER

                if (property.done) {
                    obj.styleClass.add("done")
                } else {
                    obj.styleClass.add("undone")
                }


                obj.center = label;
                obj.setPrefSize(250.0, 60.0)
                obj.setMaxSize(250.0, 60.0)
                obj.setMinSize(250.0, 60.0)

                y += 80.0

                obj.cursor = Cursor.HAND
                if (property.done)
                    finishedGroup.children.add(obj)
                else
                    group.children.add(obj)
            }
        } else {
            makeNewLabel = Label("You have no properties!\nCreate a new one now!\nClick here to create!")
            makeNewLabel?.setOnMouseClicked {
                InsertNewUI(1, 1).start()
            }
            makeNewLabel?.cursor = Cursor.HAND
            makeNewLabel?.translateX = 350.0
            makeNewLabel?.styleClass?.add("no-properties")

            group.children.addAll(makeNewLabel)
        }

        scroll.content = group
        val groupOfScrollBars = Group()

        val newCmenu = ContextMenu()
        val item1 = Menu("New")
        val item1_2 = MenuItem("Workspace")
        item1_2.setOnAction {
            NewWorkspaceUI().start()
        }
        val item1_3 = MenuItem("Property")
        item1_3.setOnAction {
            InsertNewUI(1, 1).start()
        }
        item1.items.add(item1_2)
        item1.items.add(item1_3)
        val save = MenuItem("Save")
        save.setOnAction {
            Util.runAsync {
                try {
                    ManagementYaar.save()
                } catch (e: Exception) {
                    FXDialogs.showException("An error occurred while saving", "", e)
                }
            }
            Toast.success("Save completed!")
        }

        newCmenu.items.addAll(item1, save)

//        scroll.setOnMouseClicked {
//            if (it.button == MouseButton.SECONDARY) {
//                newCmenu.show(scroll, it.screenX, it.screenY)
//            }
//        }
//
//        finished.setOnMouseClicked {
//            if (it.button == MouseButton.SECONDARY) {
//                newCmenu.show(scroll, it.screenX, it.screenY)
//            }
//        }

        groupOfScrollBars.children.add(scroll)
        finished.content = finishedGroup
        groupOfScrollBars.children.add(finished)
        groupOfScrollBars.translateX = -100.0
        root.center = groupOfScrollBars

        stage.scene = scene
        stage.show()
    }

    private var makeNewLabel: Label? = null
    private var makeNewButton: Button? = null
}