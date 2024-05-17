import com.sun.corba.se.impl.orb.ParserTable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;

public class levelTwo extends gameLevel{
    private double scaledWidth = DuckHunt.WIDTH * DuckHunt.SCALE;
    private double scaledHeight = DuckHunt.HEIGHT * DuckHunt.SCALE;
    public levelTwo(){
        super();
        this.remainingAmmo = 4;
        this.ammoText = new Text("Ammo Left: " + Integer.toString(this.getRemainingAmmo()));
    }
    public Scene returnLevelTwo(){
        Pane root = new Pane();
        ImageView chosenBackgroundImageView = new ImageView((backgroundSelectionScreen.backgroundFiles.get(DuckHunt.backgroundIndexToPlay).toURI().toString()));
        chosenBackgroundImageView.setFitWidth(scaledWidth);
        chosenBackgroundImageView.setFitHeight(scaledHeight);

        ImageView chosenCrosshairImageView = new ImageView((backgroundSelectionScreen.crosshairFiles.get(DuckHunt.crosshairIndexToPlay).toURI().toString()));

        ImageView chosenForegroundImageView = new ImageView((levelOneScreen.foregroundFiles.get(DuckHunt.backgroundIndexToPlay).toURI().toString()));
        chosenForegroundImageView.setFitWidth(scaledWidth);
        chosenForegroundImageView.setFitHeight(scaledHeight);

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE){
                root.setCursor(Cursor.DEFAULT);
            }
        });

        // Texts
        Text levelText = new Text("Level 2");
        levelText.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE * 8));
        levelText.setFill(Color.ORANGE);

        // Set the layout constraints for the levelText
        double levelTextX = levelText.getBoundsInLocal().getWidth() + DuckHunt.SCALE * 60;
        double levelTextY = levelText.getBoundsInLocal().getHeight();
        levelText.setLayoutX(levelTextX);
        levelText.setLayoutY(levelTextY);

        // Set the layout constraints for the ammoText
        this.ammoText.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE * 6));
        this.ammoText.setFill(Color.ORANGE);
        double ammoTextX = this.ammoText.getBoundsInLocal().getWidth() + DuckHunt.SCALE * 120;
        double ammoTextY = this.ammoText.getBoundsInLocal().getHeight();
        ammoText.setLayoutX(ammoTextX);
        ammoText.setLayoutY(ammoTextY);

        // Create a custom cursor with the same size as the image view
        Image cursorImage = chosenCrosshairImageView.getImage();
        double cursorWidth = scaledWidth * scaledHeight;
        double cursorHeight = scaledWidth * scaledHeight;
        Cursor customCursor = new ImageCursor(cursorImage, cursorWidth, cursorHeight);

        // Set the custom cursor when the mouse is inside the game window
        root.setOnMouseEntered(event -> root.setCursor(customCursor));
        root.setOnMouseMoved(event -> root.setCursor(customCursor));

        // Set the default cursor when the mouse is outside the game window
        root.setOnMouseExited(event -> root.setCursor(Cursor.DEFAULT));

        horizontalDuck duckOne = new horizontalDuck(100, 100, "blue");
        this.addDuck(duckOne);

        diagonalDuck duckTwo = new diagonalDuck(50, 150, "black");
        this.addDuck(duckTwo);

        root.getChildren().addAll(chosenBackgroundImageView, duckOne, duckTwo, chosenForegroundImageView, levelText, this.ammoText);

        for (duck eachDuck : this.getAllDucks()){
            // Create a Timeline animation to move each duck
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> eachDuck.flyDuck()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

            // Create a Timeline animation to update each duck animation
            Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(200), event -> eachDuck.updateAnimation()));
            timeline2.setCycleCount(Timeline.INDEFINITE);
            timeline2.play();
        }

        Scene levelTwoScene = new Scene(root, scaledWidth, scaledHeight);

        // Shooting
        if (this.getAllDucks().size() != 0) {
            root.setOnMouseClicked(event -> {
                // Get the mouse click coordinates
                double mouseX = event.getX();
                double mouseY = event.getY();

                // Perform the shooting action
                DuckHunt.shootGun(mouseX, mouseY, this, root);
            });
        }

        return levelTwoScene;
    }
}
