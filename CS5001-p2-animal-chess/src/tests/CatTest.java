package tests;

import org.junit.Assert;

import animalchess.Game;
import animalchess.Player;
import animalchess.Square;
import animalchess.Piece;
import animalchess.Cat;
import animalchess.PromotablePiece;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * A Class which is used to test Cat.
 * <p>
 * Written by teacher, edited by 210016568
 *
 * @author Teacher
 */
public class CatTest {

    private Player p0;
    private Player p1;
    private Game game;

    /**
     * The method to set up the variables for test.
     */
    @Before
    public void setup() {
        p0 = new Player("Michael", 0);
        p1 = new Player("Ozgur", 1);
        game = new Game(p0, p1);
    }

    /**
     * The method to test promote.
     */
    @Test
    public void testPromote() {
        Cat c = new Cat(p1, game.getSquare(1, 1));
        Assert.assertFalse(c.getIsPromoted());
        c.promote();
        Assert.assertTrue(c.getIsPromoted());
    }

    /**
     * The method to test move.
     */
    @Test
    public void testMoveToFinalRank() {
        Cat c = new Cat(p1, game.getSquare(1, 1));
        Assert.assertFalse(c.getIsPromoted());
        // move to final rank (and promote)
        c.move(game.getSquare(0, 1));
        Assert.assertTrue(c.getIsPromoted());
    }

    /**
     * The method to test move.
     */
    @Test
    public void testMoveToPenultimateRank() {
        Cat c = new Cat(p1, game.getSquare(2, 4));
        Assert.assertFalse(c.getIsPromoted());
        Assert.assertEquals(2, c.getLegalMoves().size());
        // move to final rank (and promote)
        c.move(game.getSquare(1, 4));
        Assert.assertTrue(c.getIsPromoted());
        Assert.assertEquals(4, c.getLegalMoves().size());
    }

    /**
     * The method to test geLegalMoves.
     */
    @Test
    public void testGetLegalMovesTestEdge() {
        // Add an extra cat to the board, near the opponent's pieces
        Cat c = new Cat(p1, game.getSquare(1, 4));
        // From this position it can make three moves (up, up-left, down-left)
        ArrayList<Square> moves = c.getLegalMoves();
        Assert.assertEquals(3, moves.size());
        Assert.assertTrue(moves.contains(game.getSquare(0, 3)));
        Assert.assertTrue(moves.contains(game.getSquare(0, 4)));
        Assert.assertTrue(moves.contains(game.getSquare(2, 3)));
    }

    /**
     * The method to test geLegalMoves.
     */
    @Test
    public void testGetLegalMovesPromoted() {
        // Add an extra cat to the board, near the opponent's pieces
        PromotablePiece c = new Cat(p1, game.getSquare(1, 4));
        // Promote it so it has extra movement options
        c.promote();
        // It can now make four moves (but can't move right off the board)
        ArrayList<Square> moves = c.getLegalMoves();
        Assert.assertEquals(4, moves.size());
    }

    /**
     * The method to test geLegalMoves.
     */
    @Test
    public void testGetLegalMovesExisting() {
        // Find P1's left cat - can only move forward or forward-right
        Piece c = game.getSquare(5, 0).getPiece();
        Assert.assertEquals(1, c.getOwner().getPlayerNumber());
        Assert.assertTrue(c instanceof Cat);
        ArrayList<Square> moves = c.getLegalMoves();
        Assert.assertEquals(2, moves.size());
        Assert.assertTrue(moves.contains(game.getSquare(4, 0)));
        Assert.assertTrue(moves.contains(game.getSquare(4, 1)));
    }

    /**
     * The method to test geLegalMoves.
     */
    @Test
    public void testGetLegalMovesPromoteExisting() {
        // Find P1's right cat - can only move forward or forward-left
        Cat c = (Cat) game.getSquare(5, 4).getPiece();
        Assert.assertEquals(1, c.getOwner().getPlayerNumber());
        Assert.assertTrue(c instanceof Cat);
        ArrayList<Square> moves = c.getLegalMoves();
        Assert.assertEquals(2, moves.size());
        // Promote it
        c.promote();
        // Can still only move 2 directions
        moves = c.getLegalMoves();
        Assert.assertEquals(2, moves.size());
    }

    /**
     * The method to test geLegalMoves.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetLegalMovesTestFail() {
        // Add an extra cat to the board in an occupied space
        Cat c = new Cat(p0, game.getSquare(2, 1));
        Assert.fail("the last line should have thrown an exception");
    }
}
