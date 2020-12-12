package io.github.picodotdev.blogbitix.internacionalizationlocalization;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;

import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        // Locales
        Locale enLocale = Locale.ENGLISH;
        Locale esLocale = new Locale("es");
        Locale esesLocale = new Locale("es", "ES");

        // Supported locales
        System.out.println();
        System.out.println("# Supported locales");
        String locales = Arrays.stream(Locale.getAvailableLocales()).map(it -> it.toLanguageTag()).sorted().collect(Collectors.joining(", "));
        System.out.println(locales);

        // Recursos de literales
        ResourceBundle enBundle = ResourceBundle.getBundle("resource", enLocale);
        ResourceBundle esBundle = ResourceBundle.getBundle("resource", esLocale);
        ResourceBundle esesBundle = ResourceBundle.getBundle("resource", esesLocale);

        // Literales
        System.out.println();
        System.out.println("# Literals");
        System.out.println("Message (en): " + enBundle.getString("message"));
        System.out.println("Message (es): " + esBundle.getString("message"));
        System.out.println("Fallback (es-ES): " + esesBundle.getString("fallback"));
        System.out.println("Message with argument (en): " + MessageFormat.format(enBundle.getString("message_0"), "picodotdev"));
        System.out.println("Message with argument (es): " + MessageFormat.format(esBundle.getString("message_0"), "picodotdev"));

        // Formas plurales
        System.out.println();
        System.out.println("# Plural forms");

        String enPluralForm = enBundle.getString("elements");
        String esPluralForm = esBundle.getString("elements");

        ChoiceFormat enChoiceFormat = new ChoiceFormat(enPluralForm);
        ChoiceFormat esChoiceFormat = new ChoiceFormat(esPluralForm);

        String enPluralForm0 = enChoiceFormat.format(0);
        String enPluralForm1 = enChoiceFormat.format(1);
        String enPluralForm2 = enChoiceFormat.format(2);
        String esPluralForm0 = esChoiceFormat.format(0);
        String esPluralForm1 = esChoiceFormat.format(1);
        String esPluralForm2 = esChoiceFormat.format(2);

        System.out.println("Plural form (en, " + 0 + "): " + MessageFormat.format(enPluralForm0, 0));
        System.out.println("Plural form (en, " + 1 + "): " + MessageFormat.format(enPluralForm1, 1));
        System.out.println("Plural form (en, " + 2 + "): " + MessageFormat.format(enPluralForm2, 2));
        System.out.println("Plural form (es, " + 0 + "): " + MessageFormat.format(esPluralForm0, 0));
        System.out.println("Plural form (es, " + 1 + "): " + MessageFormat.format(esPluralForm1, 1));
        System.out.println("Plural form (es, " + 2 + "): " + MessageFormat.format(esPluralForm2, 2));

        // Fechas
        System.out.println();
        System.out.println("# Dates");
        System.out.println("Date (iso): " + DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now()));
        System.out.println("Date (en): " + DateTimeFormatter.ofPattern("EEEE d, LLLL YYYY", enLocale).format(ZonedDateTime.now()));
        System.out.println("Date (es): " + DateTimeFormatter.ofPattern("EEEE d, LLLL YYYY", esLocale).format(ZonedDateTime.now()));

        // Números
        System.out.println();
        System.out.println("# Numbers");

        long millionNumber = 1_000_000;
        double decimalNumber = 1000.35d;

        System.out.println("Number (en): " + NumberFormat.getInstance(enLocale).format(millionNumber));
        System.out.println("Decimal number (en): " + NumberFormat.getInstance(enLocale).format(decimalNumber));
        System.out.println("Number (es): " + NumberFormat.getInstance(esLocale).format(millionNumber));
        System.out.println("Decimal number (es): " + NumberFormat.getInstance(esLocale).format(decimalNumber));

        // Importes
        System.out.println();
        System.out.println("# Money");

        Money millionUSD = Money.of(new BigDecimal("1000000"), "USD");
        Money millionEUR = Money.of(new BigDecimal("1000000"), "EUR");
        Money decimalUSD = Money.of(new BigDecimal("1000.35"), "USD");
        Money decimalEUR = Money.of(new BigDecimal("1000.35"), "EUR");

        MonetaryAmountFormat enMonetaryFormat = MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder.of(enLocale).set(CurrencyStyle.SYMBOL).build());
        MonetaryAmountFormat esMonetaryFormat = MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder.of(esLocale).set(CurrencyStyle.SYMBOL).build());

        System.out.println("Million USD (en): " + enMonetaryFormat.format(millionUSD));
        System.out.println("Million EUR (es): " + esMonetaryFormat.format(millionEUR));
        System.out.println("Decimal USD (en): " + enMonetaryFormat.format(decimalUSD));
        System.out.println("Decimal EUR (es): " + esMonetaryFormat.format(decimalEUR));
    }
}
