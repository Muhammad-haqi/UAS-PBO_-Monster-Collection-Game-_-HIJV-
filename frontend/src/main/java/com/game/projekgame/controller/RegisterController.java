package com.game.projekgame.controller;

import com.game.projekgame.ApiService;
import com.game.projekgame.GameData;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML public Button backButton;
    @FXML public Button registerButton;
    @FXML public TextField usernameField;
    @FXML public PasswordField passwordField;
    @FXML public PasswordField confirmPasswordField;
    @FXML public Label statusLabel;

    public void handleRegister(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirm  = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            statusLabel.setText("⚠ Semua field wajib diisi!");
            return;
        }

        if (!password.equals(confirm)) {
            statusLabel.setText("⚠ Password tidak cocok!");
            return;
        }

        statusLabel.setText("⏳ Mendaftarkan akun...");
        registerButton.setDisable(true);
        backButton.setDisable(true);

        new Thread(() -> {
            try {
                // SEKARANG DITAMPUNG PAKAI JsonObject, BUKAN String
                JsonObject data = ApiService.register(username, password);

                Platform.runLater(() -> {
                    registerButton.setDisable(false);
                    backButton.setDisable(false);

                    // Ambil datanya menggunakan format Gson
                    GameData.jwtToken = data.get("token").getAsString();
                    GameData.username = data.get("username").getAsString();
                    GameData.isNewPlayer = true;

                    openPage("/fxml/StarterMonster.fxml");
                });

            } catch (Exception e) {
                Platform.runLater(() -> {
                    registerButton.setDisable(false);
                    backButton.setDisable(false);
                    statusLabel.setText("❌ " + e.getMessage());
                });
            }
        }).start();
    }

    public void handleBack(ActionEvent event) {
        openPage("/fxml/Login.fxml");
    }

    private void openPage(String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}