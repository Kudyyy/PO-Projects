/**
 * Created by daniel on 30.11.2016.
 */

import java.util.*;

public class Chapter {

    private Vector<Article> articles;
    private final int numberOfChapter;
    private int numberOfFirstArticle = 0;
    private int numberOfLastArticle = 0;

    public Chapter(int chapterNumber){
        numberOfChapter = chapterNumber;
        articles = new Vector<Article>();
    }

    public void setRangeOfArticles(){
        if (articles != null) {
            numberOfFirstArticle = articles.firstElement().getNumberOfArt();
            numberOfLastArticle = articles.lastElement().getNumberOfArt();
        }
    }

    public int getNumberOfFirstArticle(){
        return numberOfFirstArticle;
    }

    public int getNumberOfLastArticle(){
        return numberOfLastArticle;
    }

    public void addArticle(Article art){
        articles.addElement(art);
    }

    public int getNumberOfChapter(){
        return numberOfChapter;
    }

    public String toStringForArticle(int articleNumber){
        for (Article article: articles){
            if (articleNumber == article.getNumberOfArt()) return "Art."+article.getNumberOfArt()+"\n"+article.toString();
        }
        return "Did not find article with number " + articleNumber;
    }

    public String toStringForArticlesInRange(int firstArt, int lastArt){
        if(firstArt > numberOfLastArticle || lastArt < numberOfFirstArticle) return "Did not find articles in range <"+firstArt+","+lastArt+">";
        String result = "";
        for (Article article: articles){
            if (firstArt <= article.getNumberOfArt() && lastArt >= article.getNumberOfArt()) result += "Art."+article.getNumberOfArt()+"\n"+article.toString()+"\n";
        }
        return result;
    }

    public String toString(){
        String result = "Chapter number: "+numberOfChapter+"\n";
        for(Article article :articles) {
            result += article.toString()+"\n";
        }
        return result;
    }
}
