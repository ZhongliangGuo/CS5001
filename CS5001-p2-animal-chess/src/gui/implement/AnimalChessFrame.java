package gui.implement;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A class to define the Frame of Animal Chess.
 *
 * @author 210016568
 * @version 3
 * @since 1
 */
public class AnimalChessFrame extends JFrame implements MouseListener {

    private final AnimalChessPanel panel = new AnimalChessPanel();

    /**
     * The constructor.
     */
    public AnimalChessFrame() {
        // get the width and height of the screen
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        // set title of window
        this.setTitle("Animal Chess GUI");
        // set size of window
        this.setSize(1053, 813);
        // make the size of window cannot change
        this.setResizable(false);
        // put it in the center of screen
        this.setLocation((width - 1053) / 2, (height - 813) / 2);
        // set the type of close window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addMouseListener(this);
        // set Panel
        panel.setLayout(null);
        //jp.setBounds(31, 56, 500, 600);
        // use Button as Square, and the title of Button is the Piece

        this.add(panel);


    }

    /**
     * The method to paint the GUI.
     *
     * @param g The object of Graphics Class
     */
    public void paint(Graphics g) {
        panel.repaint();
    }

    /**
     * The event when mouse will click.
     *
     * @param e The event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * The event when mouse will press.
     *
     * @param e The event
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * The event when mouse will release.
     *
     * @param e The event
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * The event when mouse will enter.
     *
     * @param e The event
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * The event when mouse will exit.
     *
     * @param e The event
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
