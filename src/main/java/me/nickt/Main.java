package me.nickt;

import me.nickt.summarizers.RandomSummary;
import me.nickt.utils.IOUtils;

import java.io.File;

public class Main {

    private final static String INPUT_FILE = "res" + File.separator + "005.txt";

    public static void main(String[] args) {
        String inputString = IOUtils.fileToString(INPUT_FILE);
        Document inputDoc = new Document(inputString);
        System.out.println(inputDoc);
        inputDoc.printSummary(new RandomSummary(), 3);

    }
}
