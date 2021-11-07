package tests;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import animalchess.Player;
import animalchess.Piece;
import animalchess.Square;
import animalchess.Giraffe;

import java.util.ArrayList;

/**
 * A Class which is used to test Player.
 * <p>
 * Written by teacher, edited by 210016568
 *
 * @author Teacher
 */
public class PlayerTest {

    private Player michael;
    private Player oz;

    private Piece wazir;
    private Square square0;
    private Square square1;

    /**
     * The method to set up the variables for test.
     */
    @Before
    public void setup() {
        michael = new Player("Michael", 0);
        oz = new Player("Ozgur", 1);

        // This stuff should work even without a Game object!
        square0 = new Square(null, 0, 0);
        square1 = new Square(null, 0, 2);
        wazir = new Giraffe(michael, square1);
    }

    /**
     * The method to test if this Player can be created.
     */
    @Test
    public void testExists() {
        Assert.assertNotNull(michael);
    }

    /**
     * The method to test getGame.
     */
    @Test
    public void testGetName() {
        Assert.assertEquals("Michael", michael.getName());
        Assert.assertEquals("Ozgur", oz.getName());
    }

    /**
     * The method to test getPlayerNumber.
     */
    @Test
    public void testGetPlayerNumber() {
        Assert.assertEquals(0, michael.getPlayerNumber());
        Assert.assertEquals(1, oz.getPlayerNumber());
    }

    /**
     * The method to test hasWon.
     */
    @Test
    public void testHasWonFalse() {
        Assert.assertFalse(michael.hasWon());
    }

    /**
     * The method to test what happen if a guy win the game.
     */
    @Test
    public void testHasWonTrue() {
        michael.winGame();
        Assert.assertTrue(michael.hasWon());
    }

    /**
     * The method to test getHand.
     */
    @Test
    public void testGetHandEmpty() {
        ArrayList<Piece> hand = michael.getHand();
        Assert.assertEquals(0, hand.size());
    }

    /**
     * The method to test add piece to hand.
     */
    @Test
    public void testAddPieceToHand() {
        michael.addPieceToHand(wazir);
        ArrayList<Piece> hand = michael.getHand();
        Assert.assertEquals(1, hand.size());
        Assert.assertEquals(wazir, hand.get(0));
    }

    /**
     * The method to test drop Piece.
     */
    @Test
    public void testDropPiece() {
        michael.addPieceToHand(wazir);
        michael.dropPiece(wazir, square1);
        ArrayList<Piece> hand = michael.getHand();
        Assert.assertEquals(0, hand.size());
        Assert.assertEquals(wazir, square1.getPiece());
        Assert.assertEquals(square1, wazir.getSquare());
    }

}
