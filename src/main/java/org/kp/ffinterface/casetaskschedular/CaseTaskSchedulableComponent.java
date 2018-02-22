package org.kp.ffinterface.casetaskschedular;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import javax.servlet.http.HttpServlet;

import org.kp.fulfillment.bo.MessageManager;
import org.kp.fulfillment.bo.MessageManagerInterfaceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CaseTaskSchedulableComponent extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(CaseTaskSchedulableComponent.class);
	
	public static final String CLASS_NAME =
		CaseTaskSchedulableComponent.class.getName();
	
	@Autowired
	CaseTaskSchedulableComponentImpl caseTaskSchedulableComponentImpl;
	
	public void initializeScheduler() {
//		added setting of scheduler counter to 1 so that this is the working scheduler		
		MessageManager messageManager =
			(MessageManager) MessageManagerInterfaceFactory.getInstance();

		messageManager.setSchedulerCounter(1);
		
		String METHOD_NAME = CLASS_NAME + ".main";
		logger.debug("Interface manual start of task scheduler in progress.." +METHOD_NAME);
    ScheduledExecutorService scheduler1 =
      Executors.newSingleThreadScheduledExecutor();
    
    // Get a handle, starting now, with a 10 second delay
    final ScheduledFuture<?> timeHandle =
    	scheduler1.scheduleAtFixedRate(caseTaskSchedulableComponentImpl, 0, 30, SECONDS);    
    
    // Schedule the event, and run for 1 hour (60 * 60 seconds)
    /**
     * On some platforms, you'll have to setup this infinite loop to see output
    while (true) { }
     */
	}
    
    @Scheduled(fixedRate = 300000) 	// 3600000 scheduled for every 60 mins, for testing 300000 for 3 mins. 
    public void startCaseSchedular() {
		logger.info("The auto schedular turned on");
		MessageManager messageManager = (MessageManager) MessageManagerInterfaceFactory.getInstance();
		messageManager.setSchedulerCounter(1);

		String METHOD_NAME = CLASS_NAME + ".main";
		logger.info("Redesign Record Interface starting.." + METHOD_NAME);
		caseTaskSchedulableComponentImpl.run();
    }
	
  }

