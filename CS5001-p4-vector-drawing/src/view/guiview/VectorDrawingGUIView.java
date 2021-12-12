package view.guiview;

import controller.Controller;
import model.Model;
import vector.Vector;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class to implement the main gui.
 * <p>
 * The javadoc of overridden method is from their parents classes.
 *
 * @author 210016568
 */
public class VectorDrawingGUIView implements PropertyChangeListener {

    private final Model model;
    private final Controller controller;
    private JFrame mainFrame;
    private Canvas canvas;
    private JPanel vectorButtonsPanel;
    private static final int DEFAULT_FRAME_WIDTH = 1200;
    private static final int DEFAULT_FRAME_HEIGHT = 800;
    private static final int CANVAS_WIDTH = 900;
    private static final int CANVAS_HEIGHT = 700;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 40;
    private final Dimension buttonSize = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
    private JButton undoButton;
    private JButton redoButton;

    /**
     * The constructor of this class.
     */
    public VectorDrawingGUIView(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        initialize();
        model.addListener(this);
        model.addListener(canvas);

    }

    /**
     * The method to initialize the Vector Drawing GUI.
     */
    private void initialize() {
        // initialize main window
        initializeMainWindow();
        // initialize vector buttons panel
        initializeVectorButtonsPanel();
        // initialize color choose panel

        // initialize canvas
        this.canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT, controller);
        canvas.setVisible(true);
        // add components into main frame
        mainFrame.add(canvas, BorderLayout.CENTER);
        mainFrame.add(vectorButtonsPanel, BorderLayout.WEST);
        mainFrame.setVisible(true);
    }

    /**
     * The method to initialize the main frame.
     */
    private void initializeMainWindow() {
        mainFrame = new JFrame("CS5001 practical 4 Vector Drawing Matriculation Number: 210016568");
        mainFrame.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
    }

    /**
     * The method to initialize the left button panel.
     */
    private void initializeVectorButtonsPanel() {
        vectorButtonsPanel = new JPanel();
        vectorButtonsPanel.setPreferredSize(new Dimension(BUTTON_WIDTH, CANVAS_HEIGHT));
        vectorButtonsPanel.setBackground(Color.GRAY);
        // initialize HashMap which can map the button title to draw mode
        HashMap<String, String> buttonTitleToDrawMode;
        buttonTitleToDrawMode = new HashMap<>();
        buttonTitleToDrawMode.put("line", Vector.LINE);
        buttonTitleToDrawMode.put("rectangle", Vector.RECT);
        buttonTitleToDrawMode.put("ellipse", Vector.ELLIPSE);
        buttonTitleToDrawMode.put("d cross", Vector.D_CROSS);
        buttonTitleToDrawMode.put("square", Vector.SQUARE);
        buttonTitleToDrawMode.put("circle", Vector.CIRCLE);
        buttonTitleToDrawMode.put("triangle", Vector.TRIANGLE);
        // create buttons
        ArrayList<JButton> vectorButtonList = new ArrayList<>();
        for (String title : new String[]{"line", "rectangle", "ellipse", "d cross", "square", "circle", "triangle"}) {
            JButton tempButton = new JButton(title);
            tempButton.setPreferredSize(buttonSize);
            tempButton.addActionListener(e -> {
                canvas.setDrawMode(buttonTitleToDrawMode.get(title));
                JButton clickedButton = (JButton) e.getSource();
                for (JButton button : vectorButtonList) {
                    button.setEnabled(true);
                }
                clickedButton.setEnabled(false);
            });
            vectorButtonsPanel.add(tempButton);
            vectorButtonList.add(tempButton);
        }
        vectorButtonList.get(0).setEnabled(false);
        // undo
        undoButton = new JButton("undo");
        undoButton.setPreferredSize(buttonSize);
        undoButton.addActionListener(e -> {
            controller.controlUndo();
            mainFrame.repaint();
        });
        vectorButtonsPanel.add(undoButton);
        // redo
        redoButton = new JButton("redo");
        redoButton.setPreferredSize(buttonSize);
        redoButton.addActionListener(e -> {
            controller.controlRedo();
            mainFrame.repaint();
        });
        vectorButtonsPanel.add(redoButton);
        // clear
        JButton clearButton = new JButton("clear");
        clearButton.setPreferredSize(buttonSize);
        clearButton.addActionListener(e -> {
            controller.controlClear();
            mainFrame.repaint();
        });
        vectorButtonsPanel.add(clearButton);
        // when open this window, here is no redo or undo actions
        undoButton.setEnabled(false);
        redoButton.setEnabled(false);
        // add a disabled button as a label to show the color chosen
        JButton colorLabel = new JButton("");
        colorLabel.setPreferredSize(buttonSize);
        colorLabel.setBackground(Color.BLACK);
        colorLabel.setEnabled(false);
        vectorButtonsPanel.add(colorLabel);
        // implement color chooser
        JButton setColorButton = new JButton("set color");
        setColorButton.setPreferredSize(buttonSize);
        setColorButton.addActionListener(e -> {
            Color chosenColor = JColorChooser.showDialog(new JColorChooser(), "Choose Color", canvas.getColor());
            // if user close the dialogue without choice, the result will be null
            // so give an if to maintain previous not null color value
            if (chosenColor != null) {
                canvas.setColor(chosenColor);
                colorLabel.setBackground(chosenColor);
            }
        });
        vectorButtonsPanel.add(setColorButton);
        // save the canvas as a file
        JButton saveButton = new JButton("save");
        saveButton.setPreferredSize(buttonSize);
        saveButton.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser();
            // only file can be chosen
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            // extension name limitation
            jfc.setFileFilter(new FileNameExtensionFilter("Serializable file (.ser)", "ser"));
            jfc.setSelectedFile(new File("new file.ser"));
            int statusCode = jfc.showDialog(mainFrame, "save");
            // figure out if the file save successfully
            if (statusCode == JFileChooser.APPROVE_OPTION) {
                if (controller.controlSave(jfc.getSelectedFile().getPath())) {
                    JOptionPane.showMessageDialog(mainFrame, "save successfully!");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "save failed!");
                }
            } else if (statusCode == JFileChooser.ERROR_OPTION) {
                JOptionPane.showMessageDialog(mainFrame, "unknown error!");
            }
        });
        vectorButtonsPanel.add(saveButton);
        // open the canvas from a file
        JButton openButton = new JButton("open");
        openButton.setPreferredSize(buttonSize);
        openButton.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser();
            // only file can be chosen
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            // extension name limitation
            jfc.setFileFilter(new FileNameExtensionFilter("Serializable file (.ser)", "ser"));
            int statusCode = jfc.showDialog(mainFrame, "open");
            // figure out if the file open successfully
            if (statusCode == JFileChooser.APPROVE_OPTION) {
                if (controller.controlOpen(jfc.getSelectedFile().getPath())) {
                    JOptionPane.showMessageDialog(mainFrame, "open successfully!");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "open failed!");
                }
            } else if (statusCode == JFileChooser.ERROR_OPTION) {
                JOptionPane.showMessageDialog(mainFrame, "unknown error!");
            }
        });
        vectorButtonsPanel.add(openButton);
    }

    /**
     * This method gets called when a bound property is changed.
     * <p>
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // if the vector list changed, the redo and undo list will change
        // so here need to change its enabled for redo and undo button
        redoButton.setEnabled(!model.isRedoStackEmpty());
        undoButton.setEnabled(!model.isUndoStackEmpty());
    }
}
