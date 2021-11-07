package tests;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import animalchess.Piece;
import animalchess.Square;
import animalchess.Player;
import animalchess.Giraffe;
import animalchess.Cat;

/**
 * A Class which is used to test Piece.
 * <p>
 * Written by teacher, edited by 210016568
 *
 * @author Teacher
 */
public class PieceTest {

    private Piece wazir;
    private Piece silver;

    private Square square1;
    private Square square2;
    private Square square3;
    private Player michael;
    private Player oz;

    /**
     * The method to set up the variables for test.
     */
    @Before
    public void setup() {
        square1 = new Square(null, 1, 2);
        square2 = new Square(null, 1, 0);
        square3 = new Square(null, 2, 1);
        michael = new Player("Michael", 0);
        oz = new Player("Ozgur", 1);
        wazir = new Giraffe(michael, square1);
        silver = new Cat(oz, square3);
    }

    /**
     * The method to test getSquare.
     */
    @Test
    public void testGetSquare() {
        Assert.assertEquals(square1, wazir.getSquare());
    }

    /**
     * The method to test getOwner.
     */
    @Test
    public void testGetOwner() {
        Assert.assertEquals(michael, wazir.getOwner());
    }

    /**
     * The method to test beCaptured.
     */
    @Test
    public void testBeCaptured() {
        wazir.beCaptured(oz);
        Assert.assertEquals(oz, wazir.getOwner());
        Assert.assertNotEquals(michael, wazir.getOwner());
    }

    /**
     * The method to test move.
     */
    @Test
    public void testMove() {
        wazir.move(square2);
        Assert.assertEquals(square2, wazir.getSquare());
        Assert.assertNotEquals(square1, wazir.getSquare());
    }

    /**
     * The method to test move a piece to a non-empty square.
     */
    @Test
    public void testMoveAndCapture() {
        Assert.assertEquals(square3, silver.getSquare());
        Assert.assertEquals(oz, silver.getOwner());
        // wazir takes silver by moving to its square
        wazir.move(square3);
        Assert.assertEquals(square3, wazir.getSquare());
        Assert.assertNull(silver.getSquare());
        Assert.assertEquals(michael, silver.getOwner());
        Assert.assertNotEquals(oz, silver.getOwner());
    }

}
