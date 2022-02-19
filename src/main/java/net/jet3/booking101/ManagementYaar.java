package net.jet3.booking101;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.jet3.booking101.data.Workspace;
import net.jet3.booking101.initalization.ApplicationInitalizer;
import net.jet3.booking101.ui.MainUI;
import net.jet3.booking101.ui.dev.ApplicationConsole;
import net.jet3.booking101.util.FXDialogs;
import net.jet3.booking101.util.Util;
import net.jet3.booking101.object.Property;
import net.jet3.booking101.util.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManagementYaar
{
    public static ManagementYaar instance;

    public ApplicationConsole console;

    public static Workspace WORKSPACE = null;
    public static int WIDTH;
    public static int HEIGHT;
    public static boolean MAXIMIZED;
    public static boolean DEVELOPER_MODE;
    public static List<String> RECENTS;

    public static TrayIcon trayIcon;

    public static List<Property> selectedProperties = new ArrayList<>();

    public ManagementYaar(String... args) {
        Platform.setImplicitExit(false);
        instance = this;

        Log.info("Loading application files...");
        ApplicationInitalizer.init();

        Log.info("Loading local data...");
        console = new ApplicationConsole();
        Property.cache();
        Workspace.cache();
        makeTrayIcon();

        Application.launch(MainUI.class, args);
    }

    public void makeTrayIcon() {
        SystemTray tray = SystemTray.getSystemTray();
        java.awt.Image image = null;
        try {
            image = ImageIO.read(new URL("https://github.com/Maploop/CentServices/releases/download/icon9/icon9.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
            FXDialogs.showException("An error occurred while creating the tray icon", "", ex);
        }
        ActionListener showLIstener = e -> {
            Platform.runLater(() -> {
                MainUI.publicStage.show();
            });
        };

        PopupMenu popup = new PopupMenu();

        MenuItem showItem = new MenuItem("Show");
        showItem.addActionListener(showLIstener);
        popup.add(showItem);

        MenuItem closeItem = new MenuItem("Close");
        closeItem.addActionListener(e -> {
            Platform.runLater(() -> {
                exit(1);
            });
        });
        popup.add(closeItem);

        assert image != null;
        trayIcon = new TrayIcon(image, "Tray", popup);

        try {
            tray.add(trayIcon);
        } catch (Exception ex) {
            FXDialogs.showException("An error occurred while creating the tray icon", "", ex);
        }
    }

    public static ManagementYaar getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new ManagementYaar(args);
    }

    public static void exit(int c) {
        Log.info("Exiting with code " + c);
        Platform.runLater(() -> {
            MainUI.publicStage.close();
        });
        Log.info("Saving data...");
        try {
            ManagementYaar.save();
        } catch (Exception ex) {
            Log.error("An error occurred while performing a full save!");
            Log.error(ex);
            FXDialogs.showException("An error occurred while saving", "", ex);
        }

        System.exit(c);
    }

    public static void hide() {
        Platform.runLater(() -> {
            MainUI.publicStage.hide();
        });
    }

    public static void save() throws Exception {
        Util.set(ApplicationInitalizer.configFile, "lastProject", WORKSPACE.name);
        Util.set(ApplicationInitalizer.configFile, "width", WIDTH);
        Util.set(ApplicationInitalizer.configFile, "height", HEIGHT);
        Util.set(ApplicationInitalizer.configFile, "maximized", MAXIMIZED);
        Util.set(ApplicationInitalizer.configFile, "developerMode", DEVELOPER_MODE);

        ManagementYaar.WORKSPACE.save();

        for (Property p : Property.getAllActions()) {
            p.save();
        }

        Log.info("Successfully saved all data.");
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
