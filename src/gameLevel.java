import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class gameLevel {
    private ArrayList<duck> allDucks;
    public void addDuck(duck duckToAdd){ allDucks.add(duckToAdd); }
    public void removeDuck(duck duckToRemove){ allDucks.remove(duckToRemove); }
    public ArrayList<duck> getAllDucks(){ return allDucks; }
    protected int remainingAmmo;
    public void reduceAmmo(){ if(this.remainingAmmo != 0){ this.remainingAmmo -= 1;} }
    public int getRemainingAmmo(){ return this.remainingAmmo; }
    protected Text ammoText;
    protected void updateAmmoText(){ this.ammoText.setText("Ammo Left: " + Integer.toString(getRemainingAmmo())); }
    protected Scene gameScene;
    public Scene getGameScene(){ return this.gameScene; }
    gameLevel(){
        this.allDucks = new ArrayList<duck>();

        /*
        this.ammoText = new Text("Ammo Left: ");
        double ammoTextX = this.ammoText.getBoundsInLocal().getWidth() + DuckHunt.SCALE * 120;
        double ammoTextY = this.ammoText.getBoundsInLocal().getHeight();
        this.ammoText.setLayoutX(ammoTextX);
        this.ammoText.setLayoutY(ammoTextY);
        */
    }
}
