

public class Main {

    public static void main(String[] args) {
        System.out.println("Main, first project");
        FileParser file = new FileParser("konstytucja.txt");
        Constitution Konstytucja = new Constitution(file);
    }
}
