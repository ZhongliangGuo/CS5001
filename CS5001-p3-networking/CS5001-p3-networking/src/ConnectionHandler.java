import java.awt.image.BufferedImage;
import java.net.Socket;
import javax.imageio.ImageIO;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * A class to define the connection handler.
 *
 * @author 210016568
 */
public class ConnectionHandler extends Thread {
    // the variables here is really common, I defined them as same as Michael's code
    /** The TCP/IP connection between Server and Client. */
    private final Socket conn;
    /** The document root from which server will serve documents to clients. */
    private final String documentRoot;
    /** Input stream from client. */
    private InputStream is;
    /** The output stream from server. */
    private OutputStream os;
    /** The reader to read client data. */
    private BufferedReader br;
    /** The writer to write data which will be sent to client. */
    private BufferedWriter bw;
    /** The Server this Conn belongs to, for limiting the number of connections for a port. */
    private final Server server;

    /**
     * The constructor of ConnectionHandler Class.
     *
     * @param conn         The TCP/IP connection between Server and Client
     * @param documentRoot The document root from which server will serve documents to clients
     * @param server       The counter for limit the number of connections
     */
    public ConnectionHandler(Socket conn, String documentRoot, Server server) {
        this.conn = conn;
        this.documentRoot = documentRoot;
        this.server = server;
        try {
            is = conn.getInputStream();
            os = conn.getOutputStream();
            br = new BufferedReader(new InputStreamReader(is));
            bw = new BufferedWriter(new FileWriter(".." + File.separator + "log.txt", true));
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler initial with error: " + ioe.getMessage());
        }
    }

    /**
     * The method to figure out if the request is valid.
     *
     * @param line the input line from client
     * @return the type of request, if valid, it is get or head, if it is not valid, return Not Implemented
     */
    private String isValidRequest(String line) throws DisconnectedException {
        // figure out if the client close the Conn
        if (line == null || line.equals("null")) {
            throw new DisconnectedException("client has closed the connection");
        }
        String method = line.split(" ")[0];
        if (method.contains("HEAD")) {
            return "HEAD";
        }
        if (method.contains("GET")) {
            return "GET";
        } else {
            return "Not Implemented";
        }
    }

    /**
     * The method inherited from Thread.
     * <p>
     * To deal with request
     */
    public void run() {
        System.out.println("start a new ConnectionHandler thread");
        // this connection run successfully, so add 1 conn.
        server.addConn();
        try {
            while (true) {
                // get the data or request from client by using BufferReader
                String line = br.readLine();

                String filename = line.split(" ")[1];
                //log request
                bw.append(String.valueOf(new Date())).append(" ").append(line);
                bw.newLine();
                String method = isValidRequest(line);
                switch (method) {
                    case "HEAD":
                        os.write(getHeadContent(documentRoot, filename));
                        break;
                    case "GET":
                        os.write(getGetContent(documentRoot, filename));
                        break;
                    default:
                        os.write(returnFor501());
                }
                // print the data or request from client
                System.out.println("\nConnectionHandler: " + line);
                cleanUp();
            }
        } catch (Exception e) {
            // exit with closing all Stream objects
            System.out.println("ConnectionHandler run with error:" + e.getMessage());
            cleanUp();
        }
    }

    /**
     * The method to get the content for HEAD.
     *
     * @param documentRoot The document root from which this server will serve documents to clients
     * @param filename     The path of file
     * @return The content for HEAD
     */
    private byte[] getHeadContent(String documentRoot, String filename) throws IOException {
        ByteArrayOutputStream bAos = new ByteArrayOutputStream();
        String path = documentRoot + filename;
        byte[] fileContent = getFileContent(path);
        String feedbackString = "HTTP/1.1 200 OK\r\n" + getContentType(chooseFileType(filename))
                + "Content-Length: " + fileContent.length + "\r\n\r\n";
        try {
            // try catch is used to figure out whether file can be found
            br = new BufferedReader(new FileReader(path));
            br.close();

            System.out.println(feedbackString);
            //log the response
            bw.append(String.valueOf(new Date())).append(" ").append(feedbackString);
            bw.newLine();

            bAos.write(feedbackString.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            // What happen if file not found
            feedbackString = "HTTP/1.1 404 Not Found\r\nContent-Type: text/html\r\nContent-Length: "
                    + fileContent.length + "\r\n";
            System.out.println(feedbackString);
            //log the response
            bw.append(String.valueOf(new Date())).append(" ").append(feedbackString);
            bw.newLine();
            bAos = new ByteArrayOutputStream();
            bAos.write(feedbackString.getBytes(StandardCharsets.UTF_8));
            bAos.write(fileContent);
        }
        return bAos.toByteArray();
    }

    /**
     * A method to get the content for GET request.
     *
     * @param documentRoot The document root from which this server will serve documents to clients
     * @param filename     The path of file
     * @return The content for GET request
     */
    private byte[] getGetContent(String documentRoot, String filename) throws IOException {
        ByteArrayOutputStream bAos = new ByteArrayOutputStream();
        String path = documentRoot + filename;
        String feedbackString = "";
        try {
            // try catch is used to figure out whether file can be found
            br = new BufferedReader(new FileReader(path));
            br.close();
            feedbackString += "HTTP/1.1 200 OK\r\n" + getContentType(chooseFileType(filename))
                    + "Content-Length: " + getFileContent(path).length + "\r\n\r\n";
            System.out.println(feedbackString);
            //log the response
            bw.append(String.valueOf(new Date())).append(" ").append(feedbackString);
            bw.newLine();

            bAos.write(feedbackString.getBytes(StandardCharsets.UTF_8));
            bAos.write(getFileContent(path));
        } catch (Exception e) {
            // What happen if file not found
            feedbackString += "HTTP/1.1 404 Not Found\r\nContent-Type: text/html\r\nContent-Length: "
                    + getFileContent(path).length + "\r\n";
            System.out.println(feedbackString);
            //log the response
            bw.append(String.valueOf(new Date())).append(" ").append(feedbackString);
            bw.newLine();
            bAos.write(feedbackString.getBytes(StandardCharsets.UTF_8));
            bAos.write(getFileContent(path));
        }
        return bAos.toByteArray();
    }

    /**
     * The method to get file content for HEAD and GET.
     * <p>
     * referred the usage of API from a website called cnblogs,
     * here is link: https://www.cnblogs.com/feathe/p/10510787.html
     *
     * @param path The path of file
     * @return The content of File, if file not exist, return 404 Not Found
     */
    private byte[] getFileContent(String path) {
        StringBuilder content = new StringBuilder();
        ByteArrayOutputStream bAos;
        try {
            BufferedImage img;
            switch (chooseFileType(path)) {
                case "jpg":
                    //get JPG binary file
                    img = ImageIO.read(new File(path));
                    bAos = new ByteArrayOutputStream();
                    ImageIO.write(img, "jpeg", bAos);
                    return bAos.toByteArray();
                case "gif":
                    //get GIF binary file
                    img = ImageIO.read(new File(path));
                    bAos = new ByteArrayOutputStream();
                    ImageIO.write(img, "gif", bAos);
                    break;
                case "png":
                    //get PNG binary file
                    img = ImageIO.read(new File(path));
                    ByteArrayOutputStream baOutput = new ByteArrayOutputStream();
                    ImageIO.write(img, "png", baOutput);
                    return baOutput.toByteArray();
                default:
                    //get HTML file
                    br = new BufferedReader(new FileReader(path));
                    String string;
                    while ((string = br.readLine()) != null) {
                        // Implementing line feeds
                        content.append(string).append("\r\n");
                    }
                    br.close();
                    break;
            }
        } catch (IOException e) {
            // return a simple 404 page
            // because I don't know which www resource is used when the teacher tests the code,
            // so I do not change any files in www, just append the html text here.
            content.append(
                    "<html lang=\"en\">"
                            + "<head>"
                            + "<title>404 Page not found</title>"
                            + "</head>"
                            + "<body>"
                            + "<p><h1>Page not found</h1></p>"
                            + "<p>CS5001 practical 3 networking, matriculation ID: 210016568.</p>"
                            + "<p>The page you were looking for may have been moved or deleted.</p>"
                            + "<p>If you typed the web address, check it is correct.</p>"
                            + "<p>If you pasted the web address, check you copied the entire address.</p>"
                            + "</body>"
                            + "</html>"
            );
        }
        return content.toString().getBytes();
    }

    /**
     * A method to deal with 501 Situation.
     * <p>
     * referred the content String from a website called CSDN,
     * here is url: https://blog.csdn.net/weixin_30290193/article/details/114280378
     *
     * @return The content text of 501 situation
     */
    private byte[] returnFor501() throws IOException {
        ByteArrayOutputStream bAos = new ByteArrayOutputStream();
        byte[] content = "501 Not Implemented".getBytes();
        String feedbackString = "HTTP/1.1 501 Not Implemented\r\nContent-Type: text/html\r\nContent-Length: "
                + content.length + "\r\n\r\n";
        System.out.println(feedbackString);
        //log the response
        bw.append(String.valueOf(new Date())).append(" ").append(feedbackString);
        bw.newLine();
        bAos.write(feedbackString.getBytes(StandardCharsets.UTF_8));
        bAos.write(content);
        return bAos.toByteArray();
    }

    /**
     * A method to get the file type.
     * <p>
     * because there are too many if and else if, so I put this into a method to improve the readability.
     *
     * @param filename the filename
     * @return the type of this file
     */
    private String chooseFileType(String filename) {
        if (filename.contains(".jpg")) {
            return "jpg";
        } else if (filename.contains(".gif")) {
            return "gif";
        } else if (filename.contains(".png")) {
            return "png";
        } else {
            return "html";
        }
    }

    /**
     * The method to get content type text.
     *
     * @param type the type
     * @return content type text
     */
    private String getContentType(String type) {
        switch (type) {
            case "jpg":
                return "Content-Type: image/jpeg\r\n";
            case "png":
                return "Content-Type: image/png\r\n";
            case "gif":
                return "Content-Type: image/gif\r\n";
            default:
                return "Content-Type: text/html\r\n";
        }
    }

    /**
     * The method to clean up.
     * <p>
     * Referred from Michael, written by 210016568
     */
    private void cleanUp() {
        System.out.println("ConnectionHandler: a connection of this port clean up and exit");
        try {
            // close readers, streams and connections
            br.close();
            is.close();
            conn.close();
            // when this connection closed, the counter should change its count
            server.delConn();
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler: clean up with error: " + ioe.getMessage());
        }
    }
}
