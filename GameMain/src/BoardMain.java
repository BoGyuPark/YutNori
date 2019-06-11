import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class BoardMain extends JFrame implements ActionListener{
	
	//public JButton MalPosition[] = new JButton[38];
	public HashMap<String, Integer> YutHash = new HashMap<String, Integer>();
	ImageIcon Mark = new ImageIcon("GUI\\Mark.png");
	

//	JLabel[] MarkMalPos = new JLabel[40];
//	JLabel[] MarkChar = new JLabel[40];
	JButton[] MarkMalPos = new JButton[40];
	JLabel[] MarkChar = new JLabel[40];
	ArrayList<Integer> P1NextIdx;
	ArrayList<Integer> P2NextIdx;
	
	int tempCnt=0;
	
	//�ش� ��ġ���� ������ ���ǹ�ȣ�� ����
	int NextPossibleMalPos[][] = {
		  //��,��,��,��,��,����,����
			{1,2,3,4,5,-2,-1},		//0��°, -2�� ��� �� �ѱ��
			{2,3,4,5,6,29,-1},		//1��°, -1�� continue
			{3,4,5,6,7,1,-1},		//2
			{4,5,6,7,8,2,-1},		//3
			{5,6,7,8,9,3,-1},		//4
			{20,21,22,23,24,4,-1},	//5
			
			{7,8,9,10,11,5,-1},		//6
			{8,9,10,11,12,6,-1},		//7
			{9,10,11,12,13,7,-1},		//8
			{10,11,12,13,14,8,-1},		//9
			{25,26,22,27,28,9},			//10
			
			{12,13,14,15,16,10,-1},		//11
			{13,14,15,16,17,11,-1},		//12
			{14,15,16,17,18,12,-1},		//13
			{15,16,17,18,19,13,-1},		//14
			{16,17,18,19,29,14,-1},		//15
			
			{17,18,19,29,38,15,-1},		//16
			{18,19,29,38,38,16,-1},		//17
			{19,29,38,38,38,17,-1},		//18
			{29,38,38,38,38,18,-1},		//19
			{21,22,23,24,15,5,-1},		//20
			
			{22,23,24,15,16,20,-1},		//21
			{27,28,29,38,38,21,26},		//22
			{24,15,16,17,18,22,-1},		//23
			{15,16,17,18,19,23,-1},		//24
			{26,22,27,28,29,10,-1},		//25
			
			{22,27,28,29,38,25,-1},		//26
			{28,29,38,38,38,22,-1},		//27
			{29,38,38,38,38,27,-1},		//28
			{38,38,38,38,38,19,28}		//29
	};
	
	public int BoardPosition[][] = new int[2][40];
	JTextArea SituationTextArea;
	final int MarkWidthSize=27, MarkHeightSize=28;
	Player playerOne, playerTwo;
	Container contentPane;
	Yut yut;
	public Mal mal;		//PlayerOneMal, PlayerTwoMal
	YutPan yutPan;
	
	public int CurP1Idx, CurP2Idx;
	public int CurSelP1MalPos, CurSelP2MalPos;
	//true : Player One, False : Player Two
    boolean PlayerFlag = true;
	
	public BoardMain(){
		//�ؽ� ��
		YutHash.put("��", 0);
		YutHash.put("��", 1);
		YutHash.put("��", 2);
		YutHash.put("��", 3);
		YutHash.put("��", 4);
		YutHash.put("����", 5);
		
		//������ ��ư ��ġ ����
		setBoardPosition();
		
		setTitle("Set Board");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		contentPane = this.getContentPane();
		
		MoveActionListener MAListener = new MoveActionListener();
		
		//ĳ���� �����Ҷ� ��ũ : MarkChar, �̵��� ��ġ ��ũ ��ư ���� : MarkMalPos
		for(int i=0; i<39; i++) {
			MarkChar[i] = new JLabel(Mark);
			if(i>=30 && i<=37) {
				MarkChar[i].setBounds(BoardPosition[0][i]+5,BoardPosition[1][i]-21,MarkWidthSize,MarkHeightSize);	
			}else {
				MarkChar[i].setBounds(BoardPosition[0][i]+15,BoardPosition[1][i]+5,MarkWidthSize,MarkHeightSize);
			}
			contentPane.add(MarkChar[i]);
			MarkChar[i].setVisible(false);
			
			
			if(i>=30 && i<=37) continue;
			MarkMalPos[i] = new JButton(Mark);
			MarkMalPos[i].setText(Integer.toString(i));
			MarkMalPos[i].setFont(new Font("�ü�",Font.BOLD,0));
			MarkMalPos[i].setBounds(BoardPosition[0][i]+15,BoardPosition[1][i]+21,MarkWidthSize,MarkHeightSize);
			
			MarkMalPos[i].setBorderPainted(false);
			MarkMalPos[i].setFocusPainted(false);
			MarkMalPos[i].setContentAreaFilled(false);
			
			MarkMalPos[i].addActionListener(MAListener);
			
			contentPane.add(MarkMalPos[i]);
			MarkMalPos[i].setVisible(false);
		}
		                      		
		yut = new Yut();
		mal = new Mal(contentPane);
		yutPan = new YutPan();
		playerOne = new Player("Player One State",550);
		playerTwo = new Player("Player Two State",750);
		
		//��ü GUI�� ���� Panel
		JPanel panel_Board = new JPanel();
		JPanel panel_YutPan = new JPanel();
		JPanel panel_Chat = new JPanel();

		//��� �̹���
		ImageIcon boardImg = new ImageIcon("GUI\\BigBoard.png");
		ImageIcon YutPanImg = new ImageIcon("GUI\\NewYutPan.png");
		ImageIcon YutChatImg = new ImageIcon("GUI\\Han.jpg");
		
		JLabel labelImageBoardImg = new JLabel(boardImg);
		JLabel labelImageYutPanImg = new JLabel(YutPanImg);
		JLabel labelImageYutChatImg = new JLabel(YutChatImg);
		
		
		P1ActionListener P1Listenr = new P1ActionListener();
		P2ActionListener P2Listenr = new P2ActionListener();

		//�÷��̾��� �� ��
		for(int i=0; i<4; i++) {
			mal.PlayerOneList[i].addActionListener(P1Listenr);
			mal.PlayerTwoList[i].addActionListener(P2Listenr);
		}
		
		//�� ������ ��ư �׼�
		yutPan.YutButton.addActionListener(this);
		
		
		//�ؽ�Ʈ ȭ��
		SituationTextArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(SituationTextArea);
		scrollPane.setBounds(25, 550, 600, 400);
		
		
		//�÷��̾� ���� �� 
		contentPane.add(playerOne.PlayerState);
		contentPane.add(playerTwo.PlayerState);
		
		panel_Board.setBounds(25,40, 799,506);
		panel_Board.add(labelImageBoardImg);

		panel_YutPan.setBounds(799, 80, 450, 421);
		panel_YutPan.add(labelImageYutPanImg);

		panel_Chat.setBounds(799+25,506+40, 426,454);
		panel_Chat.add(labelImageYutChatImg);
		
		for(int i=0; i<4; i++) { contentPane.add(yut.YutImg[i]);	}
		contentPane.add(scrollPane);
		contentPane.add(yutPan.YutButton);
		
		contentPane.add(panel_Board);
		contentPane.add(panel_YutPan);
		contentPane.add(panel_Chat);
		
		setLocation(50,20);
		setSize(1250,1000);
		setVisible(true);

	}
	

	private void setBoardPosition() {
		// TODO Auto-generated method stub
		BoardPosition[0][0]=700; BoardPosition[1][0]=390;
		BoardPosition[0][1]=682; BoardPosition[1][1]=305;
		BoardPosition[0][2]=660; BoardPosition[1][2]=230;
		BoardPosition[0][3]=640; BoardPosition[1][3]=165;
		BoardPosition[0][4]=625; BoardPosition[1][4]=105;
		BoardPosition[0][5]=610; BoardPosition[1][5]=40;
		
		BoardPosition[0][6]=525; BoardPosition[1][6]=40;
		BoardPosition[0][7]=440; BoardPosition[1][7]=40;
		BoardPosition[0][8]=360; BoardPosition[1][8]=40;
		BoardPosition[0][9]=280; BoardPosition[1][9]=40;
		BoardPosition[0][10]=200; BoardPosition[1][10]=40;

		BoardPosition[0][11]=185; BoardPosition[1][11]=105;
		BoardPosition[0][12]=170; BoardPosition[1][12]=165;
		BoardPosition[0][13]=155; BoardPosition[1][13]=230;
		BoardPosition[0][14]=135; BoardPosition[1][14]=310;
		BoardPosition[0][15]=120; BoardPosition[1][15]=400;
		
		BoardPosition[0][16]=230; BoardPosition[1][16]=400;
		BoardPosition[0][17]=350; BoardPosition[1][17]=400;
		BoardPosition[0][18]=467; BoardPosition[1][18]=400;
		BoardPosition[0][19]=585; BoardPosition[1][19]=400;
		
		BoardPosition[0][20]=545; BoardPosition[1][20]=100;
		BoardPosition[0][21]=485; BoardPosition[1][21]=140;
		BoardPosition[0][22]=410; BoardPosition[1][22]=190;
		BoardPosition[0][23]=315; BoardPosition[1][23]=255;
		BoardPosition[0][24]=220; BoardPosition[1][24]=325;
		
		BoardPosition[0][25]=260; BoardPosition[1][25]=90;
		BoardPosition[0][26]=330; BoardPosition[1][26]=140;
		BoardPosition[0][27]=495; BoardPosition[1][27]=255;
		BoardPosition[0][28]=595; BoardPosition[1][28]=325;
		
		//�ѹ��� ������ ��� ��ġ
		BoardPosition[0][29]=700; BoardPosition[1][29]=392;
		
		//��⸻�� ��ġ
		BoardPosition[0][30]=628; BoardPosition[1][30]=600;
		BoardPosition[0][31]=678; BoardPosition[1][31]=600;
		BoardPosition[0][32]=728; BoardPosition[1][32]=600;
		BoardPosition[0][33]=778; BoardPosition[1][33]=600;

		BoardPosition[0][34]=628; BoardPosition[1][34]=800;
		BoardPosition[0][35]=678; BoardPosition[1][35]=800;
		BoardPosition[0][36]=728; BoardPosition[1][36]=800;
		BoardPosition[0][37]=778; BoardPosition[1][37]=800;
		
		//���� ��ġ
		BoardPosition[0][38]=700; BoardPosition[1][38]=460;
	}


	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String name;
	    name = ae.getActionCommand();
	    Random random = new Random();
	    int[] yutResult = new int[4];
	    int yutBackCnt=0;
	    String[] YutStringList = {"��","��","��","��","��"};
	    boolean BackDoFlag = false;
	    
	    
	    //Player One
	    if(PlayerFlag == true) {   	
	    	//������ �� �ִ� ���� ǥ��������
			//������Ʈ�� ���鼭 �� ��ġ�� ȭ��ǥ ǥ��������
	    	MarkSelectChar(true,1);
			
			if(CheckRemainCnt(playerOne)) {	 	//���� ��ȸ Check
				//�� ������ ��ư Ŭ��
		    	if(name.equals("�� ������")) {
		 	    	for(int i=0; i<4; i++) {
		 	    		yutResult[i] = random.nextInt(2);
		 	    		if(yutResult[i]== 0) yutBackCnt++;
		 		    	// 0�϶� �Ʒ���, 1�϶� ����
		 		    	if(i == 0 && yutResult[i] == 0) {
		 		    		yut.YutImg[i].setIcon(yut.YutBackDo);
		 		    		BackDoFlag = true;
		 		    	}else {
		 		    		if(yutResult[i] == 0) { yut.YutImg[i].setIcon(yut.YutBack);	}
		 		    		else {	yut.YutImg[i].setIcon(yut.YutFront); }
		 		    	}
		 		    }
		    		
		 	    	String YutResultString = "";
		 	    	if(yutBackCnt==1) {
		 	    		YutResultString = (BackDoFlag==true ? "����" : "��");
		 	    	}else {
		 	    		YutResultString = YutStringList[yutBackCnt];
		 	    	}
		 	    	
		 	    	if(tempCnt == 0) {
			 	    	YutResultString = "��";
		 	    	}else if(tempCnt == 2) {
			 	    	YutResultString = "��";
		 	    	}else if(tempCnt == 4) {
			 	    	YutResultString = "��";
		 	    	}else if(tempCnt == 6) {
			 	    	YutResultString = "��";
		 	    	}
		 	    	tempCnt++;

		 	    	
		 	    	SituationTextArea.append(YutResultString + "\n");
		 	    	SituationTextArea.setCaretPosition(SituationTextArea.getDocument().getLength());  // �ǾƷ��� ��ũ���Ѵ�.
		 	    	
		 	    	playerOne.YutResultList.add(YutResultString);
		 	    	
		 	    	//�� �ѹ� ������ (��ȸ ����)
		 	    	playerOne.RemainCnt--;
		 	    	
		 	    	//��,�� �̻� ������ ��� �÷��̾��� ���� ��ȸ ++
		 	    	if(YutResultString.equals("��") || YutResultString.equals("��")) {
						SituationTextArea.append("P1�� ���� �ѹ� �� �������� \n");
		 	    		playerOne.RemainCnt++;
		 	    	}
		 	    	
		    	}
			
			}
		
	    }
	    //Player Two
	    else {
	    	
	    	MarkSelectChar(true,2);
			
			if(CheckRemainCnt(playerTwo)) {	 	//���� ��ȸ Check
				//�� ������ ��ư Ŭ��
		    	if(name.equals("�� ������")) {
		 	    	for(int i=0; i<4; i++) {
		 	    		yutResult[i] = random.nextInt(2);
		 	    		if(yutResult[i]== 0) yutBackCnt++;
		 		    	// 0�϶� �Ʒ���, 1�϶� ����
		 		    	if(i == 0 && yutResult[i] == 0) {
		 		    		yut.YutImg[i].setIcon(yut.YutBackDo);
		 		    		BackDoFlag = true;
		 		    	}else {
		 		    		if(yutResult[i] == 0) { yut.YutImg[i].setIcon(yut.YutBack);	}
		 		    		else {	yut.YutImg[i].setIcon(yut.YutFront); }
		 		    	}
		 		    }
		 	    	
		 	    	String YutResultString = "";
		 	    	if(yutBackCnt==1) {
		 	    		YutResultString = (BackDoFlag==true ? "����" : "��");
		 	    	}else {
		 	    		YutResultString = YutStringList[yutBackCnt];
		 	    	}
		 	    	
		 	    	
		 	    	if(tempCnt == 1) {
			 	    	YutResultString = "����";
		 	    	}else if(tempCnt == 3) {
			 	    	YutResultString = "����";
		 	    	}else if(tempCnt == 5) {
			 	    	YutResultString = "����";
		 	    	}
		 	    	else if(tempCnt == 7) {
			 	    	YutResultString = "��";
		 	    	}
		 	    	tempCnt++;

		 	    	SituationTextArea.append(YutResultString + "\n");
		 	    	SituationTextArea.setCaretPosition(SituationTextArea.getDocument().getLength());  // �ǾƷ��� ��ũ���Ѵ�.
		 	    	
		 	    	playerTwo.YutResultList.add(YutResultString);
		 	    	
		 	    	//�� �ѹ� ������ (��ȸ ����)
		 	    	playerTwo.RemainCnt--;
		 	    	
		 	    	//��,�� �̻� ������ ��� �÷��̾��� ���� ��ȸ ++
		 	    	if(YutResultString.equals("��") || YutResultString.equals("��")) {
						SituationTextArea.append("P2�� ���� �ѹ� �� �������� \n");
		 	    		playerTwo.RemainCnt++;
		 	    	}
		 	    	
		    	}
		    	
			}
	    	 	
	    }
	   
	 }
	
	private class MoveActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			//System.out.println(e.getActionCommand());
			
			b.setVisible(false);
			//������ �̵��� �� ��ġ
			int nextIdx = Integer.parseInt(e.getActionCommand());
			
			//P1
			if(PlayerFlag == true) {
				
				//������ġ�� �ٷ� �̵�
				int NextMalNum = OverLapCheck(nextIdx, mal.PlayerOneMalPosition);
				int OpponentNum = OppOverLapCheck(nextIdx, mal.PlayerTwoMalPosition);
				
				//���� ���� ��ġ�� �ʴ� ���
				if( NextMalNum == -1) {
					
					//���� ���� ��ġ�� ������ ���� ���� ��� ���
					if(OpponentNum != -1) 
					{
						//���� ���� ��� �� ��ġ�� �̵� �����ش�
						mal.PlayerTwoList[OpponentNum].setLocation(mal.OriginPlayerTwoMalPosition[0][OpponentNum], 
								mal.OriginPlayerTwoMalPosition[1][OpponentNum]);
						
						
						mal.PlayerTwoMalPosition[0][OpponentNum] = mal.OriginPlayerTwoMalPosition[0][OpponentNum];
						mal.PlayerTwoMalPosition[1][OpponentNum] = mal.OriginPlayerTwoMalPosition[1][OpponentNum];
						
						//��� ���� ���� ������ ���� ��� �ʱ�ȭ ���ش�.
						
						//�� �̹��� ����
						mal.PlayerTwoList[OpponentNum].setIcon(mal.PlayerTwoMal);
						for(int j : mal.P1Sum[OpponentNum]) {
							mal.PlayerTwoList[j].setIcon(mal.PlayerTwoMal);
						}
						
						//GoalIn�� 0���� �������ش�.
						mal.PlayerTwoGoalIn[OpponentNum] = 0;
						for(int j : mal.P2Sum[OpponentNum]) {
							mal.PlayerTwoGoalIn[j] = 0;
							mal.PlayerTwoList[j].setVisible(true);
						}
						
						mal.P2Sum[OpponentNum].clear();
						//GoalIn�� 0���� �������ش�.
						mal.PlayerTwoGoalIn[OpponentNum] = 0;
						
						//���� ��ȸ�� �߰��� �ش�.
						playerOne.RemainCnt++;
					}

					//���� ��, ��� ���� �ƴ� ���
					mal.PlayerOneList[CurP1Idx].setLocation(BoardPosition[0][nextIdx], BoardPosition[1][nextIdx]);
					mal.PlayerOneMalPosition[0][CurP1Idx] = BoardPosition[0][nextIdx];
					mal.PlayerOneMalPosition[1][CurP1Idx] = BoardPosition[1][nextIdx];
	
				}
				//���� ��ġ�� ���� ��ġ��
				else {
					
					//���� ���ǿ� �ִ� ���� �߰�
					mal.P1Sum[NextMalNum].add(CurP1Idx);
					
					//�̵� �Ϸ��� ���� ������ �� ���� ��� ���Ѵ�.
					for(int j : mal.P1Sum[CurP1Idx]) {
						mal.P1Sum[NextMalNum].add(j);
					}
					mal.P1Sum[CurP1Idx].clear();
					
					//�߰��� ������ ���� ���� ���ǿ� �ִ� ���� �̹����� ����
//					SituationTextArea.append("���� �ִ� �� ��ȣ: " + NextMalNum + "\n");
//					SituationTextArea.append("������ ����: " + mal.P1Sum[NextMalNum].size() + "\n");
					mal.PlayerOneGoalIn[CurP1Idx] = -1;		//������ �ǹ̷� -1
					
					mal.PlayerOneList[CurP1Idx].setLocation(mal.OriginPlayerOneMalPosition[0][CurP1Idx],
							mal.OriginPlayerOneMalPosition[1][CurP1Idx]);
					
					mal.PlayerOneMalPosition[0][CurP1Idx] = mal.OriginPlayerOneMalPosition[0][CurP1Idx];
					mal.PlayerOneMalPosition[1][CurP1Idx] = mal.OriginPlayerOneMalPosition[1][CurP1Idx];
					
					mal.PlayerOneList[CurP1Idx].setVisible(false);
					mal.PlayerOneList[NextMalNum].setIcon(mal.P1_Mal_Sum[mal.P1Sum[NextMalNum].size()+ 1]);
					
				}
				
						
				//���� �� �� ��� Ȯ��
				String[] t = {"��","��","��","��","��","����"};
				int i;
				for (i=0; i<6; i++) {
					if(NextPossibleMalPos[CurSelP1MalPos][i]==nextIdx) {
						//SituationTextArea.append("P1�� ������ �� ��� : " + t[i] + "\n");
						break;
					}
				}
				
				//���� �� �� ��� ����
				playerOne.YutResultList.remove(t[i]);
				
				//�� ��� ���� �� ������� ���
				SituationTextArea.append("P1 ���� �� : ");
				for(String s : playerOne.YutResultList) {
					SituationTextArea.append(s + " ");
				}
				SituationTextArea.append("\n");
				
				
				//���� �̵��� ���� ��ȣ ���
				//SituationTextArea.append("P1�� ������ �� : " + nextIdx + "\n");
				
				
				//�����ߴ� ��ǥ ��ũ�� ���ش�.
	 	    	for(int possiblePos : P1NextIdx) {
	 	    		MarkMalPos[possiblePos].setVisible(false);
	 	    	}
				
				//P1�� Goal in
				if(nextIdx == 38) {
					int CurGoalInCnt = mal.P1Sum[CurP1Idx].size() + 1;
					playerOne.GoalInMalCnt += CurGoalInCnt;
					mal.PlayerOneGoalIn[CurP1Idx] = 1;
					for(int j : mal.P1Sum[CurP1Idx]) {
						mal.PlayerOneGoalIn[j] = 1;
					}
				}
				
				//���� ȭ��
				if(playerOne.GoalInMalCnt == 4) {
					SituationTextArea.append("Player 1 Win!!!!!!!!!!!!!!!!  \n");
					JOptionPane.showMessageDialog(null, "Player 1 Win!!", "������ ����", JOptionPane.INFORMATION_MESSAGE);
				}
				
				//���� �̵��� �� �ִ� ��ȸ�� �� �ִ� ���
				if(playerOne.RemainCnt > 0){
					SituationTextArea.append("���� �� �ִ� ��ȸ�� "+ playerOne.RemainCnt + "ȸ ���ҽ��ϴ�. \n");
					if(playerOne.YutResultList.size() > 0) {
						MarkSelectChar(true,1);
					}
				}
				else {
					if(playerOne.YutResultList.size() > 0) {
						MarkSelectChar(true,1);
					}
					else {
						playerOne.RemainCnt=1;
						playerOne.YutResultList.clear();
						PlayerFlag = false;
					}
				}

			}
///////////////////////P2/////////////////////////////////////////////////////////
			else {
				//������ġ�� �ٷ� �̵�
				int NextMalNum = OverLapCheck(nextIdx, mal.PlayerTwoMalPosition);
				int OpponentNum = OppOverLapCheck(nextIdx, mal.PlayerOneMalPosition);

				if( NextMalNum == -1) {
					
					//���� ���� ��ġ�� ������ ���� ���� ��� ���
					if(OpponentNum != -1) 
					{
						//���� ���� ��� �� ��ġ�� �̵� �����ش�
						mal.PlayerOneList[OpponentNum].setLocation(mal.OriginPlayerOneMalPosition[0][OpponentNum], 
								mal.OriginPlayerOneMalPosition[1][OpponentNum]);
						
						mal.PlayerOneMalPosition[0][OpponentNum] = mal.OriginPlayerOneMalPosition[0][OpponentNum];
						mal.PlayerOneMalPosition[1][OpponentNum] = mal.OriginPlayerOneMalPosition[1][OpponentNum];
						
						//��� ���� ���� ������ ���� ��� �ʱ�ȭ ���ش�.
						
						//�� �̹��� ����
						mal.PlayerOneList[OpponentNum].setIcon(mal.PlayerOneMal);
						for(int j : mal.P1Sum[OpponentNum]) {
							mal.PlayerOneList[j].setIcon(mal.PlayerOneMal);
						}
						
						//GoalIn�� 0���� �������ش�.
						mal.PlayerOneGoalIn[OpponentNum] = 0;
						for(int j : mal.P1Sum[OpponentNum]) {
							mal.PlayerOneGoalIn[j] = 0;
							mal.PlayerOneList[j].setVisible(true);
						}
						
						//��� ���� ���� �ʱ�ȭ
						mal.P1Sum[OpponentNum].clear();

						//���� ��ȸ�� �߰��� �ش�.
						playerTwo.RemainCnt++;
					}
					
					mal.PlayerTwoList[CurP2Idx].setLocation(BoardPosition[0][nextIdx], BoardPosition[1][nextIdx]);
					mal.PlayerTwoMalPosition[0][CurP2Idx] = BoardPosition[0][nextIdx];
					mal.PlayerTwoMalPosition[1][CurP2Idx] = BoardPosition[1][nextIdx];
					
				}
				//���� ��ġ�� ���� ��ġ��
				else {
					
					//���� ���ǿ� �ִ� ���� �߰�
					mal.P2Sum[NextMalNum].add(CurP2Idx);
					
					//�̵� �Ϸ��� ���� ������ �� ���� ��� ���Ѵ�.
					for(int j : mal.P2Sum[CurP2Idx]) {
						mal.P2Sum[NextMalNum].add(j);
					}
					
					//�߰��� ������ ���� ���� ���ǿ� �ִ� ���� �̹����� ����
//					SituationTextArea.append("���� �ִ� �� ��ȣ: " + NextMalNum + "\n");
//					SituationTextArea.append("������ ����: " + mal.P2Sum[NextMalNum].size() + "\n");
					
					mal.PlayerTwoGoalIn[CurP2Idx] = -1;		//������ �ǹ̷� -1
					
					mal.PlayerTwoList[CurP2Idx].setLocation(mal.OriginPlayerTwoMalPosition[0][CurP2Idx],
							mal.OriginPlayerTwoMalPosition[1][CurP2Idx]);
					
					mal.PlayerTwoMalPosition[0][CurP2Idx] = mal.OriginPlayerTwoMalPosition[0][CurP2Idx];
					mal.PlayerTwoMalPosition[1][CurP2Idx] = mal.OriginPlayerTwoMalPosition[1][CurP2Idx];
					
					mal.PlayerTwoList[CurP2Idx].setVisible(false);
					mal.PlayerTwoList[NextMalNum].setIcon(mal.P2_Mal_Sum[mal.P2Sum[NextMalNum].size()+ 1]);

				}
				

				/////////////////////////////////////////////////////////////////////////////////////////
				//���� �� �� ��� Ȯ��
				String[] t = {"��","��","��","��","��","����"};
				int i;
				for (i=0; i<6; i++) {
					if(NextPossibleMalPos[CurSelP2MalPos][i]==nextIdx) {
						//SituationTextArea.append("P2�� ������ �� ��� : " + t[i] + "\n");
						break;
					}
				}
				
				//���� �� �� ��� ����
				playerTwo.YutResultList.remove(t[i]);
				
				//�� ��� ���� �� ������� ���
				SituationTextArea.append("P2 ���� �� : ");
				for(String s : playerTwo.YutResultList) {
					SituationTextArea.append(s + " ");
				}
				SituationTextArea.append("\n");

				/////////////////////////////////////////////////////////////////////////////////////////

				//���� �̵��� ���� ��ȣ ���
				//SituationTextArea.append("P2�� ������ �� : " + nextIdx + "\n");
				
				//�����ߴ� ��ǥ ��ũ�� ���ش�.
	 	    	for(int possiblePos : P2NextIdx) {
	 	    		MarkMalPos[possiblePos].setVisible(false);
	 	    	}
				
				//P2�� Goal in
				if(nextIdx==38) {					
					int CurGoalInCnt = mal.P2Sum[CurP2Idx].size() + 1;
					playerTwo.GoalInMalCnt += CurGoalInCnt;
					mal.PlayerTwoGoalIn[CurP2Idx] = 1;
					for(int j : mal.P2Sum[CurP2Idx]) {
						mal.PlayerTwoGoalIn[j] = 1;
					}
				}
				
				//���� ȭ��
				if(playerTwo.GoalInMalCnt == 4) {
					SituationTextArea.append("Player 2 Win!!!!!!!!!!!!!!!!  \n");
					JOptionPane.showMessageDialog(null, "Player 2 Win!!", "������ ����", JOptionPane.INFORMATION_MESSAGE);
				}
				////////////////////////////////////////////////////////////////////////////////
				//���� �̵��� �� �ִ� ��ȸ�� �� �ִ� ���
				
				if(playerTwo.RemainCnt > 0){
					SituationTextArea.append("���� �� �ִ� ��ȸ�� "+ playerOne.RemainCnt + "ȸ ���ҽ��ϴ�. \n");
					if(playerTwo.YutResultList.size() > 0) {
						MarkSelectChar(true,2);
					}
				}
				else {
					if(playerTwo.YutResultList.size() > 0) {
						MarkSelectChar(true,2);
					}else {
						playerTwo.RemainCnt=1;
						playerTwo.YutResultList.clear();
						PlayerFlag = true;
					}
				}
				
				/////////////////////////////////////////////////////////////////////////////

			}
			
			
		}
		
		
	}
	
	//���� �̵��� ��ġ�� �̹� �÷��̾ �ִ� ��� üũ
	private int OverLapCheck(int NextPlayerIdx, int PlayerMalPosition[][]) {
		// TODO Auto-generated method stub
		for(int i=0; i<4; i++) {
			if(NextPlayerIdx == i) continue;
			if(BoardPosition[0][NextPlayerIdx] == PlayerMalPosition[0][i]
					&& BoardPosition[1][NextPlayerIdx] == PlayerMalPosition[1][i] ) {
				return i;
			}
		}
		return -1;
	}
	
	public int OppOverLapCheck(int NextPlayerIdx, int[][] PlayerMalPosition) {
		// TODO Auto-generated method stub
		for(int i=0; i<4; i++) {
			if(BoardPosition[0][NextPlayerIdx] == PlayerMalPosition[0][i]
					&& BoardPosition[1][NextPlayerIdx] == PlayerMalPosition[1][i] ) {
				return i;
			}
		}
		return -1;
	}

	private class P1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			
			JButton jb = (JButton)ae.getSource();
 	    	//Player One�� ���� �����ؾ� �Ѵ�.
 	    	if(jb.equals(mal.PlayerOneList[0]) || jb.equals(mal.PlayerOneList[1])
 	    			||jb.equals(mal.PlayerOneList[2])||jb.equals(mal.PlayerOneList[3])) {
 	    		//SituationTextArea.append("Player 1 select : " + jb.getActionCommand() + "\n");
	 	    	SituationTextArea.setCaretPosition(SituationTextArea.getDocument().getLength());  // �ǾƷ��� ��ũ���Ѵ�.
	 	    		
	 	    	//���° ������ ã��
	 	    	int MalNum=0;
	 	    	for(int i=0; i<4; i++) {
	 	    		if(jb.equals(mal.PlayerOneList[i])) {
	 	    			MalNum=i;
	 	    			break;
	 	    		}
	 	    	}
	 	    	
	 	    	CurP1Idx = MalNum;
	 	    	
	 	    	int CurIdx = getMalPositionIdx(MalNum, 1);

	 	    	//���� �����Ͽ����� �����Ҽ� �ִ� �� ǥ�ø� ���ش�.
	 	    	MarkSelectChar(false,1);
	 	    	
	 	    	//������ ���� ������ ��ġ�� �˷�����
	 	    	P1NextIdx = possiblePosition(CurIdx, playerOne.YutResultList,1);		//���� ���� ��ǥ, �������
	 	    	
	 	    	int cnt=0;		//�̵��Ҽ� �ִ� ��찡 �ϳ��� ���� ��� 
	 	    	for(int possiblePos : P1NextIdx) {
	 	    		MarkMalPos[possiblePos].setVisible(true);
	 	    		cnt++;
	 	    	}
	 	    	
	 	    	//ó������ ������ ���
	 	    	if(cnt == 0) {
	 	    		playerOne.RemainCnt=1;
					playerOne.YutResultList.clear();
					PlayerFlag = false;
	 	    	}
	 	    	
	 	    	//System.out.println(ae.getSource());
 	    	}
			
			
		}
	}
	
	private class P2ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			JButton jb = (JButton)ae.getSource();
 	    	//Player Two�� ���� �����ؾ� �Ѵ�.
 	    	if(jb.equals(mal.PlayerTwoList[0]) || jb.equals(mal.PlayerTwoList[1])
 	    			||jb.equals(mal.PlayerTwoList[2])||jb.equals(mal.PlayerTwoList[3])) {
 	    		//SituationTextArea.append("Player 2 select : " + jb.getActionCommand() + "\n");
	 	    	SituationTextArea.setCaretPosition(SituationTextArea.getDocument().getLength());  // �ǾƷ��� ��ũ���Ѵ�.
	 	    		 	    	
	 	    	int MalNum=0;
	 	    	for(int i=0; i<4; i++) {
	 	    		if(jb.equals(mal.PlayerTwoList[i])) {
	 	    			MalNum=i;
	 	    			break;
	 	    		}
	 	    	}
	 	    	CurP2Idx = MalNum;
	 	    	int CurIdx = getMalPositionIdx(MalNum,2);
	 	    	
	 	    	//���� �����Ͽ����� �����Ҽ� �ִ� �� ǥ�ø� ���ش�.
	 	    	MarkSelectChar(false,2);
	 	    	
	 	    	
	 	    	//������ ���� ������ ��ġ�� �˷�����
	 	    	P2NextIdx = possiblePosition(CurIdx, playerTwo.YutResultList,2);		//���� ���� ��ǥ, �������
	 	    	int cnt=0;
	 	    	for(int possiblePos : P2NextIdx) {
	 	    		MarkMalPos[possiblePos].setVisible(true);
	 	    		cnt++;
	 	    	}
	 	    	
	 	    	//ó������ ������ ���
	 	    	if(cnt == 0) {
	 	    		playerTwo.RemainCnt=1;
					playerTwo.YutResultList.clear();
					PlayerFlag = true;
	 	    	}
	 	    	
	 	    	//System.out.println(ae.getSource());
 	    	}
	
		}
	}
	
	
	private void MarkSelectChar(boolean flag, int playerNum) {
		if(flag==true) {
			//SituationTextArea.append("���� ������ ���� ��ġ : ");
			for(int i=0; i<4; i++) {
				int curMalIdx = getMalPositionIdx(i,playerNum);
				
				//������ ��
				if(curMalIdx == -1) continue;
				//System.out.println("������ ���� ��ġ �� : " + curMalIdx);
				//SituationTextArea.append(curMalIdx + ", ");
				MarkChar[curMalIdx].setVisible(flag);
//				if(curMalIdx != 29) {
//					MarkChar[curMalIdx].setVisible(flag);
//				}
			}
			//SituationTextArea.append("\n");
		}
		
		else {
			for(int i=0; i<4; i++) {
				int curMalIdx = getMalPositionIdx(i,playerNum);
				
				//������ ��
				if(curMalIdx == -1) continue;
				MarkChar[curMalIdx].setVisible(flag);
//				if(curMalIdx != 29) {
//					MarkChar[curMalIdx].setVisible(flag);
//				}
			}
		}
		
	}

	private ArrayList<Integer> possiblePosition(int curIdx, ArrayList<String> yutResultList, int PlayerNum) {

		ArrayList<Integer> possible = new ArrayList<>();
		//System.out.println("curIdx : "+curIdx);
		for(String value : yutResultList) {
		    		
    		if(PlayerNum==1) {
    			// P1
        		//���� ��ġ�� ��� ���̶�� ������ 0
        		if(curIdx == 30 || curIdx == 31 || curIdx == 32 || curIdx == 33) {
        			curIdx = 0;
        		}
        		//���� �ΰ��
        		if(YutHash.get(value) == 5) {
        			for(int i=0; i<2; i++) {
        				if(NextPossibleMalPos[curIdx][YutHash.get(value) + i] > -1 ) {
                			possible.add(NextPossibleMalPos[curIdx][YutHash.get(value)+ i]);
            			}
        			}
        			
        		}else {
            		possible.add(NextPossibleMalPos[curIdx][YutHash.get(value)]);
        		}
    			
    		}else {
    			// P2
        		//���� ��ġ�� ��� ���̶�� ������ 0
        		if(curIdx == 34 || curIdx == 35 || curIdx == 36 || curIdx == 37) {
        			curIdx = 0;
        		}
        		
        		//���� �ΰ��
        		if(YutHash.get(value) == 5) {
        			for(int i=0; i<2; i++) {
        				if(NextPossibleMalPos[curIdx][YutHash.get(value) + i] > -1 ) {
                			possible.add(NextPossibleMalPos[curIdx][YutHash.get(value)+ i]);
            			}
        			}
        			
        		}else {
            		possible.add(NextPossibleMalPos[curIdx][YutHash.get(value)]);
        		}

    		}
   	
		}
		
		if(PlayerNum==1) {
			//SituationTextArea.append("P1�� ��ġ : "+ curIdx + ", ������ ��ǥ : ");
			CurSelP1MalPos=curIdx;
		}else {
			//SituationTextArea.append("P2�� ��ġ : "+ curIdx);
			CurSelP2MalPos = curIdx;
		}
//		for(int i : possible) {
//			SituationTextArea.append( i + ", ");
//		}
//		SituationTextArea.append("\n");
		
		return possible;
	}

	private int getMalPositionIdx(int malNum, int playerNum) {
		
		if(playerNum==1) {
			if(mal.PlayerOneGoalIn[malNum] != 0) return -1;
			for(int i=0; i< 39; i++) {
				if(mal.PlayerOneMalPosition[0][malNum]==BoardPosition[0][i] &&
						mal.PlayerOneMalPosition[1][malNum]==BoardPosition[1][i]){
					return i;
				}
			}
		}else {
			if(mal.PlayerTwoGoalIn[malNum] != 0) return -1;
			for(int i=0; i< 39; i++) {
				if(mal.PlayerTwoMalPosition[0][malNum]==BoardPosition[0][i] &&
						mal.PlayerTwoMalPosition[1][malNum]==BoardPosition[1][i]){
					return i;
				}
			}
		}
		
		return -1;
	}


	private boolean CheckRemainCnt(Player player) {

		if(player.RemainCnt > 0) return true;
		else return false;
	}

	public static void main(String[] args) {

		BoardMain mf = new BoardMain();
	}
	
	

}
