package view.guiview;

import controller.Controller;
import vector.Vector;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * A class to define canvas.
 * <p>
 * The javadoc of overridden method is from their parents classes.
 *
 * @author 210016568
 */
@SuppressWarnings("unchecked")
public class Canvas extends JPanel implements MouseListener, PropertyChangeListener {
    private int x1;
    private int y1;
    private final int width;
    private final int height;
    private String drawMode = Vector.LINE;
    private Color color;
    private ArrayList<Vector> vectorList = new ArrayList<>();
    private final Controller controller;

    /**
     * The constructor of this class.
     *
     * @param width      width
     * @param height     height
     * @param controller controller
     */
    public Canvas(int width, int height, Controller controller) {
        this.width = width;
        this.height = height;
        this.controller = controller;
        initialize();
    }

    /**
     * The method to initialize the whole frame.
     */
    private void initialize() {
        this.color = Color.BLACK;
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.white);
        addMouseListener(this);
    }

    /**
     * The method to set draw mode.
     *
     * @param drawMode the draw mode
     */
    public void setDrawMode(String drawMode) {
        this.drawMode = drawMode;
    }

    /**
     * The getter of color.
     *
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * The setter of color.
     *
     * @param color color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * The method to paint sth on the canvas.
     *
     * @param g the Graphics obj
     */
    @Override
    public void paint(Graphics g) {
        for (Vector vector : vectorList) {
            vector.drawVector(g);
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        controller.controlAddVector(new Vector(x1, y1, e.getX(), e.getY(), drawMode, color));
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        vectorList = (ArrayList<Vector>) evt.getNewValue();
        paint(getGraphics());
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
