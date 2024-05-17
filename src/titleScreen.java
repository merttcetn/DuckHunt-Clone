import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.File;

public class titleScreen {
    private static double scaledWidth = DuckHunt.WIDTH * DuckHunt.SCALE;
    private static double scaledHeight = DuckHunt.HEIGHT * DuckHunt.SCALE;

    /*
    private Text createFlashingText(String text) {
        Text flashingText = new Text(text);
        flashingText.setFont(Font.font("Arial", FontWeight.BOLD, 10));
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
    */
    public Parent createContent() {
        StackPane root = new StackPane();
        final int WIDTH = DuckHunt.WIDTH;
        final int HEIGHT = DuckHunt.HEIGHT;

        // Load the image from a file
        File imageFile = new File("src/assets/favicon/1.png");
        Image image = new Image(imageFile.toURI().toString());
        ImageView imageView = new ImageView(image);

        // Set the dimensions and position of the ImageView
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        // Load and play the background music in a loop
        String musicFile = "src/assets/effects/Title.mp3";
        AudioClip audioClip = new AudioClip(new File(musicFile).toURI().toString());
        audioClip.setCycleCount(AudioClip.INDEFINITE);
        audioClip.setVolume(DuckHunt.VOLUME);
        audioClip.play();

        // Create the flashing texts
        Text playText = DuckHunt.createFlashingText("PRESS ENTER TO PLAY", 10);
        Text exitText = DuckHunt.createFlashingText("PRESS ESC TO EXIT", 10);
        playText.setTranslateY(DuckHunt.SCALE * 20 / 3);
        exitText.setTranslateY(DuckHunt.SCALE * 20 / 3 + 10); // Add vertical spacing between the texts

        // Add the image and texts to the root
        root.getChildren().addAll(imageView, playText, exitText);

        // Center the image and texts within the stack pane
        StackPane.setAlignment(imageView, javafx.geometry.Pos.CENTER);
        StackPane.setAlignment(playText, javafx.geometry.Pos.CENTER);
        StackPane.setAlignment(exitText, javafx.geometry.Pos.CENTER);

        // Set the dimensions of the root pane based on the desired scaled dimensions
        root.setPrefWidth(scaledWidth);
        root.setPrefHeight(scaledHeight);
        root.setScaleX(DuckHunt.SCALE);
        root.setScaleY(DuckHunt.SCALE);

        return root;
    }
}
