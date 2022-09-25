package chusco.api;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import chusco.exceptions.NoGameFoundException;
import chusco.exceptions.NoUserException;
import chusco.interfaces.IExceptionController;

import org.springframework.http.HttpStatus;

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
