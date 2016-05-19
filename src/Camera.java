import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Camera implements KeyListener {

	private double x;
	private double y;
	private double z;
	private double angle;
	private double theta;
	private double speed;
	private double yv;
	private double ya;
	
	public Camera() {
		this(0,0,0);
	}
	
	public Camera(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		angle = 0;
		theta = 0;
		speed = 1.0;
		yv = 0;
		ya = .1;
		
	}
	
	public int getX(){return (int)x;}
	public int getY(){return (int)y;}
	public int getZ(){return (int)z;}
	public double getDoubleX(){return x;}
	public double getDoubleY(){return y;}
	public double getDoubleZ(){return z;}
	
	
	public double getAngle(){return angle;}
	public double getTheta(){return theta;}
	
	public void setAngle(double newAngle)
	{
		if(newAngle>=360)newAngle = newAngle-360;
		if(newAngle<0)newAngle = newAngle+360;
		angle = newAngle;
	}
	public void setTheta(double newAngle)
	{
		if(newAngle>=360)newAngle = newAngle-360;
		if(newAngle<0)newAngle = newAngle+360;
		theta = newAngle;
	}
	
	public void setPosition(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setPosition(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int findVisionWidth(int distance)
	{
		//System.out.println(distance*2+1);
		return distance*2+1;
		
		
	}
	
	public double distanceFrom(Box box)
	{
		return Math.sqrt(Math.pow((x-box.getX()), 2) + Math.pow((y-box.getY()), 2) + Math.pow((z-box.getZ()), 2));
	}
	
	
	public void rotateLeft()
	{
		setTheta(getTheta()-5);
	}
	
	public void rotateRight()
	{
		setTheta(getTheta()+5);
	}
	
	public void rotateUp()
	{
		setAngle(getAngle()+5);
	}
	
	public void rotateDown()
	{
		setAngle(getAngle()-5);
	}
	
	public void moveForward()
	{
		//setPosition(getX(),getY(),getZ()-1);
		setPosition(getDoubleX() - (speed*Math.sin(Central.degreesToRadians(theta))),getY(), getDoubleZ() -(speed*Math.cos(Central.degreesToRadians(theta))));
	}
	
	public void moveBackward()
	{
		//setPosition(getX(),getY(),getZ()+1);
		setPosition(getDoubleX() + (speed*Math.sin(Central.degreesToRadians(theta))),getY(), getDoubleZ() +(speed*Math.cos(Central.degreesToRadians(theta))));
	}

	public void moveLeft()
	{
		//setPosition(getX()-1,getY(),getZ());
		setPosition(getDoubleX() - (speed*Math.sin(Central.degreesToRadians(theta-90))),getY(), getDoubleZ() - (speed*Math.cos(Central.degreesToRadians(theta-90))));
	}
	
	public void moveRight()
	{
		//setPosition(getX()+1,getY(),getZ());
		setPosition(getDoubleX() - (speed*Math.sin(Central.degreesToRadians(theta+90))),getY(), getDoubleZ() - (speed*Math.cos(Central.degreesToRadians(theta+90))));
	}
	
	public void moveUp()
	{
		setPosition(getX(),getY()-1,getZ());
	}
	public void moveDown()
	{
		setPosition(getX(),getY()+1,getZ());
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if( e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
		{
			moveForward();
			//rotateUp();
		}  
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
		{
			moveBackward();
			//rotateDown();
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
		{
			moveLeft();
			//rotateLeft();
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
		{
			moveRight();
			//rotateRight();
		}
		if(e.getKeyCode() == KeyEvent.VK_COMMA)
		{
			moveDown();
		}
		if(e.getKeyCode() == KeyEvent.VK_PERIOD)
		{
			moveUp();
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			yv = -2;
		}
	}
	
	
	
	public double reduceAngle(double newAngle)
	{
		if(newAngle>=360)newAngle = newAngle-360;
		if(newAngle<0)newAngle = newAngle+360;
		return newAngle;
		
	}
	
	public void tick()
	{
		yv+=ya;
		if(y<=50) y = (y+yv < 50 )? y+yv : 50;
		if(y == 50)yv = 0;
	}
	
	
	
	public boolean isBehind(int newx, int newy, int newz)
	{
		int dx = newx - (int)x;
		int dy = newy- (int)y;
		int dz = -(newz - (int)z);
		double newTheta =      (dz!=0) ?     ((dz>0)? Math.atan(dx/dz) : 180-Math.atan(dx/dz))   :      ((dx>0)? 90 : -90)     ;
		double newAngle =   (Math.sqrt(Math.pow(dx, 2)+Math.pow(dz, 2)) == 0) ? Math.atan(dy/(Math.sqrt(Math.pow(dx, 2)+Math.pow(dz, 2)))) : (dy<0)? 90:-90   ;
		double thetaDifference = findDifference(theta,newTheta);
		double angleDifference = findDifference(angle, newAngle);
		if(dx==0 && dy==0 && dz ==0) return true;
		//System.out.println(dx + " " + dy + " " + dz);
		//System.out.println(newTheta);
		//System.out.println(theta);
		//if(thetaDifference>90) newAngle = 180-newAngle;
//		if(angle<315&&angle>180)
//		{
//			if(findDifference(newAngle, angle)<=90) return false;
//			else return true;
//		}
//		else
//		{
//		if(findDifference(newTheta,theta)<=90) return false;
//		else return true;
////		}
		
//		if(findDifference(angle,newAngle)>90) return true;
//		else return false;
		
//		if ((thetaDifference+angleDifference )/ 2 > 90) return true;
//		else return false;
		
		
		System.out.println(newz-(int)z);
		if(newz<z)
		return false;
		else return true;
		
	}

	public double findDifference(double angle1, double angle2)
	{
		if (Math.abs(angle1-angle2)>180)
		{
			if(angle1>angle2) return Math.abs(angle1- (angle2+360));
			else return Math.abs(angle1+360-angle2);
		}
		else return Math.abs(angle1-angle2);
		
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
