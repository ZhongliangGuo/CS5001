package gui.implement;

import animalchess.Game;
import animalchess.Piece;
import animalchess.Player;
import animalchess.Square;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * A class to define the JPanel for GUI.
 *
 * @author 210016568
 * @version 2
 * @since 1
 */
public class AnimalChessPanel extends JPanel {
    private final Player p0 = new Player("p0", 0);
    private final Player p1 = new Player("p1", 1);
    private final Game myGame = new Game(p0, p1);
    /**
     * The array to get button.
     */
    private final ButtonWithCoordinate[][] buttonList = new ButtonWithCoordinate[6][5];
    /**
     * The length of Square.
     */
    private final int lengthOfSquare = 100;
    /**
     * The hand pieces list of p0.
     */
    private final ArrayList<JButton> p0HandPiece = new ArrayList<>();
    /**
     * The hand pieces list of p1.
     */
    private final ArrayList<JButton> p1HandPiece = new ArrayList<>();
    private boolean isInMove = false;
    private Player turn = p0;
    private Piece pieceInMoving;

    /**
     * The constructor.
     */
    public AnimalChessPanel() {
        initializeCheckBoard();
    }

    /**
     * The method to get buttonList.
     *
     * @return The buttonList
     */
    public ButtonWithCoordinate[][] getButtonList() {
        return buttonList;
    }

    /**
     * The method to paint implement in Panel.
     *
     * @param g The object of Graphic Class
     */
    public void paint(Graphics g) {
        // Paint Lines for checkerboard
        /*
          The X position of checkerboard.
         */
        int checkerBoardStartPointX = 35;
        /*
          The Y position of checkerboard.
         */
        int checkerBoardStartPointY = 85;
        for (int i = 0; i < 7; i++) {
            g.drawLine(checkerBoardStartPointX, checkerBoardStartPointY + lengthOfSquare * i,
                    checkerBoardStartPointX + 5 * lengthOfSquare, checkerBoardStartPointY + lengthOfSquare * i);
        }
        for (int i = 0; i < 6; i++) {
            g.drawLine(checkerBoardStartPointX + lengthOfSquare * i, checkerBoardStartPointY,
                    checkerBoardStartPointX + lengthOfSquare * i, checkerBoardStartPointY + lengthOfSquare * 6);
        }
        // Paint Lines for space which stores hand Piece
        /*
          The X position of hand piece layout.
         */
        int handPieceStartPointX = 550;
        /*
          The Y position of hand piece layout.
         */
        int handPieceStartPointY = 85;
        for (int i = 0; i < 3; i++) {
            g.drawLine(handPieceStartPointX, handPieceStartPointY + lengthOfSquare * i,
                    handPieceStartPointX + lengthOfSquare * 4, handPieceStartPointY + lengthOfSquare * i);
        }
        for (int i = 4; i < 7; i++) {
            g.drawLine(handPieceStartPointX, handPieceStartPointY + lengthOfSquare * i,
                    handPieceStartPointX + lengthOfSquare * 4, handPieceStartPointY + lengthOfSquare * i);
        }
        for (int i = 0; i < 5; i++) {
            g.drawLine(handPieceStartPointX + lengthOfSquare * i, handPieceStartPointY,
                    handPieceStartPointX + lengthOfSquare * i, handPieceStartPointY + lengthOfSquare * 2);
        }
        for (int i = 0; i < 5; i++) {
            g.drawLine(handPieceStartPointX + lengthOfSquare * i, handPieceStartPointY + lengthOfSquare * 4,
                    handPieceStartPointX + lengthOfSquare * i, handPieceStartPointY + lengthOfSquare * 6);
        }
        // Paint Squares
        for (ButtonWithCoordinate[] i : buttonList) {
            for (ButtonWithCoordinate j : i) {
                j.repaint();
            }
        }
        // Paint the hand Piece of p0
        for (JButton b : p0HandPiece) {
            b.repaint();
        }
        // Paint the hand Piece of p1
        for (JButton b : p1HandPiece) {
            b.repaint();
        }
    }

    /**
     * The getter of p0's hand pieces.
     *
     * @return the hand pieces list
     */
    public ArrayList<JButton> getP0HandPiece() {
        return p0HandPiece;
    }

    /**
     * The getter of p1's hand pieces.
     *
     * @return the hand pieces list
     */
    public ArrayList<JButton> getP1HandPiece() {
        return p1HandPiece;
    }

    /**
     * The method to illustrate what happen when button is clicked.
     *
     * @param b The button which was clicked
     */
    public void clicked(ButtonWithCoordinate b) {
        if (!isInMove) {
            // that means here is the start of a turn
            pieceInMoving = myGame.getSquare(b.getRow(), b.getCol()).getPiece();
            ArrayList<Square> moves = b.getGame().getSquare(b.getRow(), b.getCol()).getPiece().getLegalMoves();
            for (ButtonWithCoordinate[] eachRow : buttonList) {
                for (ButtonWithCoordinate eachB : eachRow) {
                    eachB.setEnabled(false);
                }
            }
            for (Square p : moves) {
                buttonList[p.getRow()][p.getCol()].setEnabled(true);
            }
        } else {
            // that means piece is in move
            ButtonWithCoordinate movingButton = buttonList[pieceInMoving.getSquare().getRow()][pieceInMoving.getSquare().getCol()];
            pieceInMoving.move(myGame.getSquare(b.getRow(), b.getCol()));
            b.setText(movingButton.getText());
            movingButton.setText("");
            if (turn == p0) {
                turn = p1;
            } else {
                turn = p0;
            }
            resetEnableForTurn(turn);
        }
        isInMove = !isInMove;
        if (myGame.getWinner() != null) {
            JOptionPane.showMessageDialog(null, myGame.getWinner().getName() + "win the game!");
            for (ButtonWithCoordinate[] b1 : buttonList) {
                for (ButtonWithCoordinate b2 : b1) {
                    b2.setEnabled(false);
                }
            }
        }
    }

    private void initializeCheckBoard() {
        /*
          The length of button.
         */
        int lengthOfButton = 80;
        for (int row = 0; row < Game.HEIGHT; row++) {
            for (int col = 0; col < Game.WIDTH; col++) {
                ButtonWithCoordinate temp = new ButtonWithCoordinate("", myGame, row, col);
                buttonList[row][col] = temp;
                temp.setBounds(44 + lengthOfSquare * col, 96 + lengthOfSquare * row, lengthOfButton, lengthOfButton);
                temp.setEnabled(false);
                temp.addActionListener(e -> clicked((ButtonWithCoordinate) e.getSource()));
                this.add(temp);
            }
        }
        buttonList[0][0].setText("p0Cat");
        buttonList[0][0].setEnabled(true);
        buttonList[0][1].setText("p0Giraffe");
        buttonList[0][1].setEnabled(true);
        buttonList[0][2].setText("p0Lion");
        buttonList[0][2].setEnabled(true);
        buttonList[0][3].setText("p0Giraffe");
        buttonList[0][3].setEnabled(true);
        buttonList[0][4].setText("p0Cat");
        buttonList[0][4].setEnabled(true);
        buttonList[2][1].setText("p0Chick");
        buttonList[2][1].setEnabled(true);
        buttonList[2][2].setText("p0Chick");
        buttonList[2][2].setEnabled(true);
        buttonList[2][3].setText("p0Chick");
        buttonList[2][3].setEnabled(true);
        buttonList[5][0].setText("p1Cat");
        buttonList[5][1].setText("p1Giraffe");
        buttonList[5][2].setText("p1Lion");
        buttonList[5][3].setText("p1Giraffe");
        buttonList[5][4].setText("p1Cat");
        buttonList[3][1].setText("p1Chick");
        buttonList[3][2].setText("p1Chick");
        buttonList[3][3].setText("p1Chick");
        // Set up the hand Piece square for p0
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                JButton temp = new JButton("");
                temp.setBounds(560 + lengthOfSquare * i, 95 + lengthOfSquare * j, lengthOfButton, lengthOfButton);
                temp.setEnabled(false);
                this.add(temp);
                p0HandPiece.add(temp);
            }
        }
        // Set up the hand Piece square for p1
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                JButton temp = new JButton("");
                temp.setBounds(560 + lengthOfSquare * i, 95 + lengthOfSquare * (j + 4), lengthOfButton, lengthOfButton);
                temp.setEnabled(false);
                this.add(temp);
                p1HandPiece.add(temp);
            }
        }
    }

    private void resetEnableForTurn(Player turn) {
        for (ButtonWithCoordinate[] b1 : buttonList) {
            for (ButtonWithCoordinate b2 : b1) {
                Piece tempPiece = myGame.getSquare(b2.getRow(), b2.getCol()).getPiece();
                b2.setEnabled(false);
                if (tempPiece != null) {
                    b2.setEnabled(myGame.getSquare(b2.getRow(), b2.getCol()).getPiece().getOwner() == turn);
                }
            }
        }
    }
}
