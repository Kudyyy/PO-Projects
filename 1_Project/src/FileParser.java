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
        System.out.println(filePath);
    }

    public String fileToString(){
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            String result = "";
            for (String line : stream.filter(s -> s.length()>1 && !s.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]") && !s.matches("©Kancelaria Sejmu")).toArray(String[]::new)){
                result+=line+"\n";
            }
            return result;
        } catch (IOException e) {
            e.toString();
        }
        return null;
    }

    public String deleteTransfers(String cont){
        String[] linesOfCont = cont.split(System.getProperty("line.separator"));
        String result ="";
        String lineResult = "";
        boolean foundTransfer;
        for (int i = 0 ; i<linesOfCont.length; i++){
            lineResult = linesOfCont[i];
            if (linesOfCont[i].toCharArray()[linesOfCont[i].toCharArray().length-1] == '-'){
                linesOfCont[i] = linesOfCont[i].substring(0,linesOfCont[i].length()-1);
                if (linesOfCont[i+1].length() >15){
                    lineResult = linesOfCont[i] + linesOfCont[i+1].split(" ")[0];
                    linesOfCont[i+1] = linesOfCont[i+1].substring(linesOfCont[i+1].split(" ")[0].length()+1,linesOfCont[i+1].length());
                }
                else {
                    lineResult = linesOfCont[i] + linesOfCont[i+1];
                    i++;
                }
            }
            result += lineResult+"\n";
        }
        return result;
    }


}
