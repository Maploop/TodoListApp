package net.jet3.booking101;

import javafx.application.Application;
import net.jet3.booking101.initalization.ApplicationInitalizer;
import net.jet3.booking101.sql.SQLActionsData;
import net.jet3.booking101.sql.SQLDatabase;
import net.jet3.booking101.ui.MainUI;
import net.jet3.booking101.util.Log;
import net.jet3.booking101.util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (c) Jet3. All rights reserved.
 * Owned by Soroush Behzadi, Mohammad Hosein Nemati.
 */
public class ManagementYaar
{
    public static ManagementYaar instance;

    public static String today = "";
    public static String todayComputer = "";

    public SQLDatabase sql;
    public SQLActionsData actionsData;

    public static String LAST_EDITED_PROJECT = "";
    public static int WIDTH;
    public static int HEIGHT;
    public static boolean MAXIMIZED;
    public static boolean DEVELOPER_MODE;

    public ManagementYaar(String... args) {
        instance = this;

        ApplicationInitalizer.init();

        Log.info("Loading SQL database...");
        sql = new SQLDatabase();
        actionsData = new SQLActionsData();

        Date date = new Date();
        today = Util.prettify(String.valueOf(date.getDay())) + " of " + Util.getMonth(date.getMonth()) + " " + date.getYear();
        todayComputer = new SimpleDateFormat("yyyy-MM-dd").format(date);

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
}
