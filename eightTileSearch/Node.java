import java.util.ArrayList;

public class Node implements Comparable<Node> {
	
	ArrayList<Integer> state;
	Node parent;
	ArrayList<Node> children;
	String direction;
	int depth; 


	public Node(ArrayList<Integer> state, Node parent, ArrayList<Node> children, String direction){
		this.state = state;
		this.parent = parent;
		this.children = children;
		this.direction = direction;
	}


	public Node(ArrayList<Integer> state, Node parent, ArrayList<Node> children, String direction, int depth) {
		this.state = state;
		this.parent = parent;
		this.children = children;
		this.direction = direction;
		this.depth = depth; 
	}

	public Node() {
		
	}

	@Override
	public int compareTo(Node n) {		
		return  findPriority(n) - findPriority(this);
	}	
	
	private int findPriority(Node n){
		int priority = 0;
		
		// Find the number of states out of position
		// using an if block, because why not
		if(n.state.get(0) == 0){
			priority++;
		}
		if(n.state.get(1) == 1){
			priority++;
		}
		if(n.state.get(2) == 2){
			priority++;
		}
		if(n.state.get(3) == 3){
			priority++;
		}
		if(n.state.get(4) == 4){
			priority++;
		}
		if(n.state.get(5) == 5){
			priority++;
		}
		if(n.state.get(6) == 6){
			priority++;
		}
		if(n.state.get(7) == 7){
			priority++;
		}
		if(n.state.get(8) == 8){
			priority++;
		}
		
		return priority; 
	}
	

}
