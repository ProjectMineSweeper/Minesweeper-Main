import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
//import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
						
						if ((gridX > 8)&&(gridY > 8)){
							
						}
									
						if(myPanel.isMine(myPanel.mouseDownGridX, myPanel.mouseDownGridY)){
							Color newColor = Color.BLACK;
							for(int i = 0; i<9; i++){
								for (int j=0; j < 9; j++){
									if (myPanel.minesPanel[i][j]==true){
										
										myPanel.colorArray[i][j]=newColor;
										
										myPanel.repaint();
									}
								}
							}
							
							//Genera un panel con mensaje de "Game Over"
							final JOptionPane pane = new JOptionPane("Game Over");
							
							final JDialog lose = pane.createDialog("Better Luck Next Time!");
							
							lose.setVisible(true);
							
							break;
						}
					
						else if(myPanel.minesInPerimeter(myPanel.mouseDownGridX, myPanel.mouseDownGridY) == 0){
							myPanel.uncoverPerimeter(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
							myPanel.repaint();
							System.out.println(myPanel.minesInPerimeter(myPanel.mouseDownGridX, myPanel.mouseDownGridY));
						}
						else{
							//Poner el numer de bombas que tiene al rededor
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.GRAY; 
							myPanel.repaint();	

						}											
					}
				}
			
		
			myPanel.repaint();
			break;
			}		
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
