package sttrj.screen;

import java.util.ArrayList;

/**
 * Responsible for outputting {@link Section} and their {@link Line} to the screen.
 * Adjusting the output to be coloured to green for that cool retro feel and enforcing
 * maximum widths and heights.
 */
public class Renderer {

    /**
     * Colour green.
     */
    private final String ansiGreen = "\u001B[32m";
    /**
     * Colour reset.
     */
    private final String ansiReset = "\u001B[0m";
    /**
     * Add extras to help debugging renderLines.
     */
    private boolean debug = false;
    /**
     * Screen dimensions.
     */
    private final Dimensions dimensions;

    /**
     * Constructs an instance of {@link Renderer} with the given width and height.
     *
     * @param width max width of the space we want to render .
     * @param height max height of the space we want to render.
     */
    public Renderer(final int width, final int height) {
        this.dimensions = new Dimensions(width, height);
    }

    /**
     * Prints the given {@link Section} content to the screen.
     *
     * @param sections one or more {@link Section}s we want the renderer to print to the screen.
     */
    public void render(final Section... sections) {
        renderLines(sections);
    }

    /**
     * Takes an Array of {@link Section}s to render to the screen, combining the lines of these
     * various {@link Section}s together to fit within a little fake screen border that fits
     * the set width and height of the {@link Renderer}.
     *
     * @param sections sections we want to render the {@link Line}s of.
     */
    private void renderLines(final Section[] sections) {
        ArrayList<String> lines = new ArrayList<>();

        for (int line = 0; line < dimensions.height(); line += 1) {
            StringBuilder sb = new StringBuilder();
            for (Section section : sections) {
                Line sectionLine = section.getLine(line);
                if (this.debug) {
                    sb.append(sectionLine.render() + "┊");
                } else {
                    sb.append(sectionLine.render());
                }
            }
            String combinedLine = sb.toString();
            if (this.debug) {
                if (line < 10) {
                    combinedLine = " " + line + ":" + combinedLine;
                } else {
                    combinedLine = line + ":" + combinedLine;
                }
            }
            if (!this.debug) { // if not in debug mode we enforce a maximum width
                if (combinedLine.length() > this.dimensions.width()) {
                    throw new IllegalArgumentException(
                            "combined line length exceeds dimensions! Line length: "
                                    + combinedLine.length() + " w: "
                                    + this.dimensions.width() + combinedLine.toString());
                }
            }
            lines.add(combinedLine);
        }

        int lineLength = lines.get(0)
                .length(); //we assume you are always giving the renderer at least 1 line
        System.out.println(ansiGreen + "╭" + "─".repeat(lineLength) + "╮" + ansiReset);
        for (String line : lines) {
            System.out.println(ansiGreen + "│" + line + "│" + ansiReset);
        }
        System.out.println(ansiGreen + "╰" + "─".repeat(lineLength) + "╯" + ansiReset);
    }
}
