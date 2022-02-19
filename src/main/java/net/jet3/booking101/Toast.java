package net.jet3.booking101;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.jet3.booking101.ui.MainUI;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;

import javax.swing.*;
import java.io.IOException;

public class Toast {

    public static final int TOAST_SUCCESS = 11;
    public static final int TOAST_WARN = 12;
    public static final int TOAST_ERROR = 13;

    private static void showToast(int toastTyoe, String title, String text) {
        ImageView view;

        switch (toastTyoe) {
            case TOAST_SUCCESS:
                Image image = new Image(ManagementYaar.class.getClassLoader().getResourceAsStream("assets/success.png"));
                view = new ImageView(image);
                view.setFitHeight(43);
                view.setFitWidth(43);
                break;
            case TOAST_WARN:
                Image image1 = new Image(ManagementYaar.class.getClassLoader().getResourceAsStream("assets/warn.png"));
                view = new ImageView(image1);
                view.setFitHeight(43);
                view.setFitWidth(43);
                break;
            case TOAST_ERROR:
                Image image2 = new Image(ManagementYaar.class.getClassLoader().getResourceAsStream("assets/error.png"));
                view = new ImageView(image2);
                view.setFitHeight(43);
                view.setFitWidth(43);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + toastTyoe);
        }

        Notifications.create().graphic(view).hideAfter(Duration.seconds(1.6)).position(Pos.TOP_RIGHT).title(title).text(text).hideCloseButton().show();
    }

    public static void success(String msg) {
        showToast(TOAST_SUCCESS, "Success!", msg);
    }

    public static void warn(String msg) {
        showToast(TOAST_WARN, "Warning!", msg);
    }

    public static void error(String msg) {
        showToast(TOAST_ERROR, "Error!", msg);
    }

    public static void popPane(String msg) {
        // Create a WebView
        WebView webView = new WebView();

        // Wrap it inside a NotificationPane
        NotificationPane notificationPane = new NotificationPane(webView);

        // and put the NotificationPane inside a Tab
        Tab tab1 = new Tab("Tab 1");
        tab1.setContent(notificationPane);

        // and the Tab inside a TabPane. We just have one tab here, but of course
        // you can have more!
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(tab1);
        notificationPane.show();

        notificationPane.setText("Do you want to save your password?");
        notificationPane.getActions().add(new Action((e) -> {
            notificationPane.hide();
        }));
    }
}