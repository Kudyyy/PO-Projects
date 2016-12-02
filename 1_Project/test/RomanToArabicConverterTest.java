import junit.framework.TestCase;

/**
 * Created by daniel on 02.12.2016.
 */
public class RomanToArabicConverterTest extends TestCase {
    public void testConvertRomanToArabic() throws Exception {
        assertEquals(7,RomanToArabicConverter.convertRomanToArabic("VII"));
        assertEquals(9,RomanToArabicConverter.convertRomanToArabic("IX"));
    }

}