import javax.swing.JFrame;

public class MainMinesweeperProject {
	public static void main(String[] args) {
		JFrame myFrame = new JFrame("Minesweeper Game");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(500, 150);
		myFrame.setSize(600, 600);

		MyPanelProject myPanel = new MyPanelProject();
		myFrame.add(myPanel);

		MyMouseAdapterProject myMouseAdapter = new MyMouseAdapterProject();
		myFrame.addMouseListener(myMouseAdapter);

		myFrame.setVisible(true);
	}
}