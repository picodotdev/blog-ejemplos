package io.github.picodotdev.blogbitix.githubpackages.utils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class Utils {

  public static String mask(String string) {
    return mask(string, 2, 6);
  }

  public static String mask(String string, int chars, int max) {
    String substring = string.substring(0, Math.min(chars, string.length()));
    String mask = repeat("*", Math.max(0, max - chars));
    return substring + mask;
  }

  public static Map<String, String> properties(String resource) throws IOException {
    Properties properties = new Properties();
    properties.load(Utils.class.getResourceAsStream("/muzooka-id-mapping.properties"));
    return properties.entrySet().stream().collect(Collectors.toMap(it -> it.getKey().toString(), it -> it.getValue().toString()));
  }

  private static String repeat(String string, int count) {
    return string.repeat(count);
  }
}
