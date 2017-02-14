import java.awt.*;

public class Organism {

	public static int dnaLength = 100;
	private double moveSpeed = 10;
	
	private double[] dna;
	private double xPos;
	private double yPos;
	private double velocityX;
	private double velocityY;
	private Color color;
	private double size;
	
	public Organism(){
		dna = new double[dnaLength];
		for(int x=0; x<dna.length; x++){
			dna[x] = (Math.random() * (2*Math.PI));
		}
		
		xPos = 0;
		yPos = 0;
		velocityX = 0;
		velocityY = 0;
		color = Color.white;
		size = 10;
	}
	
	public Organism(int xPos, int yPos){
		dna = new double[dnaLength];
		for(int x=0; x<dna.length; x++){
			dna[x] = (Math.random() * (2*Math.PI));
		}
		
		this.xPos = xPos;
		this.yPos = yPos;
		velocityX = 0;
		velocityY = 0;
		color = Color.white;
		size = 10;
	}
	
	public void setDirection(double direction){
		velocityX = Math.cos(direction) * moveSpeed;
		velocityY = Math.sin(direction) * moveSpeed;
	}
	
	public void move(){
		xPos += velocityX;
		yPos += velocityY;
	}
	
	public void mutate(int amount){
		for(int x=0; x<amount; x++){
			int index = (int)(Math.random() * dnaLength);
			dna[index] = (Math.random() * (2*Math.PI));
		}
	}
	
	public void updateColor(){
		int r = 0;
		int g = 0;
		int b = 0;
		for(int x=0; x<dnaLength/3; x++){
			r += dna[x];
		}
		r /= (dnaLength/3);
		r *= 80;
		if(r > 255)
			r = 255;
		
		for(int x=dnaLength/3; x<(dnaLength*2)/3; x++){
			g += dna[x];
		}
		g /= (dnaLength/3);
		g *= 80;
		if(g > 255)
			g = 255;
		
		for(int x=(dnaLength*2)/3; x<dnaLength; x++){
			b += dna[x];
		}
		b /= (dnaLength/3);
		b *= 80;
		if(b > 255)
			b = 255;
		
		color = new Color(r,g,b);
	}
	
	public void setX(double x){
		xPos = x;
	}
	
	public void setY(double  y){
		yPos = y;
	}
	public void setDNA(double[] dna){
		for(int x=0; x<dna.length; x++){
			this.dna[x] = dna[x];
		}
	}
	
	public double getDirection(int index){
		return dna[index];
	}
	
	public double getX(){
		return xPos;
	}
	
	public double getY(){
		return yPos;
	}
	
	public double[] getDNA(){
		return dna;
	}
	
	public void draw(Graphics g){
		g.setColor(color);
		g.fillOval((int)(xPos - (size/2)), (int)(yPos - (size/2)), (int)size, (int)size);
	}
}
