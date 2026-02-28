package sttrj.game;

import sttrj.input.InputHandler;
import sttrj.input.validator.WarpCoordValidator;
import sttrj.input.validator.CourseValidator;
import sttrj.input.validator.EnergyValidator;
import sttrj.screen.View;

/**
 * The main Game class responsible for 
 * holding all relevant classes for the game coordinating player and enemy actions.
 */
public class Game {
    /**
     * The InputHandler for the Game.
     */
    private InputHandler input;

    /**
     * The Enterprise for the Game.
     */
    private Enterprise enterprise;

    /**
     * The Galaxy for the Game.
     */
    private Galaxy galaxy;

    /**
     * The action report for the Game.
     */
    private String report;

    /**
     * The View for the Game.
     */
    private View view;

    /**
     * The current Quadrant the game is taking place in.
     */
    private Quadrant currentQuadrant;

    /**
     * Construct an instance of Game with a generated list of 64 Quadrants 
     * and a current Quadrant chosen to act as the starting Quadrant.
     * 
     * @param input - InputHandler we want the game to use for the command line.
     * @param view - view implementation we want the game to use for printing to the screen.
     * @param enterprise - enterprise implementation we want the game to use.
     * @param galaxy - galaxy implementation we want the game to use.
     */
    public Game(InputHandler input, View view, Enterprise enterprise, Galaxy galaxy) {
        //set the InputHandler for the Game
        this.input = input;
        //set the View for the Game
        this.view = view;
        //set the Enterprise for the Game
        this.enterprise = enterprise;
        //set the Galaxy for the Game
        this.galaxy = galaxy;
        //pick the starting quadrant at (4,4)
        this.currentQuadrant = this.galaxy.quadrantAt(4, 4);
        //place the Enterprise on an empty sector at start
        XyPair pos = this.currentQuadrant.getRandomEmptySector();
        //set the Enterprise's position to the empty sector
        this.enterprise.setX(pos.getX());
        this.enterprise.setY(pos.getY());
    }

    /**
     * Returns the Galaxy instance being used by this game.
     * 
     * @return the Galaxy class.
     */ 
    public Galaxy getGalaxy() {
        //return the Galaxy instance being used by this game
        return this.galaxy;
    }

    /**
     * Returns the currently stored string report for the outcome of the last action, 
     * used by our View class.
     * 
     * @return the currently stored string report for the outcome of the last action, 
     * used by our View class.
     */
    public String lastActionReport() {
        //return the currently stored string report for the outcome of the last action
        return this.report;
    }

    /**
     * Returns the Enterprise instance.
     * 
     * @return the Enterprise instance
     */
    public Enterprise getEnterprise() {
        //return the Enterprise instance
        return this.enterprise;
    }

    /**
     * Returns the current Quadrant the game is taking place in.
     * 
     * @return the current Quadrant the game is taking place in.
     */
    public Quadrant getCurrentQuadrant() {
        //return the current Quadrant the game is taking place in
        return this.currentQuadrant;
    }

    /**
     * Progress the Game forward one 'tick', Handles Asking the user for input via the provided
     * {@link InputHandler}, doing a turn of game actions and enemy actions.
     */
    public void tick() {
        final String move = "0";
        final String shortScan = "1";
        final String longScan = "2";
        final String phaser = "3";
        final String torpedo = "4";
        final String shield = "5";
        //set the report to an empty string
        this.report = "";
        //reset the validator
        this.input.resetValidator();
        //set the prompt to the default prompt
        this.input.setPrompt(
            "Your orders Captain?: (enter any key except 0,1,2,3,4,5 for help)");
    
        final String action = this.input.get();
        //switch on the action(move, shortScan, longScan, phaser, torpedo, shield)
        switch (action) {
            case move -> {
                this.input.resetValidator();
                this.input.setValidator(new WarpCoordValidator());
                this.input.setPrompt(
                    "To move please put in 2 coordinates! Course and Warp Factor: i.e 1,1.2");
    
                String coords = this.input.get();
                final int direction = this.parseWarpCoordsForCourse(coords);
                final double distance = this.parseWarpCoordsForDistance(coords);
                this.move(direction, distance);
                this.view.move(this);  //get the coordinates
            }
            case shortScan -> {
                this.input.resetValidator();
                this.report = "Scanned Quadrant at x:" + this.currentQuadrant.getX() + " y:"
                    + this.currentQuadrant.getY();  //short scan
                this.currentQuadrant.scan();
                this.view.shortScan(this);
            }
            case longScan -> {
                this.input.resetValidator();
                this.report = "Scanned Quadrants near x:" + this.currentQuadrant.getX() + " y:"
                    + this.currentQuadrant.getY();  //long scan
                this.view.longScan(this);
            }
            case phaser -> {
                this.input.resetValidator();
                this.input.setValidator(new EnergyValidator(this.getEnterprise().energy()));
                this.input.setPrompt(
                    "How much energy to phasers? (max:" + this.getEnterprise().energy()
                        + ")");
                final int energy = Integer.parseInt(this.input.get());
                int energySpent = this.getEnterprise().drainEnergy(energy);
                this.phasers(energySpent, this.currentQuadrant);
                this.view.phasers(this);
            }
            case torpedo -> {
                this.input.resetValidator();
                this.input.setValidator(new CourseValidator());
                this.input.setPrompt("What course for the torpedo?");
                final int course = Integer.parseInt(this.input.get());
                this.torpedoes(course, this.currentQuadrant);
                this.view.torpedoes(this);
            }
            case shield -> {
                this.input.resetValidator();
                this.input.setValidator(new EnergyValidator(this.getEnterprise().energy()));
                this.input.setPrompt(
                    "How much energy to allocate to shields? (max:"
                        + this.getEnterprise().energy() + ") ");
                final int energy = Integer.parseInt(this.input.get());
                int amountTransferredToShields =
                    this.getEnterprise().transferEnergyToShields(energy);
                if (energy != amountTransferredToShields) {
                    this.report =
                        "Ordered energy to shields, only able to transfer: "
                            + amountTransferredToShields;
                } else {
                    this.report = "Ordered to transfer " + energy + " to shields.";
                }
                this.view.shields(this);
            }
            case null, default -> {
                this.input.resetValidator();
                this.view.commandList();
            }
        }
        //we check if there are any enemies, if not we don't do the turn
        if (this.currentQuadrant.klingonCount() > 1) {
            this.input.resetValidator();
            // Stop the screen scrolling before the user can read the previous messages
            this.input.setPrompt("Enter any key to continue...");
            this.input.get();
            this.view.quadrantTurn(this);
        }
        this.currentQuadrant.tick(this);
    }

    /**
     * Handles the Phaser action, takes in how much energy the user has spent to fire phasers. 
     * Phaser energy is spread equally (rounding down) amongst the Klingons in a Quadrant.
     * 
     * @param energy - how much energy they would like to spend on firing phasers.
     * @param quadrant - the quadrant we want to fire phasers in
     */
    public void phasers(int energy, Quadrant quadrant) {
        //if there are no enemies in the quadrant, report "NO ENEMIES IN SECTOR"
        if (quadrant.klingonCount() == 0) {
            this.report = "NO ENEMIES IN SECTOR";
            return;
        }
        //get the number of alive Klingons in the quadrant
        int alive = quadrant.klingonCount();
        //calculate the damage each Klingon takes
        int damageToEachKlingon = energy / alive;
        //hit the Klingons in the quadrant with the damage
        quadrant.hit(damageToEachKlingon);
        //get the number of Klingons marked for removal
        int removed = quadrant.klingonsMarkedForRemovalCount();
        //clean up the quadrant
        quadrant.cleanup();
        //report the phasers fired
        this.report = "Fired phasers for " + energy + ", dealt " 
            + damageToEachKlingon + " to each Klingon. "
            + removed + " destroyed.";
    
    }

    /**
     * Handles a torpedo being fired, if there are any left. Javadoc v1.1: 
     * If none left, just reports "NO TORPEDOES LEFT TO FIRE CAPTAIN!" 
     * Takes a given course, we wish to fire a torpedo at. 
     * Handles the torpedo travelling across the current Quadrant sectors 
     * until it hits another Entity 
     * or the edge of the Quadrant.
     * 
     * @param course - course/direction we wish to fire the torpedo along in the current Quadrant.
     * @param quadrant - the Quadrant we wish to fire the torpedo in.
     */
    public void torpedoes(int course, Quadrant quadrant) {
        //fire the torpedo
        Entity torpedo = this.enterprise.fireTorpedo();
        //if there are no torpedoes left, report "NO TORPEDOES LEFT TO FIRE CAPTAIN!"
        if (torpedo == null) {
            this.report = "NO TORPEDOES LEFT TO FIRE CAPTAIN!";
            return;
        }
        
        // Get the vector for the course direction
        XyPair vector = this.getVectorFrom(course);
        
        // Start from torpedo position and travel along the course
        XyPair torpedoPosition = new XyPair(torpedo.getX(), torpedo.getY());
        boolean hit = false;
        
        // Travel along the course until we hit something or reach the edge
        while (torpedoPosition.getX() >= 0 && torpedoPosition.getX() < 8 
            && torpedoPosition.getY() >= 0 && torpedoPosition.getY() < 8) {
            Entity entityAtPosition = quadrant.getEntityAt(torpedoPosition.getX(), 
                torpedoPosition.getY());
            //if the entity at the position is not null and is not the Enterprise, 
            //destroy the entity
            if (entityAtPosition != null && entityAtPosition != this.enterprise) {
                //set the hit flag to true
                hit = true;
                //remove the entity
                entityAtPosition.remove();
                //clean up the quadrant
                quadrant.cleanup();
                //break out of the loop
                break;
            }
            //move to the next position along the course using XyPair methods
            torpedoPosition.adjustX(vector.getX());
            torpedoPosition.adjustY(vector.getY());
        }
        //if the torpedo hit something, report "TORPEDO FIRED ON [direction], TARGET HIT."
        if (hit) {
            this.report = "TORPEDO FIRED ON " + this.getDirectionIndicatorArrow(course) 
                + " DIRECTION, TARGET HIT."; 
        } else {
            this.report = "TORPEDO FIRED ON " + this.getDirectionIndicatorArrow(course) 
                + " DIRECTION, TARGET MISSED.";
        }
    }

    /**
     * Returns a single-arrow-character String representation of the direction of a course. 
     * 1:"→", 2:"↗", 3:"↑", 4:"↖", 5:"←", 6:"↙", 7:"↓", 8:"↘", otherwise: "X". 
     * v1.1: This used to say "utf 8" arrows. That was inaccurate - arrows are Unicode-16 symbols.
     * 
     * @param course - given course value indicating direction
     * @return a single-character String representation of 
     * which direction a given course will travel 
     * using UTF-16 arrow symbols.
     */
    public String getDirectionIndicatorArrow(int course) {
        //return the arrow for the course
        switch (course) {
            case 1:
                return "→";
            case 2:
                return "↗";
            case 3:
                return "↑";
            case 4:
                return "↖";
            case 5:
                return "←";
            case 6:
                return "↙";
            case 7:
                return "↓";
            case 8:
                return "↘";
            default:
                return "X";
        }
    }

    /**
     * Returns an XyPair integer Cartesian representation of the given course (direction). 
     * Gives the desired x and y adjustment for movement required to meet a given course. 
     * v1.1: Note! This method can handle a floating point (double) value for course, 
     * however A1 only uses integers to represent directions, 
     * so you need to round to the nearest integer.
     * 
     * @param course - a double between 1 and 8.9
     * @return a XyPair representation of the direction of movement required.
     */
    public XyPair getVectorFrom(double course) {
        //set the x and y to 0
        int x = 0;
        int y = 0;
        //switch on the course
        switch ((int) course) {
            case 1:
                x = 1;
                y = 0;
                break;
            case 2:
                x = 1;
                y = -1;
                break;
            case 3:
                x = 0;
                y = -1;
                break;
            case 4:
                x = -1;
                y = -1;
                break;
            case 5:
                x = -1;
                y = 0;
                break;
            case 6:
                x = -1;
                y = 1;
                break;
            case 7:
                x = 0;
                y = 1;
                break;
            case 8:
                x = 1;
                y = 1;
                break;
        }
        return new XyPair(x, y);
    }

    /**
     * Handles the movement action for the game. Note: If the distance is below 1, 
     * we are moving within a Quadrant, so move will use moveWithinQuadrant() 
     * If the distance is greater than 1, we are moving between Quadrants and will use 
     * moveBetweenQuadrants() accordingly. 
     * Part of this method's job is to route to the correct sub action for movement 
     * based on the given distance.
     * 
     * @param course - which direction we want to move the Enterprise - 
     * is a number within a range of 1 (inclusive) and up to but not including 9.
     * @param distance - how far we wish to move the Enterprise, 
     * will be a decimal number with format of Digit.Digit
     */
    public void move(int course, double distance) {
        //if the distance is less than 1, move within the quadrant
        if (distance < 1.0) {
            //calculate the number of steps to move
            int steps = (int) (distance * 10); // sectors to move; e.g., 0.5 -> 5
            //if the steps are less than or equal to 0, report "NO MOVEMENT"
            if (steps <= 0) {
                this.report = "NO MOVEMENT";
                return;
            }
            //move within the quadrant
            this.moveWithinQuadrant(this.currentQuadrant, this.enterprise, course, steps);
        } else {
            //move between quadrants
            this.moveBetweenQuadrants(course, distance);
        }
    }   

    /**
     * Extracts the distance value from validated user input for warp coordinates.
     * 
     * @param coords - the warp coordinates String in the form "d,d" or "d,d.d" 
     * v1.1: clarified "d,d" or "d,d.d", not "d, d" or "d, d.d"
     * @return double representation of the distance part of the warp coordinates
     */
    public double parseWarpCoordsForDistance(String coords) {        
        //split the coords into parts
        String[] parts = coords.split(",");
        //return the distance (validator ensures this is valid)
        return Double.parseDouble(parts[1]);
    }

    /**
    * Extract from the given coords the course.
    *
    * @param coords - the warp coordinates String in the form "d,d" or "d,d.d" 
    * v1.1: clarified "d,d" or "d,d.d", not "d, d" or "d, d.d"
    * @return int representation of the current course
    * @throws IllegalArgumentException if the course value would be outside the bounds of:
    *                                  1(inclusive) and less than 9.
    */
    public int parseWarpCoordsForCourse(final String coords) {
        //split the coords into parts
        String[] parts = coords.split(",");
        //return the course (validator ensures this is valid)
        return Integer.parseInt(parts[0]);
    }
    

    /**
     * Move the given Entity within the Quadrant, by the course and distance given 
     * or until another Entity or edge of the Quadrant would be hit.
     * 
     * @param quadrant - the Quadrant we want the given Entity to move within.
     * @param entity - the Entity want to move within the Quadrant.
     * @param course - the course/direction we wish the given Entity 
     * to move within the given Quadrant
     * @param distance - how many sectors in the Quadrant to attempt to move
     */
    public void moveWithinQuadrant(Quadrant quadrant, Entity entity, int course, int distance) {
        //get the vector for the course
        XyPair vector = this.getVectorFrom(course);
        //set the moved to 0
        int moved = 0;
        //while the moved is less than the distance
        while (moved < distance) {
            //attempt to move in the quadrant
            boolean ok = this.attemptMoveInQuadrant(quadrant, entity, vector);
            //if the move is not successful, break out of the loop
            if (!ok) {
                break;
            }
            //increment the moved
            moved += 1;
        }
    }

    /**
     * Attempt to move an Entity in XyPair direction in this Quadrant. 
     * That is, attempt to move the given Entity to the next coordinate in the XyPair direction, 
     * in this Quadrant. This is done by adjusting the Entity's position by the given XyPair.
     * v1.1 updated: If an edge or other Entity is encountered at the proposed new location, 
     * the report for the turn is updated with "HIT THE EDGE OF THIS QUADRANT." or 
     * "ENCOUNTERED AN ENTITY: [insert symbol here] EN ROUTE.", and false is returned.
     * If the proposed adjustment would be successful the Entity has its position set to the new 
     * position and true is returned.

     * @param quadrant - Quadrant we wish to attempt to move the given Entity in.
     * @param entity - Entity we wish to attempt to move within the given Quadrant
     * @param vector - the adjustment we wish to make ot the Entity's position
     * @return whether the attempted in Quadrant move was successful or not.
     */
    public boolean attemptMoveInQuadrant(Quadrant quadrant, Entity entity, XyPair vector) {
        //calculate the next x and y coordinates
        int nextX = entity.getX() + vector.getX();
        int nextY = entity.getY() + vector.getY();
        //if the next x or y is less than 0 or greater than 8, 
        //report "HIT THE EDGE OF THIS QUADRANT."
        if (nextX < 0 || nextX >= 8 || nextY < 0 || nextY >= 8) {
            this.report = "HIT THE EDGE OF THIS QUADRANT.";
            return false;
        }
        //get the entity at the next x and y coordinates
        Entity encountered = quadrant.getEntityAt(nextX, nextY);
        //if the entity is not null, report "ENCOUNTERED AN ENTITY: [insert symbol here] EN ROUTE."
        if (encountered != null) {
            this.report = "ENCOUNTERED AN ENTITY: " + encountered.symbol() + " EN ROUTE.";
            return false;
        }
        //adjust the entity's position
        entity.adjustPosition(vector.getX(), vector.getY());
        //report the move
        this.report = "Moved to sector (" + (nextX + 1) + "," + (nextY + 1) + ")";
        return true;
    }

    /**
    * Moves which {@link Quadrant} the {@link Game} is handling using the given course, 
    * and given number of quadrants to jump (rounded down).
    *
    * @param course the direction of the jump: 1:→, 2:↗, 3:↑, 4:↖, 5:←, 6:↙, 7:↓, 8:↘
    *
    * @param distance how many quadrants to jump through (we drop the decimal for when jumping
    *                 between quadrants)
    */
    public void moveBetweenQuadrants(final int course, final double distance) {
        //get the vector for the course
        XyPair vector = this.getVectorFrom(course);
        //set the next quadrant to valid
        boolean nextQuadrantIsValid = true;
        //calculate the number of jumps
        int jumps = (int) Math.floor(distance);
        //while the next quadrant is valid and the jumps are greater than 0
        while (nextQuadrantIsValid && jumps > 0) {
            //decrement the jumps
            jumps -= 1;
            //attempt to move between quadrants
            nextQuadrantIsValid = attemptMoveBetweenQuadrants(vector);
        }
        /*
        We have just (re-)entered a Quadrant -
        starbases have had enough time to replenish energy
        and if returning, then everything has moved since we were last here!
        In Assignment 1, we do this just by re-creating the Quadrant.
        */
        // === old (remove this) ===
        // int quadrantX = this.currentQuadrant.getX();
        // int quadrantY = this.currentQuadrant.getY();
        // int stars     = this.currentQuadrant.starCount();
        // int starbases = this.currentQuadrant.starbaseCount();
        // int klingons  = this.currentQuadrant.klingonCount();
        // this.currentQuadrant = new Quadrant(quadrantX, quadrantY, starbases, klingons, stars);

        // So that our warp move doesn't put us inside a star, put Enterprise on an empty sector
        // XyPair newPosition = this.currentQuadrant.getRandomEmptySector();
        // this.enterprise.setX(newPosition.getX());
        // this.enterprise.setY(newPosition.getY());

        // === new (add this) ===
        reshuffleCurrentQuadrantPreservingCounts();
        placeEnterpriseOnEmptySector();
    }

    private void reshuffleCurrentQuadrantPreservingCounts() {
        Quadrant q = this.currentQuadrant;

        // Collect all entities currently in the quadrant by scanning the 8x8 grid.
        // We avoid needing direct access to q's internal lists.
        java.util.LinkedHashSet<Entity> entities = new java.util.LinkedHashSet<>();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Entity e = q.getEntityAt(x, y);
                if (e != null) {
                    entities.add(e);
                }
            }
        }

        // Remove Enterprise from that set (we place it last on an empty cell).
        entities.remove(this.enterprise);

        // To make sure getRandomEmptySector() works as intended while we move items,
        // we'll temporarily "free" each entity's current cell right before moving it.
        // We move entities one by one to a fresh empty sector.
        for (Entity e : entities) {
            // Pick a new empty sector (q knows which cells are occupied)
            XyPair p = q.getRandomEmptySector();

            // Move entity there via relative adjustment (no need for setters)
            int dx = p.getX() - e.getX();
            int dy = p.getY() - e.getY();
            e.adjustPosition(dx, dy);
        }

        // Note: counts are unchanged; only positions are reshuffled.
        // Klingons that were destroyed should already have been removed by your cleanup().
    }

    private void placeEnterpriseOnEmptySector() {
        Quadrant q = this.currentQuadrant;
        XyPair p = q.getRandomEmptySector();
        this.enterprise.setX(p.getX());
        this.enterprise.setY(p.getY());
    }

    /**
    * Will attempt to move using a given vector one quadrant and returns a boolean value 
     * indicating if it succeeded or failed.
     * 
     * @param vector - vector indicating how we wish to adjust the x and y of our position
     * @return if the attempted move between quadrants was successful
     */
    public boolean attemptMoveBetweenQuadrants(XyPair vector) {
        // Since galaxy position tracking was removed, we'll use the current quadrant's position
        int gx = this.currentQuadrant.getX() + vector.getX();
        int gy = this.currentQuadrant.getY() + vector.getY();
        //if the new x or y is less than 0 or greater than 8, report "CAN'T GO THAT WAY CAPTAIN"
        if (gx < 0 || gx >= 8 || gy < 0 || gy >= 8) {
            this.report = "CAN'T GO THAT WAY CAPTAIN";
            return false;
        }
        //get the quadrant at the new x and y coordinates
        Quadrant next = this.galaxy.quadrantAt(gx, gy);
        //if the quadrant is null, create a new one
        if (next == null) {
            next = this.galaxy.quadrantAt(gx, gy);
        }
        //set the current quadrant to the new quadrant
        this.currentQuadrant = next;
        //report the warp
        this.report = "Warped to Quadrant (" + gx + "," + gy + ")";
        return true;
    }

    /**
     * Returns if the game is still ongoing.
     * 
     * @return if the game is still ongoing.
     */
    public boolean isActive() {
        //return if the Enterprise is alive and there are Klingons in the galaxy
        return this.enterprise.isAlive() && this.galaxy.klingonCount() > 0;
    }
}
