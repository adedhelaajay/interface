package org.kp.ffinterface.controller;

import org.kp.ffinterface.service.ReProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/reprocesslist")
public class ProcessorController {

	private static final Logger logger = LoggerFactory.getLogger(ProcessorController.class);
	
	@Autowired
	ReProcessorService service;

	@RequestMapping(value = "/init")
	public ResponseEntity<?> getReprocessList() {
		logger.info("Reprocesslist requested.");
		logger.info("Force start the reprocessList functionality.");
		service.getReprocessList();
		return new ResponseEntity<String>("ReprocessList logged!", HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class) // catch resource exceptions.
	public ModelAndView handleAllException(Exception ex) {

		ModelAndView model = new ModelAndView("error");
		model.addObject("errMsg", "this is Exception.class");

		return model;

	}
}
