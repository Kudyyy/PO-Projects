import junit.framework.TestCase;

/**
 * Created by daniel on 02.12.2016.
 */
public class ConstitutionTest extends TestCase {
    public void testGetNumberOfFirstArticle() throws Exception {
        FileParser file = new FileParser("konstytucja.txt");
        Constitution Konstytucja = new Constitution(file);
        assertEquals(Konstytucja.getNumberOfFirstArticle(),1);

    }

    public void testGetNumberOfLastArticle() throws Exception {
        FileParser file = new FileParser("konstytucja.txt");
        Constitution Konstytucja = new Constitution(file);
        assertEquals(243,Konstytucja.getNumberOfLastArticle());
    }

}