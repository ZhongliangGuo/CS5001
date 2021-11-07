package tests;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import animalchess.Player;
import animalchess.Game;
import animalchess.Square;
import animalchess.Lion;
import animalchess.Piece;

import java.util.ArrayList;

/**
 * A Class which is used to test Lion.
 * <p>
 * Written by teacher, edited by 210016568
 *
 * @author Teacher
 */
public class LionTest {

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
     * The method to test getLegalMoves.
     */
    @Test
    public void testGetLegalMovesTestEdge() {
        // Add an extra lion to the board, to the left of its own chicks
        Lion li = new Lion(p1, game.getSquare(3, 0));
        // From this position it can make four moves
        // (it can't move to its own chick, or off the board)
        ArrayList<Square> moves = li.getLegalMoves();
        Assert.assertEquals(4, moves.size());
        Assert.assertTrue(moves.contains(game.getSquare(2, 0)));
        Assert.assertTrue(moves.contains(game.getSquare(2, 1)));
        Assert.assertTrue(moves.contains(game.getSquare(4, 0)));
        Assert.assertTrue(moves.contains(game.getSquare(4, 1)));
    }

    /**
     * The method to test getLegalMoves.
     */
    @Test
    public void testGetLegalMovesExisting() {
        // Find P1's lion (bottom-middle)
        // (can move to any of the three squares directly in front of it)
        Piece li = game.getSquare(5, 2).getPiece();
        Assert.assertTrue(li instanceof Lion);
        ArrayList<Square> moves = li.getLegalMoves();
        Assert.assertEquals(3, moves.size());
        Assert.assertTrue(moves.contains(game.getSquare(4, 1)));
        Assert.assertTrue(moves.contains(game.getSquare(4, 2)));
        Assert.assertTrue(moves.contains(game.getSquare(4, 3)));
    }

    /**
     * The method to test put a new Piece in an non-empty Square.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetLegalMovesTestFail() {
        // Add an extra lion to the board in an occupied space
        Lion li = new Lion(p0, game.getSquare(2, 1));
        Assert.fail("the last line should have thrown an exception");
    }

    /**
     * The method to test beCaptured.
     */
    @Test
    public void testBeCaptured() {
        // Find P0's lion (top-middle)
        Piece li = game.getSquare(0, 2).getPiece();
        Assert.assertFalse(p1.hasWon());

        // P1 captures P0's lion and wins the game
        li.beCaptured(p1);
        Assert.assertTrue(p1.hasWon());
    }
}
