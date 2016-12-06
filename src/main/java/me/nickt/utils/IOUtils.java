package me.nickt.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOUtils {

    /**
     * Read everything from a file into a string, nonexistent files result in the empty string
     */
    public static String fileToString(String path) {
        String fileString = "";
        try (Scanner fileScanner = new Scanner(new File(path))) {
            // Use end of file delimiter
            fileScanner.useDelimiter("\\Z");
            fileString = fileScanner.next();
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Could not open file " + path);
            e.printStackTrace();
        }
        return fileString;
    }
}
