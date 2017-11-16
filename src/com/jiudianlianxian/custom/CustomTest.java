package com.jiudianlianxian.custom;

import java.util.List;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.junit.Test;

public class CustomTest {
	
	// 读取核心配置文件，并创建表
	ProcessEngine pe = Configuration.getProcessEngine();

	/**
	 * 部署流程定义
	 */
	@Test
	public void deploy() {
		// 获得部署对象
		NewDeployment deployment = pe.getRepositoryService().createDeployment();

		// 加载流程定义文档
		deployment.addResourceFromClasspath("com/jiudianlianxian/custom/custom.jpdl.xml");
		deployment.addResourceFromClasspath("com/jiudianlianxian/custom/custom.png");

		String id = deployment.deploy();
		System.out.println("" + id);
	}

	/**
	 * 启动一个流程实例
	 */
	@Test
	public void startProcess() {
		ProcessInstance pi = pe.getExecutionService()
				.startProcessInstanceByKey("custom");
		System.out.println("启动一个流程实例  " + pi.getId());
	}
	
	
	/**
	 * 查询我的任务列表
	 */
	@Test
	public void test3() {
		/*
		 * TaskQuery query = processEngine.getTaskService().createTaskQuery();
		 * query.assignee("张三"); List<Task> list = query.list();
		 */

		String userId = "商家";
		List<Task> list2 = pe.getTaskService().findPersonalTasks(userId);

		for (Task t : list2) {
			System.out.println(t.getId() + "----" + t.getName() + "----"
					+ t.getExecutionId());
		}
	}
	/**
	 * 办理任务
	 */
	@Test
	public void test1(){
		
		// 4.在办理任务时设置       设置流程变量
		String taskId = "440001";
		pe.getTaskService().completeTask(taskId);
		

	}
	
	/**
	 * 直接向后跳一步
	 */
	@Test
	public void test02(){
		String executionId = "custom.410001";
		
		pe.getExecutionService().signalExecutionById("custom.410001","to end1");
		
	}
	
}
