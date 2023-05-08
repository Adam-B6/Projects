import java.util.ArrayList;
import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author Adam B
 * @version 3.0 March 13, 2023
 */

public class Game 
{
    private Parser parser;
    private static Room currentRoom;
    private static Room previousRoom;
    private static Stack<Room> roomStack = new Stack<>();
    private ArrayList<String> player_inventory = new ArrayList<String>();
    private ArrayList<Item> playerObjInventory = new ArrayList<Item>();
    private ArrayList<String> holster = new ArrayList<String>();
    private int hunger = 5;
    Beamer beamer = new Beamer();
    TransporterRoom transportation = new TransporterRoom();

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office, transporterRoom;
        Item outsideItem1, outsideItem2, theatreItem1, theatreItem2, pubItem1,
                pubItem2, labItem1, labItem2, officeItem1, officeItem2, cookie,
                beamer1, beamer2, transporterroomItem;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        transporterRoom = new Room("in a transporter room...");

        ArrayList<Room> allRooms = new ArrayList<>();
        allRooms.add(outside); allRooms.add(theatre); allRooms.add(pub); allRooms.add(lab); allRooms.add(office);
        transportation.setRooms(allRooms);
        
        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);
        theatre.setExit("north", transporterRoom);

        transporterRoom.setExit("anywhere", transportation.getExit("anywhere"));

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        // create items for each room
        outsideItem1 = new Item(21000.5,"Tree");
        outsideItem2 = new Item(102.7, "Bike");
        theatreItem1 = new Item(60.3, "Painting");
        theatreItem2 = new Item(17.2, "Plant");
        pubItem1 = new Item(12.6, "Tequila");
        pubItem2 = new Item(190.4, "Drunk-guy");
        labItem1 = new Item(2.8, "Wires");
        labItem2 = new Item(9.3, "Computer");
        officeItem1 = new Item(18.1, "Exams");
        officeItem2 = new Item(30.7, "Chairs");
        cookie = new Item(0.44, "Cookie");
        beamer1 = new Item(10.0, "Beamer");
        beamer2 = new Item(9.0, "Beamer");
        transporterroomItem = new Item(0.1, "Atoms");

        // initialise items for each room
        outside.addItem(outsideItem1);
        outside.addItem(outsideItem2);

        theatre.addItem(theatreItem1);
        theatre.addItem(theatreItem2);
        theatre.addItem(cookie);

        transporterRoom.addItem(cookie);
        transporterRoom.addItem(transporterroomItem);

        pub.addItem(pubItem1);
        pub.addItem(pubItem2);
        pub.addItem(beamer1);

        lab.addItem(labItem1);
        lab.addItem(labItem2);

        office.addItem(officeItem1);
        office.addItem(officeItem2);
        office.addItem(cookie);
        office.addItem(beamer2);

        previousRoom = null;
        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a simple adventure game.");
        System.out.println("This is a case-sensitive game.");
        System.out.println("Type 'help' if you need help.\n");
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look(command);
        }
        else if (commandWord.equals("eat")) {
            eat(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
        else if (commandWord.equals("stackBack")) {
            stackBack(command);
        }
        else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        } else if (commandWord.equals("charge")) {
            charge(command);
        } else if (commandWord.equals("fire")) {
            fire(command);
        } else if (commandWord.equals("stats")) {
            playerInfo();
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("You are lost. You are alone. You wander around at a university.\n");
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
        System.out.println();
        System.out.println("In this version of the game, the user has an inventory for items " +
                "and a device holster.");
        System.out.println("The player can only hold two items in the item related inventory and only one device in the holster.");
        System.out.println("Thus, a maximum of 3 items can be held at once.\n");
        System.out.println("You can only pick-up and drop (consider both as a whole) an item 5 times before you need to eat.");
        System.out.println("To drop a device type 'drop' followed by the name of the device.");
        System.out.println("To drop an item simply type 'drop'.\n");
        System.out.println("For player related info, type the command word 'stats'.");
        System.out.println("-----------------------------------------------------------------------------------------------------");
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            roomStack.push(currentRoom);
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            //System.out.println(currentRoom.getItems()); //testing purposes
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    public static Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * 'Look' prints the information to the room currently in
     *
     * @param command The command to be processed
     */
    private void look(Command command)
    {
        if (command.hasSecondWord()) {
            System.out.println("Look where?");
        }
        else {
            System.out.println(currentRoom.getLongDescription());
            System.out.println("Player Inventory: " + player_inventory);
        }
    }

    /**
     * 'Eat' prints a message to indicate the user they have eaten
     *
     * @param command The command to be processed
     */
    private void eat(Command command)
    {
        if (command.hasSecondWord()) {
            System.out.println("Eat what?");
        } else if (!player_inventory.contains("Cookie")) {
            System.out.println("You don't have a cookie to eat. Go find one!");
        } else {
            hunger = 5;
            player_inventory.clear();
            System.out.println("You have eaten and are no longer hungry.");}
    }

    /**
     * 'Back' brings the user to the last visited room
     *
     * @param command The command to be processed
     */
    private void back(Command command)
    {
        if (command.hasSecondWord()) {
            System.out.println("Back where?");
        } else if (previousRoom == null) {
            System.out.println("There's no where to go back to!");
        }
        // Go to previous room
        else {
            roomStack.push(currentRoom);
            Room saver = currentRoom;
            currentRoom = previousRoom;
            previousRoom = saver;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * stackBack brings the user to the last visited room
     * from a stack which saves the user's visited rooms
     *
     * @param command The command to be processed
     */
    private void stackBack(Command command) {
        if (roomStack.size() == 0) {
            System.out.println("There's no where to go back to!");
        }
        else {
            previousRoom = currentRoom;
            currentRoom = roomStack.peek();
            roomStack.pop();
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * 'Take' allows user to pick up an item.
     *
     * @param command The command to be processed
     */
    private void take(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }
        String item_taking = item_to_take(command);
        if (item_taking == null) {
            System.out.println("That item is not in the room."); return;
        }
        if (hunger!=0 && item_taking.equals("Beamer") && !beamer.isHolding()) {
            beamer.takeBeamer();
            holster.add(item_taking);
            removeRoomItem(item_taking);
            System.out.println("You have now equipped a Beamer.");
            System.out.println("Hmm, I wonder what this does.");
        } else if (hunger!=0 && player_inventory.size() < 2 && !item_taking.equals("Beamer")){
            // Only pick up if player has enough hunger, and enough inventory.
            // if the item is found, and it's not a beamer.
            player_inventory.add(item_taking);
            System.out.println("You are now holding: " + item_taking);
            removeRoomItem(item_taking);
        } else if (hunger == 0 && item_taking.equals("Cookie")) {
            player_inventory.add(item_taking);
            removeRoomItem(item_taking);
            System.out.println("You are now holding: Cookie");
        } else if (hunger == 0){
            System.out.println("You cannot pick up anything because " +
                    "you're hungry, maybe you should eat up.");
        } else {
            System.out.println("Your inventory is full.");
        }
    }

    /**
     * 'item_to_take' calls to 'name' method in Class Item
     * to search for the item in the room.
     * @param command
     * @return the name of the item as a String
     */
    private String item_to_take(Command command) {
        Item the_item = new Item();
        return the_item.name(command.getSecondWord());
    }

    /**
     * 'removeRoomItem' removes the item they're picking up from the room
     * @param item
     */
    private void removeRoomItem(String item) {
        ArrayList<Item> roomItems = currentRoom.getItemsList();
        int index = 0;
        int i = 0;
        for (Item thing: roomItems) {
            if ((thing.getItem_name()).equals(item)){
                index = i;
                break;
            }
            i++;
        }
        playerObjInventory.add(roomItems.get(index));
        roomItems.remove(index);
    }

    /**
     * 'Drop' allows user to drop the item that they are holding.
     *
     * @param command The command to be processed
     */
    private void drop(Command command) {
        if (!command.hasSecondWord() && player_inventory.size() == 0) {
            System.out.println("There is nothing to drop!"); return;
        } else if (!command.hasSecondWord() && player_inventory.get(0).equals("Cookie") && hunger == 0) {
            System.out.println("You must eat your Cookie!");
            return;
        } else if (!command.hasSecondWord()) {
            hunger -= 1;
            dropItemInRoom();
            System.out.println("You have dropped: " + player_inventory.get(0));
            player_inventory.remove(0);
            playerObjInventory.remove(0);
            System.out.println("Your inventory is now empty.");
            return;
        }
        if (command.getSecondWord().equals("Beamer") && beamer.isHolding()) {
            hunger -= 1;
            beamer.dropBeamer();
            dropItemInRoom();
            System.out.println("You have dropped the Beamer");
            holster.clear();
            System.out.println("Your inventory is now empty.");
            return;
        } else if (!command.getSecondWord().equals("Beamer")) {
            System.out.println("drop huh?");
        }
    }

    /**
     * 'dropItemInRoom' drops the user's item in the room they're in
     */
    private void dropItemInRoom() {
        String name = playerObjInventory.get(0).getItem_name();;
        Double weight = playerObjInventory.get(0).getWeight();
        Item tempItem = new Item(weight, name);
        currentRoom.addItem(tempItem);
    }

    /**
     * 'charge' is used when the user wants to charge their beamer.
     * @param command
     */
    private void charge(Command command) {
        if (command.hasSecondWord()) { System.out.println("Just say charge :)");
        } else if (beamer.isHolding() == false) {
            System.out.println("You have nothing that can be charged up.");
        } else if (beamer.isCharged()) {
            System.out.println("Beamer is already charged.");
        } else {
            beamer.chargeBeamer(currentRoom); }
    }

    /**
     * 'fire' is used when the user wants to fire their beamer.
     * @param command
     */
    private void fire(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Just say fire :)");
        } else if (beamer.isHolding() == false) {
            System.out.println("You have nothing to fire.");
        } else if (beamer.isCharged() == false) {
            System.out.println("Your beamer isn't charged.");
        } else { beamer.fireBeamer(); }
    }

    /**
     * 'beamerRoom' sends the player to the room where
     * the beamer was charged in.
     * @param gotoRoom
     */
    public static void beamerRoom(Room gotoRoom) {
        roomStack.push(currentRoom);
        previousRoom = currentRoom;
        currentRoom = gotoRoom;
        System.out.println(currentRoom.getLongDescription());
        System.out.println("Woah, what happened!?");
    }

    /**
     * 'playerInfo' prints info about the user
     */
    private void playerInfo() {
        System.out.println("-----------------------------");
        System.out.println("Player Inventory: " + player_inventory);
        System.out.println("Device holster: " + holster);
        System.out.println("Hunger level out of 5: " + Integer.toString(hunger));
        System.out.println("-----------------------------");
    }
}
