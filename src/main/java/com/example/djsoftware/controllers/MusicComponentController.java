package com.example.djsoftware.controllers;

import com.example.djsoftware.beans.Song;

import java.util.*;

public class MusicComponentController {

    public List<Song> parseFileContentToSongsList(String fileContent) {
        List<Song> songs = new ArrayList<>();
        Scanner myReader = new Scanner(fileContent);

        while (myReader.hasNextLine()) {
            String line = myReader.nextLine();
            Song song = parseLineToSongObject(line);

            songs.add(song);
        }

        return songs;
    }


    public Song parseLineToSongObject(String line) {
        String songFields[] = line.split("\t");
        Song song = new Song();

        song.setTitle(songFields[0]);
        song.setArtist(songFields[1]);
        song.setDuration(Double.parseDouble(songFields[2]));
        song.setAudioFileName(songFields[3]);

        return song;
    }
}
