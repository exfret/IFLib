package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    private Label storyText;
    private HBox choices;

    @Override
    public void start(Stage stage) {
        Label title = new Label("The Dark Forest");
        title.getStyleClass().add("title");

        storyText = new Label();
        storyText.getStyleClass().add("story-text");
        storyText.setWrapText(true);
        VBox.setVgrow(storyText, Priority.ALWAYS);

        choices = new HBox(12);
        choices.setAlignment(Pos.CENTER_LEFT);

        VBox root = new VBox(20, title, storyText, choices);
        root.setPadding(new Insets(32));
        root.getStyleClass().add("story-screen");

        Scene scene = new Scene(root, 720, 480);
        scene.getStylesheets().add(
                Objects.requireNonNull(
                        getClass().getResource("/style.css")
                ).toExternalForm()
        );

        showBeginning();

        stage.setTitle("Our Story");
        stage.setScene(scene);
        stage.show();
    }

    private void showBeginning() {
        showPage(
                "You enter a dark forest. Somewhere ahead, a branch snaps.",
                choice("Follow the sound", this::showCreature),
                choice("Go home", this::showHome)
        );
    }

    private void showCreature() {
        showPage(
                "Behind the trees, you find a tiny dragon guarding a silver key.",
                choice("Take the key", this::showKeyEnding),
                choice("Pet the dragon", this::showDragonEnding),
                choice("Go back", this::showBeginning)
        );
    }

    private void showHome() {
        showPage(
                "You return home safely, although you never discover what made the sound.",
                choice("Start again", this::showBeginning)
        );
    }

    private void showKeyEnding() {
        showPage(
                "The dragon lets you take the key. What it opens remains a mystery.",
                choice("Start again", this::showBeginning)
        );
    }

    private void showDragonEnding() {
        showPage(
                "The dragon follows you home. This creates several practical problems.",
                choice("Start again", this::showBeginning)
        );
    }

    private Button choice(String text, Runnable action) {
        Button button = new Button(text);
        button.setOnAction(event -> action.run());
        return button;
    }

    private void showPage(String text, Button... buttons) {
        storyText.setText(text);
        choices.getChildren().setAll(buttons);
    }

    public static void main(String[] args) {
        launch(args);
    }
}