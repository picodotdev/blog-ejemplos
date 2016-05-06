package io.github.picodotdev.blogbitix.javaregex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String REGEX = "(\\d{3})-(\\d{6})/(\\d{1})";
    private static final String DEFAULT_STRING = "123-123456/1";

    public static void main(String[] args) {
        String string = (args.length > 0) ? args[0]: DEFAULT_STRING;

        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(string);

        if (matcher.matches()) {
            logger.info("Group count: {}", matcher.groupCount());
            logger.info("Group #1: {}", matcher.group(1));
            logger.info("Group #2: {}", matcher.group(2));
            logger.info("Group #3: {}", matcher.group(3));
        } else {
            logger.info("The expression \"{}\" does not matches regex.", string);
        }
    }
}
