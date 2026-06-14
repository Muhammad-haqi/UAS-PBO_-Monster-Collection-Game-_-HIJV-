package com.game.projekgame.controller;

import com.game.projekgame.ApiService;
import com.game.projekgame.GameData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Label monsterEmojiLabel;

    @FXML
    private Label monsterLabel;

    @FXML
    private Label levelLabel;

    @FXML
    private Label hpLabel;

    @FXML
    private Label attackLabel;

    @FXML
    private Label defenseLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label expLabel;

    @FXML
    private ProgressBar hpBar;

    @FXML
    private ProgressBar expBar;

    @FXML
    public void initialize() {

        if (GameData.activeMonster == null)
            return;

        monsterLabel.setText(
                GameData.activeMonster.getName()
        );

        if (GameData.activeMonster.getName().contains("Fire"))
            monsterEmojiLabel.setText("🔥");

        else if (GameData.activeMonster.getName().contains("Water"))
            monsterEmojiLabel.setText("💧");

        else if (GameData.activeMonster.getName().contains("Thunder"))
            monsterEmojiLabel.setText("⚡");

        else if (GameData.activeMonster.getName().contains("Earth"))
            monsterEmojiLabel.setText("🪨");

        else if (GameData.activeMonster.getName().contains("Ice"))
            monsterEmojiLabel.setText("❄");

        else if (GameData.activeMonster.getName().contains("Nature"))
            monsterEmojiLabel.setText("🌿");

        levelLabel.setText(
                "Level : " + GameData.level
        );

        hpLabel.setText(
                "HP : " +
                        GameData.activeMonster.getHp()
        );

        attackLabel.setText(
                "Attack : " +
                        GameData.activeMonster.getAttack()
        );

        defenseLabel.setText(
                "Defense : " +
                        GameData.activeMonster.getDefense()
        );

        scoreLabel.setText(
                "Score : " + GameData.score
        );

        hpBar.setProgress(1.0);

        expBar.setProgress(
                GameData.exp / 100.0
        );

        expLabel.setText(
                GameData.exp + "/100 EXP"
        );
    }

    public void handleExplore() {
        openPage("/fxml/Explore.fxml");
    }

    public void handleCollection() {
        openPage("/fxml/MonsterCollection.fxml");
    }

    public void handleLeaderboard() {
        openPage("/fxml/Leaderboard.fxml");
    }

    public void handleLogout() {
        // ── PERUBAHAN: simpan game ke backend sebelum logout menggunakan Thread ──
        new Thread(() -> ApiService.saveGame()).start();

        // Reset semua data lokal
        GameData.activeMonster = null;
        GameData.enemyMonster = null;
        GameData.monsterCollection.clear();
        GameData.battleTeam.clear();
        GameData.exp = 0;
        GameData.level = 1;
        GameData.score = 0;
        GameData.jwtToken = ""; // Hapus token

        openPage("/fxml/Login.fxml");
    }

    private void openPage(String path) {

        try {

            Parent root =
                    FXMLLoader.load(
                            getClass().getResource(path)
                    );

            Scene scene =
                    new Scene(root);

            scene.getStylesheets().add(
                    getClass()
                            .getResource("/css/style.css")
                            .toExternalForm()
            );

            Stage stage =
                    (Stage) monsterLabel
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}