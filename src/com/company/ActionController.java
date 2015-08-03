package com.company;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by exfool on 02.08.15.
 */
public class ActionController implements Serializable {
    private static ActionController instance;
    private Rooms currentRoom;
    private Random rnd = new Random();

    //settings
    private Player player = new Player(10, 10, 1000, 10);
    private Shop shop = new Shop(10, 1, 10);
    private int countRooms = 10;
    private int hard = 1;

    private ActionController(int hard, int countRooms) {
        this.countRooms = countRooms;
        this.hard = hard;
        generateLabyrinth();
    }

    /**
     * Init ActionContoller with params
     */
    public static ActionController getInstance(int hard, int countRooms) {
        if (instance == null) {
            instance = new ActionController(hard, countRooms);
        }
        return instance;
    }

    /**
     * Get instance without params
     */
    public static ActionController getInstance() {
        return instance;
    }

    /**
     * Generate new labyrinth for action controller
     */
    public void generateLabyrinth() {

        /**
         * Generate some randoms Monsters for rooms
         */
        Monsters[] monster = new Monsters[countRooms];
        for (int i = 0; i < countRooms; i++) {
            monster[i] = new Monsters(
                    rnd.nextDouble() * 10 + 1,
                    rnd.nextDouble() * 10 + 1,
                    rnd.nextDouble() * 10 + 1
            );
        }

        /**
         * Genetare random rooms with monsters
         */
        Rooms[] room = new Rooms[countRooms];
        for (int i = 0; i < countRooms; i++) {
            room[i] = new Rooms("unknownRoom" + i);
            room[i].setMonster(monster[i]);
            if (rnd.nextInt(100) < 50) {
                room[i].setItem(shop.getRandomItem());
            }
        }

        /**
         * Connecting rooms with each other
         * North to South
         * East to West
         */

        //init and connect first room with labyrinth
        currentRoom = new Rooms("startRoom");
        currentRoom.setNorth(room[0]);
        room[0].setSouth(currentRoom);

        for (int i = 0; i < countRooms - 1; i++) {
            int rint;
            //North to South
            rint = rnd.nextInt(countRooms);
            if (room[i].getNorth() == null) {
                while (room[rint].getSouth() != null) {
                    rint = rnd.nextInt(countRooms);
                }
                room[i].setNorth(room[rint]);
                room[rint].setSouth(room[i]);
            }
            //East to West
            rint = rnd.nextInt(countRooms);
            if (room[i].getEast() == null) {
                while (room[rint].getWest() != null) {
                    rint = rnd.nextInt(countRooms);
                }
                room[i].setEast(room[rint]);
                room[rint].setWest(room[i]);
            }

        }


    }

    /**
     * Buy item from shop
     */
    public boolean buyItem(int id) {
        Items item = shop.getItem(id);
        if (player.withdrawMoney(item.getCost())) {
            player.changeItem(item);
            return true;
        }
        return false;
    }

    /**
     * Move to next room
     *
     * @param room
     */
    public void moveToRoom(Rooms room) {
        this.currentRoom = room;
    }

    /**
     * Get change to fight with monster
     */
    public double getChange(Monsters monster) {
        return player.getSkill() / monster.getSkill();
    }

    /**
     * Fight with monster
     *
     * @return Won - true / Lose - false
     */
    public boolean fight(Monsters monster) {
        if (rnd.nextInt(100) < getChange(monster)) {
            return true;
        }
        return false;
    }

    public Rooms getCurrentRoom() {
        return this.currentRoom;
    }

    public void setCurrentRoom(Rooms room) {
        this.currentRoom = room;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCountRooms() {
        return countRooms;
    }

    public Shop getShop() {
        return this.shop;
    }
}
