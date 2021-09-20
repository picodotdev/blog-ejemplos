///usr/bin/env jbang --quiet "$0" "$@" ; exit $? 

//DEPS org.apache.commons:commons-lang3:3.4

package io.github.picodotdev.blogbitix.javascripts;

import org.apache.commons.lang3.StringUtils;

public class Script3 {

    public static void main(String[] args) {
        System.out.printf("Script1 %s%n", StringUtils.join(args));
    }
}
