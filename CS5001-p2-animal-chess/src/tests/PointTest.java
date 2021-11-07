package tests;

import animalchess.myutil.Point;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * A class to test the Point.
 *
 * @author 210016568
 */
public class PointTest {
    private Point point1;
    private int row;
    private int col;

    /**
     * The method to set up the variables for test.
     */
    @Before
    public void setup() {
        point1 = new Point(3, 4);
        row = 3;
        col = 4;
    }

    /**
     * The method to test getRow and getCol.
     */
    @Test
    public void testGet() {
        Assert.assertEquals(row, point1.getRow());
        Assert.assertEquals(col, point1.getCol());
    }
}
