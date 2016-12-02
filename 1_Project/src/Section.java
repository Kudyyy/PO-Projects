import java.util.Vector;

/**
 * Created by klodka on 2016-12-01.
 */
public class Section implements Articles{

    private final Vector<Article> articles = new Vector<Article>();
    private int numberOfFirstArticle = 0;
    private int numberOfLastArticle = 0;
    private final String description;

    public Section(String desc){
        description = desc;
    }

    public void setRangeOfArticles(){
        if (!articles.isEmpty()) {
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

    public String getDescription(){
        return description;
    }

    public String toStringForArticle(int articleNumber){
        for (Article article: articles){
            if (articleNumber == article.getNumberOfArt()) return description+"\nArt. "+article.getNumberOfArt()+".\n"+article.toString();
        }
        return "Did not find article with number " + articleNumber;
    }

    public String toStringForArticlesInRange(int firstArt, int lastArt){
        if (firstArt >= lastArt) return "Bad range, first number has to be smaller then second";
        if(firstArt > numberOfLastArticle || lastArt < numberOfFirstArticle) return "Did not find articles in range <"+firstArt+","+lastArt+">, or range out of scope";
        String result = description+"\n";
        for (Article article: articles){
            if (firstArt <= article.getNumberOfArt() && lastArt >= article.getNumberOfArt()) result += "Art. "+article.getNumberOfArt()+".\n"+article.toString()+"\n";
        }
        return result;
    }

    public String toString(){
        String result = description+"\n";
        for(Article article :articles) {
            result +="Art. "+article.getNumberOfArt()+".\n"+ article.toString()+"\n";
        }
        return result;
    }

}
