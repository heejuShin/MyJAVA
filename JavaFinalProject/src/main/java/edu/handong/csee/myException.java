package edu.handong.csee;

import java.io.IOException;

public class myException extends Exception{
	private final Error code;

	public myException(Error code) {
		super();
		this.code = code;
	}
	public myException(String message, Error code) {
		super(message);
		this.code = code;
	}
	public myException(Throwable cause, Error code) {
		super(cause);
		this.code = code;
	}
	public myException(String message, Throwable cause, Error code) {
		super(message, cause);
		this.code = code;
	}
}