package tests;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import animalchess.Game;
import animalchess.Player;
import animalchess.Square;
import animalchess.Piece;
import animalchess.Chick;

import java.util.ArrayList;

/**
 * A Class which is used to test Chick.
 * <p>
 * Written by teacher, edited by 210016568
 *
 * @author Teacher
 */
public class ChickTest {

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
        Chick ch = new Chick(p1, game.getSquare(1, 2));
        Assert.assertFalse(ch.getIsPromoted());
        ch.promote();
        Assert.assertTrue(ch.getIsPromoted());
    }

    /**
     * The method to test move to the promotion zone to get promoted.
     */
    @Test
    public void testMoveToPromote() {
        Chick ch = new Chick(p1, game.getSquare(1, 2));
        Assert.assertFalse(ch.getIsPromoted());
        // move to final rank (and promote)
        ch.move(game.getSquare(0, 2));
        Assert.assertTrue(ch.getIsPromoted());
    }

    /**
     * The method to test getLegalMoves.
     */
    @Test
    public void testGetLegalMovesTestEdge() {
        // Add an extra chick to the board, near the opponent's pieces
        Chick ch = new Chick(p1, game.getSquare(1, 2));
        // From this position it can make only one move
        ArrayList<Square> moves = ch.getLegalMoves();
        Assert.assertEquals(1, moves.size());
        Assert.assertEquals(game.getSquare(0, 2), moves.get(0));
    }

    /**
     * The method to test getLegalMoves.
     */
    @Test
    public void testGetLegalMovesPromoted() {
        // Add an extra chick to the board, near the opponent's pieces
        Chick ch = new Chick(p1, game.getSquare(1, 4));
        // Promote it so it has extra movement options
        ch.promote();
        // It can now make four moves (but can't move right off the board)
        ArrayList<Square> moves = ch.getLegalMoves();
        Assert.assertEquals(4, moves.size());
    }

    /**
     * The method to test getLegalMoves.
     */
    @Test
    public void testGetLegalMovesExisting() {
        // Find P1's left chick - can only move forward
        Piece ch = game.getSquare(3, 1).getPiece();
        Assert.assertEquals(1, ch.getOwner().getPlayerNumber());
        ArrayList<Square> moves = ch.getLegalMoves();
        Assert.assertEquals(1, moves.size());
        Assert.assertEquals(game.getSquare(2, 1), moves.get(0));
    }

    /**
     * The method to test getLegalMoves.
     */
    @Test
    public void testGetLegalMovesPromoteExisting() {
        // Find P1's left chick - can only move forward
        Chick ch = (Chick) game.getSquare(3, 1).getPiece();
        Assert.assertEquals(1, ch.getOwner().getPlayerNumber());
        // Promote it
        ch.promote();
        // Can now move 5 directions (but not right onto P1's other chick)
        ArrayList<Square> moves = ch.getLegalMoves();
        Assert.assertEquals(5, moves.size());
    }

    /**
     * The method to test getLegalMoves.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetLegalMovesTestFail() {
        // Add an extra chick to the board in an occupied space
        Chick ch = new Chick(p0, game.getSquare(2, 1));
        Assert.fail("the last line should have thrown an exception");
    }
}
