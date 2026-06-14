package com.game.projekgame; // Sesuaikan jika kamu menaruhnya di folder service

import com.game.projekgame.model.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ApiService {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final HttpClient client = HttpClient.newHttpClient();

    // ==========================================
    // 1. AUTHENTICATION (LOGIN & REGISTER)
    // ==========================================
    public static JsonObject login(String username, String password) throws Exception {
        String jsonInput = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonInput))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

        if (response.statusCode() == 200) {
            return jsonResponse.getAsJsonObject("data");
        } else {
            throw new Exception(jsonResponse.get("message").getAsString());
        }
    }

    public static JsonObject register(String username, String password) throws Exception {
        String jsonInput = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonInput))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

        if (response.statusCode() == 201 || response.statusCode() == 200) {
            return jsonResponse.getAsJsonObject("data"); // Berisi token dll
        } else {
            throw new Exception(jsonResponse.get("message").getAsString());
        }
    }

    // ==========================================
    // 2. GAME STATE (SAVE & LOAD)
    // ==========================================
    public static void saveGame() {
        try {
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("level", GameData.level);
            requestBody.addProperty("score", GameData.score);
            requestBody.addProperty("exp", GameData.exp);

            if (GameData.activeMonster != null) {
                requestBody.addProperty("activeMonsterName", GameData.activeMonster.getName());
            }

            JsonArray collectionArray = new JsonArray();
            for (Monster m : GameData.monsterCollection) collectionArray.add(m.getName());
            requestBody.add("monsterCollection", collectionArray);

            JsonArray teamArray = new JsonArray();
            for (Monster m : GameData.battleTeam) teamArray.add(m.getName());
            requestBody.add("battleTeam", teamArray);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/game/save"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + GameData.jwtToken) // PENTING!
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("✅ Game Berhasil Disimpan ke Database!");
        } catch (Exception e) {
            System.out.println("❌ Gagal menyimpan game: " + e.getMessage());
        }
    }

    public static void loadGame() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/game/load"))
                    .header("Authorization", "Bearer " + GameData.jwtToken)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JsonObject data = JsonParser.parseString(response.body())
                        .getAsJsonObject().getAsJsonObject("data");

                GameData.level = data.get("level").getAsInt();
                GameData.score = data.get("score").getAsInt();
                GameData.exp = data.get("exp").getAsInt();

                GameData.monsterCollection = new ArrayList<>();
                data.getAsJsonArray("monsterCollection").forEach(elem ->
                        GameData.monsterCollection.add(getMonsterFromName(elem.getAsString()))
                );

                GameData.battleTeam = new ArrayList<>();
                data.getAsJsonArray("battleTeam").forEach(elem ->
                        GameData.battleTeam.add(getMonsterFromName(elem.getAsString()))
                );

                if (data.has("activeMonsterName") && !data.get("activeMonsterName").isJsonNull()) {
                    GameData.activeMonster = getMonsterFromName(data.get("activeMonsterName").getAsString());
                }
                System.out.println("✅ Game Data Berhasil Dimuat dari Database!");
            }
        } catch (Exception e) {
            System.out.println("❌ Gagal memuat game: " + e.getMessage());
        }
    }

    // ==========================================
    // 3. LEADERBOARD
    // ==========================================
    public static JsonArray getLeaderboard() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/leaderboard"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
        return jsonResponse.getAsJsonArray("data");
    }

    // Helper untuk mengubah String dari Database kembali menjadi Object Monster
    private static Monster getMonsterFromName(String name) {
        if (name.contains("Fire")) return new FireDragon();
        if (name.contains("Water")) return new WaterTurtle();
        if (name.contains("Thunder")) return new ThunderWolf();
        if (name.contains("Earth")) return new EarthGolem();
        if (name.contains("Ice")) return new IcePhoenix();
        return new NatureBeast();
    }
}