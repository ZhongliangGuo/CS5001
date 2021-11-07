package animalchess;

/**
 * A class to start a game.
 *
 * @author 210016568
 * @version 2
 * @since 1
 */
public class Game {
    /**
     * The height of checkerBoard.
     */
    public static final int HEIGHT = 6;
    /**
     * The width of checkerBoard.
     */
    public static final int WIDTH = 5;
    /**
     * The Player 0.
     */
    private final Player p0;
    /**
     * The Player 1.
     */
    private final Player p1;
    /**
     * The checkerboard to index the squares, the position should be (row, col).
     */
    private final Square[][] checkerBoard = new Square[HEIGHT][WIDTH];

    /**
     * The constructor of Game Class.
     *
     * @param p0 the Player 0
     * @param p1 the Player 1
     */
    public Game(Player p0, Player p1) {
        // initialize two player p0 and p1
        this.p0 = p0;
        this.p1 = p1;
        // initialize the first rows lines of checkerBoard
        // The number of rows for cats and chicken which they can promote.
        int promoteRowNumber = 2;
        for (int row = 0; row < promoteRowNumber; row++) {
            for (int col = 0; col < WIDTH; col++) {
                this.checkerBoard[row][col] = new Square(this, row, col, p1);
            }
        }
        // initialize the last rows lines of checkerBoard
        for (int row = HEIGHT - promoteRowNumber; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                this.checkerBoard[row][col] = new Square(this, row, col, p0);
            }
        }
        // initialize other rows of checkerBoard
        for (int row = promoteRowNumber; row < HEIGHT - promoteRowNumber; row++) {
            for (int col = 0; col < WIDTH; col++) {
                this.checkerBoard[row][col] = new Square(this, row, col);
            }
        }
        // initialize the pieces for player 0
        new Cat(p0, checkerBoard[0][0]);
        new Cat(p0, checkerBoard[0][4]);
        new Giraffe(p0, checkerBoard[0][1]);
        new Giraffe(p0, checkerBoard[0][3]);
        new Lion(p0, checkerBoard[0][2]);
        new Chick(p0, checkerBoard[2][1]);
        new Chick(p0, checkerBoard[2][2]);
        new Chick(p0, checkerBoard[2][3]);
        // initialize the pieces for player 1
        new Cat(p1, checkerBoard[5][0]);
        new Cat(p1, checkerBoard[5][4]);
        new Giraffe(p1, checkerBoard[5][1]);
        new Giraffe(p1, checkerBoard[5][3]);
        new Lion(p1, checkerBoard[5][2]);
        new Chick(p1, checkerBoard[3][1]);
        new Chick(p1, checkerBoard[3][2]);
        new Chick(p1, checkerBoard[3][3]);
    }

    /**
     * The method to get the Player in this game indexing by playerNumber.
     *
     * @param playerNumber the unique number of Player
     * @return The Player in the Game
     */
    public Player getPlayer(int playerNumber) {
        if (this.p0.getPlayerNumber() == playerNumber) {
            return p0;
        } else if (this.p1.getPlayerNumber() == playerNumber) {
            return p1;
        } else {
            // Deal with the situation that user input the non-existent playerNumber
            throw new IllegalArgumentException();
        }
    }

    /**
     * The method to get the winner of this game.
     *
     * @return The winner
     */
    public Player getWinner() {
        if (this.p0.hasWon()) {
            return this.p0;
        }
        if (this.p1.hasWon()) {
            return this.p1;
        }
        return null;
    }

    /**
     * The method to get the square on the checkerboard in this game indexing by row and col.
     *
     * @param row The row of the square position
     * @param col The col of the square position
     * @return The square which is on (row, col)
     */
    public Square getSquare(int row, int col) {
        try {
            return checkerBoard[row][col];
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
