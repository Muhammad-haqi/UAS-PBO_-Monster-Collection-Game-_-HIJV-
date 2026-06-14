package com.game.projekgame.model;

public class IcePhoenix extends Monster {

    public IcePhoenix() {

        super(
                "❄ Ice Phoenix",
                "ICE",
                110,
                28,
                12
        );
    }

    @Override
    public String useSkill() {

        return "❄ Frozen Storm";
    }
}