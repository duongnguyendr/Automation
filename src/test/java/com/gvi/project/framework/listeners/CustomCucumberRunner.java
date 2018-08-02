package com.gvi.project.framework.listeners;

import cucumber.api.junit.Cucumber;

import java.io.IOException;

import org.junit.runners.model.InitializationError;
import org.junit.runner.notification.RunNotifier;

public class CustomCucumberRunner extends Cucumber{
	public CustomCucumberRunner(Class<?> clazz) throws InitializationError, IOException {
		super(clazz);
	}
	
	 @Override
	    public void run(RunNotifier notifier) {
	        notifier.addListener(new JUnitExecutionListener());
	        super.run(notifier);
	    }

}
