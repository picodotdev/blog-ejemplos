package io.github.picodotdev.blogbitix.javaimageprocess;

public class Resolution {

    private int width;
    private int height;

    public Resolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Resolution scale(Resolution resolution) {
        return scale(resolution.getWidth(), resolution.getHeight());
    }

    public Resolution scale(int width, int height) {
        int originalWidth = this.width;
        int originalHeight = this.height;
        int boundWidth = width;
        int boundHeight = height;
        int scaledWidth = originalWidth;
        int scaledHeight = originalHeight;

        if (scaledWidth > boundWidth) {
            scaledWidth = boundWidth;
            scaledHeight = (scaledWidth * originalHeight) / originalWidth;
        }

        if (scaledHeight > boundHeight) {
            scaledHeight = boundHeight;
            scaledWidth = (scaledHeight * originalWidth) / originalHeight;
        }

        return new Resolution(scaledWidth, scaledHeight);
    }
}
