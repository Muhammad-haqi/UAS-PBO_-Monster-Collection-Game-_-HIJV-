package com.game.projekgame.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameStateResponse {

    private String username;
    private int level;
    private int score;
    private int exp;
    private String activeMonsterName;
    private List<String> monsterCollection;
    private List<String> battleTeam;
    private boolean hasSavedGame; // false jika belum pernah save
}
