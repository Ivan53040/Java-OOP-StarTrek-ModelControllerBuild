package sttrj.screen;

/**
 * Represents the concept of well, having 2d dimensions like width and height.
 */
public class Dimensions {

    /**
     * The width of this {@link Dimensions}.
     */
    private final int width;
    /**
     * The height of this {@link Dimensions}.
     */
    private final int height;

    /**
     * Constructs an instance of {@link Dimensions} with the given width and height values.
     *
     * @param width width we want to set the {@link Dimensions} to.
     * @param height height we want to set the {@link Dimensions} to.
     */
    public Dimensions(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the width of this {@link Dimensions}.
     *
     * @return the width of this {@link Dimensions}.
     */
    public int width() {
        return width;
    }

    /**
     * Returns the height of this {@link Dimensions}.
     *
     * @return the height of this {@link Dimensions}.
     */
    public int height() {
        return height;
    }
}
