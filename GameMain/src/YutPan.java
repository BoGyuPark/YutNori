import java.awt.Font;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class YutPan implements Serializable{
	public JButton YutButton;

	YutPan() {
		// À· ´øÁö±â ¹öÆ°
		ImageIcon YutButtonImg = new ImageIcon("GUI\\NewYutButton.png");
		YutButton = new JButton("À· ´øÁö±â", YutButtonImg);
		YutButton.setHorizontalTextPosition(JButton.CENTER);
		YutButton.setFont(new Font("±Ã¼­", Font.BOLD, 30));
		YutButton.setBorderPainted(false);
		YutButton.setFocusPainted(false);
		YutButton.setContentAreaFilled(false);
		YutButton.setBounds(899, 481, 255, 60);
	}

}
