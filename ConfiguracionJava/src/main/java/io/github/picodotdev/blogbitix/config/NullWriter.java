package io.github.picodotdev.blogbitix.config;

import java.io.Writer;

public class NullWriter extends Writer {

	public void close() {
	}

	public void flush() {
	}

	public void write(char[] b) {
	}

	public void write(char[] b, int off, int len) {
	}

	public void write(int b) {
	}

	public Writer append(char c) {
		return this;
	}
	
	public Writer append(CharSequence csq, int start, int end) {
		return this;
	} 
}