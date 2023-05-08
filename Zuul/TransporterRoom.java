import java.util.ArrayList;
import java.util.Random;

/**
 * Class TransporterRoom - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "TransporterRoom" represents one location in the scenery of the game.
 * It is connected to other rooms via exits. Once inside this specific room,
 * when the player decides to leave that room, they will be randomly transported
 * to any of the other rooms in the game.
 *
 * @author Adam B
 * @version 3.0 March 13, 2023
 */
public class TransporterRoom extends Room{
    private Room randomRoom;
    private Random rand = new Random();
    private int roomSelector = rand.nextInt(5);

    public TransporterRoom() {
        super(".");
    }

    /**
     * setRooms randomizes an ArrayList of the game's rooms.
     * @param roomList
     */
    public void setRooms(ArrayList<Room> roomList) { randomRoom = roomList.get(roomSelector); }

    /**
     * Returns a random room, independent of the direction parameter.
     *
     * @param direction
     * @return A randomly selected room.
     */
    public Room getExit(String direction)
    {
        return findRandomRoom();
    }

    /**
     * Choose a random room.
     * @return The room we end up in upon leaving 
     */
    private Room findRandomRoom() { return randomRoom; }

}
