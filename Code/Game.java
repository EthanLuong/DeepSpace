//Game class holds the actual game itself as launched, as well as houses the User Interface of the game.

import java.util.Scanner;

public class Game
{
    public static double version;
    private       String title;

    public static boolean gameOver;

    Player player;

    public Game(Scanner gameScanner)
    {
        player = new Player("Steve");

        gameOver = false;
        do
        {
            String line = StringParser.parseLine(gameScanner); //Version Title
            assert line != null;
            Scanner scanner = new Scanner(line);

            version = scanner.nextDouble();
            scanner.skip("[ \t]*");
            this.title = scanner.nextLine();

            line = StringParser.parseLine(gameScanner); //ROOMS
            assert line != null;
            scanner = new Scanner(line);

            String stringBuffer = scanner.next();
            int    intBuffer    = 0;
            if (stringBuffer.equalsIgnoreCase("rooms"))
            {
                intBuffer = scanner.nextInt();
            }
            for (int i = 0; i < intBuffer; i++)
            {
                new Room(gameScanner);
            }


            line = StringParser.parseLine(gameScanner); //PATHS
            assert line != null;
            scanner = new Scanner(line);

            stringBuffer = scanner.next();
            intBuffer    = 0;
            if (stringBuffer.equalsIgnoreCase("paths"))
            {
                intBuffer = scanner.nextInt();
            }
            for (int i = 0; i < intBuffer; i++)
            {
                new Path(gameScanner);
            }


            line = StringParser.parseLine(gameScanner); //ITEMS
            assert line != null;
            scanner = new Scanner(line);

            stringBuffer = scanner.next();
            intBuffer    = 0;
            if (stringBuffer.equalsIgnoreCase("items"))
            {
                intBuffer = scanner.nextInt();
            }
            for (int i = 0; i < intBuffer; i++)
            {
                new Item(gameScanner);
            }

            line = StringParser.parseLine(gameScanner); //ENEMIES
            assert line != null;
            scanner = new Scanner(line);

            stringBuffer = scanner.next();
            intBuffer    = 0;
            if (stringBuffer.equalsIgnoreCase("enemies"))
            {
                intBuffer = scanner.nextInt();
            }
            for (int i = 0; i < intBuffer; i++)
            {
                new Enemy(gameScanner);
            }

            line = StringParser.parseLine(gameScanner); //CONTROLPANELS
            assert line != null;
            scanner = new Scanner(line);

            stringBuffer = scanner.next();
            intBuffer    = 0;
            if (stringBuffer.equalsIgnoreCase("CONTROLPANEL"))
            {
                intBuffer = scanner.nextInt();
            }
            for (int i = 0; i < intBuffer; i++)
            {
                new ControlPanel(gameScanner);
            }


        }
        while (gameScanner.hasNextLine());
    }

    public void startGame()
    {
        System.out.println("Tip: If you ever get stuck with the controls of the game, Type 'help'!\n");

        System.out.println("You have awoken after 40 years in cryogenic sleep. . .");
        System.out.println("Your mind is awake, but your body aches.");
        System.out.println("You try to remember what the last thing you did was. . .");
        System.out.println("but there is no hope.");
        System.out.println("You get out of the pod in hopes to figuring out what happened...\n\n");


        do
        {

            player.getCurrentRoom().details();
            if (player.getCurrentRoom().getid() == 0)
            {
                Game.gameOver = true;
                return;
            }
            System.out.println("You decide to... ");
            player.makeMove();
        }
        while (!gameOver);
    }
}