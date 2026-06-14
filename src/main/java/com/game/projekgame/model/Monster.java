package com.game.projekgame.model;

public abstract class Monster {

    private String name;

    private String element;

    private int hp;

    private int attack;

    private int defense;

    public Monster(
            String name,
            String element,
            int hp,
            int attack,
            int defense
    ) {

        this.name = name;
        this.element = element;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public String getElement() {
        return element;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    // POLYMORPHISM

    public abstract String useSkill();

    @Override
    public String toString() {

        return name;
    }
}