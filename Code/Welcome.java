import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Welcome
{

    public void welcomeMessage()
    {

        System.out.println("Welcome to Deep Space");
        System.out.println("This game tests your skill on:");
        System.out.println("1. Solving puzzles");
        System.out.println("2. Problem Solving");
        System.out.println("3. How good of an adventurer you are");
        System.out.println("");
        System.out.println("The goal is simple:");
        System.out.println("Traverse your way though the spaceship");
        System.out.println("And MAKE IT OUT!!! ");
        System.out.println("");
        System.out.println("When you are ready, type any of the options:");
        System.out.println("1. Start");
        System.out.println("2. Help");
        System.out.println("3. Quit");

    }

    public boolean mainMenu(boolean gameStarted)
    {
        Scanner sc                 = new Scanner(System.in);
        String  userInput;
        Help    listofHelpcommands = new Help();

        while (gameStarted != true)
        {
            userInput = sc.nextLine();
            if (userInput.equalsIgnoreCase("Start") || userInput.equals("1"))
            {
                return true;
            }
            else if (userInput.equalsIgnoreCase("Quit") || userInput.equals("3"))
            {
                System.exit(0);
            }
            else if (userInput.equalsIgnoreCase("Help") || userInput.equals("2"))
            {
                listofHelpcommands.helpInstructionsDisplay();
            }
            else
            {
                System.out.println("Invalid Input, Try again.");
            }
        }
        return gameStarted;
    }


}
