package com.toshko.photoalbum.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.opencv.core.Core;

public class ServletContext implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
}
