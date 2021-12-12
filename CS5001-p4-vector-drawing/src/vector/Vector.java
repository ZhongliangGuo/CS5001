package vector;

import java.awt.*;
import java.io.Serializable;

/**
 * A class to define the vector.
 *
 * @author 210016568
 */
public class Vector implements Serializable {
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;
    private final String drawMode;
    private final Color color;
    public static final String LINE = "LINE";
    public static final String RECT = "RECT";
    public static final String ELLIPSE = "ELLIPSE";
    public static final String D_CROSS = "D_CROSS";
    public static final String SQUARE = "SQUARE";
    public static final String CIRCLE = "CIRCLE";
    public static final String TRIANGLE = "TRIANGLE";

    /**
     * The constructor of this class.
     *
     * @param x1       x1
     * @param y1       y1
     * @param x2       x2
     * @param y2       y2
     * @param drawMode draw mode
     * @param color    color
     */
    public Vector(int x1, int y1, int x2, int y2, String drawMode, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.drawMode = drawMode;
        this.color = color;
    }

    /**
     * The method to draw the vector.
     *
     * @param g the paintbrush of canvas
     */
    public void drawVector(Graphics g) {
        g.setColor(color);
        // side length for square and circle
        int sideLength = Math.min(Math.abs(x1 - x2), Math.abs(y1 - y2));
        // reset the coordinator of vector as left-top xx1-yy1, right-bottom yy1-yy2
        // to avoid the inaccuracy when user draw vector from right bottom to left top
        int xx1 = Math.min(x1, x2);
        int xx2 = Math.max(x1, x2);
        int yy1 = Math.min(y1, y2);
        int yy2 = Math.max(y1, y2);
        switch (drawMode) {
            case LINE:
                g.drawLine(x1, y1, x2, y2);
                break;
            case RECT:
                g.drawRect(xx1, yy1, (xx2 - xx1), (yy2 - yy1));
                break;
            case ELLIPSE:
                g.drawOval(xx1, yy1, xx2 - xx1, yy2 - yy1);
                break;
            case D_CROSS:
                // here I assume that the size of the diagonal cross should lock the aspect ratio (i.e., 1:1)
                float lineLength = (float) (sideLength / 4.0 * Math.sqrt(2));
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(lineLength));
                g2d.drawLine(xx1, yy1, xx1 + sideLength, yy1 + sideLength);
                g2d.drawLine(xx1, yy1 + sideLength, xx1 + sideLength, yy1);
                g2d.setStroke(new BasicStroke(1f));
                break;
            case SQUARE:
                g.drawRect(xx1, yy1, sideLength, sideLength);
                break;
            case CIRCLE:
                g.drawOval(xx1, yy1, sideLength, sideLength);
                break;
            case TRIANGLE:
                // because triangle need different direction, so I use x1 y1 instead of xx1 yy1
                int mid = (xx1 + xx2) / 2;
                g.drawLine(x1, y2, x2, y2);
                g.drawLine(x1, y2, mid, y1);
                g.drawLine(x2, y2, mid, y1);
                break;
        }
    }
}