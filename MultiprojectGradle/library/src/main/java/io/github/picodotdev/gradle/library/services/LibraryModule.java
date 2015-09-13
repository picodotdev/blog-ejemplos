package io.github.picodotdev.gradle.library.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.services.LibraryMapping;

public class LibraryModule {
    public static void contributeComponentClassResolver(Configuration <LibraryMapping> configuration) {
        configuration.add(new LibraryMapping("library", "io.github.picodotdev.gradle.library"));
    }
}