import common.GLDisplay;

/**
 * Runs Renderer2, Final Demo
 */
public class Demo2 {
    public static void main(String[] args) {
        GLDisplay neheGLDisplay = GLDisplay.createGLDisplay("Render 2");
        Renderer2 renderer = new Renderer2();
        InputHandler2 inputHandler = new InputHandler2(renderer, neheGLDisplay);
        neheGLDisplay.addGLEventListener(renderer);
        neheGLDisplay.addKeyListener(inputHandler);
        neheGLDisplay.addMouseMotionListener(inputHandler);
        neheGLDisplay.start();
    }
}
