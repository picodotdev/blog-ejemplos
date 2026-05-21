package io.github.picodotdev.blogbitix.javagradleplugin;

import java.nio.charset.Charset;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Version {

    private String version;
    private String build;
    private String hash;
    private String timestamp;

    public Version() {
        this(Collections.emptyMap());
    }

    public Version(Map<String, String> properties) {
        this.version = properties.getOrDefault("version", "0.0.0");
        this.build = properties.getOrDefault("build", System.getenv("BUILD_NUMBER"));
        this.hash = properties.getOrDefault("hash", getGitHash());
        this.timestamp = properties.getOrDefault("timestamp", DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ssX").format(ZonedDateTime.now(ZoneId.of("UTC"))));
        if (this.build == null) {
            this.build = "0";
        }
        if (this.hash == null) {
            this.hash = "0000000";
        }
    }

    public String getVersion() {
        return version;
    }

    public int getVersionMajor() {
        String n = version.split(".")[0];
        return Integer.parseInt(n);
    }

    public int getVersionMinor() {
        String n = version.split(".")[1];
        return Integer.parseInt(n);
    }

    public int getVersionPatch() {
        String n = version.split(".")[2];
        return Integer.parseInt(n);
    }

    public String getBuild() {
        return build;
    }

    public String getHash() {
        return hash;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getString() {
        List<String> strings = new ArrayList<>();
        strings.add(String.format("v%s", version));
        strings.add(String.format("b%s", build));
        strings.add(String.format("h%s", hash));
        strings.add(String.format("d%s", timestamp));
        return strings.stream().collect(Collectors.joining("-"));
    }

    public String getFormattedString() {
        return String.format(" (%s)", getString());
    }

    @Override
    public String toString() {
        return version;
    }

    private String getGitHash() {
        try {
            Process process = Runtime.getRuntime().exec("git log -n 1 --format=%h");
            process.waitFor(5000, TimeUnit.SECONDS);
            if (process.exitValue() != 0) {
                return null;
            }

            return new String(process.getInputStream().readAllBytes(), Charset.forName("UTF-8")).trim();
        } catch (Exception e) {
            return null;
        }
    }
}