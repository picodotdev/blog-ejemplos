package io.github.picodotdev.blogbitix.javabarcode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import uk.org.okapibarcode.backend.Code128;
import uk.org.okapibarcode.backend.Ean;
import uk.org.okapibarcode.backend.HumanReadableLocation;
import uk.org.okapibarcode.backend.QrCode;
import uk.org.okapibarcode.backend.Upc;
import uk.org.okapibarcode.output.Java2DRenderer;

public class Main {

    public static void main(String[] args) throws Exception {
        {
            Code128 barcode = new Code128();
            barcode.setFontSize(32);
            barcode.setModuleWidth(4);
            barcode.setBarHeight(100);
            barcode.setHumanReadableLocation(HumanReadableLocation.BOTTOM);
            barcode.setContent("1234567890");

            int width = barcode.getWidth();
            int height = barcode.getHeight();

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Java2DRenderer renderer = new Java2DRenderer(g2d, 1, Color.WHITE, Color.BLACK);
            renderer.render(barcode);

            ImageIO.write(image, "png", new File("code-128.png"));
        }

        {
            Ean ean13 = new Ean();
            ean13.setFontSize(32);
            ean13.setModuleWidth(4);
            ean13.setBarHeight(100);
            ean13.setHumanReadableLocation(HumanReadableLocation.BOTTOM);
            ean13.setContent("1234567890");

            int width = ean13.getWidth();
            int height = ean13.getHeight();


            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Java2DRenderer renderer = new Java2DRenderer(g2d, 1, Color.WHITE, Color.BLACK);
            renderer.render(ean13);

            ImageIO.write(image, "png", new File("ean-13.png"));
        }

        {
            Upc upc = new Upc();
            upc.setFontSize(32);
            upc.setModuleWidth(4);
            upc.setBarHeight(100);
            upc.setHumanReadableLocation(HumanReadableLocation.BOTTOM);
            upc.setContent("1234567890");

            int width = upc.getWidth();
            int height = upc.getHeight();

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Java2DRenderer renderer = new Java2DRenderer(g2d, 1, Color.WHITE, Color.BLACK);
            renderer.render(upc);

            ImageIO.write(image, "png", new File("upc.png"));
        }

        {
            QrCode qrCode = new QrCode();
            qrCode.setContent("1234567890");

            int width = qrCode.getWidth();
            int height = qrCode.getHeight();

            BufferedImage image = new BufferedImage(width * 8, height * 8, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Java2DRenderer renderer = new Java2DRenderer(g2d, 8, Color.WHITE, Color.BLACK);
            renderer.render(qrCode);

            ImageIO.write(image, "png", new File("qr-code.png"));
        }

        {
            QrCode qrCode = new QrCode();
            qrCode.setContent("https://picodotdev.github.io/blog-bitix/");

            int width = qrCode.getWidth();
            int height = qrCode.getHeight();

            BufferedImage image = new BufferedImage(width * 8, height * 8, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Java2DRenderer renderer = new Java2DRenderer(g2d, 8, Color.YELLOW, Color.BLACK);
            renderer.render(qrCode);

            ImageIO.write(image, "png", new File("qr-code-blogbitix.png"));
        }
    }
}
