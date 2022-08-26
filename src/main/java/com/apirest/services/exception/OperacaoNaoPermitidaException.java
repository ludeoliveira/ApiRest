package com.apirest.services.exception;

public class OperacaoNaoPermitidaException extends RuntimeException {
	private static final long serialVersionIUD = 1L;
	
	public OperacaoNaoPermitidaException(String msg) {
		super(msg);
	}
}
