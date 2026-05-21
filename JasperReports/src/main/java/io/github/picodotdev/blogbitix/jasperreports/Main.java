package io.github.picodotdev.blogbitix.jasperreports;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();

        Factura factura = new Factura("Compañía S.A.", "picodotdev", "Factura enero 2019", "100000", "00011111111", new BigDecimal("25.00"), "1111111",
            "picodotdev", "BASKESXXXX", "ES24-0000-0000-0000-0000-0000", LocalDateTime.now(), LocalDateTime.now());

        List<Factura> facturas = Arrays.asList(factura);

        InputStream is = Main.class.getResourceAsStream("factura.jrxml");
        JasperReport report = JasperCompileManager.compileReport(is);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(facturas);

        JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);

        JasperExportManager.exportReportToPdfFile(print, "factura.pdf");
    }
}
