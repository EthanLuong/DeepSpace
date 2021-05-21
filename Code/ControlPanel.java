//Control Panel class consists of the different type of control Panels in the game

import java.util.Scanner;

public class ControlPanel
{
    //id    isUnlocked  locationitUnlocks   keycode
    private int id;
    private int isUnlocked;
    private int locationitUnlocks;
    private String name;
    private int keycode;

    public ControlPanel(int id, int isUnlocked, int locationitUnlocks, int keycode, String name) {
        this.id = id;
        this.isUnlocked = isUnlocked;
        this.locationitUnlocks = locationitUnlocks;
        this.keycode = keycode;
        this.name = name;
    }

    public ControlPanel(Scanner itemScanner) {
        String line = StringParser.parseLine(itemScanner);  //location
        assert line != null;
        Scanner scanner = new Scanner(line);

        int location = scanner.nextInt();

        line = StringParser.parseLine(itemScanner); //id    isUnlocked  locationitUnlocks   keycode
        assert line != null;
        scanner = new Scanner(line);

        this.id = scanner.nextInt();
        this.isUnlocked = scanner.nextInt();
        this.locationitUnlocks = scanner.nextInt();
        this.keycode = scanner.nextInt();
        scanner.skip("[ \t]*");
        this.name = scanner.nextLine();

        Room.allRooms.get(location).addPanel(this);
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getIsUnlocked() { return isUnlocked; }

    public void setIsUnlocked(int isUnlocked) { this.isUnlocked = isUnlocked; }

    public int getLocationitUnlocks() { return locationitUnlocks; }

    public void setLocationitUnlocks(int locationitUnlocks) { this.locationitUnlocks = locationitUnlocks; }

    public int getKeycode() { return keycode; }

    public void setKeycode(int keycode) { this.keycode = keycode; }

    public void getDetails(){
        System.out.println("Control Panel: " + this.getName());
    }
}
