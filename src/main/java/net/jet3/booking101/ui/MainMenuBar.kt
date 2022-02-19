package net.jet3.booking101.ui

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.scene.image.Image
import javafx.scene.layout.BorderPane
import javafx.stage.FileChooser
import javafx.stage.Stage
import net.jet3.booking101.ManagementYaar
import net.jet3.booking101.data.Workspace
import net.jet3.booking101.initalization.ApplicationInitalizer
import net.jet3.booking101.ui.edit.InsertNewUI
import net.jet3.booking101.ui.edit.NewWorkspaceUI
import net.jet3.booking101.ui.settings.SettingsUI
import net.jet3.booking101.undoHandler.UndoHandler.Companion.redo
import net.jet3.booking101.undoHandler.UndoHandler.Companion.undo
import net.jet3.booking101.util.FXDialogs
import net.jet3.booking101.util.Util
import org.json.simple.JSONObject
import java.awt.TrayIcon
import java.io.File

class MainMenuBar {
    val ico = Menu()
    private var file: Menu? = null
    private var edit: Menu? = null
    private var view: Menu? = null
    private var help: Menu? = null
    private var developer: Menu? = null

    fun MainMenuBar() {
        val img = Image(ManagementYaar::class.java.getResourceAsStream("/assets/icon99.png"))
        val iview = javafx.scene.image.ImageView(img)
        iview.fitHeight = 20.0
        iview.fitWidth = 20.0
        ico.graphic = iview
        ico.styleClass.add("no")
        file = Menu("File")
        val New = Menu("New")

        val property = MenuItem("Property")
        val workspace = MenuItem("Workspace")

        New.items.addAll(workspace, property)

        property.setOnAction {
            InsertNewUI(1, 1).start()
        }
        workspace.setOnAction {
            NewWorkspaceUI().start()
        }
        file!!.items.add(New);
        val Open = MenuItem((Util.get(ApplicationInitalizer.lang, "file") as JSONObject)["open"].toString())
        Open.addEventHandler(ActionEvent.ACTION) { e: ActionEvent? ->
            val chooser = FileChooser()
            chooser.initialDirectory = File(ApplicationInitalizer.installPath, "workspaces")
            val f = chooser.showOpenDialog(null) ?: return@addEventHandler
            val key = f.name.replace(".mwb", "")
            val workspace = Workspace.getWorkspace(key)
            workspace.switchTo()
        }
        val openRecent = Menu("Open Recent")
        for (str in ManagementYaar.RECENTS) {
            val menuItem = MenuItem(str)
            menuItem.setOnAction {
                val workspace = Workspace.getWorkspace(str)
                workspace.switchTo()
            }
            openRecent.items.add(menuItem)
        }
        file!!.items.add(Open)
        file!!.items.add(openRecent)
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
        ) {
            run {
                ManagementYaar.WORKSPACE = Workspace.getWorkspace("A New Look")
                MainUI().update()
            }
        }
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
            FXDialogs.showInformation((Util.get(
                ApplicationInitalizer.lang,
                "help"
            ) as JSONObject)["aboutText"].toString(), "")
        }
        help!!.items.add(About)
        if (ManagementYaar.DEVELOPER_MODE) {
            developer = Menu("Developer")
            val appConsole = MenuItem("Application Console")
            developer!!.items.add(appConsole)
            appConsole.setOnAction {
                ManagementYaar.getInstance().console.isVisible = true
            }
        }
    }

    fun init(stage: Stage, root: BorderPane, scene: Scene?) {
        MainMenuBar()
        root.stylesheets.add("/jfxstyle/bar.css")
        val g = BorderPane()
        val bar = MenuBar()
        bar.styleClass.add("bar")
        ico.styleClass.add("no")
        val g1 = BorderPane()
        val leftBar = Button("X")
        leftBar.setPrefSize(60.0, 30.0)
        leftBar.setMinSize(60.0, 30.0)
        leftBar.setMaxSize(60.0, 30.0)
        leftBar.translateX = -68.0
        leftBar.styleClass.add("close")
        leftBar.setOnMouseClicked {
            ManagementYaar.exit(1)
        }

        val hide = Button("―")
        hide.styleClass.add("hide")
        hide.setPrefSize(60.0, 30.0)
        hide.setMinSize(60.0, 30.0)
        hide.setMaxSize(60.0, 30.0)
        hide.translateX = -68.0
        hide.setOnMouseClicked {
            ManagementYaar.trayIcon.displayMessage("Management Yaar", "Management Yaar is now hidden in your tray bar!", TrayIcon.MessageType.INFO)
            ManagementYaar.hide()
        }
        g1.right = leftBar
        g1.left = hide
        g1.translateX = -50.0

        g.left = bar
        g.right = g1
        bar.isUseSystemMenuBar = true
        bar.menus.add(ico)
        bar.menus.add(file)
        bar.menus.add(edit)
        bar.menus.add(view)
        bar.menus.add(help)
        bar.setMinSize(1200.0, 30.0)
        bar.setPrefSize(1200.0, 30.0)
        bar.setMaxSize(1200.0, 30.0)
        if (developer != null) bar.menus.add(developer)
        root.top = g
        stage.scene = scene
        stage.show()
    }
}