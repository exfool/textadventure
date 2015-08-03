package com.company;

import java.io.*;
import java.util.Scanner;

/**
 * Created by exfool on 02.08.15.
 */
public class GameController implements Serializable {
    private ActionController action;
    private static Scanner in = new Scanner(System.in);
    private static GameController instance;

    private GameController() {
    }

    /**
     * Singleton,  get instance of game
     *
     * @return
     */
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    /**
     * This is the only point of entry
     * starting game controller,
     * call it after initialize game
     */
    public void start() {
        instance = null;
        prnt("Choose action:");
        prnt("1 - new  game");
        prnt("2 - load game");
        switch (in.nextInt()) {
            case 1:
                newGame();
                break;
            case 2:
                try {
                    load();
                    play();
                } catch (ClassNotFoundException e) {
                    prnt("Can't find load game, sorry.");
                    start();
                } catch (IOException e) {
                    prnt("Can't load game, sorry");
                    start();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Start play the game
     */
    private void play() {
        prnt("Your stats: " +
                        "skill - " + action.getPlayer().getSkill() + ", " +
                        "attack - " + action.getPlayer().getAttack() + ", " +
                        "defence - " + action.getPlayer().getDefence() + ", " +
                        "score - " + action.getPlayer().getScore() + "/" + action.getCountRooms()
        );
        if (!won()) {
            prnt("1 - move to room");
            prnt("2 - go to shop");
            prnt("3 - save game");
            prnt("4 - exit game");
            switch (in.nextInt()) {
                case 1:
                    nextRoom();
                    break;
                case 2:
                    goShop();
                    break;
                case 3:
                    try {
                        save();
                        prnt("Success save game.");
                    } catch (IOException e) {
                        e.printStackTrace();
                        prnt("Can't save game, sorry");
                    }
                    play();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        } else {
            start();
        }
    }

    //shop console menu
    private void goShop() {
        prnt("Welcome to best shop ever! If you want buy item, input it's id");
        prnt("Input \"-1\" to exit ");
        prnt("Your cash: " + action.getPlayer().getMoney());
        prnt("Select best item");
        prnt(action.getShop().showItems());
        int itemId = in.nextInt();

        if (itemId != -1) {
            if (action.getPlayer().withdrawMoney(action.getShop().getItem(itemId).getCost())) {
                action.getPlayer().changeItem(action.getShop().getItem(itemId));
                prnt("You wear " + action.getShop().getItem(itemId).getInfo() + ". Thank you.");
            } else {
                prnt("Sorry, you don't have money ... T_T");
            }
        } else {
            prnt("Good buy, come again. :)");
        }
        play();
    }

    /**
     * Move to next room
     */
    private void nextRoom() {
        prnt("Select the room:");
        if (action.getCurrentRoom().getNorth() != null) {
            prnt("1 - North, name room: " + action.getCurrentRoom().getNorth().getName());
        }
        if (action.getCurrentRoom().getSouth() != null) {
            prnt("2 - South, name room: " + action.getCurrentRoom().getSouth().getName());
        }
        if (action.getCurrentRoom().getEast() != null) {
            prnt("3 - East, name room: " + action.getCurrentRoom().getEast().getName());
        }
        if (action.getCurrentRoom().getWest() != null) {
            prnt("4 - West, name room: " + action.getCurrentRoom().getWest().getName());
        }

        //get room's info
        prnt("You choose ");
        int nextRoom = in.nextInt();
        switch (nextRoom) {
            case 1:
                prnt(action.getCurrentRoom().getNorth().getInfo());
                prnt("Attitude of your skill to skill a monster anyway as " +
                        action.getChange(action.getCurrentRoom().getNorth().getMonster()));
                break;
            case 2:
                prnt(action.getCurrentRoom().getSouth().getInfo());
                prnt("Attitude of your skill to skill a monster anyway as " +
                        action.getChange(action.getCurrentRoom().getSouth().getMonster()));
                break;
            case 3:
                prnt(action.getCurrentRoom().getEast().getInfo());
                prnt("Attitude of your skill to skill a monster anyway as " +
                        action.getChange(action.getCurrentRoom().getEast().getMonster()));
                break;
            case 4:
                prnt(action.getCurrentRoom().getWest().getInfo());
                prnt("Attitude of your skill to skill a monster anyway as " +
                        action.getChange(action.getCurrentRoom().getWest().getMonster()));
                break;
            default:
                break;
        }


        prnt("You want to choose this room ?");
        prnt("1 - yes");
        prnt("2 - no");
        //change current room, if you want it
        if (in.nextInt() == 1) {
            switch (nextRoom) {
                case 1:
                    action.setCurrentRoom(action.getCurrentRoom().getNorth());
                    break;
                case 2:
                    action.setCurrentRoom(action.getCurrentRoom().getSouth());
                    break;
                case 3:
                    action.setCurrentRoom(action.getCurrentRoom().getEast());
                    break;
                case 4:
                    action.setCurrentRoom(action.getCurrentRoom().getWest());
                    break;
                default:
                    break;
            }
            if (action.getCurrentRoom().getMonster().getHealth()) {
                //Fight with monster and if won them
                if (action.fight(action.getCurrentRoom().getMonster())) {
                    prnt("You won this monster!");
                    prnt("you pick up " + action.getCurrentRoom().getMoney() + " money");
                    prnt("and your skill up on " + (int) action.getCurrentRoom().getSkill());

                    action.getCurrentRoom().getMonster().die();
                    action.getPlayer().depositMoney(action.getCurrentRoom().getMoney());
                    action.getPlayer().depositSkill(action.getCurrentRoom().getSkill());
                    action.getPlayer().addScore();

                    //If room have random item after fight
                    if (action.getCurrentRoom().getItem() != null) {
                        prnt("You find new item, you want to wear it ?");
                        prnt(action.getCurrentRoom().getItem().getInfo());
                        prnt("1 - yes");
                        prnt("2 - no");

                        if (in.nextInt() == 1) {
                            action.getPlayer().changeItem(action.getCurrentRoom().getItem());
                        }
                    }

                    play();
                } else { // you losing monster
                    action.getPlayer().die();
                    lose();
                }

            } else {
                prnt("You was here... monster is dead.");
                play();
            }
        } else {
            play();
        }

    }

    private void lose() {
        prnt("Oh, no! You are looooser...");
        action = null;
        start();
    }

    private boolean won() {
        if (action.getPlayer().getScore() >= action.getCountRooms()) {
            prnt("WOOOOOOOOOON. You awesome, bro! Good job :)");
            start();
            return true;
        }
        return false;
    }

    /**
     * Create new action controller with custom settings
     */
    private void newGame() {
        prnt("Choose mode game");
        prnt("1 - easy game");
        prnt("2 - normal game");
        prnt("3 - hard game");
        int hard = in.nextInt();
        if (hard < 1 || hard > 3) {
            hard = 1;
        }


        prnt("Enter count of rooms");
        prnt("min: 10, max: 100):");
        int countRooms = in.nextInt();
        if (countRooms < 10 || countRooms > 100) {
            countRooms = 10;
        }

        this.action = ActionController.getInstance(hard, countRooms);
        play();
    }

    /**
     * Save game
     */
    private void save() throws IOException {
        FileOutputStream fos = new FileOutputStream("save.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(ActionController.getInstance());
        oos.flush();
        fos.close();
        oos.close();
    }

    /**
     * Recover saved game
     */
    private void load() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("save.out");
        ObjectInputStream oin = new ObjectInputStream(fis);
        this.action = (ActionController) oin.readObject();
        fis.close();
        oin.close();
    }

    /**
     * Print custom message
     */
    private void prnt(String msg) {
        System.out.println(msg);
    }

}
