package io.github.picodotdev.blogbitix.log4j;

import org.apache.logging.log4j.message.Message;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SecuredMessage implements Message {

    private static final int UNMASKED_CHARACTERS = 3;

    private Message message;
    private String string;
    private Pattern pattern;

    public SecuredMessage(Message message, Collection<String> patterns) {
        this.message = message;
        this.pattern = compilePatterns(patterns);
    }

    public SecuredMessage(String string, Collection<String> patterns) {
        this.string = string;
        this.pattern = compilePatterns(patterns);
    }

    @Override
    public String getFormat() {
        return null;
    }

    @Override
    public Object[] getParameters() {
        return null;
    }

    @Override
    public String getFormattedMessage() {
        return securedMessage();
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }

    private String securedMessage() {
        if (message != null) {
            return securedMessage(message);
        } else if (string != null) {
            return securedString(string);
        }
        return "";
    }

    private Pattern compilePatterns(Collection<String> patterns) {
        return Pattern.compile(patterns.stream().map(it -> "(" + it + ")").collect(Collectors.joining("|")));
    }

    private String securedMessage(Message message) {
        return securedString(message.getFormattedMessage());
    }

    private String securedString(String string) {
        String result = string;
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String match = matcher.group();
            String mask = mask(match);
            result = result.replaceFirst(match, mask);
        }
        return result;
    }

    private String mask(String string) {
        return string.replaceAll(".", "*");
    }
}
