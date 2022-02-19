package net.jet3.booking101.ui;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.jet3.booking101.ManagementYaar;
import net.jet3.booking101.hotekey.KeyHandler;
import net.jet3.booking101.ui.launch.LaunchUI;
import net.jet3.booking101.util.FXDialogs;
import net.jet3.booking101.util.Log;

public class MainUI extends Application
{
    // Set window size, color, window label
    private final String windowLabel = "Management Yaar";
    private final int xSize = 1200;
    private final int ySize = 650;
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
        primaryStage.initStyle(StageStyle.UNDECORATED);
        calc(primaryStage);
    }

    private void calc(Stage stage) {
        stage.setTitle("Management Yaar");
        stage.setMaximized(false);
        BorderPane root = new BorderPane();
        root.getStylesheets().add("jfxstyle/launch.css");
        root.getStylesheets().add("jfxstyle/main.css");
        root.getStyleClass().add("root");
        Scene scene = new Scene(root, xSize, ySize, backgroundColor);

        if (ManagementYaar.WORKSPACE.name.equalsIgnoreCase("A New Look")) {
            setupLaunchMenu(stage, root, scene);
        } else {
            setupProjectMenu(stage, root, scene);
        }

        stage.getIcons().add(new Image(ManagementYaar.class.getClassLoader().getResourceAsStream("assets/icon99.png")));
        stage.setScene(scene);

        stage.setOnCloseRequest(e -> {
            String response = FXDialogs.showConfirm("Are you sure you want to exit?", "", "Exit", "Cancel");
            if (response.equals("Exit"))
                ManagementYaar.exit(1);
            else
                e.consume();
        });
        scene.setOnKeyReleased(e -> new KeyHandler().handleKeyRelease(e));
        scene.setOnKeyPressed(e -> new KeyHandler().handleKeyPress(e));

        publicRoot = root;
        publicScene = scene;
        publicStage = stage;
    }

    private void setupProjectMenu(Stage primaryStage, BorderPane root, Scene scene) {
        primaryStage.setWidth(xSize);
        primaryStage.setHeight(ySize);
        bar.init(primaryStage, root, scene);
        preview.init(primaryStage, root, scene);
        sidebar.init(primaryStage, root, scene);

        primaryStage.show();
    }

    private void setupLaunchMenu(Stage primaryStage, BorderPane root, Scene scene) {
        new LaunchUI(new String[] {"-dead"}).init(primaryStage, scene, root);

        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    public void update() {
        Log.info("Updating MainUI components...");
        publicRoot.getChildren().clear();
        calc(publicStage);
    }
}
