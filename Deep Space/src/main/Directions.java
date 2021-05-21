package main;
public enum Directions
{
    NORTH("North", "N"),
    SOUTH("South", "S"),
    EAST("East", "E"),
    WEST("West", "W"),
    UP("Up", "U"),
    DOWN("Down", "D"),
    NORTHEAST("Northeast", "NE"),
    NORTHWEST("Northwest", "NW"),
    SOUTHEAST("Southeast", "SE"),
    SOUTHWEST("Southwest", "SW"),
    NORTHNORTHEAST("North-Northeast", "NNE"),
    NORTHNORTHWEST("North-Northwest", "NNW"),
    EASTNORTHEAST("East-Northeast", "ENE"),
    WESTNORTHWEST("West-Northwest", "WNW"),
    EASTSOUTHEAST("East-Southeast", "ESE"),
    WESTSOUTHWEST("West-Southwest", "WSW"),
    SOUTHSOUTHEAST("South-Southeast", "SSE"),
    SOUTHSOUTHWEST("South-Southwest", "SSW");

    private String text;
    private String abbreviation;

    Directions(String text, String abbreviation)
    {
        this.text = text;
        this.abbreviation = abbreviation;
    }

    public String toString() { return this.text; }

    public boolean match (String string)
    {
        return string.equalsIgnoreCase(this.text) || string.equalsIgnoreCase(this.abbreviation);
    }
}
