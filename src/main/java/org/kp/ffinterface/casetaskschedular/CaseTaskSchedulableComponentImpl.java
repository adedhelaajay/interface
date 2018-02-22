package org.kp.ffinterface.casetaskschedular;

import org.kp.fulfillment.bo.MessageManager;
import org.kp.fulfillment.bo.MessageManagerInterfaceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CaseTaskSchedulableComponentImpl
	implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(CaseTaskSchedulableComponentImpl.class);
	
	private static CaseTaskSchedulableComponentImpl caseComponentImpl = null;
	/** Class Name  */
	public static final String CLASS_NAME =
		CaseTaskSchedulableComponentImpl.class.getName();

	
	public static CaseTaskSchedulableComponentImpl getInstance()
	{
		if(caseComponentImpl==null)
		{
			caseComponentImpl= new CaseTaskSchedulableComponentImpl();
		}
		return caseComponentImpl;
	}
	
	public void run() {
		String METHOD_NAME = CLASS_NAME + ".run";
		logger.info(METHOD_NAME + " Started!..");

		// Start the process for creating/updating a case.
		logger.info(METHOD_NAME + " refreshing after 5 minutes for new records");
		// System.out.println("inside shcedulable...");
		// removed the Message manager creation block outside the try block
		MessageManager messageManager = (MessageManager) MessageManagerInterfaceFactory.getInstance();
		
		if(null != messageManager) {
			logger.warn("messageManager object created.", MessageManager.CLASS_NAME);
		} else {
			logger.error("Error creating messageManager object.");
		}

		// added checking of counter value: if 1 then run the scheduler, else skip the
		// execution block
		if (messageManager.getSchedulerCounter() == 1) {
			try {
				messageManager.setSchedulerCounter(0);
				logger.info("after scheduler initialized.");
				// System.out.println("inside
				// shcedulable..."+messageManager.getSchedulerCounter());
				messageManager.processCaseRequestFromRecord();
				logger.info("after processCaseRequestFromRecord method call.");
				messageManager.setSchedulerCounter(1);
				logger.info("after scheduler set.");

			} catch (Throwable ex) {
				logger.error(METHOD_NAME + " error message -->" + ex.getMessage() + " cause:--" + ex.getCause());
				ex.printStackTrace(System.out);
				messageManager.setSchedulerCounter(1);

			}
			logger.info(METHOD_NAME + "Case request status at <timestamp>" + "is: Processed");
			// messageManager.setSchedulerCounter(1);
			// logger.info(METHOD_NAME +"Counter set to 0");
		} else {
			logger.info(METHOD_NAME + "another scheduler instance running. This schedulers counter is.."
					+ messageManager.getSchedulerCounter());
		}
		logger.info(METHOD_NAME + " ended!");

	}
}
