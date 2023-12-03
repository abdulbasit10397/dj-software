package com.example.djsoftware.components;

import com.example.djsoftware.HomeComponent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class SideBarComponent extends AnchorPane {
    public static MusicLibraryComponent musicLibraryComponent = null;
    public static PlaylistComponent playlistComponent = null;
    public static WallpaperComponent wallpaperComponent = HomeComponent.wallpaperComponent;

    public static final String IDLE_PANE_STYLE = "-fx-background-color: #2E2F30; ";
    private static final String HOVERED_PANE_STYLE = "-fx-background-color: gray; ";

    private final Image homeImage = new Image(getClass().getResourceAsStream("/images/home.png"));
    private final Image musicImage = new Image(getClass().getResourceAsStream("/images/music.png"));
    private final Image playlistImage = new Image(getClass().getResourceAsStream("/images/playlist.png"));


    private GridPane gridPane, musicPane, homePane, playlistPane;
    private ImageView musicIcon, homeIcon, playlistIcon;

    public SideBarComponent() {

        //******************** Attributes ********************
        gridPane = new GridPane();
        gridPane.setMaxSize(80, 500);
        gridPane.setAlignment(Pos.TOP_CENTER);

        homeIcon = new ImageView(homeImage);
        homeIcon.setFitHeight(50);
        homeIcon.setFitWidth(50);
        homeIcon.setPreserveRatio(true);

        homePane = new GridPane();
        homePane.setMaxSize(80, 100);
        homePane.setAlignment(Pos.CENTER);
        homePane.setPadding(new Insets(15, 15, 15, 15));
        homePane.getChildren().add(homeIcon);

        musicIcon = new ImageView(musicImage);
        musicIcon.setFitHeight(50);
        musicIcon.setFitWidth(50);
        musicIcon.setPreserveRatio(true);

        musicPane = new GridPane();
        musicPane.setMaxSize(80, 100);
        musicPane.setAlignment(Pos.CENTER);
        musicPane.setPadding(new Insets(15, 15, 15, 15));
        musicPane.getChildren().add(musicIcon);


        playlistIcon = new ImageView(playlistImage);
        playlistIcon.setFitHeight(50);
        playlistIcon.setFitWidth(50);
        playlistIcon.setPreserveRatio(true);

        playlistPane = new GridPane();
        playlistPane.setMaxSize(80, 100);
        playlistPane.setAlignment(Pos.CENTER);
        playlistPane.setPadding(new Insets(15, 15, 15, 15));
        playlistPane.getChildren().add(playlistIcon);


        gridPane.add(homePane, 0, 1);
        gridPane.add(musicPane, 0, 2);
        gridPane.add(playlistPane, 0, 3);

        setMaxSize(80, 500);
        setStyle("-fx-background-color: #2E2F30;");
        getChildren().add(gridPane);


        //******************** Actions ********************
        musicPane.setOnMouseEntered(e -> musicPane.setStyle(HOVERED_PANE_STYLE));
        musicPane.setOnMouseExited(e -> musicPane.setStyle(IDLE_PANE_STYLE));

        homePane.setOnMouseEntered(e -> homePane.setStyle(HOVERED_PANE_STYLE));
        homePane.setOnMouseExited(e -> homePane.setStyle(IDLE_PANE_STYLE));

        playlistPane.setOnMouseEntered(e -> playlistPane.setStyle(HOVERED_PANE_STYLE));
        playlistPane.setOnMouseExited(e -> playlistPane.setStyle(IDLE_PANE_STYLE));

        homePane.setOnMouseClicked(e -> {
            if (wallpaperComponent == null) {
                wallpaperComponent = new WallpaperComponent();
                StackPane.setAlignment(wallpaperComponent, Pos.TOP_RIGHT);
                HomeComponent.rootPane.getChildren().add(wallpaperComponent);

            } else {
                if (playlistComponent != null) playlistComponent.setVisible(false);
                if (musicLibraryComponent != null) musicLibraryComponent.setVisible(false);
                wallpaperComponent.setVisible(true);
            }
        });


        musicPane.setOnMouseClicked(e -> {
            if (musicLibraryComponent == null) {
                musicLibraryComponent = new MusicLibraryComponent();
                StackPane.setAlignment(musicLibraryComponent, Pos.TOP_RIGHT);
                HomeComponent.rootPane.getChildren().add(musicLibraryComponent);
            } else {
                if (wallpaperComponent != null) wallpaperComponent.setVisible(false);
                if (playlistComponent != null) playlistComponent.setVisible(false);
                musicLibraryComponent.setVisible(true);
            }
        });

        playlistPane.setOnMouseClicked(e -> {
            if (playlistComponent == null) {
                playlistComponent = new PlaylistComponent();
                StackPane.setAlignment(playlistComponent, Pos.TOP_RIGHT);
                HomeComponent.rootPane.getChildren().add(playlistComponent);
            } else {
                if (wallpaperComponent != null) wallpaperComponent.setVisible(false);
                if (musicLibraryComponent != null) musicLibraryComponent.setVisible(false);
                playlistComponent.setVisible(true);
            }
        });
    }
}
