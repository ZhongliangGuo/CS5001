/**
 * A class to configure this Server.
 * <p>
 * Referred the structure from Michael, written by 210016568
 *
 * @author 210016568
 */
public abstract class Configuration {
    /**
     * The scope of port.
     * <p>
     * According to the textbook, the port number should between 0 and 65535,
     * and some port such as 53 is for DNS.
     * <p>
     * maybe we need to forbid user accessing some certain port, so here I used an Array,
     * we check it in WebServerMain if the input port is in this List.
     * <p>
     * For usage, we can directly add the port we use in this array.
     */
    public static final int[] SCOPE_OF_PORT_NUMBER = {12345, 54321};
    /**
     * The max number of connection for one port.
     * <p>
     * I give a random number, so user can change it
     * to control the resource in OS this Server program use
     */
    public static final int MAX_NUMBER_OF_CONNECTION = 50;
}
