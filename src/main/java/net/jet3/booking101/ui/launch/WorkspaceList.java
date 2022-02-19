package net.jet3.booking101.ui.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import net.jet3.booking101.data.Workspace;

public class WorkspaceList
{
    private void initalizeComponents() {
        editorRoot = new BorderPane();

        editorScroll = new ScrollPane();
        editorScroll.getStylesheets().add("/jfxstyle/launch.css");
        editorScroll.getStyleClass().add("list-root");
        editorScroll.setPrefSize(600, 400);
        editorScroll.setMaxSize(600, 400);
        editorScroll.setMinSize(600, 400);

        editorGroup = new Group();
        editorGroup.getStylesheets().add("/jfxstyle/launch.css");
        editorGroup.setTranslateY(5);
        editorGroup.setTranslateX(5);

        int y = 0;

        for (Workspace workspace : Workspace.getWorkspaces()) {
            if (workspace.name.equalsIgnoreCase("a new look"))
                continue;

            BorderPane item = new BorderPane();
            item.setPrefWidth(590);
            item.setPrefHeight(70);
            item.getStyleClass().add("list-item");
            item.setOnMouseClicked(event -> {
                workspace.switchTo();
            });
            item.setTranslateX(80);
            item.setTranslateY(y);
            y += 80;

            Label title = new Label(workspace.name);
            title.getStyleClass().add("list-item-title");
            title.setTranslateY(10);
            title.setTextAlignment(TextAlignment.CENTER);
            item.setTop(title);
            Label description = new Label(workspace.getHandler().getPath());
            description.getStyleClass().add("list-item-description");
            description.setTranslateY(0);
            description.setTextAlignment(TextAlignment.CENTER);
            title.setTranslateX(25);
            description.setTranslateX(25);
            item.setBottom(description);

            editorGroup.getChildren().add(item);
        }

        recentWorkspaces = new Label("Recent Workspaces");
        recentWorkspaces.getStyleClass().add("list-title");
        recentWorkspaces.setTranslateX(90);
        recentWorkspaces.setTranslateY(130);
    }

    public void init(Stage primaryStage, Scene scene, BorderPane root) {
        initalizeComponents();

        editorRoot.setTop(recentWorkspaces);
        editorRoot.setCenter(editorScroll);
        editorScroll.setContent(editorGroup);
        root.setCenter(editorRoot);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BorderPane editorRoot;
    private ScrollPane editorScroll;
    private Group editorGroup;
    private Label recentWorkspaces;
}
