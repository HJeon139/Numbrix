package Numbrix;



public class Node{
	private int x, y, t;
	private Node north;
	private Node east;
	private Node south;
	private Node west;

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

	public Node getNorth() {
		return north;
	}

	public void setNorth(Node north) {
		this.north = north;
	}

	public Node getEast() {
		return east;
	}

	public void setEast(Node east) {
		this.east = east;
	}

	public Node getSouth() {
		return south;
	}

	public void setSouth(Node south) {
		this.south = south;
	}

	public Node getWest() {
		return west;
	}

	public void setWest(Node west) {
		this.west = west;
	}
	

	
	

}
