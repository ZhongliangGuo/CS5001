package tests;

import model.Model;
import model.SaveAsFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import vector.Vector;

import java.awt.Color;

/**
 * A class to test Model.
 *
 * @author 210016568
 */
public class ModelTest {
    private Model model;
    private Vector vector1;
    private Vector vector2;
    private int x1;
    private int x2;
    private int y1;
    private int y2;

    /**
     * The method to set up the variables for test.
     */
    @Before
    public void setup() {
        vector1 = new Vector(x1, y1, x2, y2, Vector.LINE, Color.BLACK);
        vector2 = new Vector(x1, y1, x2, y2, Vector.CIRCLE, Color.RED);
        model = new Model();
    }

    /**
     * The method to test if Vector and Model can be created.
     */
    @Test
    public void testExist() {
        Assert.assertNotNull(vector1);
        Assert.assertNotNull(vector2);
        Assert.assertNotNull(model);
    }

    /**
     * The method to test if addVector can work.
     */
    @Test
    public void testAddVector() {
        Assert.assertTrue(model.isUndoStackEmpty());
        Assert.assertTrue(model.isRedoStackEmpty());
        model.addVector(vector1);
        Assert.assertFalse(model.isUndoStackEmpty());
        Assert.assertTrue(model.isRedoStackEmpty());
        model.addVector(vector2);
        Assert.assertEquals(model.getVectorList().toArray().length, 2);
        Assert.assertEquals(model.getVectorList().get(0), vector1);
        Assert.assertEquals(model.getVectorList().get(1), vector2);
    }

    /**
     * The method to test if undo, redo and clear can work.
     */
    @Test
    public void testUndoRedoClear() {
        model = new Model();
        model.addVector(vector1);
        model.addVector(vector2);
        model.undo();
        Assert.assertEquals(model.getVectorList().toArray().length, 1);
        Assert.assertFalse(model.isUndoStackEmpty());
        Assert.assertFalse(model.isRedoStackEmpty());
        model.undo();
        Assert.assertEquals(model.getVectorList().toArray().length, 0);
        Assert.assertTrue(model.isUndoStackEmpty());
        Assert.assertFalse(model.isRedoStackEmpty());
        model.addVector(vector2);
        Assert.assertFalse(model.isUndoStackEmpty());
        Assert.assertTrue(model.isRedoStackEmpty());
        model.clear();
        Assert.assertTrue(model.getVectorList().isEmpty());
    }

    /**
     * The method to test if the save and reload can work.
     */
    @Test
    public void testSaveAndReload() {
        model = new Model();
        model.addVector(vector1);
        model.addVector(vector2);
        SaveAsFile saved = model.save();
        model.reload(saved);
        Assert.assertEquals(model.getVectorList(), saved.getVectorList());
        Assert.assertEquals(model.getRedoStack(), saved.getRedoStack());
        Assert.assertEquals(model.getUndoStack(), saved.getUndoStack());
    }
}
