package io.github.picodotdev.blogbitix.patronspecification.specification;

import java.util.Objects;

public class Specifications {

    public static String getAttributeName(String path, String property) {
        return (Objects.isNull(path)) ? property : path + "." + property;
    }
}
