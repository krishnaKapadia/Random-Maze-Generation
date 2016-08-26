package RandomMazeGeneration;

import ecs100.UI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Cell {
    boolean[] walls = {true, true, true, true}; // Top, Right, Bottom, Left
    boolean visited;
    int col, row, x, y;
    int size = Maze.squareSize;
    ArrayList<Cell> neighbours;

    public Cell(int col, int row){
        this.col = col;
        this.row = row;
        this.x = col * size;
        this.y = row * size;
        this.visited = false;
    }

    public void draw(){
        //Draws if square has been visited or not
        UI.setColor(Color.red);
        if (visited) UI.fillRect(x, y, size, size);
        UI.setColor(Color.black);

        //Draws the walls
        if (this.walls[0]) UI.drawLine(x, y, x + size, y); //Top
        if (this.walls[1]) UI.drawLine(x + size, y, x + size, y + size); // Right
        if (this.walls[2]) UI.drawLine(x + size, y + size, x, y + size); //Bottom
        if (this.walls[3]) UI.drawLine(x, y + size, x, y); //Left

    }

    public Cell checkNeighbours(){
        neighbours = new ArrayList<Cell>();

        //Gets the adjacent cells of this cell
        Cell top = this.index(col, row - 1);
        Cell right = this.index(col + 1, row);
        Cell bottom = this.index(col, row + 1);
        Cell left = this.index(col - 1, row);

        //Ensures that the adjacent cells exist and that they are not visited then adds them to the array of neighbours
        if (top != null && !top.visited) neighbours.add(top);
        if (bottom != null && !bottom.visited) neighbours.add(bottom);
        if (left != null && !left.visited) neighbours.add(left);
        if (right != null && !right.visited) neighbours.add(right);

        //Returns a randomly selected adjacent cell from the array of valid neighbours
        if (neighbours.size() > 0){
            Random rand = new Random();
            int num = rand.nextInt(neighbours.size());

            //Remove wall
            return neighbours.get(num);
        }else {
            //If no valid neighbours, returns null
            return null;
        }
    }

    public Cell index(int col, int row) {
        //Gets the cell at a given col/row position, returns null if edge case
        if (col < 0 || col > Maze.cols - 1 || row < 0 || row > Maze.rows - 1) return null;
        return Maze.grid[col][row];
    }

    public void highlight(){
        UI.setColor(Color.green);
        UI.fillRect(x, y, size, size);
        UI.setColor(Color.black);
    }

    public String toString(){
        return " " + visited;
    }

    public void visited(){
        this.visited = true;
    }

    public void removeTop(){ this.walls[0] = false; }

    public void removeRight(){ this.walls[1] = false; }

    public void removeBottom(){ this.walls[2] = false; }

    public void removeLeft(){ this.walls[3] = false; }

}
