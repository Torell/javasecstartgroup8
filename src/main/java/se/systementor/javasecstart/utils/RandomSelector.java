package se.systementor.javasecstart.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Data
public class RandomSelector {

    private String dirPath;

    public String getRandomImage() {
        //Get all files in dir
        URL resource = getClass().getClassLoader().getResource(this.dirPath);
        Path dir;
        try {
            dir = Paths.get(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        Set<String> allFiles =  Stream.of(new File(String.valueOf(dir)).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());

        return getByRandomClass(allFiles);
    }

    private static <T> T getByRandomClass(Set<T> set) {
        if (set == null || set.isEmpty()) {
            throw new IllegalArgumentException("The Set cannot be empty.");
        }
        int randomIndex = new Random().nextInt(set.size());
        int i = 0;
        for (T element : set) {
            if (i == randomIndex) {
                return element;
            }
            i++;
        }
        throw new IllegalStateException("Something went wrong while picking a random element.");
    }
}
