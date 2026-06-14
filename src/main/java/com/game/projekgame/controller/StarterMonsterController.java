package com.game.projekgame.controller;

import com.game.projekgame.ApiService;
import com.game.projekgame.GameData;
import com.game.projekgame.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StarterMonsterController {

    private void openDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Dashboard.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

            Stage stage = (Stage) Stage.getWindows().filtered(window -> window.isShowing()).get(0);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStarter(Monster monster) {
        GameData.activeMonster = monster;
        GameData.monsterCollection.clear();
        GameData.battleTeam.clear();

        GameData.monsterCollection.add(monster);
        GameData.battleTeam.add(monster);

        // AUTO-SAVE: Simpan starter monster ke database menggunakan Thread
        new Thread(() -> ApiService.saveGame()).start();

        openDashboard();
    }

    public void selectFireDragon() { setStarter(new FireDragon()); }
    public void selectWaterTurtle() { setStarter(new WaterTurtle()); }
    public void selectThunderWolf() { setStarter(new ThunderWolf()); }
    public void selectEarthGolem() { setStarter(new EarthGolem()); }
}