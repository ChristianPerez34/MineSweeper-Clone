import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {

	public void mousePressed(MouseEvent e) {		//When mouse button is pressed
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MineSweeperGrid myPanel = (MineSweeperGrid) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			myFrame = (JFrame) c;
			myPanel = (MineSweeperGrid) myFrame.getContentPane().getComponent(0);
			myInsets = myFrame.getInsets();
			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			x = e.getX();
			y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {		//When mouse button is released
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MineSweeperGrid myPanel = (MineSweeperGrid) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			if (myPanel.mouseDownGridX == -1 || myPanel.mouseDownGridY == -1) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if (myPanel.mouseDownGridX != gridX || myPanel.mouseDownGridY != gridY) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					}
					else {
						//On the grid
						if(myPanel.isMine(gridX, gridY) && myPanel.getColorArray(gridX,gridY) != Color.RED) {
							//If square is a bomb and hasn't been flagged, user lost
							myPanel.minePressed();
						} else if(myPanel.getColorArray(gridX,gridY) != Color.RED && myPanel.getColorArray(gridX,gridY) != Color.BLACK) {
							//Paint the panel if it hasn't been flagged or revealed to be a bomb
							myPanel.setColorArray(gridX,gridY,Color.GRAY);
							//Call the chain method to clear adjacent tiles
							myPanel.adjacentTiles(gridX, gridY);
							//Check if user has met winning conditions
							myPanel.isWinner();
						}
						myPanel.repaint();
					}
				}
			}
			myPanel.repaint();
		case 3:		//Right mouse button
			c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			myFrame = (JFrame)c;
			myPanel = (MineSweeperGrid) myFrame.getContentPane().getComponent(0);
			myInsets = myFrame.getInsets();
			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			x = e.getX();
			y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			gridX = myPanel.getGridX(x, y);
			gridY = myPanel.getGridY(x, y);
			if (myPanel.mouseDownGridX == -1 || myPanel.mouseDownGridY == -1) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//On the grid
						if(myPanel.getColorArray(gridX,gridY) == Color.RED) {
							//If tile is flagged, paint it white
							myPanel.setColorArray(gridX,gridY,Color.WHITE);
							//Lower the counter by 1
							myPanel.removeFlag();
						} else if(myPanel.getColorArray(gridX,gridY) != Color.GRAY && myPanel.getColorArray(gridX,gridY) != Color.BLACK && myPanel.getFlagCounter() < myPanel.getMaxFlags()){
							//Paint the tile red as long as it hasn't been uncovered or shown to be a bomb
							//Increase counter by 1
							myPanel.addFlag();
							myPanel.setColorArray(gridX,gridY,Color.RED);
						} 
					}
				}
			}
			myPanel.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}