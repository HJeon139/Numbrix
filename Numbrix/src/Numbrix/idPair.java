package Numbrix;

public class idPair {
	private int value, x, y;
	private double percent;
	
	public idPair(int v, double p){
		value = v;
		percent = p;
	}
	public idPair(int x, int y, int v, double p){
		this.x = x;
		this.y = y;
		value = v;
		percent = p;
	}
	
	@Override
	public String toString() {
		return String.format("idPair [value= %d, percent = %3.3f]", value, percent*100.0 );
	}

	public int getX() {
		return x;
	}

	public void setx(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}

	public void setY(int Y) {
		this.y = Y;
	}
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}
	
	
}
