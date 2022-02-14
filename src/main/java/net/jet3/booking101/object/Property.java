package net.jet3.booking101.object;

import lombok.Getter;
import lombok.Setter;
import net.jet3.booking101.ManagementYaar;
import net.jet3.booking101.data.DataHandler;
import net.jet3.booking101.util.Log;

import java.util.*;

public class Property
{
    public static final Map<UUID, Property> PROPERTY_CACHE = new HashMap<>();

    @Getter
    public int row;
    @Getter
    public int column;
    @Getter
    public UUID id;
    @Getter
    @Setter
    public String title;
    @Getter
    @Setter
    public PropertyType type;
    @Getter
    @Setter
    public String description;
    @Getter
    @Setter
    public long dateToExecute;
    @Getter
    @Setter
    public boolean notify;
    @Getter
    @Setter
    public boolean done;
    @Getter
    @Setter
    public Priority priority;

    private DataHandler handler;

    private Property(UUID id) {
        this.id = id;
        this.row = 0;
        this.column = 0;
        this.title = "No title";
        this.type = PropertyType.TASK;
        this.description = "No description";
        this.dateToExecute = System.currentTimeMillis();
        this.notify = false;
        this.handler = new DataHandler(id.toString());
        this.done = false;
        this.priority = Priority.LOW;

        boolean save = false;
        if (!handler.exists()) {
            save = true;
            handler.make();
            Log.info("Creating new action with id " + id);
        }

        if (save)
            save();
        load();
    }

    public void save() {
        handler.set("id", id.toString());
        handler.set("row", row);
        handler.set("column", column);
        handler.set("title", title);
        handler.set("type", type.name());
        handler.set("description", description);
        handler.set("dateToExecute", dateToExecute);
        handler.set("notify", notify);
        handler.set("done", done);
        handler.set("priority", priority.name());

        handler.save();
    }

    public void load() {
        this.id = UUID.fromString(handler.getString("id"));
        this.row = handler.getInt("row");
        this.column = handler.getInt("column");
        this.title = handler.getString("title");
        this.type = PropertyType.valueOf(handler.getString("type"));
        this.description = handler.getString("description");
        this.dateToExecute = handler.getLong("dateToExecute");
        this.notify = handler.getBoolean("notify");
        this.priority = Priority.valueOf(handler.getString("priority"));
    }

    public static Property getProperty(UUID id) {
        if (!PROPERTY_CACHE.containsKey(id))
            PROPERTY_CACHE.put(id, new Property(id));
        return PROPERTY_CACHE.get(id);
    }

    public static Collection<Property> getAllActions() {
        return PROPERTY_CACHE.values();
    }

    public static List<Property> cache() {
        List<Property> list = new ArrayList<>();

        for (UUID id : DataHandler.getAll()) {
            if (!PROPERTY_CACHE.containsKey(id))
                PROPERTY_CACHE.put(id, new Property(id));
            list.add(PROPERTY_CACHE.get(id));
        }

        return list;
    }

    public boolean delete() {
        PROPERTY_CACHE.remove(this.id);
        ManagementYaar.selectedProperties.remove(this);
        return handler.delete();
    }
}