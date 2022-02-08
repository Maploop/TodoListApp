package net.jet3.booking101.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class MainControllerMenu
{
    private Image image;
    private ImageView view;
    private ContextMenu cm = new ContextMenu();

    public MainControllerMenu(String imagePath) {
        File file = new File(imagePath);
        if (!file.exists())
            return;

        image = new Image(file.toURI().toString());
        view = new ImageView(image);

        MenuItem cmItem1 = new MenuItem("Copy Image");
        cmItem1.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putImage(view.getImage());
            clipboard.setContent(content);
        });

        cm.getItems().add(cmItem1);
        view.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    if (e.getButton() == MouseButton.SECONDARY)
                        cm.show(view, e.getScreenX(), e.getScreenY());
                });
    }

    public void init(Stage stage, VBox root, Scene scene) {
        stage.setScene(scene);

        if (view != null)
            root.getChildren().add(view);

        stage.show();
    }
}
