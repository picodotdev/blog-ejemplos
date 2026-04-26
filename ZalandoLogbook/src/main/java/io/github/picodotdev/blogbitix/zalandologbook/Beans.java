package io.github.picodotdev.blogbitix.zalandologbook;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.okhttp.GzipInterceptor;
import org.zalando.logbook.okhttp.LogbookInterceptor;

@Component
public class Beans {

    @Bean
    HttpLogFormatter buildHttpLogFormatter() {
        return new CustomHttpLogFormatter();
    }

    @Bean("okHttpClient")
    OkHttpClient buildOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                   .addInterceptor(logging)
                   .build();
    }

    @Bean("logbookOkHttpClient")
    OkHttpClient buildLogbookOkHttpClient(Logbook logbook) {
        return new OkHttpClient.Builder()
                   .addNetworkInterceptor(new LogbookInterceptor(logbook))
                   .addNetworkInterceptor(new GzipInterceptor())
                   .build();
    }
}
