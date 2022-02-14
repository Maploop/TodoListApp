package net.jet3.booking101.hotekey

import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import net.jet3.booking101.ManagementYaar
import net.jet3.booking101.Toast
import net.jet3.booking101.ui.MainUI
import net.jet3.booking101.undoHandler.UndoHandler

class KeyHandler() {
    var isControl = false

    fun handleKeyRelease(e: KeyEvent) {
        var code = e.code;

        if (code == KeyCode.CONTROL) {
            isControl = false
            println("Control is false")
        }
    }

    fun handleKeyPress(e: KeyEvent) {
        var code = e.code;

        if (code == KeyCode.CONTROL) {
            isControl = true
            println("Control is true")
        }

        if (code == KeyCode.Z) {
            println("you press Z!!")
            if (isControl) {
                UndoHandler.undo()
                println("Undid last action")
            }
        }
        if (code == KeyCode.Y) {
            println("u press Y!!!!")
            if (isControl) {
                UndoHandler.redo()
                println("Redid last action")
            }
        }

        if (code == KeyCode.DELETE) {
            if (ManagementYaar.selectedProperties.isEmpty()) {
                Toast.error("You do not have any properties selected!")
                return
            }

            val alert = Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(MainUI.publicScene.window);
            alert.title = "Delete Properties"
            alert.headerText = "Are you sure you want to delete " + ManagementYaar.selectedProperties.size + " properties?"
            alert.buttonTypes.set(0, ButtonType.YES);
            alert.buttonTypes.set(1, ButtonType.CANCEL);

            val result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                val list = ManagementYaar.selectedProperties
                for (prop in list) {
                    prop.delete()
                    ManagementYaar.selectedProperties.remove(prop)
                }
                MainUI().update()
            }
        }
    }
}