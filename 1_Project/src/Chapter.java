/**
 * Created by daniel on 30.11.2016.
 */

import java.util.*;

public class Chapter implements Articles{

    private final Vector<Section> sections;
    private final int numberOfChapter;
    private final String description;
    private int numberOfFirstArticle = 0;
    private int numberOfLastArticle = 0;


    public Chapter(String desc){
        description=desc;
        numberOfChapter = RomanToArabicConverter.convertRomanToArabic(desc.split(" ")[1]);
        sections = new Vector<Section>();
    }

    public void setRangeOfArticles(){
        if (!sections.isEmpty()) {
            numberOfFirstArticle = sections.firstElement().getNumberOfFirstArticle();
            numberOfLastArticle = sections.lastElement().getNumberOfLastArticle();
        }
    }

    public String getDescription(){
        return description;
    }

    public int getNumberOfFirstArticle(){
        return numberOfFirstArticle;
    }

    public int getNumberOfLastArticle(){
        return numberOfLastArticle;
    }

    public void addSection(Section sec){
        sections.addElement(sec);
    }

    public int getNumberOfChapter(){
        return numberOfChapter;
    }

    public String toStringForArticle(int articleNumber){
        for (Section section: sections){
            if (articleNumber >= section.getNumberOfFirstArticle() && articleNumber <= section.getNumberOfLastArticle()){
                return description+"\n"+section.toStringForArticle(articleNumber);
            }
        }
        return "Did not find article with number " + articleNumber;
    }

    public String toStringForArticlesInRange(int firstArt, int lastArt){
        if (firstArt >= lastArt) return "Bad range, first number has to be smaller then second";
        if(firstArt > numberOfLastArticle || lastArt < numberOfFirstArticle) return "Did not find articles in range <"+firstArt+","+lastArt+">, or range out of scope";
        String result = description+"\n";
        for (Section section: sections){
            if (firstArt <= section.getNumberOfLastArticle() && lastArt >= section.getNumberOfFirstArticle()) result += section.toStringForArticlesInRange(firstArt,lastArt)+"\n";
        }
        return result;
    }

    public String toString(){
        String result = description+"\n";
        for(Section section :sections) {
            result += section.toString()+"\n";
        }
        return result;
    }
}
