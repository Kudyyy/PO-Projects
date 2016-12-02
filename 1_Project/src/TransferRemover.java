/**
 * Created by daniel on 02.12.2016.
 */
public class TransferRemover {
    public static String deleteTransfers(String cont){
        String[] linesOfCont = cont.split(System.getProperty("line.separator"));
        String result ="";
        String lineResult = "";
        boolean foundTransfer;
        for (int i = 0 ; i<linesOfCont.length; i++){
            lineResult = linesOfCont[i];
            if (linesOfCont[i].toCharArray()[linesOfCont[i].toCharArray().length-1] == '-'){
                linesOfCont[i] = linesOfCont[i].substring(0,linesOfCont[i].length()-1);
                if (linesOfCont[i+1].length() >15){
                    lineResult = linesOfCont[i] + linesOfCont[i+1].split(" ")[0];
                    linesOfCont[i+1] = linesOfCont[i+1].substring(linesOfCont[i+1].split(" ")[0].length()+1,linesOfCont[i+1].length());
                }
                else {
                    lineResult = linesOfCont[i] + linesOfCont[i+1];
                    i++;
                }
            }
            result += lineResult+"\n";
        }
        return result;
    }
}
