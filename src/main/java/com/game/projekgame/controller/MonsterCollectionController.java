package com.game.projekgame.controller;

import com.game.projekgame.GameData;
import com.game.projekgame.model.Monster;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MonsterCollectionController {

    @FXML
    private FlowPane monsterContainer;

    @FXML
    private Label teamLabel;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {

        refreshPage();
    }

    private void refreshPage() {

        monsterContainer.getChildren().clear();

        updateTeamLabel();

        for (Monster monster :
                GameData.monsterCollection) {

            createMonsterCard(monster);
        }
    }

    private void updateTeamLabel() {

        StringBuilder teamText =
                new StringBuilder("⚔ BATTLE TEAM\n\n");

        if (GameData.battleTeam.isEmpty()) {

            teamText.append("No Monster Selected");

        } else {

            for (int i = 0;
                 i < GameData.battleTeam.size();
                 i++) {

                teamText.append(i + 1)
                        .append(". ")
                        .append(GameData.battleTeam.get(i).getName())
                        .append("\n");
            }
        }

        teamLabel.setText(
                teamText.toString()
        );
    }

    private void createMonsterCard(Monster monster) {

        VBox card = new VBox();

        card.setSpacing(10);

        card.setPadding(
                new Insets(15)
        );

        card.setPrefWidth(250);

        card.getStyleClass().add(
                "monster-card"
        );

        Label emoji =
                new Label(
                        getEmoji(monster)
                );

        emoji.setStyle(
                "-fx-font-size:60px;"
        );

        Label name =
                new Label(
                        monster.getName()
                );

        Label element =
                new Label(
                        "Element : "
                                + monster.getElement()
                );

        Label hp =
                new Label(
                        "❤️ HP : "
                                + monster.getHp()
                );

        Label atk =
                new Label(
                        "⚔ ATK : "
                                + monster.getAttack()
                );

        Label def =
                new Label(
                        "🛡 DEF : "
                                + monster.getDefense()
                );

        Label skill =
                new Label(
                        "✨ Skill : "
                                + monster.useSkill()
                );

        Button useButton =
                new Button(
                        "USE"
                );

        useButton.setPrefWidth(
                180
        );

        useButton.setOnAction(e -> {

            GameData.activeMonster =
                    monster;

            refreshPage();
        });

        Button teamButton =
                new Button();

        teamButton.setPrefWidth(
                180
        );

        if (GameData.battleTeam.contains(monster)) {

            teamButton.setText(
                    "REMOVE TEAM"
            );

        } else {

            teamButton.setText(
                    "ADD TO TEAM"
            );
        }

        teamButton.setOnAction(e -> {

            if (GameData.battleTeam.contains(monster)) {

                GameData.battleTeam.remove(monster);

            } else {

                if (GameData.battleTeam.size() < 3) {

                    GameData.battleTeam.add(monster);
                }
            }

            refreshPage();
        });

        card.getChildren().addAll(
                emoji,
                name,
                element,
                hp,
                atk,
                def,
                skill,
                useButton,
                teamButton
        );

        monsterContainer.getChildren().add(card);
    }

    private String getEmoji(
            Monster monster
    ) {

        switch (
                monster.getElement()
        ) {

            case "FIRE":
                return "🔥";

            case "WATER":
                return "💧";

            case "THUNDER":
                return "⚡";

            case "EARTH":
                return "🪨";

            case "ICE":
                return "❄";

            case "NATURE":
                return "🌿";

            default:
                return "❓";
        }
    }

    @FXML
    public void handleBack() {

        try {

            Parent root =
                    FXMLLoader.load(
                            getClass().getResource(
                                    "/fxml/Dashboard.fxml"
                            )
                    );

            Scene scene =
                    new Scene(root);

            scene.getStylesheets().add(
                    getClass()
                            .getResource("/css/style.css")
                            .toExternalForm()
            );

            Stage stage =
                    (Stage) backButton
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}