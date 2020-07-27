package lottoland.Interfaces;

import lottoland.Exceptions.NoGameFoundException;
import lottoland.Exceptions.NoUserException;

public interface IExceptionController {
	public String NoGameFoundExceptionHandler(NoGameFoundException ex);
	public String NoUserExceptionHandler(NoUserException ex);
}
