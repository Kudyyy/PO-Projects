/**
 * Created by daniel on 30.11.2016.
 */
public class Article {

    private String content;
    private final int numberOfArt;

    public Article(int articleNumber){
        numberOfArt = articleNumber;
    }

    public String toString(){
        return content;
    }
    public int getNumberOfArt(){
        return numberOfArt;
    }
}
