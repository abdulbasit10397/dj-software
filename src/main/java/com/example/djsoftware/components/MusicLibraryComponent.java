package com.example.djsoftware.components;

import com.example.djsoftware.HomeComponent;
import com.example.djsoftware.beans.Song;
import com.example.djsoftware.controllers.MusicComponentController;
import com.example.djsoftware.utils.DJUtils;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import java.io.File;


public class MusicLibraryComponent extends StackPane {

    private final Image ADD_MUSIC_IMAGE = new Image(getClass().getResourceAsStream("/images/add music.png"));
    private final Image MUSIC_SOURCE_IMAGE = new Image(getClass().getResourceAsStream("/images/music source.jpg"));

    public static final String IDLE_PANE_STYLE = "-fx-background-color: #000000; ";
    private static final String HOVERED_PANE_STYLE = "-fx-background-color: gray; ";

    public static String filePath, musicSourcePath;

    private Pane menuBar, tablePane;
    private GridPane addMusic, musicSource;
    private ImageView addMusicIcon, musicSourceIcon;
    private Label addMusicLabel, menuBarLabel, musicSourceLabel;


    public MusicLibraryComponent() {

        //******************** Attributes ********************

        //******************** Start of Menu Bar ********************
        menuBarLabel = new Label("My Music Library");
        menuBarLabel.setStyle("-fx-text-fill: white; -fx-font: 24 arial;");
        menuBarLabel.relocate(10, 25);

        musicSourceLabel = new Label("Select\nSource");
        musicSourceLabel.setStyle("-fx-text-fill: white; -fx-font: 14 arial;");

        musicSourceIcon = new ImageView(MUSIC_SOURCE_IMAGE);
        musicSourceIcon.setFitHeight(50);
        musicSourceIcon.setFitWidth(50);
        musicSourceIcon.setPreserveRatio(true);

        musicSource = new GridPane();
        musicSource.setMaxSize(120, 60);
        musicSource.setPadding(new Insets(3, 3, 3, 10));
        musicSource.setStyle(IDLE_PANE_STYLE);
        StackPane.setAlignment(musicSource, Pos.TOP_RIGHT);
        musicSource.setHgap(10);
        musicSource.setLayoutX(480);

        musicSource.add(musicSourceLabel, 0, 0);
        musicSource.add(musicSourceIcon, 1, 0);


        menuBar = new AnchorPane();
        menuBar.setMaxSize(600, 60);
        menuBar.setStyle(IDLE_PANE_STYLE);
        StackPane.setAlignment(menuBar, Pos.TOP_LEFT);
        menuBar.getChildren().addAll(menuBarLabel, musicSource);


        addMusicLabel = new Label("Add\nMusic");
        addMusicLabel.setStyle("-fx-text-fill: white; -fx-font: 14 arial;");

        addMusicIcon = new ImageView(ADD_MUSIC_IMAGE);
        addMusicIcon.setFitHeight(50);
        addMusicIcon.setFitWidth(50);
        addMusicIcon.setPreserveRatio(true);

        addMusic = new GridPane();
        addMusic.setMaxSize(120, 55);
        addMusic.setPadding(new Insets(3, 3, 3, 10));
        addMusic.setStyle(IDLE_PANE_STYLE);
        StackPane.setAlignment(addMusic, Pos.TOP_RIGHT);
        addMusic.setHgap(10);

        addMusic.add(addMusicLabel, 0, 0);
        addMusic.add(addMusicIcon, 1, 0);

        //******************** End of Menu Bar ********************


        //******************** Start of Songs Pane ********************

        TableView tableView = new TableView();

        TableColumn<Song, String> title = new TableColumn<>("Title");
        title.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Song, String> artist = new TableColumn<>("Artist Name");
        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));

        TableColumn<Song, Double> duration = new TableColumn<>("Duration");
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        TableColumn<Song, String> audioFileName = new TableColumn<>("MP3 File Name");
        audioFileName.setCellValueFactory(new PropertyValueFactory<>("audioFileName"));

        tableView.getColumns().addAll(title, artist, duration, audioFileName);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);



        tablePane = new StackPane();
        tablePane.setMaxSize(720, 440);
        tablePane.setStyle("-fx-background-color: #000000;");
        StackPane.setAlignment(tablePane, Pos.BOTTOM_CENTER);
        tablePane.getChildren().add(tableView);

        //******************** End of Songs Table ********************


        setMaxSize(720, 500);
        setStyle("-fx-background-color: #000000;");

        getChildren().addAll(menuBar, addMusic, tablePane);


        //******************** Actions ********************
        addMusic.setOnMouseEntered(e -> addMusic.setStyle(HOVERED_PANE_STYLE));
        addMusic.setOnMouseExited(e -> addMusic.setStyle(IDLE_PANE_STYLE));

        musicSource.setOnMouseEntered(e -> musicSource.setStyle(HOVERED_PANE_STYLE));
        musicSource.setOnMouseExited(e -> musicSource.setStyle(IDLE_PANE_STYLE));

        //Selecting file and importing music
        addMusic.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(HomeComponent.APPLICATION_STAGE);

            if (file != null) {
                filePath = file.getAbsolutePath();
                String fileContent = DJUtils.getFileContent(filePath);

                if (!fileContent.isEmpty() && fileContent != null) {
                    MusicComponentController musicComponentController = new MusicComponentController();
                    HomeComponent.songList = musicComponentController.parseFileContentToSongsList(fileContent);
                    if (HomeComponent.songList.size() > 0) {
                        tableView.setItems(FXCollections.observableArrayList(HomeComponent.songList));
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No Data in Selected File");
                    alert.setContentText("The file you selected is empty.\nTry again with a file that has data.");
                    alert.show();
                }
            }
        });

        //Selecting a music source directory
        musicSource.setOnMouseClicked(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(HomeComponent.APPLICATION_STAGE);
            musicSourcePath = selectedDirectory.getAbsolutePath();
        });

    }
}
