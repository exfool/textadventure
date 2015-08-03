package com.company;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by exfool on 02.08.15.
 */
public class Shop implements GetInfo, Serializable {
    private int count = 10;
    private double from = 1;
    private double to = 10;
    private Items[] listItems = new Items[count];

    public Shop() {
        generateItems();
    }

    /**
     * Set params for shop's items
     *
     * @param count
     * @param from
     * @param to
     */
    public Shop(int count, double from, double to) {
        this.count = count;
        this.from = from;
        this.to = to;
        generateItems();
    }

    /**
     * Generate new stack items from shop
     */
    public void generateItems() {
        Random rnd = new Random();
        for (int i = 0; i < count; i++)
            listItems[i] = new Items(
                    "unknownitem" + i,
                    from + rnd.nextDouble() * to,
                    from + rnd.nextDouble() * to,
                    1 + rnd.nextInt(count * 10)
            );
    }

    /**
     * Show list available item's
     */
    public String showItems() {
        String info = "";
        for (int i = 0; i < count; i++) {
            info += "Id:" + i + " " + listItems[i].getInfo() + "\n";
        }
        return info;
    }

    public Items getItem(int id) {
        return listItems[id];
    }

    public Items getRandomItem() {
        Random rnd = new Random();
        return listItems[rnd.nextInt(10)];
    }

    @Override
    public String getInfo() {
        return "count items:" + count + " " +
                "cost from " + from + " " +
                "to " + to + " .";
    }
}
