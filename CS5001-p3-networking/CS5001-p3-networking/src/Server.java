import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A class to define the Server.
 * <p>
 * it can listen for the request, I also added some method to limit the number of Conn
 *
 * @author 210016568
 */
public class Server {
    /**
     * This variable is used to count the number of Conn.
     *
     * because every Server object listen to a certain port,
     * so I cannot use "static" (it may listen to a different port by creating new object)
     */
    private int countOfConnections = 0;

    /**
     * The constructor of Server Class.
     *
     * @param documentRoot The documentRoot from which this server will serve documents to clients
     * @param port         The port on which this server should listen
     */
    public Server(String documentRoot, int port) {
        try {
            // The ServerSocket object to listen to request
            ServerSocket ss = new ServerSocket(port);
            // The counter for limit the number of connection
            System.out.println("Server started ... listening on port " + port + " ...");
            /* Determine if the maximum number of connections has been reached
             * I wanted to throw an Exception called ConnectionExceedLimitException here, but I found that if I do it,
             * the program will stop after throw Exception, it cannot meet the demand and the program will not very
             * stable, so I just write in this way
             */
            while (!isFull()) {
                Socket conn = ss.accept();
                /* Print the info for debug
                 * First print line is for figure out which IP address this server connected with
                 * Second print line is for figure out how many connections this port has and max number of connections
                 */
                System.out.println("This server get a connection with " + conn.getInetAddress());
                // Create new handler for this connection and start it
                ConnectionHandler ch = new ConnectionHandler(conn, documentRoot, this);
                // I do not addConn here, because I add it in the ConnectionHandler
                ch.start();
            }
        } catch (IOException ioe) {
            System.out.print("Server Connection error : " + ioe.getMessage());
        }
    }

    /**
     * The method to add a Conn count.
     */
    public void addConn() {
        countOfConnections += 1;
    }

    /**
     * The method to del a Conn count.
     */
    public void delConn() {
        countOfConnections -= 1;
    }

    /**
     * The Method to figure out if this Server is full.
     *
     * @return the result
     */
    public boolean isFull() {
        return !(countOfConnections < Configuration.MAX_NUMBER_OF_CONNECTION);
    }
}


