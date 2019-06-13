import java.awt.Container;
import java.util.*;

import javax.swing.JLabel;

public class Player {
	JLabel PlayerState;
	int RemainCnt = 1;
	int GoalInMalCnt = 0;
	int IngMalCnt = 0;
	ArrayList<String> YutResultList = new ArrayList<>();
	ArrayList<IntString> InfoOfNextPos;
	public int CurIdx;

	int MyPlayerNum;
	int MalRow;
	String MalName;
	Mal mal;

	Player(String str, int row, Container contentPane, int Pnum, int MalRow, String MalName) {
		MyPlayerNum = Pnum;
		this.MalRow = MalRow;
		this.MalName = MalName;

		PlayerState = new JLabel(str);
		PlayerState.setBounds(635, row, 150, 40);

		mal = new Mal(contentPane, MyPlayerNum, MalRow, MalName);
	}

}
