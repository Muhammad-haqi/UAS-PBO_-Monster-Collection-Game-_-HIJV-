package com.game.projekgame.model;

public class EarthGolem extends Monster {

    public EarthGolem() {

        super(
                "🪨 Earth Golem",
                "EARTH",
                180,
                15,
                20
        );
    }

    @Override
    public String useSkill() {

        return "🪨 Rock Smash";
    }
}