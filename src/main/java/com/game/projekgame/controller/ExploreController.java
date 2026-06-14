package com.game.projekgame.controller;

import com.game.projekgame.ApiService;
import com.game.projekgame.GameData;
import com.game.projekgame.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Random;

public class ExploreController {

    public Label wildMonsterLabel;

    public Button fightButton;

    public Button captureButton;

    public Button backButton;

    private Monster currentMonster;

    public void initialize() {

        Random random = new Random();

        int roll = random.nextInt(6);

        switch (roll) {

            case 0:
                currentMonster =
                        new FireDragon();
                break;

            case 1:
                currentMonster =
                        new WaterTurtle();
                break;

            case 2:
                currentMonster =
                        new ThunderWolf();
                break;

            case 3:
                currentMonster =
                        new EarthGolem();
                break;

            case 4:
                currentMonster =
                        new IcePhoenix();
                break;

            default:
                currentMonster =
                        new NatureBeast();
                break;
        }

        GameData.enemyMonster =
                currentMonster;

        wildMonsterLabel.setText(

                currentMonster.getName()

                        + "\n\nElement : "
                        + currentMonster.getElement()

                        + "\n❤️ HP : "
                        + currentMonster.getHp()

                        + "\n⚔ ATK : "
                        + currentMonster.getAttack()

                        + "\n🛡 DEF : "
                        + currentMonster.getDefense()

                        + "\n\nSkill : "
                        + currentMonster.useSkill()
        );
    }

    public void handleFight() {
        openPage("/fxml/Battle.fxml");
    }

    public void handleBack() {
        openPage("/fxml/Dashboard.fxml");
    }

    public void handleCapture() {
        boolean alreadyOwned = false;

        for (Monster monster : GameData.monsterCollection) {
            if (monster.getName().equals(currentMonster.getName())) {
                alreadyOwned = true;
                break;
            }
        }

        if (alreadyOwned) {
            wildMonsterLabel.setText("⚠ Monster Already Owned!");
            return;
        }

        GameData.monsterCollection.add(currentMonster);
        GameData.score += 50;

        wildMonsterLabel.setText("🎉 CAPTURE SUCCESS!\n\n" + currentMonster.getName());

        // ── PERUBAHAN: simpan ke backend setelah capture menggunakan Thread ──
        new Thread(() -> ApiService.saveGame()).start();
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
                    (Stage) fightButton
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}