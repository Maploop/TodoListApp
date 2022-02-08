package net.jet3.booking101.ui

import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import net.jet3.booking101.ui.edit.InsertNewUI
import net.jet3.booking101.undoHandler.UndoHandler.Companion.push
import net.jet3.booking101.undoHandler.Undoable

class MainTasksPreview {
    private var root: StackPane? = null

    var rows = 30
    var columns = 12
    var width = 1200.0
    var height = 650.0

    var publicGrid: Grid? = null

    companion object {
        private var contextMenu: ContextMenu? = null
        private var insertNew: MenuItem? = null
        private var deleteCurrent: MenuItem? = null
    }

    fun MainTasksPreview() {
        root = StackPane()
        contextMenu = ContextMenu()
        insertNew = MenuItem("Insert new")
        deleteCurrent = MenuItem("Delete current")
        contextMenu!!.items.addAll(insertNew, deleteCurrent)
    }

    fun init(stage: Stage, root: BorderPane, scene: Scene) {
        MainTasksPreview()
        val grid = Grid(columns, rows, width, height)
        publicGrid = grid
        val mg = MouseGestures()
        grid.translateY = 100.0
        grid.translateX = 30.0
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                val cell = Cell(column, row)
                mg.makePaintable(cell)
                grid.add(cell, column, row)
            }
        }
        root.center = grid
        scene.stylesheets.add("jfxstyle/grid.css")
        stage.scene = scene
    }

    class Grid(var columns: Int, var rows: Int, var w: Double, var h: Double) : Pane() {
        var cells: Array<Array<Cell?>>

        fun add(cell: Cell, column: Int, row: Int) {
            cells[row][column] = cell
            val w = w / columns
            val h = h / rows
            val x = w * column
            val y = h * row
            cell.layoutX = x
            cell.layoutY = y
            cell.prefWidth = w
            cell.prefHeight = h
            children.add(cell)
        }

        fun getCell(column: Int, row: Int): Cell? {
            return cells[row][column]
        }

        fun unhighlight() {
            for (row in 0 until rows) {
                for (col in 0 until columns) {
                    cells[row][col]!!.unhighlight()
                }
            }
        }

        init {
            cells = Array(rows) { arrayOfNulls(columns) }
        }
    }

    class Cell(var column: Int, var row: Int) : StackPane() {
        fun highlight() {
            styleClass.remove("cell-highlight")

            styleClass.add("cell-highlight")
            push(Undoable({ unhighlight() }) { highlight() })
        }

        fun unhighlight() {
            styleClass.remove("cell-highlight")
            push(Undoable({ highlight() }) { unhighlight() })
        }

        fun hoverHighlight() {
            // ensure the style is only once in the style list
            styleClass.remove("cell-hover-highlight")

            // add style
            styleClass.add("cell-hover-highlight")
        }

        fun hoverUnhighlight() {
            styleClass.remove("cell-hover-highlight")
        }

        override fun toString(): String {
            return column.toString() + "/" + row
        }

        init {
            styleClass.add("cell")

            opacity = 0.9
        }
    }

    class MouseGestures {
        fun makePaintable(node: Node) {
            var showHoverCursor = true

            if (showHoverCursor) {
                node.hoverProperty().addListener { observable, oldValue, newValue ->
                    if (newValue) {
                        (node as Cell).hoverHighlight()
                    } else {
                        (node as Cell).hoverUnhighlight()
                    }
                }
            }
            node.onMousePressed = onMousePressedEventHandler
            node.onDragDetected = onDragDetectedEventHandler
            node.onMouseDragEntered = onMouseDragEnteredEventHandler
            node.cursor = Cursor.HAND
            node.onMouseClicked = EventHandler { e: MouseEvent ->
                if (e.button == MouseButton.PRIMARY) {
                    insertNew?.isDisable = node.styleClass.contains("hasProperty")
                    deleteCurrent?.isDisable = !node.styleClass.contains("hasProperty")
                    insertNew?.onAction = EventHandler {
                        val insertNewUI = InsertNewUI((node as Cell).column, node.row)
                        insertNewUI.start()
                    }
                    contextMenu?.show(node, e.screenX, e.screenY)
                }
            }
        }

        var onMousePressedEventHandler =
            EventHandler { event: MouseEvent ->
                val cell = event.source as Cell
                if (event.isPrimaryButtonDown) {
                    cell.highlight()
                } else if (event.isSecondaryButtonDown) {
                    cell.unhighlight()
                }
            }
        var onMouseDraggedEventHandler =
            EventHandler { event: MouseEvent ->
                val pickResult = event.pickResult
                val node = pickResult.intersectedNode
                if (node is Cell) {
                    val cell = node
                    if (event.isPrimaryButtonDown) {
                        cell.highlight()
                    } else if (event.isSecondaryButtonDown) {
                        cell.unhighlight()
                    }
                }
            }
        var onMouseReleasedEventHandler =
            EventHandler { event: MouseEvent? -> }
        var onDragDetectedEventHandler =
            EventHandler { event: MouseEvent ->
                val cell = event.source as Cell
                cell.startFullDrag()
            }
        var onMouseDragEnteredEventHandler =
            EventHandler { event: MouseEvent ->
                val cell = event.source as Cell
                if (event.isPrimaryButtonDown) {
                    cell.highlight()
                } else if (event.isSecondaryButtonDown) {
                    cell.unhighlight()
                }
            }
    }
}