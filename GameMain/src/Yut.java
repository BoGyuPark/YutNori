import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Yut {
	JLabel[] YutImg = new JLabel[4];
	ImageIcon YutFront,YutBack,YutBackDo;
	Yut(){
		//�� �̹���
		YutFront = new ImageIcon("GUI\\NEW_YUT.png");
		YutBack = new ImageIcon("GUI\\New_yutBack.png");
		YutBackDo = new ImageIcon("GUI\\New_yutbackdo.png");
		
		
		//�� �迭�� ���Ͽ� �� ����
		for(int i=0; i<4; i++) {
			YutImg[i] = new JLabel(YutFront);
			YutImg[i].setBounds(905+i*60 , 261, 50, 83);
		}

	}
}
