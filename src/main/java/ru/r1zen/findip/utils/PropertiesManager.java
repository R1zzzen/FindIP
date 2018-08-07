package ru.r1zen.findip.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesManager {
    public static boolean isCheckBoxActivated = false;

    public static void saveOldPaths(boolean b) {
        isCheckBoxActivated = b;
        try {
            File file = new File("settings.ini");
            if (!file.exists())
                file.createNewFile();

            FileInputStream fis = new FileInputStream(file);

            Properties properties = new Properties();
            properties.load(fis);
            fis.close();

            properties.setProperty("isCheckBoxActivated", String.valueOf(b));

            FileOutputStream fileOutputStream = new FileOutputStream("settings.ini");
            properties.store(fileOutputStream, null);
            fileOutputStream.close();

        } catch (IOException e) {
            LogManager.addError(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void loadSavedPath() {
        try {
            File file = new File("settings.ini");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream("settings.ini");
                Properties properties = new Properties();
                properties.load(fis);
                isCheckBoxActivated = Boolean.parseBoolean(properties.getProperty("isCheckBoxActivated"));
            }
            else
                isCheckBoxActivated = false;

        } catch (IOException e) {
            LogManager.addError(e.getMessage());
            e.printStackTrace();
            isCheckBoxActivated = false;
        }
    }

    public static void saveSettings(String dbPath, String requestPath, String resultPath) {
        try {
            File file = new File("settings.ini");
            if (!file.exists())
                file.createNewFile();

            FileInputStream fis = new FileInputStream(file);

            Properties properties = new Properties();
            properties.load(fis);
            fis.close();

            properties.setProperty("dbPath", dbPath == null ? "" : dbPath);
            properties.setProperty("requestPath", requestPath == null ? "" : requestPath);
            properties.setProperty("resultPath", resultPath == null ? "" : resultPath);

            FileOutputStream fileOutputStream = new FileOutputStream("settings.ini");
            properties.store(fileOutputStream, null);
            fileOutputStream.close();

        } catch (IOException e) {
            LogManager.addError(e.getMessage());
            e.printStackTrace();
        }
    }

    public static String loadSettings(String propName) {
        try {
            File file = new File("settings.ini");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream("settings.ini");
                Properties properties = new Properties();
                properties.load(fis);
                return properties.getProperty(propName);
            }
            else
                return "";

        } catch (IOException e) {
            LogManager.addError(e.getMessage());
            e.printStackTrace();
            return "";
        }
    }
}
