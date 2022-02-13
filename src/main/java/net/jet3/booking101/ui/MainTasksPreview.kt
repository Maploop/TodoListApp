package net.jet3.booking101.ui

import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.input.MouseButton
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import net.jet3.booking101.ToastController
import net.jet3.booking101.`object`.Property
import net.jet3.booking101.ui.edit.EditUI
import net.jet3.booking101.util.Log
import net.jet3.booking101.util.Util

class MainTasksPreview {
    private var root: StackPane? = null

    var rows = 30
    var columns = 12
    var width = 1500.0
    var height = 900.0

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
        var x = 0.0;
        var y = 0.0;
        Log.info(Property.getAllActions());

        val group = Group()
        group.translateX = 0.0 /*-700.0*/
        group.translateY = -370.0

        for (property in Property.getAllActions()) {
            val obj = BorderPane()

            obj.stylesheets.add("/jfxstyle/property.css");
            obj.setOnMouseClicked {
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
                    ToastController.showToast(ToastController.TOAST_SUCCESS, root, "Property successfully updated!")

                    MainUI().update()
                }

                insertNew?.setOnAction {
                    EditUI(property).start()
                }

                deleteCurrent?.setOnAction {
                    val alert = Alert(Alert.AlertType.CONFIRMATION);
                    alert.title = "Delete Property"
                    alert.headerText = "Are you sure you want to delete this property?"
                    alert.buttonTypes.set(0, ButtonType.YES);
                    alert.buttonTypes.set(1, ButtonType.CANCEL);

                    val result = alert.showAndWait();
                    if (result.get() == ButtonType.YES) {
                        property.delete()
                        MainUI().update()
                        Log.info("Deleted property: " + property.title)
                    }
                }

                contextMenu!!.show(obj, it.screenX, it.screenY)
            }
            obj.translateX = x;
            obj.translateY = y;
            obj.styleClass.add("parent")
            if (property.done) {
                obj.styleClass.add("done")
            } else {
                obj.styleClass.add("undone")
            }

            val label = Label(property.title)
            label.translateX = -10.0
            label.styleClass.add("title")
            obj.center = label;
            obj.setPrefSize(200.0, 60.0)
            obj.setMaxSize(200.0, 60.0)
            obj.setMinSize(200.0, 60.0)

            x += 250.0
            if (x > width) {
                x = -370.0
                y += 110.0
            }

            group.children.add(obj)
        }

        root.center = group;

        stage.scene = scene
        stage.show()
    }
}