package io.github.picodotdev.blogbitix.zalandologbook;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Origin;
import org.zalando.logbook.Precorrelation;

public final class CustomHttpLogFormatter implements HttpLogFormatter {

    @Override
    public String format(final Precorrelation precorrelation, final HttpRequest request) throws IOException {
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("precorrelation", precorrelation.getId());
        fields.put("remote", request.getRemote());
        fields.put("host", request.getHost());
        fields.put("method", request.getMethod());
        fields.put("uri", request.getRequestUri());
        fields.put("contentType", request.getContentType());
        fields.put("protocol", request.getProtocolVersion());
        fields.put("bodySize", String.valueOf(request.getBody().length));
        return direction(request) + format(fields);
    }

    @Override
    public String format(final Correlation correlation, final HttpResponse response) throws IOException {
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("correlation", correlation.getId());
        fields.put("contentType", response.getContentType());
        fields.put("status", String.valueOf(response.getStatus()));
        fields.put("protocol", response.getProtocolVersion());
        fields.put("duration", String.valueOf(correlation.getDuration().toMillis()));
        fields.put("bodySize", String.valueOf(response.getBody().length));
        return direction(response) + format(fields);
    }

    private String direction(final HttpRequest request) {
        return (request.getOrigin() == Origin.REMOTE) ? "Incoming request" : "Outgoing request";
    }

    private String direction(final HttpResponse response) {
        return (response.getOrigin() == Origin.REMOTE) ? "Incoming response" : "Outgoing response";
    }

    private String format(Map<String, String> map) {
        return map.entrySet().stream().filter(it -> Strings.isNotBlank(it.getValue()))
                  .map(it -> String.format("%s: %s", it.getKey(), it.getValue()))
                  .collect(Collectors.joining(", ", "(", ")"));
    }
}
