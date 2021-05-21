//Player class extends Character class to specialize as the playable main character
package main;
import java.util.Scanner;

public class Player extends Character
{
    public Player(String name)
    {
        super(name);
    }



    public void getStatsGained(Item a)
    {
        System.out.println("You Equipped " + a.getName());
        System.out.println("\nYour Max Health has increased by: " + a.getHealthValue());
        System.out.println("Your Attack Damage has increased by: " + a.getAttackDamageValue());
    }

    public void getStatsLost(Item a)
    {
        System.out.println("You unequipped " + a.getName());
        System.out.println("\nYour Max Health has decreased by: " + a.getHealthValue());
        System.out.println("Your Attack Damage has decreased by: " + a.getAttackDamageValue());
    }

    public void showInventory(){
        for(Item i : this.getInventory()){
            System.out.println(i.getName());
        }
    }

    public void makeMove()
    {
        Scanner keyboard = new Scanner(System.in);
        String  move     = keyboard.next().toLowerCase();

        String itemName;
        String enemyName;
        switch (move)
        {


            case "get":
            case "pickup":

            case "attack":
            case "kill":
            case "hit":

            case "drop":
            case "letgo":
                itemName = keyboard.next();
//                this.getInventory().remove(this.getCurrentRoom().removeItem(itemName));
                break;


            case "run":
            case "run away":
                System.out.println("You run back to the previous room");
                this.setCurrentRoom(this.getPreviousRoom());
                break;

            case "activate":
            case "access":
                String panelName = keyboard.next();
                this.getCurrentRoom().usePanel(panelName);
                break;

            case "use":
                   break;
        }
    }
}
