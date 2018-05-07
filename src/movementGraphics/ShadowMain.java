package movementGraphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class ShadowMain {
	static JFrame frame;
	static ShadowTest test;
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
	static void createAndShowGUI() {
		test = new ShadowTest();
		test.setBounds(0, 0, 600, 600);
		//mainPanel.setBackground(Color.BLUE);
		frame = new JFrame("Civilisation Clicker");
		frame.setLayout(null);
        frame.setFocusTraversalKeysEnabled(false);
        frame.requestFocusInWindow();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setIconImage(new ImageIcon("graphics/icons/city_game.png").getImage());
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(test);
	}
}
