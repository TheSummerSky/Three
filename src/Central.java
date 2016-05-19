import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Central extends Canvas implements Runnable {

	private BufferedImage image;
	public final int WIDTH;
	public final int HEIGHT;
	private Camera camera;
	private ArrayList<Box> boxes;
	private Box boxe;
	private Mouse mouse;
	
	public Central(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		
		image = new BufferedImage(width, height,1);
		camera = new Camera(50,50,50);
		this.addKeyListener(camera);
		boxes = new ArrayList<Box>();
		boxe = new Box(50,50,0,10,10,10);
		boxes.add(boxe);
		boxes.add(new Box(30,30,20, 10,10,10));
		boxes.add(new Box(30, 0, 2, 2, 2, 2, Color.YELLOW));
		addBox();
		addBox();
		addBox();
		sort();
		mouse = new Mouse(WIDTH/2, HEIGHT/2);
		this.addMouseMotionListener(mouse);
	}

	
	
	
	public void fillImage(int rgbColor)
	{
		for(int i = 0; i < WIDTH; i++)
		{
			for(int j = 0; j< HEIGHT; j++)
			{
				//pixels[i][j] = rgbColor;
				image.setRGB(i,j, rgbColor);
				
			}
		}
		
	}
	
	
	public static int rgbToInt(int r, int g, int b){
		return (r<<16) + (g<<8) + b;
 		
	}
	
	public static double degreesToRadians(double degrees)
	{
		return degrees*2*Math.PI/360;
	}
	
	
	public void addBox(Box box)
	{
		boxes.add(box);
		sort();
	}
	
	public void addBox()
	{
		int x = (int)(Math.random() * 100);
		int y = (int)(Math.random() * 100);
		int z = (int)(Math.random() * 30);
		int w = (int)(Math.random() * 15)+5;
		int h = (int)(Math.random() * 15)+5;
		int d = (int)(Math.random() * 15)+5;
		addBox(new Box(x,y,z,w,h,d));
		
	}
	
	public void sort()
	{
		ArrayList<Box> sorted = new ArrayList<Box>();
		ArrayList<Box> unsorted = boxes;
		int max = boxes.size();
		for(int i = 0;i<max;i++)
		{
			Box newBox = findMinimum(unsorted);
			sorted.add(0, newBox);
			unsorted.remove(newBox);
			//System.out.println(sorted.size());
		}
		boxes = sorted;
		
	}
	
	public Box findMinimum(ArrayList<Box> array)
	{
		Box bestBox = array.get(0);
		for(Box box: array)
		{
			if (camera.distanceFrom(box)<camera.distanceFrom(bestBox)) bestBox = box;
		}
		return bestBox;
	}
	
	public void render()
	{
		
		fillImage(this.rgbToInt(0, 0, 0));
		
		//draw out calculations of converting points 3d into points 2d
		//then repeat process for each point of the square, then draw lines between each point
		
		Graphics2D graphics = image.createGraphics();
		sort();
		for(Box box:boxes)
		{
			drawTestBox(box, graphics);
			//drawBox(box, graphics);
		
		}
		
		graphics.dispose();
	}
	
	
	public void drawTestBox(Box box, Graphics2D graphics)
	{
		int deltax = box.getX() - camera.getX();
		int deltay = box.getY() - camera.getY();
		int deltaz = box.getZ() - camera.getZ();
		
		int x = (int)(WIDTH/2 + ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		if(deltaz<=0) x = (int)(WIDTH/2 - ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		int y = (int)(HEIGHT/2 + ((calculateY(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH); 
		//System.out.println(x + "    " + y);
		
		//System.out.println(y);
		
		
		deltax = box.getX() - camera.getX() + box.getWidth();
		
		deltay = box.getY() - camera.getY();
		deltaz = box.getZ() - camera.getZ();
		
		int xw =  (int)(WIDTH/2 + ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		if(deltaz<=0) xw = (int)(WIDTH/2 - ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		int yw =  (int)(HEIGHT/2 + ((calculateY(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		
		deltax = box.getX() - camera.getX();
		deltay = box.getY() - camera.getY() + box.getHeight();
		deltaz = box.getZ() - camera.getZ();
		
		int xh = (int)(WIDTH/2 + ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		if(deltaz<=0) xh = (int)(WIDTH/2 - ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		int yh = (int)(HEIGHT/2 + ((calculateY(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		
		deltax = box.getX() - camera.getX() + box.getWidth();
		deltay = box.getY() - camera.getY() + box.getHeight();
		deltaz = box.getZ() - camera.getZ();
		
		int xwh = (int)(WIDTH/2 + ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		if(deltaz<=0) xwh = (int)(WIDTH/2 - ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		int ywh = (int)(HEIGHT/2 + ((calculateY(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		
		deltax = box.getX() - camera.getX();
		deltay = box.getY() - camera.getY();
		deltaz = box.getZ() - camera.getZ() - box.getDepth();
		
		int xfront = (int)(WIDTH/2 + ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		if(deltaz<=0) xfront = (int)(WIDTH/2 - ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		int yfront = (int)(HEIGHT/2 + ((calculateY(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		
		deltax = box.getX() - camera.getX() + box.getWidth();
		deltay = box.getY() - camera.getY();
		deltaz = box.getZ() - camera.getZ() - box.getDepth();
		
		int xfrontw = (int)(WIDTH/2 + ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		if(deltaz<=0) xfrontw = (int)(WIDTH/2 - ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		int yfrontw = (int)(HEIGHT/2 + ((calculateY(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
	
		deltax = box.getX() - camera.getX() + box.getWidth();
		deltay = box.getY() - camera.getY() + box.getHeight();
		deltaz = box.getZ() - camera.getZ() - box.getDepth();
		
		int xfrontwh = (int)(WIDTH/2 + ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		if(deltaz<=0) xfrontwh = (int)(WIDTH/2 - ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		int yfrontwh = (int)(HEIGHT/2 + ((calculateY(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		
		deltax = box.getX() - camera.getX();
		deltay = box.getY() - camera.getY() + box.getHeight();
		deltaz = box.getZ() - camera.getZ() - box.getDepth();
		
		int xfronth =  (int)(WIDTH/2 + ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		if(deltaz<=0) xfronth = (int)(WIDTH/2 - ((calculateX(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		int yfronth =  (int)(HEIGHT/2 + ((calculateY(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())/camera.findVisionWidth((int)calculateDistance(deltax, deltay, deltaz, camera.getAngle(), camera.getTheta())))) * WIDTH);
		
		
		boolean topfirst = camera.getY()<box.getY();
		boolean rightfirst = camera.getX() > box.getX();
		boolean backfirst = camera.getZ() < box.getZ();
		
		
		
		if(!backfirst){
		int[] arrayx = {x,xw,xwh,xh};
		int[] arrayy = {y, yw,ywh,yh};
		Polygon front = new Polygon(arrayx,arrayy , 4);
		//graphics.setColor(new Color(120,120,120));
		graphics.setColor( new Color( (int)(box.getColor().getRed()*.47),(int)(box.getColor().getGreen()*.47), (int)(box.getColor().getBlue()*.47)));
		graphics.fill(front);
		}
		else
		{
			
			int[] frontx = {xfront, xfrontw, xfrontwh, xfronth};
			int[] fronty = {yfront, yfrontw, yfrontwh, yfronth};
			//graphics.setColor(new Color(120,120,120));
			graphics.setColor( new Color( (int)(box.getColor().getRed()*.47),(int)(box.getColor().getGreen()*.47), (int)(box.getColor().getBlue()*.47)));
			graphics.fill(new Polygon(frontx, fronty, 4));
		}
		
		
		if(topfirst)
		{
		int[] topx = {xfront, x, xw, xfrontw};
		int[] topy = {yfront, y, yw, yfrontw};
		//graphics.setColor(new Color(255,255,255));
		graphics.setColor(box.getColor());
		graphics.fill(new Polygon(topx, topy,4));
		}
		else
		{
			int[] bottomx = {xfronth, xh, xwh, xfrontwh};
			int[] bottomy = {yfronth, yh, ywh, yfrontwh};
			//graphics.setColor(new Color(25,25,25));
			graphics.setColor(new Color((int)(box.getColor().getRed()*.1)  ,(int)(box.getColor().getGreen()*.1)  ,(int)(box.getColor().getBlue()*.1)  ));
			graphics.fill(new Polygon(bottomx, bottomy, 4));
			
		}
		
		if(rightfirst)
		{
		int[] rightx = {xfrontw, xw, xwh, xfrontwh};
		int[] righty = {yfrontw, yw, ywh, yfrontwh};
		//graphics.setColor(new Color(210,210,210));
		graphics.setColor(new Color((int)(box.getColor().getRed()*.82 ) ,(int)(box.getColor().getGreen()*.82 ) , (int)(box.getColor().getBlue()*.82  )));
		graphics.fill(new Polygon(rightx, righty, 4));
		}
		else
		{
			int[] leftx = {xfront,x,xh, xfronth};
			int[] lefty = {yfront, y, yh, yfronth};
			//graphics.setColor(new Color(35,35,35));
			graphics.setColor( new Color((int)(box.getColor().getRed()*.14), (int)(box.getColor().getGreen()*.14) , (int)(box.getColor().getBlue()*.14)));
			graphics.fill(new Polygon(leftx,lefty,4));
		}
		
		if(camera.getZ()<box.getZ() && camera.getZ() > box.getZ()-box.getDepth())
		{
			if(rightfirst)
			{
			int[] rightx = {xfrontw, xw, xwh, xfrontwh};
			int[] righty = {yfrontw, yw, ywh, yfrontwh};
			//graphics.setColor(new Color(210,210,210));
			graphics.setColor(new Color((int)(box.getColor().getRed()*.82 ) ,(int)(box.getColor().getGreen()*.82 ) , (int)(box.getColor().getBlue()*.82  )));
			graphics.fill(new Polygon(rightx, righty, 4));
			}
			else
			{
				int[] leftx = {xfront,x,xh, xfronth};
				int[] lefty = {yfront, y, yh, yfronth};
				//graphics.setColor(new Color(35,35,35));
				graphics.setColor( new Color((int)(box.getColor().getRed()*.14), (int)(box.getColor().getGreen()*.14) , (int)(box.getColor().getBlue()*.14)));
				graphics.fill(new Polygon(leftx,lefty,4));
			}
			
			if(topfirst)
			{
			int[] topx = {xfront, x, xw, xfrontw};
			int[] topy = {yfront, y, yw, yfrontw};
			//graphics.setColor(new Color(255,255,255));
			graphics.setColor(box.getColor());
			graphics.fill(new Polygon(topx, topy,4));
			}
			else
			{
				int[] bottomx = {xfronth, xh, xwh, xfrontwh};
				int[] bottomy = {yfronth, yh, ywh, yfrontwh};
				//graphics.setColor(new Color(25,25,25));
				graphics.setColor(new Color((int)(box.getColor().getRed()*.1)  ,(int)(box.getColor().getGreen()*.1)  ,(int)(box.getColor().getBlue()*.1)  ));
				graphics.fill(new Polygon(bottomx, bottomy, 4));
				
			}
			
			
		}
		
		if(camera.getX()>box.getX()&& camera.getX()<box.getX()+box.getWidth())
		{
			if(rightfirst)
			{
			int[] rightx = {xfrontw, xw, xwh, xfrontwh};
			int[] righty = {yfrontw, yw, ywh, yfrontwh};
			//graphics.setColor(new Color(210,210,210));
			graphics.setColor(new Color((int)(box.getColor().getRed()*.82 ) ,(int)(box.getColor().getGreen()*.82 ) , (int)(box.getColor().getBlue()*.82  )));
			graphics.fill(new Polygon(rightx, righty, 4));
			}
			else
			{
				int[] leftx = {xfront,x,xh, xfronth};
				int[] lefty = {yfront, y, yh, yfronth};
				//graphics.setColor(new Color(35,35,35));
				graphics.setColor( new Color((int)(box.getColor().getRed()*.14), (int)(box.getColor().getGreen()*.14) , (int)(box.getColor().getBlue()*.14)));
				graphics.fill(new Polygon(leftx,lefty,4));
			}
			
			if(!backfirst){
				int[] arrayx = {x,xw,xwh,xh};
				int[] arrayy = {y, yw,ywh,yh};
				Polygon front = new Polygon(arrayx,arrayy , 4);
				//graphics.setColor(new Color(120,120,120));
				graphics.setColor( new Color( (int)(box.getColor().getRed()*.47),(int)(box.getColor().getGreen()*.47), (int)(box.getColor().getBlue()*.47)));
				graphics.fill(front);
				}
				else
				{
					
					int[] frontx = {xfront, xfrontw, xfrontwh, xfronth};
					int[] fronty = {yfront, yfrontw, yfrontwh, yfronth};
					//graphics.setColor(new Color(120,120,120));
					graphics.setColor( new Color( (int)(box.getColor().getRed()*.47),(int)(box.getColor().getGreen()*.47), (int)(box.getColor().getBlue()*.47)));
					graphics.fill(new Polygon(frontx, fronty, 4));
				}
			
		}
		if(camera.getY()> box.getY() && camera.getY()< box.getY()+box.getHeight())
		{
			if(!backfirst){
				int[] arrayx = {x,xw,xwh,xh};
				int[] arrayy = {y, yw,ywh,yh};
				Polygon front = new Polygon(arrayx,arrayy , 4);
				//graphics.setColor(new Color(120,120,120));
				graphics.setColor( new Color( (int)(box.getColor().getRed()*.47),(int)(box.getColor().getGreen()*.47), (int)(box.getColor().getBlue()*.47)));
				graphics.fill(front);
				}
				else
				{
					
					int[] frontx = {xfront, xfrontw, xfrontwh, xfronth};
					int[] fronty = {yfront, yfrontw, yfrontwh, yfronth};
					//graphics.setColor(new Color(120,120,120));
					graphics.setColor( new Color( (int)(box.getColor().getRed()*.47),(int)(box.getColor().getGreen()*.47), (int)(box.getColor().getBlue()*.47)));
					graphics.fill(new Polygon(frontx, fronty, 4));
				}
				
			if(rightfirst)
			{
			int[] rightx = {xfrontw, xw, xwh, xfrontwh};
			int[] righty = {yfrontw, yw, ywh, yfrontwh};
			//graphics.setColor(new Color(210,210,210));
			graphics.setColor(new Color((int)(box.getColor().getRed()*.82 ) ,(int)(box.getColor().getGreen()*.82 ) , (int)(box.getColor().getBlue()*.82  )));
			graphics.fill(new Polygon(rightx, righty, 4));
			}
			else
			{
				int[] leftx = {xfront,x,xh, xfronth};
				int[] lefty = {yfront, y, yh, yfronth};
				//graphics.setColor(new Color(35,35,35));
				graphics.setColor( new Color((int)(box.getColor().getRed()*.14), (int)(box.getColor().getGreen()*.14) , (int)(box.getColor().getBlue()*.14)));
				graphics.fill(new Polygon(leftx,lefty,4));
			}
		}
		
		
		
//		if(!rightfirst){
//		int[] leftx = {xfront,x,xh, xfronth};
//		int[] lefty = {yfront, y, yh, yfronth};
//		graphics.setColor(new Color(35,35,35));
//		graphics.fill(new Polygon(leftx,lefty,4));
//		}
//		else
//		{
//			int[] rightx = {xfrontw, xw, xwh, xfrontwh};
//			int[] righty = {yfrontw, yw, ywh, yfrontwh};
//			graphics.setColor(new Color(210,210,210));
//			graphics.fill(new Polygon(rightx, righty, 4));
//		}
//		if(!topfirst)
//		{
//		int[] bottomx = {xfronth, xh, xwh, xfrontwh};
//		int[] bottomy = {yfronth, yh, ywh, yfrontwh};
//		graphics.setColor(new Color(25,25,25));
//		graphics.fill(new Polygon(bottomx, bottomy, 4));
//		}
//		else
//		{
//			int[] topx = {xfront, x, xw, xfrontw};
//			int[] topy = {yfront, y, yw, yfrontw};
//			graphics.setColor(new Color(255,255,255));
//			graphics.fill(new Polygon(topx, topy,4));
//		}
//		
//		if(!backfirst)
//		{
//		int[] frontx = {xfront, xfrontw, xfrontwh, xfronth};
//		int[] fronty = {yfront, yfrontw, yfrontwh, yfronth};
//		graphics.setColor(new Color(120,120,120));
//		graphics.fill(new Polygon(frontx, fronty, 4));
//		}
//		else
//		{
//			int[] arrayx = {x,xw,xwh,xh};
//			int[] arrayy = {y, yw,ywh,yh};
//			Polygon front = new Polygon(arrayx,arrayy , 4);
//			graphics.setColor(new Color(120,120,120));
//			graphics.fill(front);
//		}
		
//		graphics.setColor(box.getColor());
//		if(!camera.isBehind(box.getX(), box.getY(), box.getZ()) && !camera.isBehind(box.getX() + box.getWidth(), box.getY(), box.getZ()))
//		graphics.drawLine(x, y, xw, yw);
//		if(!camera.isBehind(box.getX() + box.getWidth(), box.getY(), box.getZ()) && !camera.isBehind(box.getX() + box.getWidth(), box.getY() + box.getHeight(), box.getZ()))
//		graphics.drawLine(xw, yw, xwh, ywh);
//		if(!camera.isBehind(box.getX() + box.getWidth(), box.getY() + box.getHeight(), box.getZ()) && !camera.isBehind(box.getX(), box.getY() + box.getHeight(), box.getZ()))
//		graphics.drawLine(xwh, ywh, xh, yh);
//		if(!camera.isBehind(box.getX(), box.getY() + box.getHeight(), box.getZ()) && !camera.isBehind(box.getX(), box.getY(), box.getZ()))
//		graphics.drawLine(xh, yh, x, y);
//		if(!camera.isBehind(box.getX(), box.getY(), box.getZ() - box.getDepth()) && !camera.isBehind(box.getX() + box.getWidth(), box.getY(), box.getZ() - box.getDepth()))
//		graphics.drawLine(xfront, yfront, xfrontw, yfrontw);
//		if(!camera.isBehind(box.getX() + box.getWidth(), box.getY(), box.getZ() - box.getDepth()) && !camera.isBehind(box.getX() + box.getWidth(), box.getY() + box.getHeight(), box.getZ() - box.getDepth()))
//		graphics.drawLine(xfrontw, yfrontw, xfrontwh, yfrontwh);
//		if(!camera.isBehind(box.getX() + box.getWidth(), box.getY() + box.getHeight(), box.getZ() - box.getDepth()) && !camera.isBehind(box.getX(), box.getY() + box.getHeight(), box.getZ() - box.getDepth()))
//		graphics.drawLine(xfrontwh, yfrontwh, xfronth, yfronth);
//		if(!camera.isBehind(box.getX(), box.getY() + box.getHeight(), box.getZ() - box.getDepth()) && !camera.isBehind(box.getX(), box.getY(), box.getZ() - box.getDepth()))
//		graphics.drawLine(xfronth, yfronth, xfront, yfront);
//		if(!camera.isBehind(box.getX(), box.getY(), box.getZ()) && !camera.isBehind(box.getX(), box.getY(), box.getZ() - box.getDepth()))
//		graphics.drawLine(x, y, xfront, yfront);
//		if(!camera.isBehind(box.getX() + box.getWidth(), box.getY(), box.getZ()) && !camera.isBehind(box.getX() + box.getWidth(), box.getY() , box.getZ() - box.getDepth()))
//		graphics.drawLine(xw, yw, xfrontw, yfrontw);
//		if(!camera.isBehind(box.getX() + box.getWidth(), box.getY() + box.getHeight(), box.getZ()) && !camera.isBehind(box.getX() + box.getWidth(), box.getY() + box.getHeight(), box.getZ() - box.getDepth()))
//		graphics.drawLine(xwh, ywh, xfrontwh, yfrontwh);
//		if(!camera.isBehind(box.getX(), box.getY() + box.getHeight(), box.getZ()) && !camera.isBehind(box.getX(), box.getY() + box.getHeight(), box.getZ() - box.getDepth()))
//		graphics.drawLine(xh, yh, xfronth, yfronth);
		
		
		
		
	}
	
	public double calculateX(int x, int y, int z, double angle, double theta )
	{
		
		
		
		double testZero = (z!=0) ? Math.atan( ((double)x) / z   )  : Math.PI/2;
		
		double w = Math.sqrt( Math.pow(z, 2) +Math.pow(x, 2)  ) * Math.sin( this.degreesToRadians(theta) - testZero  );
		//System.out.println(w);
		return w ;
	}
	
	public double calculateY(int x, int y, int z, double angle, double theta)
	{
		
		double testZero = (z!=0) ? Math.atan( ((double)x) / z   )  : Math.PI/2;
		
		double w = Math.sqrt( Math.pow(z, 2) +Math.pow(x, 2)  ) * Math.sin( this.degreesToRadians(theta) - testZero  );
		
		testZero = ( Math.sqrt(  Math.pow(z, 2) + Math.pow(y, 2) + Math.pow(x, 2) - Math.pow(w, 2)    )!=0)        ?     Math.asin( y  /  Math.sqrt(  Math.pow(z, 2) + Math.pow(y, 2) + Math.pow(x, 2) - Math.pow(w, 2)    ) ) : 0 ;
		
		double h = Math.sqrt( Math.pow(z, 2) + Math.pow(y, 2) + Math.pow(x, 2) - Math.pow(w, 2) ) * Math.sin( testZero   - this.degreesToRadians(angle)    );
		//System.out.println(h);
		return h;
		
	}
	
	public double calculateDistance(int x, int y, int z, double angle, double theta)
	{
		
		double testZero = (z!=0) ? Math.atan( ((double)x) / z   )  : Math.PI/2 ;
		
		double w = Math.sqrt( Math.pow(z, 2) +Math.pow(x, 2)  ) * Math.sin( this.degreesToRadians(theta) - testZero  );
		
		testZero = ( Math.sqrt(  Math.pow(z, 2) + Math.pow(y, 2) + Math.pow(x, 2) - Math.pow(w, 2)    )!=0)        ?     Math.asin( y  /  Math.sqrt(  Math.pow(z, 2) + Math.pow(y, 2) + Math.pow(x, 2) - Math.pow(w, 2)    ) ) : 0 ;
		
		double d = Math.sqrt( Math.pow(z, 2) + Math.pow(y, 2) + Math.pow(x, 2) - Math.pow(w, 2) ) * Math.cos( testZero   - this.degreesToRadians(angle));
		//System.out.println(d);
		return d;
	}
	
	public void drawBox(Box box, Graphics2D graphics)
	{
		int x = (box.getX()-(camera.getX()-camera.getZ()+box.getZ()))*WIDTH/camera.findVisionWidth(camera.getZ()-box.getZ());
		int y = (box.getY() -(camera.getY()-camera.getZ()+box.getZ()))*HEIGHT/camera.findVisionWidth(camera.getZ()-box.getZ());
		
		int xw = (box.getX() + box.getWidth() - (camera.getX()-camera.getZ()+box.getZ()))*WIDTH/camera.findVisionWidth(camera.getZ()-box.getZ());
		int yw = (box.getY() - (camera.getY()-camera.getZ()+box.getZ()))*HEIGHT/camera.findVisionWidth(camera.getZ()-box.getZ());
		
		int xh = (box.getX() - (camera.getX()-camera.getZ()+box.getZ()))*WIDTH/camera.findVisionWidth(camera.getZ()-box.getZ());
		int yh = (box.getY() + box.getHeight() - (camera.getY()-camera.getZ()+box.getZ()) )*WIDTH/camera.findVisionWidth(camera.getZ()-box.getZ());
		
		int xwh = (box.getX() + box.getWidth() - (camera.getX()-camera.getZ()+box.getZ()))*WIDTH/camera.findVisionWidth(camera.getZ()-box.getZ());
		int ywh = (box.getY() + box.getHeight() - (camera.getY()-camera.getZ()+box.getZ()) )*WIDTH/camera.findVisionWidth(camera.getZ()-box.getZ());
		
		int xfront = (box.getX()-(camera.getX()-camera.getZ()+(box.getZ()+box.getDepth())))*WIDTH/camera.findVisionWidth(camera.getZ()-(box.getZ()+box.getDepth()));
		int yfront = (box.getY() -(camera.getY()-camera.getZ()+(box.getZ()+box.getDepth())))*HEIGHT/camera.findVisionWidth(camera.getZ()-(box.getZ()+box.getDepth()));
		
		int xfrontw = (box.getX() + box.getWidth() - (camera.getX()-camera.getZ()+(box.getZ()+box.getDepth())))*WIDTH/camera.findVisionWidth(camera.getZ()-(box.getZ()+box.getDepth()));
		int yfrontw = (box.getY() -(camera.getY()-camera.getZ()+(box.getZ()+box.getDepth())))*HEIGHT/camera.findVisionWidth(camera.getZ()-(box.getZ()+box.getDepth()));
		
		int xfrontwh = (box.getX() + box.getWidth() - (camera.getX()-camera.getZ()+(box.getZ()+box.getDepth())))*WIDTH/camera.findVisionWidth(camera.getZ()-(box.getZ()+box.getDepth()));
		int yfrontwh = (box.getY() + box.getHeight() - (camera.getY()-camera.getZ()+(box.getZ()+box.getDepth())) )*WIDTH/camera.findVisionWidth(camera.getZ()-(box.getZ()+box.getDepth()));
		
		int xfronth =  (box.getX()-(camera.getX()-camera.getZ()+(box.getZ()+box.getDepth())))*WIDTH/camera.findVisionWidth(camera.getZ()-(box.getZ()+box.getDepth()));
		int yfronth =  (box.getY() + box.getHeight() - (camera.getY()-camera.getZ()+(box.getZ()+box.getDepth())) )*WIDTH/camera.findVisionWidth(camera.getZ()-(box.getZ()+box.getDepth()));
//		
//		boolean topfirst = camera.getY()<box.getY();
//		
//		int[] arrayx = {x,xw,xwh,xh};
//		int[] arrayy = {y, yw,ywh,yh};
//		Polygon front = new Polygon(arrayx,arrayy , 4);
//		graphics.setColor(new Color(120,120,120));
//		graphics.fill(front);
//		if(!topfirst)
//		{
//		int[] topx = {xfront, x, xw, xfrontw};
//		int[] topy = {yfront, y, yw, yfrontw};
//		graphics.setColor(new Color(255,255,255));
//		graphics.fill(new Polygon(topx, topy,4));
//		}
//		else
//		{
//			int[] bottomx = {xfronth, xh, xwh, xfrontwh};
//			int[] bottomy = {yfronth, yh, ywh, yfrontwh};
//			graphics.setColor(new Color(25,25,25));
//			graphics.fill(new Polygon(bottomx, bottomy, 4));
//			
//		}
//		
//		int[] rightx = {xfrontw, xw, xwh, xfrontwh};
//		int[] righty = {yfrontw, yw, ywh, yfrontwh};
//		graphics.setColor(new Color(210,210,210));
//		graphics.fill(new Polygon(rightx, righty, 4));
//		int[] leftx = {xfront,x,xh, xfronth};
//		int[] lefty = {yfront, y, yh, yfronth};
//		graphics.setColor(new Color(35,35,35));
//		graphics.fill(new Polygon(leftx,lefty,4));
//		if(!topfirst)
//		{
//		int[] bottomx = {xfronth, xh, xwh, xfrontwh};
//		int[] bottomy = {yfronth, yh, ywh, yfrontwh};
//		graphics.setColor(new Color(25,25,25));
//		graphics.fill(new Polygon(bottomx, bottomy, 4));
//		}
//		else
//		{
//			int[] topx = {xfront, x, xw, xfrontw};
//			int[] topy = {yfront, y, yw, yfrontw};
//			graphics.setColor(new Color(255,255,255));
//			graphics.fill(new Polygon(topx, topy,4));
//		}
//		
//		
//		int[] frontx = {xfront, xfrontw, xfrontwh, xfronth};
//		int[] fronty = {yfront, yfrontw, yfrontwh, yfronth};
//		graphics.setColor(new Color(120,120,120));
//		graphics.fill(new Polygon(frontx, fronty, 4));
//		
		graphics.setColor(Color.WHITE);
		graphics.drawLine(x, y, xw, yw);
		graphics.drawLine(xw, yw, xwh, ywh);
		graphics.drawLine(xwh, ywh, xh, yh);
		graphics.drawLine(xh, yh, x, y);
		graphics.drawLine(xfront, yfront, xfrontw, yfrontw);
		graphics.drawLine(xfrontw, yfrontw, xfrontwh, yfrontwh);
		graphics.drawLine(xfrontwh, yfrontwh, xfronth, yfronth);
		graphics.drawLine(xfronth, yfronth, xfront, yfront);
		graphics.drawLine(x, y, xfront, yfront);
		graphics.drawLine(xw, yw, xfrontw, yfrontw);
		graphics.drawLine(xwh, ywh, xfrontwh, yfrontwh);
		graphics.drawLine(xh, yh, xfronth, yfronth);
		
		
		
	}
	
	public void checkRotation()
	{
		double lrAngle = -2 * ( mouse.getX() - WIDTH/2 ) / (WIDTH/2);
		double udAngle = ( 2 * (mouse.getY()-WIDTH/2) / (WIDTH/2));
		camera.setTheta(camera.getTheta()-lrAngle);
		camera.setAngle(camera.getAngle() + udAngle );
	}
	
	
	public void paint()
	{
		BufferStrategy bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(3);
			requestFocus();
			return;			
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(this.image, 0, 0, null);
	    g.dispose();
	    bs.show();	
	}
	
	
	
	@Override
	public void run() {
		while(true)
		{
			camera.tick();
			checkRotation();
			//System.out.println(boxes.size());
			render();
			paint();			
		}
		
	}

	public void start()
	{
		new Thread(this).start();
	}
	
	
	
	
	
	
}
