/**
 * Created by daniel on 30.11.2016.
 */

import java.util.*;
import java.lang.Object;

public class Constitution implements Articles {
    private final Vector<Chapter> chapters = new Vector<Chapter>();
    private Introduction introduction;
    private int numberOfFirstArticle = 0;
    private int numberOfLastArticle = 0;

    public Constitution(FileParser file){
        this.divideConstitutionToObjects(file);
        setRangeOfArticles();
    }

    public int getNumberOfFirstArticle(){ return numberOfFirstArticle;}
    public int getNumberOfLastArticle(){ return numberOfLastArticle;}
    public void setRangeOfArticles(){
        if(!chapters.isEmpty()){
            numberOfFirstArticle = chapters.firstElement().getNumberOfFirstArticle();
            numberOfLastArticle = chapters.lastElement().getNumberOfLastArticle();
        }
    }

    public String getIntroduction(){
        return introduction.toString();
    }

    public void addChapter(Chapter chapter){
        chapters.addElement(chapter);
    }

    public String toStringForChapter(int chapterNumber){
        for (Chapter chapter : chapters){
            if (chapterNumber == chapter.getNumberOfChapter()) return chapter.toString();
        }
        return "Did not find chapter with number " + chapterNumber;
    }

    public String toStringForArticle(int articleNumber){
        for (Chapter chapter : chapters){
            if (chapter.getNumberOfFirstArticle() <= articleNumber && articleNumber <= chapter.getNumberOfLastArticle()){
                return chapter.toStringForArticle(articleNumber);
            }
        }
        return "Did not find article with number " + articleNumber;
    }

    public String toStringForArticlesInRange(int firstArt, int lastArt){
        String result = "";
        for (Chapter chapter : chapters){
            if (firstArt <= chapter.getNumberOfLastArticle() && lastArt >= chapter.getNumberOfFirstArticle()){
                result += "\n"+chapter.toStringForArticlesInRange(firstArt,lastArt);
            }
        }
        if (result.isEmpty()) return "Did not find articles in range <"+firstArt+","+lastArt+">";
        else return result;
    }


    public String toString(){
        String result = introduction.toString()+"\n";
        for (Chapter chapter : chapters){
            result+=chapter.toString()+"\n";
        }
        return result;
    }



    public void divideConstitutionToObjects(FileParser file){
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
        Article article = null;
        int artNum=0;
        String artCont="";
        Chapter chapter = null;
        Section section = null;
        String secDesc;
        int secCount;
        boolean upper = true;
        boolean foundSection;
        boolean newChapter = false;
        boolean newSection = false;
        for (int i=iter; i<linesOfFile.length; i++){
            foundSection = false;
            secDesc="";
            if (linesOfFile[i].matches("Rozdział [XVI]*")){
                if (article != null) {
                    article = new Article(artNum,artCont);
                    section.addArticle(article);
                    artCont = "";
                }
                if (section != null) {
                    section.setRangeOfArticles();
                    chapter.addSection(section);
                }
                newChapter = true;
                if (chapter != null){
                    chapter.setRangeOfArticles();
                    chapters.addElement(chapter);
                }
                chapter = new Chapter(linesOfFile[i]);
                continue;
            }
            upper = true;
            for(char ch: linesOfFile[i].toCharArray()){
                //System.out.println(ch);
                if (Character.isLowerCase(ch) && ch != ' ' && ch != '\n' ) {
                    upper = false;
                    break;
                }
            }
            while (upper){
                foundSection = true;
                secDesc += linesOfFile[i]+" ";
                i++;
                //System.out.println(secDesc);
                for(char ch: linesOfFile[i].toCharArray()){
                    //System.out.println(ch);
                    if (Character.isLowerCase(ch) && ch != ' ' && ch != '\n' ) {
                        upper = false;
                        i--;
                        break;
                    }
                }
            }
            if(foundSection){
                if (section != null && !newChapter){
                    article = new Article(artNum,artCont);
                    section.addArticle(article);
                    artCont = "";
                    section.setRangeOfArticles();
                    chapter.addSection(section);
                    newSection = true;
                }
                section = new Section(secDesc);
            }

            else if (linesOfFile[i].matches("Art. [0-9]{1,3}.")){
                if (!newChapter && !newSection){
                    article = new Article(artNum,artCont);
                    section.addArticle(article);
                    artCont = "";
                }
                artNum=Integer.parseInt(linesOfFile[i].substring(0,linesOfFile[i].length()-1).split(" ")[1]);
                //System.out.println(artNum);
                newChapter = false;
                newSection = false;
            }
            else artCont += linesOfFile[i]+"\n";


        }
    }

}
