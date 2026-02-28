package sttrj.screen;

import sttrj.game.Enterprise;
import sttrj.game.Game;
import sttrj.game.Quadrant;
import java.util.List;

/**
 * This class is responsible for handling printing stuff out to the screen.
 */
public class View {

    /**
     * Screen width.
     */
    private static final int screenWidth = 80;
    /**
     * Inner game panel width.
     */
    private static final int gameWidth = 40;
    /**
     * Inner small column width.
     */
    private static final int smallColWidth = 20;
    /**
     * Screen height.
     */
    private static final int screenHeight = 20;
    /**
     * The Renderer with these dimensions.
     */
    private final Renderer renderer = new Renderer(screenWidth, screenHeight);

    /**
     * Constructs an instance of {@link View}
     */
    public View() {
    }

    /**
     * Render a screen giving information on the result of the movement action to the screen.
     *
     * @param game game state we use to determine what to print to the screen.
     */
    public void move(final Game game) {
        final Enterprise enterprise = game.getEnterprise();
        final Quadrant quadrant = game.getCurrentQuadrant();
        final var leftCol = this.renderScannedQuadrantSection(enterprise, quadrant);
        leftCol.addLine(game.lastActionReport());
        leftCol.addLine("");
        leftCol.addLine("Handy Movement Reference");
        leftCol.addLine("4    3    2");
        leftCol.addLine("  .  .  .");
        leftCol.addLine("    ...");
        leftCol.addLine("5---<*>---1");
        leftCol.addLine("    ...");
        leftCol.addLine("  .  .  .");
        leftCol.addLine("6    7    8");

        final var titles = new Section(smallColWidth, screenHeight);
        //https://www.asciiart.eu/television/star-trek
        titles.addLine("");
        titles.addLine(new Line("torpedoes", smallColWidth, "right"));
        titles.addLine(new Line("energy", smallColWidth, "right"));
        titles.addLine(new Line("shields", smallColWidth, "right"));
        titles.addLine("");
        titles.addLine(new Line("stars", smallColWidth, "right"));
        titles.addLine(new Line("enemies", smallColWidth, "right"));
        titles.addLine(new Line("starbases", smallColWidth, "right"));

        final var values = new Section(smallColWidth, screenHeight);
        values.addLine("");
        values.addLine(" " + enterprise.torpedoAmmo());
        values.addLine(" " + enterprise.energy());
        values.addLine(" " + enterprise.shields());
        values.addLine("");
        values.addLine(new Line(" " + quadrant.starCount(), smallColWidth, "left"));
        values.addLine(new Line(" " + quadrant.klingonCount(), smallColWidth, "left"));
        values.addLine(new Line(" " + quadrant.starbaseCount(), smallColWidth, "left"));
        this.renderer.render(leftCol, titles, values);
    }

    /**
     * Render a screen giving information on the result of the short scan action to the screen.
     *
     * @param game game state we use to determine what to print to the screen.
     */
    public void shortScan(final Game game) {
        final Enterprise enterprise = game.getEnterprise();
        final Quadrant quadrant = game.getCurrentQuadrant();
        final var leftCol = this.renderScannedQuadrantSection(enterprise, quadrant);
        leftCol.addLine("");
        leftCol.addLine("Handy Movement Reference");
        leftCol.addLine("4    3    2");
        leftCol.addLine("  .  .  .");
        leftCol.addLine("    ...");
        leftCol.addLine("5---<*>---1");
        leftCol.addLine("    ...");
        leftCol.addLine("  .  .  .");
        leftCol.addLine("6    7    8");

        final var titles = new Section(smallColWidth, screenHeight);
        //https://www.asciiart.eu/television/star-trek
        titles.addLine(" ");
        titles.addLine(" ");
        titles.addLine(" ");
        titles.addLine(" ");
        titles.addLine("");
        titles.addLine(new Line("torpedoes", smallColWidth, "right"));
        titles.addLine(new Line("energy", smallColWidth, "right"));
        titles.addLine(new Line("shields", smallColWidth, "right"));
        titles.addLine("");
        titles.addLine(new Line("stars", smallColWidth, "right"));
        titles.addLine(new Line("enemies", smallColWidth, "right"));
        titles.addLine(new Line("starbases", smallColWidth, "right"));

        final var values = new Section(smallColWidth, screenHeight);
        values.addLine("");
        values.addLine("");
        values.addLine("");
        values.addLine("");
        values.addLine("");
        values.addLine(" " + enterprise.torpedoAmmo());
        values.addLine(" " + enterprise.energy());
        values.addLine(" " + enterprise.shields());
        values.addLine("");
        values.addLine(new Line(" " + quadrant.starCount(), smallColWidth, "left"));
        values.addLine(new Line(" " + quadrant.klingonCount(), smallColWidth, "left"));
        values.addLine(new Line(" " + quadrant.starbaseCount(), smallColWidth, "left"));
        this.renderer.render(leftCol, titles, values);
    }

    /**
     * Render a screen giving information on the result of the long scan action to the screen.
     *
     * @param game game state we use to determine what to print to the screen.
     */
    public void longScan(final Game game) {
        final Quadrant center = game.getCurrentQuadrant();
        final List<Quadrant> quadrants = game.getGalaxy()
                .getQuadrantClusterAt(game.getCurrentQuadrant().getX(),
                        game.getCurrentQuadrant().getY());

        //  JB: We definitely could do this in a cleverer way,
        //  but I want it to be easy for students to read and reason through.
        String topLeft = "   ";
        String topCenter = "   ";
        String topRight = "   ";
        String centerLeft = "   ";
        String centerCenter = "   ";
        String centerRight = "   ";
        String bottomLeft = "   ";
        String bottomCenter = "   ";
        String bottomRight = "   ";
        centerCenter = center.symbol();

        for (Quadrant quadrant : quadrants) {
            boolean left = quadrant.getX() < center.getX();
            boolean right = quadrant.getX() > center.getX();
            boolean top = quadrant.getY() < center.getY();
            boolean bottom = quadrant.getY() > center.getY();
            boolean centerX = quadrant.getX() == center.getX();
            boolean centerY = quadrant.getY() == center.getY();

            //left
            if (left && top) {
                topLeft = quadrant.symbol();
            } else if (left && bottom) {
                bottomLeft = quadrant.symbol();
            } else if (left && centerY) {
                centerLeft = quadrant.symbol();
            }

            //right
            if (right && top) {
                topRight = quadrant.symbol();
            } else if (right && bottom) {
                bottomRight = quadrant.symbol();
            } else if (right && centerY) {
                centerRight = quadrant.symbol();
            }

            //center
            if (centerX && top) {
                topCenter = quadrant.symbol();
            } else if (centerX && bottom) {
                bottomCenter = quadrant.symbol();
            }
        }
        final var section = new Section(screenWidth, screenHeight);
        section.addLine(game.lastActionReport());
        section.addLine(
                "Format per Quadrant is DIGITDIGITDIGIT, showing how many of each entity type");
        Quadrant a = game.getCurrentQuadrant();
        Quadrant b = game.getGalaxy().quadrantAt(a.getX(), a.getY());
        System.out.println("SAME OBJECT? " + (a == b));  // must be true
        section.addLine("1st digit is Stars, 2nd digit is Starbases, 3rd digit is Klingons");
        section.addLine("╭" + "─".repeat(smallColWidth - 2) + "╮");
        section.addLine("┊ " + topLeft + " │ " + topCenter + " │ " + topRight + "  ┊");
        section.addLine("├" + "┈".repeat(smallColWidth - 2) + "┤");
        section.addLine("┊ " + centerLeft + " ├ " + centerCenter + " ┤ " + centerRight + "  ┊");
        section.addLine("├" + "┈".repeat(smallColWidth - 2) + "┤");
        section.addLine("┊ " + bottomLeft + " │ " + bottomCenter + " │ " + bottomRight + "  ┊");
        section.addLine("╰" + "─".repeat(smallColWidth - 2) + "╯");
        section.addLine("");
        section.addLine("Handy Movement Reference");
        section.addLine("4    3    2");
        section.addLine("  .  .  .");
        section.addLine("    ...");
        section.addLine("5---<*>---1");
        section.addLine("    ...");
        section.addLine("  .  .  .");
        section.addLine("6    7    8");
        this.renderer.render(section);
    }

    /**
     * Render a screen giving a list of command options and summaries to the screen.
     */
    public void commandList() {
        final var section = new Section(View.screenWidth, View.screenHeight);
        section.addLine("COMMANDS");
        section.addLine("0. Move");
        section.addLine("1. Scan Current Quadrant");
        section.addLine("2. Scan Nearby Quadrants");
        section.addLine("3. Fire phasers at all Klingons in current Quadrant.");
        section.addLine("4. Fire torpedos in a specific direction in the current quadrant.");
        section.addLine("5. Move Energy to Shields");
        this.renderer.render(section);
    }

    private Section renderScannedQuadrantSection(final Enterprise enterprise,
                                                 final Quadrant quadrant) {
        final var section = new Section(gameWidth, screenHeight);
        final StringBuilder topRow = new StringBuilder();
        section.addLine("QUADRANT (" + quadrant.getX() + "," + quadrant.getY() + ")");
        topRow.append(" ");
        for (int i = 0; i < 8 * 3; i += 3) {
            topRow.append(" ").append((i / 3) + 1).append(" ");
        }
        String baseString = topRow.toString();
        section.addLine(baseString);
        for (int y = 0; y < 8; y += 1) {
            final StringBuilder sb = new StringBuilder();
            sb.append(y + 1);
            for (int x = 0; x < 8; x += 1) {
                if (enterprise.getX() == x && enterprise.getY() == y) {
                    sb.append(enterprise.symbol());
                } else {
                    sb.append(quadrant.getSymbolAt(x, y));
                }
            }
            section.addLine(sb.toString());
        }
        return section;
    }

    /**
     * Render out feedback of what happens in the {@link Quadrant}.
     *
     * @param game {@link Game} game we want to render out the state of
     */
    public void quadrantTurn(final Game game) {
        Section section = new Section(screenWidth, screenHeight);
        final int klingonCount = game.getCurrentQuadrant().klingonCount();
        if (klingonCount > 0) {
            section.addLine(klingonCount + " Klingons in sector firing at us Captain!");
            section.addLine("Shields taking a beating!");
        }
        renderer.render(section);
    }


    /**
     * Render a screen giving information on the result of the phaser action to the screen.
     *
     * @param game game state we use to determine what to print to the screen.
     */
    public void phasers(final Game game) {
        Section section = new Section(screenWidth, screenHeight);
        section.addLine(game.lastActionReport());
        renderer.render(section);
    }

    /**
     * Render a screen giving information on the result of the torpedo action to the screen.
     *
     * @param game game state we use to determine what to print to the screen.
     */
    public void torpedoes(final Game game) {
        Section section = new Section(screenWidth, screenHeight);
        section.addLine(game.lastActionReport());
        renderer.render(section);
    }

    /**
     * Render a screen giving information on the result of the shields action to the screen.
     *
     * @param game game state we use to determine what to print to the screen.
     */
    public void shields(final Game game) {
        Section section = new Section(screenWidth, screenHeight);
        section.addLine(game.lastActionReport());
        renderer.render(section);
    }
}
