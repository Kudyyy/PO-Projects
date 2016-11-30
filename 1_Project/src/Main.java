

public class Main {

    public static void main(String[] args) {
        System.out.println("Main, first project");
        FileParser file = new FileParser("/Users/daniel/Desktop/Studia/PO-Projects/1_Project/konstytucja.txt");
        Constitution Konstytucja = new Constitution(file);
    }
}
