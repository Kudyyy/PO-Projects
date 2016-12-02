

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Bad number of argument, you must give 2: FilePath WantedContent");
            System.exit(1);
        }
        FileParser file = new FileParser(args[0]);
        Constitution Konstytucja = new Constitution(file);
        if (args[1].matches("A\\d{1,3}")){
            System.out.println(Konstytucja.toStringForArticle(Integer.parseInt(args[1].substring(1,args[1].length()))));
        }
        else if (args[1].matches("A\\d{1,3}-\\d{1,3}")){
            String[] range = args[1].substring(1,args[1].length()).split("-");
            System.out.println(Konstytucja.toStringForArticlesInRange(Integer.parseInt(range[0]),Integer.parseInt(range[1])));
        }

        else if (args[1].matches("C\\d{1,2}")){
            System.out.println(Konstytucja.toStringForChapter(Integer.parseInt(args[1].substring(1,args[1].length()))));
        }
        else {
            System.out.println("Second argument is invalid, bad format or out of range: "+args[1]);
            System.out.println("Must be something like : A10,A23-67,C7");
            System.exit(1);
        }

    }
}
