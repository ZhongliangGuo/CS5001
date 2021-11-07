package tests;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import animalchess.Game;
import animalchess.Square;
import animalchess.Piece;
import animalchess.Player;
import animalchess.Lion;
import animalchess.Giraffe;
import animalchess.Chick;

import java.util.List;

/**
 * A Class which is used to test Game.
 * <p>
 * Written by teacher, edited by 210016568
 *
 * @author Teacher
 */
public class GameTest {

    private Game myGame;
    private Player p0;
    private Player p1;

    /**
     * The method to set up the variables for test.
     */
    @Before
    public void setup() {
        p0 = new Player("Michael", 0);
        p1 = new Player("Oz", 1);
        myGame = new Game(p0, p1);
    }

    /**
     * The method to test creat a Game object.
     */
    @Test
    public void testGameExists() {
        Assert.assertNotNull(myGame);
    }

    /**
     * The method to test getPlayer.
     */
    @Test
    public void testGetPlayer() {
        Assert.assertEquals(myGame.getPlayer(0), p0);
        Assert.assertEquals(myGame.getPlayer(1), p1);
    }

    /**
     * The method to test getPlayer.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetPlayerBad() {
        myGame.getPlayer(3);
        Assert.fail("the last line was supposed to throw an IllegalArgumentException");
    }

    /**
     * The method to test getWinner.
     */
    @Test
    public void testGetWinnerNone() {
        Assert.assertNull(myGame.getWinner());
    }

    /**
     * The method to test getWinner.
     */
    @Test
    public void testGetWinnerP0() {
        p0.winGame();
        Assert.assertNotNull(myGame.getWinner());
        Assert.assertEquals(myGame.getWinner(), p0);
    }

    /**
     * The method to test getSquare.
     */
    @Test
    public void testGetSquareEmpty() {
        Square emptySquare = myGame.getSquare(1, 0);
        Assert.assertNull(emptySquare.getPiece());
    }

    /**
     * The method to test getSquare.
     */
    @Test
    public void testGetSquareLion() {
        Square lionSquare = myGame.getSquare(0, 2);
        Piece p0Lion = lionSquare.getPiece();
        Assert.assertNotNull(p0Lion);
        Assert.assertTrue(p0Lion instanceof Lion);
    }

    /**
     * The method to play the full game.
     */
    @Test
    public void fullGame() {
        // This last test plays out a full game, from the beginning,
        // checking various properties along the way.

        // P0 moves first, moves his right-side chick to (3,3) (takes P1's chick)
        Chick chick0r = (Chick) myGame.getSquare(2, 3).getPiece();
        chick0r.move(myGame.getSquare(3, 3));
        Assert.assertEquals(1, p0.getHand().size());
        Assert.assertFalse(chick0r.getIsPromoted());

        // P1 moves right-side cat diagonally forward to (4,3)
        Piece cat1r = myGame.getSquare(5, 4).getPiece();
        cat1r.move(myGame.getSquare(4, 3));
        Assert.assertEquals(0, p1.getHand().size());
        Assert.assertEquals(4, cat1r.getSquare().getRow());

        // P0 moves his lion forward to (1,2)
        Piece lion0 = myGame.getSquare(0, 2).getPiece();
        Assert.assertTrue(lion0 instanceof Lion);
        Assert.assertEquals(3, lion0.getLegalMoves().size());
        lion0.move(myGame.getSquare(1, 2));
        Assert.assertEquals(4, lion0.getLegalMoves().size());

        // P1 moves his right-hand giraffe sideways
        Giraffe giraffe1r = (Giraffe) myGame.getSquare(5, 3).getPiece();
        giraffe1r.move(myGame.getSquare(5, 4));
        Assert.assertEquals(4, giraffe1r.getSquare().getCol());
        Assert.assertNull(myGame.getSquare(5, 3).getPiece());

        // P0's right-hand chick takes P1's cat, and promotes
        chick0r.move(cat1r.getSquare());
        Assert.assertEquals(4, chick0r.getSquare().getRow());
        Assert.assertEquals(2, p0.getHand().size());
        Assert.assertTrue(p0.getHand().contains(cat1r));
        Assert.assertTrue(chick0r.getIsPromoted());

        // P1 takes back with his right-hand giraffe
        // Note: this move is illegal.
        giraffe1r.move(myGame.getSquare(4, 3));
        // chick unpromotes when taken
        Assert.assertFalse(chick0r.getIsPromoted());
        // P1 owns the chick now
        Assert.assertEquals(p1, chick0r.getOwner());
        Assert.assertEquals(1, p1.getHand().size());

        // P0 drops the cat he captured earlier
        Assert.assertNull(cat1r.getSquare());
        p0.dropPiece(cat1r, myGame.getSquare(4, 2));
        Assert.assertEquals(1, p0.getHand().size());

        // P1 advances his central chick
        Chick chick1c = (Chick) myGame.getSquare(3, 2).getPiece();
        List<Square> moves = chick1c.getLegalMoves();
        Assert.assertEquals(1, moves.size());
        Square toSquare = moves.get(0);
        // contains P0's chick
        Assert.assertNotNull(toSquare.getPiece());
        // take P0's chick
        chick1c.move(toSquare);
        Assert.assertEquals(2, p1.getHand().size());

        // P0 moves his lion to the right
        Square square12 = myGame.getSquare(1, 2);
        Assert.assertEquals(lion0, square12.getPiece());
        Assert.assertEquals(5, lion0.getLegalMoves().size());
        lion0.move(myGame.getSquare(1, 3));
        Assert.assertEquals(3, lion0.getSquare().getCol());

        // P1 advances his central chick into the gap, and it promotes
        Assert.assertFalse(chick1c.getIsPromoted());
        Assert.assertNull(square12.getPiece());
        chick1c.move(square12);
        Assert.assertNotNull(square12.getPiece());
        Assert.assertTrue(chick1c.getIsPromoted());

        // P0 drops a chick on the centre file
        Piece chick0new = p0.getHand().get(0);
        p0.dropPiece(chick0new, myGame.getSquare(3, 2));
        Assert.assertTrue(p0.getHand().isEmpty());
        Assert.assertTrue(chick0new.getLegalMoves().isEmpty());

        // P1's promoted chick takes P0's lion!
        chick1c.move(lion0.getSquare());
        Assert.assertEquals(p1, myGame.getWinner());
        Assert.assertTrue(p1.hasWon());
        Assert.assertFalse(p0.hasWon());
    }
}
