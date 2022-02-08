package net.jet3.booking101.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.jet3.booking101.Main;
import net.jet3.booking101.hotekey.KeyHandler;

public class MainUI extends Application
{
    // Set window size, color, window label
    private final String windowLabel = "Management Yaar";
    private final int xSize = Main.WIDTH;
    private final int ySize = Main.HEIGHT;
    private final Color backgroundColor = Color.WHITE;

    private MainMenuBar bar;
    private MainMenuSidebar sidebar;
    private MainTasksPreview preview;
    private Label projectLabel;
    public static MainControllerMenu view;

    public static Stage publicStage;
    public static VBox publicRoot;
    public static Scene publicScene;

    public MainUI() {
        bar = new MainMenuBar();
        view = new MainControllerMenu("NONE");
        sidebar = new MainMenuSidebar();
        projectLabel = new Label("Today is the " + Main.today);
        preview = new MainTasksPreview();
        projectLabel.getStyleClass().add("project-label");
        projectLabel.setTranslateX(0.0);
        projectLabel.setTranslateY(0.0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(windowLabel);

        VBox root = new VBox();
        Scene scene = new Scene(root, xSize, ySize, backgroundColor);
        bar.init(primaryStage, root, scene);
        view.init(primaryStage, root, scene);
        preview.init(primaryStage, root, scene);
        primaryStage.getIcons().add(new Image(Main.class.getClassLoader().getResourceAsStream("assets/icon2.png")));
        primaryStage.setScene(scene);
        root.getChildren().add(projectLabel);
        scene.getStylesheets().add("jfxstyle/main.css");
        sidebar.init(primaryStage, root, scene);

        publicRoot = root;
        publicScene = scene;
        publicStage = primaryStage;

        primaryStage.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Exit");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                Main.WIDTH = (int) primaryStage.getWidth();
                Main.HEIGHT = (int) primaryStage.getHeight();
                Main.MAXIMIZED = primaryStage.isMaximized();

                Main.exit();
            } else {
                e.consume();
            }
        });

        primaryStage.setMaximized(Main.MAXIMIZED);

        scene.setOnKeyReleased(e -> new KeyHandler().handleKeyRelease(e));
        scene.setOnKeyPressed(e -> new KeyHandler().handleKeyPress(e));
        primaryStage.show();
    }

    public static void update() {
        view.init(publicStage, publicRoot, publicScene);
    }
}
