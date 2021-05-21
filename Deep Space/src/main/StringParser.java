package main;
import java.util.Scanner;

public class StringParser
{
    public static String parseLine(Scanner scanner)
    {
        String line;

        while (scanner.hasNextLine())
        {
            line = scanner.nextLine();
            int comment = line.indexOf("//");   //  find comment
            if (comment == 0)   //no comment
            {
                continue;
            }
            if (comment > 0)    //has comment
            {
                line = line.substring(0, comment);
            }
            line = line.trim();
            if (line.length() > 0)
            {
                return line;
            }
        }
        return null;
    }
}
