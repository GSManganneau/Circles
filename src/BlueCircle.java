

import java.awt.Color;
import java.awt.Graphics;

public class BlueCircle {
	// FIELDS

	public double x;
	public double y;
	public int side;
	public int xRebondDroite = GamePanel.WIDTH-30;
	public int xRebondGauche = 0;
	public boolean chuteLibre;
	public boolean zigzag;
	public boolean doubleSpeed;
    public int points = 4;
    public int scoreX;
    public int scoreY;
	public double dx;
	public double dy;
	private double rad;
	private double speed;
	public Color color = new Color (29,162,218);
	public Color color2 = new Color(118,199,232);
	
	public boolean dead;
	public boolean down = false;
	

	// CONSTRUCTOR
	public BlueCircle() {
	
		speed =6;
		side=30;
		x = (Math.random() * GamePanel.WIDTH*0.5)+ 0.25*GamePanel.WIDTH;
		y = 0;
		double angle = Math.random() * 140 + 20;
		rad = Math.toRadians(angle);

		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		

		dead = false;
		
	}
	public BlueCircle(double x, double y) {
	
		speed =4;
		side=30;
		this.x = x ;
		this.y = y;
		double angle = Math.random() * 140 + 20;
		rad = Math.toRadians(angle);

		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		

		
		dead = false;
		
	}
	public BlueCircle(double x, double y, Color color,Color color2,int points) {
		speed =4;
		side=30;
		this.x = x ;
		this.y = y;
		this.points=points;
		this.color=color;
		this.color2=color2;
		double angle = Math.random() * 140 + 20;
		rad = Math.toRadians(angle);
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		dead = false;	
	}

	// FUNCTIONS

	public double getx() {
		return x;
	}

	public double gety() {
		return y;
	}
	public int getSide(){
		return side;
	}

	public void update() {
		x += dx;
		y += dy;
		if (this.y>400-side)down=true;
	   if(x<xRebondGauche && dx<0) dx=-dx;
	   if(x>xRebondDroite && dx>0) dx=-dx;
	}
	public boolean delete(){
		if(dead==true ||  y > GamePanel.HEIGHT+side){
			return true;
		}
		else return false;
	}
	
	public void zigzag(){
		if(this.zigzag && GamePanel.zigzag){
			xRebondGauche= (int)(this.x) - 25;
			xRebondDroite= (int)(this.x) + 25;
		}
		else {
			xRebondDroite = GamePanel.WIDTH-side;
			xRebondGauche = 0;
		}
	}
	public void chuteLibre(){
		if(this.chuteLibre && GamePanel.chuteLibre){
			dx=0;
			dy=7;
		}
	}
	public void doubleSpeed(){
		if (this.doubleSpeed && GamePanel.doubleSpeed){
			dx=1.5*dx;
			dy=1.5*dy;
		}
	}

	public void draw(Graphics g) {
		
		g.setColor(this.color);
		g.fillOval((int)x, (int)y, side, side);
		g.setColor(this.color2);
		g.fillOval((int)x+5, (int)y+2, side/2, side/2);
	}
	public void death(){
		this.dead=true;
		GamePanel.messages.add(new Messages((int)(this.x),(int)(this.y),"+"+this.points,color2));
		GamePanel.score+=this.points;
			}

	

	
}


