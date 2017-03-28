import javax.swing.JFrame;

/*
 *                     
 * @author Christian Perez Villanueva   
 * @author Dylan Hernandez   
 *                     
 */

public class MainGame {
	public static void main(String[] args) {
		JFrame myFrame = new JFrame("Minesweeper Game");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setSize(350, 350);
		myFrame.setLocationRelativeTo(null);

		MineSweeperGrid myPanel = new MineSweeperGrid();
		myFrame.add(myPanel);

		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);

		myFrame.setVisible(true);
	}
}