package animalchess;

import animalchess.myutil.Point;

import java.util.ArrayList;

/**
 * An abstract class to define the Piece and its behaviours.
 * <p>
 * It will be inherited by different kinds of animals
 *
 * @author 210016568
 * @version 4
 * @since 1
 */
public abstract class Piece {
    /**
     * The owner of this Piece.
     */
    private Player owner;
    /**
     * The Square this Piece is in.
     */
    private Square square;
    /**
     * The variable to record whether this Piece was captured once before.
     */
    private boolean isCapturedBefore = false;

    /**
     * The constructor of Piece Class.
     *
     * @param owner  The owner of this Piece
     * @param square The square this Piece will be put in
     */
    public Piece(Player owner, Square square) {
        // To check whether this square is empty
        if (square.getPiece() != null) {
            throw new IllegalArgumentException();
        }
        this.owner = owner;
        square.placePiece(this);
        this.square = square;
    }

    /**
     * The abstract method to get the legal movement of this Piece.
     * <p>
     * it will be inherited and overridden by different kinds of animals
     *
     * @return A list which contains all the legal squares it can be placed
     */
    public abstract ArrayList<Square> getLegalMoves();

    /**
     * The method for Piece to move itself to other Square.
     *
     * @param toSquare The square this Piece will move to
     */
    public void move(Square toSquare) {
        // To figure out if toSquare is empty
        if (toSquare.getPiece() == null) {
            // If it is empty, directly move in
            this.getSquare().removePiece();
            toSquare.placePiece(this);
        } else {
            // If it is not empty, the Piece in toSquare will be eaten
            /*
            Actually, here I want to use recursive thoughts to directly call this method itself,
            but it may confuse the reader, so I choose to use this way to write code.
            if recursive one is better, the code should be here:
            {
            Piece captured = toSquare.getPiece();
            captured.beCaptured(this.getOwner());
            toSquare.removePiece();
            move();
            }
            */
            this.getSquare().removePiece();
            Piece captured = toSquare.getPiece();
            captured.beCaptured(this.getOwner());
            toSquare.removePiece();
            toSquare.placePiece(this);
        }
    }

    /**
     * The method to let this Piece be captured.
     *
     * @param capturer The capturer
     */
    public void beCaptured(Player capturer) {
        this.setSquare(null);
        capturer.addPieceToHand(this);
        this.owner = capturer;
        this.isCapturedBefore = true;
    }

    /**
     * The method to get the Square this Piece is in.
     *
     * @return The Square this Piece is in
     */
    public Square getSquare() {
        return this.square;
    }

    /**
     * The method to put this Piece in Square.
     *
     * @param square The destination
     */
    public void setSquare(Square square) {
        this.square = square;
    }

    /**
     * The method to get the owner of this Piece.
     *
     * @return The owner of this Piece
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * The method to get whether this Piece was captured before.
     *
     * @return The result
     */
    public boolean getIsCapturedBefore() {
        return isCapturedBefore;
    }

    /**
     * A method to check whether the potential moves are legal.
     * <p>
     * It will be inherited by other animals
     * It will check whether the potential moves are in the checkerboard
     *
     * @param potentialMoves the potential moves which will be checked
     * @param thisGame       which game this piece in
     * @return the legal moves in potential moves
     */
    public ArrayList<Square> isLegal(Point[] potentialMoves, Game thisGame) {
        ArrayList<Square> legalMoves = new ArrayList<>();
        for (Point potentialMove : potentialMoves) {
            int tempRow = potentialMove.getRow();
            int tempCol = potentialMove.getCol();
            // To check if it is in the scope of checkerboard
            if (tempRow < Game.HEIGHT && tempRow >= 0) {
                if (tempCol < Game.WIDTH && tempCol >= 0) {
                    Square tempSquare = thisGame.getSquare(potentialMove.getRow(), potentialMove.getCol());
                    // To check if this Square is empty
                    if (tempSquare.getPiece() == null) {
                        // If it is empty, directly put it in
                        legalMoves.add(tempSquare);
                    }
                    // If it is not empty, check its owner
                    else if (tempSquare.getPiece().getOwner() != this.getOwner()) {
                        // The Piece can only move on the Piece which belongs to enemy
                        legalMoves.add(tempSquare);
                    }
                }
            }
        }
        return legalMoves;
    }
}
