package main;

import java.util.ArrayList;

public class Character
{
    private       String           name; //Characters name
    private       int              attackDamage;
    private       int              currentattackDamage;
    private       int              temporaryDamage;
    private       int              maxHealth;
    private       int              currentHealth;
    public        characterLoadout loadout;
    public static Room             currentRoom;
    public static Room             startRoom;

    private ArrayList<Item>         inventory;
    private ArrayList<ControlPanel> panelsActivated;


    public Character(String name)
    {
        this.name                = name;
        this.attackDamage        = 5;
        this.currentattackDamage = 0;
        this.temporaryDamage     = 0;
//        currentRoom              = null;
        this.maxHealth           = 20;
        this.loadout             = null;
        this.currentHealth       = this.maxHealth;
        loadout                  = new characterLoadout();
        this.inventory           = new ArrayList<>();
    }

    public void makeMove()
    {

    }


//--------------------------


    public int getCurrentattackDamage()
    {
        return currentattackDamage;
    }

    public void setCurrentattackDamage(int currentattackDamage)
    {
        this.currentattackDamage = currentattackDamage;
    }

    public void increaseCurrentattackDamage(int currentattackDamage)
    {
        this.currentattackDamage += currentattackDamage;
    }

    public void decreaseCurrentattackDamage(int currentattackDamage)
    {
        this.currentattackDamage -= currentattackDamage;
    }

    public int getTemporaryDamage()
    {
        return temporaryDamage;
    }

    public void increaseTemporaryDamage(int increase)
    {
        this.temporaryDamage += increase;
    }

    public void resetTemporaryDamage()
    {
        System.out.println("You lost " + this.temporaryDamage + " damage");
        this.temporaryDamage = 0;

    }


    public characterLoadout getLoadout()
    {
        return loadout;
    }

    public void setLoadout(characterLoadout loadout)
    {
        this.loadout = loadout;
    }

    public boolean isHealthFull()
    {
        return this.getMaxHealth() == this.getCurrentHealth();
    }


    public int getMaxHealth()
    {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth)
    {
        this.maxHealth = maxHealth;
    }

    public int getCurrentHealth()
    {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth)
    {
        this.currentHealth = currentHealth;
    }

    public void increaseCurrentMaxHealth(int maxHealth)
    {
        this.maxHealth += maxHealth;
    }

    public void decreaseCurrentMaxHealth(int maxHealth)
    {
        this.maxHealth -= maxHealth;
    }

    public void loseCurrentHealth(int currentHealth)
    {
        this.currentHealth -= currentHealth;
    }

    public void gainCurrentHealth(int currentHealth)
    {
        this.currentHealth += currentHealth;
        if (this.currentHealth > this.maxHealth)
        {
            this.currentHealth = this.maxHealth;
        }
    }

    public int getAttackDamage()
    {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage)
    {
        this.attackDamage = attackDamage;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom)
    {
        Character.currentRoom = currentRoom;
    }

    public void setPreviousRoom(Room startRoom)
    {
        Character.startRoom = startRoom;
    }

    public Room getPreviousRoom()
    {
        return startRoom;
    }

    public ArrayList<Item> getInventory()
    {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory)
    {
        this.inventory = inventory;
    }

    public void removeItem(Item item)
    {
        for (int i = 0; i < inventory.size(); ++i)
        {
            if (inventory.get(i) == item)
            {
                inventory.remove(i);
                System.out.println("Removed");
            }
        }
    }

    public void getStats()
    {
        System.out.println("Player: " + this.getName());
        System.out.println("Health: " + this.getCurrentHealth() + " of " + this.getMaxHealth());
        System.out.println("Attack Damage: " +
                           this.getAttackDamage() +
                           " + " +
                           this.getCurrentattackDamage() +
                           " (Equipment) + " + this.getTemporaryDamage() + " (Buffs) total damage.");
    }
}
