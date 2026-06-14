package com.game.projekgame.controller;

import com.game.projekgame.ApiService;
import com.game.projekgame.GameData;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    public Button loginButton;
    public Button registerButton;
    public TextField usernameField;
    public PasswordField passwordField;
    public Label statusLabel;

    public void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("⚠ Username dan password wajib diisi!");
            return;
        }

        statusLabel.setText("⏳ Logging in...");
        loginButton.setDisable(true);
        registerButton.setDisable(true);

        // Jalankan di thread terpisah agar UI tidak freeze
        new Thread(() -> {
            try {
                JsonObject data = ApiService.login(username, password);

                Platform.runLater(() -> {
                    loginButton.setDisable(false);
                    registerButton.setDisable(false);

                    GameData.jwtToken = data.get("token").getAsString();
                    GameData.username = data.get("username").getAsString();
                    boolean isNewPlayer = data.get("newPlayer").getAsBoolean();

                    if (isNewPlayer) {
                        // Belum punya save → pilih starter monster
                        openPage("/fxml/StarterMonster.fxml");
                    } else {
                        // Punya save → load game lalu masuk dashboard
                        ApiService.loadGame();
                        openPage("/fxml/Dashboard.fxml");
                    }
                });

            } catch (Exception e) {
                Platform.runLater(() -> {
                    loginButton.setDisable(false);
                    registerButton.setDisable(false);
                    statusLabel.setText("❌ " + e.getMessage());
                });
            }
        }).start();
    }

    public void handleRegister(ActionEvent event) {
        openPage("/fxml/Register.fxml");
    }

    private void openPage(String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}