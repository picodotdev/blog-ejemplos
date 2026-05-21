package io.github.picodotdev.bloblogbitix.javarecordbuilder;

import java.util.Collection;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record Book(String title, Collection<String> authors, String isbn, int year, int pages) {}
