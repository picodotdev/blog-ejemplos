package io.github.picodotdev.blogbitix.javatesseract;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import net.sourceforge.tess4j.Tesseract;

public class Main {

    public static void main(String[] args) throws Exception {
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("spa");
        tesseract.setDatapath("src/main/resources/tesseract");
        tesseract.setOcrEngineMode(1);
        
        BufferedImage image = ImageIO.read(Main.class.getResourceAsStream("/image.jpg"));
        String result = tesseract.doOCR(image, new Rectangle(275, 375, 1287 - 275, 1796 - 375));

        System.out.println();
        System.out.println(result);
    }
}
