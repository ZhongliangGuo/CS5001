package animalchess;

/**
 * An abstract class inherit from Piece for Cat and Chick.
 * <p>
 * It inherits from Piece
 * This class used to describe the Piece which can be promoted
 *
 * @author 210016568
 * @version 2
 * @since 1
 */
public abstract class PromotablePiece extends Piece {
    /**
     * The promotion status of the promotable Piece.
     */
    private boolean promoted;

    /**
     * The constructor of PromotablePiece Class.
     *
     * @param owner  The owner of this Piece
     * @param square The Square this Piece will be put in
     */
    public PromotablePiece(Player owner, Square square) {
        super(owner, square);
        this.promoted = false;
    }

    /**
     * The method to get the promotion status of this Piece.
     *
     * @return The promotion status
     */
    public boolean getIsPromoted() {
        return promoted;
    }

    /**
     * The method to promote this Piece.
     */
    public void promote() {
        this.promoted = true;
    }

    /**
     * The overridden method for Piece to move itself.
     *
     * @param toSquare the Square it will be moved to
     */
    @Override
    public void move(Square toSquare) {
        super.move(toSquare);
        // If it will be moved to the last two lines, it will promote automatically
        if (toSquare.isPromotionZone(this.getOwner())) {
            this.promote();
        }
    }

    /**
     * The overridden method to let this Piece be captured.
     *
     * @param capturer The capturer
     */
    @Override
    public void beCaptured(Player capturer) {
        super.beCaptured(capturer);
        this.promoted = false;
    }


}
