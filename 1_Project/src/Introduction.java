/**
 * Created by daniel on 30.11.2016.
 */
public class Introduction {
    private final String content;

    public String toString(){
        return content;
    }

    public Introduction(String intro){
        content = TransferRemover.deleteTransfers(intro);
    }

}
