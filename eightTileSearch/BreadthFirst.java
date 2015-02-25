import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirst {
	
	// initial and final states of the puzzle	
	private static ArrayList<Integer> initial_state = null;
	private static ArrayList<Integer> final_state = null;
	// Repeated States ArrayList
	private static ArrayList<ArrayList<Integer>> expanded_states =new ArrayList<ArrayList<Integer>>();
	// Queue for BreathFirst Search
	private static Queue<Node> stateQueue = null;
	private static boolean verbose;
	private static boolean repeats;

	/*  ArrayList State Structure 
	 *	x,x,x
	 *	x,x,x
	 *	x,x,x
	 *
	 * 	Search Graph
	 * 	0 <= Node Class
	 * 
	 * 		0
	 *	   / \
	 *	  0   0
	 */

	public BreadthFirst(ArrayList<Integer> initial_state, ArrayList<Integer> final_state, boolean verbose, boolean repeats) {
		System.out.println("Current State");
		showState(initial_state);
		System.out.println("Final State");
		showState(final_state);
		System.out.println("Finding Best Path...");
		// Set class variables for initial and final states 
		this.initial_state = initial_state;
		this.final_state = final_state;
		this.verbose = verbose;
		this.repeats = repeats;

	}

	// Should return the best path to the final state 
	public static ArrayList<Node> run(){
		/*
			--- Breath First Search --- 

			1.    Enqueue the root node
			2.    Dequeue a node and examine it
				i) If the element sought is found in this node, quit the search and return a result.
				ii) Otherwise enqueue any successors (the direct child nodes) that have not yet been discovered.
			3.    If the queue is empty, every node on the graph has been examine quit the search and return "not found".
			4.    If the queue is not empty, repeat from Step 2.
	
		*/

	   	// Set current_state to initial_state
		Node current_state = new Node(initial_state,null,null,null);
		expanded_states = new ArrayList<ArrayList<Integer>>();

		// Add initial_state to the queue (root of tree)
		queueStartState(current_state);
		
		// Get current State
		current_state = getFromQueue();
		if(repeats != false){
			expanded_states.add(current_state.state);
		}
		
		int time = 0; 
		// Continue BreadthFirst until we've reached our goal state or the queue is empty
		while( (!current_state.state.equals(final_state)) && current_state != null ){

			// Get children from current state
			ArrayList<Node> children = CreateChildren(current_state);

			// Add new children to the queue
			addChildrenToQueue(current_state, children);	
			
			// Get next state from queue,
			// returns null if queue is empty	
			current_state = getFromQueue();	
			// Add new State to the list of Expanded States to ignore repeats
			if(repeats != false){
				expanded_states.add(current_state.state);
			}
			
			time++; 
			
		}
		
		// Construct best path, if we've found one
		ArrayList<Node> bestPathFound = null;
		
		if( (!stateQueue.isEmpty()) ){
			bestPathFound =	constructBestPath(current_state);
		}
		
		System.out.println("Nodes expanded " + time);
		// return null if no best path, else return best path	
		return bestPathFound;
	}


	@SuppressWarnings("unchecked")
	private static ArrayList<Node> CreateChildren(Node current_state) {
		
		ArrayList<Integer> workingState = current_state.state;
		
		ArrayList<Node> children = new ArrayList<Node>();
		
		// If blank space ('0') is in the center
		if(workingState.get(4) == 0){
			int[] changes = {1,3,5,7};
			String[] directions = {"Up","Right","Left","Down"};
			for(int x=0; x < 4; x++){
				ArrayList<Integer> newState;
				newState = changeState((ArrayList<Integer>)workingState.clone(),changes[x],4);
				children.add( new Node(newState, current_state, null, directions[x]) );
			}
		// If blank space ('0') is in the sides
		}else if(workingState.get(1) == 0 || workingState.get(3) == 0 || workingState.get(5) == 0 || workingState.get(7) == 0){
			if(workingState.get(1) == 0){
				int[] changes = {0,2,4};
				String[] directions = {"Left","Right","Down"};
				for(int x=0; x < 3; x++){
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>)workingState.clone(),changes[x],1);
					children.add( new Node(newState, current_state, null, directions[x]) );
				}
			}else if(workingState.get(3) == 0){
				int[] changes = {0,4,6};
				String[] directions = {"Up","Right","Down"};
				for(int x=0; x < 3; x++){
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>)workingState.clone(),changes[x],3);
					children.add( new Node(newState, current_state, null, directions[x]) );
				}
			}else if(workingState.get(5) == 0){
				int[] changes = {2,4,8};
				String[] directions = {"Up","Left","Down"};
				for(int x=0; x < 3; x++){
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>)workingState.clone(),changes[x],5);
					children.add( new Node(newState, current_state, null, directions[x]) );
				}
			}else if(workingState.get(7) == 0){
				int[] changes = {6,4,8};
				String[] directions = {"Left","Up","Right"};
				for(int x=0; x < 3; x++){
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>)workingState.clone(),changes[x],7);
					children.add( new Node(newState, current_state, null, directions[x]) );
				}
			}
		// If blank space ('0') is in the corner
		}else if(workingState.get(0) == 0 || workingState.get(2) == 0 || workingState.get(6) == 0 || workingState.get(8) == 0){
			if(workingState.get(0) == 0){
				int[] changes = {1,3};
				String[] directions = {"Right","Down"};
				for(int x=0; x < 2; x++){
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>)workingState.clone(),changes[x],0);
					children.add( new Node(newState, current_state, null, directions[x]) );
				}
			}else if(workingState.get(2) == 0){
				int[] changes = {1,5};
				String[] directions = {"Left","Down"};
				for(int x=0; x < 2; x++){
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>)workingState.clone(),changes[x],2);
					children.add( new Node(newState, current_state, null, directions[x]) );
				}
			}else if(workingState.get(6) == 0){
				int[] changes = {3,7};
				String[] directions = {"Up","Left"};
				for(int x=0; x < 2; x++){
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>)workingState.clone(),changes[x],6);
					children.add( new Node(newState, current_state, null, directions[x]) );
				}
			}else if(workingState.get(8) == 0){
				int[] changes = {7,5};
				String[] directions = {"Left","Up"};
				for(int x=0; x < 2; x++){
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>)workingState.clone(),changes[x],8);
					children.add( new Node(newState, current_state, null, directions[x]) );
				}
			}
		}
		
		if(repeats != false){
			children = removeRepeats(children);
		}
		
		return children;
	}
	
	// Remove the repeated states from our children list
	private static ArrayList<Node> removeRepeats(ArrayList<Node> children) {
		ArrayList<Node> tempList = new ArrayList<Node>();
		
		for(int x=0; x < children.size(); x++){
			// If our child state isn't included in the repeated states don't do it
			if( !expanded_states.contains(children.get(x).state) ){
				tempList.add(children.get(x));
			}
		}
		
		return tempList;
	}

	// Method to change the location of the blank space (current_state, position to move, blank space)
	private static ArrayList<Integer> changeState(ArrayList<Integer> current, int i, int j) {
		if(verbose == true){
			showState(current);
			System.out.println("Shifting "+current.get(i)+"->"+current.get(j));
		}
		int x = current.get(i);
		int y = current.get(j);
		
		// x -> j
		// y -> i
		current.set(i, y);
		current.set(j, x);
		if(verbose == true){
			showState(current);
		}
		return current;
	}

	private static void addChildrenToQueue(Node parent ,ArrayList<Node> children) {
		// Thought this method would be harder, meh still gunna leave this
		stateQueue.addAll(children);
				
	}

	private static Node getFromQueue() {
		// Thought this method would be harder, meh still gunna leave this
		//System.out.println(stateQueue);
		return stateQueue.poll();
	}

	private static void queueStartState(Node initial_state) {
		stateQueue = new LinkedList<Node>();
		stateQueue.add(initial_state);
		
	}
	private static ArrayList<Node> constructBestPath(Node current_state) {
		ArrayList<Node> bestPath = new ArrayList<Node>();
		// Walk back up the tree to the root and construct path
		Node current = new Node();
		current = current_state;
		
		//If we've reached the root return
		while(current.parent != null){
			//Add state of board
			bestPath.add(current);
			//Switch to new State father up the tree
			Node temp = new Node();
			temp = current;
			current = temp.parent;
		}
		bestPath.add(current);
		// Return the BestPath
		return bestPath;
	}
	
	private static void showState(ArrayList<Integer> state){
		System.out.println("------------------");
		System.out.println(state.get(0) +"|"+ state.get(1) +"|"+ state.get(2));
		System.out.println(state.get(3) +"|"+ state.get(4) +"|" + state.get(5));
		System.out.println(state.get(6) +"|"+ state.get(7) +"|" + state.get(8));
		System.out.println("------------------");
	}

}
