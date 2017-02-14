import java.awt.*;

public class Target {

	private double xPos;
	private double yPos;
	private double size;
	private Color color;
	
	public Target(){
		xPos = 0;
		yPos = 0;
		size = 20;
		color = Color.red;
	}
	
	public Target(int x, int y){
		xPos = x;
		yPos = y;
		size = 20;
		color = Color.red;
	}
	
	public double getX(){
		return xPos;
	}
	
	public double getY(){
		return yPos;
	}
	
	public void setX(int x){
		xPos = x;
	}
	
	public void setY(int y){
		yPos = y;
	}
	
	public void draw(Graphics g){
		g.setColor(color);
		g.fillOval((int)(xPos - (size/2)), (int)(yPos - (size/2)), (int)size, (int)size);
	}
}
