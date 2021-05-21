import java.util.ArrayList;

public class characterLoadout
{
    private Item helmet;
    private Item armor;
    private Item leggings;
    private Item weapon;

    public characterLoadout()
    {

    }

    public characterLoadout(Item helmet, Item armor, Item leggings, Item weapon)
    {
        this.helmet   = helmet;
        this.armor    = armor;
        this.leggings = leggings;
        this.weapon   = weapon;
    }

    public Item getHelmet()
    {
        return helmet;
    }

    public void printHelmet()
    {
        if (this.getHelmet() == null)
        {
            System.out.println("Helmet: None");
        }
        else
        {
            System.out.println("Helmet: " + this.helmet.getName());
        }
    }

    public void setHelmet(Item helmet)
    {
        this.helmet = helmet;
    }

    public Item getArmor()
    {
        return armor;
    }

    public void printArmor()
    {
        if (this.getArmor() == null)
        {
            System.out.println("Armor: None");
        }
        else
        {
            System.out.println("Armor: " + this.armor.getName());
        }
    }

    public void setArmor(Item armor)
    {
        this.armor = armor;
    }

    public Item getLeggings()
    {
        return leggings;
    }

    public void printLeggings()
    {
        if (this.getLeggings() == null)
        {
            System.out.println("Leggings: None");
        }
        else
        {
            System.out.println("Leggings: " + this.getLeggings().getName());
        }
    }

    public void setLeggings(Item leggings)
    {
        this.leggings = leggings;
    }

    public Item getWeapon()
    {
        return weapon;
    }

    public void printWeapon()
    {
        if (this.getWeapon() == null)
        {
            System.out.println("Weapon: None");
        }
        else
        {
            System.out.println("Weapon: " + this.weapon.getName());
        }
    }

    public void setWeapon(Item weapon)
    {
        this.weapon = weapon;
    }

    public void printoutLoadout()
    {
        printHelmet();
        printArmor();
        printLeggings();
        printWeapon();
    }

    public void currentLoadout()
    {
        System.out.println("Your current loadout is: ");
        printoutLoadout();
    }

}
