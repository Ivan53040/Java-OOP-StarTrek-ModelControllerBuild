package sttrj.screen;

import java.util.ArrayList;
import java.util.List;

/**
 * A section represents a chunk of the screen with a set width and height.
 */
public class Section {

    /**
     * Section dimensions.
     */
    private final Dimensions dimensions;
    /**
     * All the lines in this section.
     */
    private final ArrayList<Line> lines = new ArrayList<>();

    /**
     * Constructs a {@link Section} instance with the given width and height.
     *
     * @param width width
     * @param height height
     */
    public Section(final int width, final int height) {
        this.dimensions = new Dimensions(width, height);
    }

    /**
     * Adds a given string to a section, wrapping it in a new {@link Line} class internally with
     * sensible defaults, if a given String is too long, it will be truncated to fit.
     *
     * @param content line to add
     */
    public void addLine(final String content) {
        String cleanedContent = content;
        if (content.length() > this.dimensions.width()) {
            cleanedContent = content.substring(0, this.dimensions.width());
        }
        var lineObj = new Line(cleanedContent, this.dimensions.width());
        lines.add(lineObj);
        if (lines.size() > this.dimensions.height()) {
            throw new IllegalArgumentException("Lines can not be more than the height dimension!");
        }
    }

    /**
     * Add a given {@link Line} to this {@link Section}.
     *
     * @param line line to add
     */
    public void addLine(final Line line) {
        if (line.getContent().length() > this.dimensions.width()) {
            throw new IllegalArgumentException("Line can not be longer than the width dimension!");
        }
        lines.add(line);
    }

    /**
     * Returns the string for a given line
     *
     * @param line the line index we want to retrieve
     * @return line at the given index
     */
    public Line getLine(final int line) {
        if (this.lines.isEmpty()) {
            System.out.println("SECTION IS EMPTY");
        }
        if (line < 0 || line >= this.dimensions.height()) {
            throw new IllegalArgumentException("Invalid line number: " + line);
        }
        if (line >= this.lines.size()) {
            return new Line("", this.dimensions.width());
        }
        return this.lines.get(line);
    }

    /**
     * Returns the total Dimensions of a section.
     *
     * @return the Dimensions of a section;
     */
    public Dimensions getDimensions() {
        return dimensions;
    }

    /**
     * Returns all lines between a starting and ending line inclusive of the start and end.
     *
     * @param startLine line to start at.
     * @param endLine line to end on.
     * @return the list of lines.
     */
    public List<Line> getLines(final int startLine, final int endLine) {
        if (startLine > endLine) {
            throw new IllegalArgumentException("Invalid start or end line number!");
        }
        if (startLine < 0) {
            throw new IllegalArgumentException("Invalid start line number!");
        }
        if (endLine > lines.size()) {
            throw new IllegalArgumentException("Invalid end line number!");
        }
        ArrayList<Line> lines = new ArrayList<>();
        for (int i = startLine; i <= endLine; i++) {
            lines.add(this.getLine(i));
        }
        return lines;
    }

}
