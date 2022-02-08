package net.jet3.booking101.hotekey

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
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
    }
}