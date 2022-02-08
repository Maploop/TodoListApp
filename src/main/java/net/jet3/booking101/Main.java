package net.jet3.booking101;

import javafx.application.Application;
import net.jet3.booking101.initalization.ApplicationInitalizer;
import net.jet3.booking101.timeHandler.DateAndTime;
import net.jet3.booking101.ui.MainUI;
import net.jet3.booking101.util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (c) Jet3. All rights reserved.
 * Owned by Soroush Behzadi, Mohammad Hosein Nemati.
 */
public class Main
{
    public static String today = "";
    public static String todayComputer = "";

    public static String LAST_EDITED_PROJECT = "";
    public static int WIDTH;
    public static int HEIGHT;
    public static boolean MAXIMIZED;

    public static void main(String[] args) {
        ApplicationInitalizer.init();

        Date date = new Date();
        today = Util.prettify(String.valueOf(date.getDay())) + " of " + Util.getMonth(date.getMonth()) + " " + date.getYear();
        todayComputer = new SimpleDateFormat("yyyy-MM-dd").format(date);

        Application.launch(MainUI.class, args);
    }

    public static void exit() {
        Util.set(ApplicationInitalizer.configFile, "lastEditedProject", LAST_EDITED_PROJECT);
        Util.set(ApplicationInitalizer.configFile, "width", WIDTH);
        Util.set(ApplicationInitalizer.configFile, "height", HEIGHT);
        Util.set(ApplicationInitalizer.configFile, "maximized", MAXIMIZED);

        System.exit(0);
    }
}
