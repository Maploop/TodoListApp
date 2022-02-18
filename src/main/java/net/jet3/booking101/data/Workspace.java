package net.jet3.booking101.data;

import net.jet3.booking101.ManagementYaar;
import net.jet3.booking101.initalization.ApplicationInitalizer;
import net.jet3.booking101.object.Property;
import net.jet3.booking101.ui.MainUI;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Workspace
{
    public static Map<String, Workspace> WORKSPACE_CACHE = new HashMap<>();
    public static File ROOT_FOLDER = new File(ApplicationInitalizer.installPath + "/workspaces");

    public List<Property> properties;
    public String name;

    private DataHandler handler;

    public Workspace(String name) {
        this.name = name;
        this.properties = new ArrayList<>();
        handler = new DataHandler(new File(ApplicationInitalizer.installPath + "/workspaces"), name, "mwb");

        WORKSPACE_CACHE.put(name, this);

        boolean save = false;
        if (!handler.exists()) {
            handler.make();
            save = true;
        }

        if (save)
            save();
        load();
    }

    public void save() {
        handler.set("name", name);
        handler.set("properties", properties.stream().map((p) -> p.getId().toString()).collect(Collectors.toList()));

        handler.save();
    }

    public void load() {
        this.name = handler.getString("name");
        this.properties = ((List<String>) handler.get("properties")).stream().map((id) -> Property.getProperty(UUID.fromString(id))).collect(Collectors.toList());
    }

    public static Workspace getWorkspace(String name) {
        if (WORKSPACE_CACHE.containsKey(name))
            return WORKSPACE_CACHE.get(name);
        return new Workspace(name);
    }

    public void rename(String newName) {
        handler.rename(newName);
        for (Property p : properties)
            p.workspace = newName;
        name = newName;
    }

    public void switchTo() {
        ManagementYaar.WORKSPACE.save();
        ManagementYaar.WORKSPACE = this;
        if (!ManagementYaar.RECENTS.contains(this.name))
            ManagementYaar.RECENTS.add(this.name);
        new MainUI().update();
    }

    public static Collection<Workspace> getWorkspaces() {
        return WORKSPACE_CACHE.values();
    }

    public static void cache() {
        for (File file : ROOT_FOLDER.listFiles()) {
            if (!file.isDirectory())
                getWorkspace(file.getName().replaceAll(".mwb", ""));
        }
    }
}
