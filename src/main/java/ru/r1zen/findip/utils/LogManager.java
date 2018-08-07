package ru.r1zen.findip.utils;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LogManager {
    private static ObservableList<String> messages = FXCollections.observableArrayList();
    private static ObservableList<String> errors = FXCollections.observableArrayList();

    public static void addMessage(String msg) {
        Platform.runLater(() -> messages.add(msg));
        System.out.println("Сообщение: " + msg);
    }

    public static void addError(String error) {
        Platform.runLater(() -> errors.add(error));
        System.out.println("Ошибка: " + error);
    }

    public static ObservableList<String> getMessages() {
        return messages;
    }

    public static ObservableList<String> getErrors() {
        return errors;
    }
}
