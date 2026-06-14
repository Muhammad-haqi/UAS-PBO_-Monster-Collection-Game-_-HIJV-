package com.game.projekgame;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class SceneManager {

    public static void switchPage(
            Stage stage,
            String fxml
    ) {

        try {

            Parent root =
                    FXMLLoader.load(
                            SceneManager.class
                                    .getResource(fxml)
                    );

            stage.getScene().setRoot(root);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}