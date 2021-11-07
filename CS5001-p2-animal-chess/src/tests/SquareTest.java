package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import animalchess.Square;
import animalchess.Player;
import animalchess.Piece;
import animalchess.Giraffe;
import animalchess.Cat;


/**
 * A Class which is used to test Square.
 * <p>
 * Written by teacher, edited by 210016568
 *
 * @author Teacher
 */
public class SquareTest {

    private Square square;
    private Square square2;
    private Piece wazir;
    private Piece silver;
    private Player michael;

    /**
     * The method to set up the variables for test.
     */
    @Before
    public void setup() {
        // no game (null)
        square = new Square(null, 1, 2);
        square2 = new Square(null, 3, 1);
        michael = new Player("Michael", 0);
        wazir = new Giraffe(michael, square);
        silver = new Cat(michael, square2);
    }

    /**
     * The method to test if the Square is empty.
     */
    @Test
    public void testExists() {
        Assert.assertNotNull(square);
    }

    /**
     * The method to test getRow.
     */
    @Test
    public void testGetRow() {
        Assert.assertEquals(1, square.getRow());
    }

    /**
     * The method to test getCol.
     */
    @Test
    public void testGetCol() {
        Assert.assertEquals(2, square.getCol());
    }

    /**
     * The method to test getGame.
     */
    @Test
    public void testGetGameNull() {
        // this one was created with a null game
        Assert.assertNull(square.getGame());
    }

    /**
     * The method to test getPiece.
     */
    @Test
    public void testGetPiece() {
        Assert.assertEquals(wazir, square.getPiece());
    }

    /**
     * The method to test if Player place the piece in a not empty Square.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPlacePieceFail() {
        // place silver on square occupied by wazir
        square.placePiece(silver);
        Assert.fail("the last line should have thrown an exception");
    }

    /**
     * The method to test removePiece.
     */
    @Test
    public void testRemovePiece() {
        square.removePiece();
        Assert.assertNull(square.getPiece());
    }

    /**
     * The method to test placePiece when the destination is empty.
     */
    @Test
    public void testPlacePieceSuccess() {
        square.removePiece();
        square.placePiece(silver);
        Assert.assertEquals(silver, square.getPiece());
    }
}
