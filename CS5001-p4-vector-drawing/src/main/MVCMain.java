package main;

import controller.Controller;
import model.Model;
import view.guiview.VectorDrawingGUIView;

/**
 * A class to run this vector drawing tool.
 *
 * @author 210016568
 */
public class MVCMain {
    /**
     * The main method.
     */
    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        new VectorDrawingGUIView(model, controller);
    }
}
