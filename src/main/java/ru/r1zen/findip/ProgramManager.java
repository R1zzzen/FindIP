package ru.r1zen.findip;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.r1zen.findip.utils.LogManager;
import ru.r1zen.findip.view.AboutScreenController;
import ru.r1zen.findip.view.MainScreenController;
import ru.r1zen.findip.view.RootLayoutController;
import ru.r1zen.findip.view.SettingsWindowController;

import java.io.IOException;

public class ProgramManager extends Application {
    public static final String TITLE = "FindIP by R1zen";
    public static final String VERSION = "3.0";
    public static final String EMAIL = "support@r1zen.ru";
    private BorderPane rootLayout;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(TITLE + " v" + VERSION);

        // Устанавливаем иконку приложения.
        this.primaryStage.getIcons().add(new Image(ProgramManager.class.getResourceAsStream("/images/icon.png")));

        initRootLayout();
        showMainScreen();
    }



    private void showMainScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(ProgramManager.class.getResource("view/MainScreen.fxml"));

            VBox mainScreen = fxmlLoader.load();
            rootLayout.setCenter(mainScreen);

            MainScreenController mainScreenController = fxmlLoader.getController();
            mainScreenController.setPrimaryStage(primaryStage);

        } catch (IOException e) {
            LogManager.addError(e.getMessage());
            System.exit(1);
        }
    }

    private void initRootLayout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("view/RootLayout.fxml"));

            rootLayout = fxmlLoader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = fxmlLoader.getController();
            controller.setProgramManager(this);

            primaryStage.show();
        } catch (IOException e) {
            LogManager.addError(e.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) { launch(args);}

    public void showAboutWindow() {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/AboutScreen.fxml"));
            AnchorPane page = loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("О программе");
            dialogStage.getIcons().add(new Image(ProgramManager.class.getResourceAsStream("/images/icon.png")));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            AboutScreenController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

        } catch (IOException e) {
            LogManager.addError(e.getMessage());
        }
    }

    public void showSettingsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/SettingsWindow.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Настройки");
            dialogStage.getIcons().add(new Image(ProgramManager.class.getResourceAsStream("/images/icon.png")));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            SettingsWindowController settingsWindowController = loader.getController();
            settingsWindowController.setDialogStage(dialogStage);

            dialogStage.showAndWait();

        }
        catch (IOException ex) {
            LogManager.addError(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
