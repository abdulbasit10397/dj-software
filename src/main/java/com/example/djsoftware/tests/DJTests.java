package com.example.djsoftware.tests;

import com.example.djsoftware.beans.Song;
import com.example.djsoftware.components.PlaylistComponent;
import com.example.djsoftware.controllers.MusicComponentController;
import com.example.djsoftware.controllers.PlaylistComponentController;
import com.example.djsoftware.utils.DJUtils;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class DJTests {
    public static String SONG_FILE_PATH = "src\\main\\resources\\storage\\dj Test Song.txt";

    //Test Case for reading data from file
    @Test
    public void getFileContent_GetsValidFilePath_ReturnsFileContentsString() {
        String result = DJUtils.getFileContent(SONG_FILE_PATH);
        Assert.assertNotEquals("", result);
    }

    //Test Music Component Controller
    @Test
    public void parseFileContent_GetsValidFileContent_ReturnsListOfSongs() {
        String fileContent = DJUtils.getFileContent(SONG_FILE_PATH);
        MusicComponentController musicComponentController = new MusicComponentController();
        List<Song> result = musicComponentController.parseFileContentToSongsList(fileContent);
        Assert.assertEquals(70, result.size());
    }

    //Check For Valid Insertion Position Test
    @Test
    public void checkValidPosition_ValidPosition_ReturnsTrue() {
        PlaylistComponent.playlist = new ArrayList<>();
        Assert.assertEquals(true, DJUtils.checkForValidPosition(1));
    }

    //Search Song Test
    @Test
    public void searchSongsByArtistName_ArtistHaveSongs_ReturnsSongsList()
    {
        String fileContent = DJUtils.getFileContent(SONG_FILE_PATH);
        MusicComponentController musicComponentController = new MusicComponentController();

        List<Song> result = musicComponentController.parseFileContentToSongsList(fileContent);
        PlaylistComponentController.songList = result;
        PlaylistComponentController playlistComponentController = new PlaylistComponentController();
        result = playlistComponentController.searchSongsByArtistName("kane brown");

        Assert.assertEquals(2, result.size());
    }

    @Test
    public void searchSongsByArtistName_ArtistDoNotHaveSongs_ReturnsEmptyList()
    {
        String fileContent = DJUtils.getFileContent(SONG_FILE_PATH);
        MusicComponentController musicComponentController = new MusicComponentController();

        List<Song> result = musicComponentController.parseFileContentToSongsList(fileContent);
        PlaylistComponentController.songList = result;
        PlaylistComponentController playlistComponentController = new PlaylistComponentController();
        result = playlistComponentController.searchSongsByArtistName("eminem");

        Assert.assertEquals(0, result.size());
    }

}
