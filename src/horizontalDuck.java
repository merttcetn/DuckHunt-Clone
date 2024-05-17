import javafx.scene.image.Image;

import java.io.File;

public class horizontalDuck extends duck{
    private double horizontalSpeed;
    protected void loadAnimationFrames(){
        // Load animation frames
        this.animationFrames = new Image[3];
        for (int i = 0; i < 3; i++) {
            String framePath = "src/assets/duck_" + this.getDuckColor() + "/" + (i + 4) + ".png";
            this.animationFrames[i] = new Image(new File(framePath).toURI().toString());
        }
    }
    public horizontalDuck(double initialX, double initialY, String duckColor) {
        super(initialX, initialY, duckColor);
        this.horizontalSpeed = 5 * DuckHunt.SCALE;
    }
    public void flyDuck() {
        if(!this.isShot){
            // Current position of the duck
            double x = this.getLayoutX();

            // If the duck has reached the edges
            boolean atRightBorder = x >= ((DuckHunt.WIDTH * DuckHunt.SCALE) - this.getWidth());
            boolean atLeftBorder = x <= 0;

            // Calculate the new position of the duck
            x += (horizontalSpeed * this.xDirection);

            // Check if the duck hits the walls and change direction accordingly
            if (atRightBorder) {
                this.xDirection = -1; // Reverse direction to move left
                turnBack();
            } else if (atLeftBorder) {
                this.xDirection = 1; // Reverse direction to move right
                turnBack();
            }

            // Set the new position of the duck
            this.setLayoutX(x);
        } else {
            this.fallDuck();
        }
    }
}
