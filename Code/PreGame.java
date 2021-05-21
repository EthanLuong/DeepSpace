//Pregame class will parse map file and initialize all relevant classes before launching the game

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PreGame
{
    static public Scanner gameScanner;
    static public String  gamePath;
    static public File    gameFile;

    public static void main(String[] args)
    {

        //UI.initializeUI();
        Scanner input = new Scanner(System.in);


        if (args.length >= 1)
        {
            gamePath = args[0];
            gameFile = new File(gamePath);
        }
        else
        {
            System.out.println("Please enter the name of the game map: ");
            gamePath = input.nextLine();
            gameFile = new File(gamePath);

            while (!gameFile.exists())
            {
                System.out.println("Game name invalid. Please enter the name of the game map: ");
                gamePath = input.nextLine();
                gameFile = new File(gamePath);
            }
        }

        gameScanner = null;

        try
        {
            gameScanner = new Scanner(gameFile);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("File not found: " + gamePath);
            System.exit(-1);
        }


        //begin Game
        Game game = new Game(gameScanner);

        gameScanner.close();

        game.startGame();
    }
}
