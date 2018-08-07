package ru.r1zen.findip.view;

import javafx.fxml.FXML;
import ru.r1zen.findip.ProgramManager;

public class RootLayoutController {
    private ProgramManager programManager;

    public void setProgramManager(ProgramManager programManager) {
        this.programManager = programManager;
    }

    @FXML
    private void showAboutWindow() {
        programManager.showAboutWindow();
    }

    @FXML
    private void showSettingsWindow() {
        programManager.showSettingsWindow();
    }

    @FXML
    private void exit() {
        System.exit(0);
    }
}
