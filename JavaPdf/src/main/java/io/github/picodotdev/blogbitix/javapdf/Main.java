package io.github.picodotdev.blogbitix.javapdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class Main {

    public static void main(String[] args) throws Exception {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A6);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Text
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 32);
            contentStream.newLineAtOffset( 20, page.getMediaBox().getHeight() - 52);
            contentStream.showText("Hello World!");
            contentStream.endText();

            // Image
            PDImageXObject image = PDImageXObject.createFromByteArray(document, Main.class.getResourceAsStream("/java.png").readAllBytes(), "Java Logo");
            contentStream.drawImage(image, 20, 20, image.getWidth() / 3, image.getHeight() / 3);

            contentStream.close();

            document.save("document.pdf");
        }
    }
}
