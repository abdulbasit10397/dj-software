package com.example.djsoftware.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


public class BottomBarComponent extends StackPane {

    public static final String IDLE_PANE_STYLE = "-fx-background-color: #022A3E; ";
    private static final String HOVERED_PANE_STYLE = "-fx-background-color: gray; ";

    private final Image playImage = new Image(getClass().getResourceAsStream("/images/play.png"));
    private final Image nextImage = new Image(getClass().getResourceAsStream("/images/next.png"));
    private final Image pauseImage = new Image(getClass().getResourceAsStream("/images/pause.png"));

    private GridPane gridPane, currentlyPlayingPane, playPane, nextPane, progressBarPane;
    public static Label currentlyPlayingSongLabel;
    private Label currentlyPlayingLabel;
    private ImageView playIcon, nextIcon;
    public static ProgressBar progressBar;


    public BottomBarComponent() {
        //******************** Attributes ********************

        currentlyPlayingLabel = new Label("Now Playing: ");
        currentlyPlayingLabel.setStyle("-fx-text-fill: white; -fx-font: 20 arial;");

        currentlyPlayingSongLabel = new Label("No song playing right now.");
        currentlyPlayingSongLabel.setStyle("-fx-text-fill: white; -fx-font: 16 arial;");
        currentlyPlayingSongLabel.setMaxSize(650, 50);
        currentlyPlayingSongLabel.setWrapText(true);

        currentlyPlayingPane = new GridPane();
        currentlyPlayingPane.setMaxSize(650, 100);
        currentlyPlayingPane.setAlignment(Pos.TOP_LEFT);
        currentlyPlayingPane.setPadding(new Insets(5, 5, 5, 5));
        currentlyPlayingPane.setVgap(10);
        currentlyPlayingPane.add(currentlyPlayingLabel, 0, 0);
        currentlyPlayingPane.add(currentlyPlayingSongLabel, 0, 1);

        gridPane = new GridPane();
        gridPane.setMaxSize(1000, 90);
        gridPane.setPadding(new Insets(0, 100, 0, 0));
        gridPane.setAlignment(Pos.TOP_RIGHT);
        gridPane.setHgap(20);

        playIcon = new ImageView(playImage);
        playIcon.setFitHeight(50);
        playIcon.setFitWidth(50);
        playIcon.setPreserveRatio(true);

        playPane = new GridPane();
        playPane.setMaxSize(50, 50);
        playPane.setAlignment(Pos.TOP_CENTER);
        playPane.setPadding(new Insets(0, 5, 0, 5));
        playPane.getChildren().add(playIcon);


        nextIcon = new ImageView(nextImage);
        nextIcon.setFitHeight(30);
        nextIcon.setFitWidth(30);
        nextIcon.setPreserveRatio(true);

        nextPane = new GridPane();
        nextPane.setMaxSize(40, 40);
        nextPane.setAlignment(Pos.TOP_CENTER);
        nextPane.setPadding(new Insets(5, 5, 5, 5));
        nextPane.getChildren().add(nextIcon);


        gridPane.add(playPane, 0, 0);
        gridPane.add(nextPane, 1, 0);


        progressBar = new ProgressBar(0);
        progressBar.setPrefSize(200, 10);

        progressBarPane = new GridPane();
        progressBarPane.setMaxSize(300, 30);
        progressBarPane.setPadding(new Insets(5, 20, 10, 50));
        StackPane.setAlignment(progressBarPane, Pos.BOTTOM_RIGHT);
        progressBarPane.getChildren().add(progressBar);


        setMaxSize(800, 100);
        setStyle("-fx-background-color: #022A3E;");
        getChildren().addAll(currentlyPlayingPane, gridPane, progressBarPane);

        //******************** Actions ********************

        playPane.setOnMouseEntered(e -> playPane.setStyle(HOVERED_PANE_STYLE));
        playPane.setOnMouseExited(e -> playPane.setStyle(IDLE_PANE_STYLE));

        nextPane.setOnMouseEntered(e -> nextPane.setStyle(HOVERED_PANE_STYLE));
        nextPane.setOnMouseExited(e -> nextPane.setStyle(IDLE_PANE_STYLE));

        playPane.setOnMouseClicked(e -> {

            if (playIcon.getImage().equals(playImage)) {
                playIcon.setImage(pauseImage);
                if (SideBarComponent.playlistComponent != null) {
                    SideBarComponent.playlistComponent.playSong();
                }

            } else {
                SideBarComponent.playlistComponent.pauseSong();
                playIcon.setImage(playImage);
            }
        });

        nextPane.setOnMouseClicked(e -> {
            SideBarComponent.playlistComponent.playNextSong();
        });
    }
}
