package ie.gmit.sw.ai;

import ie.gmit.sw.ai.maze.Node;

public class Maze {
	
	private Node[][] maze;
	
	public Maze(int dimension){
		
		maze = new Node[dimension][dimension];
		init();
		buildMaze();
		
		int featureNumber = (int)((dimension * dimension)* 0.005);
		addFeature(1, 0, featureNumber); //1 is a sword, 0 is a hedge
		addFeature(2, 0, featureNumber); //2 is help, 0 is a hedge
		addFeature(3, 0, featureNumber); //3 is a bomb, 0 is a hedge
		addFeature(4, 0, featureNumber); //4 is a hydrogen bomb, 0 is a hedge
		
		featureNumber = (int)((dimension * dimension)* 0.005);
		addFeature(6, -1, featureNumber); //6 is a Black Spider, 0 is a hedge
		addFeature(7, -1, featureNumber); //7 is a Blue Spider, 0 is a hedge
		addFeature(8, -1, featureNumber); //8 is a Brown Spider, 0 is a hedge
		addFeature(9, -1, featureNumber); //9 is a Green Spider, 0 is a hedge
		addFeature(10, -1, featureNumber); //: is a Grey Spider, 0 is a hedge
		addFeature(11, -1, featureNumber); //; is a Orange Spider, 0 is a hedge
		addFeature(12, -1, featureNumber); //< is a Red Spider, 0 is a hedge
		addFeature(13, -1, featureNumber); //= is a Yellow Spider, 0 is a hedge
	}
	
	private void init(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				maze[row][col] = new Node(row, col, 0);
			}
		}
	}
	
	private void addFeature(int feature, int replace, int number){
		int counter = 0;
		while (counter < number){
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());
			
			if (maze[row][col].getId() == replace){
				
				//create spider nodes in here
				maze[row][col].setId(feature);
				counter++;
			}
		}
	}
	
	private void buildMaze(){ 
		for (int row = 1; row < maze.length - 1; row++){
			for (int col = 1; col < maze[row].length - 1; col++){
				int num = (int) (Math.random() * 10);
				
				if (num > 5 && col + 1 < maze[row].length - 1){
					maze[row][col + 1].setId(-1);
				}else{
					if (row + 1 < maze.length - 1){
						maze[row + 1][col].setId(-1);
					}
				}
			}
		}		
	}
	
	public Node[][] getMaze(){
		return this.maze;
	}
	
	public Node get(int row, int col){
		return this.maze[row][col];
	}
	
	public void set(int row, int col, Node n){
		this.maze[row][col] = n;
	}
	
	public int size(){
		return this.maze.length;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				sb.append(maze[row][col]);
				if (col < maze[row].length - 1) sb.append(",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}