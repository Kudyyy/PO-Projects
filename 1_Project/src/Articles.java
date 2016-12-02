/**
 * Created by daniel on 30.11.2016.
 */
public interface Articles {

    String toStringForArticlesInRange(int firstArt, int lastArt);
    String toStringForArticle(int articleNumber);
    int getNumberOfFirstArticle();
    int getNumberOfLastArticle();
    void setRangeOfArticles();
}
