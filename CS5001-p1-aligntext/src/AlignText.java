import java.util.List;


/**
 * A class for CS5001 practice-1 AlignText.
 *
 * @author 210016568
 * @version 3
 * @since 1
 */
public class AlignText {
    /**
     * The length of "| " and " |" which is used in S type of alignment.
     */
    public static final int LENGTH_OF_HEAD_AND_TAIL_IN_S = 4;
    /**
     * The max number of arguments can be collected from CMD.
     */
    public static final int MAX_ARGUMENTS_NUM_FROM_CMD = 3;
    /**
     * The keyword for alignment type L.
     */
    public static final String TYPE_L = "L";
    /**
     * The keyword for alignment type R.
     */
    public static final String TYPE_R = "R";
    /**
     * The keyword for alignment type C.
     */
    public static final String TYPE_C = "C";
    /**
     * The keyword for alignment type S.
     */
    public static final String TYPE_S = "S";

    /**
     * The main method.
     *
     * @param args the input from CMD
     */
    public static void main(String[] args) {
        try {
            // the name (and path) of the file containing the text
            String fileName = args[0];

            // desired length of the line for wrapping the text
            int lineLength = Integer.parseInt(args[1]);
            if (lineLength < 1) {
                // the minimum lineLength should bigger than 0
                throw new Exception();
            }

            String alignmentType; // the type of text alignment
            if (args.length < MAX_ARGUMENTS_NUM_FROM_CMD) {
                // if no input of optional argument, default is L
                alignmentType = "L";
            } else {
                switch (args[2]) {
                    // select the type of alignment, if input is unrecognizable, default is left-aligned
                    case TYPE_R:
                        alignmentType = TYPE_R;
                        break;
                    case TYPE_C:
                        alignmentType = TYPE_C;
                        break;
                    case TYPE_S:
                        alignmentType = TYPE_S;
                        break;
                    default:
                        alignmentType = TYPE_L;
                        break;
                }

            }

            List<String> alignedText = AlignTextInLines.alignTextInLine(fileName, lineLength, alignmentType);

            if (alignedText.isEmpty()) {
                // alignText() will return empty list if there is something wrong
                throw new Exception();
            }

            for (String str : alignedText) {
                System.out.println(str);
            }

        } catch (Exception e) {
            System.out.println("usage: java AlignText file_name line_length [align_mode]");
        }
    }
}
