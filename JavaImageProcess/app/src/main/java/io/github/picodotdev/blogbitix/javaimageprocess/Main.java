package io.github.picodotdev.blogbitix.javaimageprocess;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;

import javax.imageio.ImageIO;

import com.twelvemonkeys.image.ResampleOp;
import net.coobird.thumbnailator.Thumbnails;

public class Main {

    public static void main(String[] args) throws Exception {
        printSupportedFormats();

        BufferedImage image = readImage(Main.class.getResourceAsStream("/gnome.jpg"));
        BufferedImage imageWebp = readImage(Main.class.getResourceAsStream("/gnome.webp"));

        BufferedImage scaledImage = scaleJava(image);
        convertJava(scaledImage, ImageFormat.JPG, new FileOutputStream("gnome-scaled-java.jpg"));
        convertImageMagick(Main.class.getResourceAsStream("/gnome.jpg"), new FileOutputStream("gnome-convert-imagemagick.webp"));

        scaleThumbnailator(Main.class.getResourceAsStream("/gnome.jpg"), new FileOutputStream("gnome-scaled-thumbnailator.jpg"));
        scaleImageMagick(Main.class.getResourceAsStream("/gnome.jpg"), new FileOutputStream("gnome-scaled-imagemagick.jpg"));
        scaleTwelvemonkeys(readImage(Main.class.getResourceAsStream("/gnome.jpg")), new FileOutputStream("gnome-scaled-twelvemonkeys.jpg"));

        printImageWidthHeight("original", image);
        printImageWidthHeight("original webp", imageWebp);
        printImageWidthHeight("scaled", scaledImage);
        printImageWidthHeight("scaled thumbnailator", readImage(new FileInputStream("gnome-scaled-thumbnailator.jpg")));
        printImageWidthHeight("scaled imagemagick", readImage(new FileInputStream("gnome-scaled-imagemagick.jpg")));
        printImageWidthHeight("scaled twelvemonkeys", readImage(new FileInputStream("gnome-scaled-twelvemonkeys.jpg")));
    }

    private static void printSupportedFormats() {
        String[] readerFormatNames = javax.imageio.ImageIO.getReaderFormatNames();
        String[] writerFormatNames = javax.imageio.ImageIO.getWriterFormatNames();

        System.out.printf("Reader format names: %s%n", String.join(",", readerFormatNames));
        System.out.printf("Writer format names: %s%n", String.join(",", writerFormatNames));
    }

    private static BufferedImage readImage(InputStream is) throws IOException {
        return ImageIO.read(is);
    }

    private static void writeImage(BufferedImage image, ImageFormat format, OutputStream os) throws IOException {
        ImageIO.write(image, format.name(), os);
    }

    private static void printImageWidthHeight(String name, BufferedImage image) {
        System.out.printf("Width (%s): %s%n", name, image.getWidth());
        System.out.printf("Height (%s): %s%n", name,  image.getHeight());
    }

    private static BufferedImage scaleJava(BufferedImage image) {
        Resolution scaledResolution = new Resolution(image.getWidth(), image.getHeight()).scale(650, 450);
        BufferedImage scaledImage = new BufferedImage(scaledResolution.getWidth(), scaledResolution.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.drawImage(image, 0, 0, scaledResolution.getWidth(), scaledResolution.getHeight(), null);
        graphics2D.dispose();
        return scaledImage;
    }

    private static void convertJava(BufferedImage image, ImageFormat format, OutputStream os) throws IOException {
        ImageIO.write(image, format.name().toLowerCase(), os);
    }

    private static void scaleThumbnailator(InputStream is, OutputStream os) throws IOException {
        Thumbnails.of(is)
                .size(650, 450)
                .outputQuality(0.9)
                .outputFormat("jpg")
                .toOutputStream(os);
    }

    private static void scaleTwelvemonkeys(BufferedImage image, OutputStream os) throws IOException, InterruptedException {
        Resolution scaledResolution = new Resolution(image.getWidth(), image.getHeight()).scale(650, 450);
        BufferedImageOp resampler = new ResampleOp(scaledResolution.getWidth(), scaledResolution.getHeight(), ResampleOp.FILTER_LANCZOS);
        BufferedImage scaledImage = resampler.filter(image, null);
        writeImage(scaledImage, ImageFormat.JPG, os);
    }

    private static void scaleImageMagick(InputStream is, OutputStream os) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder().command("convert", "-resize", "650x450", "jpeg:-", "jpeg:-");
        Process process = builder.start();

        process.getOutputStream().write(is.readAllBytes());
        process.getOutputStream().close();
        os.write(process.getInputStream().readAllBytes());

        process.waitFor();
        int value = process.exitValue();
        if (value != 0) {
            throw new IOException(MessageFormat.format("Código de salida con error (%d)", value));
        }
    }

    private static void convertImageMagick(InputStream is, OutputStream os) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder().command("convert", "jpeg:-", "webp:-");
        Process process = builder.start();

        process.getOutputStream().write(is.readAllBytes());
        process.getOutputStream().close();
        os.write(process.getInputStream().readAllBytes());

        process.waitFor();
        int value = process.exitValue();
        if (value != 0) {
            throw new IOException(MessageFormat.format("Código de salida con error (%d)", value));
        }
    }
}
