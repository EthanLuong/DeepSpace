//Enemy class consists of the different type of enemies in the game
package main;
import java.util.Scanner;


public class Enemy {

    private int id;
    private int health;
    private int personality;
    private String name;
    private String description;
    private String death;
    private String imageLocation;
    private int attackDamage;
    private int itemDropID;


    public Enemy(int id, int health, int personality, String name, String description, String death, int attackDamage, int itemDropID, String imageLocation) {
        this.id = id;
        this.health = health;
        this.name = name;
        this.personality = personality;
        this.description = description;
        this.death = death;
        this.imageLocation = imageLocation;
        this.attackDamage = attackDamage;
        this.itemDropID = itemDropID;
    }

    public Enemy(Scanner itemScanner) {
        String line = StringParser.parseLine(itemScanner);  //location
        assert line != null;
        Scanner scanner = new Scanner(line);

        int location = scanner.nextInt();

        line = StringParser.parseLine(itemScanner); //  id  health  attackDamage    itemdropID  personality name
        assert line != null;
        scanner = new Scanner(line);

        this.id = scanner.nextInt();
        this.health = scanner.nextInt();
        this.attackDamage = scanner.nextInt();
        this.itemDropID = scanner.nextInt();
        this.personality = scanner.nextInt();
        scanner.skip("[ \t]*");
        this.name = scanner.nextLine();

        line = StringParser.parseLine(itemScanner);
        this.imageLocation = line;

        line = StringParser.parseLine(itemScanner); //  lines-in-description
        assert line != null;
        scanner = new Scanner(line);

        int numLines = scanner.nextInt();
        StringBuilder buffer = new StringBuilder();   //description
        for (int i = 0; i < numLines; i++)
        {
            buffer.append(itemScanner.nextLine());
        }
        this.description = buffer.toString();


        line = StringParser.parseLine(itemScanner); //  lines-in-death
        assert line != null;
        scanner = new Scanner(line);

        int numLines2 = scanner.nextInt();
        StringBuilder buffer2 = new StringBuilder();   //death
        for (int i = 0; i < numLines2; i++)
        {
            buffer2.append(itemScanner.nextLine());
        }


        this.death = buffer2.toString();

       Room.allRooms.get(location).addEnemy(this);





    }


    public String getImageLocation(){ return this.imageLocation;}

    public boolean match(String enemyName)
    {
        return enemyName.equalsIgnoreCase(this.name);
    }

    public void setPersonality(int personality) {
        this.personality = personality;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public int getPersonality() {
        return personality;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getItemDropID() {
        return itemDropID;
    }

    public void setItemDropID(int itemDropID) {
        this.itemDropID = itemDropID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void looseHealth(int health){

        this.health -= health;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String deathDetails()
    {
        return(this.death + "\n");
    }

    public String details()
    {
        System.out.println(this.description);
        System.out.println("| Name: " + this.name + " | HP: " + this.health + " | Attack Damage: " + this.attackDamage + " |\n");

        return (this.description + "\n" + "| Name: " + this.name + " | HP: " + this.health + " | Attack Damage: " + this.attackDamage + " |\n");
    }
}
