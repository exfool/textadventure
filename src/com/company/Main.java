package com.company;

import java.io.Serializable;

public class Main implements Serializable {

    public static void main(String[] args) {
        GameController game = GameController.getInstance();
        game.start();
    }
}
