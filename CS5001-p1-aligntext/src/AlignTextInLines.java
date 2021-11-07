import java.util.ArrayList;
import java.util.List;

/**
 * A class for aligning text in line.
 *
 * @author 210016568
 * @version 6
 * @since 1
 */
public class AlignTextInLines {


    /**
     * Align text in lines by 4 different type of alignment.
     *
     * @param fileName      The name (and path) of the file containing the text
     * @param lineLength    Desired length of the line for wrapping the text
     * @param alignmentType The type of text alignment.
     *                      L: Left-align text from the file
     *                      R: Right-align text from the file
     *                      C: Centre-align text from the file
     *                      S: Put text on an ASCII art signpost
     * @return The aligned list
     */
    public static List<String> alignTextInLine(String fileName, int lineLength, String alignmentType) {
        try {
            /* the param to record max word length if there is a word longer than lineLength
               set the same value as lineLength when type is S */
            int lineLengthAlignInS = lineLength - AlignText.LENGTH_OF_HEAD_AND_TAIL_IN_S;
            if (AlignText.TYPE_S.equals(alignmentType)) {
                // set original value as lineLength-4 because every line in type S need 4 chars "| " and " |"
                lineLength -= AlignText.LENGTH_OF_HEAD_AND_TAIL_IN_S;
            }
            List<String> singleLineText = new ArrayList<>();
            for (String singleParagraph : FileUtil.readFile(fileName)) {
                String[] words = singleParagraph.split(" ");
                // to collect the words which will bve put in next line
                List<String> tempLineWordList = new ArrayList<>();
                // count[0] to count how many words temp line used; count[1] to count how  much length those words used
                int[] count = new int[2];
                for (String word : words) {
                    if (word.length() > lineLength) {
                        // update param when there is a word longer than lineLength
                        lineLengthAlignInS = Math.max(lineLengthAlignInS, word.length());
                        if (count[0] == 0) {
                            singleLineText.add(word);
                        } else {
                            singleLineText.add(ManipulateStr.listToString(tempLineWordList));
                            singleLineText.add(word);
                            count[0] = 0;
                            count[1] = 0;
                            tempLineWordList.clear();
                        }
                    } else {
                        count[0] += 1;
                        count[1] += word.length();
                        if (count[1] + count[0] - 1 <= lineLength) {
                            tempLineWordList.add(word);
                        } else {
                            singleLineText.add(ManipulateStr.listToString(tempLineWordList));
                            tempLineWordList.clear();
                            tempLineWordList.add(word);
                            count[0] = 1;
                            count[1] = word.length();
                        }
                    }
                }
                if (!tempLineWordList.isEmpty()) {
                    singleLineText.add(ManipulateStr.listToString(tempLineWordList));
                }
            }
            return AlignType.alignType(singleLineText, lineLength, alignmentType, lineLengthAlignInS);

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
