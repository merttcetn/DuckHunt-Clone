import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import java.io.File;
public class duck extends Pane {
    protected ImageView duckView;
    protected int xDirection;
    protected int yDirection;
    private int frameIndex = 0;
    protected Image[] animationFrames;
    private String duckColor;
    boolean isShot = false;
    private boolean isFalling = false;
    protected void loadAnimationFrames(){}
    protected String getDuckColor(){ return this.duckColor; }
    public duck(double initialX, double initialY, String duckColor) {
        // DuckHunt.allDucks.add(this);
        this.setLayoutX(initialX);
        this.setLayoutY(initialY);
        this.duckColor = duckColor;
        this.loadAnimationFrames();
        this.xDirection = 1;
        this.yDirection = 1;

        duckView = new ImageView(new Image(new File("src/assets/duck_" + this.duckColor + "/4.png").toURI().toString()));
        duckView.setFitWidth(25 * DuckHunt.SCALE); // Set the desired width of the duck
        duckView.setPreserveRatio(true); // Preserve the aspect ratio of the duck image

        this.getChildren().add(duckView);
    }
    public void turnBack(){ this.duckView.setScaleX(this.xDirection); }
    public void flyDuck() {}
    public void updateAnimation() {
        if (!this.isShot){
            // Increment the frame index
            frameIndex++;
            // Check if the frame index exceeds the number of animation frames
            if (frameIndex >= animationFrames.length) {
                frameIndex = 0;
            }
            // Update the image view with the new frame
            duckView.setImage(animationFrames[frameIndex]);
        }
    }
    public void duckDies(){
        this.isShot = true;
        AudioClip duckFalls = new AudioClip(new File("src/assets/effects/DuckFalls.mp3").toURI().toString());
        duckFalls.setVolume(0.025);
        duckFalls.play();

        duckView.setImage(new Image(new File("src/assets/duck_"+ this.duckColor +"/7.png").toURI().toString()));

        // Start falling animation after a short delay
        Timeline fallingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.3), event -> {
                    isFalling = true;
                    duckView.setImage(new Image(new File("src/assets/duck_"+ this.duckColor +"/8.png").toURI().toString()));
                    duckView.setFitHeight(25 * DuckHunt.SCALE);
                    duckView.setPreserveRatio(true);
                })
        );
        fallingTimeline.setCycleCount(Timeline.INDEFINITE);
        fallingTimeline.play();
    }
    public void fallDuck() {
        double fallSpeed = 6 * DuckHunt.SCALE;

        if (isFalling) {
            double x = this.getLayoutX();
            double y = this.getLayoutY();

            // Calculate the new position of the duck
            y += fallSpeed;

            // Set the new position of the duck
            this.setLayoutX(x);
            this.setLayoutY(y);
        }
    }
}
