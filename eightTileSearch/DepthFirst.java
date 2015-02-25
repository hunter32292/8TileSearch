

import java.util.ArrayList;
import java.util.Stack;

public class DepthFirst {

	// the initial and final states
	private ArrayList<Integer> inital_state = null;
	private ArrayList<Integer> final_state = null;
	private Stack<Node> s = null;
	private static ArrayList<ArrayList<Integer>> expandedStates = new ArrayList<ArrayList<Integer>>();
	private	static boolean verbose;
	private static boolean repeats;
	ArrayList<Integer> checked; 

	// depth first search constructor
	public DepthFirst(ArrayList<Integer> initial_state, ArrayList<Integer> final_state2, boolean verbose,
			boolean repeats) {
		this.inital_state = initial_state;
		this.final_state = final_state2;
		this.verbose = verbose; 
		this.repeats = repeats;
	}

	public ArrayList<Node> run() {
		System.out.println("beginning State");
		showState(inital_state);

		System.out.println("final state");
		showState(final_state);

		System.out.println("Now Searching....");
		// create the data structures we will be using
		ArrayList<Node> bestPath = new ArrayList<Node>();
		s = new Stack<Node>();

		// Put start node on the stack
		Node currentState = new Node(inital_state, null, null, null, 0);
		s.push(currentState);

		int time = 0;
		// While the final state isn't solved
		while ( (!isGoal(currentState) || s.isEmpty()) && currentState != null ) {

				// If the depth on branch is less than 10, expand nodes
			if (currentState.depth < 10) {
				// Get children from current state14
				ArrayList<Node> children = createChildren(currentState);
				// Add new children to the queue
				s.addAll(children);
			}

			// get next Current state
			currentState = s.pop();

			if(repeats != false){
				expandedStates.add(currentState.state);
			}

			// get cost
			time++;
		}

		bestPath = findBestPath(currentState);

		System.out.println("Nodes expanded: " + time);
		return bestPath;
	}

	private ArrayList<Node> findBestPath(Node currentState) {
		ArrayList<Node> bestPath = new ArrayList<Node>();
		// create the best path
		do {
			bestPath.add(currentState);
			currentState = currentState.parent;
		} 
		while (currentState.parent != null); 
		return bestPath;
	}

	@SuppressWarnings("unchecked")
	private static ArrayList<Node> createChildren(Node currentState) {

		ArrayList<Integer> workingState = currentState.state;

		ArrayList<Node> children = new ArrayList<Node>();

		// If blank space ('0') is in the center
		if (workingState.get(4) == 0) {
			int[] changes = { 1, 3, 5, 7 };
			String[] directions = { "Up", "Right", "Left", "Down" };
			for (int x = 0; x < 4; x++) {
				ArrayList<Integer> newState;
				newState = changeState((ArrayList<Integer>) workingState.clone(), changes[x], 4);
				children.add(new Node(newState, currentState, null, directions[x], currentState.depth + 1));
			}
			// If blank space ('0') is in the sides
		} else if (workingState.get(1) == 0 || workingState.get(3) == 0 || workingState.get(5) == 0
				|| workingState.get(7) == 0) {
			if (workingState.get(1) == 0) {
				int[] changes = { 0, 2, 4 };
				String[] directions = { "Left", "Right", "Down" };
				for (int x = 0; x < 3; x++) {
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>) workingState.clone(), changes[x], 1);
					children.add(new Node(newState, currentState, null, directions[x], currentState.depth + 1));
				}
			} else if (workingState.get(3) == 0) {
				int[] changes = { 0, 4, 6 };
				String[] directions = { "Up", "Right", "Down" };
				for (int x = 0; x < 3; x++) {
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>) workingState.clone(), changes[x], 3);
					children.add(new Node(newState, currentState, null, directions[x], currentState.depth + 1));
				}
			} else if (workingState.get(5) == 0) {
				int[] changes = { 2, 4, 8 };
				String[] directions = { "Up", "Left", "Down" };
				for (int x = 0; x < 3; x++) {
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>) workingState.clone(), changes[x], 5);
					children.add(new Node(newState, currentState, null, directions[x], currentState.depth + 1));
				}
			} else if (workingState.get(7) == 0) {
				int[] changes = { 6, 4, 8 };
				String[] directions = { "Left", "Up", "Right" };
				for (int x = 0; x < 3; x++) {
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>) workingState.clone(), changes[x], 7);
					children.add(new Node(newState, currentState, null, directions[x], currentState.depth + 1));
				}
			}
			// If blank space ('0') is in the corner
		} else if (workingState.get(0) == 0 || workingState.get(2) == 0 || workingState.get(6) == 0
				|| workingState.get(8) == 0) {
			if (workingState.get(0) == 0) {
				int[] changes = { 1, 3 };
				String[] directions = { "Right", "Down" };
				for (int x = 0; x < 2; x++) {
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>) workingState.clone(), changes[x], 0);
					children.add(new Node(newState, currentState, null, directions[x], currentState.depth + 1));
				}
			} else if (workingState.get(2) == 0) {
				int[] changes = { 1, 5 };
				String[] directions = { "Left", "Down" };
				for (int x = 0; x < 2; x++) {
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>) workingState.clone(), changes[x], 2);
					children.add(new Node(newState, currentState, null, directions[x], currentState.depth + 1));
				}
			} else if (workingState.get(6) == 0) {
				int[] changes = { 3, 7 };
				String[] directions = { "Up", "Left" };
				for (int x = 0; x < 2; x++) {
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>) workingState.clone(), changes[x], 6);
					children.add(new Node(newState, currentState, null, directions[x], currentState.depth + 1));
				}
			} else if (workingState.get(8) == 0) {
				int[] changes = { 7, 5 };
				String[] directions = { "Left", "Up" };
				for (int x = 0; x < 2; x++) {
					ArrayList<Integer> newState;
					newState = changeState((ArrayList<Integer>) workingState.clone(), changes[x], 8);
					children.add(new Node(newState, currentState, null, directions[x], currentState.depth + 1));
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
			if( !expandedStates.contains(children.get(x).state) ){
				tempList.add(children.get(x));
			}
		}
		
		return tempList;
	}

	// Method to change the location of the blank space (currentState, position
	// to move, blank space)
	private static ArrayList<Integer> changeState(ArrayList<Integer> current, int i, int j) {
		if(verbose == true){
			showState(current);
			System.out.println("Shifting " + current.get(i) + "->" + current.get(j));
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

	public boolean isGoal(Node currentState) {
		return currentState.state.equals(final_state);
	}

	private static void showState(ArrayList<Integer> state) {
		System.out.println("------------------");
		System.out.println(state.get(0) + "|" + state.get(1) + "|" + state.get(2));
		System.out.println(state.get(3) + "|" + state.get(4) + "|" + state.get(5));
		System.out.println(state.get(6) + "|" + state.get(7) + "|" + state.get(8));
		System.out.println("------------------");
	}

}
