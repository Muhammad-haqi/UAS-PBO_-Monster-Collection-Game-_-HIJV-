package com.game.projekgame.model;

public class FireDragon extends Monster {

    public FireDragon() {

        super(
                "🔥 Fire Dragon",
                "FIRE",
                120,
                25,
                10
        );
    }

    @Override
    public String useSkill() {

        return "🔥 Fire Ball";
    }
}