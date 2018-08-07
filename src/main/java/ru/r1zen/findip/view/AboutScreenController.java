package ru.r1zen.findip.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ru.r1zen.findip.ProgramManager;
import ru.r1zen.findip.utils.LogManager;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class AboutScreenController  {
    @FXML
    private Label versionLabel;

    @FXML
    private Label emailLabel;

    private Stage dialogStage;

    @FXML
    public void initialize() {
        versionLabel.setText(ProgramManager.VERSION);
        emailLabel.setText(ProgramManager.EMAIL);
    }

    @FXML
    private void close() {
        dialogStage.close();
    }

    @FXML
    private void sendMessage() {
        Desktop desktop = Desktop.getDesktop();
        //String message = "mailto:" + ProgramManager.EMAIL;
        String message = "mailto:" +ProgramManager.EMAIL +"&subject=FindIP%20v" + ProgramManager.VERSION;
        URI uri = URI.create(message);
        try {
            desktop.mail(uri);
        } catch (IOException e) {
            LogManager.addError(e.getMessage());
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


}
