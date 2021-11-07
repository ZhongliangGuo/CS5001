package gui;

import gui.implement.AnimalChessFrame;


/**
 * A class to run animal chess into GUI.
 *
 * @author 210016568
 * @version 1
 * @since 1
 */
public class AnimalChessGui {

    /**
     * Main method.
     *
     * @param args the input will get from cmd
     */
    public static void main(String[] args) {
        AnimalChessFrame animalChessWin = new AnimalChessFrame();
        // show the window
        animalChessWin.setVisible(true);
    }
}
