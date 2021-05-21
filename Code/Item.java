//Items consist of weaponry, buffs, debuffs, keys, etc.

import java.util.Scanner;

public class Item
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


    void use(Room room, Player player)
    {
//        if (this.keyCode > 0)
//        {
//            room.useKey(this);
//        }

        switch (this.id)
        {
            case 1:
                if (this.keyCode > 0)
                {
                    room.useKey(this);
                }
                break;
            case 4:
                player.gainCurrentHealth(this.healthValue);
                System.out.println("You use the first aid kit...\nGained 5 HP!");
                player.removeItem(this);
                break;
            case 5:
                player.increaseTemporaryDamage(this.attackDamageValue);
                System.out.println("You used the power pill...\nGained " +
                                   this.attackDamageValue +
                                   " damage until the end of the next combat!");
                player.removeItem(this);
                break;

        }

    }
    //--------------------------------------


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

    public void details()
    {
        System.out.println("Name: " + this.name);
        System.out.println(this.description + "\n");
    }
}
