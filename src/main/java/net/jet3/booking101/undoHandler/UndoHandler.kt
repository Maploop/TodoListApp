package net.jet3.booking101.undoHandler

sealed class UndoHandler {
    companion object {
        var undoStack: MutableList<Undoable> = mutableListOf()
        var redoStack: MutableList<Undoable> = mutableListOf()

        fun push(undoable: Undoable) {
            undoStack.add(undoable)
        }

        fun undo(): Undoable? {
            if (undoStack.isEmpty()) {
                return null
            }
            val undoable = undoStack.removeAt(undoStack.size - 1)
            undoable.undo()

            redoStack.add(undoable)

            return undoable
        }

        fun clear() {
            undoStack.clear()
        }

        fun isEmpty() = undoStack.isEmpty()

        fun size() = undoStack.size

        fun canUndo() = !undoStack.isEmpty()

        fun canRedo() = !redoStack.isEmpty()

        fun redo(): Undoable? {
            if (redoStack.isEmpty()) {
                return null
            }
            val undoable = redoStack.removeAt(redoStack.size - 1)
            undoable.redo()

            return undoable
        }
    }
}