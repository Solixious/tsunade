package com.konoha.tsunade.exceptions;

public class MyFileNotFoundException extends Exception {

	private static final long serialVersionUID = -1902654914919760278L;

	public MyFileNotFoundException(final String ex) {
		super(ex);
	}

	public MyFileNotFoundException(final String ex, final Throwable th) {
		super(ex, th);
	}
}
