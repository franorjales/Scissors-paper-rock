package lottoland.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import lottoland.Commons.Constants;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = Constants.NO_USER_EXCEPTION_MESSAGE)
public class NoUserException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public NoUserException(String errorMessage) {
        super(errorMessage);
    }
}