package animalchess.myutil;

/**
 * A class to define the Point.
 * <p>
 * This class is used to help the different Pieces describe potential legal move points.
 *
 * @author 210016568
 * @version 1
 * @since 1
 */
public class Point {
    private final int row;
    private final int col;

    /**
     * The constructor of Point Class.
     *
     * @param row the row for Point
     * @param col the col for Point
     */
    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * The method to get row of this Point.
     *
     * @return The row of this Point
     */
    public int getRow() {
        return row;
    }

    /**
     * The method to get col of this Point.
     *
     * @return The col of this Point
     */
    public int getCol() {
        return col;
    }
}
