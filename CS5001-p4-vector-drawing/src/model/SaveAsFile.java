package model;

import vector.Vector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 * A class to shift the model to a class which can be saved in files.
 * <p>
 * Because the in Model Class, here are some listeners, it is hard to change,
 * so I save the vector list, undo stack and redo stack into this class.
 * So that it can be saved and reloaded without any change for model's listeners.
 *
 * @author 210016568
 */
public class SaveAsFile implements Serializable {
    private static final long serialVersionUID = 4780759220397161457L;
    ArrayList<Vector> vectorList;
    Stack<ArrayList<Vector>> undoStack;
    Stack<ArrayList<Vector>> redoStack;

    /**
     * The constructor of this class.
     *
     * @param vectorList the vector list
     * @param undoStack  undo list
     * @param redoStack  redo list
     */
    public SaveAsFile(ArrayList<Vector> vectorList, Stack<ArrayList<Vector>> undoStack, Stack<ArrayList<Vector>> redoStack) {
        this.vectorList = vectorList;
        this.undoStack = undoStack;
        this.redoStack = redoStack;
    }

    /**
     * The method to get vector list.
     * <p>
     * is only called by tests
     *
     * @return vector list
     */
    public ArrayList<Vector> getVectorList() {
        return vectorList;
    }

    /**
     * The method to get undo stack.
     * <p>
     * is only called by tests
     *
     * @return undo stack
     */
    public Stack<ArrayList<Vector>> getUndoStack() {
        return undoStack;
    }

    /**
     * The method to get redo stack.
     * <p>
     * is only called by tests
     *
     * @return redo stack
     */
    public Stack<ArrayList<Vector>> getRedoStack() {
        return redoStack;
    }
}
