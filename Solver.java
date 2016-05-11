import java.util.*;

class GraphNode implements Comparable<GraphNode>{
	int moves;
	Board bd;
	GraphNode previous;
	private int priority;
	
	GraphNode(Board bd, int moves, GraphNode previous){
		this.bd = bd;
		this.moves = moves;
		this.previous = previous;
	}
	
	public int getPriority() {
		/*comparing the priority of the graphNode one with 'this' GrpahNode priority */
		priority = this.moves + this.bd.manhattan();
		return priority;
	}
	
	public int compareTo(GraphNode g) {
		if(this.getPriority() > g.getPriority()){
			return 1;
		}else{
			return -1;
		}
	}
	
}


public class Solver {
	Stack<GraphNode> trace;
	PriorityQueue<GraphNode> minPQ;
	private int moves;
	
	public Solver(){
		trace = new Stack<GraphNode>();
		minPQ = new PriorityQueue<GraphNode>();
		moves = 0;
	}
	
	public void solve(Board initial){
		Board board = initial;
		GraphNode node = new GraphNode(board, 0, null);
		GraphNode temp;
		minPQ.add(node);
		
		while(true){
			temp = minPQ.poll();
			
			System.out.println("Heuristic" + temp.getPriority());
			
			if(temp.bd.checkForGoal()){
				trace.push(temp);
				temp.bd.printGrid();
				break;
			}
						
			for(Board b : temp.bd.neighbors()){
				if(temp.previous == null || !(temp.equals(temp.bd))){
					System.out.println("::::::::::::::::");
					//b.printGrid();
					
					minPQ.add(new GraphNode(b, temp.moves + 1, temp));
				}
			}
		}
	
		while(temp != null){
			trace.push(temp);
			System.out.println("------------------------");
			temp.bd.printGrid();
			temp = temp.previous;
		}
	}
	
	public static void main(String args[]){
		Board bd = new Board();
		Solver s = new Solver();
		GraphNode g = new GraphNode(bd, 0, null);
		s.solve(bd);
	}
}
