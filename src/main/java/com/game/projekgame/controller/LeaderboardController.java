package com.game.projekgame.controller;

import com.game.projekgame.ApiService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LeaderboardController {

    @FXML
    private Label scoreLabel;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        scoreLabel.setText("Mencari data leaderboard...");

        // Panggil API Leaderboard di Thread terpisah
        new Thread(() -> {
            try {
                JsonArray leaderboardData = ApiService.getLeaderboard();
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < leaderboardData.size(); i++) {
                    JsonObject player = leaderboardData.get(i).getAsJsonObject();
                    String name = player.get("username").getAsString();
                    int score = player.get("score").getAsInt();
                    int level = player.get("level").getAsInt();

                    sb.append((i+1)).append(". ").append(name)
                            .append(" - Lv: ").append(level).append(" | Score: ").append(score).append("\n");
                }

                Platform.runLater(() -> scoreLabel.setText(sb.toString()));
            } catch (Exception e) {
                Platform.runLater(() -> scoreLabel.setText("Gagal memuat leaderboard."));
            }
        }).start();
    }

    @FXML
    public void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Dashboard.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}