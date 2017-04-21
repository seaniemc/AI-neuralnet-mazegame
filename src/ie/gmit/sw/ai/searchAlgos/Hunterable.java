package ie.gmit.sw.ai.searchAlgos;

import ie.gmit.sw.ai.maze.Spider;

/**
 * Created by Sean on 20/04/2017.
 */
public interface Hunterable {
    public Node hunt(Node[][] maze, Node start, Spider spider);
}
