import java.util.*;

import javax.swing.JLabel;

public class Player {
	JLabel PlayerState;
	int RemainCnt = 1;
	int RemainMalCnt = 4;
	int GoalInMalCnt = 0;
	int IngMalCnt = 0;
	ArrayList<String> YutResultList = new ArrayList<>();
	Player(String str, int row){
		PlayerState = new JLabel(str);
		PlayerState.setBounds(635,row,150,40);
	}
}
