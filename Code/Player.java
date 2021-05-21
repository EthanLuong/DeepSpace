//Player class extends Character class to specialize as the playable main character

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

    public void makeMove()
    {
        Scanner keyboard = new Scanner(System.in);
        String  move     = keyboard.next().toLowerCase();

        String itemName;
        String enemyName;
        switch (move)
        {
            case "exit":
            case "leave":
                System.out.println("Exiting...");
                Game.gameOver = true;
                break;

            case "look":
                this.getCurrentRoom().details();
                break;


            case "go":
            case "move":
                String direction = keyboard.next();
                this.setPreviousRoom(this.getCurrentRoom());
                this.setCurrentRoom(this.getCurrentRoom().followRoom(direction));

                break;


            case "get":
            case "pickup":
                itemName = keyboard.next();
                this.getInventory().add(this.getCurrentRoom().pickUpItem(itemName));


                break;
            case "attack":
            case "kill":
            case "hit":
                enemyName = keyboard.next();
                System.out.println("You decide to attack the " + enemyName);
                this.setCurrentHealth(this.getCurrentRoom()
                                          .attackEnemy(enemyName,
                                                  this.getAttackDamage() +
                                                  this.getCurrentattackDamage() +
                                                  this.getTemporaryDamage(),
                                                  this.getCurrentHealth()));
                this.resetTemporaryDamage();
                break;

            case "drop":
            case "letgo":
                itemName = keyboard.next();
                this.getInventory().remove(this.getCurrentRoom().removeItem(itemName));
                break;

            case "inv":
            case "inventory":
                System.out.println("You check your inventory, you see that you have the following items: ");
                for (Item item : this.getInventory())
                {
                    item.details();
                }
                break;
            case "equip":
            case "put on":
            case "don":
                itemName = keyboard.next();
                ///in case the item was freshly dropped from a kill we'll pick up the item from floor.
                if (!this.getInventory().contains(itemName))
                {
                    Item item = this.getCurrentRoom().pickUpItem(itemName);
                    if (item != null)
                    {
                        this.getInventory().add(item);
                    }
                    else
                    {
                        break;
                    }
                }

                for (Item item : this.getInventory())
                {
                    if (item.match(itemName))
                    {
                        if (item.isEquippable() == 1)
                        {
                            int check = 0;
                            if (this.isHealthFull())
                            {
                                check = 1;
                            }
                            if (item.isArmor() == 1)
                            {
                                if (this.loadout.getArmor() == null)
                                {
                                    this.loadout.setArmor(item);
                                    this.increaseCurrentMaxHealth(item.getHealthValue());
                                    this.increaseCurrentattackDamage(item.getAttackDamageValue());
                                    getStatsGained(item);
                                }
                                else
                                {
                                    this.decreaseCurrentattackDamage(loadout.getArmor().getAttackDamageValue());
                                    this.decreaseCurrentMaxHealth(loadout.getArmor().getHealthValue());
                                    getStatsLost(loadout.getArmor());
                                    this.loadout.setArmor(item);
                                    this.increaseCurrentMaxHealth(item.getHealthValue());
                                    this.increaseCurrentattackDamage(item.getAttackDamageValue());
                                    getStatsGained(item);

                                }
                            }
                            else if (item.isHelmet() == 1)
                            {
                                if (this.loadout.getHelmet() != null)
                                {
                                    this.decreaseCurrentattackDamage(loadout.getHelmet().getAttackDamageValue());
                                    this.decreaseCurrentMaxHealth(loadout.getHelmet().getHealthValue());
                                    getStatsLost(loadout.getHelmet());
                                }
                                this.loadout.setHelmet(item);
                                this.increaseCurrentMaxHealth(item.getHealthValue());
                                this.increaseCurrentattackDamage(item.getAttackDamageValue());
                                getStatsGained(item);
                            }
                            else if (item.isLeg() == 1)
                            {
                                if (this.loadout.getLeggings() != null)
                                {
                                    this.decreaseCurrentattackDamage(loadout.getLeggings().getAttackDamageValue());
                                    this.decreaseCurrentMaxHealth(loadout.getLeggings().getHealthValue());
                                    getStatsLost(loadout.getLeggings());

                                }
                                this.loadout.setLeggings(item);
                                this.increaseCurrentMaxHealth(item.getHealthValue());
                                this.increaseCurrentattackDamage(item.getAttackDamageValue());
                                getStatsGained(item);
                            }
                            else if (item.isWeapon() == 1)
                            {
                                if (this.loadout.getWeapon() != null)
                                {
                                    this.decreaseCurrentattackDamage(loadout.getWeapon().getAttackDamageValue());
                                    this.decreaseCurrentMaxHealth(loadout.getWeapon().getHealthValue());
                                    getStatsLost(loadout.getWeapon());
                                }
                                this.loadout.setWeapon(item);
                                this.increaseCurrentMaxHealth(item.getHealthValue());
                                this.increaseCurrentattackDamage(item.getAttackDamageValue());
                                getStatsGained(item);
                            }
                            this.setCurrentHealth(this.getMaxHealth());
                        }
                        else
                        {
                            System.out.println("Sorry, you can't equip this item!");
                        }
                    }
                }
                break;

            case "run":
            case "run away":
                System.out.println("You run back to the previous room");
                this.setCurrentRoom(this.getPreviousRoom());
                break;
            case "stat":
            case "stats":
                System.out.println("You check your stats.\n");
                this.getStats();
                this.loadout.currentLoadout();
                break;
            case "activate":
            case "access":
                String panelName = keyboard.next();
                this.getCurrentRoom().usePanel(panelName);
                break;

            case "use":
                itemName = keyboard.next();
//                for (Item item : this.getInventory())
//                {
//                    if (item.match(itemName))
//                        item.use(this.getCurrentRoom(), this);
//                }
                int itemIndex = -1;
                for (int i = 0; i < this.getInventory().size(); ++i)
                {
                    if (this.getInventory().get(i).match(itemName))
                    {
                        itemIndex = i;
                        break;
                    }
                }
                if (itemIndex != -1)
                {
                    this.getInventory().get(itemIndex).use(this.getCurrentRoom(), this);
                }
                else
                {
                    System.out.println("You don't have a " + itemName);
                }
                break;

            default:
                break;
        }
    }
}
