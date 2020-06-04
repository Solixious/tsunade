package com.konoha.tsunade.exceptions;

import com.konoha.tsunade.constants.ErrorCode;

public class BaseBusinessException extends Exception {

	private static final long serialVersionUID = 4367486770142492720L;

	private ErrorCode errorCode;

	public BaseBusinessException(final ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode.name();
	}

	public String getDescription() {
		return errorCode.getDescription();
	}
}
