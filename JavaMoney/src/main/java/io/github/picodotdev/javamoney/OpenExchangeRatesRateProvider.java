package io.github.picodotdev.javamoney;

import java.io.InputStream;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import javax.money.Monetary;
import javax.money.convert.ConversionContextBuilder;
import javax.money.convert.ConversionQuery;
import javax.money.convert.CurrencyConversionException;
import javax.money.convert.ExchangeRate;
import javax.money.convert.ProviderContext;
import javax.money.convert.ProviderContextBuilder;
import javax.money.convert.RateType;
import javax.money.spi.Bootstrap;

import org.apache.commons.io.IOUtils;
import org.javamoney.moneta.ExchangeRateBuilder;
import org.javamoney.moneta.spi.AbstractRateProvider;
import org.javamoney.moneta.spi.DefaultNumberValue;
import org.javamoney.moneta.spi.LoaderService;
import org.javamoney.moneta.spi.LoaderService.LoaderListener;
import org.json.JSONObject;

public class OpenExchangeRatesRateProvider extends AbstractRateProvider implements LoaderListener {

    private static final String DATA_ID = OpenExchangeRatesRateProvider.class.getSimpleName();

    private static final ProviderContext CONTEXT = ProviderContextBuilder.of("OER", RateType.DEFERRED).set("providerDescription", "Open Exchange Rates").set("days", 1)
            .build();

    private String BASE_CURRENCY_CODE = "USD";

    protected Future<Boolean> load;
    
    protected final Map<LocalDate, Map<String, ExchangeRate>> rates = new ConcurrentHashMap<>();

    public OpenExchangeRatesRateProvider() {
        super(CONTEXT);
        LoaderService loader = Bootstrap.getService(LoaderService.class);
        loader.addLoaderListener(this, getDataId());
        load = loader.loadDataAsync(getDataId());
    }

    public String getDataId() {
        return DATA_ID;
    }

    @Override
    public void newDataLoaded(String resourceId, InputStream is) {
        try {
            String data = IOUtils.toString(is, "UTF-8");

            LocalDate date = LocalDate.now();
            ExchangeRateBuilder builder = new ExchangeRateBuilder(ConversionContextBuilder.create(CONTEXT, RateType.DEFERRED).set(date).build());
            
            rates.putIfAbsent(date, new HashMap<String, ExchangeRate>());

            JSONObject json = new JSONObject(data);
            JSONObject r = json.getJSONObject("rates");
            builder.setBase(Monetary.getCurrency(BASE_CURRENCY_CODE));            
            for (String term : r.keySet()) {
                if (!Monetary.isCurrencyAvailable(term)) {
                    continue;
                }

                Double factor = r.getDouble(term);
            
                builder.setTerm(Monetary.getCurrency(term));
                builder.setFactor(DefaultNumberValue.of(factor));
                
                ExchangeRate exchangeRate = builder.build();                
                rates.get(date).put(term, exchangeRate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ExchangeRate getExchangeRate(ConversionQuery conversionQuery) {
        try {
            load.get();
        } catch (Exception e) {
            return null;
        }
        
        Objects.requireNonNull(conversionQuery);
        if (rates.isEmpty()) {
            return null;
        }
        LocalDate[] dates = getQueryDates(conversionQuery);
        LocalDate selectedDate = null;
        Map<String, ExchangeRate> targets = null;
        for (LocalDate date : dates) {
            targets = this.rates.get(date);
            if (targets != null) {
                selectedDate = date;
                break;
            }
        }
        if (Objects.isNull(targets)) {
            return null;
        }
        ExchangeRateBuilder builder = getBuilder(conversionQuery, selectedDate);
        ExchangeRate sourceRate = targets.get(conversionQuery.getBaseCurrency().getCurrencyCode());
        ExchangeRate target = targets.get(conversionQuery.getCurrency().getCurrencyCode());
        return createExchangeRate(conversionQuery, builder, sourceRate, target);
    }

    private LocalDate[] getQueryDates(ConversionQuery query) {
        LocalDate date = query.get(LocalDate.class);
        if (date == null) {
            LocalDateTime dateTime = query.get(LocalDateTime.class);
            if (dateTime != null) {
                date = dateTime.toLocalDate();
            } else {
                date = LocalDate.now();
            }
        }
        return new LocalDate[] { date, date.minus(Period.ofDays(1)), date.minus(Period.ofDays(2)), date.minus(Period.ofDays(3)) };
    }

    private ExchangeRateBuilder getBuilder(ConversionQuery query, LocalDate localDate) {
        ExchangeRateBuilder builder = new ExchangeRateBuilder(ConversionContextBuilder.create(getContext(), RateType.HISTORIC).set(localDate).build());
        builder.setBase(query.getBaseCurrency());
        builder.setTerm(query.getCurrency());
        return builder;
    }

    private ExchangeRate createExchangeRate(ConversionQuery query, ExchangeRateBuilder builder, ExchangeRate sourceRate, ExchangeRate target) {
        if (areBothBaseCurrencies(query)) {
            builder.setFactor(DefaultNumberValue.ONE);
            return builder.build();
        } else if (BASE_CURRENCY_CODE.equals(query.getCurrency().getCurrencyCode())) {
            if (Objects.isNull(sourceRate)) {
                return null;
            }
            return reverse(sourceRate);
        } else if (BASE_CURRENCY_CODE.equals(query.getBaseCurrency().getCurrencyCode())) {
            return target;
        } else {
            // Get Conversion base as derived rate: base -> EUR -> term
            ExchangeRate rate1 = getExchangeRate(query.toBuilder().setTermCurrency(Monetary.getCurrency(BASE_CURRENCY_CODE)).build());
            ExchangeRate rate2 = getExchangeRate(query.toBuilder().setBaseCurrency(Monetary.getCurrency(BASE_CURRENCY_CODE)).setTermCurrency(query.getCurrency()).build());
            if (Objects.nonNull(rate1) && Objects.nonNull(rate2)) {
                builder.setFactor(multiply(rate1.getFactor(), rate2.getFactor()));
                builder.setRateChain(rate1, rate2);
                return builder.build();
            }
            throw new CurrencyConversionException(query.getBaseCurrency(), query.getCurrency(), sourceRate.getContext());
        }
    }

    private boolean areBothBaseCurrencies(ConversionQuery query) {
        return BASE_CURRENCY_CODE.equals(query.getBaseCurrency().getCurrencyCode()) && BASE_CURRENCY_CODE.equals(query.getCurrency().getCurrencyCode());
    }

    private ExchangeRate reverse(ExchangeRate rate) {
        if (Objects.isNull(rate)) {
            throw new IllegalArgumentException("Rate null is not reversible.");
        }
        return new ExchangeRateBuilder(rate).setRate(rate).setBase(rate.getCurrency()).setTerm(rate.getBaseCurrency())
                .setFactor(divide(DefaultNumberValue.ONE, rate.getFactor(), MathContext.DECIMAL64)).build();
    }
}