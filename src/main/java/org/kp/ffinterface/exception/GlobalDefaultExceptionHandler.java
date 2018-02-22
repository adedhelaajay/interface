package org.kp.ffinterface.exception;

import java.text.DateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
	public static final String DEFAULT_ERROR_VIEW = "error";

	@ExceptionHandler(Exception.class)// handling all exceptions here
	public ModelAndView handleAllException(Exception ex) {

		ModelAndView model = new ModelAndView(DEFAULT_ERROR_VIEW);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		String formattedDate = dateFormat.format(date);
		
		model.addObject("serverTime", formattedDate);
		model.addObject("errMsg", ex.getMessage());
		logger.error("Cause: ", ex.getCause());
		return model;

	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handleError404(HttpServletRequest request, Exception e) {
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("exception", e);
		mav.addObject("errorcode", "404");
		mav.addObject("Message", "Resource not found!");
		
		return mav;
	}

}
