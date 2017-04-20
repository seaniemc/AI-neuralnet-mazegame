package ie.gmit.sw.ai.searchAlgos;

import java.util.*;


public class BruteForceTraversator extends Utility implements Traversator {
	
	private boolean dfs = false;
	
	public BruteForceTraversator(boolean depthFirst){
		this.dfs = depthFirst;
	}
	
	public void traverse(Node[][] maze, Node node){
		System.out.println("Using Brute Force Traversator - Looking for Maze Exit Goal!\n");
		unvisitA(maze);
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		Deque<Node> queue = new LinkedList<Node>();
		queue.offer(node);
		
		while (!queue.isEmpty()){
			node = queue.poll();
			node.setVisited(true);
			visitCount++;
			
			if (node.isGoalNode() && node.nodeType != 'P'){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        TraversatorStats.printStats(node, time, visitCount, false);
				break;
			}
			
			// Sleep for x amount of seconds
			//sleep(1);
			
			Node[] children = node.children(maze);
			for (int i = 0; i < children.length; i++) {
				if (children[i] != null && !children[i].isVisited()){
					children[i].setParent(node);
					if (dfs){
						queue.addFirst(children[i]);
					}else{
						queue.addLast(children[i]);
					}
				}									
			}			
		}
	}
}