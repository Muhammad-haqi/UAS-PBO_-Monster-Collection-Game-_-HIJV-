package com.game.projekgame.model;

public class WaterTurtle extends Monster {

    public WaterTurtle() {

        super(
                "💧 Water Turtle",
                "WATER",
                150,
                18,
                15
        );
    }

    @Override
    public String useSkill() {

        return "🌊 Water Cannon";
    }
}