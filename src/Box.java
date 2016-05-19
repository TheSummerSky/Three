import java.awt.Color;

public class Box {

	int x;
	int y;
	int z;
	int w;
	int h;
	int d;
	Color color;
	
	public Box(int x, int y, int z, int w, int h, int d, Color color) {
		this.x=x;
		this.y=y;
		this.z=z;
		this.w=w;
		this.h=h;
		this.d=d;
		this.color = color;
	}
	

	public Box(int x, int y, int z, int w, int h, int d) 
	{
		this(x,y,z,w,h,d,Color.WHITE);
	}
	
	public Box(int x, int y, int z)
	{
		this(x,y,z,5,5,5, Color.WHITE);
	}
	
	public int getX(){return x;}
	public int getY(){return y;}
	public int getZ(){return z;}
	public int getWidth(){return w;}
	public int getHeight(){return h;}
	public int getDepth(){return d;}
	public Color getColor(){return color;}
	


}
