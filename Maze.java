package RandomMazeGeneration;

import ecs100.*;

import java.util.Stack;

public class Maze {
    int width = 400;
    int height = 400;

    boolean loop = true;
    public static int cols;
    public static int rows;

    public static int squareSize = 20;

    public static Cell[][] grid;
    Cell current;

    Stack<Cell> stack = new Stack<Cell>();

    public Maze(){
        //Calculates grid size
        cols = Math.round(width / squareSize);
        rows = Math.round(height / squareSize);

        //Creates grid of x size
        grid = new Cell[cols][rows];

        this.populate();

        //Sets the current cell to initially be the first cell
        current = grid[4][4];

        //Draw Loop, once ever half a second
        while (!allVisited()){
            try{
                this.drawMaze();
                Thread.sleep(0);
            }catch (InterruptedException e){
                UI.println(e);
            }
        }
//        this.drawMaze();

        UI.println("Complete");

    }

    public boolean allVisited(){
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (!grid[x][y].visited) return false;
            }
        }

        return true;
    }

    //Draws the maze along
    public void drawMaze(){
        //Draws Cell Objects
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                grid[x][y].draw();
            }
        }

        current.visited();
        current.highlight();

        //Find the next cell to move too
        Cell next = current.checkNeighbours();

        //Checks for validity then moves to it, if invalid game loop ends
        if (next != null) {
            //sets current cell to next cell
            next.visited = true;

            //Adds current to stack
            stack.push(current);

            //removes walls between cells
            this.removeWalls(current, next);

            current = next;
        }
        else if (stack.size() > 0){
            current = stack.pop();
        }
    }

    //Removes walls of spesified adjacent cells, PROBLEM IS HERE
    public void removeWalls(Cell current, Cell next){
        int dx = current.col - next.col;
        int dy = current.row - next.row;

        UI.println(dx);

//        switch (num){
//            case 0:
//                if (dx == 0) {
//                    current.removeLeft();
//                    next.removeRight();
//                }
//
//                if (dx == -1) {
//                    current.removeRight();
//                    next.removeLeft();
//                }
//                break;
//
//            case 1:
//                if (dy == 0) {
//                    current.removeTop();
//                    next.removeBottom();
//                }
//
//                if (dy == -1) {
//                    current.removeBottom();
//                    next.removeTop();
//                }
//                break;
//        }


        //x difference
        if (dx == 0) {
            current.removeLeft();
            next.removeRight();
        }else if (dx == -1) {
            current.removeRight();
            next.removeLeft();
        }

        //y difference
        if (dy == 0) {
            current.removeTop();
            next.removeBottom();
        }else if (dy == -1) {
            current.removeBottom();
            next.removeTop();
        }

    }

    //Populates grid with cells
    public void populate(){
        //Creates cell objects on the grid
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                grid[x][y] = new Cell(x, y);
            }
        }
    }

    public static void main(String[] args) {
        new Maze();
    }

}
