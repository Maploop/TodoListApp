package net.jet3.booking101;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class ToastController {

    public static final int TOAST_SUCCESS = 11;
    public static final int TOAST_WARN = 12;
    public static final int TOAST_ERROR = 13;

    @FXML
    private HBox containerToast;

    @FXML
    private Label textToast;

    private void setToast(int toastType, String content) {
        textToast.setText(content);
        switch (toastType) {
            case TOAST_SUCCESS:
                containerToast.setStyle("-fx-background-color: #9FFF96");
                break;
            case TOAST_WARN:
                containerToast.setStyle("-fx-background-color: #FFCF82");
                break;
            case TOAST_ERROR:
                containerToast.setStyle("-fx-background-color: #FF777C");
                break;
        }
    }

    public static void showToast(int toastTyoe, Node control, String text) {
        Stage dialog = new Stage();
        dialog.initOwner(control.getScene().getWindow());
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.initStyle(StageStyle.UNDECORATED);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        dialog.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 300);
        dialog.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 950);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ToastController.class.getResource("/fxml/toastPopup.fxml"));
            loader.load();
            ToastController ce = loader.getController();
            ce.setToast(toastTyoe, text);
            dialog.setScene(new Scene(loader.getRoot()));
            dialog.show();
            new Timeline(new KeyFrame(
                    Duration.millis(1500),
                    ae -> {
                        dialog.close();
                    })).play();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}