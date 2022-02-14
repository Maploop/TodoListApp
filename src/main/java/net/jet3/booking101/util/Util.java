package net.jet3.booking101.util;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import net.jet3.booking101.ManagementYaar;
import net.jet3.booking101.timeHandler.DateAndTime;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class Util
{
    public static File getAppHome() {
        File appdata = new File(System.getProperty("user.home") + "/Appdata/Roaming/Jet3/Management Yaar");
        if (!appdata.exists()) {
            appdata.mkdirs();
        }
        return appdata;
    }

    public static Object get(File file, String key) {
        try {
            FileReader reader = new FileReader(file);
            JSONObject object = (JSONObject) new JSONParser().parse(reader);
            return object.get(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getString(File file, String key) {
        return get(file, key).toString();
    }

    public static int getInt(File file, String key) {
        return Integer.parseInt(getString(file, key));
    }

    public static boolean getBoolean(File file, String key) {
        return Boolean.parseBoolean(getString(file, key));
    }

    public static void set(File file, String key, Object value) {
        try {
            FileReader reader = new FileReader(file);
            JSONObject object = (JSONObject) new JSONParser().parse(reader);

            object.put(key, value);

            FileWriter writer = new FileWriter(file);
            writer.write(object.toJSONString());
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static public String exportResource(String resource, File file) throws IOException {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        try {
            stream = ManagementYaar.class.getResourceAsStream(resource);
            if(stream == null) {
                throw new IllegalStateException("Failed to get " + resource + "!");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            resStreamOut = new FileOutputStream(file.toString());
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                stream.close();
                resStreamOut.close();
            } catch (Exception ignored) { }
        }

        return file.toString();
    }

    public static String prettify(String old) {
        if (old.endsWith("1"))
            return old + "st";
        else if (old.endsWith("2"))
            return old + "nd";
        else if (old.endsWith("3"))
            return old + "rd";
        else
            return old + "th";
    }

    public static DateAndTime getMonth(int month) {
        for (DateAndTime time : DateAndTime.values()) {
            if (time.getIndex() == month) {
                return time;
            }
        }
        return null;
    }

    public static void runAsync(Runnable run) {
        Thread thread = new Thread(run);
        thread.start();
    }

    public static void delay(Runnable run, long duration) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                run.run();
            }
        }, duration);
    }

    private Text createText() {
        final Text text = new Text("A");
        text.setBoundsType(TextBoundsType.VISUAL);

        return text;
    }

    public static void makeDraggable(Node node) {
        final Delta dragDelta = new Delta();

        node.setOnMouseEntered(me -> {
            if (!me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.HAND);
            }
        });
        node.setOnMouseExited(me -> {
            if (!me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        node.setOnMousePressed(me -> {
            if (me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.DEFAULT);
            }
            dragDelta.x = me.getX();
            dragDelta.y = me.getY();
            node.getScene().setCursor(Cursor.MOVE);
        });
        node.setOnMouseReleased(me -> {
            if (!me.isPrimaryButtonDown()) {
                node.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        node.setOnMouseDragged(me -> {
            node.setLayoutX(node.getLayoutX() + me.getX() - dragDelta.x);
            node.setLayoutY(node.getLayoutY() + me.getY() - dragDelta.y);
        });
    }

    // stackoverflow code lol
    static class Delta { double x, y; }
    private void enableDrag(final Circle circle) {
        final Delta dragDelta = new Delta();
        circle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                dragDelta.x = circle.getCenterX() - mouseEvent.getX();
                dragDelta.y = circle.getCenterY() - mouseEvent.getY();
                circle.getScene().setCursor(Cursor.MOVE);
            }
        });
        circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                circle.getScene().setCursor(Cursor.HAND);
            }
        });
        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                circle.setTranslateX(mouseEvent.getX() + dragDelta.x);
                circle.setTranslateX(mouseEvent.getY() + dragDelta.y);
            }
        });
        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    circle.getScene().setCursor(Cursor.HAND);
                }
            }
        });
        circle.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    circle.getScene().setCursor(Cursor.DEFAULT);
                }
            }
        });
    }
}
