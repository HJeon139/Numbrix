package Numbrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Map {
	private Node[][] map;
	private Integer[] remaining, assigned;
	private int[]  mapx, mapy;
	private ArrayList<SegPair> codex = new ArrayList<SegPair>();

	public Map(Node[][] map) {
		super();
		this.map = map;
		int d = map.length;
		assigned = new Integer[d*d];
		remaining = new Integer[d*d];
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
		remaining = new Integer[d*d];
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

	public boolean eligible(int x, int y, Integer t){
		//System.out.println("...eligible check...");
		boolean a = false, b = false;//, c=false;
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
		b=((n==tp)||(n==tm)||(e==tp)||(e==tm)||(s==tp)||(s==tm)||(w==tp)||(w==tm))/*||((n==0)&&(e==0)&&(s==0)&&(w==0))*/;
		//second degree of connection possible
		/*int nn=0, ne=0, ee=0, se=0, ss=0, sw=0, ww=0, nw=0;
		tp++; tm--;
		if(x-1>=0)
			nn=map[x-2][y].getT();
		if(x-1>=0)
			ne=map[x-1][y+1].getT();
		if(x-1>=0)
			ee=map[x][y+2].getT();
		if(x-1>=0)
			se=map[x+1][y+1].getT();
		if(x-1>=0)
			ss=map[x+2][y].getT();
		if(x-1>=0)
			sw=map[x+1][y-1].getT();
		if(x-1>=0)
			ww=map[x][y-2].getT();
		if(x-1>=0)
			nw=map[x-1][y-1].getT();
		c=(nn==tp)||(nn==tm)||(ne==tp)||(ne==tm)||(ee==tp)||(ee==tm)||(se==tp)||(se==tm)||(ss==tp)||(ss==tm)||(sw==tp)||(sw==tm)||(ww==tp)||(ww==tm)||(nw==tp)||(nw==tm);*/
		//System.out.println("A>Number does not already exist: " + a+"\nB>Adjacent number is an increment: "+b/*+"\nC>Second Degree is possible"+c*/+"\nA and B: "/* or C:*/+ (a&&b) + "\n...eligible check end...");
		return ((a&&b)/*||c*/);
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
	public boolean addForce(int x, int y, Integer t, boolean k){
		boolean a=true;
		//for (int i=0; i<assigned.length; i++){
		if(assigned[t-1] == t){
			a = false;
			System.out.println("...eligible exist fail...");
		}
		else if(k){
			if(y+1 < map.length && y+1 >=0){
				a=map[x][y+1].getT()==t+1||map[x][y+1].getT()==t-1;

			}else if(y-1 <map.length && y-1 >=0){
				a=map[x][y-1].getT()==t+1||map[x][y-1].getT()==t-1;
			}else if(x+1 < map.length && x+1 >=0){
				a=map[x+1][y].getT()==t+1||map[x+1][y].getT()==t-1;
			}else if(x-1 <map.length && x-1 >=0){
				a=map[x-1][y].getT()==t+1||map[x-1][y].getT()==t-1;
			}
			//System.out.println("...eligible exist pass...");
		}else{
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
		return a;
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
		Node recent = null;
		Integer top = null, bottom = null, MAX =30;
		while(!this.checkGame()&&MAX>0){
			int rcount = 0;
			System.out.println("[limit: "+MAX+"]");
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
				if(remaining[i]==i+1)
					rcount++;
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
				//System.out.println("Connecting: "+codex.get(i));
				/*if(*/this.connect(i);/*){*/
				//if (codex.get(i).getBottom() ==1){
					System.out.println("The prob map after "+codex.get(i));
					this.probString();
				//}
				//System.out.println("Connected: "+codex.get(i));
				//System.out.print(this);

				//codex.remove(i);
				//i--;
				//s--;
				/*}
			else
				System.out.println("Failed to Connect: "+codex.get(i));*/
				//this.probString();

			}
			//this.probString();
			System.out.println(map[4][0].possible);
			
			boolean ka = false;
			//int ro=30;
			//while(ka && ro>0){
			Node rome = null;
			System.out.println("rcount = "+rcount);
			for(int pick=0; pick< rcount; pick++){
				rome = this.highestProb(pick);
				//System.out.println("Most Probable: "+rome);
				if(this.addValue(rome.getX(), rome.getY(), rome.getT())){
					ka=true;
					break;
				}
					
			}
			System.out.println("at["+rome.getY()+", "+rome.getX()+"]: possible="+map[rome.getX()][rome.getY()].possible);
			
				for(int i=0; i<map.length;i++){
					for(int j=0; j<map.length;j++){
						//map[i][j].possible.removeAll(remus);

						map[i][j].possible.clear();
					}
				}
			//state = true;
			top = null;
			bottom = null;
			int totem = 0;
			char or = '0', an = '0';
			do{
				state = false;
			for(int i=0; i<assigned.length;i++){
				totem = 0;
				or = '0';
				an = '0';
				boolean tem = false, tom = false;
				if(i+1<assigned.length)
					tem=(assigned[i+1]!=null);
				if(i-1>0)
					tom=(assigned[i-1]!=null);
				if(assigned[i]!=null && !(tem && tom)){
					int x = this.mapx[i];
					int y = this.mapy[i];
					int t = this.assigned[i];
					if(x+1>=0 && x+1<map.length){
						//east
						if(map[x+1][y].getT()==0){
							totem++;
							or='e';
						}else {

							if(an=='0'){
								if(map[x+1][y].getT()==t+1 && (t-1)>0){
									if(an=='a'||an=='b'){
										an='0';
									}else
										an='a';
								}else if(map[x+1][y].getT()==t-1&&(t+1)<=map.length*map.length){
									if(an=='a'||an=='b'){
										an='0';
									}else an='b';
								}
							}
						}

					}
					if(x-1>=0 && x-1<map.length){
						//west
						if(map[x-1][y].getT()==0){
							totem++;
							or='w';
						}else {

							if(an=='0'){
								if(map[x-1][y].getT()==t+1 && (t-1)>0){
									if(an=='a'||an=='b'){
										an='0';
									}else an='a';
								}else if(map[x-1][y].getT()==t-1&&(t+1)<=map.length*map.length){
									if(an=='a'||an=='b'){
										an='0';
									}else an='b';
								}
							}
						}
					}
					if(y+1>=0 && y+1<map.length){
						//south
						if(map[x][y+1].getT()==0){
							totem++;
							or='s';
						}else {

							if(an=='0'){
								if(map[x][y+1].getT()==t+1&& (t-1)>0){
									if(an=='a'||an=='b'){
										an='0';
									}else an='a';
								}else if(map[x][y+1].getT()==t-1&&(t+1)<=map.length*map.length){
									if(an=='a'||an=='b'){
										an='0';
									}else an='b';
								}
							}
						}
					}
					if(y-1>=0 && y-1<map.length){
						//north
						if(map[x][y-1].getT()==0){
							totem++;
							or='n';
						}else {

							if(an=='0'){
								if(map[x][y-1].getT()==t+1&& (t-1)>0){
									if(an=='a'||an=='b'){
										an='0';
									}else an='a';
								}else if(map[x][y-1].getT()==t-1&&(t+1)<=map.length*map.length){
									if(an=='a'||an=='b'){
										an='0';
									}else an='b';
								}
							}
						}
					}

					if(totem == 1 && an!='0'){
					state=true;
					//System.out.println("T="+t+" Totem= "+totem+" an="+an+" or="+or);

					if(or=='e'){
						if(an=='b'){
							//System.out.println("adding "+(t+1)+" at [x="+(x+1)+", y="+y+"]");
							state = this.addForce(x+1, y, t+1, false);
							ArrayList<Integer> remus = new ArrayList<Integer>();
							remus.add(t+1);
							map[x+1][y].possible.clear();
							for(int k=0; k<map.length;k++){
								for(int j=0; j<map.length;j++){
									//map[k][j].possible.removeAll(remus);
									map[k][j].possible.clear();
								}
							}
						}
						else if(an=='a'){
							//System.out.println("adding "+(t-1)+" at [x="+(x+1)+", y="+y+"]");
							state = this.addForce(x+1, y, t-1, false);
							ArrayList<Integer> remus = new ArrayList<Integer>();
							remus.add(t-1);
							map[x+1][y].possible.clear();
							for(int k=0; k<map.length;k++){
								for(int j=0; j<map.length;j++){
									//map[k][j].possible.removeAll(remus);
									map[k][j].possible.clear();
								}
							}
						}

					}else if(or=='w'){
						if(an=='b'){
							//System.out.println("adding "+(t+1)+" at [x="+(x-1)+", y="+y+"]");
							state = this.addForce(x-1, y, t+1, false);
							ArrayList<Integer> remus = new ArrayList<Integer>();
							remus.add(t+1);
							map[x-1][y].possible.clear();
							//this.echoTap(rome.getT(), 1, 1, dval);
							for(int k=0; k<map.length;k++){
								for(int j=0; j<map.length;j++){
									//map[k][j].possible.removeAll(remus);
									map[k][j].possible.clear();
								}
							}
						}
						else if(an=='a'){
							//System.out.println("adding "+(t-1)+" at [x="+(x-1)+", y="+y+"]");
							state = this.addForce(x-1, y, t-1, false);
							ArrayList<Integer> remus = new ArrayList<Integer>();
							remus.add(t-1);
							map[x-1][y].possible.clear();
							//this.echoTap(rome.getT(), 1, 1, dval);
							for(int k=0; k<map.length;k++){
								for(int j=0; j<map.length;j++){
									//map[k][j].possible.removeAll(remus);
									map[k][j].possible.clear();
								}
							}
						}
					}else if(or=='s'){
						if(an=='b'){
							//System.out.println("adding "+(t+1)+" at [x="+x+", y="+(y+1)+"]");
							state = this.addForce(x, y+1, t+1, false);
							ArrayList<Integer> remus = new ArrayList<Integer>();
							remus.add(t+1);
							map[x][y+1].possible.clear();
							//this.echoTap(rome.getT(), 1, 1, dval);
							for(int k=0; k<map.length;k++){
								for(int j=0; j<map.length;j++){
									//map[k][j].possible.removeAll(remus);
									map[k][j].possible.clear();
								}
							}
						}
						else if(an=='a'){
							//System.out.println("adding "+(t-1)+" at [x="+x+", y="+(y+1)+"]");
							state = this.addForce(x, y+1, t-1, false);
							ArrayList<Integer> remus = new ArrayList<Integer>();
							remus.add(t-1);
							map[x][y+1].possible.clear();
							//this.echoTap(rome.getT(), 1, 1, dval);
							for(int k=0; k<map.length;k++){
								for(int j=0; j<map.length;j++){
									//map[k][j].possible.removeAll(remus);
									map[k][j].possible.clear();
								}
							}
						}

					}else if(or=='n'){
						if(an=='b'){
							//System.out.println("adding "+(t+1)+" at [x="+x+", y="+(y-1)+"]");
							state = this.addForce(x, y-1, t+1, false);
							ArrayList<Integer> remus = new ArrayList<Integer>();
							remus.add(t+1);
							map[x][y-1].possible.clear();
							//this.echoTap(rome.getT(), 1, 1, dval);
							for(int k=0; k<map.length;k++){
								for(int j=0; j<map.length;j++){
									//map[k][j].possible.removeAll(remus);
									map[k][j].possible.clear();
								}
							}
						}

						else if(an=='a'){
							//System.out.println("adding "+(t-1)+" at [x="+x+", y="+(y-1)+"]");
							state = this.addForce(x, y-1, t-1, false);
							ArrayList<Integer> remus = new ArrayList<Integer>();
							remus.add(t-1);
							map[x][y-1].possible.clear();
							//this.echoTap(rome.getT(), 1, 1, dval);
							for(int k=0; k<map.length;k++){
								for(int j=0; j<map.length;j++){
									//map[k][j].possible.removeAll(remus);
									map[k][j].possible.clear();
								}
							}
						}
						
						

					}
					//System.out.println(this);
					}/*else{
						state = false;
					}*/

				}

			}
		}while(state);
			state = true;
			System.out.println(this);
			
			codex.clear();
			MAX--;
		}
		return this.checkGame();
	}

	public boolean connect(int i){
		boolean retval = false;
		int top = codex.get(i).getTop();
		int bottom = codex.get(i).getBottom();
		int dif = codex.get(i).getDif(map.length*map.length);
		/*if(dif>5)
			dif=5;*/
		if(top<=0 && bottom >0){
			//			if(dif>2)
			//				retval = this.echoTap(bottom, (int)(Math.ceil((double)dif)/2.0), 1);
			//			else
			//retval = this.echoTap(bottom, (int)(Math.ceil((double)dif)/1.2), 1, -1, i);
			
			retval = this.shout(bottom, -1, (int)(Math.ceil((double)dif)/2.2), 1, codex.size()-i);
		}
		else if(top>0 && bottom <=0){
			//			if(dif>2)
			//				retval = this.echoTap(top, (int)(Math.ceil((double)dif)/2.0), -1);
			//			else
			//retval = this.echoTap(top, (int)(Math.ceil((double)dif)/1.2), -1, -1, i);
			retval = this.shout(top, -1, (int)(Math.ceil((double)dif)/2.2), -1, codex.size()-i);
		}
		else if(top>0 && bottom>0){
			if(dif==2){
				//connect the two
				int topx = this.mapx[top-1];
				int topy = this.mapy[top-1];
				int bottomx = this.mapx[bottom-1];
				int bottomy = this.mapy[bottom-1];
				if(topx == bottomx){
					if(bottomy<topy){
						bottomy++;
					}else{
						bottomy = ++topy;
					}
					retval = this.addValue(topx, bottomy, bottom+1);
				}else if(topy == bottomy){
					if(bottomx<topx){
						bottomx++;
					}else{
						bottomx = ++topx;
					}
					retval = this.addValue(bottomx, bottomy, bottom+1);
				}else{
					boolean b = this.echoTap(bottom, (int)(Math.ceil((double)dif)/1.4), 1, top, i);
					//System.out.println("Phase2: top to bottom");
					boolean a = this.echoTap(top, (int)(Math.ceil((double)dif)/1.4), -1, bottom, i);
					retval = a&&b;
				}
			}else{
			//System.out.println("Phase1: bottom to top");
			boolean b = this.echoTap(bottom, (int)(Math.ceil((double)dif)/1.4), 1, top, i);
			//System.out.println("Phase2: top to bottom");
			boolean a = this.echoTap(top, (int)(Math.ceil((double)dif)/1.4), -1, bottom, i);
			retval = a&&b;
			}
		}


		return retval;
	}

	public boolean checkBounds(int x, int y){
		return ((x>=0)&&(x<map.length)&&(y>=0)&&(y<map.length));
	}
	
	public boolean shout(int source, int dest, int depth, int inc, int i){
		int x, y, dx, dy;
		x=this.mapx[source-1];
		y=this.mapy[source-1];
		boolean a= false, b=false, c=false, d=false;

		if(dest >0){
			dx=this.mapx[dest-1];
			dy = this.mapy[dest-1];
			if(depth<0){
				a= true;
			}else{
				if((Math.abs(dx-x)+Math.abs(dy-y))>=Math.abs(dest-source)){
					//north [x-1]
					if(x-1>=0 && x-1<map.length && dx<x)
						a=echo(new Node(x, y, source), new Node(dx, dy, dest), new Node(x-1, y, source+inc), depth-1, inc, i+2); 
					else
						a=echo(new Node(x, y, source), new Node(dx, dy, dest), new Node(x-1, y, source+inc), depth-1, inc, i);
					//east [y+1]
					if(y+1>=0 && y+1<map.length && dy>y)
						b=echo(new Node(x, y, source), new Node(dx, dy, dest), new Node(x, y+1, source+inc), depth-1, inc, i+2);
					else
						b=echo(new Node(x, y, source), new Node(dx, dy, dest), new Node(x, y+1, source+inc), depth-1, inc, i);
					//south [x+1]
					if(x+1>=0 && x+1<map.length && dx>x)
						c=echo(new Node(x, y, source), new Node(dx, dy, dest), new Node(x+1, y, source+inc), depth-1, inc, i+2); 
					else
						c=echo(new Node(x, y, source), new Node(dx, dy, dest), new Node(x+1, y, source+inc), depth-1, inc, i);
					//west [y-1]
					if(y-1>=0 && y-1<map.length && dy<y)
						d=echo(new Node(x, y, source), new Node(dx, dy, dest), new Node(x, y-1, source+inc), depth-1, inc, i+2);
					else
						d=echo(new Node(x, y, source), new Node(dx, dy, dest), new Node(x, y-1, source+inc), depth-1, inc, i);
				}else{
					//north [x-1]
					if(x-1>=0 && x-1<map.length)
						a=echo(new Node(x, y, source), new Node(dx, dy, dest), new Node(x-1, y, source+inc), depth-1, inc, i); 
					//east [y+1]
					if(y+1>=0 && y+1<map.length)
						b=echo(new Node(x, y, source), new Node(dx, dy, dest), new Node(x, y+1, source+inc), depth-1, inc, i); 
					//south [x+1]
					if(x+1>=0 && x+1<map.length)
						c=echo(new Node(x, y, source), new Node(dx, dy, dest), new Node(x+1, y, source+inc), depth-1, inc, i); 
					//west [y-1]
					if(y-1>=0 && y-1<map.length)
						d=echo(new Node(x, y, source), new Node(dx, dy, dest), new Node(x, y-1, source+inc), depth-1, inc, i);
					
				}
			}
		}else{
			
			//north [x-1]
			if(depth<0){
				a= true;
			}else{

				if(x-1>=0 && x-1<map.length)
					a=echou(x,y, new Node(x-1, y, source+inc), depth-1, inc, i); 
				//east [y+1]
				if(y+1>=0 && y+1<map.length)
					b=echou(x,y, new Node(x, y+1, source+inc), depth-1, inc, i); 
				//south [x+1]
				if(x+1>=0 && x+1<map.length)
					c=echou(x,y, new Node(x+1, y, source+inc), depth-1, inc, i); 
				//west [y-1]
				if(y-1>=0 && y-1<map.length)
					d=echou(x,y, new Node(x, y-1, source+inc), depth-1, inc, i); 
			}
		}
		return a||b||c||d;
	}

	public boolean echo(Node source, Node dest, Node current, int depth, int inc, int i){
		
		return false;
	}
	
	public boolean echou(int dx, int dy, Node current, int depth, int inc, int i){
		boolean a= false, b=false, c=false, d=false;
		int x=current.getX();
		int y=current.getY();
		int t=current.getT();
		
		
		//tap and stop case
		if(depth <0 || t<=1 || t>=map.length*map.length){
			
				a=false;
		}else if(t==1 || t==map.length*map.length){
			a=true;
		}else{
			//current.setT(t+inc);
			//t=t+inc;
			//north [x-1]
			if(x-1>=0 && x-1<map.length && dx != x-1){
				//current.setX(x-1);
				//current.setT(t+inc);
				a=echou(x,y, new Node(x-1, y, t+inc), depth-1, inc, i-1); 
			}
			else{
				a=false;}
			//east [y+1]
			if(y+1>=0 && y+1<map.length&& dy != y+1){
				//current.setY(y+1);
				b=echou(x,y, new Node(x, y+1, t+inc), depth-1, inc, i-1); 
			}else{
				b=false;}
			//south [x+1]
			if(x+1>=0 && x+1<map.length&& dx != x+1){
				//current.setX(x+1);
				c=echou(x,y, new Node(x+1, y, t+inc), depth-1, inc, i-1);
			}else{
				c=false;}
			//west [y-1]
			if(y-1>=0 && y-1<map.length&& dy != y-1){
				//current.setY(y-1);
				d=echou(x,y, new Node(x, y-1, t+inc), depth-1, inc, i-1); 
			}else{
				d=false;}
			a= a||b||c||d;
		}
		if(a) System.out.println(current+" depth= "+depth);
		//commit
		a=a && map[x][y].getT()==0;
		if(a){
			if(i<0) i=0;
			for(int k=0; k<=i; k++)
				map[x][y].possible.add(t);
		}

		return a;
	}
	
	
	public boolean tap(Node current){
		//commits node and returns true if possiblity at node is the same as current value
		return false;
	}
	
	public boolean echoTap(Node peta, int depth, int inc, boolean first, Node dest, Node source, int rep){
		boolean retval = false/*, prune = true*/;
		int x = peta.getX();
		int y = peta.getY();
		int t = peta.getT();
		int dx=0, dy=0, dt=0;
		if(dest != null){
			dx = dest.getX();
			dy = dest.getY();
			dt = dest.getT();
			/*if(Math.abs(dx-x)+Math.abs(dy-y)<Math.abs(dt-t)){
				dest = null;
				System.out.print(" depth and dest trucated ");
				if(depth>1)
					depth = 1;
			}*/
		}
		if(first){
			source = new Node(x, y, t);
		}
		//System.out.println(peta);
		if((!first&&!this.hasRemaining(t))||this.checkGame() || depth <0 ||(!first && map[x][y].getT()==t && map[x][y].getT()!=0)|| t<1 || t>map.length*map.length){//game solved or depth end or value does not match, prune
			retval = true;
			//System.out.println("EchoTap("+x+", "+y+"): depth="+depth+", recursion stop: t="+t+" mapT="+map[x][y].getT());
		}
		else{ 
			if(map[x][y].getT() == 0 && this.hasRemaining(t)&&!first){
				//set the possible values into the possible arraylist of that node.
				//for(int i=0; i<=(Math.abs(dt-t+depth)/rep); i++)
				map[x][y].possible.add(t);
				//System.out.println("EchoTap("+x+", "+y+"): depth="+depth+", possible add: t="+t);
			}	
			if(dest == null&& checkBounds(x,y-1) && depth>0&& map[x][y-1].getT() == 0 &&t>0 && t<(map.length*map.length+1)){
				if(y-1<map.length && y-1>=0 && t-inc >0 && t-inc <=map.length*map.length){
					if(!map[x][y-1].possible.contains(t-inc)){
						//System.out.print("west: ");
						peta.setNorth(new Node(x, y-1, t+inc));
						//if(first|| x!=source.getX() || y-1!=source.getY())
							retval = this.echoTap(peta.getNorth(), depth-1, inc, false, dest, peta, rep);
						
							
					}
				}
			}
			else if((dy<=y)&&checkBounds(x,y-1) && depth>0&& map[x][y-1].getT() == 0&&t>0 && t<(map.length*map.length+1)){
				peta.setNorth(new Node(x, y-1, t+inc));
				/*if(map[x][y-1].possible.contains(t+inc)){
					map[x][y-1].possible.add(t+inc);
				}else if(map[x][y-1].possible.contains(t-inc)){
					map[x][y-1].possible.add(t-inc);
				}*/
				//System.out.print("west2: ");
				//if(first|| x!=source.getX() || y-1!=source.getY())
					retval = this.echoTap(peta.getNorth(), depth-1, inc, false, dest, peta, rep);
				
			}
			if(dest == null&& checkBounds(x+1,y) && depth>0 && map[x+1][y].getT() == 0 &&t>0 && t<(map.length*map.length+1)){
				if(x+1<map.length && x+1>=0 && t-inc >0 && t-inc <=map.length*map.length){
					if(!map[x+1][y].possible.contains(t-inc)){
						peta.setEast(new Node(x+1, y, t+inc));
						//System.out.print("south: ");
						//if(first|| x!=source.getX() || y!=source.getY())
							retval = this.echoTap(peta.getEast(), depth-1, inc, false, dest, peta, rep);
						
					}
				}

			}else if((dx>=x)&& checkBounds(x+1,y) && depth>0 && map[x+1][y].getT() == 0 &&t>0 && t<(map.length*map.length+1)){
				peta.setEast(new Node(x+1, y, t+inc));
				/*if(map[x+1][y].possible.contains(t+inc)){
					map[x+1][y].possible.add(t+inc);
				}else if(map[x+1][y].possible.contains(t-inc)){
					map[x+1][y].possible.add(t-inc);
				}*/
				//System.out.print("south2: ");
				//if(first|| x+1!=source.getX() || y!=source.getY())
					retval = this.echoTap(peta.getEast(), depth-1, inc, false, dest, peta, rep);
				
			}
			if(dest == null && checkBounds(x,y+1) && depth>0 && map[x][y+1].getT() == 0 &&t>0 && t<(map.length*map.length+1)){
				if(y+1<map.length && y+1>=0 && t-inc >0 && t-inc <=map.length*map.length){
					if(!map[x][y+1].possible.contains(t-inc)){
						peta.setSouth(new Node(x, y+1, t+inc));
						//System.out.print("east: ");
						//if(first|| x!=source.getX() || y+1!=source.getY())
							retval = this.echoTap(peta.getSouth(), depth-1, inc, false, dest, peta, rep);
						
					}
				}

			}else if((dy>=y) && checkBounds(x,y+1) && depth>0 && map[x][y+1].getT() == 0 &&t>0 && t<(map.length*map.length+1)){
				//south

				peta.setSouth(new Node(x, y+1, t+inc));
				/*if(map[x][y+1].possible.contains(t+inc)){
					map[x][y+1].possible.add(t+inc);
				}else if(map[x][y+1].possible.contains(t-inc)){
					map[x][y+1].possible.add(t-inc);
				}*/
				//System.out.print("east2: ");
				//if(first|| x!=source.getX() || y+1!=source.getY())
					retval = this.echoTap(peta.getSouth(), depth-1, inc, false, dest, peta, rep);
				
			}
			if(dest == null&&checkBounds(x-1,y) && depth>0&& map[x-1][y].getT() == 0 &&t>0 && t<(map.length*map.length+1)){
				//west
				if(x-1<map.length && x-1>=0 && t-inc >0 && t-inc <=map.length*map.length){
					if(!map[x-1][y].possible.contains(t-inc)){
						peta.setWest(new Node(x-1, y, t+inc));
						//System.out.print("north: ");
						//if(first|| x-1!=source.getX() || y!=source.getY())
						retval = this.echoTap(peta.getWest(), depth-1, inc, false, dest,peta, rep);
						
					}
				}


			}else if((dx<=x) && checkBounds(x-1,y) && depth>0&& map[x-1][y].getT() == 0 &&t>0 && t<(map.length*map.length+1)){
				//west
				peta.setWest(new Node(x-1, y, t+inc));
				/*if(map[x-1][y].possible.contains(t+inc)){
					map[x-1][y].possible.add(t+inc);
				}else if(map[x-1][y].possible.contains(t-inc)){
					map[x-1][y].possible.add(t-inc);
				}*/
				//System.out.print("north2: ");
				//if(first|| x-1!=source.getX() || y!=source.getY())
				retval = this.echoTap(peta.getWest(), depth-1, inc, false, dest, peta, rep);
				
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

	public boolean echoTap(int val, int depth, int inc, int dval, int rep){
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
			return this.echoTap(peta, depth, inc, true, dest, null, rep+1);
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
				double ants = 0.0;
				if(value[i]!=null){
					double percent = ((double)(count[i]*count[i]))/((double)pval.size());
					//percent = percent*percent;
					//percent = percent/spread(value[i]);
					if(x+1<map.length&&x+1>=0){
						if(map[x+1][y].getT()==value[i]+1 || map[x+1][y].getT()==value[i]-1){
							percent = percent+ants;
						}
					}else if(x-1<map.length&&x-1>=0){
						if(map[x-1][y].getT()==value[i]+1 || map[x-1][y].getT()==value[i]-1){
							percent = percent+ants;
						}
					}else if(y+1<map.length&&y+1>=0){
						if(map[x][y+1].getT()==value[i]+1 || map[x][y+1].getT()==value[i]-1){
							percent = percent+ants;
						}
					}else if(y-1<map.length&&y-1>=0){
						if(map[x][y-1].getT()==value[i]+1 || map[x][y-1].getT()==value[i]-1){
							percent = percent+ants;
						}
					}/**/
					retval.add(new idPair(value[i], percent ));
				}

			}
		}
		Collections.sort(retval, new Comparator<idPair>() {
			public int compare(idPair a, idPair b) {
				int retval = (int)((100*b.getPercent()/*/spread(b.getValue())*/ )- (100*a.getPercent()/*/spread(b.getValue())*/));

				return retval;
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

	public double spread(int pos){
		double retval = 0;
		for(int i=0; i<map.length; i++){
			for(int j=0; j<map.length; j++){
				if(map[i][j].possible.contains(pos))
					retval++;
			}
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
				int retval = (int)((100*b.getPercent()/*/spread(b.getValue())*/ )- (100*a.getPercent()/*/spread(b.getValue())*/));

				return retval;
			}
		});
		System.out.println(probRef.get(0));
		return new Node(probRef.get(0).getX(), probRef.get(0).getY(), probRef.get(0).getValue());
	}

	public Node highestProb(int pick){
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
				int retval = (int)((100*b.getPercent()/*/spread(b.getValue())*/ )- (100*a.getPercent()/*/spread(b.getValue())*/));

				return retval;
			}
		});
		//System.out.println(probRef.get(pick));
		if(probRef.size()<=pick){
			return new Node(probRef.get(probRef.size()-1).getX(), probRef.get(probRef.size()-1).getY(), probRef.get(probRef.size()-1).getValue());
		}else{
			return new Node(probRef.get(pick).getX(), probRef.get(pick).getY(), probRef.get(pick).getValue());
		}
		
	}
	
	public Node pickingProb(int pick, int pop){
		ArrayList<idPair> probRef = new ArrayList<idPair>();

		for(int i = 0; i<this.map.length; i++){
			for(int j=0; j<this.map.length; j++){
				if(!this.highestPset(i,j).isEmpty()){
					idPair temp = this.highestPset(i,j).get(pop);
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
				int retval = (int)((100*b.getPercent()/*/spread(b.getValue())*/ )- (100*a.getPercent()/*/spread(b.getValue())*/));

				return retval;
			}
		});
		//System.out.println(probRef.get(pick));
		return new Node(probRef.get(pick).getX(), probRef.get(pick).getY(), probRef.get(pick).getValue());
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
