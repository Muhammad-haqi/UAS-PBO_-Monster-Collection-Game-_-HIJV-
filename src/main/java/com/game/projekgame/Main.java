package com.game.projekgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource("/fxml/Login.fxml")
                );

        Scene scene =
                new Scene(
                        loader.load(),
                        1000,
                        700
                );

        scene.getStylesheets().add(
                getClass()
                        .getResource("/css/style.css")
                        .toExternalForm()
        );

        stage.setTitle("Monster Collection Game");

        stage.setScene(scene);

        stage.setMinWidth(900);
        stage.setMinHeight(600);

        stage.setResizable(true);

        stage.centerOnScreen();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}