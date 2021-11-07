import java.util.List;


/**
 * A class for AlignText.alignText() to suit different type of alignment.
 *
 * @author 210016568
 * @version 4
 * @since 1
 */
public class AlignType {


    /**
     * Choose type of alignment.
     *
     * @param singleLineText     The list of the contents of each line divided according to the lineLength
     * @param lineLength         Desired length of the line for wrapping the text
     * @param alignmentType      The type of text alignment.
     *                           L: Left-align text from the input list
     *                           R: Right-align text from the input list
     *                           C: Centre-align text from the input list
     *                           S: Put input list on an ASCII art signpost
     * @param lineLengthAlignInS The lineLength when put text on an ASCII art signpost
     * @return The list of aligned lines, it meet the demand of alignment and lineLength, can be directly print
     */
    public static List<String> alignType(List<String> singleLineText, int lineLength, String alignmentType, int lineLengthAlignInS) {
        // divide 4 different types into 4 methods
        switch (alignmentType) {
            case AlignText.TYPE_R:
                return alignInR(singleLineText, lineLength);
            case AlignText.TYPE_C:
                return alignInC(singleLineText, lineLength);
            case AlignText.TYPE_S:
                return alignInS(singleLineText, lineLengthAlignInS);
            default:
                return alignInL(singleLineText);
        }
    }


    /**
     * Left-align text from the input list.
     *
     * @param lines The input list which will be left-aligned
     * @return Aligned text
     */
    private static List<String> alignInL(List<String> lines) {
        return lines;
    }


    /**
     * Right-align text from the input list.
     *
     * @param lines      The input list which will be right-aligned
     * @param lineLength Desired length of the line for wrapping the text
     * @return Aligned text
     */
    private static List<String> alignInR(List<String> lines, int lineLength) {
        for (int i = 0; i < lines.toArray().length; i++) {
            String head = ManipulateStr.repeatStr(" ", lineLength - lines.get(i).length());
            lines.set(i, head + lines.get(i));
        }
        return lines;
    }


    /**
     * Centre-align text from the input list.
     *
     * @param lines      The input list which will be centre-aligned
     * @param lineLength Desired length of the line for wrapping the text
     * @return Aligned text
     */
    private static List<String> alignInC(List<String> lines, int lineLength) {
        for (int i = 0; i < lines.toArray().length; i++) {
            int sumBlank = lineLength - lines.get(i).length();
            String head = ManipulateStr.repeatStr(" ", sumBlank / 2 + sumBlank % 2);
            String tail = ManipulateStr.repeatStr(" ", sumBlank / 2);
            lines.set(i, head + lines.get(i) + tail);
        }
        return lines;
    }


    /**
     * Put input list on an ASCII art signpost.
     *
     * @param lines      The input list which will be put input list on an ASCII art signpost
     * @param lineLength Desired length of the line for wrapping the text
     * @return Aligned text
     */
    private static List<String> alignInS(List<String> lines, int lineLength) {
        lines.add(0, " " + ManipulateStr.repeatStr("_", lineLength + 2));
        lines.add(1, "/" + ManipulateStr.repeatStr(" ", lineLength + 2) + "\\");
        for (int i = 2; i < lines.toArray().length; i++) {
            lines.set(i, "| " + lines.get(i) + ManipulateStr.repeatStr(" ", lineLength - lines.get(i).length()) + " |");
        }
        lines.add("\\" + ManipulateStr.repeatStr("_", lineLength + 2) + "/");
        lines.add("        |  |");
        lines.add("        |  |");
        lines.add("        L_ |");
        lines.add("       / _)|");
        lines.add("      / /__L");
        lines.add("_____/ (____)");
        lines.add("       (____)");
        lines.add("_____  (____)");
        lines.add("     \\_(____)");
        lines.add("        |  |");
        lines.add("        |  |");
        lines.add("        \\__/");
        return lines;
    }
}
