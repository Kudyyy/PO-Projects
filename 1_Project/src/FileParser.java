/**
 * Created by daniel on 30.11.2016.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.*;


public class FileParser {

    private String filePath;

    public FileParser(String pathToFile){
        filePath = pathToFile;
    }

    public String fileToString(){
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            String result = "";
            for (String line : stream.filter(s -> s.length()>1 && !s.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]") && !s.matches("©Kancelaria Sejmu")).toArray(String[]::new)){
                result+=line+"\n";
            }
            return result;
        } catch (IOException e) {
            System.out.println("Could not open the file with given path : "+filePath);
            System.out.println("Error message : "+ e.toString());
            System.exit(1);
        }
        return null;
    }

}
