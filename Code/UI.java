import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI
{
    protected static JFrame frame;

    public static void initializeUI()
    {
        // Creates instance of JFrame
        frame = new JFrame("Deep Space");
        // Sets the width and height to desired numerical values
        frame.setSize(340, 340);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);

        Welcome welcome = new Welcome();
        welcome.welcomeMessage();
        JButton playButton = new JButton("Play");
        playButton.setBounds(10, 80, 80, 25);
        panel.add(playButton);

        JButton helpButton = new JButton("Help");
        helpButton.setBounds(10, 110, 80, 25);
        panel.add(helpButton);

        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(10, 110, 80, 25);
        panel.add(quitButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(10, 80, 80, 25);

        JLabel enter = new JLabel("Enter:");
        enter.setBounds(10, 20, 80, 25);


        helpButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JPanel newPanel = new JPanel();
                frame.setContentPane(newPanel);
                frame.invalidate();
                frame.validate();
                frame.setSize(500, 200);
                newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.PAGE_AXIS));
                String[] commands = {
                        "Quit = quit the game",
                        "OPEN = Open door/chest",
                        "EXIT = Return to Main menu",
                        "GO exdirection = Moves you to a specified direction",
                        "LOOK = displays valuables around your character"
                        ,
                        "PUT = Puts an object in your inventory into a chest/door/valuable",
                        "INVENTORY = Displays all your current inventory",
                        "HELP = Displays this menu"
                };
                JLabel Welcome = new JLabel("Commands:");
                Welcome.setBounds(10, 20, 80, 25);
                newPanel.add(Welcome);
                int x = 10;
                for (int i = 0; i < commands.length; i++)
                {
                    JLabel a = new JLabel(commands[i]);
                    a.setBounds(10, 20 + x, 80, 25);
                    newPanel.add(a);
                    x = x + 10;
                }
                newPanel.add(backButton);

            }
        });

        backButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.setContentPane(panel);
                frame.invalidate();
                frame.validate();
                frame.setSize(340, 340);
            }
        });

        quitButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        playButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JPanel gamePanel = new JPanel();
                frame.setContentPane(gamePanel);
                frame.invalidate();
                frame.validate();
                frame.setSize(340, 340);
                gamePanel.add(enter);

            }
        });


        // Setting the frame visible to true
        frame.setVisible(true);
    }
}
