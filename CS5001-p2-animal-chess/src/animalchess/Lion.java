package animalchess;

import animalchess.myutil.Point;

import java.util.ArrayList;

/**
 * A class to define the Lion Piece.
 * <p>
 * It inherits from Piece
 *
 * @author 210016568
 * @version 1
 * @since 1
 */
public class Lion extends Piece {
    /**
     * The constructor of Lion Class.
     *
     * @param owner  The owner of this Piece
     * @param square The Square this Piece will be put in
     */
    public Lion(Player owner, Square square) {
        super(owner, square);
    }

    /**
     * The overridden method for Loin to get its legal moves.
     *
     * @return a list of squares which is the legal moves for Loin
     */
    @Override
    public ArrayList<Square> getLegalMoves() {
        int nowRow = this.getSquare().getRow();
        int nowCol = this.getSquare().getCol();
        Game thisGame = this.getSquare().getGame();

        //use Point to illustrate the potential moves for Lion Piece
        Point[] potentialMoves = {
                new Point(nowRow - 1, nowCol - 1), new Point(nowRow - 1, nowCol), new Point(nowRow - 1, nowCol + 1),
                new Point(nowRow, nowCol - 1), new Point(nowRow, nowCol + 1),
                new Point(nowRow + 1, nowCol - 1), new Point(nowRow + 1, nowCol), new Point(nowRow + 1, nowCol + 1),
        };

        return isLegal(potentialMoves, thisGame);
    }

    /**
     * The overridden method to let Loin be captured.
     *
     * @param capturer The capturer
     */
    @Override
    public void beCaptured(Player capturer) {
        // The Player who capture the Lion will win the game
        super.beCaptured(capturer);
        capturer.winGame();
    }

}
