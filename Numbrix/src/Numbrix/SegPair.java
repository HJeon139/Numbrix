package Numbrix;



public class SegPair {
	private int top, bottom;
	
	public SegPair(int top, int bottom){
		this.top = top;
		this.bottom = bottom;
	}
	
	public int getDif(int max){
		int retval=-1;
		if(top ==-1){
			retval = max-bottom;
		}else if(bottom ==-1){
			retval = top;
		}else if(top>0 && bottom>0){
			retval = top-bottom;
		}
		retval = Math.abs(retval);
		return retval;
	}

	
	
	@Override
	public String toString() {
		return "SegPair [bottom=" + bottom +", top=" + top + "]";
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}
	
}


