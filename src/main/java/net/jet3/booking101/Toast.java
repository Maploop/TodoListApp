package net.jet3.booking101;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.jet3.booking101.ui.MainUI;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class Toast {

    public static final int TOAST_SUCCESS = 11;
    public static final int TOAST_WARN = 12;
    public static final int TOAST_ERROR = 13;

    public static int count = 0;

    @FXML
    private HBox containerToast;

    @FXML
    private Label textToast;

    private void setToast(int toastType, String content, Label label) {
        textToast.setText(content);
        switch (toastType) {
            case TOAST_SUCCESS:
                containerToast.setStyle("-fx-border-color: darkgray; -fx-background-color: #9FFF96");
                try {
                    Image image = new Image(ManagementYaar.class.getClassLoader().getResourceAsStream("assets/success.png"));
                    javafx.scene.image.ImageView view = new javafx.scene.image.ImageView(image);
                    view.setFitHeight(43);
                    view.setFitWidth(43);
                    label.setGraphic(view);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case TOAST_WARN:
                containerToast.setStyle("-fx-border-color: darkgray; -fx-background-color: #FFCF82");
                try {
                    Image image = new Image(ManagementYaar.class.getClassLoader().getResourceAsStream("assets/warn.png"));
                    javafx.scene.image.ImageView view = new javafx.scene.image.ImageView(image);
                    view.setFitHeight(43);
                    view.setFitWidth(43);
                    label.setGraphic(view);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case TOAST_ERROR:
                containerToast.setStyle("-fx-border-color: darkgray; -fx-background-color: #FF777C");
                try {
                    Image image = new Image(ManagementYaar.class.getClassLoader().getResourceAsStream("assets/error.png"));
                    javafx.scene.image.ImageView view = new javafx.scene.image.ImageView(image);
                    view.setFitHeight(43);
                    view.setFitWidth(43);
                    label.setGraphic(view);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }

    private static void showToast(int toastTyoe, String title, String text) {
        ImageView view;

        if (true) {
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
            return;
        }
        Node control = MainUI.publicRoot;
        Stage dialog = new Stage();
        dialog.initOwner(control.getScene().getWindow());
//        dialog.setAlwaysOnTop(true);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setResizable(false);
        dialog.initStyle(StageStyle.UNDECORATED);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        dialog.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - ((text.length() > 32 ? text.length() * 4 : text.length() * 2) + 250));
        dialog.setY((primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 950) + (70 * count));

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Toast.class.getResource("/fxml/toastPopup.fxml"));
            loader.load();
            Toast ce = loader.getController();
            ce.setToast(toastTyoe, text, ce.textToast);
            dialog.setScene(new Scene(loader.getRoot()));

            dialog.show();

            count++;
            new Timeline(new KeyFrame(
                    Duration.millis(1500),
                    ae -> {
                        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), dialog.getScene().getRoot());
                        fade.setToValue(0);
                        fade.playFromStart();
                        fade.setOnFinished(actionEvent -> dialog.close());
                        count--;
                    })).play();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
}