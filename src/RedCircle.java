import java.awt.Color;
import java.awt.Graphics;


public class RedCircle implements Circle{

	public double x;
	public double y;
	public int side;

	public double dx;
	public double dy;
	private double rad;
	private double speed;
	public Color color = new Color(177,71,1);
	public Color color2 = new Color(208,143,101);
	public Color or = new Color(250,210,75);
	public boolean dead;
	
	// CONSTRUCTOR
		public RedCircle() {
			
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
	@Override
	public void update() {
		// TODO Auto-generated method stub
		x += dx;
		y += dy;
	   if(x<0 && dx<0) dx=-dx;
	   if(x>GamePanel.WIDTH-side && dx>0) dx=-dx;
	}

	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		if(dead==true ||  y > GamePanel.HEIGHT+side){
			return true;
		}
		else return false;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(this.color);
		g.fillOval((int)x, (int)y, side, side);
		g.setColor(this.color2);
		g.fillOval((int)x+5, (int)y+2, side/2, side/2);
	}

	@Override
	public void death() {
		// TODO Auto-generated method stub
		GamePanel.messages.add(new Messages((int)(this.x),(int)(this.y),"dédoublement",color2));
		for(int i = 0; i<=1;i++){
			GamePanel.blueCircles.add(new BlueCircle(this.x,this.y,new Color(236,214,19), new Color(243,230,112),16));
			}
	}

}
