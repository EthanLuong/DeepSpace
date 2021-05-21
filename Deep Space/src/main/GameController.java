package main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class GameController {
    Player mainPlayer;

    @FXML private Label roomLabel;
    @FXML private Label hpLabel;
    @FXML private Label adLabel;
    @FXML private TextArea roomDescription;
    @FXML private Button pickUpButton;
    @FXML private ComboBox<String> inventory;
    @FXML private ImageView enemyPic;
    @FXML private ImageView itemPic;
    @FXML private HBox roomPicture;
    @FXML private Button runButton;
    @FXML private Button fightButton;
    @FXML private Button activateButton;
    @FXML private Button northButton;
    @FXML private Button southButton;
    @FXML private Button eastButton;
    @FXML private Button westButton;

    @FXML

    public void updateBackgroundPicture(){
        Image img = new Image("file:" +mainPlayer.getCurrentRoom().getpictureSource());
        BackgroundImage bg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(0.0,0.0,false,false,true,true));

        Background b = new Background(bg);

        roomPicture.setBackground(b);
    }



    public void initialize(){
        mainPlayer = new Player("Steve");
        roomLabel.setText("Room: " + mainPlayer.getCurrentRoom().getName());
        roomDescription.setText(mainPlayer.getCurrentRoom().getDescription());
        hpLabel.setText("HP: " + mainPlayer.getCurrentHealth() + "/" + mainPlayer.getMaxHealth());
        adLabel.setText("AD: " + mainPlayer.getAttackDamage());


        updateBackgroundPicture();

    }

    private void updateDesc(boolean flag){
        String description;
        roomLabel.setText("Room: " + mainPlayer.getCurrentRoom().getName());


        //System.out.println(mainPlayer.getCurrentRoom().getallEnemies());
        if(!mainPlayer.getCurrentRoom().getallEnemies().isEmpty()) {
            String enemyPng = "";
            for (int i = 0; i < mainPlayer.getCurrentRoom().getallEnemies().size(); i++)
                 enemyPng = "file:" + mainPlayer.getCurrentRoom().getallEnemies().get(i).getImageLocation();
            Image a  = new Image(enemyPng);
            enemyPic.setImage(a);
        }
        else{
            enemyPic.setImage(null);
        }

        if(mainPlayer.getCurrentRoom().checkItems() != null) {
            if (mainPlayer.getCurrentRoom().checkItems().get(0).isADrop() == 1) {
                itemPic.setImage(null);
            } else {
                String itemPng = "file:" + mainPlayer.getCurrentRoom().checkItems().get(0).getImageLocation();
                Image a = new Image(itemPng);
                itemPic.setImage(a);
            }
        }
        else{
            itemPic.setImage(null);
        }


        description = mainPlayer.getCurrentRoom().getDescription();
        updateBackgroundPicture();

        description = mainPlayer.getCurrentRoom().getDescription();


        ArrayList<Item> items = mainPlayer.getCurrentRoom().checkItems();
        ArrayList<Enemy> enemies = mainPlayer.getCurrentRoom().checkEnemies();
        ArrayList<ControlPanel> panels = mainPlayer.getCurrentRoom().checkPanel();

        pickUpButton.setVisible(false);
        runButton.setVisible(false);
        fightButton.setVisible(false);
        activateButton.setVisible(false);
        northButton.setVisible(true);
        westButton.setVisible(true);
        eastButton.setVisible(true);
        southButton.setVisible(true);

        if(items != null){
            for(Item i: items){
                if(i.isADrop() == 0){
                    description += i.details();
                    pickUpButton.setVisible(true);
                }
            }

        }
        if(enemies != null){
            System.out.println("Enemies are here");
            fightButton.setVisible(true);
            for(Enemy e: enemies){
                if (e.getPersonality() == 0)
                {
                    description += e.details();
                }
                else if (e.getPersonality() == 1)
                {
                    northButton.setVisible(false);
                    westButton.setVisible(false);
                    eastButton.setVisible(false);
                    southButton.setVisible(false);
                    description += e.details();
                    runButton.setVisible(true);
                    description += "You are dragged into battle. There seems to be only two options. Run or fight, what do you choose?\n";
                }
            }
        }
        if(panels != null){
            boolean x = true;
            for(ControlPanel p : panels){
                if(p.getIsUnlocked() == 1){
                    x = false;
                }
            }
            if(x){
                activateButton.setVisible(true);
                for(ControlPanel p : panels){
                    description += p.getDetails();
                }
            }

        }
        if(flag && roomDescription.getText().equals(description)){
            roomDescription.setText("Nothing has changed . . .");
        } else{
            roomDescription.setText(description);
        }

        hpLabel.setText("HP: " + mainPlayer.getCurrentHealth() + "/" + mainPlayer.getMaxHealth());
        String adText = "AD: 5";
        if(mainPlayer.getCurrentattackDamage() > 0){
            adText += "+" + mainPlayer.getCurrentattackDamage();
        }
        if(mainPlayer.getTemporaryDamage() > 0){
            adText += "+" + mainPlayer.getTemporaryDamage();
        }
        adLabel.setText(adText);


    }

    private void makeMove(String input){

        mainPlayer.setPreviousRoom(mainPlayer.getCurrentRoom());
        mainPlayer.setCurrentRoom(mainPlayer.getCurrentRoom().followRoom(input));
        updateDesc(false);

    }

    @FXML
    private void pickUp(){
        ArrayList<Item> items = mainPlayer.getCurrentRoom().checkItems();
        Iterator<Item> itr = items.iterator();
//        for(Item i : items){
//            if(i.isADrop() == 0){
//                inventory.getItems().addAll(i.getName());
//                mainPlayer.getInventory().add(i);
//            }
//        }
        while (itr.hasNext()) {
            Item x = itr.next();
            if(x.isADrop() == 0){
                inventory.getItems().addAll(x.getName());
                mainPlayer.getInventory().add(x);
                itemPic.setImage(null);
                itr.remove();
            }
        }
        pickUpButton.setVisible(false);
        mainPlayer.showInventory();
        updateDesc(false);
    }

    @FXML
    private void dropItem(){
        mainPlayer.getCurrentRoom().removeItem(inventory.getValue(), mainPlayer);
        inventory.getItems().remove(inventory.getValue());
        mainPlayer.showInventory();
        updateDesc(false);
    }

    @FXML
    private void useItem(){
        Item temp = null;
        String itemName = inventory.getValue();
        inventory.getItems().remove(inventory.getValue());
        for(Item i: mainPlayer.getInventory()){
            if(i.getName() == itemName){
                temp = i;
            }
        }
        if(temp != null){
            String x = temp.use(mainPlayer.getCurrentRoom(), mainPlayer);
            updateDesc(false);
            if(x != null){
                roomDescription.setText(x);
            }
        }
    }


    @FXML private void run(){
        mainPlayer.setCurrentRoom(mainPlayer.getPreviousRoom());
        updateDesc(false);
    }

    @FXML private void fight(){
        ArrayList<Enemy> enemies = mainPlayer.getCurrentRoom().checkEnemies();
        String enemyName;
        if(enemies == null){
            System.out.println("NULL");
        }
        enemyName = enemies.get(0).getName();
//        mainPlayer.setCurrentHealth(mainPlayer.getCurrentRoom()
//                .attackEnemy(enemyName,
//                        mainPlayer.getAttackDamage() +
//                                mainPlayer.getCurrentattackDamage() +
//                                mainPlayer.getTemporaryDamage(),
//                        mainPlayer.getCurrentHealth()));
        String description = mainPlayer.getCurrentRoom().attackEnemy(enemyName, mainPlayer);
        mainPlayer.resetTemporaryDamage();
        enemyPic.setImage(null);
        if(!mainPlayer.getCurrentRoom().checkItems().isEmpty()) {
            if (mainPlayer.getCurrentRoom().checkItems().get(0).isADrop() == 1) {
                itemPic.setImage(null);
            } else {
                String itemPng = "file:" + mainPlayer.getCurrentRoom().checkItems().get(0).getImageLocation();
                Image a = new Image(itemPng);
                itemPic.setImage(a);
            }
        }
        if(description != null){
            roomDescription.setText(description);
        }
        runButton.setVisible(false);
        fightButton.setVisible(false);
        hpLabel.setText("HP: " + mainPlayer.getCurrentHealth() + "/" + mainPlayer.getMaxHealth());
        String adText = "AD: 5";
        if(mainPlayer.getCurrentattackDamage() > 0){
            adText += "+" + mainPlayer.getCurrentattackDamage();
        }
        if(mainPlayer.getTemporaryDamage() > 0){
            adText += "+" + mainPlayer.getTemporaryDamage();
        }
        adLabel.setText(adText);
        northButton.setVisible(true);
        westButton.setVisible(true);
        eastButton.setVisible(true);
        southButton.setVisible(true);
    }

    @FXML private void activatePanel(){
        ArrayList<ControlPanel> panels = mainPlayer.getCurrentRoom().checkPanel();
        roomDescription.setText(mainPlayer.getCurrentRoom().usePanel(panels.get(0).getName()));
        activateButton.setVisible(false);
    }

    @FXML private void lookAround(){
        updateDesc(true);
    }

    @FXML private void goNorth(){ makeMove("north"); }

    @FXML private void goEast(){ makeMove("east"); }

    @FXML private void goSouth(){ makeMove("south"); }

    @FXML private void goWest(){ makeMove("west"); }


}
