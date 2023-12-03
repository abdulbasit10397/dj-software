package com.example.djsoftware.controllers;

import com.example.djsoftware.HomeComponent;
import com.example.djsoftware.beans.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaylistComponentController {
    public static List<Song> songList = HomeComponent.songList;

    public List<Song> searchSongsByArtistName(String artist) {
        List<Song> searchResult = new ArrayList<>();
        for (Song song : songList) {
            if (song.getArtist().toLowerCase(Locale.ROOT).equals(artist))
                searchResult.add(song);
        }

        return searchResult;
    }
}
