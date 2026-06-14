package com.game.projekgame;

import com.game.projekgame.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Taruh file ini di com.game.projekgame (sama dengan GameData.java).
 * Mengkonversi nama monster (String) menjadi object Monster.
 * Dibutuhkan saat load game dari backend.
 */
public class MonsterFactory {

    /**
     * Buat Monster berdasarkan nama.
     * Nama harus cocok dengan yang disimpan (getName() dari setiap subclass).
     */
    public static Monster createByName(String name) {
        if (name == null || name.isBlank()) return null;

        if (name.contains("Fire Dragon"))   return new FireDragon();
        if (name.contains("Water Turtle"))  return new WaterTurtle();
        if (name.contains("Thunder Wolf"))  return new ThunderWolf();
        if (name.contains("Earth Golem"))   return new EarthGolem();
        if (name.contains("Ice Phoenix"))   return new IcePhoenix();
        if (name.contains("Nature Beast"))  return new NatureBeast();

        return null; // nama tidak dikenal
    }

    /**
     * Buat List<Monster> dari List<String> nama monster.
     */
    public static List<Monster> createByNames(List<String> names) {
        List<Monster> result = new ArrayList<>();
        if (names == null) return result;

        for (String name : names) {
            Monster m = createByName(name);
            if (m != null) result.add(m);
        }

        return result;
    }
}
