import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class DuckHunt extends Application{
    public static final int WIDTH = 160 * 4 / 3;
    public static final int HEIGHT = 150 * 4 / 3;
    public static double SCALE = 3;  // Default scale value
    public static double VOLUME = 0.025;  // Default volume value
    double scaledWidth = WIDTH * SCALE;
    double scaledHeight = HEIGHT * SCALE;
    public static int backgroundIndexToPlay;
    public static int crosshairIndexToPlay;
    public static Stage gameStage;
    public static int currentLevel;
    public static Text createFlashingText(String text, int textSize) {
        Text flashingText = new Text(text);
        flashingText.setFont(Font.font("Arial", FontWeight.BOLD, textSize));
        flashingText.setFill(Color.ORANGE);

        // Create a timeline animation for the flashing effect
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> flashingText.setVisible(false)),
                new KeyFrame(Duration.seconds(1), e -> flashingText.setVisible(true))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        return flashingText;
    }
    public static void shootGun(double mouseX, double mouseY, gameLevel theLevel, Pane rootNode) {
        AudioClip gunshot = new AudioClip(new File("src/assets/effects/Gunshot.mp3").toURI().toString());
        gunshot.setVolume(0.025);

        theLevel.reduceAmmo();

        // Iterate through all the ducks on the screen
        for (duck eachDuck : theLevel.getAllDucks()) {
            // Get the position of the duck
            double duckX = eachDuck.getLayoutX();
            double duckY = eachDuck.getLayoutY();

            gunshot.play();

            // Check if the clicked coordinates are within the duck's boundaries
            if (mouseX >= duckX && mouseX <= duckX + eachDuck.getWidth() && mouseY >= duckY && mouseY <= duckY + eachDuck.getHeight()) {
                // Duck hit! Perform actions for duck hit
                eachDuck.duckDies(); // Call a method to handle the duck's death
                theLevel.removeDuck(eachDuck);
                // Perform other actions like updating score, playing sound, etc.
                break; // Exit the loop after shooting the first duck found
            }
        }

        if (theLevel.getRemainingAmmo() == 0 && theLevel.getAllDucks().size() != 0) {
            // Game is over!
            System.out.println("GAME OVER!");
        } else if (theLevel.getAllDucks().size() == 0) {
            // Create the "YOU WIN!" text
            Text winText = new Text("YOU WIN!");
            winText.setFont(Font.font("Arial", FontWeight.BOLD, 48));
            winText.setFill(Color.ORANGE);

            // Position the text in the middle of the screen
            double centerX = DuckHunt.WIDTH * DuckHunt.SCALE / 2;
            double centerY = DuckHunt.HEIGHT * DuckHunt.SCALE / 2;
            winText.setLayoutX(centerX - winText.getBoundsInLocal().getWidth() / 2);
            winText.setLayoutY(centerY);

            Text pressEnterText = createFlashingText("Press ENTER to play next level", 35);
            pressEnterText.setLayoutX(centerX - pressEnterText.getBoundsInLocal().getWidth() / 2);
            pressEnterText.setLayoutY(centerY + DuckHunt.SCALE * 11);

            // Add the text to the root pane of the current scene
            rootNode.getChildren().addAll(winText, pressEnterText);

            theLevel.getGameScene().setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    if (theLevel.getAllDucks().isEmpty()) {
                        System.out.println("Go to level " + Integer.toString(currentLevel));

                        switch (currentLevel) {
                            case 1:
                                levelTwo secondLevelScene = new levelTwo();
                                DuckHunt.gameStage.setScene(secondLevelScene.returnLevelTwo());
                                currentLevel += 1;
                                break;
                        }
                    }
                } else if (event.getCode() == KeyCode.ESCAPE) {
                    theLevel.getGameScene().setCursor(Cursor.DEFAULT);
                }
            });
        }
        theLevel.updateAmmoText();
    }
    @Override
    public void start(Stage stage) throws Exception {
        currentLevel = 1;
        gameStage = stage;
        backgroundIndexToPlay = 0;
        crosshairIndexToPlay = 0;

        // Create instances of screens
        titleScreen titleScreen = new titleScreen();
        backgroundSelectionScreen backgroundSelectionScreen = new backgroundSelectionScreen();
        levelOneScreen levelOneScreen = new levelOneScreen();

        // Setting up scenes
        Scene titleScene = new Scene(titleScreen.createContent(), scaledWidth, scaledHeight);
        Scene backgroundSelectionScene = new Scene(backgroundSelectionScreen.createContent(), scaledWidth, scaledHeight);
        Scene levelOneScene = new Scene(levelOneScreen.createContent(), scaledWidth, scaledHeight);

        // Audio clips
        AudioClip introMusic = new AudioClip(new File("src/assets/effects/Intro.mp3").toURI().toString());
        AudioClip titleMusic = new AudioClip(new File("src/assets/effects/Title.mp3").toURI().toString());

        titleScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                introMusic.setVolume(VOLUME);
                introMusic.play();
                titleMusic.stop();
                stage.setScene(backgroundSelectionScene);
            } else if (event.getCode() == KeyCode.ESCAPE){  // Close the game
                stage.close();
            }
        });

        backgroundSelectionScene.setOnKeyPressed(event -> {
            if ((event.getCode() == KeyCode.ENTER)){ // && (!introMusic.isPlaying()) ekleeeeee!!!!!!!!

                backgroundIndexToPlay = backgroundSelectionScreen.getSelectedBackgroundIndex();
                crosshairIndexToPlay = backgroundSelectionScreen.getSelectedCrosshairIndex();

                levelOne firstLevelScene = new levelOne();
                stage.setScene(firstLevelScene.returnLevelOne());

            } else if (event.getCode() == KeyCode.RIGHT){
                backgroundSelectionScreen.nextBackground();
            } else if (event.getCode() == KeyCode.LEFT){
                backgroundSelectionScreen.previousBackground();
            } else if (event.getCode() == KeyCode.UP){
                backgroundSelectionScreen.nextCrosshair();
            } else if (event.getCode() == KeyCode.DOWN){
                backgroundSelectionScreen.previousCrosshair();
            } else if (event.getCode() == KeyCode.ESCAPE){
                introMusic.stop();
                titleMusic.setVolume(VOLUME);
                titleMusic.play();
                titleMusic.setCycleCount(AudioClip.INDEFINITE);
                stage.setScene(titleScene);
            }
        });

        stage.setTitle("HUBBM DuckHunt");
        stage.setScene(titleScene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) { launch(args); }
}