package edu.handong.analysis.utils;


public class NotEnoughArgumentException extends RuntimeException{

	public NotEnoughArgumentException() {
		
	}

	public NotEnoughArgumentException(String message) {}
	
	public String getMessage() {
		// TODO Auto-generated method stub
		System.out.println("No CLI argument Exception! Please put a file path.");
		System.exit(0);
		return null;
	}

}
