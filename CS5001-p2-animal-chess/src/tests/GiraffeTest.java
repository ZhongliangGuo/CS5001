package tests;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import animalchess.Player;
import animalchess.Game;
import animalchess.Square;
import animalchess.Giraffe;
import animalchess.Piece;

import java.util.ArrayList;

/**
 * A Class which is used to test Giraffe.
 * <p>
 * Written by teacher, edited by 210016568
 *
 * @author Teacher
 */
public class GiraffeTest {

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
        // Add an extra giraffe to the board
        Giraffe d = new Giraffe(p1, game.getSquare(4, 4));
        // From this position it can only make two moves - up and left
        ArrayList<Square> moves = d.getLegalMoves();
        Assert.assertEquals(2, moves.size());
        Assert.assertTrue(moves.contains(game.getSquare(3, 4)));
        Assert.assertTrue(moves.contains(game.getSquare(4, 3)));
    }

    /**
     * The method to test getLegalMoves.
     */
    @Test
    public void testGetLegalMovesStart() {
        // Find P1's giraffe (bottom-left) - can only move forward
        Piece d = game.getSquare(5, 1).getPiece();
        Assert.assertTrue(d instanceof Giraffe);
        Assert.assertEquals(1, d.getLegalMoves().size());
    }

    /**
     * The method to test getLegalMoves.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetLegalMovesTestFail() {
        // Add an extra giraffe to the board
        Giraffe d = new Giraffe(p0, game.getSquare(0, 3));
    }
}
