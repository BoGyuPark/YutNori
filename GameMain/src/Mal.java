
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Mal {
	final int CharWidthSize = 55, CharHeightSize = 60;
	int PlayerNum;
	String[][] ImageAddress = { { "GUI\\char1.png", "GUI\\char1_2.png", "GUI\\char1_3.png", "GUI\\char1_4.png" },
			{ "GUI\\char2.png", "GUI\\char2_2.png", "GUI\\char2_3.png", "GUI\\char2_4.png" } };

	ImageIcon MalChar;
	ImageIcon MalChar_Sum[] = new ImageIcon[5];

	public JButton[] MalList = new JButton[4];
	public int[][] CurMalPos = new int[2][4];
	public int[][] WaitingMalPos = new int[2][4];
	public int[] GoalInList = { 0, 0, 0, 0 };

	ArrayList<Integer> CombList[] = new ArrayList[4];

	Mal(Container contentPane, int PlayerNum, int Row, String MalName) {

		this.PlayerNum = PlayerNum - 1;

		// 말 이미지
		MalChar = new ImageIcon(ImageAddress[this.PlayerNum][0]);

		setPlayerList(MalList, MalChar, Row, MalName, contentPane, CurMalPos, WaitingMalPos);

		for (int i = 0; i < 4; i++) {
			CombList[i] = new ArrayList<Integer>();
		}

		for (int i = 2; i < 5; i++) {
			MalChar_Sum[i] = new ImageIcon(ImageAddress[this.PlayerNum][i - 1]);
		}

	}

	public void setPlayerList(JButton[] playerList, ImageIcon playerMalImg, int row, String MalName,
			Container contentPane, int[][] MalPosition, int[][] OriginMalPosition) {

		for (int i = 0; i < 4; i++) {

			playerList[i] = new JButton(MalName + Integer.toString(i + 1), playerMalImg);

			playerList[i].setBorderPainted(false);
			playerList[i].setFocusPainted(false);
			playerList[i].setContentAreaFilled(false);

			playerList[i].setBounds(628 + (i * 50), row, CharWidthSize, CharHeightSize);

			MalPosition[0][i] = OriginMalPosition[0][i] = 628 + (i * 50);
			MalPosition[1][i] = OriginMalPosition[1][i] = row;

			contentPane.add(playerList[i]);
		}
	}

}
