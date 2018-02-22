package org.kp.ffinterface.service;

import org.kp.ffinterface.reprocessor.LogReprocessorDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogReprocessorImpl implements ReProcessorService {

	private static final Logger logger = LoggerFactory.getLogger(LogReprocessorImpl.class);
			
	@Autowired
	LogReprocessorDriver logReprocessorDriver;
	
	@Override
	public void getReprocessList() {
		logger.debug("Request logged for reprocess list at serveice");
		logReprocessorDriver.getReprocessList();
	}

}
