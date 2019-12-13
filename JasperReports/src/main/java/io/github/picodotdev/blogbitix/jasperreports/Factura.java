package io.github.picodotdev.blogbitix.jasperreports;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Factura {

    private String ordenante;
    private String titular;
    private String concepto;
    private String referencia;
    private String adeudo;
    private BigDecimal importe;
    private String acreedor;
    private String titulares;
    private String bic;
    private String iban;
    private LocalDateTime fecha;
    private LocalDateTime valor;

    public Factura(String ordenante, String titular, String concepto, String referencia, String adeudo, BigDecimal importe, String acreedor,
            String titulares, String bic, String iban, LocalDateTime fecha, LocalDateTime valor) {
        this.ordenante = ordenante;
        this.titular = titular;
        this.concepto = concepto;
        this.referencia = referencia;
        this.adeudo = adeudo;
        this.importe = importe;
        this.acreedor = acreedor;
        this.titulares = titulares;
        this.bic = bic;
        this.iban = iban;
        this.fecha = fecha;
        this.valor = valor;
    }

    public String getOrdenante() {
        return this.ordenante;
    }

    public String getTitular() {
        return titular;
    }

    public String getConcepto() {
        return concepto;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getAdeudo() {
        return adeudo;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public String getAcreedor() {
        return acreedor;
    }

    public String getTitulares() {
        return titulares;
    }

    public String getBic() {
        return bic;
    }

    public String getIban() {
        return iban;
    }

    public Date getFecha() {
        return Date.from(fecha.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date getValor() {
        return Date.from(valor.atZone(ZoneId.systemDefault()).toInstant());
    }
}
