package com.game.projekgame;

import com.game.projekgame.model.Monster;
import java.util.ArrayList;
import java.util.List;

public class GameData {
    public static String username = "";

    // Variabel token yang dicari oleh ApiService
    public static String jwtToken = "";
    public static String token = ""; // Jaga-jaga kalau LoginController lamamu pakai ini
    public static boolean isNewPlayer = false;

    public static int level = 1;
    public static int score = 0;
    public static int exp = 0;

    public static Monster activeMonster;
    public static Monster enemyMonster;

    public static List<Monster> monsterCollection = new ArrayList<>();
    public static List<Monster> battleTeam = new ArrayList<>();

    public static void reset() {
        username = "";
        jwtToken = "";
        token = "";
        level = 1;
        score = 0;
        exp = 0;
        activeMonster = null;
        enemyMonster = null;
        monsterCollection.clear();
        battleTeam.clear();
    }
}