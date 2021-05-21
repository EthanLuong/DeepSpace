package main;//Pregame class will parse map file and initialize all relevant classes before launching the game

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PreGame
{
    static public Scanner gameScanner;
    static public String  gamePath;
    static public File    gameFile;

    public boolean start(String input){
        gameFile = new File(input);
        gameScanner = null;
        System.out.println("Here");
        try
        {
            gameScanner = new Scanner(gameFile);
            //begin Game
            Game game = new Game(gameScanner);
            gameScanner.close();
            return true;
        }
        catch (FileNotFoundException e)
        {
            System.err.println("File not found: " + gamePath);
            return false;
        }



    }
}
