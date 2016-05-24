import java.util.*;

public class Board {
	private int tiles[][];
	private int dimension;
	int blankx, blanky;
	public Board(){
		Scanner sc = new Scanner(System.in);
		int i, j;
		dimension = 3; /* keeping n = 3 here. if for any other n, just change this value*/
		tiles = new int[dimension][dimension];
		
		System.out.println("Enter the starting state of the board");
		
		for(i = 0; i < dimension; i++){
			for(j = 0; j < dimension; j++){
				tiles[i][j] = sc.nextInt();
			}
		}
		
	}
	
	public Board(int board[][]){
		int i, j;
		dimension = 3;
		tiles = new int[3][3];
		
		for(i = 0; i < dimension; i++){
			for(j = 0; j < dimension; j++){
				tiles[i][j] = board[i][j];
			}
		}
		
	}
	/*correct condition*/
	void cloneBoard(int board[][]){
		int i, j;
		
		for(i = 0; i < dimension; i++){
			for(j = 0; j < dimension; j++){
				tiles[i][j] = board[i][j];
			}
		}
				
	}
	/*reference code*/
	public Board deepCopy(Board src) {
	        int[][] dest = new int[src.dimension][src.dimension];

	        for (int i = 0; i < src.dimension; i++)
	            for (int j = 0; j < src.dimension; j++)
	                dest[i][j] = src.tiles[i][j];

	        Board copy = new Board(dest);
	        return copy; 
	  }
	/*works correctly*/
	boolean checkForGoal(){
		int i, j , rightvalue = 0;
		for(i = 0; i < dimension; i++){
			for(j = 0; j < dimension; j++){
				rightvalue++;
				if(tiles[i][j] == 0){
					continue;
				}
				
				if(tiles[i][j] != rightvalue){
					return false;
				}
			}
		}
		
		return true;
	}
	/* works correctly*/
	int manhattan(){
		int manhattan = 0, i , j;
		for(i = 0; i < dimension; i++){
			for(j = 0; j < dimension; j++){
			    if (tiles[i][j] != 0 && tiles[i][j] != i * dimension + j + 1) {
                    int initialX = (tiles[i][j] - 1) / dimension;
                    int initialY = tiles[i][j] - 1 - initialX * dimension;
                    int distance = Math.abs(i - initialX) + Math.abs(j - initialY);
                    manhattan += distance;
                }
			}
		}
		return manhattan;
	}
	/*works correctly*/
	public void findBlank(){
		int i, j;
		for(i = 0; i < dimension; i++){
			for(j = 0; j < dimension; j++){
				if(tiles[i][j] == 0){
					blankx = i;
					blanky = j;
					return;
				}
			}
		}
	}
	/*works correctly*/
	public void swap(int x, int y, int x2, int y2){
		int  temp;
					
		temp = this.tiles[x][y];
		this.tiles[x][y] = this.tiles[x2][y2];
		this.tiles[x2][y2] = temp;
		
		return;
	}
	
	public Iterable<Board> giveNeighbours(){
		Stack<Board> neighbours = new Stack<Board>();
		
		Board down = new Board(this.tiles);
		Board up = new Board(this.tiles);
		Board left = new Board(this.tiles);
		Board right = new Board(this.tiles);
		findBlank();
		
		if((blankx +  1 < dimension)){
			down.cloneBoard(tiles);
			down.swap(blankx, blanky, blankx + 1, blanky);
			down.printGrid();
			neighbours.push(down);
		}
	
		if((blankx - 1 >= 0)){
			up.cloneBoard(tiles);
			up.swap(blankx, blanky, blankx - 1, blanky);
			up.printGrid();
			neighbours.push(up);
		}
		
		if((blanky + 1 < dimension)){
			right.cloneBoard(tiles);
			right.swap(blankx, blanky, blankx, blanky + 1);
			right.printGrid();
			neighbours.push(right);
		}
		
		if((blanky - 1 >= 0)){
			left.cloneBoard(tiles);
			left.swap(blankx, blanky, blankx , blanky - 1);
			left.printGrid();
			neighbours.push(left);
		}
						
		return neighbours;
	}
	
	/*reference code */
	public Iterable<Board> neighbors() {

	        Stack<Board> stack = new Stack<Board>();
	       
	        int row_blank = -1; 
	        int col_blank = -1; 
	        findBlank:
	        for (int i = 0; i < this.dimension; i++) {
	            for (int j = 0; j < this.dimension; j++) {
	                if (this.tiles[i][j] == 0) {
	                    row_blank = i;
	                    col_blank = j; 
	                    break findBlank;
	                }
	            }
	        }

	        // swap blank with tile to the left, if possible
	        if (col_blank - 1 >= 0) {
	            Board left = deepCopy(this);
	            left.tiles[row_blank][col_blank] = this.tiles[row_blank][col_blank - 1];
	            left.tiles[row_blank][col_blank - 1] = 0;
	          
	            stack.push(left); 
	        }

	        // swap blank with tile to the right, if possible
	        if (col_blank + 1 < this.dimension) {
	            Board right = deepCopy(this);
	            right.tiles[row_blank][col_blank] = this.tiles[row_blank][col_blank + 1];
	            right.tiles[row_blank][col_blank + 1] = 0;
	            stack.push(right);
	          
	        }
	        
	        // swap blank with tile above, if possible
	        if (row_blank - 1 >= 0) {
	            Board up = deepCopy(this);
	            up.tiles[row_blank][col_blank] = this.tiles[row_blank - 1][col_blank];
	            up.tiles[row_blank - 1][col_blank] = 0;
	            stack.push(up);   
	          
	        }
	        
	        // swap blank with tile below, if possible
	        if (row_blank + 1 < this.dimension) {
	            Board down = deepCopy(this);
	            down.tiles[row_blank][col_blank] = this.tiles[row_blank + 1][col_blank];
	            down.tiles[row_blank + 1][col_blank] = 0;
	            stack.push(down);
	          
	        }

	        return stack;
	    }    	
	public Board twin() {
	        Board board = new Board(tiles);

	        for (int i = 0; i < dimension; i++) {
	            for (int j = 0; j < dimension - 1; j++) {
	                if (tiles[i][j] != 0 && tiles[i][j + 1] != 0) {
	                    board.swap(i, j, i, j + 1);
	                    return board;
	                }
	            }
	        }

	        return board;
	 }
	public void printGrid(){
		int i, j;
		for(i = 0; i < dimension; i++){
			for(j = 0; j < dimension; j++){
				System.out.print(" " + this.tiles[i][j]);
			}
			System.out.println();
		}
	}
	
/*	public static void main(String args[]){
		Board b = new Board();
		for(Board bd : b.neighbors()){
			bd.printGrid();
		}
	}*/
	
}
