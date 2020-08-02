package lottoland.Api;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import lottoland.Exceptions.NoGameFoundException;
import lottoland.Exceptions.NoUserException;
import lottoland.Interfaces.IExceptionController;

@ControllerAdvice
public class ExceptionsControllerImpl implements IExceptionController{
	
	  @Override
	  @ResponseBody
	  @ExceptionHandler(NoGameFoundException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  public String NoGameFoundExceptionHandler(NoGameFoundException ex) {
	    return ex.getMessage();
	  }
	  
	  @Override
	  @ResponseBody
	  @ExceptionHandler(NoUserException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  public String NoUserExceptionHandler(NoUserException ex) {
	    return ex.getMessage();
	  }
	  
	  
}
