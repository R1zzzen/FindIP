package ru.r1zen.findip.view;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.r1zen.findip.ProgramManager;
import ru.r1zen.findip.model.IpRangeModel;
import ru.r1zen.findip.model.Model;
import ru.r1zen.findip.utils.DesktopManager;
import ru.r1zen.findip.utils.LogManager;
import ru.r1zen.findip.utils.PropertiesManager;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class MainScreenController {
    @FXML
    private TextField dbPath;

    @FXML
    private TextField requestPath;

    @FXML
    private TextField resultPath;

    @FXML
    private ListView<String> messagesArea;

    @FXML
    private ListView<String> errorsArea;

    @FXML
    private Accordion accordion;

    @FXML
    private ProgressBar progressBar;

    private Stage primaryStage;
    private Model model;
    private boolean isDatabaseLoaded = false;
    private boolean isRequestLoaded = false;

    public void initialize() {
        this.model = new IpRangeModel();
        messagesArea.setItems(LogManager.getMessages());

        errorsArea.setItems(LogManager.getErrors());
        errorsArea.getChildrenUnmodifiable().addListener((ListChangeListener<Node>) c -> errorsArea.scrollTo(errorsArea.getItems().size()));
        LogManager.getErrors().addListener((ListChangeListener<String>) c -> accordion.setExpandedPane(accordion.getPanes().get(1)));

        accordion.setExpandedPane(accordion.getPanes().get(0));
        loadSettings();
        dbPath.setFocusTraversable(false);
    }

    @FXML
    private void handleRequestClick() {
        int err = 0;
        if (dbPath.getText() == null || dbPath.getText().length() < 5) {
            err++;
            LogManager.addError("Укажите путь к базе данных");
        }
        if (requestPath.getText() == null || requestPath.getText().length() < 5) {
            err++;
            LogManager.addError("Укажите путь к файлу запроса или один IP-адрес");
        }
        if (resultPath.getText() == null || resultPath.getText().length() < 5) {
            err++;
            LogManager.addError("Укажите путь к файлу ответа");
        }

        if (err == 0) {
            progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

            if (!isDatabaseLoaded)
                model.loadDataBase(dbPath.getText());
            if (!isRequestLoaded)
                model.loadRequest(requestPath.getText());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = resultPath.getText();
                    if (!result.endsWith(".xls"))
                        result += ".xls";

                    resultPath.setText(result);
                    model.findIp(result);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(100.0);
                        }
                    });
                }
            }).start();
        }

        if (PropertiesManager.isCheckBoxActivated)
            PropertiesManager.saveSettings(dbPath.getText(), requestPath.getText(), resultPath.getText());
    }

    @FXML
    private void selectDbPath(){
        selectFile(0);
    }

    @FXML
    private void selectRequestPath(){
        selectFile(1);
    }

    @FXML
    private void selectResultPath(){
        selectFile(2);
    }

    @FXML
    private void clearLog() {
        LogManager.getMessages().clear();
        LogManager.getErrors().clear();
        progressBar.setProgress(0.0);
    }

    @FXML
    private void openResultFile() {
        if (resultPath.getText() != null && resultPath.getText().length() > 5)
            DesktopManager.openFile(resultPath.getText());
    }

    private void loadSettings() {
        if (PropertiesManager.loadSettings("dbPath") != null)
            dbPath.setText(PropertiesManager.loadSettings("dbPath"));

        if (PropertiesManager.loadSettings("requestPath") != null)
            requestPath.setText(PropertiesManager.loadSettings("requestPath"));

        if (PropertiesManager.loadSettings("resultPath") != null)
            resultPath.setText(PropertiesManager.loadSettings("resultPath"));
    }

    private void selectFile(int from) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            switch (from) {
                case 0:
                    dbPath.setText(file.getAbsolutePath());
                    //сразу начать загрзку БД в фоне
                    isDatabaseLoaded = model.loadDataBase(dbPath.getText());
                    break;

                case 1:
                    requestPath.setText(file.getAbsolutePath());
                    //сразу начать загрзку БД в фоне
                    isRequestLoaded = model.loadRequest(requestPath.getText());
                    break;

                case 2:
                    resultPath.setText(file.getAbsolutePath());
                    break;
            }
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }



}
