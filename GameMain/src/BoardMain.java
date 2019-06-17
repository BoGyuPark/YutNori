import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.*;

public class BoardMain extends JFrame implements ActionListener , Serializable{

	final int MarkWidthSize = 27, MarkHeightSize = 28;

	ImageIcon Mark = new ImageIcon("GUI\\Mark.png");
	JButton[] MarkMalPos = new JButton[40];
	JLabel[] MarkChar = new JLabel[40];

	Random random;

	boolean BackDoFlag;
	boolean NakFlag;

	int tempCnt = 0;

	// ���� ��ġ���� ������� ���� ���� ���� ��ġ
	int NextPossibleMalPos[][] = {
			// ��,��,��,��,��,����,����
			{ 1, 2, 3, 4, 5, -2, -1 }, // 0��°, -2�� ��� �� �ѱ��
			{ 2, 3, 4, 5, 6, 29, -1 }, // 1��°, -1�� continue
			{ 3, 4, 5, 6, 7, 1, -1 }, // 2
			{ 4, 5, 6, 7, 8, 2, -1 }, // 3
			{ 5, 6, 7, 8, 9, 3, -1 }, // 4
			{ 20, 21, 22, 23, 24, 4, -1 }, // 5

			{ 7, 8, 9, 10, 11, 5, -1 }, // 6
			{ 8, 9, 10, 11, 12, 6, -1 }, // 7
			{ 9, 10, 11, 12, 13, 7, -1 }, // 8
			{ 10, 11, 12, 13, 14, 8, -1 }, // 9
			{ 25, 26, 22, 27, 28, 9, -1 }, // 10

			{ 12, 13, 14, 15, 16, 10, -1 }, // 11
			{ 13, 14, 15, 16, 17, 11, -1 }, // 12
			{ 14, 15, 16, 17, 18, 12, -1 }, // 13
			{ 15, 16, 17, 18, 19, 13, -1 }, // 14
			{ 16, 17, 18, 19, 29, 14, -1 }, // 15

			{ 17, 18, 19, 29, 38, 15, -1 }, // 16
			{ 18, 19, 29, 38, 38, 16, -1 }, // 17
			{ 19, 29, 38, 38, 38, 17, -1 }, // 18
			{ 29, 38, 38, 38, 38, 18, -1 }, // 19
			{ 21, 22, 23, 24, 15, 5, -1 }, // 20

			{ 22, 23, 24, 15, 16, 20, -1 }, // 21
			{ 27, 28, 29, 38, 38, 21, 26 }, // 22
			{ 24, 15, 16, 17, 18, 22, -1 }, // 23
			{ 15, 16, 17, 18, 19, 23, -1 }, // 24
			{ 26, 22, 27, 28, 29, 10, -1 }, // 25

			{ 22, 27, 28, 29, 38, 25, -1 }, // 26
			{ 28, 29, 38, 38, 38, 22, -1 }, // 27
			{ 29, 38, 38, 38, 38, 27, -1 }, // 28
			{ 38, 38, 38, 38, 38, 19, 28 } // 29
	};

	public int BoardPosition[][] = new int[2][40];
	JTextArea SituationTextArea;

	Player P1, P2;
	Container contentPane;

	Yut yut;
	YutPan yutPan;

	// true : Player One, False : Player Two
	boolean PlayerFlag = true;

	public BoardMain() {

		// ������ ��ư ��ġ ����
		setBoardPosition();

		setTitle("Set Board");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		contentPane = this.getContentPane();
		contentPane.addKeyListener(new MyKeyListener());
		MoveActionListener MAListener = new MoveActionListener();

		// ĳ���� �����Ҷ� ��ũ : MarkChar, �̵��� ��ġ ��ũ ��ư ���� : MarkMalPos
		SetMarker(MAListener);

		yut = new Yut();
		yutPan = new YutPan();
		P1 = new Player("Player One State", 550, contentPane, 1, 600, "Player 1 Mal ");
		P2 = new Player("Player Two State", 750, contentPane, 2, 800, "Player 2 Mal ");

		// ��ü GUI�� ���� Panel
		JPanel panel_Board = new JPanel();
		JPanel panel_YutPan = new JPanel();
		JPanel panel_Chat = new JPanel();

		// ��� �̹���
		ImageIcon boardImg = new ImageIcon("GUI\\BigBoard.png");
		ImageIcon YutPanImg = new ImageIcon("GUI\\NewYutPan.png");
		ImageIcon YutChatImg = new ImageIcon("GUI\\New_kim.jpg");

		JLabel labelImageBoardImg = new JLabel(boardImg);
		JLabel labelImageYutPanImg = new JLabel(YutPanImg);
		JLabel labelImageYutChatImg = new JLabel(YutChatImg);

		PlayerActionListener P1Listenr = new PlayerActionListener();
		PlayerActionListener P2Listenr = new PlayerActionListener();

		// �÷��̾��� �� ��
		for (int i = 0; i < 4; i++) {
			P1.mal.MalList[i].addActionListener(P1Listenr);
			P2.mal.MalList[i].addActionListener(P2Listenr);
		}

		// �� ������ ��ư �׼�
		yutPan.YutButton.addActionListener(this);

		// �ؽ�Ʈ ȭ��
		SituationTextArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(SituationTextArea);
		scrollPane.setBounds(25, 550, 600, 400);

		// �÷��̾� ���� ��
		contentPane.add(P1.PlayerState);
		contentPane.add(P2.PlayerState);

		panel_Board.setBounds(25, 40, 799, 506);
		panel_Board.add(labelImageBoardImg);

		panel_YutPan.setBounds(799, 80, 450, 421);
		panel_YutPan.add(labelImageYutPanImg);

		panel_Chat.setBounds(799 + 25, 506 + 40, 426, 454);
		panel_Chat.add(labelImageYutChatImg);

		for (int i = 0; i < 4; i++) {
			contentPane.add(yut.YutImg[i]);
		}
		contentPane.add(scrollPane);
		contentPane.add(yutPan.YutButton);

		contentPane.add(panel_Board);
		contentPane.add(panel_YutPan);
		contentPane.add(panel_Chat);

		setLocation(50, 20);
		setSize(1250, 1000);
		setVisible(true);

		contentPane.requestFocus();

	}

	private void SetMarker(MoveActionListener MAListener) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 39; i++) {

			MarkChar[i] = new JLabel(Mark);

			if (i >= 30 && i <= 37) {
				MarkChar[i].setBounds(BoardPosition[0][i] + 5, BoardPosition[1][i] - 21, MarkWidthSize, MarkHeightSize);
			} else {
				MarkChar[i].setBounds(BoardPosition[0][i] + 15, BoardPosition[1][i] + 5, MarkWidthSize, MarkHeightSize);
			}
			contentPane.add(MarkChar[i]);
			MarkChar[i].setVisible(false);

			if (i >= 30 && i <= 37)
				continue;

			MarkMalPos[i] = new JButton(Mark);
			MarkMalPos[i].setText(Integer.toString(i));
			MarkMalPos[i].setFont(new Font("�ü�", Font.BOLD, 0));
			MarkMalPos[i].setBounds(BoardPosition[0][i] + 15, BoardPosition[1][i] + 21, MarkWidthSize, MarkHeightSize);

			MarkMalPos[i].setBorderPainted(false);
			MarkMalPos[i].setFocusPainted(false);
			MarkMalPos[i].setContentAreaFilled(false);

			MarkMalPos[i].addActionListener(MAListener);

			contentPane.add(MarkMalPos[i]);
			MarkMalPos[i].setVisible(false);
		}
	}

	private void setBoardPosition() {
		// TODO Auto-generated method stub
		BoardPosition[0][0] = 700;
		BoardPosition[1][0] = 390;
		BoardPosition[0][1] = 682;
		BoardPosition[1][1] = 305;
		BoardPosition[0][2] = 660;
		BoardPosition[1][2] = 230;
		BoardPosition[0][3] = 640;
		BoardPosition[1][3] = 165;
		BoardPosition[0][4] = 625;
		BoardPosition[1][4] = 105;
		BoardPosition[0][5] = 610;
		BoardPosition[1][5] = 40;

		BoardPosition[0][6] = 525;
		BoardPosition[1][6] = 40;
		BoardPosition[0][7] = 440;
		BoardPosition[1][7] = 40;
		BoardPosition[0][8] = 360;
		BoardPosition[1][8] = 40;
		BoardPosition[0][9] = 280;
		BoardPosition[1][9] = 40;
		BoardPosition[0][10] = 200;
		BoardPosition[1][10] = 40;

		BoardPosition[0][11] = 185;
		BoardPosition[1][11] = 105;
		BoardPosition[0][12] = 170;
		BoardPosition[1][12] = 165;
		BoardPosition[0][13] = 155;
		BoardPosition[1][13] = 230;
		BoardPosition[0][14] = 135;
		BoardPosition[1][14] = 310;
		BoardPosition[0][15] = 120;
		BoardPosition[1][15] = 400;

		BoardPosition[0][16] = 230;
		BoardPosition[1][16] = 400;
		BoardPosition[0][17] = 350;
		BoardPosition[1][17] = 400;
		BoardPosition[0][18] = 467;
		BoardPosition[1][18] = 400;
		BoardPosition[0][19] = 585;
		BoardPosition[1][19] = 400;

		BoardPosition[0][20] = 545;
		BoardPosition[1][20] = 100;
		BoardPosition[0][21] = 485;
		BoardPosition[1][21] = 140;
		BoardPosition[0][22] = 410;
		BoardPosition[1][22] = 190;
		BoardPosition[0][23] = 315;
		BoardPosition[1][23] = 255;
		BoardPosition[0][24] = 220;
		BoardPosition[1][24] = 325;

		BoardPosition[0][25] = 260;
		BoardPosition[1][25] = 90;
		BoardPosition[0][26] = 330;
		BoardPosition[1][26] = 140;
		BoardPosition[0][27] = 495;
		BoardPosition[1][27] = 255;
		BoardPosition[0][28] = 595;
		BoardPosition[1][28] = 325;

		// �ѹ��� ������ ��� ��ġ
		BoardPosition[0][29] = 700;
		BoardPosition[1][29] = 392;

		// ��⸻�� ��ġ
		BoardPosition[0][30] = 628;
		BoardPosition[1][30] = 600;
		BoardPosition[0][31] = 678;
		BoardPosition[1][31] = 600;
		BoardPosition[0][32] = 728;
		BoardPosition[1][32] = 600;
		BoardPosition[0][33] = 778;
		BoardPosition[1][33] = 600;

		BoardPosition[0][34] = 628;
		BoardPosition[1][34] = 800;
		BoardPosition[0][35] = 678;
		BoardPosition[1][35] = 800;
		BoardPosition[0][36] = 728;
		BoardPosition[1][36] = 800;
		BoardPosition[0][37] = 778;
		BoardPosition[1][37] = 800;

		// ���� ��ġ
		BoardPosition[0][38] = 700;
		BoardPosition[1][38] = 460;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String name;
		name = ae.getActionCommand();
		random = new Random();

		BackDoFlag = false;
		NakFlag = false;
		yut.yutBackCnt = 0;

		// P1
		if (PlayerFlag == true) {

			if (CheckRemainCnt(P1)) { // ���� ��ȸ Check

				// ������ �� �ִ� ���� ǥ�������� //������Ʈ�� ���鼭 �� ��ġ�� ȭ��ǥ ǥ��������
				MarkSelectChar(true, P1);

				// �� ������ ��ư Ŭ��
				if (name.equals("�� ������")) {
					ThrowingYut(P1, false);
				}

			}

		}
		// Player Two
		else {

			if (CheckRemainCnt(P2)) { // ���� ��ȸ Check

				MarkSelectChar(true, P2);

				// �� ������ ��ư Ŭ��
				if (name.equals("�� ������")) {
					ThrowingYut(P2, true);
				}

			}

		}

	}

	private void ThrowingYut(Player player, boolean TurnFlag) {

		// �� �ѹ� ������ (��ȸ ����)
		player.RemainCnt--;

		// ���� ����� ����
		float NakNum = random.nextFloat();

		// 0.02 �������� ��!
		if (NakNum <= 0.02)
			NakFlag = true;

		// ���� �߻� �� ���, ������ ���� ��ũ�� ���� �ϰ� ���濡�� ���� �ѱ��.
		if (NakFlag == true) {
			if (player.YutResultList.size() == 0) {

				SituationTextArea.append("P" + player.MyPlayerNum + "�� ���� �Ͽ����ϴ�. ���� �ѱ�ϴ�.\n");
				OccurNak(player, TurnFlag);

			} else {
				SituationTextArea.append("P" + player.MyPlayerNum + "�� ���� �Ͽ����ϴ�.\n");
			}

		} else {

			MakeYutResult(player);

			// "����"�� ���� ���
			if (player.YutResultList.size() == 1 && player.YutResultList.contains("����")) {
				OccurBackDo(player, TurnFlag);

			}

		}
	}

	private void OccurBackDo(Player player, boolean turnFlag) {

		ArrayList<Integer> WaitingCnt = CheckWaitingMal(player.mal.CurMalPos, player.mal.WaitingMalPos);

		// ������� ���� ���� ��
		if (4 - player.GoalInMalCnt == WaitingCnt.size()) {

			SituationTextArea.append("���� ����� ���� �̰� " + "P" + player.MyPlayerNum + "�� ������� ���� ���� ���� �ѱ�ϴ�.\n");
			player.YutResultList.clear();
			OccurNak(player, turnFlag);

		}
		// ������� ���� ���� �� ���, ������� ���� ǥ��
		else {

			System.out.print("P" + player.MyPlayerNum + "�� ������� �� : ");
			// ��� ���� ���� Mark ����
			for (int j : WaitingCnt) {
				System.out.print(j + ", ");

				int curMalIdx = getMalPositionIdx(j, player);

				// ������ ��
				if (curMalIdx == -1)
					continue;

				MarkChar[curMalIdx].setVisible(false);
			}

			System.out.println();
		}
	}

	private void MakeYutResult(Player player) {

		for (int i = 0; i < 4; i++) {

			// 60% Ȯ���� �޸��� ���´�.
			int tempNum = random.nextInt(100);

			yut.BiyutResult[i] = (tempNum >= 60 ? 1 : 0);

			if (yut.BiyutResult[i] == 0)
				yut.yutBackCnt++;

			// 0�϶� �Ʒ���, 1�϶� ����
			if (i == 0 && yut.BiyutResult[i] == 0) {
				yut.YutImg[i].setIcon(yut.YutBackDo);
				BackDoFlag = true;

			} else {
				if (yut.BiyutResult[i] == 0) {
					yut.YutImg[i].setIcon(yut.YutBack);
				} else {
					yut.YutImg[i].setIcon(yut.YutFront);
				}
			}
		}

		String YutResultString = "";

		if (yut.yutBackCnt == 1) {
			YutResultString = (BackDoFlag == true ? "����" : "��");
		} else {
			YutResultString = yut.YutStringList[yut.yutBackCnt];
		}

		SituationTextArea.append("P" + player.MyPlayerNum + "�� ����� " + YutResultString + "\n");
		SituationTextArea.setCaretPosition(SituationTextArea.getDocument().getLength()); // �ǾƷ��� ��ũ���Ѵ�.

		player.YutResultList.add(YutResultString);

		// ��,�� �̻� ������ ��� �÷��̾��� ���� ��ȸ ++
		if (YutResultString.equals("��") || YutResultString.equals("��")) {
			SituationTextArea.append("P" + player.MyPlayerNum + "�� ���� �ѹ� �� ������ �ֽ��ϴ�. \n");
			player.RemainCnt++;
		}

		// ���� ���� �� �����!
		SituationTextArea.append("P" + player.MyPlayerNum + "�� �̵� �� �� �ִ� ���� : ");
		for (String s : player.YutResultList) {
			SituationTextArea.append(s + " ");
		}
		SituationTextArea.append("\n");
	}

	private void OccurNak(Player player, boolean turnFlag) {
		MarkSelectChar(false, player);
		player.RemainCnt++;

		// ���� �Ѱ��ش�.
		PlayerFlag = turnFlag;
	}

	// ������� ���� �ε����� ã�� ��ȯ�Ѵ�.

	private ArrayList<Integer> CheckWaitingMal(int[][] MalPos, int[][] WaitingMalPos) {

		ArrayList<Integer> WaitMalIdxList = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			if (MalPos[0][i] == WaitingMalPos[0][i] && MalPos[1][i] == WaitingMalPos[1][i]) {
				WaitMalIdxList.add(i);
			}
		}
		return WaitMalIdxList;
	}

	// ���ϴ� ĳ���͸� ������ �� ���忡�� �̵������� ������ġ�� Ŭ����
	private class MoveActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			JButton b = (JButton) e.getSource();
			b.setVisible(false);

			// ������ �̵��� ���� ��ġ
			int nextIdx = Integer.parseInt(e.getActionCommand());

			// P1
			if (PlayerFlag == true) {
				PlayerMove(nextIdx, P1, P2, false);
			}
			// P2
			else {
				PlayerMove(nextIdx, P2, P1, true);
			}
			ButtonEnable();
		}
	}

	private void PlayerMove(int nextBoardIdx, Player player, Player Enemy, boolean TurnFlag) {

		// Goal in
		if (nextBoardIdx == 38) {

			int CurGoalInCnt = player.mal.CombList[P1.CurIdx].size() + 1;
			player.GoalInMalCnt += CurGoalInCnt;

			System.out.println("P" + player.MyPlayerNum + ".GoalInMalCnt �� : " + player.GoalInMalCnt);

			player.mal.GoalInList[player.CurIdx] = 1;
			for (int j : player.mal.CombList[player.CurIdx]) {
				player.mal.GoalInList[j] = 1;
			}

			// ���� ��, ��� ���� �ƴ� ���
			ChangeMalPos(player, player.CurIdx, BoardPosition, nextBoardIdx);

		}
		// ���� �� ���� �ִ� ���, ������ġ�� �ٷ� �̵�
		else {

			int NextMalNum = OverLapCheck(nextBoardIdx, player.mal.CurMalPos);
			int EnemyIdx = OverLapCheck(nextBoardIdx, Enemy.mal.CurMalPos);

			// ���� ���� ��ġ�� �ʴ� ���
			if (NextMalNum == -1) {

				// ���� ���� ��ġ�� ������ ���� ���� ��� ���
				if (EnemyIdx != -1 && nextBoardIdx != 38) {

					// ���� ���� ��� �� ��ġ�� �̵� �����ش�
					ChangeMalPos(Enemy, EnemyIdx, Enemy.mal.WaitingMalPos, EnemyIdx);

					// ��� ���� ���� ������ ���� ��� �ʱ�ȭ ���ش�.
					EatenMal(player, Enemy, EnemyIdx);

				}

				// ���� ��, ��� ���� �ƴ� ���
				ChangeMalPos(player, player.CurIdx, BoardPosition, nextBoardIdx);

			}
			// ���� ��ġ�� ���� ��ġ��
			else {

				// ���� ���ǿ� �ִ� ���� �߰�
				player.mal.CombList[NextMalNum].add(player.CurIdx);

				// �̵� �Ϸ��� ���� ������ �� ���� ��� ���Ѵ�.
//				System.out.println("���� ������ �� : " + player.CurIdx);
//				System.out.print("���� ������ ���� ���� �� �� ��ȣ : ");
				for (int j : player.mal.CombList[player.CurIdx]) {
//					System.out.print(j + " ");
					player.mal.CombList[NextMalNum].add(j);
				}
//				System.out.println();

				player.mal.CombList[player.CurIdx].clear();

//				System.out.println("������ �� ��ȣ : " + NextMalNum);
//				System.out.print("������ ���� ���� �� �� ��ȣ : ");
//				for (int j : player.mal.CombList[NextMalNum]) {
//					System.out.print(j + " ");
//				}
//				System.out.println();

				// �߰��� ������ ���� ���� ���ǿ� �ִ� ���� �̹����� ����
				// SituationTextArea.append("������ ����: " + player.mal.CombList[NextMalNum].size()
				// + "\n");
				player.mal.GoalInList[player.CurIdx] = -1; // ������ �ǹ̷� -1

				ChangeMalPos(player, player.CurIdx, player.mal.WaitingMalPos, player.CurIdx);

				player.mal.MalList[player.CurIdx].setVisible(false);
				player.mal.MalList[NextMalNum]
						.setIcon(player.mal.MalChar_Sum[player.mal.CombList[NextMalNum].size() + 1]);

			}
		}

		RemoveSelectedYut(player, nextBoardIdx);

		PrintRemainYut(player);

		// ���� ȭ��
		if (player.GoalInMalCnt >= 4) {
			SituationTextArea.append("Player " + player.MyPlayerNum + " Win!!!!!!!!!!!!!!!!  \n");
			JOptionPane.showMessageDialog(null, "Player " + player.MyPlayerNum + " Win!!", "������ ����",
					JOptionPane.INFORMATION_MESSAGE);
			 System.exit(0);
		}

		// ���� �̵��� �� �ִ� ��ȸ�� �� �ִ� ���
		if (player.RemainCnt > 0) {

			SituationTextArea.append("���� �� �ִ� ��ȸ�� " + player.RemainCnt + "ȸ ���ҽ��ϴ�. \n");
			if (player.YutResultList.size() > 0) {
				MarkSelectChar(true, player);
			}

		} else {

			if (player.YutResultList.size() > 0) {

				MarkSelectChar(true, player);

				// "����"�� ���� ���
				if (player.YutResultList.size() == 1 && player.YutResultList.contains("����")) {
					OccurBackDo(player, TurnFlag);
				}

			} else {
				player.RemainCnt = 1;
				player.YutResultList.clear();
				PlayerFlag = TurnFlag;
			}
		}
	}

	private void PrintRemainYut(Player player) {
		// �� ��� ���� �� ������� ���
		SituationTextArea.append("P" + player.MyPlayerNum + " ���� �� : ");
		for (String s : player.YutResultList) {
			SituationTextArea.append(s + " ");
		}
		SituationTextArea.append("\n");
	}

	private void RemoveSelectedYut(Player player, int nextBoardIdx) {
		// ���� �� �� ��� ����
		for (IntString j : player.InfoOfNextPos) {
			if (j.PosNum == nextBoardIdx) {
				player.YutResultList.remove(j.Yut);
				// SituationTextArea.append("P" + player.MyPlayerNum + "�� ������ �� ��� : " + j.Yut +
				// "\n");
				break;
			}
		}

		// �����ߴ� ��ǥ ��ũ�� ���ش�.
		for (IntString possiblePos : player.InfoOfNextPos) {
			MarkMalPos[possiblePos.PosNum].setVisible(false);
		}
		return;
	}

	private void EatenMal(Player player, Player enemy, int enemyIdx) {

		// �� �̹��� ����
		enemy.mal.MalList[enemyIdx].setIcon(enemy.mal.MalChar);
		for (int j : player.mal.CombList[enemyIdx]) {
			enemy.mal.MalList[j].setIcon(enemy.mal.MalChar);
		}

		// GoalIn�� 0���� �������ش�.
		enemy.mal.GoalInList[enemyIdx] = 0;
		for (int j : enemy.mal.CombList[enemyIdx]) {
			enemy.mal.GoalInList[j] = 0;
			enemy.mal.MalList[j].setVisible(true);
		}

		enemy.mal.CombList[enemyIdx].clear();

		// GoalIn�� 0���� �������ش�.
		enemy.mal.GoalInList[enemyIdx] = 0;

		// ���� ��ȸ�� �߰��� �ش�.
		player.RemainCnt++;

	}
	public void testPrint() {
		
	}
	public void SetMalPos() {
		for(int i = 0; i<4; i++) {
			//System.out.println(P1.mal.CurMalPos[0][i]+ " , " + P1.mal.CurMalPos[1][i]);
			SituationTextArea.append(P1.mal.CurMalPos[0][i]+ " , " + P1.mal.CurMalPos[1][i]+"\n");

			//contentPane.revalidate();
			P1.mal.MalList[i].repaint();
			P1.mal.MalList[i].revalidate();

			P1.mal.MalList[i].setLocation(P1.mal.CurMalPos[0][i], P1.mal.CurMalPos[1][i]);
			P2.mal.MalList[i].setLocation(P2.mal.CurMalPos[0][i], P2.mal.CurMalPos[1][i]);
			P1.mal.MalList[i].setVisible(true);
			P2.mal.MalList[i].setVisible(true);
			//contentPane.add(P1.mal.MalList[i]);

		}
		System.out.println();
	}
	
	
	private void ChangeMalPos(Player player, int curIdx, int[][] PosInfo, int nextBoardIdx) {

		// ���� ��, ��� ���� �ƴ� ���
		player.mal.MalList[curIdx].setLocation(PosInfo[0][nextBoardIdx], PosInfo[1][nextBoardIdx]);
		player.mal.CurMalPos[0][curIdx] = PosInfo[0][nextBoardIdx];
		player.mal.CurMalPos[1][curIdx] = PosInfo[1][nextBoardIdx];
	}
	
	// ���� �̵��� ���� ��ġ�� ���� ���̳� ���� ���� �ִ� ��� üũ�Ͽ�, �����Ѵٸ� �ش� ���� �ε����� ��ȯ.
	private int OverLapCheck(int NextPlayerIdx, int PlayerMalPosition[][]) {

		for (int i = 0; i < 4; i++) {
			if (BoardPosition[0][NextPlayerIdx] == PlayerMalPosition[0][i]
					&& BoardPosition[1][NextPlayerIdx] == PlayerMalPosition[1][i]) {
				return i;
			}
		}
		return -1;

	}
	
	class MyKeyListener implements KeyListener {
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {

			int keyCode = e.getKeyCode();

			switch (keyCode) {
			case KeyEvent.VK_E:
				System.out.println("EŰ ����!!");
				// EŰ�� ������ ������ ���� �̵������� ǥ�� ����,
				// ���ð����� �� ǥ�� ���ش�.

				// �÷��̾� 1�϶�
				if (PlayerFlag == true) {
					Deselect(P1, false);
				}
				// �÷��̾� 2�϶�
				else {
					Deselect(P2, true);
				}
				ButtonEnable();
				break;

//            	case KeyEvent.VK_Q:	
//					System.exit(0);
//					break;
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}

	// �÷��̾� ���� �����ϴ� ���
	private class PlayerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {

			JButton jb = (JButton) ae.getSource();
			System.out.println(ae.getActionCommand());
			
			//���� ��ư�� �����ϰ� ��ư ��Ȱ��ȭ
			ButtonUnable();
			jb.setEnabled(true);
			
			Player SelectPlayer = CheckPlayer(jb);
			
			// ���° ������ ã��
			int MalNum = 0;
			for (int i = 0; i < 4; i++) {
				if (jb.equals(SelectPlayer.mal.MalList[i])) {
					MalNum = i;
					break;
				}
			}

			SelectPlayer.CurIdx = MalNum;

			int CurIdx = getMalPositionIdx(MalNum, SelectPlayer);

			// ���� �����Ͽ����� �����Ҽ� �ִ� �� ǥ�ø� ���ش�.
			MarkSelectChar(false, SelectPlayer);

			// ������ ���� ������ ��ġ�� �˷�����
			SelectPlayer.InfoOfNextPos = possiblePosition(CurIdx, SelectPlayer); // ���� ���� ��ǥ, �������

			for (IntString possiblePos : SelectPlayer.InfoOfNextPos) {
				MarkMalPos[possiblePos.PosNum].setVisible(true);
			}
			contentPane.requestFocus();

		}
	}

	private void MarkSelectChar(boolean flag, Player player) {
		
		for (int i = 0; i < 4; i++) {
			
			int curMalIdx = getMalPositionIdx(i, player);

			// ������ ��
			if (curMalIdx == -1)
				continue;
	
			MarkChar[curMalIdx].setVisible(flag);
		}
	
	}

    public void ButtonUnable() {
    	for(int i = 0; i<4; i++) {
    		P1.mal.MalList[i].setEnabled(false);
    		P2.mal.MalList[i].setEnabled(false);
    	}
    }
    
    public void ButtonEnable() {
    	for(int i = 0; i<4; i++) {
    		P1.mal.MalList[i].setEnabled(true);
    		P2.mal.MalList[i].setEnabled(true);
    	}
    }

	public Player CheckPlayer(JButton jb) {
		Player tempPlayer = P1;
		if (jb.equals(P2.mal.MalList[0]) || jb.equals(P2.mal.MalList[1]) || jb.equals(P2.mal.MalList[2])
				|| jb.equals(P2.mal.MalList[3])) {
			tempPlayer = P2;
		}
		
		return tempPlayer;
	}

	public void Deselect(Player player, boolean turnFlag) {

		// �����ߴ� ��ǥ ��ũ�� ���ش�.
		for (IntString possiblePos : player.InfoOfNextPos) {
			MarkMalPos[possiblePos.PosNum].setVisible(false);
		}

		// ���ð����� �� ǥ�� ���ش�.
		MarkSelectChar(true, player);

		if (player.YutResultList.size() == 1 && player.YutResultList.contains("����")) {
			OccurBackDo(player, turnFlag);
		}

	}

	private ArrayList<IntString> possiblePosition(int curIdx, Player player) {

		ArrayList<IntString> possible = new ArrayList<>();
		
		for (String value : player.YutResultList) {
			
			if (curIdx >= 30 && curIdx <= 37) {	curIdx = 0;	}
			
			// ���� �ΰ��
			if (yut.YutHash.get(value) == 5) {
				for (int i = 0; i < 2; i++) {
					IntString is = new IntString();
					if (NextPossibleMalPos[curIdx][yut.YutHash.get(value) + i] > -1) {
						is.PosNum = NextPossibleMalPos[curIdx][yut.YutHash.get(value) + i];
						is.Yut = value;
						possible.add(is);
					}
				}

			} else {
				
				IntString is = new IntString();
				is.PosNum = NextPossibleMalPos[curIdx][yut.YutHash.get(value)];
				is.Yut = value;
				possible.add(is);
			}
			
		}

		return possible;
	}

	private int getMalPositionIdx(int malNum, Player player) {

		if (player.mal.GoalInList[malNum] != 0)
			return -1;
		
		for (int i = 0; i < 39; i++) {
			if (player.mal.CurMalPos[0][malNum] == BoardPosition[0][i]
					&& player.mal.CurMalPos[1][malNum] == BoardPosition[1][i]) {
				return i;
			}
		}

		return -1;
	}

	private boolean CheckRemainCnt(Player player) {

		if (player.RemainCnt > 0)
			return true;
		else
			return false;
	}

	public static void main(String[] args) {

		BoardMain mf = new BoardMain();
	}

}
