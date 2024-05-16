import javafx.scene.image.Image;
import java.io.File;

public class diagonalDuck extends duck{
    private double horizontalSpeed;
    private double verticalSpeed;
    protected void loadAnimationFrames(){
        // Load animation frames
        this.animationFrames = new Image[3];
        for (int i = 0; i < 3; i++) {
            String framePath = "src/assets/duck_black/" + (i + 1) + ".png";
            this.animationFrames[i] = new Image(new File(framePath).toURI().toString());
        }
    }
    public diagonalDuck(double initialX, double initialY, String duckColor) {
        super(initialX, initialY, duckColor);
        this.horizontalSpeed = 5 * DuckHunt.SCALE;
        this.verticalSpeed = -5 * DuckHunt.SCALE;
    }
    public void flyDuck() {
        if(!this.isShot){
            // Current position of the duck
            double x = this.getLayoutX();
            double y = this.getLayoutY();

            // If the duck has reached the edges
            boolean atRightBorder = x >= ((DuckHunt.WIDTH * DuckHunt.SCALE) - this.getWidth());
            boolean atLeftBorder = x <= 0;
            boolean atTopBorder = y <= 0;
            boolean atBottomBorder = y >= (DuckHunt.HEIGHT * DuckHunt.SCALE);

            // Calculate the new position of the duck
            x += (horizontalSpeed * this.xDirection);
            y += (verticalSpeed * this.yDirection);

            // Check if the duck hits the walls and change direction accordingly
            if (atRightBorder) {
                this.xDirection = -1; // Reverse direction to move left
                turnBack();
            } else if (atLeftBorder) {
                this.xDirection = 1; // Reverse direction to move right
                turnBack();
            }

            if (atTopBorder) {
                this.yDirection = -1;
                this.duckView.setScaleY(this.yDirection);
            } else if (atBottomBorder) {
                this.yDirection = 1;
                this.duckView.setScaleY(this.yDirection);
            }

            // Set the new position of the duck
            this.setLayoutX(x);
            this.setLayoutY(y);
        } else {
            this.fallDuck();
        }
    }
}
