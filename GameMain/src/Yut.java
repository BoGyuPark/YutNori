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
	String[] YutStringList = { "모", "도", "개", "걸", "윷" };

	Yut() {
		// 윷 이미지
		YutFront = new ImageIcon("GUI\\NEW_YUT.png");
		YutBack = new ImageIcon("GUI\\New_yutBack.png");
		YutBackDo = new ImageIcon("GUI\\New_yutbackdo.png");

		// 해쉬 셋
		YutHash.put("도", 0);
		YutHash.put("개", 1);
		YutHash.put("걸", 2);
		YutHash.put("윷", 3);
		YutHash.put("모", 4);
		YutHash.put("빽도", 5);

		// 윳 배열을 통하여 윷 세팅
		for (int i = 0; i < 4; i++) {
			YutImg[i] = new JLabel(YutFront);
			YutImg[i].setBounds(905 + i * 60, 261, 50, 83);
		}

	}
}
