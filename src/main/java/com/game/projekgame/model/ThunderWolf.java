package com.game.projekgame.model;

public class ThunderWolf extends Monster {

    public ThunderWolf() {

        super(
                "⚡ Thunder Wolf",
                "THUNDER",
                100,
                30,
                8
        );
    }

    @Override
    public String useSkill() {

        return "⚡ Thunder Strike";
    }
}