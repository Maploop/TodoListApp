package net.jet3.booking101.data;

import javafx.scene.control.Alert;
import net.jet3.booking101.ManagementYaar;
import net.jet3.booking101.initalization.ApplicationInitalizer;
import net.jet3.booking101.object.Property;
import net.jet3.booking101.Toast;
import net.jet3.booking101.util.FXDialogs;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DataHandler
{
    public static List<Integer> ROWS = new ArrayList<>();
    public static List<Integer> COLUMNS = new ArrayList<>();

    private static final File ROOT_FOLDER = new File(ApplicationInitalizer.dataFile + "/actions");

    private File file;
    private String name;
    private JSONObject object;
    private File croot;
    private String custom;

    public DataHandler(String name) {
        File file = new File(ROOT_FOLDER, name + ".json");
        if (file.exists()) {
            try {
                this.object = (JSONObject) new JSONParser().parse(new FileReader(file));
            } catch (Exception ex) {
                ex.printStackTrace();
                FXDialogs.showException("An error occurred", "", ex);
            }
        }
        this.file = file;
        this.name = name;
        this.croot = null;
        this.custom = null;
    }

    public DataHandler(File rootFolder, String name, String custom) {
        File file = new File(rootFolder, name + "." + custom);
        if (file.exists()) {
            try {
                this.object = (JSONObject) new JSONParser().parse(new FileReader(file));
            } catch (Exception ex) {
                ex.printStackTrace();
                FXDialogs.showException("Failed to load one or multiple actions.", "", ex);
            }
        }
        this.file = file;
        this.name = name;
        this.croot = rootFolder;
        this.custom = custom;
    }

    public void make() {
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
            JSONObject object = new JSONObject();
            object.put("name", name);

            this.object = object;
            FileWriter writer = new FileWriter(file);
            writer.write(object.toJSONString());
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            FXDialogs.showException("Failed to create new action.", "", ex);
        }
    }

    public boolean exists() {
        return file.exists();
    }

    public Object get(String key) {
        return object.get(key);
    }

    public String getString(String key) {
        return get(key).toString();
    }

    public JSONObject getJsonObject(String key) {
        return (JSONObject) get(key);
    }

    public long getLong(String key) {
        return Long.parseLong(getString(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(getString(key));
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    public void save() {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(object.toJSONString());
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            FXDialogs.showException("Failed to save action.", "", ex);
        }
    }

    public boolean delete() {
        if (file.delete()) {
            Toast.success("Deleted property '" + name + "'.");
        } else {
            Toast.error("Failed to delete property '" + name + "'.");
        }
        return file.delete();
    }

    public void set(String key, Object value) {
        object.put(key, value);
    }

    public static List<UUID> getAll() {
        if (!ROOT_FOLDER.exists())
            return new ArrayList<>();
        List<UUID> ids = new ArrayList<>();

        for (File f : ROOT_FOLDER.listFiles()) {
            DataHandler handler = new DataHandler(f.getName().replace(".json", ""));
            ids.add(UUID.fromString(handler.getString("id")));
            ROWS.add(handler.getInt("row"));
            COLUMNS.add(handler.getInt("column"));
        }

        return ids;
    }

    public static Property get(int row, int col) {
        return Property.getAllActions().stream().filter((property) -> property.column == col && property.row == row).collect(Collectors.toList()).get(0);
    }

    public void rename(String newName) {
        if (croot == null) {
            File newFile = new File(ROOT_FOLDER, newName + ".json");
            try {
                newFile.createNewFile();
                Files.move(file.toPath(), newFile.toPath());
                file.delete();
            } catch (Exception ex) {
                FXDialogs.showException("An error occurred renaming your workspace.", "", ex);
            }
            this.file = newFile;
            return;
        }
        File newFile = new File(croot, newName + "." + custom);
        try {
            newFile.createNewFile();
            Files.move(file.toPath(), newFile.toPath());
            file.delete();
        } catch (Exception ex) {
            FXDialogs.showException("An error occurred renaming your workspace.", "", ex);
        }
        this.file = newFile;
    }
}
