package ie.gmit.sw.ai.searchAlgos;

import java.awt.Color;


public abstract class Utility {
	
	/**
	 * Sets the nodes back to their default values
	 * @param maze The node maze array object
	 */
	public void unvisitA(Node[][] maze){
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
				if(maze[i][j].getNodeType() == 'T' || maze[i][j].getNodeType() == ' '){
					maze[i][j].setNodeType(' ');
					maze[i][j].setColor(Color.LIGHT_GRAY);
				}
			}
		}
	}
	
	/**
	 * Sets the nodes back to their default values
	 * @param maze The node maze array object
	 */
	public void unvisitB(Node[][] maze){
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
			}
		}
	}
	
	/**
	 * Puts the thread to sleep for x amount of seconds
	 * @param seconds Amount of seconds to suspend the thread
	 */
	public void sleep(int seconds){
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}