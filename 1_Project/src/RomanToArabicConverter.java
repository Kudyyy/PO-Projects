import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by daniel on 01.12.2016.
 */



public class RomanToArabicConverter {
    private Map<Character,Integer> values = new HashMap<Character, Integer>();

    public RomanToArabicConverter(){
        values.put('I',1);
        values.put('V',5);
        values.put('X',10);
        values.put('L',50);
        values.put('C',100);
        values.put('D',500);
        values.put('M',1000);
    }

    public int convertRomanToArabic(String roman){
        Vector<Integer> result = new Vector<Integer>();
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
