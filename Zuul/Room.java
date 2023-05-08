import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author Adam B
 * @version 3.0 March 13, 2023
 */

public class Room 
{
    private ArrayList<Item> Items = new ArrayList<Item>();
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open courtyard".
     * 
     * @param description The room's description.
     */
    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    /**
     * Define an exit from this room.
     * 
     * @param direction The direction of the exit
     * @param neighbour The room to which the exit leads
     */
    public void setExit(String direction, Room neighbour) 
    {
        exits.put(direction, neighbour);
    }

    /**
     * Returns a short description of the room, i.e. the one that
     * was defined in the constructor
     * 
     * @return The short description of the room
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north, west
     *     
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        String rando = "";
        for (Item i: Items) {
             rando += i.itemInfo();
        }
        return "You are " + description + ".\n" + "Items,\n" + rando + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * 
     * @return Details of the room's exits
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction
     * @return The room in the given direction
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * 'addItem' adds an item to a room that you are currently in
     */
    public void addItem(Item addedItem) {Items.add(addedItem);}

    public ArrayList<Item> getItemsList() { return Items; }

    /**
     * Returns the list of items for the
     * player's current room
     *
     * @return The ArrayList 'Items'
     */
    public ArrayList<String> getItems() {
        ArrayList<String> itemNames = new ArrayList<String>();
        for (Item i: Items) {
            itemNames.add(i.getItem_name());
        }
        return itemNames; }
}
