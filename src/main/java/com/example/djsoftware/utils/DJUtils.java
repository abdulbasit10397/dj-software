package com.example.djsoftware.utils;

import com.example.djsoftware.components.PlaylistComponent;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DJUtils {
    public static String getFileContent(String filePath) {
        String fileContents = "";
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);

            StringBuilder sb = new StringBuilder();
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            fileContents = sb.toString();
            myReader.close();

        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Problem in Reading File");
            alert.setContentText("Please make sure you are giving providing the correct file.\n" +
                    "Sorry for the inconvenience. Please try again.");
            alert.show();
        }

        return fileContents;
    }

    public static void duplicateSongAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Song Already Added");
        alert.setContentText("The song you're trying to add is already in the list.");
        alert.show();
    }

    public static void noSongSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Song Selected");
        alert.setContentText("Please select a song first and then press add button.");
        alert.show();
    }

    public static void addNumericValueForPosition() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wrong Position Value");
        alert.setContentText("Please enter a numeric or non-null value for position.");
        alert.show();
    }

    public static void couldNotFindMediaFile() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Media File Not Found");
        alert.setContentText("Media file was not present in the selected directory.");
        alert.show();
    }

    public static void noSongAvailableInLibrary() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Song Available");
        alert.setContentText("Please add song to music library/playlist first");
        alert.show();
    }

    public static void noSongAvailableOfGivenArtist() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Song Available");
        alert.setContentText("There is no song of the given artist in music library.");
        alert.show();
    }

    public static boolean checkForValidPosition(int position) {
        boolean isValid = true;

        if (position - 1 > PlaylistComponent.playlist.size()) {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Position Value");
            alert.setContentText("Please enter a valid position.");
            alert.show();
        }
        return isValid;
    }

}
