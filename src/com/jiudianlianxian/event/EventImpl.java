package com.jiudianlianxian.event;

import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;

public class EventImpl implements EventListener {

	@Override
	public void notify(EventListenerExecution arg0) throws Exception {
		
		System.out.println("notify--事件任务执行");
		
	}

}
