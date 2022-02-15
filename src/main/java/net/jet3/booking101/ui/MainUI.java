package net.jet3.booking101.ui;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.jet3.booking101.ManagementYaar;
import net.jet3.booking101.hotekey.KeyHandler;
import net.jet3.booking101.util.FXDialogs;

public class MainUI extends Application
{
    // Set window size, color, window label
    private final String windowLabel = "Management Yaar";
    private final int xSize = ManagementYaar.WIDTH;
    private final int ySize = ManagementYaar.HEIGHT;
    private final Color backgroundColor = Color.WHITE;

    private MainMenuBar bar;
    private MainMenuSidebar sidebar;
    private MainTasksPreview preview;

    private ScrollBar scrollLeftRight;

    public static Stage publicStage;
    public static BorderPane publicRoot;
    public static Scene publicScene;

    public MainUI() {
        bar = new MainMenuBar();
        sidebar = new MainMenuSidebar();
        preview = new MainTasksPreview();
        scrollLeftRight = new ScrollBar();
        scrollLeftRight.setOrientation(Orientation.HORIZONTAL);
        scrollLeftRight.setPrefHeight(20);
        scrollLeftRight.setTranslateX(5);
        scrollLeftRight.setTranslateY(70);
        scrollLeftRight.setMin(0);
        scrollLeftRight.setMax(100);
        scrollLeftRight.setValue(0);
        scrollLeftRight.setVisible(false);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(windowLabel);

        BorderPane root = new BorderPane();
        root.getStylesheets().add("jfxstyle/main.css");
        root.getStyleClass().add("root");
        Scene scene = new Scene(root, xSize, ySize, backgroundColor);
        bar.init(primaryStage, root, scene);
        preview.init(primaryStage, root, scene);
        primaryStage.getIcons().add(new Image(ManagementYaar.class.getClassLoader().getResourceAsStream("assets/icon2.png")));
        primaryStage.setScene(scene);
        scene.getStylesheets().add("jfxstyle/main.css");
        sidebar.init(primaryStage, root, scene);

        publicRoot = root;
        publicScene = scene;
        publicStage = primaryStage;

        primaryStage.setOnCloseRequest(e -> {
            String response = FXDialogs.showConfirm("Are you sure you want to exit?", "", "Exit", "Cancel");
            if (response.equals("Exit"))
                ManagementYaar.exit();
            else
                e.consume();
        });

        primaryStage.setMaximized(true);

        scene.setOnKeyReleased(e -> new KeyHandler().handleKeyRelease(e));
        scene.setOnKeyPressed(e -> new KeyHandler().handleKeyPress(e));
        primaryStage.show();
    }

    public void update() {
        publicRoot.getChildren().clear();

        bar.init(publicStage, publicRoot, publicScene);
        sidebar.init(publicStage, publicRoot, publicScene);
        preview.init(publicStage, publicRoot, publicScene);
        publicStage.show();
    }
}
