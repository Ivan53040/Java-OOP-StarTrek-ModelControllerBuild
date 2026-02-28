package sttrj.screen;

/**
 * Represents a line of text.
 * With several adjustable properties including text-alignment, width and text content.
 */
public class Line {

    /**
     * The text to display.
     */
    private final String content;
    /**
     * The width of the line.
     */
    private final int width;
    /**
     * Text alignment: "left", "center" or "right".
     */
    private final String alignment;

    /**
     * Constructs a {@link Line} with alignment of 'left' the text content set to the value given
     * for the content parameter, and the width set to the width value given as a parameter.
     *
     * @param content the text we want to display for this line.
     * @param width how wide we want the line to be.
     */
    public Line(final String content, final int width) {
        this.content = content;
        this.width = width;
        this.alignment = "left";
    }

    /**
     * Constructs a {@link Line} with the text content set to the value given for the content
     * parameter, the width set to the width value given as a parameter and the alignment set
     * to the string value given.
     *
     * @param content the text we want to display for this line.
     * @param width how wide we want the line to be.
     * @param alignment should only be "left", "center" or "right"
     */
    public Line(final String content, final int width, final String alignment) {
        this.content = content;
        this.width = width;
        this.alignment = alignment;
    }

    /**
     * Return the text content for this line.
     *
     * @return the text content for this line.
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns a String representation of the line based on the set content, width and alignment
     * properties set for this line.
     *
     * @return a String representation of the line based on the set content, width and alignment
     * properties set for this line.
     */
    public String render() {
        String padded = "";
        if (alignment.equals("right")) {
            padded = String.format("%" + width + "s", content);
        } else if (alignment.equals("left")) {
            padded = String.format("%-" + width + "s", content);
        } else if (alignment.equals("center")) {
            int padding = Math.max(0, (width - content.length()) / 2);
            String leftPad = " ".repeat(padding);
            String rightPad = " ".repeat(width - padding - content.length());
            padded = leftPad + content + rightPad;
        }
        return padded;
    }
}
