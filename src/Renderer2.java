
/*
 * Lesson07.java
 *
 * Created on July 25, 2003, 12:33 PM
 */

import common.TextureReader;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/** Port of the NeHe OpenGL Tutorial (Lesson 7)
 * to Java using the Jogl interface to OpenGL.  Jogl can be obtained
 * at http://jogl.dev.java.net/
 *
 * @author Kevin Duling (jattier@hotmail.com)
 */
class Renderer2 implements GLEventListener {
	
	private static Building[] buildings = new Building[10];
	private Building building = GenerateBuilding.generateBuilding(BuildingType.HighRise);
	
	private ArrayList<Building> cityBlocks[][] = new ArrayList[10][10];
	
	private HashMap<Building, Location>[][] buildingLocations = new HashMap[10][10];
	
    private boolean lightingEnabled;				// Lighting ON/OFF

    private int filter;				                // Which texture to use
    private int[] textures = new int[12];			// Storage For 3 Textures

    private float xrot = 13.0f;				// X Rotation
    private float yrot = 131.0f;				// Y Rotation

    private float xspeed = 0.5f;				// X Rotation Speed
    private boolean increaseX;
    private boolean decreaseX;

    private float yspeed = 0.3f;				// Y Rotation Speed
    private boolean increaseY;
    private boolean decreaseY;

    private float z = -15.0f;			// Depth Into The Screen
    private boolean zoomIn;
    private boolean zoomOut;
    
    private float x = -5.0f;            //Left right
    private boolean right;
    private boolean left;
    
    private float y = -9.0f;            //up down
    private boolean up;
    private boolean down;

    private float[] lightAmbient = {0.5f, 0.5f, 0.5f, 1.0f};
    private float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};
    private float[] lightPosition = {0.0f, 0.0f, 2.0f, 1.0f};

    private GLU glu = new GLU();

    public void toggleLighting() {
        lightingEnabled = !lightingEnabled;
    }

    public void increaseXspeed(boolean increaseX) {
        this.increaseX = increaseX;
    }

    public void decreaseXspeed(boolean decreaseX) {
        this.decreaseX = decreaseX;
    }

    public void increaseYspeed(boolean increaseY) {
        this.increaseY = increaseY;
    }

    public void decreaseYspeed(boolean decreaseY) {
        this.decreaseY = decreaseY;
    }

    public void zoomIn(boolean zoomIn) {
        this.zoomIn = zoomIn;
    }

    public void zoomOut(boolean zoomOut) {
        this.zoomOut = zoomOut;
    }
    
    public void right(boolean right) {
        this.right = right;
    }

    public void left(boolean left) {
        this.left = left;
    }
    
    public void up(boolean up) {
        this.up = up;
    }

    public void down(boolean down) {
        this.down = down;
    }

    public void switchFilter() {
        filter = (filter + 1) % 3;
    }
    
    public void setRotX(float rot){
    	xrot = rot;
    }
    
    public void setRotY(float rot){
    	yrot = rot;
    }
    
    public float getRotX(){
    	return xrot;
    }
    public float getRotY(){
    	return yrot;
    }

    private void update() {
        if (increaseX)
            xrot += 0.5f;
        if (decreaseX)
            xrot -= 0.5f;
        if (increaseY)
            yrot += 0.5f;
        if (decreaseY)
            yrot -= 0.5f;
        if (zoomIn)
            z += 0.1f;
        if (zoomOut)
            z -= 0.1f;
        if (right)
            x -= 0.1f;
        if (left)
            x += 0.1f;
        if (up)
            y -= 0.1f;
        if (down)
            y += 0.1f;
    }

    /** Called by the drawable to initiate OpenGL rendering by the client.
     * After all GLEventListeners have been notified of a display event, the
     * drawable will swap its buffers if necessary.
     * @param gLDrawable The GLAutoDrawable object.
     */
    public void display(GLAutoDrawable gLDrawable) {
        update();

        final GL gl = gLDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();									// Reset The View
        gl.glTranslatef(this.x, this.y, this.z);

        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);

        //gl.glBindTexture(GL.GL_TEXTURE_2D, textures[3]);
        
        if (lightingEnabled)
            gl.glEnable(GL.GL_LIGHTING);
        else
            gl.glDisable(GL.GL_LIGHTING);

		   
        for(int i = 0; i < 10; i++){
        	for(int j = 0; j < 10; j++){
        		gl.glPushMatrix();
        		gl.glTranslatef((float)i*4, 0.0f, 0.0f);
        		gl.glTranslatef(0.0f, 0.0f, (float)j*4);
        		
     	       for(Building building: cityBlocks[i][j]){	
    	    	   gl.glPushMatrix();
    	    	   gl.glTranslatef(buildingLocations[i][j].get(building).getX()/20, 0.0f, buildingLocations[i][j].get(building).getY()/20);
    	    	   gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0 + filter]);
    	    	   gl.glBegin(GL.GL_QUADS);
    	    	   
    	    	 //front plane
    		        gl.glNormal3f(0.0f, 0.0f, 1.0f);
    			    gl.glTexCoord2f(0.0f, 0.0f);
    				gl.glVertex3f(-(((float)building.getMaxW()/2)/10), 0.0f, (((float)building.getMaxS()/2)/10));
    				gl.glTexCoord2f(1.0f, 0.0f);
    				gl.glVertex3f((((float)building.getMaxE()/2)/10), 0.0f, (((float)building.getMaxS()/2)/10));
    				gl.glTexCoord2f(1.0f, 1.0f);
    				gl.glVertex3f((((float)building.getMaxE()/2)/10), Building.baseHeight, (((float)building.getMaxS()/2)/10));
    				gl.glTexCoord2f(0.0f, 1.0f);
    				gl.glVertex3f(-(((float)building.getMaxW()/2)/10), Building.baseHeight, (((float)building.getMaxS()/2)/10));
    			   
    				
    				//top plane
    				gl.glNormal3f(0.0f, 1.0f, 0.0f);
    				gl.glTexCoord2f(0.0f, 1.0f);
    				gl.glVertex3f(-(((float)building.getMaxW()/2)/10), Building.baseHeight, (((float)building.getMaxS()/2)/10));
    				gl.glTexCoord2f(0.0f, 0.0f);
    				gl.glVertex3f((((float)building.getMaxE()/2)/10), Building.baseHeight, (((float)building.getMaxS()/2)/10));
    				gl.glTexCoord2f(1.0f, 0.0f);
    				gl.glVertex3f((((float)building.getMaxE()/2)/10), Building.baseHeight, -(((float)building.getMaxN()/2)/10));
    				gl.glTexCoord2f(1.0f, 1.0f);
    				gl.glVertex3f(-(((float)building.getMaxW()/2)/10), Building.baseHeight, -(((float)building.getMaxN()/2)/10));
    				
    				//back plane
    				gl.glNormal3f(0.0f, 0.0f, -1.0f);
    				gl.glTexCoord2f(1.0f, 0.0f);
    				gl.glVertex3f((((float)building.getMaxE()/2)/10), 0.0f, -(((float)building.getMaxN()/2)/10));
    				gl.glTexCoord2f(1.0f, 1.0f);
    				gl.glVertex3f(-(((float)building.getMaxW()/2)/10), 0.0f, -(((float)building.getMaxN()/2)/10));
    				gl.glTexCoord2f(0.0f, 1.0f);
    				gl.glVertex3f(-(((float)building.getMaxW()/2)/10), Building.baseHeight, -(((float)building.getMaxN()/2)/10));
    				gl.glTexCoord2f(0.0f, 0.0f);
    				gl.glVertex3f((((float)building.getMaxE()/2)/10), Building.baseHeight, -(((float)building.getMaxN()/2)/10));
    				
    				//right plane
    				gl.glNormal3f(1.0f, 0.0f, 0.0f);
    				gl.glTexCoord2f(1.0f, 0.0f);
    				gl.glVertex3f((((float)building.getMaxE()/2)/10), Building.baseHeight, (((float)building.getMaxS()/2)/10));
    				gl.glTexCoord2f(1.0f, 1.0f);
    				gl.glVertex3f((((float)building.getMaxE()/2)/10), 0.0f, (((float)building.getMaxS()/2)/10));
    				gl.glTexCoord2f(0.0f, 1.0f);
    				gl.glVertex3f((((float)building.getMaxE()/2)/10), 0.0f, -(((float)building.getMaxN()/2)/10));
    				gl.glTexCoord2f(0.0f, 0.0f);
    				gl.glVertex3f((((float)building.getMaxE()/2)/10), Building.baseHeight, -(((float)building.getMaxN()/2)/10));
    				
    				//left plane
    				
    				gl.glNormal3f(-1.0f, 0.0f, 0.0f);
    				gl.glTexCoord2f(0.0f, 0.0f);
    				gl.glVertex3f(-(((float)building.getMaxW()/2)/10), 0.0f, (((float)building.getMaxS()/2)/10));
    				gl.glTexCoord2f(1.0f, 0.0f);
    				gl.glVertex3f(-(((float)building.getMaxW()/2)/10), Building.baseHeight, (((float)building.getMaxS()/2)/10));
    				gl.glTexCoord2f(1.0f, 1.0f);
    				gl.glVertex3f(-(((float)building.getMaxW()/2)/10), Building.baseHeight, -(((float)building.getMaxN()/2)/10));
    				gl.glTexCoord2f(0.0f, 1.0f);
    				gl.glVertex3f(-(((float)building.getMaxW()/2)/10), 0.0f, -(((float)building.getMaxN()/2)/10));
    				
    				
    				//bottom plane
    				gl.glNormal3f(0.0f, -1.0f, 0.0f);
    				gl.glTexCoord2f(1.0f, 1.0f);
    				gl.glVertex3f(-(((float)building.getMaxW()/2)/10), 0.0f, (((float)building.getMaxS()/2)/10));
    				gl.glTexCoord2f(0.0f, 1.0f);
    				gl.glVertex3f(-(((float)building.getMaxW()/2)/10), 0.0f, -(((float)building.getMaxN()/2)/10));
    				gl.glTexCoord2f(0.0f, 0.0f);
    				gl.glVertex3f((((float)building.getMaxE()/2)/10), 0.0f, -(((float)building.getMaxN()/2)/10));
    				gl.glTexCoord2f(1.0f, 0.0f);
    				gl.glVertex3f((((float)building.getMaxE()/2)/10), 0.0f, (((float)building.getMaxS()/2)/10));
    				
    				gl.glEnd();
    			
    				//draw building blocks
    				
    				gl.glBindTexture(GL.GL_TEXTURE_2D, textures[3 + filter]);

    			
    				for(Block b: building.getBlocks()){
    					gl.glBegin(GL.GL_QUADS);
    					//front plane
    					gl.glNormal3f(0.0f, 0.0f, 1.0f);
    					gl.glTexCoord2f(0.0f, 0.0f);
    					gl.glVertex3f(-(((float)b.getW()/2)/10), 0.001f, (((float)b.getS()/2)/10));
    					gl.glTexCoord2f(1.0f, 0.0f);
    					gl.glVertex3f((((float)b.getE()/2)/10), 0.001f, (((float)b.getS()/2)/10));
    					gl.glTexCoord2f(1.0f, 1.0f);
    					gl.glVertex3f((((float)b.getE()/2)/10), ((float)b.getHeight()/50), (((float)b.getS()/2)/10));
    					gl.glTexCoord2f(0.0f, 1.0f);
    					gl.glVertex3f(-(((float)b.getW()/2)/10), ((float)b.getHeight()/50), (((float)b.getS()/2)/10));
    					gl.glEnd();
    					
    					gl.glBegin(GL.GL_QUADS);
    					//back plane
    					gl.glNormal3f(0.0f, 0.0f, -1.0f);
    					gl.glTexCoord2f(1.0f, 0.0f);
    					gl.glVertex3f((((float)b.getE()/2)/10), 0.001f, -(((float)b.getN()/2)/10));
    					gl.glTexCoord2f(1.0f, 1.0f);
    					gl.glVertex3f(-(((float)b.getW()/2)/10), 0.001f, -(((float)b.getN()/2)/10));
    					gl.glTexCoord2f(0.0f, 1.0f);
    					gl.glVertex3f(-(((float)b.getW()/2)/10), ((float)b.getHeight()/50), -(((float)b.getN()/2)/10));
    					gl.glTexCoord2f(0.0f, 0.0f);
    					gl.glVertex3f((((float)b.getE()/2)/10), ((float)b.getHeight()/50), -(((float)b.getN()/2)/10));
    					
    					gl.glEnd();
    					
    					gl.glBegin(GL.GL_QUADS);
    					
    					//top plane

    					gl.glNormal3f(0.0f, 1.0f, 0.0f);
    					gl.glTexCoord2f(0.0f, 0.0f);
    					gl.glVertex3f(-(((float)b.getW()/2)/10), ((float)b.getHeight()/50), (((float)b.getS()/2)/10));
    					gl.glTexCoord2f(1.0f, 0.0f);
    					gl.glVertex3f((((float)b.getE()/2)/10), ((float)b.getHeight()/50), (((float)b.getS()/2)/10));
    					gl.glTexCoord2f(1.0f, 1.0f);
    					gl.glVertex3f((((float)b.getE()/2)/10), ((float)b.getHeight()/50), -(((float)b.getN()/2)/10));
    					gl.glTexCoord2f(0.0f, 1.0f);
    					gl.glVertex3f(-(((float)b.getW()/2)/10), ((float)b.getHeight()/50), -(((float)b.getN()/2)/10));
    					
    					gl.glEnd();
    					
    					gl.glBegin(GL.GL_QUADS);
    					
    					//right plane
    					gl.glNormal3f(1.0f, 0.0f, 0.0f);
    					gl.glTexCoord2f(1.0f, 0.0f);
    					gl.glVertex3f((((float)b.getE()/2)/10), ((float)b.getHeight()/50), (((float)b.getS()/2)/10));
    					gl.glTexCoord2f(1.0f, 1.0f);
    					gl.glVertex3f((((float)b.getE()/2)/10), 0.001f, (((float)b.getS()/2)/10));
    					gl.glTexCoord2f(0.0f, 1.0f);
    					gl.glVertex3f((((float)b.getE()/2)/10), 0.001f, -(((float)b.getN()/2)/10));
    					gl.glTexCoord2f(0.0f, 0.0f);
    					gl.glVertex3f((((float)b.getE()/2)/10), ((float)b.getHeight()/50), -(((float)b.getN()/2)/10));
    					
    					
    					gl.glEnd();
    					
    					gl.glBegin(GL.GL_QUADS);
    					
    					//left plane
    					gl.glNormal3f(-1.0f, 0.0f, 0.0f);
    					gl.glTexCoord2f(0.0f, 0.0f);
    					gl.glVertex3f(-(((float)b.getW()/2)/10), 0.001f, (((float)b.getS()/2)/10));
    					gl.glTexCoord2f(1.0f, 0.0f);
    					gl.glVertex3f(-(((float)b.getW()/2)/10), ((float)b.getHeight()/50), (((float)b.getS()/2)/10));
    					gl.glTexCoord2f(1.0f, 1.0f);
    					gl.glVertex3f(-(((float)b.getW()/2)/10), ((float)b.getHeight()/50), -(((float)b.getN()/2)/10));
    					gl.glTexCoord2f(0.0f, 1.0f);
    					gl.glVertex3f(-(((float)b.getW()/2)/10), 0.001f, -(((float)b.getN()/2)/10));
    					
    					
    					gl.glEnd();
    					
    					gl.glBegin(GL.GL_QUADS);
    					
    					//bottom plane
    					gl.glNormal3f(0.0f, -1.0f, 0.0f);
    					gl.glTexCoord2f(1.0f, 1.0f);
    					gl.glVertex3f(-(((float)b.getW()/2)/10), 0.001f, (((float)b.getS()/2)/10));
    					gl.glTexCoord2f(0.0f, 1.0f);
    					gl.glVertex3f(-(((float)b.getW()/2)/10), 0.001f, -(((float)b.getN()/2)/10));
    					gl.glTexCoord2f(0.0f, 0.0f);
    					gl.glVertex3f((((float)b.getE()/2)/10), 0.001f, -(((float)b.getN()/2)/10));
    					gl.glTexCoord2f(1.0f, 0.0f);
    					gl.glVertex3f((((float)b.getE()/2)/10), 0.001f, (((float)b.getS()/2)/10));
    					
    					gl.glEnd();
    				}
    				gl.glPopMatrix();
    	       }
     	      gl.glPopMatrix();
        	}
        }
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[6 + filter]);
        
        gl.glBegin(GL.GL_QUADS);
        
        gl.glNormal3f(0.0f, 1.0f, 0.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-20, 0.0f, 70);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-20, 0.0f, -10);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(70, 0.0f, -10);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(70, 0.0f, 70);
		gl.glEnd();

		gl.glBindTexture(GL.GL_TEXTURE_2D, textures[9 + filter]);
		//background
		gl.glBegin(GL.GL_QUADS);
        
        gl.glNormal3f(0.0f, 1.0f, 0.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-20, 0.0f, 50);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(200, 0.0f, 20);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(200, 50.0f, 20);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-20, 50.0f, 50);
        
        gl.glNormal3f(0.0f, 1.0f, 0.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(50, 0.0f, -20);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(50, 0.0f, 50);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(50, 50.0f, 50);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(50, 50.0f, -20);
		gl.glEnd();
		
    }

    /** Called when the display mode has been changed.  <B>!! CURRENTLY UNIMPLEMENTED IN JOGL !!</B>
     * @param gLDrawable The GLAutoDrawable object.
     * @param modeChanged Indicates if the video mode has changed.
     * @param deviceChanged Indicates if the video device has changed.
     */
    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
    }

    /** Called by the drawable immediately after the OpenGL context is
     * initialized for the first time. Can be used to perform one-time OpenGL
     * initialization such as setup of lights and display lists.
     * @param gLDrawable The GLAutoDrawable object.
     */
    public void init(GLAutoDrawable gLDrawable) {
        GL gl = gLDrawable.getGL();

        gl.glShadeModel(GL.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL.GL_DEPTH_TEST);							// Enables Depth Testing
        gl.glDepthFunc(GL.GL_LEQUAL);								// The Type Of Depth Testing To Do
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);	// Really Nice Perspective Calculations
        gl.glEnable(GL.GL_TEXTURE_2D);

        TextureReader.Texture texturebase = null;
        TextureReader.Texture granitebuilding = null;
        
        //plain scene
        TextureReader.Texture gray = null;
        TextureReader.Texture basegray = null;
        TextureReader.Texture floorgray = null;
        TextureReader.Texture bluesky = null;
        
        TextureReader.Texture mariosky = null;
        TextureReader.Texture marioground = null;
        TextureReader.Texture mariobuilding = null;
        TextureReader.Texture mariobase = null;
        
        try {

            //load images in plain view
            gray = TextureReader.readTexture("data/images/gray.png");
            basegray = TextureReader.readTexture("data/images/basegray.png");
            floorgray = TextureReader.readTexture("data/images/floorgray.png");
            bluesky = TextureReader.readTexture("data/images/bluesky.png");
            
            //load images second display
            granitebuilding = TextureReader.readTexture("data/images/granite.png");
            texturebase = TextureReader.readTexture("data/images/bumps.png");
            
            //load images mario view
            mariosky = TextureReader.readTexture("data/images/Mario.png");
            marioground = TextureReader.readTexture("data/images/marioground.png");
            mariobuilding = TextureReader.readTexture("data/images/grayBrick.png");
            mariobase = TextureReader.readTexture("data/images/mariobrick.png");
            
            
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        gl.glGenTextures(12, this.textures, 0);

        GenerateCityBlock cityBlockz[][] = new GenerateCityBlock[10][10];
        
        BuildingType type;
        Random rand = new Random();
        
        float newX;
        float newY;
        
        for(int y = 0; y < 10; y++){
        	for(int x = 0; x < 10; x++){
        		float random = rand.nextFloat();
        		newX = (float) x * (random+0.5f);
        		newY = (float) y * (random+0.5f);
        		if(newY < 2 || newX < 2){
        			type = BuildingType.LowRise;
        		}
        		else if(newY > 6 && newX > 6){
        			type = BuildingType.HighRise;
        		}
        		else{
        			type = BuildingType.MediumRise;
        		}
        		
        		cityBlockz[y][x] = new GenerateCityBlock(type, 50, 50);
        		System.out.println(x + " " + y);
        	}
        }
        
            for(int i = 0; i < 10; i++){
            	for(int j = 0; j < 10; j++){
            		
            	cityBlocks[i][j] = new ArrayList();
        		//GenerateCityBlock cityBlock = new GenerateCityBlock(BuildingType.HighRise, 50, 50);
        		//GenerateCityBlock x = new GenerateCityBlock(BuildingType.HighRise, 50, 50);
        		
        		ArrayList<ArrayList<Building>> buildings = cityBlockz[i][j].getBuildings();
        		
        		for(ArrayList<Building> aB: buildings){
        			for(Building b: aB){
        				cityBlocks[i][j].add(b);
        			}
        		}

        		buildingLocations[i][j] = cityBlockz[i][j].getLocations();
        	}
        }
        //ArrayList<Location> l = new ArrayList<Location>();
        
        

        //bases 0 -> 2
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
        makeRGBTexture(gl, glu, basegray, GL.GL_TEXTURE_2D, false);

        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[1]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        makeRGBTexture(gl, glu, texturebase, GL.GL_TEXTURE_2D, false);

        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[2]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        makeRGBTexture(gl, glu, mariobase, GL.GL_TEXTURE_2D, true);
        
        //buildings 3 -> 5
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[3]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
        makeRGBTexture(gl, glu, gray, GL.GL_TEXTURE_2D, false);
        
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[4]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        makeRGBTexture(gl, glu, granitebuilding, GL.GL_TEXTURE_2D, false);
        
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[5]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        makeRGBTexture(gl, glu, mariobuilding, GL.GL_TEXTURE_2D, true);

        //floors 6 -> 8
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[6]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        makeRGBTexture(gl, glu, floorgray, GL.GL_TEXTURE_2D, true);
        
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[7]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        makeRGBTexture(gl, glu, floorgray, GL.GL_TEXTURE_2D, true);
        
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[8]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        makeRGBTexture(gl, glu, marioground, GL.GL_TEXTURE_2D, true);
        
        //skys 9 -> 11
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[9]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        makeRGBTexture(gl, glu, bluesky, GL.GL_TEXTURE_2D, true);
        
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[10]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        makeRGBTexture(gl, glu, bluesky, GL.GL_TEXTURE_2D, true);
        
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[11]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        makeRGBTexture(gl, glu, mariosky, GL.GL_TEXTURE_2D, true);
        
        // Set up lighting
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, this.lightAmbient, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, this.lightDiffuse, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, this.lightPosition, 0);
        gl.glEnable(GL.GL_LIGHT1);
        gl.glEnable(GL.GL_LIGHTING);
        this.lightingEnabled = true;
    }

    /** Called by the drawable during the first repaint after the component has
     * been resized. The client can update the viewport and view volume of the
     * window appropriately, for example by a call to
     * GL.glViewport(int, int, int, int); note that for convenience the component
     * has already called GL.glViewport(int, int, int, int)(x, y, width, height)
     * when this method is called, so the client may not have to do anything in
     * this method.
     * @param gLDrawable The GLAutoDrawable object.
     * @param x The X Coordinate of the viewport rectangle.
     * @param y The Y coordinate of the viewport rectanble.
     * @param width The new width of the window.
     * @param height The new height of the window.
     */
    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
        final GL gl = gLDrawable.getGL();

        if (height <= 0) // avoid a divide by zero error!
            height = 1;
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 100.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private void makeRGBTexture(GL gl, GLU glu, TextureReader.Texture img, int target, boolean mipmapped) {
        if (mipmapped) {
            glu.gluBuild2DMipmaps(target, GL.GL_RGB8, img.getWidth(), img.getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        } else {
            gl.glTexImage2D(target, 0, GL.GL_RGB, img.getWidth(), img.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        }
    }
}