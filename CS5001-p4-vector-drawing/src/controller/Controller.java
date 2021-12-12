package controller;

import model.Model;
import model.SaveAsFile;
import vector.Vector;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

/**
 * A class to define the controller of MVC.
 *
 * @author 210016568
 */
public class Controller {
    private final Model model;

    /**
     * The constructor of this class.
     *
     * @param model the model this controller will control
     */
    public Controller(Model model) {
        this.model = model;
    }

    /**
     * The method to add vector in model.
     *
     * @param vector the vector
     */
    public void controlAddVector(Vector vector) {
        model.addVector(vector);
    }

    /**
     * The method to undo in model.
     */
    public void controlUndo() {
        model.undo();
    }

    /**
     * The method to redo in model.
     */
    public void controlRedo() {
        model.redo();
    }

    /**
     * The method to clear in model.
     */
    public void controlClear() {
        model.clear();
    }

    /**
     * The method to save model as a file.
     *
     * @param path the path the file will be stored
     */
    public boolean controlSave(String path) {
        try {
            if (!path.endsWith(".ser")) {
                path = path + ".ser";
            }
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(model.save());
            out.close();
            fileOut.close();
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }

    /**
     * The method to reload model from a file.
     *
     * @param path the path the file was stored
     */
    public boolean controlOpen(String path) {
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            SaveAsFile fileRead = (SaveAsFile) in.readObject();
            in.close();
            fileIn.close();
            // ensure that here is nothing wrong (even close the io stream), then can reload model
            model.reload(fileRead);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
