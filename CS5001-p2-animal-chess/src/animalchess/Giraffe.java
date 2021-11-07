package animalchess;

import animalchess.myutil.Point;

import java.util.ArrayList;

/**
 * A class to define Giraffe and its behaviours.
 * <p>
 * It inherits from Piece
 *
 * @author 210016568
 * @version 1
 * @since 1
 */
public class Giraffe extends Piece {
    /**
     * The constructor of Giraffe Class.
     *
     * @param owner  The owner of this Piece
     * @param square The Square this Piece will be put in
     */
    public Giraffe(Player owner, Square square) {
        super(owner, square);
    }

    /**
     * The method for Giraffe to get its legal moves.
     *
     * @return a list of squares which is the legal moves for Giraffe
     */
    @Override
    public ArrayList<Square> getLegalMoves() {
        int nowRow = this.getSquare().getRow();
        int nowCol = this.getSquare().getCol();
        Game thisGame = this.getSquare().getGame();

        //use Point to illustrate the potential moves for Giraffe Piece
        Point[] potentialMoves = {
                new Point(nowRow - 1, nowCol),
                new Point(nowRow, nowCol - 1), new Point(nowRow, nowCol + 1),
                new Point(nowRow + 1, nowCol),
        };

        return isLegal(potentialMoves, thisGame);
    }
}
