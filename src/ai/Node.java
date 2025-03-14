package ai;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Node extends JButton implements ActionListener {

	Node parent;
	public int col;
	public int row;
	int gCost;
	int hCost;
	int fCost;
	boolean start;
	boolean goal;
	boolean solid;
	boolean open;
	boolean checked;
	
	public Node(int col, int row) {
		this.col = col;
		this.row = row;

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		setBackground(Color.orange);
	}
	
	public void setAsStart() {
		setBackground(Color.blue);
		setForeground(Color.black);
		setText("START");
		start = true;
	}
	public void setAsGoal() {
		setBackground(Color.yellow);
		setForeground(Color.black);
		setText("GOAL");
		goal = true;
	}
	public void setAsSolid() {
		setBackground(Color.black);
		setForeground(Color.black);
		solid = true;
	}
	public void setAsOpen() {
		open = true;
	}
	public void setAsChecked() {
		if(start == false && goal == false) {
			setBackground(Color.orange);
			setForeground(Color.black);
		}
		checked = true;
	}
	public void setAsPath() {
		setBackground(Color.green);
		setForeground(Color.black);
	}
}
