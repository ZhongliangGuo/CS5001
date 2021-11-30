import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class for CS5001 Practical 3 networking.
 * <p>
 * Referred the structure form Michael
 *
 * @author 210016568
 */
public class WebServerMain {


    /**
     * The main function to run this project.
     *
     * @param args The input from cmd
     *             args[0] should be document root
     *             args[1] should be port
     */
    public static void main(String[] args) {
        try {
            // get the input from cmd
            String documentRoot = args[0];
            int port = Integer.parseInt(args[1]);
            // to check the scope of port number, the description of this can be seen in Configuration
            List<Integer> legalPortList = Arrays.stream(
                    Configuration.SCOPE_OF_PORT_NUMBER).boxed().collect(Collectors.toList()
            );
            if (legalPortList.contains(port)) {
                throw new Exception("Port number exceed the scope 0 to 65535");
            }
            /* create a new server here, if the document root is wrong
            this program will corrupt
            then throw an Exception
            so that can print the output "Usage: java WebServerMain <document_root> <port>"
             */
            new Server(documentRoot, port);
        } catch (Exception e) {
            System.out.print("Usage: java WebServerMain <document_root> <port>");
            System.exit(0);
        }
    }
}
