

public class Main {

    public static void main(String[] args) {
        System.out.println("Main, first project");
        FileParser file = new FileParser("konstytucja.txt");
        Constitution Konstytucja = new Constitution(file);
        System.out.println(Konstytucja.toString());
        System.out.println(Konstytucja.toStringForArticle(98));
        System.out.println(Konstytucja.toStringForArticlesInRange(96,128));
    }
}
