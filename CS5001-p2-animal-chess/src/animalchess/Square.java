package animalchess;

/**
 * A class to define the Square which may contain a Piece.
 * <p>
 * Square is used to place piece
 *
 * @author 210016568
 * @version 1
 * @since 1
 */
public class Square {
    /**
     * The Game this Square in.
     */
    private final Game game;
    /**
     * The row of this Square.
     */
    private final int row;
    /**
     * The col of this Square.
     */
    private final int col;
    /**
     * The Player who can promote its Cats and Chicken in this Square.
     */
    private Player promotesPlayer = null;
    /**
     * The Piece of this Square.
     */
    private Piece piece = null;


    /**
     * The constructor of Square Class.
     * <p>
     * It will be reloaded
     *
     * @param game The game this Square belongs to
     * @param row  The row of this Square's position in the checkerboard
     * @param col  The col of this Square's position in the checkerboard
     */
    public Square(Game game, int row, int col) {
        this.game = game;
        this.row = row;
        this.col = col;
    }


    /**
     * The constructor of Square Class.
     * <p>
     * Overload it
     *
     * @param game           The game this Square belongs to
     * @param row            The row of this Square's position in the checkerboard
     * @param col            The col of this Square's position in the checkerboard
     * @param promotesPlayer The Player which can promote in this Square
     */
    public Square(Game game, int row, int col, Player promotesPlayer) {
        this.game = game;
        this.row = row;
        this.col = col;
        this.promotesPlayer = promotesPlayer;
    }

    /**
     * The method to place Piece into this Square.
     *
     * @param piece The Piece which will be put in this Square
     */
    public void placePiece(Piece piece) {
        // To check if it is empty
        if (this.piece != null) {
            // if it is not empty, throw an Exception
            throw new IllegalArgumentException();
        }
        piece.setSquare(this);
        this.piece = piece;
    }

    /**
     * The method to remove the Piece from this Square.
     */
    public void removePiece() {
        this.piece = null;
    }

    /**
     * The method to check if this Square is promotion zone for the input Player.
     *
     * @param player The Player which will be checked
     * @return The result
     */
    public boolean isPromotionZone(Player player) {
        return player == this.promotesPlayer;
    }

    /**
     * The method to get the game this Square in.
     *
     * @return The Game this Square in.
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * The method to get the Piece which is put this Square.
     *
     * @return The Piece in this Square
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * The method to get the row of this Square.
     *
     * @return The row of this Square
     */
    public int getRow() {
        return row;
    }

    /**
     * The method to get the col of this Square.
     *
     * @return The col of this Square
     */
    public int getCol() {
        return col;
    }
}
