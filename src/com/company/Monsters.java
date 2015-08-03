package com.company;

import java.io.Serializable;

/**
 * Created by exfool on 02.08.15.
 */
public class Monsters implements GetInfo, Serializable {
    private boolean health;
    private double attack;
    private double defence;
    private double skill;

    public Monsters(double attack, double defence, double skill) {
        this.health = true;
        this.attack = attack;
        this.defence = defence;
        this.skill = skill;
    }

    /**
     * Killing monster
     */
    public void die() {
        this.health = false;
    }

    /**
     * Check monster health
     */
    public boolean getHealth() {
        return this.health;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefence() {
        return defence;
    }

    public double getSkill() {
        return skill;
    }

    @Override
    public String getInfo() {
        return "health: " + health + ", " +
                "attack: " + (int) attack + ", " +
                "defence: " + (int) defence + ", " +
                "skill: " + (int) skill;
    }
}
