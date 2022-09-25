package chusco.exceptions;

public class NoGameFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public NoGameFoundException(String errorMessage) {
        super(errorMessage);
    }
}
