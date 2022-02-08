package net.jet3.booking101.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.jet3.booking101.ManagementYaar;
import net.jet3.booking101.hotekey.KeyHandler;

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
    private Label projectLabel;

    public static Stage publicStage;
    public static BorderPane publicRoot;
    public static Scene publicScene;

    public MainUI() {
        bar = new MainMenuBar();
        sidebar = new MainMenuSidebar();
        projectLabel = new Label("Today is the " + ManagementYaar.today);
        preview = new MainTasksPreview();
        projectLabel.getStyleClass().add("project-label");
        projectLabel.setTranslateX(5);
        projectLabel.setTranslateY(50);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(windowLabel);

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, xSize, ySize, backgroundColor);
        bar.init(primaryStage, root, scene);
        preview.init(primaryStage, root, scene);
        primaryStage.getIcons().add(new Image(ManagementYaar.class.getClassLoader().getResourceAsStream("assets/icon2.png")));
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
                ManagementYaar.WIDTH = (int) primaryStage.getWidth();
                ManagementYaar.HEIGHT = (int) primaryStage.getHeight();
                ManagementYaar.MAXIMIZED = primaryStage.isMaximized();

                ManagementYaar.exit();
            } else {
                e.consume();
            }
        });

        primaryStage.setMaximized(ManagementYaar.MAXIMIZED);

        scene.setOnKeyReleased(e -> new KeyHandler().handleKeyRelease(e));
        scene.setOnKeyPressed(e -> new KeyHandler().handleKeyPress(e));
        primaryStage.show();
    }
}
