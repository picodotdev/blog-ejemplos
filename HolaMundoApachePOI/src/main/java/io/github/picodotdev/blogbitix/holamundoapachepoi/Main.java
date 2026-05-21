package io.github.picodotdev.blogbitix.holamundoapachepoi;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static List<Object> DATA;

    static {
        DATA = Arrays.asList(new Object[] {
            new Object[] { "PlayStation 4 (PS4) - Consola 500GB", new BigDecimal("340.95"), "https://www.amazon.es/PlayStation-4-PS4-Consola-500GB/dp/B013U9CW8A" },
            new Object[] { "Raspberry Pi 3 Modelo B (1,2 GHz Quad-core ARM Cortex-A53, 1GB RAM, USB 2.0)", new BigDecimal("41.95"), "https://www.amazon.es/Raspberry-Modelo-GHz-Quad-core-Cortex-A53/dp/B01CD5VC92/" },
            new Object[] { "Gigabyte Brix - Barebón (Intel, Core i5, 2,6 GHz, 6, 35 cm (2.5\"), Serial ATA III, SO-DIMM) Negro ", new BigDecimal("421.36"), "https://www.amazon.es/Gigabyte-Brix-Bareb%C3%B3n-Serial-SO-DIMM/dp/B00HFCTUPM/" }
        });
    }

    public static void main(String[] args) throws Exception {
        writeExcel();
        readExcel();

        writeCsv();
        readCsv();
    }

    private static void writeExcel() throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, "Hoja excel");

        String[] headers = new String[]{
                "Producto",
                "Precio",
                "Enlace"
        };

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        HSSFRow headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; ++i) {
            String header = headers[i];
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }

        for (int i = 0; i < DATA.size(); ++i) {
            HSSFRow dataRow = sheet.createRow(i + 1);

            Object[] d = (Object[]) DATA.get(i);
            String product = (String) d[0];
            BigDecimal price = (BigDecimal) d[1];
            String link = (String) d[2];

            dataRow.createCell(0).setCellValue(product);
            dataRow.createCell(1).setCellValue(price.doubleValue());
            dataRow.createCell(2).setCellValue(link);
        }

        HSSFRow dataRow = sheet.createRow(1 + DATA.size());
        HSSFCell total = dataRow.createCell(1);
        total.setCellType(CellType.FORMULA);
        total.setCellStyle(style);
        total.setCellFormula(String.format("SUM(B2:B%d)", 1 + DATA.size()));

        FileOutputStream file = new FileOutputStream("data.xls");
        workbook.write(file);
        file.close();
    }

    private static void readExcel() throws Exception {
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream("data.xls"));
        HSSFSheet sheet = wb.getSheetAt(0);

        int rows = sheet.getLastRowNum();
        for (int i = 1; i < rows; ++i) {
            HSSFRow row = sheet.getRow(i);

            HSSFCell productCell = row.getCell(0);
            HSSFCell priceCell = row.getCell(1);
            HSSFCell linkCell = row.getCell(2);

            String product = productCell.getStringCellValue();
            BigDecimal price = new BigDecimal(priceCell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            String link = linkCell.getStringCellValue();

            System.out.printf("%s, %s, %s%n", product, price.toString(), link);
        }
    }

    private static void writeCsv() throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter("data.csv"));

        System.out.println();
        DATA.forEach(row -> {
            Object[] d = (Object[]) row;
            String product = (String) d[0];
            BigDecimal price = (BigDecimal) d[1];
            String link = (String) d[2];

            String[] line = new String[] { product, price.toString(), link };
            writer.writeNext(line);
        });

        writer.close();
    }

    private static void readCsv() throws Exception {
        CSVReader reader = new CSVReader(new FileReader("data.csv"));
        List<String[]> lines = reader.readAll();

        System.out.println();
        lines.forEach(d -> {
            String product = (String) d[0];
            BigDecimal price = new BigDecimal(d[1]);
            String link = (String) d[2];

            System.out.printf("%s, %s, %s%n", product, price.toString(), link);
        });

        reader.close();
    }
}

