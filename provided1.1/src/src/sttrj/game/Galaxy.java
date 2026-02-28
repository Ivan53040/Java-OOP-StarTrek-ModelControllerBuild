package sttrj.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the Quadrants held within the Galaxy.
 */
public class Galaxy extends Object {
    /**
     * The Quadrants held within the Galaxy.
     */
    private ArrayList<Quadrant> quadrants;
    
    /**
     * Constructs a new Galaxy of 64 Quadrants.
     */
    public Galaxy() {
        //set the Galaxy's quadrants
        this.quadrants = new ArrayList<>();
        //generate the Galaxy's quadrants
        this.generateQuadrants();
    }

    /**
     * Generate 64 Quadrants, each with their own unique XyPair coordinates 
     * to set them in an 8*8 grid.
     * 
     * @return a List of Quadrants
     */
    public List<Quadrant> generateQuadrants() {
        //generate the Galaxy's quadrants(8x8 grid)
        for (int y = 0; y < 8; y += 1) {
            for (int x = 0; x < 8; x += 1) {
                //add the quadrant to the Galaxy's quadrants
                this.quadrants.add(new Quadrant(x, y));
            }
        }
        return this.quadrants;
    }

    /**
     * Get a List of Quadrants adjacent to one with the given coordinates. 
     * The list includes the Quadrant at the given coordinates. 
     * As an example, if you pass in 1 and 1, the List should contain the Quadrants 
     * (if they exist) at: 0,0 - top left corner; 1,0 - top center; 2,0 - top right; 
     * 0,1 - center left; 1,1 - center; 2,1 - center right; 0,2 - bottom left; 
     * 1,2 - bottom center; and 2,2 - bottom right.
     * 
     * @param x - the x coordinate of the center quadrant for the cluster
     * @param y - the y coordinate of the center quadrant for the cluster
     * @return a List of Quadrants adjacent to one with the given x and y, 
     * inclusive of the one at the given coordinates.
     */
    public List<Quadrant> getQuadrantClusterAt(int x, int y) {
        //create a new ArrayList to store the quadrant cluster
        ArrayList<Quadrant> cluster = new ArrayList<>();
        //iterate through the quadrants in the cluster
        for (int dy = -1; dy <= 1; dy += 1) {
            for (int dx = -1; dx <= 1; dx += 1) {
                //calculate the new x and y coordinates
                int nx = x + dx;
                int ny = y + dy;
                //if the new x and y coordinates are within the bounds of the Galaxy
                if (nx >= 0 && nx < 8 && ny >= 0 && ny < 8) {
                    //get the quadrant at the new x and y coordinates
                    Quadrant q = this.quadrantAt(nx, ny);
                    //if the quadrant is not null, add it to the cluster
                    if (q != null) {
                        cluster.add(q);
                    }
                }
            }
        }
        return cluster;
    }

    /**
     * Returns how many Klingon total are in the Quadrants in this Galaxy.
     * 
     * @return how many Klingon total are in the Quadrants in this Galaxy.
     */
    public int klingonCount() {
        //create a variable to store the total number of Klingons
        int total = 0;
        //if the quadrants list is null, return 0
        if (this.quadrants == null) {
            return 0;
        }
        //if the quadrants list is not null, add up the klingon count of each quadrant
        for (Quadrant q : this.quadrants) {
            total += q.klingonCount();
        }
        return total;
    }

    /**
     * Get a specific Quadrant using its x and y coordinate to find it.
     * 
     * @param x - horizontal coordinate of the Quadrant we are trying to find in the Galaxy
     * @param y - vertical coordinate of the Quadrant we are trying to find in the Galaxy
     * @return the Quadrant we are looking for
     */
    public Quadrant quadrantAt(int x, int y) {
        //if the quadrants list is null, return null
        if (this.quadrants == null) {
            return null;
        }
        //if the quadrants list is not null, check if the quadrant at the given coordinates exists
        for (Quadrant q : this.quadrants) {
            if (q.getX() == x && q.getY() == y) {
                return q;
            }
        }
        return null;
    }
}
