package io.github.picodotdev.todo.tapestry.components;

import org.apache.tapestry5.MarkupWriter;

public class HolaMundo {

    protected boolean beginRender(MarkupWriter writer) {
	    writer.write("Hola mundo Tapestry !!!");
		return false;
	}
}