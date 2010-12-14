import common.GLDisplay;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

class InputHandler2 extends KeyAdapter implements MouseMotionListener{
    private Renderer2 renderer;
    private float mouseX = Integer.MIN_VALUE;
    private float mouseY = Integer.MIN_VALUE;

    public InputHandler2(Renderer2 renderer, GLDisplay glDisplay) {
        this.renderer = renderer;
        glDisplay.registerKeyStrokeForHelp(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0), "Toggle lighting");
        glDisplay.registerKeyStrokeForHelp(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "Switch texture filter");
        glDisplay.registerKeyStrokeForHelp(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "Zoom in");
        glDisplay.registerKeyStrokeForHelp(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "Zoom out");
        glDisplay.registerKeyStrokeForHelp(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "Right");
        glDisplay.registerKeyStrokeForHelp(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "Left");
        glDisplay.registerKeyStrokeForHelp(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "Up");
        glDisplay.registerKeyStrokeForHelp(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), "Down");
        glDisplay.registerKeyStrokeForHelp(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Rotate slower along X-axis");
        glDisplay.registerKeyStrokeForHelp(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "Rotate faster along X-axis");
        glDisplay.registerKeyStrokeForHelp(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Rotate slower along Y-axis");
        glDisplay.registerKeyStrokeForHelp(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Rotate faster along Y-axis");
    }

    public void keyPressed(KeyEvent e) {
        processKeyEvent(e, true);
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_L:
                renderer.toggleLighting();
                break;
            case KeyEvent.VK_F:
                renderer.switchFilter();
                break;
            default:
                processKeyEvent(e, false);
        }
    }

    private void processKeyEvent(KeyEvent e, boolean pressed) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                renderer.zoomIn(pressed);
                break;
            case KeyEvent.VK_S:
                renderer.zoomOut(pressed);
                break;
            case KeyEvent.VK_D:
                renderer.right(pressed);
                break;
            case KeyEvent.VK_A:
                renderer.left(pressed);
                break;
            case KeyEvent.VK_E:
                renderer.up(pressed);
                break;
            case KeyEvent.VK_Q:
                renderer.down(pressed);
                break;
            case KeyEvent.VK_UP:
                renderer.increaseXspeed(pressed);
                break;
            case KeyEvent.VK_DOWN:
                renderer.decreaseXspeed(pressed);
                break;
            case KeyEvent.VK_RIGHT:
                renderer.increaseYspeed(pressed);
                break;
            case KeyEvent.VK_LEFT:
                renderer.decreaseYspeed(pressed);
                break;
        }
    }

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		if(mouseX == Integer.MIN_VALUE || mouseY == Integer.MIN_VALUE){
			mouseX = x;
			mouseY = y;
			return;
		}
		float tempX = (x - mouseX)/10 + renderer.getRotY();
		float tempY = (y - mouseY)/10 + renderer.getRotX();
		renderer.setRotY(tempX);
		renderer.setRotX(tempY);
		mouseX = x;
		mouseY = y;
	}
}
