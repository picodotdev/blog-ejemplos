package io.github.picodotdev.blogbitix.gradleversion.build

class ProjectVersion {

    private String major
    private String minor
    private String build
    private String hash

    public String toString() {
        def strings = []
        if (major) {
            strings.add(major)
        }
        if (minor) {
            strings.add(minor)
        }
        if (build) {
            strings.add("b$build")
        }
        if (hash) {
            strings.add(hash)
        }
        return strings.join('.')
    }
}
