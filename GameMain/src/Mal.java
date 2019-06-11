
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Mal{
	ImageIcon PlayerOneMal, PlayerTwoMal;
	
	ImageIcon P1_Mal_Sum[] = new ImageIcon[5];
	ImageIcon P2_Mal_Sum[] = new ImageIcon[5];

	final int CharWidthSize=55,CharHeightSize=60;
	public JButton[] PlayerOneList= new JButton[4];
	public JButton[] PlayerTwoList = new JButton[4];
	
	public int [][] PlayerOneMalPosition = new int[2][4]; 
	public int [][] PlayerTwoMalPosition = new int[2][4]; 
	public int [][] OriginPlayerOneMalPosition = new int[2][4]; 
	public int [][] OriginPlayerTwoMalPosition = new int[2][4]; 
	
	ArrayList<Integer> P1Sum[] = new ArrayList[4];
	ArrayList<Integer> P2Sum[] = new ArrayList[4];

	public int [] PlayerOneGoalIn = {0,0,0,0};
	public int [] PlayerTwoGoalIn = {0,0,0,0};
	
	Mal(Container contentPane) {
		//�� �̹���
		PlayerOneMal = new ImageIcon("GUI\\char1.png"); 
		PlayerTwoMal = new ImageIcon("GUI\\char2.png");
		setPlayerList(PlayerOneList,PlayerOneMal,600, "Player 1 Mal ",
				contentPane,PlayerOneMalPosition,OriginPlayerOneMalPosition);
		setPlayerList(PlayerTwoList,PlayerTwoMal,800,"Player 2 Mal ",
				contentPane,PlayerTwoMalPosition,OriginPlayerTwoMalPosition);
		
		for(int i = 0 ; i < 4 ; i++) {
			P1Sum[i] = new ArrayList<Integer>();
			P2Sum[i] = new ArrayList<Integer>();
		}
		
		P1_Mal_Sum[2] = new ImageIcon("GUI\\char1_2.png"); 
		P1_Mal_Sum[3] = new ImageIcon("GUI\\char1_3.png");
		P1_Mal_Sum[4] = new ImageIcon("GUI\\char1_4.png");
		
		P2_Mal_Sum[2] = new ImageIcon("GUI\\char2_2.png"); 
		P2_Mal_Sum[3] = new ImageIcon("GUI\\char2_3.png");
		P2_Mal_Sum[4] = new ImageIcon("GUI\\char2_4.png");
		
	}
	
	public void setPlayerList(JButton[] playerList, ImageIcon playerMalImg, int row, 
				String MalName,Container contentPane, int [][] MalPosition, int [][]OriginMalPosition) {
		// TODO Auto-generated method stub
		for(int i=0; i<4; i++) {
			playerList[i] = new JButton(MalName + Integer.toString(i+1),playerMalImg);
			playerList[i].setBorderPainted(false);
			playerList[i].setFocusPainted(false);
			playerList[i].setContentAreaFilled(false);
			playerList[i].setBounds(628+(i*50), row, CharWidthSize, CharHeightSize);
			MalPosition[0][i] = OriginMalPosition[0][i] = 628+(i*50);
			MalPosition[1][i] = OriginMalPosition[1][i] = row;
			//playerList[i].addActionListener(this);
			contentPane.add(playerList[i]);
		}
	}
}
