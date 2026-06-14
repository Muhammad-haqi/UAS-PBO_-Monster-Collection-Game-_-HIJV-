package com.game.projekgame.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// DTO untuk menyimpan game state dari frontend → backend
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameStateRequest {

    // Cocok persis dengan field di GameData.java frontend
    private int level;
    private int score;
    private int exp;
    private String activeMonsterName;

    // List nama monster, contoh: ["🔥 Fire Dragon", "💧 Water Turtle"]
    private List<String> monsterCollection;

    // List nama monster di battle team (max 3)
    private List<String> battleTeam;
}
