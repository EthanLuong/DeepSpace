//Items consist of weaponry, buffs, debuffs, keys, etc.
package main;
import java.util.Scanner;

public class Item   //TODO:implement abstract and move over scanner to new Unique item class
{
    private       int    id;
    private       int    isaDrop;
    private       int    isEquipped;
    private       int    healthValue;
    private       int    attackDamageValue;
    private       int    isLeg;
    private       int    isHelmet;
    private       int    isArmor;
    private       int    isWeapon;
    private       String name;
    private       String description;
    private       String imageLocation;
    private       double weight;
    private       double value;
    private final int    keyCode;

    public Item(int id,
                int isaDrop,
                int isEquipped,
                int healthValue,
                int attackDamageValue,
                int isLeg,
                int isHelmet,
                int isArmor,
                int isWeapon,
                String name,
                String description,
                String imageLocation,
                double weight,
                double value,
                int keyCode)
    {
        this.id                = id;
        this.isaDrop           = isaDrop;
        this.isEquipped        = isEquipped;
        this.healthValue       = healthValue;
        this.attackDamageValue = attackDamageValue;
        this.isLeg             = isLeg;
        this.isHelmet          = isHelmet;
        this.isArmor           = isArmor;
        this.isWeapon          = isWeapon;
        this.name              = name;
        this.description       = description;
        this.imageLocation     = imageLocation;
        this.weight            = weight;
        this.value             = value;
        this.keyCode           = keyCode;
    }


    public Item(Scanner itemScanner)
    {
        String line = StringParser.parseLine(itemScanner);  //location
        assert line != null;
        Scanner scanner = new Scanner(line);

        int location = scanner.nextInt();

        line = StringParser.parseLine(itemScanner); //  id  isaDrop isEquipped  hpValue attackDamageValue isLeg isHelmet isArmor isWeapon  value   weight  keycode title
        assert line != null;
        scanner = new Scanner(line);

        this.id                = scanner.nextInt();
        this.isaDrop           = scanner.nextInt();
        this.isEquipped        = scanner.nextInt();
        this.healthValue       = scanner.nextInt();
        this.attackDamageValue = scanner.nextInt();
        this.isLeg             = scanner.nextInt();
        this.isHelmet          = scanner.nextInt();
        this.isArmor           = scanner.nextInt();
        this.isWeapon          = scanner.nextInt();
        this.value             = scanner.nextDouble();
        this.weight            = scanner.nextDouble();
        this.keyCode           = scanner.nextInt();
        scanner.skip("[ \t]*");
        this.name = scanner.nextLine();

        line = StringParser.parseLine(itemScanner);
        this.imageLocation = line;

        line = StringParser.parseLine(itemScanner); //  lines-in-description
        assert line != null;
        scanner = new Scanner(line);

        int           numLines = scanner.nextInt();
        StringBuilder buffer   = new StringBuilder();   //description
        for (int i = 0; i < numLines; i++)
        {
            buffer.append(itemScanner.nextLine());
        }
        this.description = buffer.toString();

        Room.allRooms.get(location).addItem(this);
    }


    public String use(Room room, Player player)
    {

        if (this.isEquippable() == 1)
        {
            if (this.isArmor() == 1)
            {
                if (player.loadout.getArmor() == null)
                {
                    player.loadout.setArmor(this);
                    player.increaseCurrentMaxHealth(this.getHealthValue());
                    player.increaseCurrentattackDamage(this.getAttackDamageValue());
                    player.getStatsGained(this);
                }
                else
                {
                    player.decreaseCurrentattackDamage(player.loadout.getArmor().getAttackDamageValue());
                    player.decreaseCurrentMaxHealth(player.loadout.getArmor().getHealthValue());
                    player.getStatsLost(player.loadout.getArmor());
                    player.loadout.setArmor(this);
                    player.increaseCurrentMaxHealth(this.getHealthValue());
                    player.increaseCurrentattackDamage(this.getAttackDamageValue());
                    player.getStatsGained(this);

                }
            }
            else if (this.isHelmet() == 1)
            {
                if (player.loadout.getHelmet() != null)
                {
                    player.decreaseCurrentattackDamage(player.loadout.getHelmet().getAttackDamageValue());
                    player.decreaseCurrentMaxHealth(player.loadout.getHelmet().getHealthValue());
                    player.getStatsLost(player.loadout.getHelmet());
                }
                player.loadout.setHelmet(this);
                player.increaseCurrentMaxHealth(this.getHealthValue());
                player.increaseCurrentattackDamage(this.getAttackDamageValue());
                player.getStatsGained(this);
            }
            else if (this.isLeg() == 1)
            {
                if (player.loadout.getLeggings() != null)
                {
                    player.decreaseCurrentattackDamage(player.loadout.getLeggings().getAttackDamageValue());
                    player.decreaseCurrentMaxHealth(player.loadout.getLeggings().getHealthValue());
                    player.getStatsLost(player.loadout.getLeggings());

                }
                player.loadout.setLeggings(this);
                player.increaseCurrentMaxHealth(this.getHealthValue());
                player.increaseCurrentattackDamage(this.getAttackDamageValue());
                player.getStatsGained(this);
            }
            else if (this.isWeapon() == 1)
            {
                if (player.loadout.getWeapon() != null)
                {
                    player.decreaseCurrentattackDamage(player.loadout.getWeapon().getAttackDamageValue());
                    player.decreaseCurrentMaxHealth(player.loadout.getWeapon().getHealthValue());
                    player.getStatsLost(player.loadout.getWeapon());
                }
                player.loadout.setWeapon(this);
                player.increaseCurrentMaxHealth(this.getHealthValue());
                player.increaseCurrentattackDamage(this.getAttackDamageValue());
                player.getStatsGained(this);
            }
            player.setCurrentHealth(player.getMaxHealth());
        }
        else {
            switch (this.id) {
                case 1:
                    if (this.keyCode > 0) {
                        return room.useKey(this);
                    }
                    break;
                case 4:
                    player.gainCurrentHealth(this.healthValue);
                    player.removeItem(this);
                    return "You use the first aid kit...\nGained 5 HP!";
                case 5:
                    player.increaseTemporaryDamage(this.attackDamageValue);
                    player.removeItem(this);
                    return "You used the power pill...\nGained " +
                            this.attackDamageValue +
                            " damage until the end of the next combat!";

            }
        }
    return null;
    }
    //--------------------------------------

    public String getImageLocation() { return this.imageLocation;}

    public int getAttackDamageValue()
    {
        return attackDamageValue;
    }

    public void setAttackDamageValue(int attackDamageValue)
    {
        this.attackDamageValue = attackDamageValue;
    }

    public int isLeg()
    {
        return isLeg;
    }

    public void setIsLeg(int isLeg)
    {
        this.isLeg = isLeg;
    }

    public int isHelmet()
    {
        return isHelmet;
    }

    public void setIsHelmet(int isHelmet)
    {
        this.isHelmet = isHelmet;
    }

    public int isArmor()
    {
        return isArmor;
    }

    public void setIsArmor(int isArmor)
    {
        this.isArmor = isArmor;
    }

    public int isWeapon()
    {
        return isWeapon;
    }

    public void setIsWeapon(int isWeapon)
    {
        this.isWeapon = isWeapon;
    }

    public int isEquippable()
    {
        return isEquipped;
    }

    public void setIsEquipped(int isEquipped)
    {
        this.isEquipped = isEquipped;
    }

    public int getHealthValue()
    {
        return healthValue;
    }

    public void setHealthValue(int healthValue)
    {
        this.healthValue = healthValue;
    }

    public int isADrop()
    {
        return isaDrop;
    }

    public void setIsaDrop(int isaDrop)
    {
        this.isaDrop = isaDrop;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getWeight()
    {
        return this.weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    public double getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getKeyCode()
    {
        return this.keyCode;
    }

    public boolean match(String itemName)
    {
        return itemName.equalsIgnoreCase(this.name);
    }

    public String details()
    {
//        System.out.println("Name: " + this.name);
//        System.out.println(this.description + "\n");

        return("Name: " + this.name + "\n" + this.description + "\n");
    }
}
