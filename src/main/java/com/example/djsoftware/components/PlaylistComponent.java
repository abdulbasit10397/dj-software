package com.example.djsoftware.components;

import com.example.djsoftware.HomeComponent;
import com.example.djsoftware.beans.Song;
import com.example.djsoftware.controllers.PlaylistComponentController;
import com.example.djsoftware.utils.DJUtils;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.*;

public class PlaylistComponent extends StackPane {
    public static List<Song> searchResult = new ArrayList<>();
    public static List<Song> playlist = new LinkedList<>();

    private File mediaFile;
    private Media media;
    private MediaPlayer player;
    private String musicSourcePath = MusicLibraryComponent.musicSourcePath;
    private Song runningSong;
    public static Timer timer;
    public static TimerTask task;
    private boolean isResume = false, isNext = false;

    public static final String IDLE_PANE_STYLE = "-fx-background-color: #000000;";
    private static final String HOVERED_PANE_STYLE = "-fx-background-color: gray; ";

    private final Image searchImage = new Image(getClass().getResourceAsStream("/images/search.png"));
    private final Image addImage = new Image(getClass().getResourceAsStream("/images/add.png"));
    private final Image removeImage = new Image(getClass().getResourceAsStream("/images/remove.png"));
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

    private Pane menuBar, playlistPane, playlistLabelPane, combinedPane, searchPane;
    private Pane playlistOperationsPane, playlistTablePane;
    private GridPane searchIconPane, addIconPane, removeIconPane;
    private Label menuBarLabel, playlistLabel, searchPaneLabel, playlistOperationsLabel, positionLabel;
    private TextField searchTextField, positionField;
    private ImageView searchIcon, addIcon, removeIcon;
    private TableView searchTable, playlistOperationsTable, playlistTable;

    public PlaylistComponent() {
        //******************** Attributes ********************
        //******************** Start of Menu Bar ********************
        menuBarLabel = new Label("My Playlist");
        menuBarLabel.setStyle("-fx-text-fill: white; -fx-font: 24 arial;");
        menuBarLabel.setAlignment(Pos.BOTTOM_LEFT);
        menuBarLabel.relocate(10, 25);

        menuBar = new AnchorPane();
        menuBar.setMaxSize(420, 60);
        menuBar.setStyle("-fx-border-color: gray; -fx-border-width: 0px 0px 1px 0px; " + IDLE_PANE_STYLE);
        StackPane.setAlignment(menuBar, Pos.TOP_LEFT);
        menuBar.getChildren().add(menuBarLabel);

        playlistLabel = new Label("View Running Playlist");
        playlistLabel.setStyle("-fx-text-fill: white; -fx-font: 24 arial; ");
        playlistLabel.setAlignment(Pos.BOTTOM_LEFT);
        playlistLabel.relocate(10, 25);

        playlistLabelPane = new AnchorPane();
        playlistLabelPane.setMaxSize(300, 60);
        playlistLabelPane.setStyle("-fx-border-color: gray; -fx-border-width: 0px 0px 1px 1px; " + IDLE_PANE_STYLE);
        StackPane.setAlignment(playlistLabelPane, Pos.TOP_RIGHT);
        playlistLabelPane.getChildren().add(playlistLabel);
        //******************** End of Menu Bar ********************

        //******************** Start of Combined Pane ********************
        //******************** Start of Search Pane ********************

        searchPaneLabel = new Label("Search");
        searchPaneLabel.setStyle("-fx-text-fill: white; -fx-font: 14 arial;");
        searchPaneLabel.relocate(7, 15);

        searchTextField = new TextField();
        searchTextField.setPrefSize(285, 30);
        searchTextField.relocate(55, 8);

        searchIcon = new ImageView(searchImage);
        searchIcon.setFitHeight(35);
        searchIcon.setFitWidth(35);
        searchIcon.setPreserveRatio(true);

        searchIconPane = new GridPane();
        searchIconPane.setMaxSize(35, 35);
        searchIconPane.relocate(345, 5);
        searchIconPane.getChildren().add(searchIcon);

        searchTable = new TableView();
        TableColumn<Song, String> searchTableTitle = new TableColumn<>("Title");
        searchTableTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Song, String> searchTableArtist = new TableColumn<>("Artist Name");
        searchTableArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        TableColumn<Song, Double> searchTableDuration = new TableColumn<>("Duration");
        searchTableDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        searchTable.getColumns().addAll(searchTableTitle, searchTableArtist, searchTableDuration);
        searchTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        searchTable.relocate(7, 50);
        searchTable.setPrefSize(333, 160);

        addIcon = new ImageView(addImage);
        addIcon.setFitHeight(35);
        addIcon.setFitWidth(35);
        addIcon.setPreserveRatio(true);

        addIconPane = new GridPane();
        addIconPane.setMaxSize(35, 35);
        addIconPane.relocate(345, 50);
        addIconPane.getChildren().add(addIcon);

        positionLabel = new Label("Add At\nPosition:");
        positionLabel.setStyle("-fx-text-fill: white; -fx-font: 12 arial;");
        positionLabel.relocate(345, 90);

        positionField = new TextField();
        positionField.setPrefSize(70, 20);
        positionField.relocate(345, 125);
        positionField.setText("0");

        searchPane = new AnchorPane();
        searchPane.setMaxSize(420, 220);
        searchPane.setStyle("-fx-border-color: gray; -fx-border-width: 0px 0px 1px 0px; " + IDLE_PANE_STYLE);
        StackPane.setAlignment(searchPane, Pos.TOP_CENTER);
        searchPane.getChildren().addAll(searchPaneLabel, searchTextField, searchIconPane,
                searchTable, addIconPane, positionLabel, positionField);

        //******************** End of Search Pane ********************


        //******************** Start of Playlist Operations Pane ********************
        playlistOperationsLabel = new Label("Songs Added In Playlist");
        playlistOperationsLabel.setStyle("-fx-text-fill: white; -fx-font: 14 arial;");
        playlistOperationsLabel.relocate(7, 10);

        playlistOperationsTable = new TableView();
        TableColumn<Song, String> playlistTableTitle = new TableColumn<>("Title");
        playlistTableTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Song, String> playlistTableArtist = new TableColumn<>("Artist Name");
        playlistTableArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        TableColumn<Song, Double> durationTableDuration = new TableColumn<>("Duration");
        durationTableDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        playlistOperationsTable.getColumns().addAll(playlistTableTitle, playlistTableArtist, durationTableDuration);
        playlistOperationsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        playlistOperationsTable.relocate(7, 30);
        playlistOperationsTable.setPrefSize(333, 180);

        removeIcon = new ImageView(removeImage);
        removeIcon.setFitHeight(30);
        removeIcon.setFitWidth(30);
        removeIcon.setPreserveRatio(true);

        removeIconPane = new GridPane();
        removeIconPane.setMaxSize(33, 33);
        removeIconPane.setPadding(new Insets(3, 3, 3, 3));
        removeIconPane.relocate(345, 30);
        removeIconPane.getChildren().add(removeIcon);


        playlistOperationsPane = new AnchorPane();
        playlistOperationsPane.setMaxSize(420, 220);
        playlistOperationsPane.setStyle("-fx-border-color: gray; -fx-border-width: 0px 0px 0px 0px; " + IDLE_PANE_STYLE);
        StackPane.setAlignment(playlistOperationsPane, Pos.BOTTOM_CENTER);
        playlistOperationsPane.getChildren().addAll(playlistOperationsLabel, playlistOperationsTable, removeIconPane);

        //******************** End of Playlist Operations Pane ********************

        //******************** Start of Now Playing Pane ********************

        playlistTable = new TableView();
        TableColumn<Song, String> nowPlayingListTitle = new TableColumn<>("Title");
        nowPlayingListTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<Song, String> nowPlayingListArtist = new TableColumn<>("Artist Name");
        nowPlayingListArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        TableColumn<Song, Double> nowPlayingListDuration = new TableColumn<>("Duration");
        nowPlayingListDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        playlistTable.getColumns().addAll(nowPlayingListTitle, nowPlayingListArtist, nowPlayingListDuration);
        playlistTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        playlistTable.setSelectionModel(null);


        playlistTablePane = new AnchorPane();
        playlistLabelPane.setMaxSize(300, 440);
        StackPane.setAlignment(playlistTablePane, Pos.BOTTOM_RIGHT);
        playlistLabelPane.getChildren().add(playlistTable);

        playlistPane = new StackPane();
        playlistPane.setMaxSize(300, 440);
        playlistPane.setStyle("-fx-border-color: gray; -fx-border-width: 0px 0px 0px 0px; " + IDLE_PANE_STYLE);
        StackPane.setAlignment(playlistPane, Pos.BOTTOM_RIGHT);
        playlistPane.getChildren().addAll(playlistTable);
        //******************** End of Now Playing Pane ********************


        combinedPane = new StackPane();
        combinedPane.setMaxSize(420, 440);
        combinedPane.setStyle("-fx-border-color: gray; -fx-border-width: 0px 1px 0px 0px; " + IDLE_PANE_STYLE);
        StackPane.setAlignment(combinedPane, Pos.BOTTOM_LEFT);
        combinedPane.getChildren().addAll(searchPane, playlistOperationsPane);
        //******************** Start of Combined Pane ********************


        setMaxSize(720, 500);
        setStyle("-fx-background-color: #000000;");
        getChildren().addAll(menuBar, playlistLabelPane, playlistPane, combinedPane);


        //******************** Actions ********************
        updatePlaylistTable();

        searchIconPane.setOnMouseEntered(e -> searchIconPane.setStyle(HOVERED_PANE_STYLE));
        searchIconPane.setOnMouseExited(e -> searchIconPane.setStyle(IDLE_PANE_STYLE));

        addIconPane.setOnMouseEntered(e -> addIconPane.setStyle(HOVERED_PANE_STYLE));
        addIconPane.setOnMouseExited(e -> addIconPane.setStyle(IDLE_PANE_STYLE));

        removeIconPane.setOnMouseEntered(e -> removeIconPane.setStyle(HOVERED_PANE_STYLE));
        removeIconPane.setOnMouseExited(e -> removeIconPane.setStyle(IDLE_PANE_STYLE));

        searchIconPane.setOnMouseClicked(e -> {
            List<Song> songList = HomeComponent.songList;
            if (songList != null && songList.size() > 0) {

                PlaylistComponentController playlistComponentController = new PlaylistComponentController();
                searchResult = playlistComponentController.searchSongsByArtistName(
                        searchTextField.getText().toLowerCase(Locale.ROOT));

                if (searchResult.size() > 0) {
                    searchTable.setItems(FXCollections.observableArrayList(searchResult));

                } else {
                    DJUtils.noSongAvailableOfGivenArtist();
                }
            } else {
                DJUtils.noSongAvailableInLibrary();
            }
        });

        addIconPane.setOnMouseClicked(e -> addSongInPlaylist());

        removeIconPane.setOnMouseClicked(e -> {
            Song song = (Song) playlistOperationsTable.getSelectionModel().getSelectedItem();
            if (song != null) {
                playlist.remove(song);
                updatePlaylistTable();
            }
        });

        //To reorder songs in the playlist
        playlistOperationsTable.setRowFactory(tv -> {
            TableRow<Song> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer) db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    Song draggedSong = (Song) playlistOperationsTable.getItems().remove(draggedIndex);
                    playlist.remove(draggedIndex);

                    int dropIndex;

                    if (row.isEmpty()) {
                        dropIndex = playlistOperationsTable.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    playlistOperationsTable.getItems().add(dropIndex, draggedSong);
                    playlist.add(dropIndex, draggedSong);

                    event.setDropCompleted(true);
                    playlistOperationsTable.getSelectionModel().select(dropIndex);
                    updatePlaylistTable();
                    event.consume();
                }
            });

            return row;
        });
    }

    public void updatePlaylistTable() {
        playlistOperationsTable.setItems(FXCollections.observableArrayList(playlist));
        playlistTable.setItems(FXCollections.observableArrayList(playlist));
    }

    public void addSongInPlaylist() {
        if (searchResult.size() > 0) {
            Song song = (Song) searchTable.getSelectionModel().getSelectedItem();
            if (song != null) {
                if (!playlist.contains(song)) {
                    addSongsAccordingToPosition(song);
                    updatePlaylistTable();
                } else {
                    DJUtils.duplicateSongAlert();
                }
            } else {
                DJUtils.noSongSelectedAlert();
            }
        }
    }

    public void addSongsAccordingToPosition(Song song) {
        int position;
        try {
            position = Integer.parseInt(positionField.getText());
        } catch (Exception ex) {
            DJUtils.addNumericValueForPosition();
            position = -1;
        }

        if (DJUtils.checkForValidPosition(position)) {
            if (position > 0)
                playlist.add(position - 1, song);
            else if (position == 0)
                playlist.add(song);
        }
    }

    public void playSong() {
        Song song = playlist.get(0);
        selectSongAction(song);
    }

    public void playNextSong() {
        Song nextSong = playlist.get(0);
        isNext = true;
        if (player != null)
            player.pause();

        selectSongAction(nextSong);
    }

    public void selectSongAction(Song song) {
        try {
            //Checking if to play next song
            if (song != null && isNext) {
                play(song);
                isNext = false;
            }

            //Check if to resume song
            if (song != null && isResume) {
                player.play();
                beginTimer();
                isResume = false;
            } else {
                //playing a new song
                play(song);
            }

        } catch (
                Exception e) {
            DJUtils.couldNotFindMediaFile();
            e.printStackTrace();
        }
    }

    private void play(Song song) {
        try {
            musicSourcePath = musicSourcePath.replace("\\", "/");
            String audioPath = musicSourcePath + "/" + song.getAudioFileName();

            mediaFile = new File(audioPath);
            media = new Media(mediaFile.toURI().toURL().toString());
            player = new MediaPlayer(media);
            player.play();
            beginTimer();

            updateBottomBarRunningSongLabel(song);
            playlist.remove(song);
            updatePlaylistTable();
        } catch (
                Exception e) {
            DJUtils.couldNotFindMediaFile();
            e.printStackTrace();
        }
    }

    public void pauseSong() {
        player.pause();
        cancelTimer();
        isResume = true;

    }

    public void updateBottomBarRunningSongLabel(Song song) {
        BottomBarComponent.currentlyPlayingSongLabel.setText(song.getTitle() + "\t" +
                song.getArtist() + "\t" + song.getDuration());
    }

    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                isResume = true;
                double current = player.getCurrentTime().toSeconds();
                double end = player.getTotalDuration().toSeconds();
                BottomBarComponent.progressBar.setProgress(current/end);
                if (current / end == 1) {
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void cancelTimer() {
        isResume = false;
        timer.cancel();

    }

}
