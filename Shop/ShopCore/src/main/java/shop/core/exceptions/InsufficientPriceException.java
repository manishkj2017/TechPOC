package shop.core.exceptions;

public class InsufficientPriceException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public InsufficientPriceException(String message){
		super(message);
		this.message = message;
		
	}
	
	public String getMessage(){
		return message;
	}
}
