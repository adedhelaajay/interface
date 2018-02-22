package org.kp.ffinterface.controller;

import org.kp.ffinterface.casetaskschedular.CaseTaskSchedulableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/caseTaskSchedulabler")
public class CaseTaskSchedulableController {

	private static final Logger logger = LoggerFactory.getLogger(CaseTaskSchedulableController.class);
	
	public static final String CLASS_NAME =
			CaseTaskSchedulableController.class.getName();
	
	@Autowired
	CaseTaskSchedulableComponent caseTaskSchedulableComponent;
	
	@RequestMapping(value="/init")
	public void startScheduler() {
		logger.info("Force initiated Scheduler in progress.");
		caseTaskSchedulableComponent.initializeScheduler();
		//caseTaskSchedulableComponent.startCaseSchedular(); -spring Scheduler to be picket up auto on context load.
	}

}
