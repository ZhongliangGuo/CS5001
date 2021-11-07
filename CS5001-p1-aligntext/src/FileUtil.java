import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Simple file utility class for reading text files.
 *
 * @author jonl
 */
public class FileUtil {


    /**
     * Attempts to read the text file specified by filename and returns an array of Strings (paragraphs) found in the file.
     *
     * @param filename of the file to read
     * @return Returns an array of Strings, each string representing a paragraph of text from the file. If an exception occurs an array containing the empty string is returned.
     */
    public static String[] readFile(String filename) {
        try {
            // try to read from the specified file and store paragraphs (lines of text
            // with new-line at end) in list and convert list to array for return
            FileReader fr = new FileReader(filename);
            BufferedReader bfr = new BufferedReader(fr);
            ArrayList<String> content = new ArrayList<String>();
            String paragraph = null;
            while ((paragraph = bfr.readLine()) != null) {
                content.add(paragraph);
            }
            String[] paragraphs = new String[content.size()];
            for (int i = 0; i < content.size(); i++) {
                paragraphs[i] = content.get(i);
            }
            return paragraphs;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Ooops: " + e.getMessage());
        }
        // If an exception occurred we will get to here as the return statement above was not executed
        // so setup a paragraphs array to return which contains the empty string
        String[] paragraphs = new String[1];
        paragraphs[0] = "";
        return paragraphs;
    }
}
