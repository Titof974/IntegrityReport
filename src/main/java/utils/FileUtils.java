package utils;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    public static List<Path> listAllInDirectory(String path, Optional<String> includeRegex, Optional<String> excludeRegex) throws IOException {
        Stream<Path> allFiles = Files.walk(Paths.get(path)).filter(Files::isRegularFile);
        if (includeRegex.isPresent() && !includeRegex.get().isEmpty()) {
            allFiles = allFiles.filter(p -> p.toString().matches(includeRegex.get()));
        }
        if (excludeRegex.isPresent() && !excludeRegex.get().isEmpty()) {
            allFiles = allFiles.filter(p -> !p.toString().matches(excludeRegex.get()));
        }
        return allFiles.collect(Collectors.toList());
    }

    public static void saveStringInFile(String text, String path) {
        try{
            Writer output = null;
            File file = new File(path);
            output = new BufferedWriter(new FileWriter(file));
            output.write(text);
            output.close();

        }catch(Exception e){
            System.err.println("Could not create file");
        }
    }

    public static String openFileAndRead(String path){
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, StandardCharsets.UTF_8);
    }

}

