import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;

import javax.swing.*;

public class Maze extends JPanel{

	private static BufferedImage image;
	private static Graphics2D g;
	private static int xWidth = 2000;
	private static int yWidth = 2000;
	private static Timer t;
	private static double timer = 1;
	private static double currentTimer;
	private static int step = 0;
	
	private static int numOrganisms = 100;
	private static Target target;
	private static double survivalPercentage = 0.8;
	
	private static ArrayList<Organism> organisms = new ArrayList<Organism>();
	
	public Maze(){
		image = new BufferedImage(xWidth, yWidth, BufferedImage.TYPE_INT_RGB);
		g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		target = new Target(xWidth/2, yWidth/2 - 100);
		
		for(int x=0; x<numOrganisms; x++){
			organisms.add(new Organism(xWidth/2,yWidth/2 + 100));
		}
		
		currentTimer = timer;
		
		addMouseListener(new Click());
		addKeyListener(new Key());
		setFocusable(true);
		
		t = new Timer(5, new TimerListener());
		t.start();
	}
	
	public void runGeneration(boolean draw){
		for(Organism organism : organisms){
			organism.move();
		}
		
		if(draw){
			g.setColor(Color.black);
			g.fillRect(0, 0, xWidth, yWidth);
			for(Organism organism : organisms){
				organism.updateColor();
				organism.draw(g);
			}
			target.draw(g);
		}
		
		currentTimer-=1;
		if(currentTimer<=0){
			currentTimer = timer;
			for(Organism organism : organisms){
				organism.setDirection(organism.getDirection(step));
			}
			step++;
		}
		
		if(step >= Organism.dnaLength){
			advanceGeneration();
		}
		repaint();
	}
	
	public void advanceGeneration(){
		int numDie = (int)(organisms.size() * (1-survivalPercentage));
		int numSurvive = organisms.size() - numDie;
		
		for(int k=0; k<numDie; k++){
			int farthestIndex = 0;
			for(int x=1; x<organisms.size(); x++){
				if(getDistance(organisms.get(x).getX(), organisms.get(x).getY(), target.getX(), target.getY()) > 
						getDistance(organisms.get(farthestIndex).getX(), organisms.get(farthestIndex).getY(), target.getX(), target.getY())){
					farthestIndex = x;
				}
			}
			organisms.remove(farthestIndex);
		}
		
		for(int k=0; k<numDie; k++){
			double[] newDNA = organisms.get((int)(Math.random() * numSurvive)).getDNA();
			Organism newOrganism = new Organism();
			newOrganism.setDNA(newDNA);
			newOrganism.mutate(3);
			organisms.add(newOrganism);
		}
		for(Organism organism : organisms){
			organism.setX(xWidth/2);
			organism.setY(yWidth/2 + 100);
		}
		step = 0;
		currentTimer = timer;
	}
	
	public double getDistance(double x1, double y1, double x2, double y2){
		double dist = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
		return dist;
	}
	
	private class Click extends MouseAdapter{
			
		public void mousePressed(MouseEvent e){
			target.setX(e.getX());
			target.setY(e.getY());
		}
	}
	
	private class Key extends KeyAdapter{
		
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				for(int x=0; x<100000; x++){
					runGeneration(false);
				}
			}
		}
	}
	
	private class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			runGeneration(true);
		}
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Maze");
		frame.setSize(xWidth + 16, yWidth + 38);
		frame.setLocation(100,100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setContentPane(new Maze());
		frame.setVisible(true);
	}
}
