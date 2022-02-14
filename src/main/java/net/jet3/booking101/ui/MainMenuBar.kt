package net.jet3.booking101.ui

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.scene.layout.BorderPane
import javafx.stage.FileChooser
import javafx.stage.Stage
import net.jet3.booking101.ManagementYaar
import net.jet3.booking101.initalization.ApplicationInitalizer
import net.jet3.booking101.ui.dev.ConsoleUI
import net.jet3.booking101.ui.edit.InsertNewUI
import net.jet3.booking101.ui.settings.SettingsUI
import net.jet3.booking101.undoHandler.UndoHandler.Companion.redo
import net.jet3.booking101.undoHandler.UndoHandler.Companion.undo
import net.jet3.booking101.util.Util
import org.json.simple.JSONObject

class MainMenuBar {
    private var file: Menu? = null
    private var edit: Menu? = null
    private var view: Menu? = null
    private var help: Menu? = null
    private var developer: Menu? = null

    fun MainMenuBar() {
        file = Menu("File")
        val New = MenuItem("New")
        New.setOnAction {
            InsertNewUI(1, 1).start();
        }
        file!!.items.add(New);
        val Open = MenuItem((Util.get(ApplicationInitalizer.lang, "file") as JSONObject)["open"].toString())
        Open.addEventHandler(ActionEvent.ACTION) { e: ActionEvent? ->
            val chooser = FileChooser()
            val f = chooser.showOpenDialog(null) ?: return@addEventHandler
            val filename = f.absolutePath
            println(filename)
        }
        file!!.items.add(Open)
        val Save = MenuItem((Util.get(ApplicationInitalizer.lang, "file") as JSONObject)["save"].toString())
        file!!.items.add(Save)
        val SaveAs = MenuItem((Util.get(ApplicationInitalizer.lang, "file") as JSONObject)["saveAs"].toString())
        file!!.items.add(SaveAs)
        val Exit = MenuItem((Util.get(ApplicationInitalizer.lang, "file") as JSONObject)["exit"].toString())
        val settings = MenuItem((Util.get(ApplicationInitalizer.lang, "file") as JSONObject)["settings"].toString())
        file!!.items.add(settings)
        settings.onAction = EventHandler { e: ActionEvent? -> SettingsUI().start() }
        Exit.addEventHandler(
            ActionEvent.ACTION
        ) { event: ActionEvent? -> ManagementYaar.exit() }
        file!!.items.add(Exit)
        edit = Menu("Edit")
        val Cut = MenuItem((Util.get(ApplicationInitalizer.lang, "edit") as JSONObject)["cut"].toString())
        edit!!.items.add(Cut)
        val Copy = MenuItem((Util.get(ApplicationInitalizer.lang, "edit") as JSONObject)["copy"].toString())
        edit!!.items.add(Copy)
        val Paste = MenuItem((Util.get(ApplicationInitalizer.lang, "edit") as JSONObject)["paste"].toString())
        val Undo = MenuItem((Util.get(ApplicationInitalizer.lang, "edit") as JSONObject)["undo"].toString())
        Undo.onAction = EventHandler { e: ActionEvent? -> undo() }
        edit!!.items.add(Undo)
        val Redo = MenuItem((Util.get(ApplicationInitalizer.lang, "edit") as JSONObject)["redo"].toString())
        Redo.onAction = EventHandler { e: ActionEvent? -> redo() }
        edit!!.items.add(Redo)
        edit!!.items.add(Paste)
        view = Menu("View")
        val ZoomIn = MenuItem((Util.get(ApplicationInitalizer.lang, "view") as JSONObject)["zoomIn"].toString())
        view!!.items.add(ZoomIn)
        val ZoomOut = MenuItem((Util.get(ApplicationInitalizer.lang, "view") as JSONObject)["zoomOut"].toString())
        view!!.items.add(ZoomOut)
        help = Menu("Help")
        val About = MenuItem((Util.get(ApplicationInitalizer.lang, "help") as JSONObject)["about"].toString())
        About.onAction = EventHandler { e: ActionEvent? ->
            ManagementYaar.pop(Alert.AlertType.INFORMATION,
                (Util.get(
                    ApplicationInitalizer.lang,
                    "help"
                ) as JSONObject)["aboutTitle"].toString()
                        , (Util.get(
                    ApplicationInitalizer.lang,
                    "help"
                ) as JSONObject)["aboutText"].toString())
        }
        help!!.items.add(About)
        if (ManagementYaar.DEVELOPER_MODE) {
            developer = Menu("Developer")
            val appConsole = MenuItem("Application Console")
            developer!!.items.add(appConsole)
            appConsole.setOnAction {
                ConsoleUI().start()
            }
        }
    }

    fun init(stage: Stage, root: BorderPane, scene: Scene?) {
        MainMenuBar()
        val bar = MenuBar()
        bar.isUseSystemMenuBar = true
        bar.menus.add(file)
        bar.menus.add(edit)
        bar.menus.add(view)
        bar.menus.add(help)
        if (developer != null) bar.menus.add(developer)
        root.top = bar
        stage.scene = scene
        stage.show()
    }
}