package com.company;

import java.io.Serializable;

/**
 * Created by exfool on 02.08.15.
 */
public class Player implements Serializable {
    private boolean health;
    private double attack;
    private double defence;
    private double skill;
    private double money;
    private Items item;
    private int score = 0;

    /**
     * Set params for player's ability
     */
    public Player(double attack, double defence, double skill, double money) {
        this.health = true;
        this.attack = attack;
        this.defence = defence;
        this.skill = skill;
        this.money = money;
    }

    /**
     * Change owner item to new item
     */
    public void changeItem(Items item) {
        if (this.item != null) {
            withdrawAttack(this.item.getAttack());
            withdrawDefence(this.item.getDefence());
        }
        this.item = item;
        depositAttack(item.getAttack());
        depositDefence(item.getDefence());
    }

    /**
     * Check player's health
     */
    public boolean check() {
        return health;
    }

    /**
     * If player lose, kill them
     */
    public void die() {
        health = false;
    }

    /**
     * 3 method for interaction with attack
     */

    public void withdrawAttack(double attack) {
        this.attack -= attack;
    }

    public void depositAttack(double attack) {
        this.attack += attack;
    }

    public double getAttack() {
        return this.attack;
    }

    /**
     * 3 method for interaction with defence
     */

    public void withdrawDefence(double defence) {
        this.defence -= defence;
    }

    public void depositDefence(double defence) {
        this.defence += defence;
    }

    public double getDefence() {
        return this.defence;
    }

    /**
     * 3 method for interaction with skill
     */

    public void withdrawSkill(double skill) {
        this.skill -= skill;
    }

    public void depositSkill(double skill) {
        this.skill += skill;
    }

    public double getSkill() {
        return this.skill;
    }

    /**
     * 3 method for interaction with money
     */

    public boolean withdrawMoney(double money) {
        if (this.money >= money) {
            this.money -= money;
            return true;
        }
        return false;
    }

    public void depositMoney(double money) {
        this.money += money;
    }

    public double getMoney() {
        return this.money;
    }

    /**
     * Get info about player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Increment score
     */
    public void addScore() {
        this.score++;
    }

    /**
     * Get item's info to compare with new
     */
    public Items getItem() {
        return this.item;
    }
}
