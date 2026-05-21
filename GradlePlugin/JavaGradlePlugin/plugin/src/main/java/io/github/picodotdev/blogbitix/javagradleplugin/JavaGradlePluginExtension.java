package io.github.picodotdev.blogbitix.javagradleplugin;

import org.gradle.api.provider.Property;

public interface JavaGradlePluginExtension {

  Property<Boolean> getCheckstyleEnabled();
  Property<Boolean> getVersionEnabled();
}
