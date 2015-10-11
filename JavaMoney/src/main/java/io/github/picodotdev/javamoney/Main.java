package io.github.picodotdev.javamoney;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

public class Main {

	public static void main(String[] args) {
		// getting CurrencyUnit by currency code and locale
		CurrencyUnit euro = Monetary.getCurrency("EUR");		
		CurrencyUnit dollar = Monetary.getCurrency(Locale.US);
		
		System.out.println("getting CurrencyUnit by currency code and locale");
		System.out.printf("%s\n", euro);
		System.out.printf("%s\n", dollar);
		
		// getting MonetaryAmount by currency code and CurrencyUnit, without using Money (implementation class)
		MonetaryAmount fiveEuro = Money.of(5, euro);
		MonetaryAmount twelveEuro = Money.of(new BigDecimal("12"), euro);
		MonetaryAmount tenDollar = Money.of(10, "USD");
		MonetaryAmount tenPound = Monetary.getDefaultAmountFactory().setNumber(10).setCurrency("GBP").create();
		
		System.out.println();
		System.out.println("getting MonetaryAmount by currency code and CurrencyUnit, without using Money (implementation class)");
		System.out.printf("5 EUR: %s\n", fiveEuro);
		System.out.printf("12 EUR: %s\n", twelveEuro);
		System.out.printf("10 USD: %s\n", tenDollar);
		System.out.printf("10 GBP: %s\n", tenPound);
	
		// getting currency, the numeric amount and precision
		MonetaryAmount amount = Money.of(123.45, euro);		
		
		System.out.println();
		System.out.println("getting currency, the numeric amount and precision");
		System.out.printf("123.45 EUR (currency): %s\n", amount.getCurrency());
		System.out.printf("123.45 EUR (long): %s\n", amount.getNumber().longValue());
		System.out.printf("123.45 EUR (number): %s\n", amount.getNumber());
		System.out.printf("123.45 EUR (fractionNumerator): %s\n", amount.getNumber().getAmountFractionNumerator());
		System.out.printf("123.45 EUR (fractionDenominator): %s\n", amount.getNumber().getAmountFractionDenominator());
		System.out.printf("123.45 EUR (amount, BigDecimal): %s\n", amount.getNumber().numberValue(BigDecimal.class));
		
		// aritmetic
		MonetaryAmount seventeenEuros = fiveEuro.add(twelveEuro);
		MonetaryAmount sevenEuros = twelveEuro.subtract(fiveEuro);
		MonetaryAmount tenEuro = fiveEuro.multiply(2);
		MonetaryAmount twoPointFiveEuro = fiveEuro.divide(2);
		
		System.out.println();
		System.out.println("aritmetic");
		System.out.printf("5 EUR + 12 EUR: %s\n", seventeenEuros);
		System.out.printf("12 EUR - 5 EUR: %s\n", sevenEuros);
		System.out.printf("5 EUR * 2: %s\n", tenEuro);
		System.out.printf("5 EUR / 2: %s\n", twoPointFiveEuro);
		 
		// negative
		MonetaryAmount minusSevenEuro = fiveEuro.subtract(twelveEuro);
		
		System.out.println();
		System.out.println("negative");
		System.out.printf("5 EUR - 12 EUR: %s\n", minusSevenEuro);
		
		// Note that MonetaryAmounts need to have the same CurrencyUnit to do mathematical operations
		// this fails with: javax.money.MonetaryException: Currency mismatch: EUR/USD
		//fiveEuro.add(tenDollar);
		
		// comparing
        System.out.println();
        System.out.println("comparing");
        System.out.printf("7€ < 10€: %s\n", sevenEuros.isLessThan(tenEuro));
        System.out.printf("7€ > 10€: %s\n", sevenEuros.isGreaterThan(tenEuro));
        System.out.printf("10 > 7€: %s\n", tenEuro.isGreaterThan(sevenEuros));
        		
		// rounding
		MonetaryAmount euros = Money.of(12.34567, "EUR");
		MonetaryAmount roundedEuros = euros.with(Monetary.getDefaultRounding());
		
		System.out.println();
		System.out.println("rounding");
		System.out.printf("12.34567 EUR redondeados: %s\n", roundedEuros);
		
		// streams
		List<MonetaryAmount> amounts = new ArrayList<>();
		amounts.add(Money.of(2, "EUR"));
		amounts.add(Money.of(7, "USD"));
		amounts.add(Money.of(18, "USD"));
		amounts.add(Money.of(42, "USD"));
		amounts.add(Money.of(13.37, "GBP"));
		
		// filter
		List<MonetaryAmount> onlyDollars = amounts.stream()
				.filter(MonetaryFunctions.isCurrency(dollar))
				.collect(Collectors.toList());
		
		System.out.println();
		System.out.println("filter");
		System.out.printf("Solo USD: %s\n", onlyDollars);
		
		List<MonetaryAmount> euroAndDollar = amounts.stream()
				.filter(MonetaryFunctions.isCurrency(euro, dollar))
				.collect(Collectors.toList());
		
		System.out.printf("Solo EUR y USD: %s\n", euroAndDollar);
		
		List<MonetaryAmount> greaterThanTenDollar = amounts.stream()
			    .filter(MonetaryFunctions.isCurrency(dollar))
			    .filter(MonetaryFunctions.isGreaterThan(tenDollar))
			    .collect(Collectors.toList());
		
		System.out.printf("Solo USD y mayores que 10: %s\n", greaterThanTenDollar);
		
		List<MonetaryAmount> sortedByAmount = onlyDollars.stream()
				.sorted(MonetaryFunctions.sortNumber())
				.collect(Collectors.toList());
		
		System.out.printf("Solo USD y ordenados: %s\n", sortedByAmount);
		
		// grouping
		Map<CurrencyUnit, List<MonetaryAmount>> groupedByCurrency = amounts.stream()
				.collect(MonetaryFunctions.groupByCurrencyUnit());
		
		System.out.println();
		System.out.println("grouping");
		System.out.printf("Agrupación por divisa: %s\n", groupedByCurrency);
		
		// reduction
		List<MonetaryAmount> euroAmounts = new ArrayList<>();
		euroAmounts.add(Money.of(7.5, "EUR"));
		euroAmounts.add(Money.of(10, "EUR"));
		euroAmounts.add(Money.of(12, "EUR"));
		
		Optional<MonetaryAmount> max = euroAmounts.stream().reduce(MonetaryFunctions.max());
		Optional<MonetaryAmount> min = euroAmounts.stream().reduce(MonetaryFunctions.min());
		Optional<MonetaryAmount> sum = euroAmounts.stream().reduce(MonetaryFunctions.sum());
		
		System.out.println();
		System.out.println("reduction");
		System.out.printf("Máximo %s, mínimo %s, suma %s\n", max.get(), min.get(), sum.get());
		
		// exchange rates
		ExchangeRateProvider exchangeRateProviderECB = MonetaryConversions.getExchangeRateProvider("ECB");
		ExchangeRateProvider exchangeRateProviderOER = MonetaryConversions.getExchangeRateProvider("OER");
		ExchangeRate exchangeRateECB = exchangeRateProviderECB.getExchangeRate("USD", "EUR");
		ExchangeRate exchangeRateOER = exchangeRateProviderOER.getExchangeRate("USD", "EUR");
		
		System.out.println();
		System.out.println("exchange rates");
		System.out.printf("Ratio de conversión de USD a EUR (ECB, European Central Bank): %f\n", exchangeRateECB.getFactor().doubleValue());
		System.out.printf("Ratio de conversión de USD a EUR (OER, Open Exchange Rates): %f\n", exchangeRateOER.getFactor().doubleValue());
		
		// conversion
		CurrencyConversion toEuroECB = MonetaryConversions.getConversion("EUR", "ECB");
		CurrencyConversion toEuroOER = MonetaryConversions.getConversion("EUR", "OER");
		MonetaryAmount tenDollarToEuroECB = tenDollar.with(toEuroECB);
		MonetaryAmount tenDollarToEuroOER = tenDollar.with(toEuroOER);
		
		System.out.println();
		System.out.println("conversion");
		System.out.printf("10 USD son %s EUR (ECB)\n", tenDollarToEuroECB);
		System.out.printf("10 USD son %s EUR (OER)\n", tenDollarToEuroOER);

		// formating
		MonetaryAmountFormat spainFormat = MonetaryFormats.getAmountFormat(new Locale("es", "ES"));
		MonetaryAmountFormat usFormat = MonetaryFormats.getAmountFormat(new Locale("en", "US"));
		MonetaryAmount fiveThousandEuro = Money.of(5000, euro);
		
		System.out.println();
		System.out.println("formating");
		System.out.printf("Formato de 5000 EUR localizado en España: %s\n", spainFormat.format(fiveThousandEuro));
		System.out.printf("Formato de 5000 EUR localizado en Estados Unidos: %s\n", usFormat.format(fiveThousandEuro));
				
		// parsing
		MonetaryAmount twelvePointFiveEuro = spainFormat.parse("12,50 EUR");
		
		System.out.println();
		System.out.println("parsing");
		System.out.printf("Analizando «12,50 EUR» es %s\n", spainFormat.format(twelvePointFiveEuro));
		
		// custom exchange rates provider
		ExchangeRateProvider customExchangeRateProvider = MonetaryConversions.getExchangeRateProvider("OER");
		ExchangeRate customExchangeRate = customExchangeRateProvider.getExchangeRate("USD", "EUR");
		
		System.out.println();
		System.out.println("custom exchange rates");
		System.out.printf("Ratio de conversión de USD a EUR: %f\n", customExchangeRate.getFactor().doubleValue());
	}
}