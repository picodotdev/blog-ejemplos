package io.github.picodotdev.blogbitix.javagradleplugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.spotbugs.snom.SpotBugsExtension;
import com.github.spotbugs.snom.SpotBugsPlugin;
import org.apache.tools.ant.filters.ReplaceTokens;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.java.archives.Manifest;
import org.gradle.api.plugins.JavaApplication;
import org.gradle.api.plugins.quality.CheckstyleExtension;
import org.gradle.api.plugins.quality.CheckstylePlugin;
import org.gradle.api.plugins.quality.PmdExtension;
import org.gradle.api.plugins.quality.PmdPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.bundling.Jar;

public class JavaGradlePlugin implements Plugin<Project> {

    private static final String SPOTBUGS_EXCLUDE_RESOURCE = "/spotbugs/spotbugs-exclude.xml";
    private static final String CHECKSTYLE_CONFIG_RESOURCE = "/checkstyle/checkstyle.xml";
    private static final String CHECKSTYLE_SUPPRESSIONS_RESOURCE = "/checkstyle/suppressions.xml";

    public void apply(Project project) {
        project.getPluginManager().apply(PmdPlugin.class);
        project.getPluginManager().apply(CheckstylePlugin.class);
        project.getPluginManager().apply(SpotBugsPlugin.class);

        JavaGradlePluginExtension javaGradlePluginExtension = project.getExtensions().create("version", JavaGradlePluginExtension.class);
        javaGradlePluginExtension.getVersionEnabled().convention(Boolean.FALSE);

        PmdExtension pmdExtension = project.getExtensions().getByType(PmdExtension.class);
        pmdExtension.setToolVersion("6.44.0");
        pmdExtension.setConsoleOutput(true);
        pmdExtension.setIgnoreFailures(false);
        pmdExtension.setRuleSets(List.of("category/java/bestpractices.xml"));

        CheckstyleExtension checkstyleExtension = project.getExtensions().getByType(CheckstyleExtension.class);
        checkstyleExtension.setConfigFile(getFileFromResource(project, CHECKSTYLE_CONFIG_RESOURCE));
        checkstyleExtension.setConfigProperties(Map.of("suppressionFile", getFileFromResource(project, CHECKSTYLE_SUPPRESSIONS_RESOURCE)));
        checkstyleExtension.setMaxWarnings(0);
        checkstyleExtension.setMaxErrors(0);

        SpotBugsExtension spotBugsExtension = project.getExtensions().getByType(SpotBugsExtension.class);
        spotBugsExtension.getToolVersion().convention("4.2.2");
        spotBugsExtension.getShowProgress().convention(true);
        spotBugsExtension.getIgnoreFailures().convention(true);
        spotBugsExtension.getExcludeFilter().convention(() -> getFileFromResource(project, SPOTBUGS_EXCLUDE_RESOURCE));

        project.getTasks().named("processResources", Copy.class).configure(copy -> {
            copy.from("src/main/resources", (itCopySpec) -> {
                itCopySpec.setDuplicatesStrategy(DuplicatesStrategy.INCLUDE);
                itCopySpec.from("src/main/resources", (itFrom) -> {
                    itFrom.include("banner.txt");
                    itFrom.exclude("version.properties");
                });
                Map<String, String> tokens = new HashMap<>();
                tokens.put("gradle.app.name", project.getName());
                tokens.put("gradle.version.version", getPluginVersion(project).get().toString());
                tokens.put("gradle.version.build", getPluginVersion(project).get().getBuild());
                tokens.put("gradle.version.hash", getPluginVersion(project).get().getHash());
                tokens.put("gradle.version.timestamp", getPluginVersion(project).get().getTimestamp());
                tokens.put("gradle.version.string", getPluginVersion(project).get().getString());
                tokens.put("gradle.version.formatted-string", getPluginVersion(project).get().getFormattedString());
                itCopySpec.filter(Map.of("beginToken", "${", "endToken", "}", "tokens", tokens), ReplaceTokens.class);
            });
        });

        project.getTasks().named("jar", Jar.class).configure(jar -> {
            jar.manifest((Manifest manifest) -> {
                Map<String, String> attributes = new HashMap<>();
                attributes.put("Class-Path", project.getConfigurations().getByName("runtimeClasspath").getFiles().stream().map(it -> String.format("files/%s", it.getName())).collect(
                        Collectors.joining(" ")));
                if (project.getExtensions().getByType(JavaApplication.class).getMainClass().isPresent()) {
                    attributes.put("Main-Class", project.getExtensions().getByType(JavaApplication.class).getMainClass().get());
                }
                attributes.put("App-Name", project.getName());
                if (getPluginVersion(project).isPresent()) {
                    attributes.put("App-Version", getPluginVersion(project).get().toString());
                }
                manifest.attributes(attributes);
            });
        });
    }

    private File getFileFromResource(Project project, String resource) {
        return project.getResources().getText().fromUri(getClass().getResource(resource)).asFile();
    }

    private Optional<Version> getPluginVersion(Project project) {
        if (project.getVersion() instanceof Version) {
            return Optional.of((Version) project.getVersion());
        }
        return Optional.empty();
    }
}
