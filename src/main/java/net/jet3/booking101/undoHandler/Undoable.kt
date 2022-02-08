package net.jet3.booking101.undoHandler

class Undoable(var undoAction: Runnable, var redoAction: Runnable) {
    fun undo() {
        undoAction.run()
    }

    fun redo() {
        redoAction.run()
    }

    fun canUndo(): Boolean {
        return true
    }

    fun canRedo(): Boolean {
        return true
    }
}