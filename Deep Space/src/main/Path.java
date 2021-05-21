package main;
import java.util.Scanner;

public class Path
{
    private int        id;
    private Room       origin;
    private Room       destination;
    private String     directionRAW;
    private boolean    lockStatus;
    private int        lockCode;
    private Directions directions;


    public Path(int id,
                Room origin,
                Room destination,
                String directionRAW,
                boolean lockStatus,
                int lockCode,
                Directions directions)
    {
        this.id           = id;
        this.origin       = origin;
        this.destination  = destination;
        this.directionRAW = directionRAW;
        this.lockStatus   = lockStatus;
        this.lockCode     = lockCode;
        this.directions   = directions;
    }


    public Path(Scanner pathScanner)
    {
        String  line       = StringParser.parseLine(pathScanner);  //path id   origin  direction destination lock-code
        Scanner lineParser = new Scanner(line);

        this.id     = lineParser.nextInt();
        this.origin = Room.getRoomByID(lineParser.nextInt());

        this.directionRAW = lineParser.next();
        for (Directions direction : Directions.values())
        {
            if (direction.match(this.directionRAW))
            {
                this.directions = direction;
                break;
            }
        }

        this.destination = Room.getRoomByID(lineParser.nextInt());


        this.lockCode   = lineParser.nextInt();
        this.lockStatus = this.lockCode > 0;

        this.origin.addPath(this);
    }


    public boolean isLocked()
    {
        return lockStatus;
    }

    public String useKey(Item key)
    {
        if (this.lockCode != 0)
        {
            if (this.lockCode == key.getKeyCode())
            {
                System.out.println("Here");
                this.lockStatus = !this.lockStatus;
                return "click...";
            }
        }
        return null;
    }

    String useCode(int code)
    {
        if (this.lockCode != 0)
        {
            if (this.lockCode == code)
            {
                this.lockStatus = !this.lockStatus;
                System.out.println("Here");
                return "\nclick...\n";
            }
        }
        return null;
    }

    public Room travel()
    {
        if (this.lockStatus)
        {
            System.out.println("This door is locked!\n");
            return origin;
        }
        else
        {
            return destination;
        }
    }

    public boolean match(String string)
    {
        return directions.match(string);
    }
}
