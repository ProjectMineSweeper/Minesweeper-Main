import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame myFrame = new JFrame("Color Grid");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(700, 200);
		myFrame.setSize(500, 520);

		MyPanel myPanel = new MyPanel();
		myFrame.add(myPanel);

		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);

		myFrame.setVisible(true);
	}
}