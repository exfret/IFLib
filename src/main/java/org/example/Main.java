package org.example;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.iflib.Resource;

import java.net.URL;

public class Main extends Application {

    private final ObservableList<Resource> resources =
            FXCollections.observableArrayList(
                    new Resource("gold", "Gold"),
                    new Resource("food", "Food"),
                    new Resource("wood", "Wood")
            );

    private Label storyText;
    private HBox choices;
    private TableView<Resource> resourceTable;

    @Override
    public void start(Stage stage) {
        Tab storyTab = createStoryTab();
        Tab resourcesTab = createResourcesTab();

        TabPane tabPane = new TabPane(storyTab, resourcesTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Scene scene = new Scene(tabPane, 720, 480);

        URL stylesheet = getClass().getResource("/style.css");

        if (stylesheet != null) {
            scene.getStylesheets().add(stylesheet.toExternalForm());
        }

        showBeginning();

        stage.setTitle("IFLib");
        stage.setScene(scene);
        stage.show();
    }

    private Tab createStoryTab() {
        Label title = new Label("The Dark Forest");
        title.getStyleClass().add("title");

        storyText = new Label();
        storyText.setWrapText(true);
        storyText.getStyleClass().add("story-text");

        choices = new HBox(12);
        choices.setAlignment(Pos.CENTER_LEFT);

        VBox storyLayout = new VBox(
                20,
                title,
                storyText,
                choices
        );

        storyLayout.setPadding(new Insets(32));
        storyLayout.getStyleClass().add("story-screen");

        VBox.setVgrow(storyText, Priority.ALWAYS);

        return new Tab("Story", storyLayout);
    }

    private Tab createResourcesTab() {
        resourceTable = new TableView<>(resources);

        resourceTable.setPlaceholder(
                new Label("No resources")
        );

        resourceTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        TableColumn<Resource, String> nameColumn =
                new TableColumn<>("Resource");

        nameColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(
                        cell.getValue().toString()
                )
        );

        TableColumn<Resource, String> amountColumn =
                new TableColumn<>("Amount");

        amountColumn.setCellValueFactory(cell ->
                new ReadOnlyStringWrapper(
                        cell.getValue().getValue()
                )
        );

        amountColumn.setStyle(
                "-fx-alignment: CENTER-RIGHT;"
        );

        resourceTable.getColumns().add(nameColumn);
        resourceTable.getColumns().add(amountColumn);

        VBox resourcesLayout = new VBox(resourceTable);
        resourcesLayout.setPadding(new Insets(24));

        VBox.setVgrow(resourceTable, Priority.ALWAYS);

        return new Tab("Resources", resourcesLayout);
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
                "You return home safely.",
                choice("Start again", this::showBeginning)
        );
    }

    private void showKeyEnding() {
        showPage(
                "The dragon allows you to take the mysterious silver key.",
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

        button.setOnAction(event ->
                action.run()
        );

        return button;
    }

    private void showPage(
            String text,
            Button... buttons
    ) {
        storyText.setText(text);
        choices.getChildren().setAll(buttons);
    }

    public static void main(String[] args) {
        launch(args);
    }
}