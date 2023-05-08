import java.util.ArrayList;

/**
 * Class Item - an item in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * An "Item" represents items in each room. Items can be picked up and dropped
 * in any of the rooms. There is no limit to the amount of items that a user places
 * in a room. However, the user may only pick up and hold one item at a time.
 *
 * @author Adam B
 * @version 3.0 March 13, 2023
 */

public class Item {
    private String item_name;
    private double weight;
    public Item() {
        item_name = "";
        weight = 0;
    }

    public Item(double the_weight, String description) {
        item_name = description;
        weight = the_weight;
    }

    /**
     * Returns the details of the items of
     * a room that you are currently in
     * @return Description and weight of an item
     */
    public String itemInfo() {
        return "      " + item_name + " that weighs " + weight + " lbs" + "\n";
    }
    public String getItem_name() { return item_name; }
    public double getWeight() { return weight; }

    /**
     * Used for the 'take' method
     * @param item_Name
     * @return Either the item the user wishes to pick up
     * or nothing if it isn't found in the room
     */
    public String name(String item_Name) {
        Room currRoom = Game.getCurrentRoom();

        ArrayList<String> nearby_items = currRoom.getItems();

        boolean found = false;
        if (nearby_items.contains(item_Name)) {
            found = true;
        }
        if (found) { return item_Name; }
        else { return null; }
    }
}