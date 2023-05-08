/**
 * Class Beamer - an item, more specifically, a transportation device, in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Beamer" represents an item that the user can pick up.
 * User can charge and fire the device. When fired, the user is
 * teleported to the room where the beamer was charged in.
 *
 * @author Adam B
 * @version 3.0 March 13, 2023
 */

public class Beamer extends Item{
    private boolean holding = false;
    private boolean charged = false;
    private Room roomChargedIn;

    public Beamer() {
    }

    /**
     * takeBeamer sets the boolean variable 'holding' to true
     */
    public void takeBeamer() { holding = true; }

    /**
     * dropBeamer sets the boolean variable 'holding' to false
     */
    public void dropBeamer() { holding = false; }

    /**
     * Charges up the beamer and saves the room
     * where the user charged it up in.
     * @param chargedIn
     */
    public void chargeBeamer(Room chargedIn) {
        charged = true;
        roomChargedIn = chargedIn;
        System.out.println("The beamer has now been charged!");
    }

    /**
     * Fires the beamer and teleports user to
     * the room where it had been charged in.
     */
    public void fireBeamer() {
        charged = false;
        Game.beamerRoom(roomChargedIn);
    }

    /**
     * 'isCharged' checks if the beamer is charged or not
     * @return the boolean 'charged'
     */
    public boolean isCharged() { return charged; }

    /**
     * 'isHolding' is used to check if player
     * is holding a beamer or not.
     * @return the boolean 'holding'
     */
    public boolean isHolding() { return holding; }
}
