package io.github.picodotdev.blogbitix.javadoc;

import com.sun.tools.doclets.Taglet;
import com.sun.javadoc.Tag;
import java.util.Map;

/**
 * A sample Taglet representing @todo. This tag can be used in any kind of
 * {@link com.sun.javadoc.Doc}.  It is not an inline tag. The text is displayed
 * in yellow to remind the developer to perform a task.
 *
 * @author picodotdev
 */
public class TodoTaglet implements Taglet {

    private static final String NAME = "todo";
    private static final String HEADER = "To do:";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean inField() {
        return true;
    }

    @Override
    public boolean inConstructor() {
        return true;
    }

    @Override
    public boolean inMethod() {
        return true;
    }

    @Override
    public boolean inOverview() {
        return true;
    }

    @Override
    public boolean inPackage() {
        return true;
    }

    @Override
    public boolean inType() {
        return true;
    }

    @Override
    public boolean isInlineTag() {
        return false;
    }

    public static void register(Map<String, Taglet> tagletMap) {
       TodoTaglet tag = new TodoTaglet();
       Taglet t = (Taglet) tagletMap.get(tag.getName());
       if (t != null) {
           tagletMap.remove(tag.getName());
       }
       tagletMap.put(tag.getName(), tag);
    }

    @Override
    public String toString(Tag tag) {
        return "<span class=\"todoLabel\">" + HEADER + "</span><span class=\"todo\">" + tag.text() + "</span>\n";
    }

    @Override
    public String toString(Tag[] tags) {
        if (tags.length == 0) {
            return null;
        }

        String result = "<span class=\"todoLabel\">" + HEADER + "</span><ul class=\"todo\">" ;
        for (Tag tag : tags) {
            result += "<li><span style=\"background-color: yellow;\">" + tag.text() + "</span></li>";
        }
        result += "</ul>";
        return result;
    }
}
