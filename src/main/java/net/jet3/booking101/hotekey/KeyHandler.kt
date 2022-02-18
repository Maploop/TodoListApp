package net.jet3.booking101.hotekey

import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import net.jet3.booking101.ManagementYaar
import net.jet3.booking101.Toast
import net.jet3.booking101.ui.MainUI
import net.jet3.booking101.undoHandler.UndoHandler
import net.jet3.booking101.util.FXDialogs

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
        val code = e.code;

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

            val result = FXDialogs.showConfirm("Are you sure you want to delete " + ManagementYaar.selectedProperties.size + " properties?", "", "Yes", "Cancel")

            if (result == "Yes") {
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