package com.konoha.tsunade.exceptions;

public class FileStorageException extends Exception {

	private static final long serialVersionUID = -7362238474120258295L;

	public FileStorageException(final String ex) {
		super(ex);
	}

	public FileStorageException(final String ex, final Throwable th) {
		super(ex, th);
	}
}
