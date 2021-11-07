package animalchess;

import animalchess.myutil.Point;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A class to define the Chick Piece and its behaviours.
 * <p>
 * It inherits from PromotablePiece
 *
 * @author 210016568
 * @version 1
 * @since 1
 */
public class Chick extends PromotablePiece {
    /**
     * The constructor of Chicken Class.
     *
     * @param owner  The owner of this Piece
     * @param square The Square this Piece will be put in
     */
    public Chick(Player owner, Square square) {
        super(owner, square);
    }

    /**
     * The method for Chick to get its legal moves.
     *
     * @return a list of squares which is the legal moves for Chick
     */
    @Override
    public ArrayList<Square> getLegalMoves() {
        // The row of position this piece now stand at.
        int nowRow = this.getSquare().getRow();
        // The col of position this piece now stand at.
        int nowCol = this.getSquare().getCol();
        // The game this piece is in.
        Game thisGame = this.getSquare().getGame();

        //use Point to illustrate the potential moves for Cat Piece before it getting promoted
        Point[] originalPotentialMovesForP0 = {
                new Point(nowRow + 1, nowCol),
        };
        Point[] originalPotentialMovesForP1 = {
                new Point(nowRow - 1, nowCol),
        };

        //use Point to illustrate the potential moves for Cat Piece after it getting promoted
        Point[] promotedPotentialMovesForP0 = {
                new Point(nowRow - 1, nowCol),
                new Point(nowRow, nowCol - 1), new Point(nowRow, nowCol + 1),
                new Point(nowRow + 1, nowCol - 1), new Point(nowRow + 1, nowCol), new Point(nowRow + 1, nowCol + 1),
        };
        Point[] promotedPotentialMovesForP1 = {
                new Point(nowRow - 1, nowCol - 1), new Point(nowRow - 1, nowCol), new Point(nowRow - 1, nowCol + 1),
                new Point(nowRow, nowCol - 1), new Point(nowRow, nowCol + 1),
                new Point(nowRow + 1, nowCol),
        };

        // Check whether this piece is captured before because the pieces in hand cannot move when it is dropped.
        if (this.getIsCapturedBefore()) {
            return new ArrayList<>();
        }
        // Check whether this piece is promoted because promotion will let piece have different directions to move.
        else if (this.getIsPromoted()) {
            if (Objects.equals(this.getOwner().getName(), "p0")) {
                return isLegal(promotedPotentialMovesForP0, thisGame);
            } else {
                return isLegal(promotedPotentialMovesForP1, thisGame);
            }
        } else {
            if (Objects.equals(this.getOwner().getName(), "p0")) {
                return isLegal(originalPotentialMovesForP0, thisGame);
            } else {
                return isLegal(originalPotentialMovesForP1, thisGame);
            }
        }
    }
}
