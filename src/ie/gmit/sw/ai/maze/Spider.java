package ie.gmit.sw.ai.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Sean on 19/04/2017.
 */
public class Spider extends Node{

    private long movementSpeed = 3000;
    private Random rand = new Random();
    private Object lock = null;
    private Node[][] maze = null;
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private Node lastNode = null;
    private volatile int moveNum = 0;

    public Spider(int row, int col, int id, Object lock, Node[][] maze) {

        // setup constructor
        super(row, col, id);

        // set variables
        this.lock = lock;
        this.maze = maze;

        // start moving the spider
        // run()
        executor.submit(() -> {

            while (true) {
                try {

                    // sleep thread to simulate a movement pace
                    Thread.sleep(movementSpeed);

                    // start moving the spider
                    move();

                } catch (Exception ex) {

                } // try catch
            } // while

        });

    } // constructor


    // Method for moving the spider
    // run in a thread
    private void move(){

        synchronized (lock) {

            System.out.println("Moving number: " + moveNum);

            Node[] adjacentNodes = null;
            List<Node> canMoveTo = new ArrayList<>();

            // get the spiders adjacent nodes
            adjacentNodes = adjacentNodes(maze);

            for (Node n : adjacentNodes) {

                // check that the node is empty space
                if (n.getId() == -1 && !n.equals(lastNode)) {

                    // add node to list of available nodes
                    canMoveTo.add(n);
                } // if
            } // if

            if (canMoveTo.size() > 0) {
                int newX, newY, oldX, oldY;

                oldX = getRow();
                oldY = getCol();

                // pick a random index to move to
                int index = rand.nextInt(canMoveTo.size());

                newX = canMoveTo.get(index).getRow();
                newY = canMoveTo.get(index).getCol();

                // update X and Y
                setRow(newX);
                setCol(newY);
                canMoveTo.get(index).setRow(oldX);
                canMoveTo.get(index).setCol(oldY);

                // save last node
                lastNode = canMoveTo.get(index);

                // move to that node
                maze[newX][newY] = (Spider)this;

                // remove self from original spot
                maze[oldX][oldY] = canMoveTo.get(index);

            } else if(canMoveTo.size() < 1 && lastNode != null){ // if moved into a corner, go back to last node

                // move to last node

                int newX, newY, oldX, oldY;

                oldX = getRow();
                oldY = getCol();
                newX = lastNode.getRow();
                newY = lastNode.getCol();

                // update X and Y
                setRow(newX);
                setCol(newY);
                lastNode.setRow(oldX);
                lastNode.setCol(oldY);

                // save last node
                lastNode = lastNode;

                // move to that node
                maze[newX][newY] = (Spider)this;

                // remove self from original spot
                maze[oldX][oldY] = lastNode;

            } // if

        } // synchronized()

        //moveNum++;
    } // move()



}
