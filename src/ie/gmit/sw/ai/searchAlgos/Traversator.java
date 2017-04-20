package ie.gmit.sw.ai.searchAlgos;

import ie.gmit.sw.ai.maze.*;
public interface Traversator {
	public void traverse(Node[][] maze, Node start) throws InterruptedException;
}
