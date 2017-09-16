package io.github.picodotdev.blogbitix.javadoc;

import com.sun.tools.doclets.Taglet;
import com.sun.javadoc.Tag;
import java.util.Map;

/**
 * A sample Taglet representing @todo. This tag can be used in any kind of
 * {@link com.sun.javadoc.Doc}.  It is not an inline tag. The text is displayed
 * in yellow to remind the developer to perform a task. For
 * example, "@todo Fix this!" would be shown as.
 *
 * @author picodotdev
 */
public class TodoTaglet implements Taglet {

    private static final String NAME = "todo";
    private static final String HEADER = "Por hacer:";

    public String getName() {
        return NAME;
    }

    public boolean inField() {
        return true;
    }

    public boolean inConstructor() {
        return true;
    }

    public boolean inMethod() {
        return true;
    }

    public boolean inOverview() {
        return true;
    }

    public boolean inPackage() {
        return true;
    }

    public boolean inType() {
        return true;
    }

    public boolean isInlineTag() {
        return false;
    }

    public static void register(Map tagletMap) {
       TodoTaglet tag = new TodoTaglet();
       Taglet t = (Taglet) tagletMap.get(tag.getName());
       if (t != null) {
           tagletMap.remove(tag.getName());
       }
       tagletMap.put(tag.getName(), tag);
    }

    public String toString(Tag tag) {
        return "<b>" + HEADER + "</b><span style=\"background-color: yellow;\">" + tag.text() + "</span>\n";
    }

    public String toString(Tag[] tags) {
        if (tags.length == 0) {
            return null;
        }

        String result = "<b>" + HEADER + "</b><ul>" ;
        for (Tag tag : tags) {
            result += "<li><span style=\"background-color: yellow;\">" + tag.text() + "</span></li>";
        }
        result += "</ul>";
        return result;
    }
}
