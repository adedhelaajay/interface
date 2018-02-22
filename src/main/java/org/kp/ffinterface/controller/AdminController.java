package org.kp.ffinterface.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@RequestMapping(value = "/")
	public ResponseEntity<?> ping(Locale locale) {
		logger.info("Invoked ping on the server.");
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		logger.info("Server up! time on server >>>> " +formattedDate);
		
		return new ResponseEntity<String>("Server up! time on server >>>> " +formattedDate, HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleAllException(Exception ex) {

		logger.warn("Exception encountered.");
		logger.error("Error: ", ex);
	
		return new ResponseEntity<String>("Exception occored processing request", HttpStatus.NOT_ACCEPTABLE);

	}
}
