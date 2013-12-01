package Numbrix;

import java.io.*;
import java.util.*;


public class Main {
public static void main(String[] args) throws IOException{
	boolean active = true;
	String history = "Numbrix Puzzle History Created @";
	String history1 = ""; 
	Date aDate = new Date();
	history = history+aDate.toString()+"\n\n";
	Scanner scanner = new Scanner(System.in);
	while(active){
	int step1 = -1;
	do{
	System.out.println("\n\nWelcome to the Numbrix Program!\nThis program will allow you edit numbrix puzzles.\nPlease choose from the following options by typing in the corresponding number:");
	System.out.println("Rules of Game:\n1)only one of each number goes into the table\n2)numbers are adjacent to its increment\n3)puzzle is complete when all the numbers are in the table.");
	System.out.println(">1 open Numbrix from a .txt file");
	System.out.println(">2 Solve Numbrix from a .txt file");
	System.out.println(">3 create an empty Numbrix puzzle");
	System.out.println(">4 exit");
	step1 = scanner.nextInt();
	if(!((step1 > 0)&&(step1<=4)))
		System.out.println("Error: input was not 1, 2, or 3.");
	}while(!((step1 > 0)&&(step1<=4)));
	if(step1 == 1){
		
		history = history+"client> imported puzzle from .txt file\n";
		//import table size
		//the .txt files are formatted like so
		//Empty slots are annotated with a 0
		/*line	text
		 *	1| <size>
		 * 	2|	# # #
		 * 	3|  # # #
		 *  4|  # # #
		 *  
		 * Example:
		 * 1| 3
		 * 2| 1 0 0
		 * 3| 0 9 6
		 * 4| 0 0 0
		 */
		//only one puzzle is allowed per txt file.
		
		System.out.println("The puzzle file must be in the following format:\n#\n# # #\n# # #\n# # #\nPlease type in the file location including the text file:");
		//String fileloc = System.getProperty("user.dir")+"\\"+scanner.next();
		String fileloc = scanner.next();
		System.out.println("Reading puzzle from location: "+fileloc);
		while(!(fileloc.substring(fileloc.length()-4, fileloc.length())).equals(".txt")){
			System.out.println("Input is not a text file. ");//+fileloc.substring(fileloc.length()-4, fileloc.length()));
			System.out.println("Please type in the file location including the text file:");
			fileloc = scanner.next();
		}
		BufferedReader in = new BufferedReader(new FileReader(fileloc));
		String tampa= in.readLine();
		int size = Integer.parseInt(tampa);
		Map small = new Map(size);
		//import init parts
		//int temp;
		//System.out.println(size+"\ncontinue?\n");
		//String blah = scanner.next();
		for (int i=0; i<size; i++){
			tampa = in.readLine();
			int taurs[] = new int[size];
			for (int j=0, p=0; j<size*2; j+=2,p++){
				taurs[p] =  Integer.parseInt(tampa.substring(j, j+1));
				
			}
			
			for (int j=0; j<size; j++){
				
				if(taurs[j]>0 && taurs[j]<=size*size){
					small.addForce(i, j, taurs[j]);
				}
				else if (taurs[j]!=0)
					System.out.println("Error: While loading file, values are out of bounds. " + taurs[j]);
				
			}
		}
		System.out.println("Puzzle of size: "+ size+ " successfully loaded...");
		System.out.print(small);
		boolean getout = true;
		int x, y, t;
		do{
		System.out.println("\nInsert values in this format: x y value \nEach numer should be separated by a space\nPlease refer to the x and y axis for the coordinate values.");
		boolean edit = true;
		/*scanner.useDelimiter("[");
		scanner.useDelimiter(",");
		scanner.useDelimiter("]");*/
		y = scanner.nextInt();
		x = scanner.nextInt();
		t = scanner.nextInt();
		//System.out.println("["+x+","+y+","+t+"]");
		while (edit){
			if(small.addValue(x, y, t)){
				System.out.println("Sucessfully added " + t+ " to [" +y +", "+ x+"]");
				System.out.print(small);
				history1 = history1 + small.toString();
				edit=false;
			}
			else{
				System.out.println("Error: Illegal move!");
				System.out.println("\nInsert values in this format: x y value \nEach numer should be separated by a space Please refer to the x and y axis for the coordinate values.");
				System.out.println("To force quit type: -1 -1 -1");
				y = scanner.nextInt();
				x = scanner.nextInt();
				t = scanner.nextInt();
				if((y==-1)&&(x==-1)&&(t==-1))
					edit=false;
				//System.out.println("["+x+","+y+","+t+"]");
			}
		}
		System.out.println("Continue? (Y/N)");
		String key =scanner.next();
		while (!((key.equalsIgnoreCase("Y"))||((key.equalsIgnoreCase("N"))))){
			System.out.println("Please respond with a Y or N.");
			key =scanner.next();
		}
		if(key.equalsIgnoreCase("N"))
			getout = false;
		}while(getout);
		System.out.println("Returning to Main Menu...");
		in.close();
	}
	else if(step1==2){
		history = history+"client> imported puzzle from .txt file\n";
		//import table size
		//the .txt files are formatted like so
		//Empty slots are annotated with a 0
		/*line	text
		 *	1| <size>
		 * 	2|	# # #
		 * 	3|  # # #
		 *  4|  # # #
		 *  
		 * Example:
		 * 1| 3
		 * 2| 1 0 0
		 * 3| 0 9 6
		 * 4| 0 0 0
		 */
		//only one puzzle is allowed per txt file.
		
		System.out.println("The puzzle file must be in the following format:\n#\n# # #\n# # #\n# # #\nPlease type in the file location including the text file:");
		//String fileloc = System.getProperty("user.dir")+"\\"+scanner.next();
		String fileloc = scanner.next();
		System.out.println("Reading puzzle from location: "+fileloc);
		while(!(fileloc.substring(fileloc.length()-4, fileloc.length())).equals(".txt")){
			System.out.println("Input is not a text file. ");//+fileloc.substring(fileloc.length()-4, fileloc.length()));
			System.out.println("Please type in the file location including the text file:");
			fileloc = scanner.next();
		}
		BufferedReader in = new BufferedReader(new FileReader(fileloc));
		//System.out.println(in.readLine());
		String tampa= in.readLine();
		int size = Integer.parseInt(tampa);
		Map small = new Map(size);
		//import init parts
		//int temp;
		//System.out.println(size+"\ncontinue?\n");
		//String blah = scanner.next();
		for (int i=0; i<size; i++){
			tampa = in.readLine();
			
			String loco[] = tampa.split("\\s+");
			int taurs[] = new int[size];
			for (int p=0; p<loco.length;p++){
				
				taurs[p] =  Integer.parseInt(loco[p]);
				//System.out.println(taurs[p]);
			}
			
			for (int j=0; j<size; j++){
				
				if(taurs[j]>0 && taurs[j]<=size*size){
					//System.out.println(taurs[j]);
					small.addForce(i, j, taurs[j]);
				}
				else if (taurs[j]!=0)
					System.out.println("Error: While loading file, values are out of bounds. " + taurs[j]);
				
			}
		}
		System.out.println("Puzzle of size: "+ size+ " successfully loaded...");
		System.out.print(small);
		//boolean getout = true;
		//int x, y, t;
		if(small.AISolve())
			System.out.print("\nPuzzle solved:\n"+small);
		else
			System.out.print("\nError: Puzzle could not be solved:\n"+small);
		small.probString();
		System.out.println("Returning to Main Menu...");
		in.close();
	}
	else if (step1 == 3){
		int n = 1;
		boolean build = true;
		while (build){
			System.out.println("Please type in the size of the puzzle:");
			n = scanner.nextInt();
			if(n>=0 && n<100)
				build=false;
			else if (n<100)
				System.out.println("Error: number must be greater than 1");
			else {
				System.out.println("Warning: size > 99 will break formatting. Do you want to continue?(Y/N)");
				String key =scanner.next();
				while (!((key.equalsIgnoreCase("Y"))||((key.equalsIgnoreCase("N"))))){
					System.out.println("Please respond with a Y or N.");
					key =scanner.next();
				}
				if(key.equalsIgnoreCase("Y"))
					build = false;
			}
				
		}
		Map small = new Map(n);
		System.out.println("Building puzzle of size " + n +"...");
		history = history+"client> bulid empty puzzle of size " + n+"\n";
		System.out.print(small);
		history1 = history1 + small.toString();
		boolean getout = true;
		int x, y, t;
		do{
		System.out.println("\nInsert values in this format: x y value \nEach numer should be separated by a space\nPlease refer to the x and y axis for the coordinate values.");
		boolean edit = true;
		/*scanner.useDelimiter("[");
		scanner.useDelimiter(",");
		scanner.useDelimiter("]");*/
		y = scanner.nextInt();
		x = scanner.nextInt();
		t = scanner.nextInt();
		//System.out.println("["+x+","+y+","+t+"]");
		while (edit){
			if(small.addValue(x, y, t)){
				System.out.println("Sucessfully added " + t+ " to [" +y +", "+ x+"]");
				System.out.print(small);
				history1 = history1 + small.toString();
				edit=false;
			}
			else{
				System.out.println("Error: Illegal move!");
				System.out.println("\nInsert values in this format: x y value \nEach numer should be separated by a space Please refer to the x and y axis for the coordinate values.");
				System.out.println("To force quit type: -1 -1 -1");
				y = scanner.nextInt();
				x = scanner.nextInt();
				t = scanner.nextInt();
				if((y==-1)&&(x==-1)&&(t==-1))
					edit=false;
				//System.out.println("["+x+","+y+","+t+"]");
			}
		}
		System.out.println("Continue? (Y/N)");
		String key =scanner.next();
		while (!((key.equalsIgnoreCase("Y"))||((key.equalsIgnoreCase("N"))))){
			System.out.println("Please respond with a Y or N.");
			key =scanner.next();
		}
		if(key.equalsIgnoreCase("N"))
			getout = false;
		}while(getout);
		System.out.println("Returning to Main Menu...");
	}
	else {
		System.out.println("Exiting...");
		active = false;
	}
	}
	scanner.close();
	}

}
