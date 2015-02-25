import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/*
 * 	Artificial Intelligence CS 491 Special Topics
 * 	8-Tile Search Algorithms
 * 	Created by: Caitlin Schaub and John Stupka
 * 	Professor: Heather Amthauer
 * 	
 * 	Summary: 
 * 	A program to implement search algorithm for the "8-tile" game, 
 * 	the algorithms that will be used are.
 * 	1) Breadth-First Search 
 * 	2) Depth-First Search
 * 	3) Best-First Search
 *    
 */

public class Main {
	// Create Fame and Panel
	static JFrame frame = new JFrame();
	static JPanel pane = new JPanel();

		@SuppressWarnings("deprecation")
		public static void main(String[] args) {		
		ArrayList<Integer> initial_state = null;
		ArrayList<Integer> final_state = null;
		boolean verbose = false;
		boolean repeats = false;
		long startTime = System.currentTimeMillis();
		String s = null;
		int search = 0;
		
		Console c = System.console();
        if (c == null && args.length < 1) {
            System.err.println("No console.");
            
	        // UI Block for Program Settings
	        Object[] possibilities = {"Breadth-First Search Algorithm", "Depth-First Search Algorithm", "Best-First Search Algorithm"};
			s = (String)JOptionPane.showInputDialog(new JFrame(),"Which Algorithm would you like to use?\n","Algorithm Picker",
														JOptionPane.PLAIN_MESSAGE,null,possibilities,"Breadth-First Search Algorithm");
			if ((s == null) && (s.length() <= 0)) {
				System.exit(0);
			}
			
			int reply;
				reply = JOptionPane.showConfirmDialog(null, "Would you like to run the verbose version of this algorithm?", "Verbose Check", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	System.out.println("Verbose used");
		        	verbose = true;
		        } else {
		        	System.out.println("Verbose not used");
		        	verbose = false;
		        }
		        
				reply = JOptionPane.showConfirmDialog(null, "Would you like to remove repeated states in this version of this algorithm?", "Repeat State Check", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	System.out.println("Repeats allowed");
		        	repeats = true;
		        } else {
		        	System.out.println("Repeats not allowed");
		        	repeats = false;
		        }	
		        
		        //Ask for User input for start and end states
		        JOptionPane.showMessageDialog(new JFrame(), "What does your 8-tile game look like?(Inital State)");
		        initial_state = setState();
		        JOptionPane.showMessageDialog(new JFrame(), "Which would you like your 8-tile to look like?(Final State)");
		        final_state = setState();
		        ArrayList<ArrayList<Integer>> bestPath = null;
	            
        }else if(c != null && args.length < 1){
        	do{
		        System.out.println("Please Select you're search:");
		        System.out.println("1.	Breadth-First Search Algorithm");
		        System.out.println("2.	Depth-First Search Algorithm");
		        System.out.println("3.	Best-First Search Algorithm");
		        search = (int)Integer.parseInt(c.readLine(""));
        	}while( !((search == 1) || (search == 2) || (search == 3)) );
        	
        	String input;
        	do{
		        System.out.println("Would you like to run the verbose version of this algorithm?");
		        input = c.readLine("Please use \"Y or N\":");
		        if(input.equals("Y")){
		        	verbose = true;
		        }else if(input.equals("N")){
		        	verbose = false;
		        }

        	}while( !((input.equals("Y")) || (input.equals("N"))) );
        	do{
		        System.out.println("Would you like to remove repeated states in this version of this algorithm?");
		        input = c.readLine("Please use \"Y or N\":");
		        if(input.equals("Y")){
		        	repeats = true;
		        }else if(input.equals("N")){
		        	repeats = false;
		        }

	     	}while( !((input.equals("Y")) || (input.equals("N"))) );
	        System.out.println("Please enter the initial state?");
	        initial_state = setStateConsole(c);
	        System.out.println("Please enter the final state?");
	        final_state = setStateConsole(c);
        }else if(args.length > 0){
        		
        	// Parse command line arguments, if received
        	ArrayList<String> arguments = new ArrayList<String>();
        	for(int x=0; x < args.length; x++){
        		arguments.add(args[x]);
        	}
			arguments.add(args[0]);
        	if(arguments.contains("-h") || arguments.contains("-H")){
        		usage(c);
				System.exit(0);
        	}else if(arguments.contains("-B") || arguments.contains("-D") || arguments.contains("-b") || arguments.contains("-v") || arguments.contains("-r") ){
	        	if(arguments.contains("-B")){
	        		search = 1;
	        	}else if(arguments.contains("-D")){
	        		search = 2;
	        	}else if(arguments.contains("-b")){
	        		search = 3;
	        	}
	        	if(arguments.contains("-v")){
	        		verbose = true;
	        	}
	        	if(arguments.contains("-r")){
	        		repeats = true;
	        	}
        	
	        	System.out.println("Please enter the initial state?");
		        initial_state = setStateConsole(c);
		        System.out.println("Please enter the final state?");
		        final_state = setStateConsole(c);
    		}else{
				usage(c);
				System.exit(0);
			}	
        }

			System.out.println("Verbose - " + verbose);
 			System.out.println("Repeats - " + repeats); 

			ArrayList<Node> bestPath = null;
		    if(search == 1 || s == "Breadth-First Search Algorithm" ){
		    	System.out.println("Breadth-First Search Algorithm is starting...");
		    	BreadthFirst breadthfirstsearch = new BreadthFirst(initial_state, final_state, verbose, repeats);
		    	bestPath = breadthfirstsearch.run();
		    }else if(search == 2 || s == "Depth-First Search Algorithm"){
		    	System.out.println("Depth-First Search Algorithm is starting...");
		    	DepthFirst depthfirstsearch = new DepthFirst(initial_state, final_state, verbose, repeats);
		    	bestPath = depthfirstsearch.run(); 
		    }else if(search == 3 || s == "Best-First Search Algorithm"){
		    	System.out.println("Best-First Search Algorithm is starting...");
		    	BestFirst bestfirstsearch = new BestFirst(initial_state, final_state, verbose, repeats);
				bestPath = bestfirstsearch.run();
			}
			

			long endTime = System.currentTimeMillis();
			System.out.println("Time " + (endTime - startTime) +" ms");
			
			if(bestPath != null){
			    System.out.println("Total Number of Moves: " + (bestPath.size()-1 ));
			}else{
			    System.out.println("Total Number of Moves: 0");
			}
				
		    showBestPath(bestPath);
		    //Finish Program
		    System.exit(0);
	}
	
	private static void usage(Console c) {
		// How to use the program, printed to console
		System.out.println("To use this program, please use the following commands:");
		System.out.println("	-B	Use Breadth-First Search");
		System.out.println("	-D	Use Depth-First Search");
		System.out.println("	-b	Use Best-First Search");
		System.out.println("	-v	Use Verbose Version of search");
		System.out.println("	-r	Remove repeated states in search");
	}

	private static ArrayList<Integer> setStateConsole(Console c) {
		boolean moveOn = false;
		ArrayList<Integer> ourState;
		do{
			System.out.println("Please be sure to use a lower case 'x' for the blank space:");
	        String strRow1 = c.readLine("x,x,x : ");
	        String strRow2 = c.readLine("x,x,x : ");
	        String strRow3 = c.readLine("x,x,x : ");
			
	        // Here is where the magic happens, clean the inputs.
	        // if returned null, we repeat until the user gives us the correct input
				
			ourState = cleanInput(strRow1, strRow2, strRow3);
	        if(ourState != null){
	        	moveOn = true;
	        }else{
	        	System.out.println("Please be sure to use one number 1-8 and leave one X");
	        }
   
		}while(!moveOn);
		
		return ourState;
	}

	private static void showBestPath(ArrayList<Node> bestPath) {
		System.out.println("Best Path -------------------");
		//System.out.println("Total Number of Moves: " + bestPath.size() );
		for(int x = bestPath.size(); x > 0; x--){
			showState(bestPath.get(x-1).state);
		}
		System.out.println("Best Path -------------------");
		
	}
	
	private static void showState(ArrayList<Integer> state){
		System.out.println("------------------");
		System.out.println(state.get(0) +"|"+ state.get(1) +"|"+ state.get(2));
		System.out.println(state.get(3) +"|"+ state.get(4) +"|" + state.get(5));
		System.out.println(state.get(6) +"|"+ state.get(7) +"|" + state.get(8));
		System.out.println("------------------");
	}

	//Loop until we've reach the state we want
	static ArrayList<Integer> setState() {
		boolean moveOn = false;
		ArrayList<Integer> ourState;
		do{
			JPanel p = new JPanel(new BorderLayout(5,5));
	        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
	        labels.add(new JLabel("Row-1", SwingConstants.RIGHT));
	        labels.add(new JLabel("Row-2", SwingConstants.RIGHT));
	        labels.add(new JLabel("Row-3", SwingConstants.RIGHT));
	        p.add(labels, BorderLayout.WEST);
	
	        JPanel controls = new JPanel(new GridLayout(0,1,2,2));
	        JTextField Row1 = new JTextField("x,x,x");
	        controls.add(Row1);
	        JTextField Row2 = new JTextField("x,x,x");
	        controls.add(Row2);
	        JTextField Row3 = new JTextField("x,x,x");
	        controls.add(Row3);
	        p.add(controls, BorderLayout.CENTER);
	        JOptionPane.showMessageDialog(new JFrame(), p, "Set State", JOptionPane.QUESTION_MESSAGE);
	        String strRow1 = Row1.getText();
	        String strRow2 = Row2.getText();
	        String strRow3 = Row3.getText();
	        // Here is where the magic happens, clean the inputs.
	        // if returned null, we repeat until the user gives us the correct input
	        ourState = cleanInput(strRow1, strRow2, strRow3);
	        if(ourState != null){
	        	moveOn = true;
	        }else{
	        	JOptionPane.showMessageDialog(new JFrame(), "Please be sure to use one number 1-8 and leave one X");
	        }
	        
		}while(!moveOn);
		
		return ourState;
    }
	private static ArrayList<Integer> cleanInput(String one, String two, String three){
		
		String[] state1 = one.split(",");
		String[] state2 =  two.split(",");
		String[] state3 = three.split(",");
		String[] state = {state1[0],state1[1],state1[2],
						  state2[0],state2[1],state2[2],
						  state3[0],state3[1],state3[2],};
		//state array should have one of each of the following string objects
		String[] requiredInput = {"1","2","3","4","5","6","7","8","x"};
		for(int x=0; x < state.length; x++){
			if(!Arrays.asList(state).contains(requiredInput[x])){
				return null;
			}
		}
		ArrayList<Integer> intState = new ArrayList<Integer>();
		for(int x=0; x < state.length; x++){
			if(state[x].equals("x")){
				intState.add(Integer.parseInt("0"));
			}else{
				intState.add(Integer.parseInt(state[x]));
			}
		}
		return intState;
	}

	
	//Working on this, will allow visual of the actual algorithm progress
	static void setUpGui(){
		
		frame.setTitle("8-Tile Search Algorithms");
		frame.setSize(500, 500);
		//Deprecation warning for show() on swing?
		pane.show(true);
		frame.show(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(pane);
		
	}

}
