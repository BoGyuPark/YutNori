import java.io.Serializable;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Yut implements Serializable{

	public HashMap<String, Integer> YutHash = new HashMap<String, Integer>();

	JLabel[] YutImg = new JLabel[4];
	ImageIcon YutFront, YutBack, YutBackDo;
	
	int[] BiyutResult = new int[4];
	int yutBackCnt = 0;
	String[] YutStringList = { "��", "��", "��", "��", "��" };

	Yut() {
		// �� �̹���
		YutFront = new ImageIcon("GUI\\NEW_YUT.png");
		YutBack = new ImageIcon("GUI\\New_yutBack.png");
		YutBackDo = new ImageIcon("GUI\\New_yutbackdo.png");

		// �ؽ� ��
		YutHash.put("��", 0);
		YutHash.put("��", 1);
		YutHash.put("��", 2);
		YutHash.put("��", 3);
		YutHash.put("��", 4);
		YutHash.put("����", 5);

		// �� �迭�� ���Ͽ� �� ����
		for (int i = 0; i < 4; i++) {
			YutImg[i] = new JLabel(YutFront);
			YutImg[i].setBounds(905 + i * 60, 261, 50, 83);
		}

	}
}
