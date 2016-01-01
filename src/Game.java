



import javax.swing.JFrame;
public class Game extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Game(String s){
		super(s);
	}

	public static void main(String[] args) {
	   Game window = new Game("BubblesCrazy");
	   window.setLocationRelativeTo(null);    
	   window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	   window.setResizable(false);
	   window.setSize(300,500);
	   window.setAlwaysOnTop(false);
	   GamePanel panel=new GamePanel();
	   panel.setFocusable(true);
	   window.setContentPane(panel);
	   window.setVisible(true);
	   window.setLocationRelativeTo(null);
	}


	


	
}

