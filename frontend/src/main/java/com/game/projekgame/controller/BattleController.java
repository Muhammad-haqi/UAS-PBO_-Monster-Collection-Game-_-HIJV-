package com.game.projekgame.controller;

import com.game.projekgame.ApiService;
import com.game.projekgame.GameData;
import com.game.projekgame.model.Monster;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class BattleController {

    @FXML
    private Label playerMonsterLabel;

    @FXML
    private Label playerEmojiLabel;

    @FXML
    private Label enemyMonsterLabel;

    @FXML
    private Label playerHpLabel;

    @FXML
    private Label enemyHpLabel;

    @FXML
    private Label battleInfoLabel;

    @FXML
    private Label teamLabel;

    @FXML
    private ProgressBar playerHpBar;

    @FXML
    private ProgressBar enemyHpBar;

    @FXML
    private Button fightAgainButton;

    @FXML
    private Button backButton;

    private Monster currentMonster;

    private int currentMonsterIndex = 0;

    private int playerCurrentHp;

    private int enemyCurrentHp;

    private boolean battleEnded = false;

    @FXML
    public void initialize() {

        if (GameData.battleTeam.isEmpty()) {

            battleInfoLabel.setText(
                    "No Battle Team!"
            );

            return;
        }

        currentMonster =
                GameData.battleTeam.get(0);

        playerCurrentHp =
                currentMonster.getHp();

        enemyCurrentHp =
                GameData.enemyMonster.getHp();

        updateTeamLabel();

        updateBattleUI();

        battleInfoLabel.setText(
                "⚔ Battle Started!"
        );
    }

    @FXML
    public void handleAttack() {

        if (battleEnded)
            return;

        double playerMultiplier =
                getElementMultiplier(
                        currentMonster.getElement(),
                        GameData.enemyMonster.getElement()
                );

        int playerDamage =
                (int)(
                        currentMonster.getAttack()
                                * playerMultiplier
                );

        enemyCurrentHp -= playerDamage;

        if (enemyCurrentHp < 0)
            enemyCurrentHp = 0;

        if (enemyCurrentHp == 0) {

            GameData.score += 100;

            GameData.exp += 25;

            if (GameData.exp >= 100) {

                GameData.exp = 0;

                GameData.level++;
            }

            updateBattleUI();

            showWin();

            return;
        }

        double enemyMultiplier =
                getElementMultiplier(
                        GameData.enemyMonster.getElement(),
                        currentMonster.getElement()
                );

        int enemyDamage =
                (int)(
                        GameData.enemyMonster.getAttack()
                                * enemyMultiplier
                );

        playerCurrentHp -= enemyDamage;

        if (playerCurrentHp < 0)
            playerCurrentHp = 0;

        updateBattleUI();

        battleInfoLabel.setText(
                currentMonster.useSkill()
                        + "\n\n"
                        + "⚔ You Deal "
                        + playerDamage
                        + " Damage\n"
                        + "👹 Enemy Deal "
                        + enemyDamage
                        + " Damage"
        );

        if (playerCurrentHp == 0) {
            autoSwapMonster();
        }
    }

    @FXML
    public void handleSwap() {

        if (battleEnded)
            return;

        if (GameData.battleTeam.size() <= 1)
            return;

        currentMonsterIndex++;

        if (currentMonsterIndex >=
                GameData.battleTeam.size()) {

            currentMonsterIndex = 0;
        }

        currentMonster =
                GameData.battleTeam.get(
                        currentMonsterIndex
                );

        playerCurrentHp =
                currentMonster.getHp();

        updateBattleUI();

        battleInfoLabel.setText(
                "🔄 Swapped To\n"
                        + currentMonster.getName()
        );
    }

    private void autoSwapMonster() {

        currentMonsterIndex++;

        if (currentMonsterIndex >=
                GameData.battleTeam.size()) {

            showLose();
            return;
        }

        currentMonster =
                GameData.battleTeam.get(
                        currentMonsterIndex
                );

        playerCurrentHp =
                currentMonster.getHp();

        updateBattleUI();

        battleInfoLabel.setText(
                "⚠ Monster Defeated!\n"
                        + "Switched To\n"
                        + currentMonster.getName()
        );
    }

    private void showWin() {
        battleEnded = true;

        // ── PERUBAHAN: simpan ke backend setelah WIN menggunakan Thread ──
        new Thread(() -> ApiService.saveGame()).start();

        battleInfoLabel.setText(
                "🏆 YOU WIN!\n\n"
                        + "+100 Score\n"
                        + "+25 EXP"
        );

        fightAgainButton.setVisible(true);
        fightAgainButton.setManaged(true);
        backButton.setVisible(true);
        backButton.setManaged(true);
    }

    private void showLose() {

        battleEnded = true;

        battleInfoLabel.setText(
                "💀 YOU LOSE!"
        );

        backButton.setVisible(true);
        backButton.setManaged(true);
    }

    @FXML
    public void handleFightAgain() {
        openPage("/fxml/Explore.fxml");
    }

    @FXML
    public void handleBackToDashboard() {
        openPage("/fxml/Dashboard.fxml");
    }

    private void updateBattleUI() {

        playerMonsterLabel.setText(
                currentMonster.getName()
        );

        playerEmojiLabel.setText(
                getEmoji(
                        currentMonster.getElement()
                )
        );

        enemyMonsterLabel.setText(
                GameData.enemyMonster.getName()
        );

        playerHpLabel.setText(
                "HP : "
                        + playerCurrentHp
                        + "/"
                        + currentMonster.getHp()
        );

        enemyHpLabel.setText(
                "HP : "
                        + enemyCurrentHp
                        + "/"
                        + GameData.enemyMonster.getHp()
        );

        playerHpBar.setProgress(
                (double) playerCurrentHp
                        / currentMonster.getHp()
        );

        enemyHpBar.setProgress(
                (double) enemyCurrentHp
                        / GameData.enemyMonster.getHp()
        );
    }

    private void updateTeamLabel() {

        StringBuilder builder =
                new StringBuilder();

        for (Monster monster :
                GameData.battleTeam) {

            builder.append(
                    monster.getName()
            ).append("\n");
        }

        teamLabel.setText(
                builder.toString()
        );
    }

    private String getEmoji(String element) {

        switch (element) {

            case "FIRE":    return "🔥";
            case "WATER":   return "💧";
            case "THUNDER": return "⚡";
            case "EARTH":   return "🪨";
            case "ICE":     return "❄";
            case "NATURE":  return "🌿";
            default:        return "❓";
        }
    }

    private double getElementMultiplier(
            String attacker,
            String defender
    ) {

        if (attacker.equals("WATER")   && defender.equals("FIRE"))    return 1.5;
        if (attacker.equals("FIRE")    && defender.equals("NATURE"))  return 1.5;
        if (attacker.equals("NATURE")  && defender.equals("EARTH"))   return 1.5;
        if (attacker.equals("EARTH")   && defender.equals("THUNDER")) return 1.5;
        if (attacker.equals("THUNDER") && defender.equals("WATER"))   return 1.5;
        if (attacker.equals("ICE")     && defender.equals("NATURE"))  return 1.5;
        if (attacker.equals("FIRE")    && defender.equals("ICE"))     return 1.5;

        if (attacker.equals("FIRE")    && defender.equals("WATER"))   return 0.5;
        if (attacker.equals("NATURE")  && defender.equals("FIRE"))    return 0.5;
        if (attacker.equals("EARTH")   && defender.equals("NATURE"))  return 0.5;
        if (attacker.equals("THUNDER") && defender.equals("EARTH"))   return 0.5;
        if (attacker.equals("WATER")   && defender.equals("THUNDER")) return 0.5;
        if (attacker.equals("NATURE")  && defender.equals("ICE"))     return 0.5;
        if (attacker.equals("ICE")     && defender.equals("FIRE"))    return 0.5;

        return 1.0;
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
                    (Stage) playerMonsterLabel
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}