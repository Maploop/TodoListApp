package net.jet3.booking101;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.jet3.booking101.initalization.ApplicationInitalizer;
import net.jet3.booking101.ui.MainUI;
import net.jet3.booking101.ui.dev.ApplicationConsole;
import net.jet3.booking101.util.FXDialogs;
import net.jet3.booking101.util.Util;
import net.jet3.booking101.object.Property;
import net.jet3.booking101.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ManagementYaar
{
    public static ManagementYaar instance;

    public ApplicationConsole console;

    public static String LAST_EDITED_PROJECT = "";
    public static int WIDTH;
    public static int HEIGHT;
    public static boolean MAXIMIZED;
    public static boolean DEVELOPER_MODE;

    public static List<Property> selectedProperties = new ArrayList<>();

    public ManagementYaar(String... args) {
        instance = this;

        Log.info("Loading application files...");
        ApplicationInitalizer.init();

        Log.info("Loading local data...");
        console = new ApplicationConsole();
        Property.cache();

        Application.launch(MainUI.class, args);
    }

    public static ManagementYaar getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new ManagementYaar(args);
    }

    public static void exit() {
        Util.set(ApplicationInitalizer.configFile, "lastEditedProject", LAST_EDITED_PROJECT);
        Util.set(ApplicationInitalizer.configFile, "width", WIDTH);
        Util.set(ApplicationInitalizer.configFile, "height", HEIGHT);
        Util.set(ApplicationInitalizer.configFile, "maximized", MAXIMIZED);
        Util.set(ApplicationInitalizer.configFile, "developerMode", DEVELOPER_MODE);

        System.exit(0);
    }

    public static Optional<ButtonType> pop(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);

        DialogPane pane = alert.getDialogPane();

        ObjectProperty<ButtonType> result = new SimpleObjectProperty<>();
        for (ButtonType t : pane.getButtonTypes()) {
            ButtonType resultValue = t;
            ((Button) pane.lookupButton(t)).setOnAction(e -> {
                result.set(resultValue);
                pane.getScene().getWindow().hide();
            });
        }

        pane.getScene().setRoot(new Label());
        Scene sc = new Scene(pane);

        Stage dialog = new Stage();
        dialog.setScene(sc);
        dialog.setTitle(title);
        try {
            dialog.getIcons().add(new Image(ManagementYaar.class.getClassLoader().getResourceAsStream("assets/icon3.png")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        dialog.showAndWait();
        return null;
    }
}
