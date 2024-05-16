import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class levelOneScreen {
    private ImageView backgroundImageView;
    private ImageView foregroundImageView;
    private ImageView crosshairImageView;
    private Image crosshairImage;
    private static List<File> getForegroundFiles() {
        File backgroundDir = new File("src/assets/foreground");
        File[] backgroundFiles = backgroundDir.listFiles();
        if (backgroundFiles != null) {
            return Arrays.asList(backgroundFiles);
        }
        return new ArrayList<>();
    }
    static List<File> foregroundFiles = getForegroundFiles();
    public Parent createContent() {
        StackPane root = new StackPane();
        final int WIDTH = DuckHunt.WIDTH;
        final int HEIGHT = DuckHunt.HEIGHT;

        // Texts

        // Create the background image view
        backgroundImageView = new ImageView(backgroundSelectionScreen.backgroundFiles.get(DuckHunt.backgroundIndexToPlay).toURI().toString());
        backgroundImageView.setFitWidth(WIDTH);
        backgroundImageView.setFitHeight(HEIGHT);

        // Create the foreground image view
        foregroundImageView = new ImageView(foregroundFiles.get(DuckHunt.backgroundIndexToPlay).toURI().toString());
        foregroundImageView.setFitWidth(WIDTH);
        foregroundImageView.setFitHeight(HEIGHT);

        // Create the crosshair image view
        crosshairImageView = new ImageView(backgroundSelectionScreen.crosshairFiles.get(DuckHunt.crosshairIndexToPlay).toURI().toString());

        crosshairImage = crosshairImageView.getImage();

        // Add the background image view to the root pane
        root.getChildren().addAll(backgroundImageView, foregroundImageView, crosshairImageView);
        //root.setPrefWidth(scaledWidth);
        //root.setPrefHeight(scaledHeight);
        root.setScaleX(DuckHunt.SCALE);
        root.setScaleY(DuckHunt.SCALE);

        // Get the width and height of the cursor image view
        double cursorWidth = crosshairImageView.getBoundsInLocal().getWidth();
        double cursorHeight = crosshairImageView.getBoundsInLocal().getHeight();

        // Create a custom cursor with the same size as the image view
        Cursor customCursor = new ImageCursor(crosshairImage, cursorWidth, cursorHeight);

        root.setCursor(customCursor);
        root.setOnMouseEntered(event -> root.setCursor(customCursor));
        root.setOnMouseMoved(event -> root.setCursor(customCursor));

        return root;
    }
}
