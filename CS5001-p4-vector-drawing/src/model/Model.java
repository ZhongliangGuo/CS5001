package model;

import vector.Vector;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Stack;


/**
 * A class to define the model for vector drawing.
 *
 * @author 210016568
 */
@SuppressWarnings("unchecked")
public class Model {
    private final PropertyChangeSupport notifier;
    private Stack<ArrayList<Vector>> undoStack;
    private Stack<ArrayList<Vector>> redoStack;
    private ArrayList<Vector> vectorList;

    /**
     * The constructor of this class.
     */
    public Model() {
        this.notifier = new PropertyChangeSupport(this);
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.vectorList = new ArrayList<>();
    }

    /**
     * The method to add listener for this model.
     * <p>
     * Register a listener, so it will be notified of any changes.
     *
     * @param listener the listener
     */
    public void addListener(PropertyChangeListener listener) {
        notifier.addPropertyChangeListener(listener);
    }

    /**
     * The method to update change.
     * <p>
     * Broadcast most recent change to all listeners
     */
    private void update() {
        notifier.firePropertyChange("vectorList", null, vectorList);
    }

    /**
     * The method to add a vector into vector list.
     *
     * @param vector the vector
     */
    public void addVector(Vector vector) {
        redoStack.clear();
        undoStack.push((ArrayList<Vector>) vectorList.clone());
        vectorList.add(vector);
        update();
    }

    /**
     * The method to undo.
     */
    public void undo() {
        redoStack.push((ArrayList<Vector>) vectorList.clone());
        vectorList = undoStack.pop();
        update();
    }

    /**
     * The method to redo.
     */
    public void redo() {
        undoStack.push((ArrayList<Vector>) vectorList.clone());
        vectorList = redoStack.pop();
        update();
    }

    /**
     * The method to check if undo stack is empty.
     *
     * @return the result
     */
    public boolean isUndoStackEmpty() {
        return undoStack.empty();
    }

    /**
     * The method to check if redo stack is empty.
     *
     * @return the result
     */
    public boolean isRedoStackEmpty() {
        return redoStack.empty();
    }

    /**
     * The method to clear canvas.
     */
    public void clear() {
        undoStack.push((ArrayList<Vector>) vectorList.clone());
        vectorList = new ArrayList<>();
        update();
    }

    /**
     * The method shift model so that it can be saved model as a file.
     *
     * @return the class which can be saved in the file
     */
    public SaveAsFile save() {
        return new SaveAsFile(vectorList, undoStack, redoStack);
    }

    /**
     * The method to reload the model from a file.
     *
     * @param fileRead the class which is read from file
     */
    public void reload(SaveAsFile fileRead) {
        vectorList = fileRead.vectorList;
        undoStack = fileRead.undoStack;
        redoStack = fileRead.redoStack;
        update();
    }

    /**
     * The method to get the vector list.
     * <p>
     * is only called by tests.
     *
     * @return vector list
     */
    public ArrayList<Vector> getVectorList() {
        return vectorList;
    }

    /**
     * The method to get undo Stack.
     * <p>
     * is only called by tests.
     *
     * @return undo Stack
     */
    public Stack<ArrayList<Vector>> getUndoStack() {
        return undoStack;
    }

    /**
     * The method to get redo Stack.
     * <p>
     * is only called by tests.
     *
     * @return redo Stack
     */
    public Stack<ArrayList<Vector>> getRedoStack() {
        return redoStack;
    }
}
