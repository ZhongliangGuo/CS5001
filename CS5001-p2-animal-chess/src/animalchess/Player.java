package animalchess;

import java.util.ArrayList;

/**
 * A class to define Player and its behaviour.
 *
 * @author 210016568
 * @version 3
 * @since 1
 */
public class Player {
    /**
     * The name of the Player.
     * because once Player has been named, it cannot be changed, so use the "final"
     */
    private final String name;
    /**
     * The unique number of the Player.
     */
    private final int playerNumber;
    /**
     * The list of pieces which are captured by this Player and held in hands.
     */
    private final ArrayList<Piece> handPiece = new ArrayList<>();
    /**
     * The variable used to record whether this player wins or not.
     */
    private boolean won = false;

    /**
     * The constructor of Player Class.
     *
     * @param name         the name of this Player
     * @param playerNumber the unique number of this Player
     */
    public Player(String name, int playerNumber) {
        if (!(playerNumber == 0 || playerNumber == 1)) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.playerNumber = playerNumber;
    }

    /**
     * The method to get the name of this Player.
     *
     * @return The name of this Player
     */
    public String getName() {
        return name;
    }

    /**
     * The method to get the unique number of this Player.
     *
     * @return The unique number of this Player
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * The method to get the pieces in the hand of this Player.
     *
     * @return The list of pieces in the hand of this Player
     */
    public ArrayList<Piece> getHand() {
        return handPiece;
    }

    /**
     * The method to add a piece to this Player's hand.
     *
     * @param piece The piece will be added
     */
    public void addPieceToHand(Piece piece) {
        Square s = piece.getSquare();
        if (s != null) {
            s.removePiece();
        }
        piece.setSquare(null);
        this.handPiece.add(piece);
    }

    /**
     * The method to drop an in-hand piece on the empty checkerboard.
     *
     * @param piece  The piece will be dropped
     * @param square The square this piece will be dropped on
     */
    public void dropPiece(Piece piece, Square square) {
        // Check whether this square is empty
        if (square.getPiece() != null) {
            throw new IllegalArgumentException();
        }
        handPiece.remove(piece);
        square.placePiece(piece);
    }

    /**
     * The method to let this Player win the game.
     */
    public void winGame() {
        this.won = true;
    }

    /**
     * The method to check whether this Player win the game.
     *
     * @return The result whether this Player win the game
     */
    public boolean hasWon() {
        return won;
    }

}
