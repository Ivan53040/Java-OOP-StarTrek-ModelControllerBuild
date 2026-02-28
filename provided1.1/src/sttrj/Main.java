package sttrj;

import sttrj.game.Enterprise;
import sttrj.game.Galaxy;
import sttrj.game.Game;
import sttrj.input.InputHandler;
import sttrj.screen.View;

/**
 * The main class for the game.
 */
public class Main {
    public static void main(String[] args) {
        Game game = new Game(new InputHandler(), new View(), new Enterprise(), new Galaxy());
        while (game.isActive()) {
            game.tick();
        }
        //if the enterprise is alive and there are no klingons, print the victory message
        if (game.getEnterprise().isAlive()&&game.getGalaxy().klingonCount() == 0) {
            System.out.print("\n\n");
            System.out.println("THE LAST KLINGON BATTLE CRUISER IN THE GALAXY HAS BEEN DESTROYED");
            System.out.println("THE FEDERATION HAS BEEN SAVED !!!");
        //if the enterprise is not alive, print the defeat message
        } else {
            System.out.println("\n\nTHE ENTERPRISE HAS BEEN DESTROYED.");
            System.out.println("THE FEDERATION WILL BE CONQUERED!");
        }
    }
}