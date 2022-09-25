package chusco.interfaces;

import chusco.exceptions.NoGameFoundException;
import chusco.exceptions.NoUserException;

public interface IExceptionController {
	public String NoGameFoundExceptionHandler(NoGameFoundException ex);
	public String NoUserExceptionHandler(NoUserException ex);
}
