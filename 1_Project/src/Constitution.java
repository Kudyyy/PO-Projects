/**
 * Created by daniel on 30.11.2016.
 */

import java.util.*;

public class Constitution {
    private Vector<Chapter> Chapters;
    private Introduction introduction;

    public Constitution(FileParser file){
        String[] linesOfFile = file.fileToString().split(System.getProperty("line.separator"));
        int iter=0;
        String intro = "";
        for (int i=0; i<linesOfFile.length; i++){
            if (linesOfFile[i].matches("Rozdział I")){
                iter = i;
                introduction = new Introduction(intro);
                break;
            }
            intro+=linesOfFile[i]+"\n";
        }
        String chapter="";
        String article="";
        for (int i=iter; i<linesOfFile.length; i++){
            if (linesOfFile[i].matches("Rozdział [XVI]*")){
                System.out.println(linesOfFile[i]);
            }
        }
    }

    public String getIntroduction(){
        return introduction.toString();
    }

    public void addChapter(Chapter chapter){
        Chapters.addElement(chapter);
    }

    public String toStringForChapter(int chapterNumber){
        for (Chapter chapter : Chapters){
            if (chapterNumber == chapter.getNumberOfChapter()) return chapter.toString();
        }
        return "Did not find chapter with number " + chapterNumber;
    }

    public String toStringForArticle(int articleNumber){
        for (Chapter chapter : Chapters){
            if (chapter.getNumberOfFirstArticle() <= articleNumber && articleNumber <= chapter.getNumberOfLastArticle()){
                return chapter.toStringForArticle(articleNumber);
            }
        }
        return "Did not find article with number " + articleNumber;
    }

    public String toStringForArticlesInRange(int firstArt, int lastArt){
        String result = "";
        for (Chapter chapter : Chapters){
            if (firstArt <= chapter.getNumberOfLastArticle() && lastArt >= chapter.getNumberOfFirstArticle()){
                result += "\n"+"Rodział nr."+chapter.getNumberOfChapter()+"\n"+chapter.toStringForArticlesInRange(firstArt,lastArt);
            }
        }
        if (result.isEmpty()) return "Did not find articles in range <"+firstArt+","+lastArt+">";
        else return result;
    }


}
