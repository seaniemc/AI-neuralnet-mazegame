package ie.gmit.sw.ai.searchAlgos;
import ie.gmit.sw.ai.maze.*;

import java.util.*;
public class AStarTraversator {
	private Node goal;
	
	public AStarTraversator(Node goal){
		this.goal = goal;
	}
	
	public void traverse(Node[][] maze, Node node) {
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		PriorityQueue<Node> open = new PriorityQueue<Node>(20, (Node current, Node next)-> (current.getPathCost() + current.getHeuristic(goal)) + (next.getPathCost() + next.getHeuristic(goal)));
		java.util.List<Node> closed = new ArrayList<Node>();
    	   	
		open.offer(node);
		node.setPathCost(0);		
		while(!open.isEmpty()){//this conditions guarantees that if a path exists to the goal node we will find it......
			//
			node = open.poll();	//get the lowest f(n) from the queue	
			closed.add(node);
			node.setVisited(true);	
			visitCount++;
			
			if (node.isGoalNode()){
				System.out.println("Open: " + open);
		        time = System.currentTimeMillis() - time; //Stop the clock
		        //TraversatorStats.printStats(node, time, visitCount);
				break;
			}
			
			try { //Simulate processing each expanded node
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Process adjacent nodes
			Node[] children = node.children(maze);
			for (int i = 0; i < children.length; i++) {
				Node child = children[i];
				int score = node.getPathCost() + 1 + child.getHeuristic(goal);
				int existing = child.getPathCost() + child.getHeuristic(goal);
				
				if ((open.contains(child) || closed.contains(child)) && existing < score){
					continue;
				}else{
					open.remove(child);//Remove this child because the f(n) value will change
					closed.remove(child);
					child.setParent(node);//switch parent if on better path
					child.setPathCost(node.getPathCost() + 1);// update the path cost to a better value
					open.add(child);
				}
			}									
		}
	}
}
