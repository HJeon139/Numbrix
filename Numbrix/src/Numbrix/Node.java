package Numbrix;

import java.util.ArrayList;

public class Node {
	private int x, y, t;
	private ArrayList<Integer> p = new ArrayList<Integer>();

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public Node(int x, int y, int t) {
		super();
		this.x = x;
		this.y = y;
		this.t = t;
	}
	
	public boolean pFull(){
		return (p.size() >= 20);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public ArrayList<Integer> getP() {
		return p;
	}
	public void setP(ArrayList<Integer> p) {
		this.p = p;
	}

	
	

}
