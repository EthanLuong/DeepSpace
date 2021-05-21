package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Room
{
    private int    id;
    private String name;
    private String description;
    private String pictureSource;

    private ArrayList<Item>         items   = new ArrayList<>();
    private ArrayList<Path>         paths   = new ArrayList<>();
    private ArrayList<Enemy>        enemies = new ArrayList<>();
    private ArrayList<ControlPanel> panels  = new ArrayList<>();

    public static HashMap<Integer, Room> allRooms = new HashMap<>();

    private static boolean isFirstRunThrough = true;



    public Room(int id, String name, String description, String pictureSource)
    {
        this.id          = id;
        this.name        = name;
        this.description = description;
        this.pictureSource = pictureSource;
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
        System.out.println("New Room");
        String line = StringParser.parseLine(roomScanner);  //id and name
        assert line != null;
        Scanner scanner = new Scanner(line);
        this.id = scanner.nextInt();
        scanner.skip("[ \t]*");
        this.name = scanner.nextLine();


        line = StringParser.parseLine(roomScanner);
        this.pictureSource = line;

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

            new Room(0, "Exit", "Well done! You've made it out!","escapePod.jpg");

            isFirstRunThrough  = false;
            Player.currentRoom = this;
        }
    }


    public String getpictureSource() { return this.pictureSource; }

    public ArrayList<Enemy> getallEnemies(){ return this.enemies;}


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

    public Item removeItem(String itemName, Player player)
    {
        for (Item item : player.getInventory())
        {
            if (item.match(itemName))
            {
                this.items.add(item);
                player.getInventory().remove(item);
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

    public String attackEnemy(String enemyName, Player player)
    {
        String returnValue = null;
        int initialPlayerHealth = player.getCurrentHealth();
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
                        enemy.looseHealth(player.getCurrentattackDamage() + player.getAttackDamage() + player.getTemporaryDamage());
                    }
                    else
                    {
                        player.setCurrentHealth(player.getCurrentHealth() - enemy.getAttackDamage());
                    }
                    playerTurn = !playerTurn;
                    if ((enemy.getHealth() <= 0) || (player.getCurrentHealth() <= 0))
                    {
                        someoneDie = true;
                    }
                }
                if (enemy.getHealth() <= 0)
                {
                    returnValue = enemy.deathDetails();
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
                                                   (initialPlayerHealth - player.getCurrentHealth()) +
                                                   " from that battle");
                                returnValue += "As the " + enemyName + " dies, you see it drop something. . .\n" + "You lost " +
                                        (initialPlayerHealth - player.getCurrentHealth()) +
                                        " from that battle";

                            }
                        }
                    }

                    this.enemies.remove(enemy);
                }
                return returnValue;
            }
        }
        return returnValue;
    }

    public void addPanel(ControlPanel panel)
    {
        this.panels.add(panel);
    }

    public String usePanel(String panelname)
    {
        String returnVal = null;
        for (ControlPanel panel : this.panels)
        {
            if (panel.getName().equalsIgnoreCase(panelname))
            {
                if (panel.getIsUnlocked() == 0)
                {
                    this.panels.remove(panel);
                    panel.setIsUnlocked(1);
                    this.panels.add(panel);
                    returnVal = panel.getName() + " Has been activated.";
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
                                String pathReturn;
                                pathReturn = path.useCode(panel.getKeycode());
                                if(pathReturn != null){
                                    returnVal += pathReturn;
                                }
                            }
                        }
                    }
                }
            }
        }
        return returnVal;
    }

    public String useKey(Item key)
    {
        String x = null;
        for (Path path : this.paths)
        {
            System.out.println(x);
            if(x == null){
                x = path.useKey(key);
            } else{
                break;
            }

        }
        return x;
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


    public ArrayList<Item> checkItems() {
        if(!this.items.isEmpty()){
            return this.items;
        }
        return null;
    }

    public ArrayList<Enemy> checkEnemies(){
        if(!this.enemies.isEmpty()){
            return this.enemies;
        }
        return null;
    }

    public ArrayList<ControlPanel> checkPanel(){
        if(!this.panels.isEmpty()){
            return this.panels;
        }
        return null;
    }

    public String getDescription(){
        return this.description;
    }

    public String getName(){
        return this.name;
    }
}
