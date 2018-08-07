package ru.r1zen.findip.view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.r1zen.findip.utils.LogManager;
import ru.r1zen.findip.utils.PropertiesManager;

public class SettingsWindowController {

    @FXML
    private TextField dbPath;

    @FXML
    private TextField requestPath;

    @FXML
    private TextField resultPath;

    @FXML
    private CheckBox checkBox;

    private Stage dialogStage;

    public void initialize() {
        if (PropertiesManager.loadSettings("dbPath") != null)
            dbPath.setText(PropertiesManager.loadSettings("dbPath"));

        if (PropertiesManager.loadSettings("requestPath") != null)
            requestPath.setText(PropertiesManager.loadSettings("requestPath"));

        if (PropertiesManager.loadSettings("resultPath") != null)
            resultPath.setText(PropertiesManager.loadSettings("resultPath"));

        PropertiesManager.loadSavedPath();
        checkBox.setSelected(PropertiesManager.isCheckBoxActivated);
    }

    @FXML
    private void okClick() {
        PropertiesManager.saveSettings(dbPath.getText(), requestPath.getText(), resultPath.getText());
        PropertiesManager.saveOldPaths(checkBox.isSelected());
        LogManager.addMessage("Настройки сохранены");
        dialogStage.close();
    }

    @FXML
    private void cancelClick() {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}