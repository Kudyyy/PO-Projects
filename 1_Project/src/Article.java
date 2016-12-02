/**
 * Created by daniel on 30.11.2016.
 */
public class Article {

    private final String content;
    private final int numberOfArt;

    public Article(int articleNumber,String cont){

        numberOfArt = articleNumber;
        content = TransferRemover.deleteTransfers(cont);
    }

    public String toString(){
        return content;
    }
    public int getNumberOfArt(){
        return numberOfArt;
    }

}
