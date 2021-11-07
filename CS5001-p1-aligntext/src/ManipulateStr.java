import java.util.List;


/**
 * A class to manipulate string.
 *
 * @author 210016568
 * @version 1
 * @since 1
 */
public class ManipulateStr {


    /**
     * Transfer a list of words to a complete sentence.
     *
     * @param list The list of words which will be transferred to a sentence
     * @return The sentence which is made by the list of words
     */
    public static String listToString(List<String> list) {
        StringBuilder sentence = new StringBuilder();
        for (String str : list) {
            sentence.append(str).append(" ");
        }
        return String.valueOf(sentence).strip();
    }

    /**
     * Repeat the same string any number of times.
     *
     * @param str The string will be repeated
     * @param num The number of times repeated
     * @return The text after str is repeated num times
     */
    public static String repeatStr(String str, int num) {
        return String.valueOf(str).repeat(Math.max(0, num));
    }
}
