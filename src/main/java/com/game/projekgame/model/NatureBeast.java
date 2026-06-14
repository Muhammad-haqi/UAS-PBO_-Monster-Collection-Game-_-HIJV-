package com.game.projekgame.model;

public class NatureBeast extends Monster {

    public NatureBeast() {

        super(
                "🌿 Nature Beast",
                "NATURE",
                140,
                22,
                14
        );
    }

    @Override
    public String useSkill() {

        return "🌿 Nature Wrath";
    }
}