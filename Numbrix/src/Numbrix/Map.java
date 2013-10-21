package Numbrix;

//import java.util.Arrays;

public class Map {
	private Node[][] map;
	private int[] assigned, remaining;

	public Map(Node[][] map) {
		super();
		this.map = map;
		int d = map.length;
		 assigned = new int[d*d];
		 remaining = new int[d*d];
		 for(int i=0; i<d*d; i++){
			 remaining[i] = i+1;
		 }
	}
	public Map(int d){
		this.build_nest(d);
	}
	
	@Override
	public String toString() {
		String retString = "   +";
		for (int i=0; i<map.length*3; i++){
			retString = retString + "-";
		}
		retString = retString + "+\n    ";
		for (int i=0; i<map.length; i++){
			retString = retString + String.format("%2d ", i);
		}
		retString = retString + "\n   +";
		for (int i=0; i<map.length*3; i++){
			retString = retString + "-";
		}
		retString = retString + "+\n";
		for (int i=0; i<map.length; i++){
			 retString = retString + String.format("%2d |", i);
			 for (int j=0; j<map.length; j++){
				 int k=map[i][j].getT();
				 if (k>0){
					 retString = retString + String.format("%2d ", k);
				 }
				 else{
					 retString = retString + " ? ";
				 }
			 }
			 retString = retString + "|\n";
		 }
		retString = retString + "   +";
		for (int i=0; i<map.length*3; i++){
			retString = retString + "-";
		}
		retString = retString + "+\n\n";
		//display # remaining
		retString = retString + "#'s Remaining:{";
		for(int i = 0; i< remaining.length; i++){
			if(remaining[i]>0){
				retString = retString+remaining[i];
				//if()
					retString = retString+",";
			}
		}
		retString = retString + "}\n";
		return retString;
	}

	public void build_nest(int d){
		 map = new Node[d][d];
		 assigned = new int[d*d];
		 remaining = new int[d*d];
		 for (int i=0; i<d; i++){
			 for (int j=0; j<d; j++){
				 map[i][j]= new Node(i, j, 0);
			 }
		 }
		 for(int i=0; i<d*d; i++){
			 remaining[i] = i+1;
		 }
	}

	public Node[][] getMap() {
		return map;
	}
	public void setMap(Node[][] map) {
		this.map = map;
	}
	
	public Node north(Node node){
		int x = node.getX();
		int y = node.getY()-1;
		return map[x][y];
	}
	public Node north(int x, int y){
		return map[x][y-1];
	}
	public Node east(Node node){
		int x = node.getX()+1;
		int y = node.getY();
		return map[x][y];
	}
	public Node east(int x, int y){
		return map[x+1][y];
	}
	public Node south(Node node){
		int x = node.getX();
		int y = node.getY()+1;
		return map[x][y];
	}
	public Node south(int x, int y){
		return map[x][y+1];
	}
	public Node west(Node node){
		int x = node.getX()-1;
		int y = node.getY();
		return map[x][y];
	}
	public Node west(int x, int y){
		return map[x-1][y];
	}

	public boolean eligible(int x, int y, int t){
		System.out.println("...eligible check...");
		boolean a = false, b = false;
		int n = 0,e = 0,s = 0,w = 0;
		int tp = t+1, tm = t-1;
		if(y-1 >=0)
			n = this.north(map[x][y]).getT();
		if(x+1 <map.length)
			e = this.east(map[x][y]).getT();
		if(y+1 <map.length)
			s = this.south(map[x][y]).getT();
		if(x-1 >=0)
			w = this.west(map[x][y]).getT();
		for (int i=0; i<assigned.length; i++){
			if(assigned[i] == t){
				a = false;
				System.out.println("...eligible exist fail...");
				break;
			}
			else{
				//System.out.println("...eligible exist pass...");
				a=true;
			}
				
		}
		b=((n==tp)||(n==tm)||(e==tp)||(e==tm)||(s==tp)||(s==tm)||(w==tp)||(w==tm))||((n==0)&&(e==0)&&(s==0)&&(w==0));
		System.out.println("A>Number does not already exist: " + a+"\nB>Adjacent number is an increment: "+b+"\nA and B: "+ (a&&b) + "\n...eligible check end...");
		return (a&&b);
	}
	public boolean addValue(int x, int y, int t){
		int d = map.length;
		if((x>d)||(x<0)||(y>d)||(y<0)||(t<1)||(t>d*d)){
			System.out.println("Error: Input numbers are out of bounds of the puzzle!");
			return false;
		}
		boolean w = this.eligible(x, y, t);
		boolean k = true;
		if(w){
			map[x][y].setT(t);
			assigned[t-1]=t;
			remaining[t-1]=-1;
			
		}
		for(int i =0; i<remaining.length; i++){
			k = (k && (remaining[i] ==-1));
			//System.out.print(" "+remaining[i]+" ");
		}
		if(k)
			System.out.println("Congrats! The puzzle is complete.");
		return w;
	}
	public void addForce(int x, int y, int t){
		boolean a=true;
		for (int i=0; i<assigned.length; i++){
			if(assigned[i] == t){
				a = false;
				System.out.println("...eligible exist fail...");
				break;
			}
			else{
				//System.out.println("...eligible exist pass...");
				a=true;
			}
				
		}
		if(a){
			map[x][y].setT(t);
			assigned[t-1]=t;
			remaining[t-1]=-1;
		}
		else
			System.out.println("Error: While loading file, inital puzzle repeats number");
	}
}
