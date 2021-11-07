package gui.implement;

import animalchess.Game;

import javax.swing.JButton;

/**
 * A class to add coordinate in Button.
 * <p>
 * inherit from JButton
 *
 * @author Zhongliang
 * @version 1
 * @since 1
 */
public class ButtonWithCoordinate extends JButton {
    private final Game game;
    private final int row;
    private final int col;

    /**
     * The constructor of Button which has coordinates ,and it will be indexed by row and col.
     *
     * @param text The title of this button
     * @param game The Game it will in
     * @param row  The row for coordinate
     * @param col  The col for coordinate
     */
    public ButtonWithCoordinate(String text, Game game, int row, int col) {
        super(text);
        this.game = game;
        this.row = row;
        this.col = col;
    }

    /**
     * The getter of row.
     *
     * @return The row
     */
    public int getRow() {
        return row;
    }

    /**
     * The getter of col.
     *
     * @return The col
     */
    public int getCol() {
        return col;
    }

    /**
     * The getter of game.
     *
     * @return The game
     */
    public Game getGame() {
        return this.game;
    }
}
