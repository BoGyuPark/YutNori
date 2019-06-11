import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Yut {
	JLabel[] YutImg = new JLabel[4];
	ImageIcon YutFront,YutBack,YutBackDo;
	Yut(){
		//윷 이미지
		YutFront = new ImageIcon("GUI\\NEW_YUT.png");
		YutBack = new ImageIcon("GUI\\New_yutBack.png");
		YutBackDo = new ImageIcon("GUI\\New_yutbackdo.png");
		
		
		//윳 배열을 통하여 윷 세팅
		for(int i=0; i<4; i++) {
			YutImg[i] = new JLabel(YutFront);
			YutImg[i].setBounds(905+i*60 , 261, 50, 83);
		}

	}
}
