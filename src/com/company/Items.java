package com.company;

import java.io.Serializable;

/**
 * Created by exfool on 02.08.15.
 */
public class Items implements GetInfo, Serializable{
    private String name;
    private double attack;
    private double defence;
    private double cost;

    public Items(String name, double attack, double defence, double cost ){
        this.name = name;
        this.attack = attack;
        this.defence = defence;
        this.cost = cost;
    }


    public String getName() {
        return name;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefence() {
        return defence;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String getInfo() {
        return
                name + " " +
                "attack: " + (int)attack + " " +
                "defence: " + (int)defence + " " +
                "cost: " + (int)cost;
    }
}
