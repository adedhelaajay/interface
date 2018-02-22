package org.kp.ffinterface.reprocessor;

import java.util.ArrayList;

import org.kp.fulfillment.dao.LogReprocessorDAO;
import org.kp.fulfillment.exception.FulfillmentMessageQueueException;
import org.kp.fulfillment.exception.InterfacesSQLException;
import org.kp.fulfillment.transport.MessageSender;
import org.kp.fulfillment.transport.MessageSenderEnum;
import org.kp.fulfillment.transport.MessageSenderEnum.MessageSenderEnumNotFoundException;
import org.kp.fulfillment.vo.FailedMessageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
* The log reprocessor will retrive a batch of failed messages from 
* the database, resend each of them to their 
* corresponding destination queue and then delete them
* from the database.
* 
* Change History:
* <li>
*     Shekhar Singh Chauhan,12/07/2004,Created this Class
* </li>
* @author Sapient
* @version 0.9
*/

@Component
public class LogReprocessor {

	/** single instance of LogReprocessor  */
	private static LogReprocessor instance = new LogReprocessor();
	
	private static final Logger logger = LoggerFactory.getLogger(LogReprocessor.class);
	
	/**
	 * Private Constructor for Log Reprocessor, so as to enforce that this 
	 * class can be instantiated only by calling the getInstance() method.
	 */
	private LogReprocessor() {

	}

	/**
	 * The convenience method to create an instance of the 
	 * <code>LogReprocessor</code>class. 
	 * 
	 * @return LogReprocessor An instance of LogReprocessor.
	 */
	public static LogReprocessor getInstance() {
		return instance;
	}

	/**
	 * This method is called to carry out all reprocessing tasks,
	 * i.e, getting messages from the database, resending them to
	 * their destination queue and then deleting them if they are 
	 * successfully put on the queue. 
	 * @throws InterfacesSQLException
	 */
	public void reprocessList() {
		
		logger.info("Checking reprocess list.");
		
		String METHOD_NAME = "ReprocessList";
		String xmlMessage = null;
		MessageSender msgSender = null;
		long queueOrdinal;
		long messageId;
		//int numRetries;
		String createdTimestamp;
		logger.debug(METHOD_NAME);
		LogReprocessorDAO reprocessorDao = LogReprocessorDAO.getInstance();
		ArrayList<FailedMessageVO> messageList;
		
		logger.info("Starting the reprocessing function......");
		ArrayList<FailedMessageVO> deletionList = new ArrayList<>();
		//get batch of failed messages from the database
		try {
			messageList = new ArrayList<FailedMessageVO>(reprocessorDao.getMessageList());
			logger.info("Number of messages to be processed : "
			+ messageList.size());		
			logger.debug(METHOD_NAME+"Size of messageList obtained is: " + 
			messageList.size());
			FailedMessageVO failedMessageVO = new FailedMessageVO();
			if ((messageList != null) && messageList.size()>0) {
				for(int counter=0; counter<messageList.size();counter++) {
					logger.debug("..");
					failedMessageVO = messageList
					.get(counter);
					xmlMessage = failedMessageVO.getMessage();
					queueOrdinal = failedMessageVO.getQueueId();
					messageId = failedMessageVO.getMessageId();
					createdTimestamp = failedMessageVO.getCreatedTimestamp();
					logger.debug(METHOD_NAME+"Sending Message: " 
					+ messageId);
					// try to send the message to its destination queue
					try {
						msgSender =
								MessageSender.getInstance(
								MessageSenderEnum.getByOrdinal(queueOrdinal));
						msgSender.sendMessage(xmlMessage,createdTimestamp);
						deletionList.add(failedMessageVO);
					} catch (MessageSenderEnumNotFoundException msenfe) {
						logger.error(METHOD_NAME+msenfe.toString());
					} catch (FulfillmentMessageQueueException e) {
						logger.error(e.getMessage());
						e.printStackTrace();
					}
					finally { // closing & logging in finally block
						if (msgSender != null) {
							try {
								msgSender.close();
							} catch (FulfillmentMessageQueueException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				} // end of for
				//delete the list of messages that were successfully
				//put on queue
				logger.info("\n Number of messages that were " +
					"successfully put back on queue: "
				+ deletionList.size());
				reprocessorDao.deleteMessageList(deletionList);
			} // end of if
		} catch (InterfacesSQLException ise) {
					logger.error(METHOD_NAME+ise.toString());
		}
		/* Log the End of the method */
		logger.info("Invoked ReprocessorList!");
	}
}
