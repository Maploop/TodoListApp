package net.jet3.booking101.ui;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.jet3.booking101.Main;
import net.jet3.booking101.initalization.ApplicationInitalizer;
import net.jet3.booking101.undoHandler.UndoHandler;
import net.jet3.booking101.util.Util;
import org.json.simple.JSONObject;

import java.io.File;

public class MainMenuBar
{
    private Menu file;
    private Menu edit;
    private Menu view;
    private Menu help;

    public MainMenuBar() {
        file = new Menu("File");
        MenuItem Open = new MenuItem(((JSONObject) Util.get(ApplicationInitalizer.lang, "file")).get("open").toString());
        Open.addEventHandler(ActionEvent.ACTION, e -> {
            FileChooser chooser = new FileChooser();
            File f = chooser.showOpenDialog(null);
            if (f == null)
                return;

            String filename = f.getAbsolutePath();
            System.out.println(filename);

            MainUI.view = new MainControllerMenu(filename);
            MainUI.update();
        });
        file.getItems().add(Open);
        MenuItem Save = new MenuItem(((JSONObject) Util.get(ApplicationInitalizer.lang, "file")).get("save").toString());
        file.getItems().add(Save);
        MenuItem SaveAs = new MenuItem(((JSONObject) Util.get(ApplicationInitalizer.lang, "file")).get("saveAs").toString());
        file.getItems().add(SaveAs);
        MenuItem Exit = new MenuItem(((JSONObject) Util.get(ApplicationInitalizer.lang, "file")).get("exit").toString());
        Exit.addEventHandler(ActionEvent.ACTION, event -> Main.exit());
        file.getItems().add(Exit);

        edit = new Menu("Edit");
        MenuItem Cut = new MenuItem(((JSONObject) Util.get(ApplicationInitalizer.lang, "edit")).get("cut").toString());
        edit.getItems().add(Cut);
        MenuItem Copy = new MenuItem(((JSONObject) Util.get(ApplicationInitalizer.lang, "edit")).get("copy").toString());
        edit.getItems().add(Copy);
        MenuItem Paste = new MenuItem(((JSONObject) Util.get(ApplicationInitalizer.lang, "edit")).get("paste").toString());
        MenuItem Undo = new MenuItem(((JSONObject) Util.get(ApplicationInitalizer.lang, "edit")).get("undo").toString());
        Undo.setOnAction(e -> {
            UndoHandler.Companion.undo();
        });
        edit.getItems().add(Undo);
        MenuItem Redo = new MenuItem(((JSONObject) Util.get(ApplicationInitalizer.lang, "edit")).get("redo").toString());
        Redo.setOnAction(e -> {
            UndoHandler.Companion.redo();
        });
        edit.getItems().add(Redo);
        edit.getItems().add(Paste);

        view = new Menu("View");
        MenuItem ZoomIn = new MenuItem(((JSONObject) Util.get(ApplicationInitalizer.lang, "view")).get("zoomIn").toString());
        view.getItems().add(ZoomIn);
        MenuItem ZoomOut = new MenuItem(((JSONObject) Util.get(ApplicationInitalizer.lang, "view")).get("zoomOut").toString());
        view.getItems().add(ZoomOut);

        help = new Menu("Help");
        MenuItem About = new MenuItem(((JSONObject) Util.get(ApplicationInitalizer.lang, "help")).get("about").toString());
        About.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(((JSONObject) Util.get(ApplicationInitalizer.lang, "help")).get("aboutTitle").toString());
            alert.setHeaderText(((JSONObject) Util.get(ApplicationInitalizer.lang, "help")).get("aboutText").toString());
            alert.showAndWait();
        });
        help.getItems().add(About);
    }

    public void init(Stage stage, VBox root, Scene scene) {
        MenuBar bar = new MenuBar();
        bar.getMenus().add(file);
        bar.getMenus().add(edit);
        bar.getMenus().add(view);
        bar.getMenus().add(help);
        root.getChildren().add(bar);

        stage.setScene(scene);
        stage.show();
    }
}
