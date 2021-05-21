public class Help
{
    String[] commands = {
            "Quit = quit the game",
            "OPEN = Open door/chest",
            "EXIT = Return to Main menu",
            "GO direction = Moves you to a specified direction",
            "LOOK = displays valuables around your character"
            ,
            "PUT = Puts an object in your inventory into a chest/door/valuable",
            "INVENTORY = Displays all your current inventory",
            "HELP = Displays this menu"
    };

    public void helpInstructionsDisplay()
    {
        for (int i = 0; i < commands.length; i++)
        {
            System.out.println(commands[i]);
        }

    }
}
