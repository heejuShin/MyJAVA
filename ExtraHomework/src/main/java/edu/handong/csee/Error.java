package edu.handong.csee;


public class Error extends RuntimeException{

	public Error() {
		
	}

	public Error(String message) {}
	
	public String getMessage() {
		// TODO Auto-generated method stub
		System.out.println("No CLI argument Exception! Please put a file path.");
		System.exit(0);
		return null;
	}

}