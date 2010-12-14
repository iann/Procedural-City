import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.ByteBuffer;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.sun.opengl.util.GLUT;


public class DisplayBuilding extends JFrame implements GLEventListener , KeyListener, MouseListener, MouseMotionListener, ActionListener{

	private Building building = GenerateBuilding.generateBuilding(BuildingType.HighRise);
	private Building building2 = GenerateBuilding.generateBuilding(BuildingType.HighRise);
	private Random rand = new Random();
	
	// mouse control variables
	private final GLCanvas canvas;
	private int winW = 800, winH = 800;
	private int mouseX, mouseY;
	private int mouseButton;
	private boolean mouseClick = false;
	private boolean clickedOnShape = false;

	// gl shading/transformation variables
	private float tx = 0.0f, ty = -2.0f;
	private float scale = 0.5f;
	private float angle = 0.0f;
	private float angleX = 0.0f;
	private boolean drawWireframe = false;
	private float lightPos[] = { -5.0f, 10.0f, 5.0f, 1.0f };
	

	// gl context/variables
	private GL gl;
	private final GLU glu = new GLU();
	private final GLUT glut = new GLUT();

	public static void main(String args[]) {
		new DisplayBuilding();
	}

	// constructor
	public DisplayBuilding() {
		super("Display Building");
		canvas = new GLCanvas();
		canvas.addGLEventListener(this);
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		getContentPane().add(canvas);
		setSize(winW, winH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		canvas.requestFocus();
	}
	
	// gl display function
	public void display(GLAutoDrawable drawable) {
		
		// if mouse is clicked, we need to detect whether it's clicked on the shape
		if (mouseClick) {
			ByteBuffer pixel = ByteBuffer.allocateDirect(1);

			gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
			gl.glColor3f(1.0f, 1.0f, 1.0f);
			gl.glDisable( GL.GL_LIGHTING );
			drawShape();
			gl.glReadPixels(mouseX, (winH-1-mouseY), 1, 1, GL.GL_RED, GL.GL_UNSIGNED_BYTE, pixel);
			
			if (pixel.get(0) == (byte)255 || true) {
				// mouse clicked on the shape, set clickedOnShape to true
				clickedOnShape = true;
			}
			// set mouseClick to false to avoid detecting again
			mouseClick = false;
		}

		// shade the current shape
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		//gl.glEnable(GL.GL_LIGHTING);
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, drawWireframe ? GL.GL_LINE : GL.GL_FILL);
		gl.glColor3f(1.0f, 0.3f, 0.1f);
		drawShape();
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
		// TODO Auto-generated method stub
	}

	// draw the current shape
	public void drawShape() {
		gl.glLoadIdentity();
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPos, 0);
		gl.glTranslatef(tx, ty, -10.0f);
		gl.glScalef(scale, scale, scale);
		gl.glRotatef(angle, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(angleX, 0.0f, 1.0f, 0.0f);
		
		
		//draw building base
		//front plane

	   
		   gl.glBegin(GL.GL_QUADS);
		   
		   gl.glColor3f(0.0f, 0.0f, 1.0f);
		   
		   //front plane
			gl.glVertex3f(-(((float)building.getMaxW()/2)/10), 0.0f, (((float)building.getMaxS()/2)/10));
			gl.glVertex3f((((float)building.getMaxE()/2)/10), 0.0f, (((float)building.getMaxS()/2)/10));
			gl.glVertex3f((((float)building.getMaxE()/2)/10), Building.baseHeight, (((float)building.getMaxS()/2)/10));
			gl.glVertex3f(-(((float)building.getMaxW()/2)/10), Building.baseHeight, (((float)building.getMaxS()/2)/10));	
		   
			//top plane
			gl.glVertex3f(-(((float)building.getMaxW()/2)/10), Building.baseHeight, (((float)building.getMaxS()/2)/10));
			gl.glVertex3f((((float)building.getMaxE()/2)/10), Building.baseHeight, (((float)building.getMaxS()/2)/10));
			gl.glVertex3f((((float)building.getMaxE()/2)/10), Building.baseHeight, -(((float)building.getMaxN()/2)/10));
			gl.glVertex3f(-(((float)building.getMaxW()/2)/10), Building.baseHeight, -(((float)building.getMaxN()/2)/10));
					
			//back plane
			gl.glVertex3f((((float)building.getMaxE()/2)/10), 0.0f, -(((float)building.getMaxN()/2)/10));
			gl.glVertex3f(-(((float)building.getMaxW()/2)/10), 0.0f, -(((float)building.getMaxN()/2)/10));
			gl.glVertex3f(-(((float)building.getMaxW()/2)/10), Building.baseHeight, -(((float)building.getMaxN()/2)/10));
			gl.glVertex3f((((float)building.getMaxE()/2)/10), Building.baseHeight, -(((float)building.getMaxN()/2)/10));
			
			//right plane
			gl.glVertex3f((((float)building.getMaxE()/2)/10), Building.baseHeight, (((float)building.getMaxS()/2)/10));
			gl.glVertex3f((((float)building.getMaxE()/2)/10), 0.0f, (((float)building.getMaxS()/2)/10));
			gl.glVertex3f((((float)building.getMaxE()/2)/10), 0.0f, -(((float)building.getMaxN()/2)/10));
			gl.glVertex3f((((float)building.getMaxE()/2)/10), Building.baseHeight, -(((float)building.getMaxN()/2)/10));
			
			//left plane
			gl.glVertex3f(-(((float)building.getMaxW()/2)/10), 0.0f, (((float)building.getMaxS()/2)/10));
			gl.glVertex3f(-(((float)building.getMaxW()/2)/10), Building.baseHeight, (((float)building.getMaxS()/2)/10));
			gl.glVertex3f(-(((float)building.getMaxW()/2)/10), Building.baseHeight, -(((float)building.getMaxN()/2)/10));
			gl.glVertex3f(-(((float)building.getMaxW()/2)/10), 0.0f, -(((float)building.getMaxN()/2)/10));
			
			//bottom plane
		  
			gl.glVertex3f(-(((float)building.getMaxW()/2)/10), 0.0f, (((float)building.getMaxS()/2)/10));
			gl.glVertex3f(-(((float)building.getMaxW()/2)/10), 0.0f, -(((float)building.getMaxN()/2)/10));
			gl.glVertex3f((((float)building.getMaxE()/2)/10), 0.0f, -(((float)building.getMaxN()/2)/10));
			gl.glVertex3f((((float)building.getMaxE()/2)/10), 0.0f, (((float)building.getMaxS()/2)/10));

			gl.glColor3f(0.3f, 1.0f, 0.1f);
		
			//draw building blocks
		
			for(Block b: building.getBlocks()){
				//front plane
				gl.glVertex3f(-(((float)b.getW()/2)/10), 0.0f, (((float)b.getS()/2)/10));
				gl.glVertex3f((((float)b.getE()/2)/10), 0.0f, (((float)b.getS()/2)/10));
				gl.glVertex3f((((float)b.getE()/2)/10), ((float)b.getHeight()/50), (((float)b.getS()/2)/10));
				gl.glVertex3f(-(((float)b.getW()/2)/10), ((float)b.getHeight()/50), (((float)b.getS()/2)/10));
				
				//top plane
				gl.glVertex3f(-(((float)b.getW()/2)/10), ((float)b.getHeight()/50), (((float)b.getS()/2)/10));
				gl.glVertex3f((((float)b.getE()/2)/10), ((float)b.getHeight()/50), (((float)b.getS()/2)/10));
				gl.glVertex3f((((float)b.getE()/2)/10), ((float)b.getHeight()/50), -(((float)b.getN()/2)/10));
				gl.glVertex3f(-(((float)b.getW()/2)/10), ((float)b.getHeight()/50), -(((float)b.getN()/2)/10));
				
				//back plane
				gl.glVertex3f((((float)b.getE()/2)/10), 0.0f, -(((float)b.getN()/2)/10));
				gl.glVertex3f(-(((float)b.getW()/2)/10), 0.0f, -(((float)b.getN()/2)/10));
				gl.glVertex3f(-(((float)b.getW()/2)/10), ((float)b.getHeight()/50), -(((float)b.getN()/2)/10));
				gl.glVertex3f((((float)b.getE()/2)/10), ((float)b.getHeight()/50), -(((float)b.getN()/2)/10));
				
				//right plane
				gl.glVertex3f((((float)b.getE()/2)/10), ((float)b.getHeight()/50), (((float)b.getS()/2)/10));
				gl.glVertex3f((((float)b.getE()/2)/10), 0.0f, (((float)b.getS()/2)/10));
				gl.glVertex3f((((float)b.getE()/2)/10), 0.0f, -(((float)b.getN()/2)/10));
				gl.glVertex3f((((float)b.getE()/2)/10), ((float)b.getHeight()/50), -(((float)b.getN()/2)/10));
				
				//left plane
				gl.glVertex3f(-(((float)b.getW()/2)/10), 0.0f, (((float)b.getS()/2)/10));
				gl.glVertex3f(-(((float)b.getW()/2)/10), ((float)b.getHeight()/50), (((float)b.getS()/2)/10));
				gl.glVertex3f(-(((float)b.getW()/2)/10), ((float)b.getHeight()/50), -(((float)b.getN()/2)/10));
				gl.glVertex3f(-(((float)b.getW()/2)/10), 0.0f, -(((float)b.getN()/2)/10));
				
				//bottom plane
				gl.glVertex3f(-(((float)b.getW()/2)/10), 0.0f, (((float)b.getS()/2)/10));
				gl.glVertex3f(-(((float)b.getW()/2)/10), 0.0f, -(((float)b.getN()/2)/10));
				gl.glVertex3f((((float)b.getE()/2)/10), 0.0f, -(((float)b.getN()/2)/10));
				gl.glVertex3f((((float)b.getE()/2)/10), 0.0f, (((float)b.getS()/2)/10));

			}
			
			gl.glEnd();
		
	}

	// initialization
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		gl = drawable.getGL();
    		gl.setSwapInterval(1);

		gl.glColorMaterial(GL.GL_FRONT, GL.GL_DIFFUSE);
		gl.glEnable( GL.GL_COLOR_MATERIAL ) ;
		gl.glEnable(GL.GL_LIGHT0);
		gl.glEnable(GL.GL_NORMALIZE);
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glCullFace(GL.GL_BACK);
		gl.glEnable(GL.GL_CULL_FACE);
		
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); 
		gl.glEnable(GL.GL_TEXTURE_2D);
	//texture = genTexture(gl);
		

		// set clear color: this determines the background color (which is dark gray)
		gl.glClearColor(.3f, .3f, .3f, 1f);
		gl.glClearDepth(1.0f);
	}

	// reshape callback function: called when the size of the window changes
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		winW = width;
		winH = height;

		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(30.0f, (float) width / (float) height, 0.01f, 100.0f);
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL.GL_MODELVIEW);		
	}

	// mouse pressed even callback function
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseClick = true;
		mouseX = e.getX();
		mouseY = e.getY();
		mouseButton = e.getButton();
		canvas.display();
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		clickedOnShape = false;
		canvas.display();
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!clickedOnShape)	return;

		int x = e.getX();
		int y = e.getY();
		if (mouseButton == MouseEvent.BUTTON3) {
			// right button scales
			scale += (y - mouseY) * 0.01f;
		}
		else if (mouseButton == MouseEvent.BUTTON2) {
			// middle button translates
			tx += (x - mouseX) * 0.01f;
			ty -= (y - mouseY) * 0.01f;
		}
		else if (mouseButton == MouseEvent.BUTTON1) {
			// left button rotates
			angle += (y - mouseY);
			angleX += (x - mouseX);
		}
		mouseX = x;
		mouseY = y;
		canvas.display();
		
	}
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		case KeyEvent.VK_W:
			drawWireframe = !drawWireframe;
			break;
		case KeyEvent.VK_SPACE:
			int random = rand.nextInt(3);
			switch(random){
				case 0: building = GenerateBuilding.generateBuilding(BuildingType.HighRise); break;
				case 1: building = GenerateBuilding.generateBuilding(BuildingType.MediumRise);break;
				case 2: building = GenerateBuilding.generateBuilding(BuildingType.LowRise);break;
			}
			break;
		}
		canvas.display();		
	}

	// these event functions are not used for this assignment
	// but may be useful in the future
	public void keyTyped(KeyEvent e) { }
	public void keyReleased(KeyEvent e) { }
	public void mouseMoved(MouseEvent e) { }
	public void actionPerformed(ActionEvent e) { }
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) {	}

}
