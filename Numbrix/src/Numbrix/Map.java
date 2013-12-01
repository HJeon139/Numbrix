package Numbrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Map {
	private Node[][] map;
	private Integer[] assigned;
	private int[] remaining, mapx, mapy;
	private ArrayList<SegPair> codex = new ArrayList<SegPair>();

	public Map(Node[][] map) {
		super();
		this.map = map;
		int d = map.length;
		 assigned = new Integer[d*d];
		 remaining = new int[d*d];
		 mapx = new int[d*d];
		 mapy = new int[d*d];
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
		 assigned = new Integer[d*d];
		 remaining = new int[d*d];
		 mapx = new int[d*d];
		 mapy = new int[d*d];
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
		
		if(w){
			map[x][y].setT(t);
			assigned[t-1]=t;
			remaining[t-1]=-1;
			mapx[t-1] = x;
			mapy[t-1] = y;
		}
		
		if(this.checkGame())
			System.out.println("Congrats! The puzzle is complete.");
		return w;
	}
	public boolean checkGame(){
		boolean k =true;
		for(int i =0; i<remaining.length; i++){
			k = (k && (remaining[i] ==-1));
			//System.out.print(" "+remaining[i]+" ");
		}
		return k;
	}
	public void addForce(int x, int y, Integer t){
		boolean a=true;
		//for (int i=0; i<assigned.length; i++){
			if(assigned[t-1] == t){
				a = false;
				System.out.println("...eligible exist fail...");
			}
			else{
				//System.out.println("...eligible exist pass...");
				a=true;
			}
				
		//}
		if(a){
			map[x][y].setT(t);
			assigned[t-1]=t;
			remaining[t-1]=-1;
			mapx[t-1] = x;
			mapy[t-1] = y;
		}
		else
			System.out.println("Error: While loading file, inital puzzle repeats number");
	}
	
	public boolean commitNode(Node newVal){
		int t = newVal.getT();
		int y = newVal.getY();
		int x = newVal.getX();
		return this.addValue(x, y, t);
	}
	
	public boolean AISolve(){
		//check each adjoining pairs in assigned if it is not continuous then
		//e.g. is assigned[1] = assigned[0]+1
		//assigned is designed so that each number is at it's n-1 slot
		//e.g. [1 2 3 n n n 7 n 9]
		//so pairs to check in this example are (3,7) and (7,9)
		//also determines special cases (n,#) and (#,n)
		//This method assumes that at least one assigned exists.
		boolean state = true;
		Integer top = null, bottom = null;
		for(int i=0; i<assigned.length; i++){
			//set top and bottom
			if(state){
				bottom = assigned[i];
				state = false;
			}
			if(i+1<assigned.length){
				top = assigned[i+1];
			}else
				break;
			
			//iteration process
			if(bottom == null && top != null){
				state = true; 
				//this.connect(top, -1);
				codex.add(new SegPair(top, -1));
			}
			if(bottom != null && top == null && i+1 ==assigned.length-1){
				codex.add(new SegPair(-1, bottom));
			}
			
			if(bottom != null && top != null && top > bottom){
				state = true;
				//this.connect(top, bottom);
				if(top!=bottom+1)
					codex.add(new SegPair(top, bottom));
			}
			if(bottom != null && top != null && top<=bottom){//fail catcher
				System.out.println("Error: Pair search failed");
				break;
			}
		}
		
		Collections.sort(codex, new Comparator<SegPair>() {
		    public int compare(SegPair a, SegPair b) {
		        return a.getDif(map.length*map.length)-b.getDif(map.length*map.length);
		    }
		});
		int s = codex.size();
		for(int i=0; i<s;i++){
			System.out.println("Connecting: "+codex.get(i));
			if(this.connect(i)){
				System.out.println("Connected: "+codex.get(i));
				//System.out.print(this);
				
				//codex.remove(i);
				//i--;
				//s--;
			}
			else
				System.out.println("Failed to Connect: "+codex.get(i));
			this.probString();
			
		}
		while(!this.checkGame()){
		Node rome = this.highestProb();
		System.out.println("Most Probable: "+rome);
		this.addValue(rome.getX(), rome.getY(), rome.getT());
		ArrayList<Integer> remus = new ArrayList<Integer>();
		remus.add(rome.getT());
		for(int i=0; i<map.length;i++){
			for(int j=0; j<map.length;j++){
				map[i][j].possible.removeAll(remus);
			}
		}
		}
		return this.checkGame();
	}
	
	public boolean connect(int i){
		boolean retval = false;
		int top = codex.get(i).getTop();
		int bottom = codex.get(i).getBottom();
		int dif = codex.get(i).getDif(map.length*map.length);
		if(top==-1 && bottom >0){
//			if(dif>2)
//				retval = this.echoTap(bottom, (int)(Math.ceil((double)dif)/2.0), 1);
//			else
				retval = this.echoTap(bottom, dif, 1, -1);
		}
		else if(top>0 && bottom ==-1){
//			if(dif>2)
//				retval = this.echoTap(top, (int)(Math.ceil((double)dif)/2.0), -1);
//			else
				retval = this.echoTap(top, dif, -1, -1);
		}
		else if(top>0 && bottom>0){
			System.out.println("Phase1: bottom to top");
			boolean b = this.echoTap(bottom, (int)(Math.ceil((double)dif)/1.5), 1, top);
			System.out.println("Phase2: top to bottom");
			boolean a = this.echoTap(top, (int)(Math.ceil((double)dif)/1.5), -1, bottom);
			retval = a&&b;
		
		}
		
		
		return retval;
	}
	
	public boolean checkBounds(int x, int y){
		return ((x>=0)&&(x<map.length)&&(y>=0)&&(y<map.length));
	}
	public boolean echoTap(Node peta, int depth, int inc, boolean first, Node dest){
		boolean retval = false/*, prune = true*/;
		int x = peta.getX();
		int y = peta.getY();
		int t = peta.getT();
		int dx=0, dy=0/*, dt=0*/;
		if(dest != null){
			dx = dest.getX();
			dy = dest.getY();
			//dt = dest.getT();
		}
		System.out.println(peta);
		if((!first&&!this.hasRemaining(t))||this.checkGame() || depth <0 ||(!first && map[x][y].getT()==t && map[x][y].getT()!=0)|| t<1 || t>map.length*map.length){//game solved or depth end or value does not match, prune
			retval = true;
			System.out.println("EchoTap("+x+", "+y+"): depth="+depth+", recursion stop: t="+t+" mapT="+map[x][y].getT());
		}
		else{ 
			if(map[x][y].getT() == 0 && this.hasRemaining(t)&&!first){
			//set the possible values into the possible arraylist of that node.
			map[x][y].possible.add(t);
			System.out.println("EchoTap("+x+", "+y+"): depth="+depth+", possible add: t="+t);
			}	
			if(dest == null && checkBounds(x,y-1) && depth>0&& map[x][y-1].getT() == 0 &&t>0 && t<(map.length*map.length+1)){
				System.out.print("west: ");
				peta.setNorth(new Node(x, y-1, t+inc));
				retval = this.echoTap(peta.getNorth(), depth-1, inc, false, dest);
				
			}
			else if((Math.abs(dy-(y-1))<=y)&&checkBounds(x,y-1) && depth>0&& map[x][y-1].getT() == 0&&t>0 && t<(map.length*map.length+1)){
				peta.setNorth(new Node(x, y-1, t+inc));
				if(map[x][y-1].possible.contains(t+inc)){
					map[x][y-1].possible.add(t+inc);
				}else if(map[x][y-1].possible.contains(t-inc)){
					map[x][y-1].possible.add(t-inc);
				}
				System.out.print("west2: ");
				retval = this.echoTap(peta.getNorth(), depth-1, inc, false, dest);
				
			}
			if(dest == null && checkBounds(x+1,y) && depth>0 && map[x+1][y].getT() == 0 &&t>0 && t<(map.length*map.length+1)){
				peta.setEast(new Node(x+1, y, t+inc));
				System.out.print("south: ");
				retval = this.echoTap(peta.getEast(), depth-1, inc, false, dest);
				
			}else if((Math.abs(dx-(x+1))<=x)&& checkBounds(x+1,y) && depth>0 && map[x+1][y].getT() == 0 &&t>0 && t<(map.length*map.length+1)){
				peta.setEast(new Node(x+1, y, t+inc));
				if(map[x+1][y].possible.contains(t+inc)){
					map[x+1][y].possible.add(t+inc);
				}else if(map[x+1][y].possible.contains(t-inc)){
					map[x+1][y].possible.add(t-inc);
				}
				System.out.print("south2: ");
				retval = this.echoTap(peta.getEast(), depth-1, inc, false, dest);
				
			}
			if(dest == null && checkBounds(x,y+1) && depth>0 && map[x][y+1].getT() == 0 &&t>0 && t<(map.length*map.length+1)){
				peta.setSouth(new Node(x, y+1, t+inc));
				System.out.print("east: ");
				retval = this.echoTap(peta.getSouth(), depth-1, inc, false, dest);
				
			}else if((Math.abs(dy-(y+1))<=y) && checkBounds(x,y+1) && depth>0 && map[x][y+1].getT() == 0 &&t>0 && t<(map.length*map.length+1)){
				//south
				peta.setSouth(new Node(x, y+1, t+inc));
				if(map[x][y+1].possible.contains(t+inc)){
					map[x][y+1].possible.add(t+inc);
				}else if(map[x][y+1].possible.contains(t-inc)){
					map[x][y+1].possible.add(t-inc);
				}
				System.out.print("east2: ");
				retval = this.echoTap(peta.getSouth(), depth-1, inc, false, dest);
				
			}
			if(dest == null && checkBounds(x-1,y) && depth>0&& map[x-1][y].getT() == 0 &&t>0 && t<(map.length*map.length+1)){
				//west
				peta.setWest(new Node(x-1, y, t+inc));
				System.out.print("north: ");
				retval = this.echoTap(peta.getWest(), depth-1, inc, false, dest);
				
			}else if((Math.abs(dx-(x-1))<=x) && checkBounds(x-1,y) && depth>0&& map[x-1][y].getT() == 0 &&t>0 && t<(map.length*map.length+1)){
				//west
				peta.setWest(new Node(x-1, y, t+inc));
				if(map[x-1][y].possible.contains(t+inc)){
					map[x-1][y].possible.add(t+inc);
				}else if(map[x-1][y].possible.contains(t-inc)){
					map[x-1][y].possible.add(t-inc);
				}
				System.out.print("north2: ");
				retval = this.echoTap(peta.getWest(), depth-1, inc, false, dest);
				
			}
		}
		return retval;
	}
	
	public boolean hasRemaining(int v){
		boolean r = false;
		for(int i=0; i<remaining.length; i++){
			if(v==remaining[i]){
				r = true;
				break;
			}
		}
		return r;
	}
	
	public boolean echoTap(int val, int depth, int inc, int dval){
		//boolean retval = false;
		
		int x=this.mapx[val-1], y=this.mapy[val-1];
		Node peta = new Node(x, y, val);
		Node dest = null;
		if(dval>0 &&dval<map.length*map.length){
			x=this.mapx[dval-1];
			y=this.mapy[dval-1];
			dest = new Node(x, y, dval);
		}
//		if(map[x][y].getT()==val && map[x][y].getT()!=0){
//			peta=map[x][y];
//			retval=true;
//		}
		
		if(x<0 || y<0 || peta==null)
			return false;
		else
			return this.echoTap(peta, depth, inc, true, dest);
	}
	
	public ArrayList<idPair> highestPset(int x, int y){
		ArrayList<idPair> retval = new ArrayList<idPair>();
		ArrayList<Integer> pval = map[x][y].possible;
		//System.out.println(pval);
		if(pval!=null){
			//int length = pval.size();
			Integer[] count = new Integer[pval.size()];
			Integer[] value = new Integer[pval.size()];
			//retval = new idPair[pval.size()];
			
			for(int i=0; i<pval.size();i++ ){
				if(i==0){//first case
					value[i]=pval.get(i);
					count[i]=1;
					
				}else{
					for(int j=0; j<value.length; j++){
						//look until match or null
						if(pval.get(i)==value[j] && value[j]!=null){
							count[j]++;
							break;
						}else if(value[j+1] == null){
							value[j+1]=pval.get(i);
							count[j+1]=1;
							break;
						}
					}
				}
			}
			for(int i=0; i<count.length; i++){
				//System.out.println("value: "+value[i]);
				if(value[i]!=null)
					retval.add(new idPair(value[i], ((double)count[i])/((double)pval.size())));
			}
		}
		Collections.sort(retval, new Comparator<idPair>() {
		    public int compare(idPair a, idPair b) {
		        return (int)(100*a.getPercent() - 100*b.getPercent())*-1;
		    }
		});
		return retval;
	}
	
	public int highestPval(int x, int y){
		int retval = 0;
		ArrayList<Integer> pval = map[x][y].possible;
		
		if(!pval.isEmpty()){
			//int length = pval.size();
			Integer[] count = new Integer[pval.size()];
			Integer[] value = new Integer[pval.size()];
			//retval = new idPair[pval.size()];
			
			for(int i=0; i<pval.size();i++ ){
				if(i==0){//first case
					value[i]=pval.get(i);
					count[i]=1;
					
				}else{
					for(int j=0; j<value.length; j++){
						//look until match or null
						if(pval.get(i)==value[j] && value[j]!=null){
							count[j]++;
							break;
						}else if(value[j+1] == null){
							value[j+1]=pval.get(i);
							count[j+1]=1;
							break;
						}
					}
				}
			}
			
			int loc=-1,max=-1;
			for(int i =0; i<count.length;i++){
				if(i==0){
					max=count[i];
					loc=i;
				}else if(count[i]>max){
					max=count[i];
					loc=i;
				}
				
				if(count[i+1]==null){
					break;
				}
			}
			retval=value[loc];
		}
		return retval;
	}
		
	public Node highestProb(int x, int y){
		Node retval = null;
		idPair probRef = null;
		if(!this.highestPset(x,y).isEmpty()){
			probRef = this.highestPset(x,y).get(0);
			retval = new Node(x, y, probRef.getValue());
		}else
			retval = new Node(x, y, 0);
		
		//System.out.println(probRef);
		
		return retval;
	}
	public Node highestProb(){
		ArrayList<idPair> probRef = new ArrayList<idPair>();
		
		for(int i = 0; i<this.map.length; i++){
			for(int j=0; j<this.map.length; j++){
				if(!this.highestPset(i,j).isEmpty()){
				idPair temp = this.highestPset(i,j).get(0);
				temp.setx(i);
				temp.setY(j);
				probRef.add(temp);
				}
				//System.out.println(probRef);
			}
		}
		
		//prob print
		Collections.sort(probRef, new Comparator<idPair>() {
		    public int compare(idPair a, idPair b) {
		        return (int)(100*a.getPercent() - 100*b.getPercent())*-1;
		    }
		});
		System.out.println(probRef.get(0));
		return new Node(probRef.get(0).getX(), probRef.get(0).getY(), probRef.get(0).getValue());
	}
	
	public void probString(){
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
				 int k=this.highestProb(i, j).getT();
				 if (k>0){
					 retString = retString + String.format("%2d ", k);
				 }
				 else{
					 if(map[i][j].getT()>0)
						 retString = retString + String.format("%2d ", map[i][j].getT());
					 else
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
		retString = retString + "Most Probable Map";
		retString = retString + "\n";
		System.out.println(retString);
	}
	
}
