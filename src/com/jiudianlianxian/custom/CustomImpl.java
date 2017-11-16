package com.jiudianlianxian.custom;

import java.util.Map;

import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.activity.ExternalActivityBehaviour;

public class CustomImpl implements ExternalActivityBehaviour {

	@Override
	public void execute(ActivityExecution execution) throws Exception {
		// 任何情况下都会执行
		System.out.println("停留在execute方法");
		execution.waitForSignal();
		System.out.println("execute方法已经执行");
	}

	@Override
	public void signal(ActivityExecution arg0, String arg1, Map<String, ?> arg2)
			throws Exception {
		// 在自定义活动停留在此节点，离开此节点才会执行此方法。
		System.out.println("signal方法已经执行");
	}

}
