package com.example.djsoftware.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class WallpaperComponent extends StackPane {
    private final Image homeWallpaper = new Image(getClass().getResourceAsStream("/images/wallpaper.jpg"));

    private ImageView homeWallpaperImage;

    public WallpaperComponent() {
        homeWallpaperImage = new ImageView(homeWallpaper);
        homeWallpaperImage.setFitHeight(500);
        homeWallpaperImage.setFitWidth(720);

        setMaxSize(720, 500);
        setStyle("-fx-background-color: #000000;");

        getChildren().addAll(homeWallpaperImage);
    }
}
