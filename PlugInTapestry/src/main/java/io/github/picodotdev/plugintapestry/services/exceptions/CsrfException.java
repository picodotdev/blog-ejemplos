package io.github.picodotdev.plugintapestry.services.exceptions;

public class CsrfException extends Exception {

	 private static final long serialVersionUID = -5205940043310676114L;

	 public CsrfException() {
	 }

	 public CsrfException(String message) {
		  super(message);
	 }
}