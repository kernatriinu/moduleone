package main.java.cryptology.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ConfigLoader {
    private static final String RESOURCE_PATH = "src/resources/";
    static Notifier notifier = SingletonNotifier.getNotifier();

    public static Set<String> loadCommonWords(String language) {
        var path = Paths.get("src/resources/" + language + "_words.txt");
        try {
            notifier.notifyInfo("Loading words from: " + path);
            Set<String> words = Files.lines(path)
                    .map(line -> line.split(","))
                    .flatMap(Arrays::stream)
                    .map(String::trim)
                    .collect(Collectors.toSet());
            notifier.notifyInfo("Loaded words: " + words);

            return words;

        } catch (IOException e) {
            e.printStackTrace();

            return Collections.emptySet();
        }
    }

    public static String loadAlphabet(String language) {
        var props = new Properties();
        try {
            props.load(Files.newInputStream(Paths.get(RESOURCE_PATH + "alphabet.properties")));

            return props.getProperty(language);

        } catch (IOException e) {
            notifier.notifyInfo("Error loading alphabet properties: " + e.getMessage());

            return null;
        }
    }
}
