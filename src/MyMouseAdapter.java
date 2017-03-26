import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	public void mousePressed(MouseEvent e) {
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
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
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
			Component b = e.getComponent();
			while (!(b instanceof JFrame)) {
				b = b.getParent();
				if (b == null) {
					return;
				}
			}
			JFrame myOtherFrame = (JFrame)b;
			MyPanel myOtherPanel = (MyPanel) myOtherFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myOtherInsets = myOtherFrame.getInsets();
			int X1 = myOtherInsets.left;
			int Y1 = myOtherInsets.top;
			e.translatePoint(-X1, -Y1);
			int X = e.getX();
			int Y = e.getY();
			myOtherPanel.x = X;
			myOtherPanel.y = Y;
			myOtherPanel.mouseDownGridX = myOtherPanel.getGridX(X, Y);
			myOtherPanel.mouseDownGridY = myOtherPanel.getGridY(X, Y);
			myOtherPanel.repaint();
			//Do nothing
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {
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
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
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
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
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
						//Released the mouse button on the same cell where it was pressed
						myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.gray ;



//						if ((gridX == 0) || (gridY == 0)) {
//							//On the left column and on the top row... do nothing
//						} else {
//							//On the grid other than on the left column and on the top row:
//							Color newColor = null;
//							switch (generator.nextInt(5)) {
//							case 0:
//								newColor = Color.YELLOW;
//								break;
//							case 1:
//								newColor = Color.MAGENTA;
//								break;
//							case 2:
//								newColor = Color.BLACK;
//								break;
//							case 3:
//								newColor = new Color(0x964B00);   //Brown (from http://simple.wikipedia.org/wiki/List_of_colors)
//								break;
//							case 4:
//								newColor = new Color(0xB57EDC);   //Lavender (from http://simple.wikipedia.org/wiki/List_of_colors)
//								break;
//							}
//							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
//							myPanel.repaint();
//						}
					}
				}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			Component c1 = e.getComponent();
			while (!(c1 instanceof JFrame)) {
				c1 = c1.getParent();
				if (c1 == null) {
					return;
				}
			}
			JFrame myOtherFrame = (JFrame)c1;
			MyPanel myOtherPanel = (MyPanel) myOtherFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myOtherInsets = myOtherFrame.getInsets();
			int X1 = myOtherInsets.left;
			int Y1 = myOtherInsets.top;
			e.translatePoint(-X1, -Y1);
			int X = e.getX();
			int Y = e.getY();
			myOtherPanel.x = X;
			myOtherPanel.y = Y;
			int otherGridX = myOtherPanel.getGridX(X, Y);
			int otherGridY = myOtherPanel.getGridY(X, Y);
			if(myOtherPanel.mouseDownGridX == otherGridX && myOtherPanel.mouseDownGridY == otherGridY){
				myOtherPanel.colorArray[otherGridX][otherGridY] = Color.RED ;

			}
			else if ((myOtherPanel.mouseDownGridX == -1) || (myOtherPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((otherGridX == -1) || (otherGridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myOtherPanel.mouseDownGridX != otherGridX) || (myOtherPanel.mouseDownGridY != otherGridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					}
				}
			}	
			myOtherPanel.repaint();
			//Do nothing
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}