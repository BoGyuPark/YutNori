import java.awt.Font;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class YutPan implements Serializable{
	public JButton YutButton;

	YutPan() {
		// �� ������ ��ư
		ImageIcon YutButtonImg = new ImageIcon("GUI\\NewYutButton.png");
		YutButton = new JButton("�� ������", YutButtonImg);
		YutButton.setHorizontalTextPosition(JButton.CENTER);
		YutButton.setFont(new Font("�ü�", Font.BOLD, 30));
		YutButton.setBorderPainted(false);
		YutButton.setFocusPainted(false);
		YutButton.setContentAreaFilled(false);
		YutButton.setBounds(899, 481, 255, 60);
	}

}
