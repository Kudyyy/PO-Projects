

public class Main {

    public static void main(String[] args) {
        System.out.println("Main, first project");
        FileParser file = new FileParser("konstytucja.txt");
        Constitution Konstytucja = new Constitution(file);
        System.out.println(RomanToArabicConverter.convertRomanToArabic("IX"));
        System.out.println(Konstytucja.toString());
        //System.out.println(Konstytucja.toStringForArticlesInRange(170,175));
    }
}
