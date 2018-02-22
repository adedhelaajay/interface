package org.kp.ffinterface.reprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//Application imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
* The logReprocessorDriver will use call the reprocessList()
* function of LogReprocessor to start the reprocessing process
* 
* Change History:
* <li>
*     Shekhar Singh Chauhan,12/28/2004,Created this Class
* </li>
* @author Sapient
* @version 0.9
*/
@Component
public class LogReprocessorDriver {
/**
 * This is the main method which calls the LogReprocessor class
 * function to start the reprocessing function.
 * @param args String[]
 */	
//	public static void main (String args[]) {
//		LogReprocessor reprocessor = LogReprocessor.getInstance();
//		reprocessor.reprocessList();
//	}
	private static final Logger logger = LoggerFactory.getLogger(LogReprocessorDriver.class);
	
	@Autowired
	LogReprocessor logReprocessor;
	
	public void getReprocessList() {
		logger.debug("Request for reprocessor at Driver");
		logReprocessor.reprocessList();
	}
	
	
} 