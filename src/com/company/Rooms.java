package com.company;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by exfool on 02.08.15.
 */
public class Rooms implements GetInfo, Serializable {
    private String name;
    // Each of fields can be null
    private Rooms east;
    private Rooms west;
    private Rooms north;
    private Rooms south;
    private Items item;
    private Monsters monster = new Monsters(1, 1, 1);

    public Rooms(String name) {
        this.name = name;
    }


    /**
     * Get profit from room after monster's killing
     */
    public double getMoney() {
        if (!monster.getHealth()) {
            Random rnd = new Random();
            return monster.getAttack() +
                    monster.getDefence() +
                    monster.getSkill() +
                    rnd.nextDouble() * 15;
        }
        return 0;
    }

    public double getSkill() {
        if (!monster.getHealth()) {
            return monster.getSkill() / 10;
        }
        return 0;
    }

    /**
     * Getters and Setters for all fiends
     * (name(getter only), monster, east, west, north, south)
     */
    public String getName() {
        return this.name = name;
    }

    public Rooms getEast() {
        return east;
    }

    public void setEast(Rooms east) {
        this.east = east;
    }

    public Rooms getWest() {
        return west;
    }

    public void setWest(Rooms west) {
        this.west = west;
    }

    public Rooms getNorth() {
        return north;
    }

    public void setNorth(Rooms north) {
        this.north = north;
    }

    public Rooms getSouth() {
        return south;
    }

    public void setSouth(Rooms south) {
        this.south = south;
    }

    public Monsters getMonster() {
        return monster;
    }

    public void setMonster(Monsters monster) {
        this.monster = monster;
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    @Override
    public String getInfo() {
        return "Room " + name + ", Monster - " + monster.getInfo();
    }
}
