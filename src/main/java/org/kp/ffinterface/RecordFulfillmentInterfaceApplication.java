package org.kp.ffinterface;

import org.kp.ffinterface.service.LogReprocessorImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RecordFulfillmentInterfaceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(RecordFulfillmentInterfaceApplication.class,
				args);
		appContext.getBean(LogReprocessorImpl.class).getReprocessList();
	}

}
