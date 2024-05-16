import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class backgroundSelectionScreen {
    private static double scaledWidth = DuckHunt.WIDTH * DuckHunt.SCALE;
    private static double scaledHeight = DuckHunt.HEIGHT * DuckHunt.SCALE;
    private static int selectedBackgroundIndex = 0;  // Initial background index
    private static int selectedCrosshairIndex = 0;  // Initial crosshair index
    public static int getSelectedBackgroundIndex(){ return selectedBackgroundIndex; }
    public static int getSelectedCrosshairIndex(){ return selectedCrosshairIndex; }
    public void nextBackground(){
        selectedBackgroundIndex += 1;
        selectedBackgroundIndex = selectedBackgroundIndex % backgroundFiles.size();
        updateBackground();
    }
    public void previousBackground(){
        selectedBackgroundIndex -= 1;
        selectedBackgroundIndex += backgroundFiles.size();
        selectedBackgroundIndex = selectedBackgroundIndex % backgroundFiles.size();
        updateBackground();
    }
    public void nextCrosshair(){
        selectedCrosshairIndex += 1;
        selectedCrosshairIndex = selectedCrosshairIndex % crosshairFiles.size();
        updateCrosshair();
    }
    public void previousCrosshair(){
        selectedCrosshairIndex -= 1;
        selectedCrosshairIndex += crosshairFiles.size();
        selectedCrosshairIndex = selectedCrosshairIndex % crosshairFiles.size();
        updateCrosshair();
    }
    private ImageView backgroundImageView;
    public static List<File> backgroundFiles = getBackgroundFiles();
    private ImageView crosshairImageView;
    public static List<File> crosshairFiles = getCrosshairFiles();
    private static List<File> getBackgroundFiles() {
        File backgroundDir = new File("src/assets/background");
        File[] backgroundFiles = backgroundDir.listFiles();
        if (backgroundFiles != null) {
            return Arrays.asList(backgroundFiles);
        }
        return new ArrayList<>();
    }
    private static List<File> getCrosshairFiles() {
        File crosshairDir = new File("src/assets/crosshair");
        File[] crosshairFiles = crosshairDir.listFiles();
        if (crosshairFiles != null) {
            return Arrays.asList(crosshairFiles);
        }
        return new ArrayList<>();
    }
    public Parent createContent() {
        StackPane root = new StackPane();
        final int WIDTH = DuckHunt.WIDTH;
        final int HEIGHT = DuckHunt.HEIGHT;

        // Texts
        Text navigateText = createSelectionText("USE ARROW KEYS TO NAVIGATE");
        Text startText = createSelectionText("PRESS ENTER TO START");
        Text exitText = createSelectionText("PRESS ESC TO EXIT");
        navigateText.setTranslateY(-1 * HEIGHT / 2 + DuckHunt.SCALE * 2);
        startText.setTranslateY(-1 * HEIGHT / 2 + DuckHunt.SCALE * 2 + 10);
        exitText.setTranslateY(-1 * HEIGHT / 2 + DuckHunt.SCALE * 2 + 20);

        // Create the background image view
        backgroundImageView = new ImageView(backgroundSelectionScreen.getBackgroundFiles().get(selectedBackgroundIndex).toURI().toString());
        backgroundImageView.setFitWidth(WIDTH);
        backgroundImageView.setFitHeight(HEIGHT);

        // Create the crosshair image view
        crosshairImageView = new ImageView(backgroundSelectionScreen.getCrosshairFiles().get(selectedCrosshairIndex).toURI().toString());
        // crosshairImageView.setFitWidth(10);
        // crosshairImageView.setFitHeight(10);

        // Update the background image with the initial selected background and crosshair
        updateBackground();
        updateCrosshair();

        // Add the background image view to the root pane
        root.getChildren().addAll(backgroundImageView, crosshairImageView, navigateText, startText, exitText);
        //root.setPrefWidth(scaledWidth);
        //root.setPrefHeight(scaledHeight);
        root.setScaleX(DuckHunt.SCALE);
        root.setScaleY(DuckHunt.SCALE);

        return root;
    }
    public void updateBackground() {
        Image backgroundImage = new Image(backgroundFiles.get(selectedBackgroundIndex).toURI().toString());
        backgroundImageView.setImage(backgroundImage);
        backgroundImageView.setFitWidth(DuckHunt.WIDTH);
        backgroundImageView.setFitHeight(DuckHunt.HEIGHT);
    }
    public void updateCrosshair() {
        Image crosshairImage = new Image(crosshairFiles.get(selectedCrosshairIndex).toURI().toString());
        crosshairImageView.setImage(crosshairImage);
    }
    private Text createSelectionText(String text) {
        Text selectionText = new Text(text);
        selectionText.setFont(Font.font("Arial", FontWeight.BOLD, DuckHunt.SCALE * 2));
        selectionText.setFill(Color.ORANGE);
        // selectionText.setX((DuckHunt.WIDTH - selectionText.getLayoutBounds().getWidth()) / 2);
        // selectionText.setY(0);
        return selectionText;
    }
}
