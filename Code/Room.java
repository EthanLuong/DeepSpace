import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Room
{
    private int    id;
    private String name;
    private String description;

    private ArrayList<Item>         items   = new ArrayList<>();
    private ArrayList<Path>         paths   = new ArrayList<>();
    private ArrayList<Enemy>        enemies = new ArrayList<>();
    private ArrayList<ControlPanel> panels  = new ArrayList<>();

    public static HashMap<Integer, Room> allRooms = new HashMap<>();

    private static boolean isFirstRunThrough = true;


    public Room(int id, String name, String description)
    {
        this.id          = id;
        this.name        = name;
        this.description = description;
        this.items       = new ArrayList<>();
        this.paths       = new ArrayList<>();
        this.enemies     = new ArrayList<>();
        this.panels      = new ArrayList<>();
        this.items       = new ArrayList<>();
        this.paths       = new ArrayList<>();

        allRooms.put(this.id, this);
    }

    public Room(Scanner roomScanner)
    {
        String line = StringParser.parseLine(roomScanner);  //id and name
        assert line != null;
        Scanner scanner = new Scanner(line);
        this.id = scanner.nextInt();
        scanner.skip("[ \t]*");
        this.name = scanner.nextLine();

        line = StringParser.parseLine(roomScanner); // num lines in desc
        assert line != null;
        scanner = new Scanner(line);
        int           numLines = scanner.nextInt();
        StringBuilder buffer   = new StringBuilder();
        for (int i = 0; i < numLines; i++)
        {
            buffer.append(StringParser.parseLine(roomScanner)).append("\n");
        }
        this.description = buffer.toString();


        allRooms.put(this.id, this);

        if (isFirstRunThrough)
        {
            new Room(0, "Exit", "Well done! You've made it out!");
            isFirstRunThrough  = false;
            Player.currentRoom = this;
        }
    }

    public int getid()
    {
        return this.id;
    }

    public static Room getRoomByID(int id)
    {
        return allRooms.get(id);
    }

    public void addPath(Path path)
    {
        this.paths.add(path);
    }

    public Room followRoom(String pathDirection)
    {
        for (Path path : paths)
        {
            if (path.match(pathDirection))
            {
                return path.travel();
            }
        }
        return this;
    }


    public void addItem(Item item)
    {
        this.items.add(item);
    }

    public Item removeItem(String itemName)
    {
        for (Item item : this.items)
        {
            if (item.match(itemName))
            {
                this.items.remove(item);
                System.out.println("You dropped the " + item.getName() + ".");
                return item;
            }
        }
        System.out.println("You do not have this item!!!");
        return null;
    }


    public void addEnemy(Enemy enemy)
    {
        this.enemies.add(enemy);
    }

    public Enemy removeEnemy(String enemyName)
    {
        for (Enemy enemy : this.enemies)
        {
            if (enemy.match(enemyName))
            {
                this.enemies.remove(enemy);
                return enemy;
            }
        }
        return null;
    }

    public int attackEnemy(String enemyName, int playerAttackDamage, int playerHealth)
    {
        int initialPlayerHealth = playerHealth;
        for (Enemy enemy : this.enemies)
        {
            if (enemy.match(enemyName))
            {
                boolean playerTurn = true;
                boolean someoneDie = false;
                while (!someoneDie)
                {
                    if (playerTurn)
                    {
                        enemy.looseHealth(playerAttackDamage);
                    }
                    else
                    {
                        playerHealth = playerHealth - enemy.getAttackDamage();
                    }
                    playerTurn = !playerTurn;
                    if ((enemy.getHealth() <= 0) || (playerHealth <= 0))
                    {
                        someoneDie = true;
                    }
                }
                if (enemy.getHealth() <= 0)
                {
                    int itemDropID = enemy.getItemDropID();
                    if (itemDropID > 0)
                    {
                        for (Item item : this.items)
                        {
                            if (itemDropID == item.getId())
                            {

                                this.items.remove(item);
                                item.setIsaDrop(0);
                                this.items.add(item);
                                System.out.println("As the " + enemyName + " dies, you see it drop something. . .");
                                System.out.println("You lost " +
                                                   (initialPlayerHealth - playerHealth) +
                                                   " from that battle");

                            }
                        }
                    }
                    enemy.deathDetails();
                    this.enemies.remove(enemy);
                }
                return playerHealth;
            }
        }
        return playerHealth;
    }

    public void addPanel(ControlPanel panel)
    {
        this.panels.add(panel);
    }

    public void usePanel(String panelname)
    {
        for (ControlPanel panel : this.panels)
        {
            if (panel.getName().equalsIgnoreCase(panelname))
            {
                if (panel.getIsUnlocked() == 0)
                {
                    this.panels.remove(panel);
                    panel.setIsUnlocked(1);
                    this.panels.add(panel);
                    System.out.println(panel.getName() + " Has been activated.");
                    int check = 0;
                    for (Room a : allRooms.values())
                    {
                        for (ControlPanel panel2 : a.panels)
                        {
                            if (panel2.getKeycode() == panel.getKeycode() &&
                                !panel2.getName().equalsIgnoreCase(panel.getName()))
                            {
                                if (panel2.getIsUnlocked() == 0)
                                {
                                    check = 1;
                                }
                            }
                        }
                    }
                    if (check == 0)
                    {
                        for (Room a : allRooms.values())
                        {
                            for (Path path : a.paths)
                            {
                                path.useCode(panel.getKeycode());
                            }
                        }
                    }
                }
            }
        }
    }

    public void useKey(Item key)
    {
        for (Path path : this.paths)
        {
            path.useKey(key);
        }
    }


    public Item pickUpItem(String name)
    {
        for (Item item : this.items)
        {
            if (name.equalsIgnoreCase(item.getName()))
            {
                this.items.remove(item);
                System.out.println("You got the " + item.getName() + "!!!\n");
                return item;
            }
        }
        System.out.println(("There is no such Item!!!\n"));
        return null;
    }

    public void details()
    {
        System.out.println("\n" + this.name);
        System.out.println(this.description);

        if (!this.items.isEmpty())
        {

            for (Item item : this.items)
            {
                if (item.isADrop() == 0)
                {
                    System.out.println("You can also see a few items in the room...\n");
                    item.details();
                }

            }
        }
        if (!this.enemies.isEmpty())
        {
            System.out.println("You see something reveal it self out of the shadows...\n");
            for (Enemy enemy : this.enemies)
            {
                if (enemy.getPersonality() == 0)
                {
                    enemy.details();
                }
                else if (enemy.getPersonality() == 1)
                {
                    enemy.details();
                    System.out.println(
                            "You are dragged into battle. There seems to be only two options. Run or fight, what do you choose?");

                }
            }
        }

        if (!this.panels.isEmpty())
        {
            for (ControlPanel panel : this.panels)
            {
                panel.getDetails();
            }
        }

    }
}
