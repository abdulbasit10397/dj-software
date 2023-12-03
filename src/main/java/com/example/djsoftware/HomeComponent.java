package com.example.djsoftware;

import com.example.djsoftware.beans.Song;
import com.example.djsoftware.components.BottomBarComponent;
import com.example.djsoftware.components.SideBarComponent;
import com.example.djsoftware.components.WallpaperComponent;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HomeComponent extends Application {
    public static List<Song> songList = new ArrayList<>();
    public static Pane rootPane;
    public static Stage APPLICATION_STAGE;
    public static WallpaperComponent wallpaperComponent;

    @Override
    public void start(Stage mainStage) {
        APPLICATION_STAGE = mainStage;

        Scene homeScene = getHomeScene();

        mainStage.setTitle("DJ Music");
        mainStage.setScene(homeScene);
        mainStage.show();
    }

    public Scene getHomeScene() {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> System.out.println("Hello World!"));

        Pane sideBar, bottomBar;

        //Getting Panes
        sideBar = new SideBarComponent();
        bottomBar = new BottomBarComponent();
        wallpaperComponent = new WallpaperComponent();

        StackPane.setAlignment(sideBar, Pos.TOP_LEFT);
        StackPane.setAlignment(bottomBar, Pos.BOTTOM_CENTER);

        StackPane.setAlignment(wallpaperComponent, Pos.TOP_RIGHT);

        rootPane = new StackPane();
        rootPane.setStyle("-fx-background-color: #000000;");
        rootPane.getChildren().addAll(sideBar, bottomBar, wallpaperComponent);

        Scene homeScene = new Scene(rootPane, 800, 600);
        return homeScene;
    }


    public static void main(String[] args) {
        launch();
    }
}