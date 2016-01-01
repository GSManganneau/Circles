import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class Messages {
	public int x;
	public int y;
	public Color color;
	public String message;
	public int meter = 10;
	
	public Messages(int x, int y,String message,Color color){
		this.x = x;
		this.y = y;
		this.message= message;
		this.color=color;
	}
	public void update(){
		y-=10;
		meter--;
	}
	public boolean delete(){
		if(meter==0)return true;
		else return false;
	}
	public void draw(Graphics g){
		
		g.setFont(new Font("verdana", Font.PLAIN, 25));
		g.setColor(color);
		g.drawString(message, x, y);
	}

}
