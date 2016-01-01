import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
public class GamePanel extends JPanel implements Runnable, MouseListener {
	/**
**/

	private static final long serialVersionUID = 1L;
	// FIELDS
	public static int WIDTH = 300;
	public static int HEIGHT = 500;

	private Thread thread;
	private boolean running;
	public static int timer = 20;
	public static int inctimer = 0;
	public static int addBlueCircle = 0;
	public static int addOtherCircle = 0;
	public static int life = 1;
	public static  int score=0;

	public static boolean pause =false;
	public boolean game = true;
	public boolean firstGame;
	public static boolean chuteLibre;
	public static boolean doubleSpeed;
	public static boolean zigzag;
	private BufferedImage image;
	private BufferedImage panneau;
	private Graphics2D g;
	private Graphics g1;
	private int FPS = 30;
	public int score1;
	public int score2;
	public static boolean multiplayers;
	public static boolean jouer;
	public Connection Bubble;

	public static ArrayList<BlueCircle> blueCircles;
	public static ArrayList<RedCircle> redCircles;
	public static ArrayList<GreenCircle> greenCircles;
	public static ArrayList<BlackCircle> blackCircles;
	public static ArrayList<VioletCircle> violetCircles;
    public static ArrayList<Messages> messages;
	// CONSTRUCTOR
	public GamePanel() {
		super();
		this.addMouseListener(this);
		pause = false;
		firstGame = true;
		jouer = false;
		String url = "jdbc:postgresql://localhost:5433/BubbleCrazy";
	    String user = "postgres";
	    String passwd = "1gillesleon";
	     try {
		      Class.forName("org.postgresql.Driver");
		      Bubble = DriverManager.getConnection(url, user, passwd);	  
		 } catch (Exception e) {
		      e.printStackTrace();
		    } 
	}

	// FUNCTIONS
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}

	}

	public void run() {
		running = true;
		image = new BufferedImage(WIDTH, HEIGHT - 100,
				BufferedImage.TYPE_INT_RGB);
		panneau = new BufferedImage(WIDTH, 100, BufferedImage.TYPE_INT_RGB);
		g1 = panneau.getGraphics();
		g = (Graphics2D) image.getGraphics();
		// Lissage du text uniquement
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// Lissage des dessins
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		long startTime;
		long URDTimeMillis;
		double waitTime;

		long targetTime = 1000 / FPS;
		do {

			running = true;

			blueCircles = new ArrayList<BlueCircle>();
			redCircles = new ArrayList<RedCircle>();
			blackCircles = new ArrayList<BlackCircle>();
			//orangeCircles = new ArrayList<OrangeCircle>();
			greenCircles = new ArrayList<GreenCircle>();
			violetCircles = new ArrayList<VioletCircle>();
            messages= new ArrayList<Messages>();
			// GAME LOOP
			while (running) {
				startTime = System.nanoTime();

				gameUpdate();
				gameRender();
				gameDraw();

				URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
				waitTime =Math.sqrt(Math.pow(targetTime - URDTimeMillis,2));
				try {
					Thread.sleep((long)waitTime);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			for (int i = 0; i < 2500; i++) {
				g.setFont(new Font("calibri", Font.PLAIN, 15));
				g.setColor(Color.gray);
				String l = "- G A M E   O V E R -";
				g.drawString(l, 80, 150);
				g.drawString("S C O R E : " + score, 80,170);
				gameDraw();
			}
			firstGame = false; // Permet de remettre le Jeu � z�ro
			jouer = false;// r�affiche le menu
		} while (game);
	}

	private void gameUpdate() {
		
		addBlueCircle++;
		addOtherCircle++;
		if (addBlueCircle > timer) {
			blueCircles.add(new BlueCircle());

			addBlueCircle = 0;
		}
	boolean down=false;
		int l = 0;
while( l < blueCircles.size()){
		down = blueCircles.get(l).down;
		if (down) {
			life--;
			l=blueCircles.size();
		}
		
		else l++;
		}
		for (int i = 0; i < blueCircles.size(); i++) {
			blueCircles.get(i).update();
			boolean delete = blueCircles.get(i).delete();
			if (delete) {
				blueCircles.remove(i);
				i--;

			}
			
		}
		int k = (int) (Math.random() * 6);
		if (k == 1) {
			if (addOtherCircle > 8 * timer) {
				redCircles.add(new RedCircle());

				addOtherCircle = 0;
			}
		}
		else if (k == 2) {
			if (addOtherCircle > 8 * timer) {
				blackCircles.add(new BlackCircle());

				addOtherCircle = 0;
			}
		}
		else if (k == 3) {
			if (addOtherCircle > 8 * timer) {
				greenCircles.add(new GreenCircle());

				addOtherCircle = 0;
			}
		}
		
		else if (k == 4) {
			if (addOtherCircle > 8 * timer) {
				violetCircles.add(new VioletCircle());

				addOtherCircle = 0;
			}
				}
		
		for (int i = 0; i < greenCircles.size(); i++) {
			greenCircles.get(i).update();
			boolean delete = greenCircles.get(i).delete();
			if (delete) {
				greenCircles.remove(i);
				i--;

			}
		}
		for (int i = 0; i < blackCircles.size(); i++) {
			blackCircles.get(i).update();
			boolean delete = blackCircles.get(i).delete();
			if (delete) {
				blackCircles.remove(i);
				i--;

			}
		}
		for (int i = 0; i < redCircles.size(); i++) {
			redCircles.get(i).update();
			boolean delete = redCircles.get(i).delete();
			if (delete) {
				redCircles.remove(i);
				i--;

			}
		}
		for (int i = 0; i < violetCircles.size(); i++) {
			violetCircles.get(i).update();
			boolean delete = violetCircles.get(i).delete();
			if (delete) {
				violetCircles.remove(i);
				i--;

			}
		}
		for (int i = 0; i < messages.size(); i++) {
			messages.get(i).update();
			boolean delete = messages.get(i).delete();
			if (delete) {
				messages.remove(i);
				i--;

			}
		}
		
	}

	// Powerups update

	// Collisions Cubes-Vaisseau

	// add powerups

	private void gameRender() {
		Color color = new Color(247,251,254);
		Color color2 = new Color(5,118,165);
		Color colorScore =new Color (40,164,102);
		Color colorPanneau = new Color(6,135,189);
		g1.setColor(color2);
		g1.fillRect(0, 50, WIDTH, 50);
		g1.setColor(colorPanneau);
		g1.fillRect(0, 0, WIDTH, 50);
		g1.setColor(colorScore);
		g1.fillRoundRect(110, 20, 80, 30,0,20);
		g1.fillRoundRect(10, 20, 80, 30,20,0);
		g1.fillRoundRect(210, 20, 80, 30,0,20);
		g1.setColor(new Color(36,151,94));
		g1.fillRoundRect(110, 50, 80, 30,20,0);
		g1.fillRoundRect(10, 50, 80, 30,20,0);
		g1.fillRoundRect(210, 50, 80, 30,20,0);
		g1.setFont(new Font("verdana", Font.PLAIN, 16));
		g1.setColor(Color.white);
		g1.drawString("Pause", 25, 55);
		g1.drawString("Score", 125, 45);
		g1.drawString("Highscore", 210, 45);
		g1.drawString(""+score, 125, 65);
		// Affichage du score, des vies et des boucliers et des powerups
		g.setColor(color);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for (int i = 0; i < blueCircles.size(); i++) {
			blueCircles.get(i).draw(g);

		}
		for (int i = 0; i < redCircles.size(); i++) {
			redCircles.get(i).draw(g);

		}
		for (int i = 0; i < blackCircles.size(); i++) {
			blackCircles.get(i).draw(g);

		}
		for (int i = 0; i < greenCircles.size(); i++) {
			greenCircles.get(i).draw(g);

		}
		
		for (int i = 0; i < violetCircles.size(); i++) {
			violetCircles.get(i).draw(g);

		}
		for (int i = 0; i < messages.size(); i++) {
			messages.get(i).draw(g);

		}


		// Mettre le jeu en pause
		while (pause) {
			gameDraw();
			g.setColor(new Color(118,199,232));
			g.fillRoundRect(110, 150, 20, 60,0,20);
			g.fillRoundRect(150, 150, 20, 60,0,20);

		}
	}

	// Affichage Gravity && Inversion

	// Affichage du jeu sur la fenetre
	private void gameDraw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(panneau, 0, 0, null);
		g2.drawImage(image, 0, 100, null);
		// g2.dispose();
	}

	public void mouseClicked(MouseEvent event) {

		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
		int y = event.getY()-100;
		int x = event.getX();
		int y1 = event.getY();
        if(y1<=90 && y1>=20 && x>=10 && x<=90){
        	pause=!pause;
        }
        if(y<=210 && y>=150 && x>=110 && x<=190 && pause)pause=!pause;
		for (int i = 0; i < blueCircles.size(); i++) {
			if (y <= (int) (blueCircles.get(i).y + 1.5*blueCircles.get(i).side )
					&& y >= (int) (blueCircles.get(i).y)
					&& (x >= (int) (blueCircles.get(i).x) && x <= (int) (blueCircles
							.get(i).x + 1.5*blueCircles.get(i).side))&& !pause) {
				blueCircles.get(i).death();
			}
		}
		for (int i = 0; i < blackCircles.size(); i++) {
			if (y <= (int) (blackCircles.get(i).y + blackCircles.get(i).side)
					&& y >= (int) (blackCircles.get(i).y)
					&& (x >= (int) (blackCircles.get(i).x) && x <= (int) (blackCircles
							.get(i).x + blackCircles.get(i).side))&& !pause) {
				blackCircles.get(i).death();
			}
		}
		for (int i = 0; i < redCircles.size(); i++) {
			if (y <= (int) (redCircles.get(i).y + redCircles.get(i).side)
					&& y >= (int) (redCircles.get(i).y)
					&& (x >= (int) (redCircles.get(i).x) && x <= (int) (redCircles
							.get(i).x + redCircles.get(i).side))&& !pause) {
				redCircles.get(i).death();
				redCircles.get(i).dead = true;
			}
		}
		for (int i = 0; i < greenCircles.size(); i++) {
			if (y <= (int) (greenCircles.get(i).y + greenCircles.get(i).side)
					&& y >= (int) (greenCircles.get(i).y)
					&& (x >= (int) (greenCircles.get(i).x) && x <= (int) (greenCircles
							.get(i).x + greenCircles.get(i).side))&& !pause) {
				greenCircles.get(i).death();
				greenCircles.get(i).dead = true;
			}
		}
		for (int i = 0; i < violetCircles.size(); i++) {
			if (y <= (int) (violetCircles.get(i).y + violetCircles.get(i).side)
					&& y >= (int) (violetCircles.get(i).y)
					&& (x >= (int) (violetCircles.get(i).x) && x <= (int) (violetCircles
							.get(i).x + violetCircles.get(i).side))&& !pause) {
				violetCircles.get(i).death();
				
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub

	}
	// Affichage du score des vies, munitions et des powerups

	// Class Clavier

}
