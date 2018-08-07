package ru.r1zen.findip.utils;

import java.awt.*;
import java.io.File;

public class DesktopManager {
    public static void openFile(String fileName) {
        try {
            Desktop.getDesktop().open(new File(fileName));
        } catch (Exception e) {
            LogManager.addError(e.getMessage());
        }
    }
}
