import javax.swing.JFrame;

public class MainGame {
	public static void main(String[] args) {
		JFrame myFrame = new JFrame("Minesweeper Game");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(500, 150);
		myFrame.setSize(600, 600);

		MineSweeperGrid myPanel = new MineSweeperGrid();
		myFrame.add(myPanel);

		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);

		myFrame.setVisible(true);
	}
}