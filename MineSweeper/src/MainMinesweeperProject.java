import javax.swing.JFrame;

public class MainMinesweeperProject {
	public static void main(String[] args) {
		JFrame myFrame = new JFrame("Color Grid");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(400, 400);

		MyPanelProject myPanel = new MyPanelProject();
		myFrame.add(myPanel);

		MyMouseAdapterProject myMouseAdapter = new MyMouseAdapterProject();
		myFrame.addMouseListener(myMouseAdapter);

		myFrame.setVisible(true);
	}
}