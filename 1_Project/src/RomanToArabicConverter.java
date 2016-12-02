import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by daniel on 01.12.2016.
 */



public class RomanToArabicConverter {

    public static int convertRomanToArabic(String roman){
        Map<Character,Integer> values = new HashMap<Character, Integer>();
        values.put('I',1);
        values.put('V',5);
        values.put('X',10);
        Vector<Integer> result = new Vector<Integer>();
        if (roman.matches("(.*)([^IVX]+)(.*)")){
            System.out.println("Could not resolve roman : " + roman +". Probably out of range.");
            System.exit(1);
        }
        for (char character:roman.toCharArray()) {
            result.addElement(values.get(character));
        }
        int sum=0;
        if (result.firstElement() < result.lastElement()) {
            sum = result.lastElement();
            for (int i =0; i<result.size()-1;i++) sum--;
        }
        else for (int i:result) sum+=i;
        return sum;
    }

}
