package net.jet3.booking101.ui.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.jet3.booking101.ManagementYaar;
import net.jet3.booking101.ui.edit.NewWorkspaceUI;

public class LaunchUI
{
    private String[] args;

    public LaunchUI(String[] args) {
        this.args = args;
    }

    public void initalizeComponents() {
        // Roots
        editorRoot = new BorderPane();
        leftRoot = new BorderPane();
        rightRoot = new BorderPane();
        center = new Group();
        editorRoot.getStylesheets().add("/jfxstyle/launch.css");
        leftRoot.getStylesheets().add("/jfxstyle/launch.css");
        rightRoot.getStylesheets().add("/jfxstyle/launch.css");
        center.getStylesheets().add("/jfxstyle/launch.css");
        editorRoot.getStyleClass().add("root");
        center.getStyleClass().add("side-left");
        leftRoot.getStyleClass().add("side-left");
        rightRoot.getStyleClass().add("side-right");

        // Roots edit
        leftRoot.setMinHeight(700);
        leftRoot.maxHeight(700);
        leftRoot.setPrefHeight(700);
        leftRoot.setMaxWidth(200);
        leftRoot.setPrefWidth(200);
        leftRoot.setMinWidth(200);

        // close
        close = new Button("X");
        close.getStyleClass().add("close");
        close.setOnAction(e -> ManagementYaar.exit(1));
        close.setPrefSize(40, 40);
        close.setMinSize(40, 40);
        close.setMaxSize(40, 40);

        // Logo
        logoLabel = new Label("ManagementYaar");
        ImageView view = new ImageView("/assets/icon9.png");
        view.setFitHeight(70);
        view.setFitWidth(70);
        logoLabel.setGraphic(view);
        logoLabel.getStyleClass().add("normal-label");

        // center
        center.setTranslateY(-260);

        // newWorkspace
        newWorkspace = new Label("New Workspace");
        newWorkspace.getStyleClass().add("clickable-label");
        newWorkspace.setOnMouseClicked(event -> {
            if (NewWorkspaceUI.Companion.getOpen())
                return;
            new NewWorkspaceUI().start();
        });
    }

    public void init(Stage primaryStage, Scene scene, BorderPane root) {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        initalizeComponents();
        primaryStage.setWidth(1000);
        primaryStage.setMaxWidth(1000);
        root.setTop(editorRoot);

        center.getChildren().add(newWorkspace);

        leftRoot.setTop(logoLabel);
        leftRoot.setCenter(center);
        rightRoot.setTop(close);

        editorRoot.setLeft(leftRoot);
        editorRoot.setRight(rightRoot);

        primaryStage.setScene(scene);
    }

    private BorderPane editorRoot;
    private BorderPane leftRoot;
    private BorderPane rightRoot;

    private Group center;

    private Label logoLabel;
    private Label newWorkspace;
    private Button close;
    private Label openWorkspace;
}
